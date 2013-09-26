package com.enation.app.shop.core.model.support;

import java.util.List;

public class CartDTO
{
  private List itemList;
  private List ruleList;
  private Double orderPrice;

  public List getItemList()
  {
    return this.itemList;
  }
  public void setItemList(List itemList) {
    this.itemList = itemList;
  }
  public Double getOrderPrice() {
    return this.orderPrice;
  }
  public void setOrderPrice(Double orderPrice) {
    this.orderPrice = orderPrice;
  }
  public List getRuleList() {
    return this.ruleList;
  }
  public void setRuleList(List ruleList) {
    this.ruleList = ruleList;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.CartDTO
 * JD-Core Version:    0.6.1
 */