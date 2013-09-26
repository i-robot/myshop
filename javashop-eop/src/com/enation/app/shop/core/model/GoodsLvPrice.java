package com.enation.app.shop.core.model;

public class GoodsLvPrice
{
  private Double price;
  private int lvid;
  private int productid;
  private int goodsid;

  public Double getPrice()
  {
    return this.price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
  public int getLvid() {
    return this.lvid;
  }
  public void setLvid(int lvid) {
    this.lvid = lvid;
  }
  public int getProductid() {
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
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.GoodsLvPrice
 * JD-Core Version:    0.6.1
 */