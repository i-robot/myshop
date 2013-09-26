package com.enation.app.shop.core.service;

import java.util.List;

public abstract interface IOrderAllocationManager
{
  public abstract List listAllocation(int paramInt);

  public abstract void clean(Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IOrderAllocationManager
 * JD-Core Version:    0.6.1
 */