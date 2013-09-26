package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IFavoriteManager
{
  public abstract Page list(int paramInt1, int paramInt2);

  public abstract Page list(int paramInt1, int paramInt2, int paramInt3);

  public abstract List list();

  public abstract void delete(int paramInt);

  public abstract void add(Integer paramInteger);

  public abstract int getCount(Integer paramInteger1, Integer paramInteger2);

  public abstract int getCount(Integer paramInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IFavoriteManager
 * JD-Core Version:    0.6.1
 */