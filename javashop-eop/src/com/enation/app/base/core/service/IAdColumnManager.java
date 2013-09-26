package com.enation.app.base.core.service;

import com.enation.app.base.core.model.AdColumn;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IAdColumnManager
{
  public abstract void updateAdvc(AdColumn paramAdColumn);

  public abstract AdColumn getADcolumnDetail(Long paramLong);

  public abstract void addAdvc(AdColumn paramAdColumn);

  public abstract void delAdcs(String paramString);

  public abstract Page pageAdvPos(int paramInt1, int paramInt2);

  public abstract List listAllAdvPos();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.IAdColumnManager
 * JD-Core Version:    0.6.1
 */