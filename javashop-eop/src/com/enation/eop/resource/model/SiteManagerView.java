package com.enation.eop.resource.model;

import com.enation.framework.database.PrimaryKeyField;

public class SiteManagerView
{
  private Integer id;
  private Integer userid;
  private String sitename;
  private Integer siteid;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserid() {
    return this.userid;
  }
  public void setUserid(Integer userid) {
    this.userid = userid;
  }
  public String getSitename() {
    return this.sitename;
  }
  public void setSitename(String sitename) {
    this.sitename = sitename;
  }
  public Integer getSiteid() {
    return this.siteid;
  }
  public void setSiteid(Integer siteid) {
    this.siteid = siteid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.SiteManagerView
 * JD-Core Version:    0.6.1
 */