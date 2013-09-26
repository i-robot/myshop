package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class PromotionActivity
{
  private Integer id;
  private String name;
  private int enable;
  private Long begin_time;
  private Long end_time;
  private String brief;
  private int disabled;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getEnable() {
    return this.enable;
  }
  public void setEnable(int enable) {
    this.enable = enable;
  }
  public Long getBegin_time() {
    return this.begin_time;
  }
  public void setBegin_time(Long beginTime) {
    this.begin_time = beginTime;
  }
  public Long getEnd_time() {
    return this.end_time;
  }
  public void setEnd_time(Long endTime) {
    this.end_time = endTime;
  }
  public String getBrief() {
    return this.brief;
  }
  public void setBrief(String brief) {
    this.brief = brief;
  }
  public int getDisabled() {
    return this.disabled;
  }
  public void setDisabled(int disabled) {
    this.disabled = disabled;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.PromotionActivity
 * JD-Core Version:    0.6.1
 */