package com.enation.app.shop.core.model.support;

public class DiscountPrice
{
  private Double orderPrice;
  private Double shipFee;
  private Integer point;

  public Double getOrderPrice()
  {
    return this.orderPrice;
  }
  public void setOrderPrice(Double orderPrice) {
    this.orderPrice = orderPrice;
  }
  public Double getShipFee() {
    return this.shipFee;
  }
  public void setShipFee(Double shipFee) {
    this.shipFee = shipFee;
  }
  public Integer getPoint() {
    return this.point;
  }
  public void setPoint(Integer point) {
    this.point = point;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.DiscountPrice
 * JD-Core Version:    0.6.1
 */