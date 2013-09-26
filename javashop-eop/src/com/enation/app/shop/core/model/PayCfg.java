package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class PayCfg
  implements Serializable
{
  protected Integer id;
  protected String name;
  protected String config;
  protected String biref;
  protected Double pay_fee;
  protected String type;

  public String getBiref()
  {
    return this.biref;
  }

  public void setBiref(String biref) {
    this.biref = biref;
  }
  public String getConfig() {
    return this.config;
  }
  public void setConfig(String config) {
    this.config = config;
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
  public Double getPay_fee() {
    return this.pay_fee;
  }
  public void setPay_fee(Double pay_fee) {
    this.pay_fee = pay_fee;
  }
  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.PayCfg
 * JD-Core Version:    0.6.1
 */