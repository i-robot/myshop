package com.enation.app.shop.core.model;

public class AdjunctItem
{
  private int productid;
  private int goodsid;
  private String name;
  private String specs;
  private Double price;
  private Double coupPrice;
  private int num;

  public int getProductid()
  {
    return this.productid;
  }
  public void setProductid(int productid) {
    this.productid = productid;
  }
  public int getGoodsid() {
    return this.goodsid;
  }
  public void setGoodsid(int goodsid) {
    this.goodsid = goodsid;
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
  public Double getCoupPrice() {
    return this.coupPrice;
  }
  public void setCoupPrice(Double coupPrice) {
    this.coupPrice = coupPrice;
  }
  public String getSpecs() {
    return this.specs;
  }
  public void setSpecs(String specs) {
    this.specs = specs;
  }

  public int getNum() {
    return this.num;
  }
  public void setNum(int num) {
    this.num = num;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.AdjunctItem
 * JD-Core Version:    0.6.1
 */