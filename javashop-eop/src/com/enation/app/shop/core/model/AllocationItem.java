package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;

public class AllocationItem
{
  private Integer allocationid;
  private int itemid;
  private int cat_id;
  private int orderid;
  private int depotid;
  private int goodsid;
  private int productid;
  private int num;
  private String other;
  private int iscmpl;

  public int getIscmpl()
  {
    return this.iscmpl;
  }
  public void setIscmpl(int iscmpl) {
    this.iscmpl = iscmpl;
  }
  public Integer getAllocationid() {
    return this.allocationid;
  }
  public void setAllocationid(Integer allocationid) {
    this.allocationid = allocationid;
  }
  public int getOrderid() {
    return this.orderid;
  }
  public void setOrderid(int orderid) {
    this.orderid = orderid;
  }
  public int getDepotid() {
    return this.depotid;
  }
  public void setDepotid(int depotid) {
    this.depotid = depotid;
  }
  public int getGoodsid() {
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
  public int getNum() {
    return this.num;
  }
  public void setNum(int num) {
    this.num = num;
  }
  @NotDbField
  public int getCat_id() {
    return this.cat_id;
  }
  public void setCat_id(int cat_id) {
    this.cat_id = cat_id;
  }
  public String getOther() {
    return this.other;
  }
  public void setOther(String other) {
    this.other = other;
  }
  public int getItemid() {
    return this.itemid;
  }
  public void setItemid(int itemid) {
    this.itemid = itemid;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.AllocationItem
 * JD-Core Version:    0.6.1
 */