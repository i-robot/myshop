package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.PointHistory;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IPointHistoryManager
{
  public abstract Page pagePointHistory(int paramInt1, int paramInt2, int paramInt3);

  public abstract Page pagePointHistory(int paramInt1, int paramInt2);

  public abstract List<PointHistory> listPointHistory(int paramInt1, int paramInt2);

  public abstract Long getConsumePoint(int paramInt);

  public abstract Long getGainedPoint(int paramInt);

  public abstract void addPointHistory(PointHistory paramPointHistory);

  public abstract Page pagePointFreeze(int paramInt1, int paramInt2);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IPointHistoryManager
 * JD-Core Version:    0.6.1
 */