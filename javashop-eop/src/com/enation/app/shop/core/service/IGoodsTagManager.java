package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;

public abstract interface IGoodsTagManager
{
  public abstract Page getGoodsList(int paramInt1, int paramInt2, int paramInt3);

  public abstract Page getGoodsList(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void addTag(int paramInt1, int paramInt2);

  public abstract void removeTag(int paramInt1, int paramInt2);

  public abstract void updateOrderNum(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer[] paramArrayOfInteger3);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IGoodsTagManager
 * JD-Core Version:    0.6.1
 */