package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.support.CartItem;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IAfterOrderCreateEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void onAfterOrderCreate(Order paramOrder, List<CartItem> paramList, String paramString);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IAfterOrderCreateEvent
 * JD-Core Version:    0.6.1
 */