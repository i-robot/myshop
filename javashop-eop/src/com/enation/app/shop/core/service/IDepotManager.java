package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Depot;
import java.util.List;

public abstract interface IDepotManager
{
  public abstract void add(Depot paramDepot);

  public abstract void update(Depot paramDepot);

  public abstract Depot get(int paramInt);

  public abstract void delete(int paramInt);

  public abstract List<Depot> list();
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IDepotManager
 * JD-Core Version:    0.6.1
 */