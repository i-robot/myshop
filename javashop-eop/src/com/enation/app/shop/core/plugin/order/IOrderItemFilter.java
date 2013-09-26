package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.OrderItem;
import java.util.List;

public abstract interface IOrderItemFilter
{
  public abstract void filter(Integer paramInteger, List<OrderItem> paramList);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IOrderItemFilter
 * JD-Core Version:    0.6.1
 */