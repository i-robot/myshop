package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Brand;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IBrandManager
{
  public abstract boolean checkUsed(String paramString);

  public abstract boolean checkname(String paramString, Integer paramInteger);

  public abstract void add(Brand paramBrand);

  public abstract void update(Brand paramBrand);

  public abstract Page list(String paramString, int paramInt1, int paramInt2);

  public abstract Page listTrash(String paramString, int paramInt1, int paramInt2);

  public abstract void revert(String paramString);

  public abstract void delete(String paramString);

  public abstract void clean(String paramString);

  public abstract List<Brand> list();

  public abstract List<Brand> listByTypeId(Integer paramInteger);

  public abstract List<Brand> listByCatId(Integer paramInteger);

  public abstract List groupByCat();

  public abstract Brand get(Integer paramInteger);

  public abstract Page getGoods(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract List<Map> queryAllTypeNameAndId();

  public abstract Page search(int paramInt1, int paramInt2, String paramString, Integer paramInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IBrandManager
 * JD-Core Version:    0.6.1
 */