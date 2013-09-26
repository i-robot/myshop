package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;

public abstract interface IOrderConfirmPayEvent
{
  public abstract void confirmPay(Order paramOrder);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IOrderConfirmPayEvent
 * JD-Core Version:    0.6.1
 */