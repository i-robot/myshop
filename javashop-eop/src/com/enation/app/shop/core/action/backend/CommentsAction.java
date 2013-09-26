package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.MemberComment;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.app.shop.core.service.IMemberCommentManager;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IMemberOrderItemManager;
import com.enation.app.shop.core.service.IMemberPointManger;
import com.enation.eop.processor.httpcache.HttpCacheManager;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class CommentsAction extends WWAction
{
  private IMemberCommentManager memberCommentManager;
  private IMemberPointManger memberPointManger;
  private IMemberOrderItemManager memberOrderItemManager;
  private IMemberManager memberManager;
  private IGoodsManager goodsManager;
  private int type;
  private int status;
  private int comment_id;
  private MemberComment memberComment;
  private Member member;
  private String reply;
  private String id;

  public int getStatus()
  {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String execute()
  {
    System.out.println("execute");
    return null;
  }

  public String list()
  {
    setPageSize(10);
    this.webpage = this.memberCommentManager.getAllComments(getPage(), this.pageSize, this.type);

    return "bglist";
  }

  public String msgList() {
    this.webpage = this.memberCommentManager.getCommentsByStatus(getPage(), getPageSize(), this.type, this.status);
    return "bglist";
  }

  public String detail() {
    IUserService userService = UserServiceFactory.getUserService();
    int managerid = userService.getCurrentManagerId().intValue();
    this.memberComment = this.memberCommentManager.get(this.comment_id);
    if ((this.memberComment.getMember_id() != null) && (this.memberComment.getMember_id().intValue() != 0))
    {
      this.member = this.memberManager.get(this.memberComment.getMember_id());
    }
    if ((this.memberComment != null) && (!StringUtil.isEmpty(this.memberComment.getImg())))
    {
      this.memberComment.setImgPath(UploadUtil.replacePath(this.memberComment.getImg()));
    }

    return "detail";
  }

  public String add()
  {
    if (StringUtil.isEmpty(this.reply)) {
      this.json = "{'result':1,'message':'回复不能为空！'}";
      return "json_message";
    }
    MemberComment dbMemberComment = this.memberCommentManager.get(this.comment_id);
    if (dbMemberComment == null) {
      this.json = "{'result':1,'message':'此评论或咨询不存在！'}";
      return "json_message";
    }
    dbMemberComment.setReply(this.reply);
    dbMemberComment.setReplystatus(1);
    dbMemberComment.setReplytime(System.currentTimeMillis());
    this.memberCommentManager.update(dbMemberComment);
    this.json = "{'result':0,'message':'回复成功！'}";
    return "json_message";
  }

  public String delete() {
    try {
      if (!StringUtil.isEmpty(this.id)) {
        String[] ids = this.id.split(",");
        for (String tempId : ids) {
          if (!StringUtil.isEmpty(tempId)) {
            this.memberCommentManager.delete(StringUtil.toInt(tempId.replaceAll(" ", "")));
          }
        }
      }

      this.json = "{'result':0,'message':'操作成功'}";
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.json = "{'result':1,'message':'操作失败'}";
    }
    return "json_message";
  }

  public String hide() {
    try {
      this.memberComment = this.memberCommentManager.get(this.comment_id);
      this.memberComment.setStatus(2);
      this.memberCommentManager.update(this.memberComment);
      this.json = "{'result':0,'message':'操作成功'}";
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.json = "{'result':1,'message':'操作失败'}";
    }
    return "json_message";
  }

  public String show() {
    try {
      this.memberComment = this.memberCommentManager.get(this.comment_id);
      boolean isFirst = false;

      if ((this.memberCommentManager.getGoodsCommentsCount(this.memberComment.getGoods_id()) == 0) && (this.memberComment.getType() != 2))
      {
        isFirst = true;
      }

      this.memberComment.setStatus(1);
      this.memberCommentManager.update(this.memberComment);

      Map goods = this.goodsManager.get(Integer.valueOf(this.memberComment.getGoods_id()));
      if (goods != null) {
        String url = "";
        if (goods.get("cat_id") != null)
          switch (StringUtil.toInt(goods.get("cat_id").toString())) {
          case 1:
          case 2:
            url = "yxgoods";
            break;
          case 3:
          case 4:
          case 12:
          case 18:
            url = "jkgoods";
            break;
          case 6:
            url = "jpgoods";
            break;
          case 5:
          case 7:
          case 8:
          case 9:
            url = "goods";
            break;
          case 19:
            url = "gngoods";
          case 10:
          case 11:
          case 13:
          case 14:
          case 15:
          case 16:
          case 17: }  HttpCacheManager.updateUrlModified("/" + url + "-" + this.memberComment.getGoods_id() + ".html");
      }

      String reson = "文字评论";
      String type = "comment";
      if (!StringUtil.isEmpty(this.memberComment.getImg())) {
        type = "comment_img";
        reson = "上传图片评论";
      }

      if ((this.memberPointManger.checkIsOpen(type)) && 
        (this.memberComment.getMember_id() != null) && (this.memberComment.getMember_id().intValue() != 0) && (this.memberComment.getType() != 2))
      {
        int point = this.memberPointManger.getItemPoint(type + "_num");
        int mp = this.memberPointManger.getItemPoint(type + "_num_mp");
        this.memberPointManger.add(this.memberComment.getMember_id().intValue(), point, reson, null, mp);
      }

      if ((isFirst) && 
        (this.memberPointManger.checkIsOpen("first_comment")))
      {
        int point = this.memberPointManger.getItemPoint("first_comment_num");

        int mp = this.memberPointManger.getItemPoint("first_comment_num_mp");

        this.memberPointManger.add(this.memberComment.getMember_id().intValue(), point, "首次评论", null, mp);
      }

      this.json = "{'result':0,'message':'操作成功'}";
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.json = "{'result':1,'message':'操作失败'}";
    }
    return "json_message";
  }

  public String deletealone()
  {
    this.memberComment = this.memberCommentManager.get(this.comment_id);
    if (this.memberComment != null) {
      this.memberCommentManager.delete(this.comment_id);
    }
    this.msgs.add("删除成功");
    if (this.memberComment != null) {
      this.urls.put("评论列表", "comments!list.do?type=" + this.memberComment.getType());
    }
    else {
      this.urls.put("评论列表", "comments!list.do?type=1");
    }
    return "message";
  }

  public IMemberPointManger getMemberPointManger() {
    return this.memberPointManger;
  }

  public void setMemberPointManger(IMemberPointManger memberPointManger) {
    this.memberPointManger = memberPointManger;
  }

  public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager)
  {
    this.memberOrderItemManager = memberOrderItemManager;
  }

  public void setMemberCommentManager(IMemberCommentManager memberCommentManager)
  {
    this.memberCommentManager = memberCommentManager;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getComment_id() {
    return this.comment_id;
  }

  public void setComment_id(int comment_id) {
    this.comment_id = comment_id;
  }

  public MemberComment getMemberComment() {
    return this.memberComment;
  }

  public void setMemberManager(IMemberManager memberManager) {
    this.memberManager = memberManager;
  }

  public Member getMember() {
    return this.member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public String getReply() {
    return this.reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setGoodsManager(IGoodsManager goodsManager) {
    this.goodsManager = goodsManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.CommentsAction
 * JD-Core Version:    0.6.1
 */