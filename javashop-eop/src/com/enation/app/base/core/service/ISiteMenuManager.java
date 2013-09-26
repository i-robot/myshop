package com.enation.app.base.core.service;

import com.enation.app.base.core.model.SiteMenu;
import java.util.List;

public abstract interface ISiteMenuManager
{
  public abstract void add(SiteMenu paramSiteMenu);

  public abstract SiteMenu get(Integer paramInteger);

  public abstract void edit(SiteMenu paramSiteMenu);

  public abstract void delete(Integer paramInteger);

  public abstract List<SiteMenu> list(Integer paramInteger);

  public abstract void updateSort(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.ISiteMenuManager
 * JD-Core Version:    0.6.1
 */