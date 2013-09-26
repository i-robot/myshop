package com.enation.eop.resource;

import com.enation.app.base.core.model.DataLog;
import com.enation.framework.database.Page;

public abstract interface IDataLogManager
{
  public abstract void add(DataLog paramDataLog);

  public abstract Page list(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract void delete(Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IDataLogManager
 * JD-Core Version:    0.6.1
 */