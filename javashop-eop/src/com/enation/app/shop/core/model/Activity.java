package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class Activity
  implements Serializable
{
  private Integer id;
  private String name;
  private Integer enable;
  private Long begin_time;
  private Long end_time;
  private String brief;
  private Integer disabled;

  public Long getBegin_time()
  {
    return this.begin_time;
  }
  public void setBegin_time(Long begin_time) {
    this.begin_time = begin_time;
  }
  public String getBrief() {
    return this.brief;
  }
  public void setBrief(String brief) {
    this.brief = brief;
  }
  public Integer getDisabled() {
    return this.disabled;
  }
  public void setDisabled(Integer disabled) {
    this.disabled = disabled;
  }
  public Integer getEnable() {
    return this.enable;
  }
  public void setEnable(Integer enable) {
    this.enable = enable;
  }
  public Long getEnd_time() {
    return this.end_time;
  }
  public void setEnd_time(Long end_time) {
    this.end_time = end_time;
  }
  @PrimaryKeyField
  public Integer getId() {
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
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Activity
 * JD-Core Version:    0.6.1
 */