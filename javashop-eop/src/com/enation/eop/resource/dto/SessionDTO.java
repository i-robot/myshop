package com.enation.eop.resource.dto;

public class SessionDTO
{
  private Integer userid;
  private Integer defaultsiteid;
  private Integer managerid;

  public Integer getUserid()
  {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public Integer getDefaultsiteid() {
    return this.defaultsiteid;
  }

  public void setDefaultsiteid(Integer defaultsiteid) {
    this.defaultsiteid = defaultsiteid;
  }

  public Integer getManagerid() {
    return this.managerid;
  }

  public void setManagerid(Integer managerid) {
    this.managerid = managerid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.dto.SessionDTO
 * JD-Core Version:    0.6.1
 */