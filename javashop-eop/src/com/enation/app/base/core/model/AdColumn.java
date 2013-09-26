package com.enation.app.base.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class AdColumn
{
  private Integer acid;
  private String cname;
  private String width;
  private String height;
  private String description;
  private Integer anumber;
  private Integer atype;
  private Integer rule;
  private Integer userid;
  private Integer siteid;
  private String disabled;

  @PrimaryKeyField
  public Integer getAcid()
  {
    return this.acid;
  }

  public void setAcid(Integer acid) {
    this.acid = acid;
  }

  public String getCname() {
    return this.cname;
  }

  public void setCname(String cname) {
    this.cname = cname;
  }

  public String getWidth() {
    return this.width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getHeight() {
    return this.height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getAnumber() {
    return this.anumber;
  }

  public void setAnumber(Integer anumber) {
    this.anumber = anumber;
  }

  public Integer getAtype() {
    return this.atype;
  }

  public void setAtype(Integer atype) {
    this.atype = atype;
  }

  public Integer getRule() {
    return this.rule;
  }

  public void setRule(Integer rule) {
    this.rule = rule;
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

  public String getDisabled() {
    return this.disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.AdColumn
 * JD-Core Version:    0.6.1
 */