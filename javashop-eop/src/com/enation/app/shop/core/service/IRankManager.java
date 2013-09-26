package com.enation.app.shop.core.service;

import java.util.List;
import java.util.Map;

public abstract interface IRankManager
{
  public abstract List rank_goods(int paramInt1, int paramInt2, String paramString1, String paramString2);

  public abstract List rank_member(int paramInt1, int paramInt2, String paramString1, String paramString2);

  public abstract List rank_buy(int paramInt1, int paramInt2, String paramString);

  public abstract Map rank_all();
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IRankManager
 * JD-Core Version:    0.6.1
 */