package com.enation.eop.resource.model;

import com.enation.framework.database.PrimaryKeyField;

public class EopSiteDomain
{
  private Integer id;
  private String domain;
  private Integer domaintype;
  private Integer siteid;
  private Integer userid;
  private Integer status;

  public Integer getStatus()
  {
    return this.status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  public Integer getUserid() {
    return this.userid;
  }
  public void setUserid(Integer userid) {
    this.userid = userid;
  }
  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getDomain() {
    return this.domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
  }
  public Integer getDomaintype() {
    return this.domaintype;
  }
  public void setDomaintype(Integer domaintype) {
    this.domaintype = domaintype;
  }
  public Integer getSiteid() {
    return this.siteid;
  }
  public void setSiteid(Integer siteid) {
    this.siteid = siteid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.EopSiteDomain
 * JD-Core Version:    0.6.1
 */