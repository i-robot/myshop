package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class FreeOfferCategory
{
  private Integer cat_id;
  private String cat_name;
  private Integer publishable;
  private Integer sorder;
  private Integer disabled;
  private Integer userid;
  private Integer siteid;

  @PrimaryKeyField
  public Integer getCat_id()
  {
    return this.cat_id;
  }

  public void setCat_id(Integer catId) {
    this.cat_id = catId;
  }

  public String getCat_name() {
    return this.cat_name;
  }

  public void setCat_name(String catName) {
    this.cat_name = catName;
  }

  public Integer getPublishable() {
    return this.publishable;
  }

  public void setPublishable(Integer publishable) {
    this.publishable = publishable;
  }

  public Integer getSorder() {
    return this.sorder;
  }

  public void setSorder(Integer sorder) {
    this.sorder = sorder;
  }

  public Integer getDisabled() {
    return this.disabled;
  }

  public void setDisabled(Integer disabled) {
    this.disabled = disabled;
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
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.FreeOfferCategory
 * JD-Core Version:    0.6.1
 */