package com.enation.app.shop.core.model;

public class DepotStore
{
  private int depotid;
  private String name;
  private int goodsid;
  private int productid;
  private int store;

  public int getGoodsid()
  {
    return this.goodsid;
  }
  public void setGoodsid(int goodsid) {
    this.goodsid = goodsid;
  }
  public int getProductid() {
    return this.productid;
  }
  public void setProductid(int productid) {
    this.productid = productid;
  }
  public int getStore() {
    return this.store;
  }
  public void setStore(int store) {
    this.store = store;
  }
  public int getDepotid() {
    return this.depotid;
  }
  public void setDepotid(int depotid) {
    this.depotid = depotid;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.DepotStore
 * JD-Core Version:    0.6.1
 */