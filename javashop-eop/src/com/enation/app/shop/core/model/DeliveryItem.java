package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class DeliveryItem
{
  private Integer item_id;
  private int order_itemid;
  private Integer delivery_id;
  private Integer goods_id;
  private Integer product_id;
  private String sn;
  private String name;
  private Integer num;
  private Integer itemtype;

  @PrimaryKeyField
  public Integer getItem_id()
  {
    return this.item_id;
  }
  public void setItem_id(Integer itemId) {
    this.item_id = itemId;
  }
  public Integer getDelivery_id() {
    return this.delivery_id;
  }
  public void setDelivery_id(Integer deliveryId) {
    this.delivery_id = deliveryId;
  }
  public Integer getProduct_id() {
    return this.product_id;
  }
  public void setProduct_id(Integer productId) {
    this.product_id = productId;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public Integer getGoods_id() {
    return this.goods_id;
  }
  public void setGoods_id(Integer goodsId) {
    this.goods_id = goodsId;
  }
  public Integer getNum() {
    return this.num;
  }
  public void setNum(Integer num) {
    this.num = num;
  }
  public Integer getItemtype() {
    return this.itemtype;
  }
  public void setItemtype(Integer itemtype) {
    this.itemtype = itemtype;
  }
  public int getOrder_itemid() {
    return this.order_itemid;
  }
  public void setOrder_itemid(int order_itemid) {
    this.order_itemid = order_itemid;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.DeliveryItem
 * JD-Core Version:    0.6.1
 */