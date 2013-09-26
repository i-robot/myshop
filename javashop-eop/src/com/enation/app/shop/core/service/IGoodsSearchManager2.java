package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Cat;
import com.enation.framework.database.Page;
import java.util.Map;

public abstract interface IGoodsSearchManager2
{
  public abstract Page search(int paramInt1, int paramInt2, String paramString);

  public abstract Map<String, Object> getSelector(String paramString);

  public abstract void putParams(Map<String, Object> paramMap, String paramString);

  public abstract Cat getCat(String paramString);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IGoodsSearchManager2
 * JD-Core Version:    0.6.1
 */