package com.enation.app.base.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import com.enation.framework.model.Image;
import java.util.List;

public class DataLog
{
  private Integer id;
  private String content;
  private String url;
  private String pics;
  private String sitename;
  private String domain;
  private String logtype;
  private String optype;
  private Long dateline;
  private Integer userid;
  private Integer siteid;
  private List<Image> picList;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPics() {
    return this.pics;
  }

  public void setPics(String pics) {
    this.pics = pics;
  }

  public String getSitename() {
    return this.sitename;
  }

  public void setSitename(String sitename) {
    this.sitename = sitename;
  }

  public String getLogtype() {
    return this.logtype;
  }

  public void setLogtype(String logtype) {
    this.logtype = logtype;
  }

  public String getOptype() {
    return this.optype;
  }

  public void setOptype(String optype) {
    this.optype = optype;
  }

  public Long getDateline() {
    return this.dateline;
  }

  public void setDateline(Long dateline) {
    this.dateline = dateline;
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

  @NotDbField
  public List<Image> getPicList() {
    return this.picList;
  }

  public void setPicList(List<Image> picList) {
    this.picList = picList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.DataLog
 * JD-Core Version:    0.6.1
 */