package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;

public abstract interface IOrderPayEvent
{
  public abstract void pay(Order paramOrder, boolean paramBoolean);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IOrderPayEvent
 * JD-Core Version:    0.6.1
 */