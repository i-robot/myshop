package com.enation.eop.resource;

import com.enation.eop.resource.model.Menu;
import java.util.List;

public abstract interface IMenuManager
{
  public abstract Integer add(Menu paramMenu);

  public abstract void edit(Menu paramMenu);

  public abstract List<Menu> getMenuList();

  public abstract Menu get(Integer paramInteger);

  public abstract Menu get(String paramString);

  public abstract List<Menu> getMenuTree(Integer paramInteger);

  public abstract void delete(Integer paramInteger)
    throws RuntimeException;

  public abstract void delete(String paramString);

  public abstract void updateSort(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract void clean();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IMenuManager
 * JD-Core Version:    0.6.1
 */