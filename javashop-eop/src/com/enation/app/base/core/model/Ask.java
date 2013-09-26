package com.enation.app.base.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.util.List;

public class Ask
{
  private Integer askid;
  private String title;
  private String content;
  private Long dateline;
  private Integer isreply;
  private Integer userid;
  private Integer siteid;
  private String domain;
  private String sitename;
  private String username;
  private List replyList;

  @PrimaryKeyField
  public Integer getAskid()
  {
    return this.askid;
  }
  public void setAskid(Integer askid) {
    this.askid = askid;
  }
  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContent() {
    return this.content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Long getDateline() {
    return this.dateline;
  }
  public void setDateline(Long dateline) {
    this.dateline = dateline;
  }
  public Integer getIsreply() {
    return this.isreply;
  }
  public void setIsreply(Integer isreply) {
    this.isreply = isreply;
  }
  public Integer getUserid() {
    return this.userid;
  }
  public void setUserid(Integer userid) {
    this.userid = userid;
  }
  public Integer getSiteid() {
    return this.siteid;
  }
  public void setSiteid(Integer siteid) {
    this.siteid = siteid;
  }
  public String getDomain() {
    return this.domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
  }
  public String getSitename() {
    return this.sitename;
  }
  public void setSitename(String sitename) {
    this.sitename = sitename;
  }
  public String getUsername() {
    return this.username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  @NotDbField
  public List getReplyList() {
    return this.replyList;
  }
  public void setReplyList(List replyList) {
    this.replyList = replyList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.Ask
 * JD-Core Version:    0.6.1
 */