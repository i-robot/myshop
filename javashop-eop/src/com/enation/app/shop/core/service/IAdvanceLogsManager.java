package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.AdvanceLogs;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IAdvanceLogsManager
{
  public abstract Page pageAdvanceLogs(int paramInt1, int paramInt2);

  public abstract void add(AdvanceLogs paramAdvanceLogs);

  public abstract List listAdvanceLogsByMemberId(int paramInt);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IAdvanceLogsManager
 * JD-Core Version:    0.6.1
 */