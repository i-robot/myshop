package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Logi;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface ILogiManager
{
  public abstract void saveAdd(Logi paramLogi);

  public abstract void saveEdit(Logi paramLogi);

  public abstract Page pageLogi(String paramString, Integer paramInteger1, Integer paramInteger2);

  public abstract List list();

  public abstract void delete(String paramString);

  public abstract Logi getLogiById(Integer paramInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.ILogiManager
 * JD-Core Version:    0.6.1
 */