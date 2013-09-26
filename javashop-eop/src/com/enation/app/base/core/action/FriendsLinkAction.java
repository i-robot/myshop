package com.enation.app.base.core.action;

import com.enation.app.base.core.model.FriendsLink;
import com.enation.app.base.core.service.IFriendsLinkManager;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.FileUtil;
import java.io.File;
import java.util.List;
import java.util.Map;

public class FriendsLinkAction extends WWAction
{
  private String id;
  private FriendsLink friendsLink;
  private IFriendsLinkManager friendsLinkManager;
  private List listLink;
  private File pic;
  private String picFileName;
  private String oldpic;
  private int link_id;

  public String list()
  {
    this.listLink = this.friendsLinkManager.listLink();
    return "list";
  }

  public String add() {
    return "add";
  }

  public String delete() {
    try {
      this.friendsLinkManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String addSave() {
    if (this.pic != null)
      if (FileUtil.isAllowUp(this.picFileName)) {
        this.friendsLink.setLogo(UploadUtil.upload(this.pic, this.picFileName, "friendslink"));
      } else {
        this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
        return "message";
      }
    try
    {
      this.friendsLinkManager.add(this.friendsLink);
      this.msgs.add("友情链接添加成功");
      this.urls.put("友情链接列表", "friendsLink!list.do");
    } catch (Exception e) {
      e.printStackTrace();
      this.msgs.add("友情链接添加失败");
      this.urls.put("友情链接列表", "friendsLink!list.do");
    }
    return "message";
  }

  public String edit() {
    this.friendsLink = this.friendsLinkManager.get(this.link_id);
    return "edit";
  }
  public String editSave() {
    if (this.pic != null)
      if (FileUtil.isAllowUp(this.picFileName)) {
        this.friendsLink.setLogo(UploadUtil.upload(this.pic, this.picFileName, "friendslink"));
      } else {
        this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
        return "message";
      }
    try
    {
      this.friendsLinkManager.update(this.friendsLink);
      this.msgs.add("友情链接修改成功");
      this.urls.put("友情链接列表", "friendsLink!list.do");
    } catch (Exception e) {
      e.printStackTrace();
      this.msgs.add("友情链接修改失败");
      this.urls.put("友情链接列表", "!list.do");
    }
    return "message";
  }

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public FriendsLink getFriendsLink() {
    return this.friendsLink;
  }
  public void setFriendsLink(FriendsLink friendsLink) {
    this.friendsLink = friendsLink;
  }
  public IFriendsLinkManager getFriendsLinkManager() {
    return this.friendsLinkManager;
  }
  public void setFriendsLinkManager(IFriendsLinkManager friendsLinkManager) {
    this.friendsLinkManager = friendsLinkManager;
  }
  public List getListLink() {
    return this.listLink;
  }
  public void setListLink(List listLink) {
    this.listLink = listLink;
  }

  public File getPic() {
    return this.pic;
  }

  public void setPic(File pic) {
    this.pic = pic;
  }

  public String getPicFileName() {
    return this.picFileName;
  }

  public void setPicFileName(String picFileName) {
    this.picFileName = picFileName;
  }

  public String getOldpic() {
    return this.oldpic;
  }

  public void setOldpic(String oldpic) {
    this.oldpic = oldpic;
  }

  public int getLink_id() {
    return this.link_id;
  }

  public void setLink_id(int linkId) {
    this.link_id = linkId;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.FriendsLinkAction
 * JD-Core Version:    0.6.1
 */