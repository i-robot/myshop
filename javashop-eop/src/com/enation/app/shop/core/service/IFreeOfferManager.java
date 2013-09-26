package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.FreeOffer;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IFreeOfferManager
{
  public abstract FreeOffer get(int paramInt);

  public abstract void saveAdd(FreeOffer paramFreeOffer);

  public abstract void update(FreeOffer paramFreeOffer);

  public abstract void delete(String paramString);

  public abstract void revert(String paramString);

  public abstract void clean(String paramString);

  public abstract Page list(int paramInt1, int paramInt2);

  public abstract Page list(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract Page pageTrash(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List getOrderGift(int paramInt);

  public abstract List list(Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IFreeOfferManager
 * JD-Core Version:    0.6.1
 */