package com.enation.eop.resource.model;

import com.enation.framework.database.PrimaryKeyField;

public class EopSiteAdmin
{
  private Integer id;
  private Integer managerid;
  private Integer siteid;
  private Integer userid;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getManagerid() {
    return this.managerid;
  }

  public void setManagerid(Integer managerid) {
    this.managerid = managerid;
  }

  public Integer getSiteid() {
    return this.siteid;
  }

  public void setSiteid(Integer siteid) {
    this.siteid = siteid;
  }

  public Integer getUserid() {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.EopSiteAdmin
 * JD-Core Version:    0.6.1
 */