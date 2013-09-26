package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Product;
import com.enation.app.shop.core.model.Specification;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IProductManager
{
  public abstract void add(List<Product> paramList);

  public abstract Product get(Integer paramInteger);

  public abstract Product getByGoodsId(Integer paramInteger);

  public abstract List<String> listSpecName(int paramInt);

  public abstract List<Specification> listSpecs(Integer paramInteger);

  public abstract Page list(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3);

  public abstract List list(Integer[] paramArrayOfInteger);

  public abstract List<Product> list(Integer paramInteger);

  public abstract void delete(Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IProductManager
 * JD-Core Version:    0.6.1
 */