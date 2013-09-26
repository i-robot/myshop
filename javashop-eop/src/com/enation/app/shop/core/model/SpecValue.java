package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class SpecValue
{
  private Integer spec_value_id;
  private Integer spec_id;
  private String spec_value;
  private String spec_image;
  private Integer spec_order;
  private Integer spec_type;

  public Integer getSpec_type()
  {
    return this.spec_type;
  }
  public void setSpec_type(Integer specType) {
    this.spec_type = specType;
  }

  @PrimaryKeyField
  public Integer getSpec_value_id() {
    return this.spec_value_id;
  }
  public void setSpec_value_id(Integer specValueId) {
    this.spec_value_id = specValueId;
  }
  public Integer getSpec_id() {
    return this.spec_id;
  }
  public void setSpec_id(Integer specId) {
    this.spec_id = specId;
  }
  public String getSpec_value() {
    return this.spec_value;
  }
  public void setSpec_value(String specValue) {
    this.spec_value = specValue;
  }
  public String getSpec_image() {
    return this.spec_image;
  }
  public void setSpec_image(String specImage) {
    this.spec_image = specImage;
  }
  public Integer getSpec_order() {
    return this.spec_order;
  }
  public void setSpec_order(Integer specOrder) {
    this.spec_order = specOrder;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.SpecValue
 * JD-Core Version:    0.6.1
 */