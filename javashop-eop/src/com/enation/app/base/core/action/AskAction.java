package com.enation.app.base.core.action;

import com.enation.app.base.core.model.Ask;
import com.enation.app.base.core.model.Reply;
import com.enation.app.base.core.service.IAskManager;
import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.EopUser;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AskAction extends WWAction
{
  private IAskManager askManager;
  private IUserManager userManager;
  private Integer askid;
  private Ask ask;
  private Integer[] id;
  private String keyword;
  private String startTime;
  private String endTime;
  private String title;
  private String content;

  public String listAll()
  {
    this.keyword = (StringUtil.isEmpty(this.keyword) ? null : StringUtil.toUTF8(this.keyword));
    Date start = StringUtil.isEmpty(this.startTime) ? null : DateUtil.toDate(this.startTime, "yyyy-MM-dd");
    Date end = StringUtil.isEmpty(this.endTime) ? null : DateUtil.toDate(this.endTime, "yyyy-MM-dd");
    this.webpage = this.askManager.listAllAsk(this.keyword, start, end, getPage(), getPageSize());

    return "alllist";
  }

  public String listMy()
  {
    this.keyword = (StringUtil.isEmpty(this.keyword) ? null : StringUtil.toUTF8(this.keyword));
    Date start = StringUtil.isEmpty(this.startTime) ? null : DateUtil.toDate(this.startTime, "yyyy-MM-dd");
    Date end = StringUtil.isEmpty(this.endTime) ? null : DateUtil.toDate(this.endTime, "yyyy-MM-dd");
    this.webpage = this.askManager.listMyAsk(this.keyword, start, end, getPage(), getPageSize());

    return "mylist";
  }

  public String userview()
  {
    this.ask = this.askManager.get(this.askid);
    return "user_view";
  }

  public String adminview()
  {
    this.ask = this.askManager.get(this.askid);
    return "admin_view";
  }

  public String adminReply()
  {
    Reply reply = new Reply();
    reply.setAskid(this.askid);
    reply.setContent(this.content);
    reply.setUsername("客服");
    this.askManager.reply(reply);
    this.msgs.add("回答已经提交");
    this.urls.put("问题列表", "ask!listAll.do");
    return "message";
  }

  public String userReply()
  {
    IUserService userService = UserServiceFactory.getUserService();
    Integer userid = userService.getCurrentUserId();
    EopUser user = this.userManager.get(userid);

    Reply reply = new Reply();
    reply.setAskid(this.askid);
    reply.setContent(this.content);
    reply.setUsername(user.getUsername());
    this.askManager.reply(reply);
    this.msgs.add("回答已经提交");
    this.urls.put("问题列表", "ask!listMy.do");
    return "message";
  }

  public String toAsk()
  {
    return "ask";
  }

  public String ask() {
    Ask ask = new Ask();
    ask.setTitle(this.title);
    ask.setContent(this.content);
    this.askManager.add(ask);
    this.msgs.add("问题已经提交");
    this.urls.put("问题列表", "ask!listMy.do");
    return "message";
  }

  public String delete() {
    try {
      this.askManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public Integer[] getId() {
    return this.id;
  }

  public void setId(Integer[] id)
  {
    this.id = id;
  }

  public IAskManager getAskManager()
  {
    return this.askManager;
  }

  public void setAskManager(IAskManager askManager) {
    this.askManager = askManager;
  }

  public String getKeyword() {
    return this.keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getStartTime() {
    return this.startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return this.endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public Ask getAsk()
  {
    return this.ask;
  }

  public void setAsk(Ask ask)
  {
    this.ask = ask;
  }

  public Integer getAskid()
  {
    return this.askid;
  }

  public void setAskid(Integer askid)
  {
    this.askid = askid;
  }

  public IUserManager getUserManager()
  {
    return this.userManager;
  }

  public void setUserManager(IUserManager userManager)
  {
    this.userManager = userManager;
  }

  public String getContent()
  {
    return this.content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.AskAction
 * JD-Core Version:    0.6.1
 */