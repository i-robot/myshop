package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IOrderReturnsEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void returned(Delivery paramDelivery, List<DeliveryItem> paramList);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IOrderReturnsEvent
 * JD-Core Version:    0.6.1
 */