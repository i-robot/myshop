package com.enation.eop.resource.model;

import com.enation.framework.database.PrimaryKeyField;

public class Border extends Resource
{
  private Integer id;
  private String borderid;
  private String bordername;
  private String themepath;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getBorderid() {
    return this.borderid;
  }
  public void setBorderid(String borderid) {
    this.borderid = borderid;
  }
  public String getBordername() {
    return this.bordername;
  }
  public void setBordername(String bordername) {
    this.bordername = bordername;
  }
  public String getThemepath() {
    return this.themepath;
  }
  public void setThemepath(String themepath) {
    this.themepath = themepath;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.Border
 * JD-Core Version:    0.6.1
 */