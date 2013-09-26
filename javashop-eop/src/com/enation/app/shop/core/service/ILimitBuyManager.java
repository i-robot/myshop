package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.LimitBuy;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface ILimitBuyManager
{
  public abstract void add(LimitBuy paramLimitBuy);

  public abstract void edit(LimitBuy paramLimitBuy);

  public abstract Page list(int paramInt1, int paramInt2);

  public abstract List<LimitBuy> listEnable();

  public abstract List<Map> listEnableGoods();

  public abstract void delete(Integer paramInteger);

  public abstract LimitBuy get(Integer paramInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.ILimitBuyManager
 * JD-Core Version:    0.6.1
 */