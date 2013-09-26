package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.OrderMeta;
import java.util.List;

public abstract interface IOrderMetaManager
{
  public abstract void add(OrderMeta paramOrderMeta);

  public abstract List<OrderMeta> list(int paramInt);

  public abstract OrderMeta get(int paramInt, String paramString);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IOrderMetaManager
 * JD-Core Version:    0.6.1
 */