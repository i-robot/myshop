package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IGoodsSearchManager
{
  public abstract Page search(int paramInt1, int paramInt2, Map<String, String> paramMap);

  public abstract List[] getPropListByCat(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IGoodsSearchManager
 * JD-Core Version:    0.6.1
 */