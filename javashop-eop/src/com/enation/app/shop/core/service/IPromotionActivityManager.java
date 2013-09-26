package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.PromotionActivity;
import com.enation.framework.database.Page;

public abstract interface IPromotionActivityManager
{
  public abstract void add(PromotionActivity paramPromotionActivity);

  public abstract PromotionActivity get(Integer paramInteger);

  public abstract Page list(int paramInt1, int paramInt2);

  public abstract void edit(PromotionActivity paramPromotionActivity);

  public abstract void delete(Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IPromotionActivityManager
 * JD-Core Version:    0.6.1
 */