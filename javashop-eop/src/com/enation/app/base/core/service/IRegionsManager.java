package com.enation.app.base.core.service;

import com.enation.app.base.core.model.Regions;
import java.util.List;

public abstract interface IRegionsManager
{
  public abstract List listProvince();

  public abstract List listCity(int paramInt);

  public abstract List listRegion(int paramInt);

  public abstract List listChildren(Integer paramInteger);

  public abstract List listChildren(String paramString);

  public abstract String getChildrenJson(Integer paramInteger);

  public abstract Regions get(int paramInt);

  public abstract Regions getByName(String paramString);

  public abstract void add(Regions paramRegions);

  public abstract void delete(int paramInt);

  public abstract void update(Regions paramRegions);

  public abstract void reset();

  public abstract Regions[] get(String paramString);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.IRegionsManager
 * JD-Core Version:    0.6.1
 */