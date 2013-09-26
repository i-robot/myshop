package com.enation.app.shop.core.model.support;

public class Adjunct
{
  private String name;
  private Double price;
  private Double old_price;
  private Integer goods_id;
  private String sn;
  private String spec_id;

  public String getSpec_id()
  {
    return this.spec_id;
  }
  public void setSpec_id(String spec_id) {
    this.spec_id = spec_id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Double getPrice() {
    return this.price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }
  public Double getOld_price() {
    return this.old_price;
  }
  public void setOld_price(Double old_price) {
    this.old_price = old_price;
  }
  public Integer getGoods_id() {
    return this.goods_id;
  }
  public void setGoods_id(Integer goods_id) {
    this.goods_id = goods_id;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.Adjunct
 * JD-Core Version:    0.6.1
 */