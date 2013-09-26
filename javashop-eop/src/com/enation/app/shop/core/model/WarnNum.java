package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class WarnNum
  implements Serializable
{
  private Integer id;
  private Integer goods_id;
  private Double sphere;
  private Double cylinder;
  private Integer product_id;
  private String color;
  private Integer warn_num;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getGoods_id() {
    return this.goods_id;
  }

  public void setGoods_id(Integer goods_id) {
    this.goods_id = goods_id;
  }

  public Double getSphere() {
    return this.sphere;
  }

  public void setSphere(Double sphere) {
    this.sphere = sphere;
  }

  public Double getCylinder() {
    return this.cylinder;
  }

  public void setCylinder(Double cylinder) {
    this.cylinder = cylinder;
  }

  public Integer getProduct_id() {
    return this.product_id;
  }

  public void setProduct_id(Integer product_id) {
    this.product_id = product_id;
  }

  public String getColor() {
    return this.color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Integer getWarn_num() {
    return this.warn_num;
  }

  public void setWarn_num(Integer warn_num) {
    this.warn_num = warn_num;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.WarnNum
 * JD-Core Version:    0.6.1
 */