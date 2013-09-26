package com.enation.app.shop.core.model;

import com.enation.framework.database.DynamicField;
import com.enation.framework.database.PrimaryKeyField;

public class Cart extends DynamicField
{
  private Integer cart_id;
  private Integer goods_id;
  private Integer product_id;
  private Integer num;
  private Double weight;
  private String session_id;
  private Integer itemtype;
  private String name;
  private Double price;
  private String addon;

  @PrimaryKeyField
  public Integer getCart_id()
  {
    return this.cart_id;
  }
  public void setCart_id(Integer cartId) {
    this.cart_id = cartId;
  }
  public Integer getProduct_id() {
    return this.product_id;
  }
  public void setProduct_id(Integer productId) {
    this.product_id = productId;
  }
  public Integer getNum() {
    return this.num;
  }
  public void setNum(Integer num) {
    this.num = num;
  }
  public String getSession_id() {
    return this.session_id;
  }
  public void setSession_id(String sessionId) {
    this.session_id = sessionId;
  }
  public Integer getItemtype() {
    return this.itemtype;
  }
  public void setItemtype(Integer itemtype) {
    this.itemtype = itemtype;
  }
  public Double getWeight() {
    return this.weight;
  }
  public void setWeight(Double weight) {
    this.weight = weight;
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
  public String getAddon() {
    return this.addon;
  }
  public void setAddon(String addon) {
    this.addon = addon;
  }
  public Integer getGoods_id() {
    return this.goods_id;
  }
  public void setGoods_id(Integer goods_id) {
    this.goods_id = goods_id;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Cart
 * JD-Core Version:    0.6.1
 */