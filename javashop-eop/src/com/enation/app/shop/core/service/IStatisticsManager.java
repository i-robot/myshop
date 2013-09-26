package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.statistics.DayAmount;
import com.enation.app.shop.core.model.statistics.MonthAmount;
import java.util.List;
import java.util.Map;

public abstract interface IStatisticsManager
{
  public abstract List<MonthAmount> statisticsMonth_Amount();

  public abstract List<MonthAmount> statisticsMonth_Amount(String paramString);

  public abstract List<DayAmount> statisticsDay_Amount();

  public abstract List<DayAmount> statisticsDay_Amount(String paramString);

  public abstract List<Map> orderStatByPayment();

  public abstract List<Map> orderStatByShip();
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IStatisticsManager
 * JD-Core Version:    0.6.1
 */