package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.PrintTmpl;
import java.util.List;

public abstract interface IPrintTmplManager
{
  public abstract List list();

  public abstract List trash();

  public abstract List listCanUse();

  public abstract void add(PrintTmpl paramPrintTmpl);

  public abstract void edit(PrintTmpl paramPrintTmpl);

  public abstract PrintTmpl get(int paramInt);

  public abstract void delete(Integer[] paramArrayOfInteger);

  public abstract void revert(Integer[] paramArrayOfInteger);

  public abstract void clean(Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IPrintTmplManager
 * JD-Core Version:    0.6.1
 */