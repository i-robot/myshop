package com.enation.app.base.core.service;

import com.enation.app.base.core.model.MultiSite;
import java.util.List;

public abstract interface IMultiSiteManager
{
  public abstract void open(String paramString);

  public abstract void close();

  public abstract void add(MultiSite paramMultiSite);

  public abstract void update(MultiSite paramMultiSite);

  public abstract void delete(int paramInt);

  public abstract List list();

  public abstract MultiSite get(int paramInt);

  public abstract MultiSite get(String paramString);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.IMultiSiteManager
 * JD-Core Version:    0.6.1
 */