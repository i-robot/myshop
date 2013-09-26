package com.enation.app.shop.core.model;

import com.enation.app.shop.core.model.support.DlyTypeConfig;
import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;
import java.util.List;

public class DlyType
  implements Serializable
{
  private Integer type_id;
  private String name;
  private Integer protect;
  private Float protect_rate;
  private Float min_price;
  private Integer has_cod;
  private Integer corp_id;
  private Integer ordernum;
  private Integer disabled;
  private Integer is_same;
  private String detail;
  private String config;
  private String expressions;
  private String json;
  private DlyTypeConfig typeConfig;
  private List typeAreaList;
  private Double price;

  @NotDbField
  public String getJson()
  {
    return this.json;
  }

  public void setJson(String json)
  {
    this.json = json;
  }

  public String getDetail()
  {
    return this.detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Float getMin_price() {
    return this.min_price;
  }

  public void setMin_price(Float min_price) {
    this.min_price = min_price;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Float getProtect_rate() {
    return this.protect_rate;
  }

  public void setProtect_rate(Float protect_rate) {
    this.protect_rate = protect_rate;
  }

  @PrimaryKeyField
  public Integer getType_id() {
    return this.type_id;
  }

  public void setType_id(Integer type_id) {
    this.type_id = type_id;
  }

  public Integer getProtect() {
    return Integer.valueOf(this.protect == null ? 0 : this.protect.intValue());
  }

  public void setProtect(Integer protect) {
    this.protect = protect;
  }

  public Integer getHas_cod() {
    return this.has_cod;
  }

  public void setHas_cod(Integer hasCod) {
    this.has_cod = hasCod;
  }

  public Integer getCorp_id() {
    return this.corp_id;
  }

  public void setCorp_id(Integer corpId) {
    this.corp_id = corpId;
  }

  public Integer getOrdernum() {
    return this.ordernum;
  }

  public void setOrdernum(Integer ordernum) {
    this.ordernum = ordernum;
  }

  public Integer getDisabled() {
    return this.disabled;
  }

  public void setDisabled(Integer disabled) {
    this.disabled = disabled;
  }

  public Integer getIs_same() {
    return this.is_same;
  }

  public void setIs_same(Integer isSame) {
    this.is_same = isSame;
  }

  public String getExpressions()
  {
    return this.expressions;
  }

  public void setExpressions(String expressions) {
    this.expressions = expressions;
  }

  public String getConfig() {
    return this.config;
  }

  public void setConfig(String config) {
    this.config = config;
  }

  @NotDbField
  public DlyTypeConfig getTypeConfig() {
    return this.typeConfig;
  }

  public void setTypeConfig(DlyTypeConfig typeConfig) {
    this.typeConfig = typeConfig;
  }

  @NotDbField
  public List getTypeAreaList() {
    return this.typeAreaList;
  }

  public void setTypeAreaList(List typeAreaList) {
    this.typeAreaList = typeAreaList;
  }

  @NotDbField
  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price)
  {
    this.price = price;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.DlyType
 * JD-Core Version:    0.6.1
 */