package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.DlyArea;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IAreaManager
{
  public abstract void saveAdd(String paramString);

  public abstract void saveEdit(Integer paramInteger, String paramString);

  public abstract List getAll();

  public abstract Page pageArea(String paramString, int paramInt1, int paramInt2);

  public abstract void delete(String paramString);

  public abstract DlyArea getDlyAreaById(Integer paramInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IAreaManager
 * JD-Core Version:    0.6.1
 */