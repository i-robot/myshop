package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;

public abstract interface IOrderCanelEvent
{
  public abstract void canel(Order paramOrder);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IOrderCanelEvent
 * JD-Core Version:    0.6.1
 */