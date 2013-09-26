package com.enation.app.base.core.service.auth;

import com.enation.app.base.core.model.AuthAction;
import java.util.List;

public abstract interface IAuthActionManager
{
  public abstract AuthAction get(int paramInt);

  public abstract List<AuthAction> list();

  public abstract int add(AuthAction paramAuthAction);

  public abstract void edit(AuthAction paramAuthAction);

  public abstract void delete(int paramInt);

  public abstract void addMenu(int paramInt, Integer[] paramArrayOfInteger);

  public abstract void deleteMenu(int paramInt, Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.auth.IAuthActionManager
 * JD-Core Version:    0.6.1
 */