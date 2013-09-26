package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.ArticleCat;
import java.util.List;

public abstract interface IArticleCatManager
{
  public abstract ArticleCat getById(int paramInt);

  public abstract void saveAdd(ArticleCat paramArticleCat);

  public abstract void update(ArticleCat paramArticleCat);

  public abstract int delete(int paramInt);

  public abstract void saveSort(int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public abstract List listChildById(Integer paramInteger);

  public abstract List listHelp(int paramInt);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IArticleCatManager
 * JD-Core Version:    0.6.1
 */