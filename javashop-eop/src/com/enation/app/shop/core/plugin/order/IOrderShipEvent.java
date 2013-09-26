package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.AllocationItem;
import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import com.enation.app.shop.core.model.Order;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IOrderShipEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void itemShip(Order paramOrder, DeliveryItem paramDeliveryItem, AllocationItem paramAllocationItem);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void ship(Delivery paramDelivery, List<DeliveryItem> paramList);

  public abstract boolean canBeExecute(int paramInt);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IOrderShipEvent
 * JD-Core Version:    0.6.1
 */