package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.DepotUser;
import java.util.List;

public abstract interface IDepotUserManager
{
  public abstract void add(DepotUser paramDepotUser);

  public abstract void edit(DepotUser paramDepotUser);

  public abstract void delete(int paramInt);

  public abstract DepotUser get(int paramInt);

  public abstract List<DepotUser> listByDepotId(int paramInt);

  public abstract List<DepotUser> listByRoleId(int paramInt);

  public abstract List<DepotUser> listByRoleId(int paramInt1, int paramInt2);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IDepotUserManager
 * JD-Core Version:    0.6.1
 */