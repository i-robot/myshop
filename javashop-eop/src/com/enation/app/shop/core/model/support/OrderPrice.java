package com.enation.app.shop.core.model.support;

import java.util.HashMap;
import java.util.Map;

public class OrderPrice
{
  private Double originalPrice;
  private Double goodsPrice;
  private Double orderPrice;
  private Double discountPrice;
  private Double shippingPrice;
  private Double protectPrice;
  private Double needPayMoney;
  private Double weight;
  private Integer point;
  private Map<String, Object> discountItem;

  public OrderPrice()
  {
    this.discountItem = new HashMap();
  }

  public Double getOriginalPrice() {
    return this.originalPrice;
  }
  public void setOriginalPrice(Double originalPrice) {
    this.originalPrice = originalPrice;
  }
  public Double getGoodsPrice() {
    return this.goodsPrice;
  }
  public void setGoodsPrice(Double goodsPrice) {
    this.goodsPrice = goodsPrice;
  }
  public Double getOrderPrice() {
    return this.orderPrice;
  }
  public void setOrderPrice(Double orderPrice) {
    this.orderPrice = orderPrice;
  }
  public Double getDiscountPrice() {
    this.discountPrice = Double.valueOf(this.discountPrice == null ? 0.0D : this.discountPrice.doubleValue());
    return this.discountPrice;
  }
  public void setDiscountPrice(Double discountPrice) {
    this.discountPrice = discountPrice;
  }
  public Integer getPoint() {
    return this.point;
  }
  public void setPoint(Integer point) {
    this.point = point;
  }
  public Double getProtectPrice() {
    return this.protectPrice;
  }
  public void setProtectPrice(Double protectPrice) {
    this.protectPrice = protectPrice;
  }
  public Double getWeight() {
    return this.weight;
  }
  public void setWeight(Double weight) {
    this.weight = weight;
  }
  public Double getShippingPrice() {
    return this.shippingPrice;
  }
  public void setShippingPrice(Double shippingPrice) {
    this.shippingPrice = shippingPrice;
  }
  public Map<String, Object> getDiscountItem() {
    return this.discountItem;
  }
  public void setDiscountItem(Map<String, Object> discountItem) {
    this.discountItem = discountItem;
  }

  public Double getNeedPayMoney() {
    return this.needPayMoney;
  }
  public void setNeedPayMoney(Double needPayMoney) {
    this.needPayMoney = needPayMoney;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.OrderPrice
 * JD-Core Version:    0.6.1
 */