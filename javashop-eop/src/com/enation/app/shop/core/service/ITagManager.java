package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Tag;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface ITagManager
{
  public abstract boolean checkname(String paramString, Integer paramInteger);

  public abstract boolean checkJoinGoods(Integer[] paramArrayOfInteger);

  public abstract Tag getById(Integer paramInteger);

  public abstract void add(Tag paramTag);

  public abstract void update(Tag paramTag);

  public abstract void delete(Integer[] paramArrayOfInteger);

  public abstract Page list(int paramInt1, int paramInt2);

  public abstract List<Tag> list();

  public abstract List<Integer> list(Integer paramInteger);

  public abstract void saveRels(Integer paramInteger, Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.ITagManager
 * JD-Core Version:    0.6.1
 */