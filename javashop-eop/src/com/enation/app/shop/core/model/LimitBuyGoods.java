package com.enation.app.shop.core.model;

public class LimitBuyGoods
{
  private Integer limitbuyid;
  private Integer goodsid;
  private Double price;

  public Integer getGoodsid()
  {
    return this.goodsid;
  }
  public void setGoodsid(Integer goodsid) {
    this.goodsid = goodsid;
  }
  public Double getPrice() {
    return this.price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
  public Integer getLimitbuyid() {
    return this.limitbuyid;
  }
  public void setLimitbuyid(Integer limitbuyid) {
    this.limitbuyid = limitbuyid;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.LimitBuyGoods
 * JD-Core Version:    0.6.1
 */