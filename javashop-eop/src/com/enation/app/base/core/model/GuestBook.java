package com.enation.app.base.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import com.enation.framework.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestBook
{
  private Integer id;
  private Integer parentid;
  private String title;
  private String content;
  private Integer issubject;
  private Long dateline;
  private String username;
  private String email;
  private String qq;
  private String tel;
  private Integer sex;
  private String ip;
  private String area;
  private String addtime;
  private List replyList;

  public GuestBook()
  {
    this.replyList = new ArrayList();
  }

  public void addReply(GuestBook reply)
  {
    this.replyList.add(reply);
  }

  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle()
  {
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

  public Integer getParentid()
  {
    return this.parentid;
  }

  public void setParentid(Integer parentid) {
    this.parentid = parentid;
  }

  public Integer getIssubject() {
    return this.issubject;
  }

  public void setIssubject(Integer issubject) {
    this.issubject = issubject;
  }

  public Long getDateline() {
    return this.dateline;
  }

  public void setDateline(Long dateline) {
    this.dateline = dateline;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  @NotDbField
  public String getAddtime() {
    this.addtime = DateUtil.toString(new Date(this.dateline.longValue() * 1000L), "MM-dd hh:mm");

    return this.addtime;
  }

  public Integer getSex() {
    return this.sex;
  }

  public void setAddtime(String addtime) {
    this.addtime = addtime;
  }

  @NotDbField
  public List getReplyList()
  {
    return this.replyList;
  }

  public void setReplyList(List replyList) {
    this.replyList = replyList;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getQq() {
    return this.qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getIp()
  {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getArea() {
    return this.area;
  }

  public void setArea(String area) {
    this.area = area;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.GuestBook
 * JD-Core Version:    0.6.1
 */