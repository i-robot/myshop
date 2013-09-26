package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Allocation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IOrderAllocationEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void onAllocation(Allocation paramAllocation);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IOrderAllocationEvent
 * JD-Core Version:    0.6.1
 */