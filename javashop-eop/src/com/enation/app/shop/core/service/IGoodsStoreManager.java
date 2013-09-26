package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.WarnNum;
import java.util.List;
import java.util.Map;

public abstract interface IGoodsStoreManager
{
  public abstract List<Map> listProductStore(Integer paramInteger);

  public abstract List<Map> ListProductDepotStore(Integer paramInteger1, Integer paramInteger2);

  public abstract List<Map> listProductAllo(Integer paramInteger1, Integer paramInteger2);

  public abstract String getStoreHtml(Integer paramInteger);

  public abstract String getWarnHtml(Integer paramInteger);

  public abstract void saveWarn(int paramInt);

  public abstract String getStockHtml(Integer paramInteger);

  public abstract String getShipHtml(Integer paramInteger);

  public abstract void saveStore(int paramInt);

  public abstract void saveStock(int paramInt);

  public abstract void saveShip(int paramInt);

  public abstract void saveCmpl(int paramInt);

  public abstract List<Map> getDegreeDepotStore(int paramInt1, int paramInt2);

  public abstract List<WarnNum> listWarns(Integer paramInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IGoodsStoreManager
 * JD-Core Version:    0.6.1
 */