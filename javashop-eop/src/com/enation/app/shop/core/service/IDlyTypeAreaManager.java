package com.enation.app.shop.core.service;

import java.util.Map;

public abstract interface IDlyTypeAreaManager
{
  public abstract Map listAllByRegion(String paramString);

  public abstract int queryByrdgion(String paramString);

  public abstract Map queryOtherRegions(Integer paramInteger, String paramString);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IDlyTypeAreaManager
 * JD-Core Version:    0.6.1
 */