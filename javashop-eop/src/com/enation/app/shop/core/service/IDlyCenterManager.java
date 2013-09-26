package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.DlyCenter;
import java.util.List;

public abstract interface IDlyCenterManager
{
  public abstract List<DlyCenter> list();

  public abstract DlyCenter get(Integer paramInteger);

  public abstract void add(DlyCenter paramDlyCenter);

  public abstract void edit(DlyCenter paramDlyCenter);

  public abstract void delete(Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IDlyCenterManager
 * JD-Core Version:    0.6.1
 */