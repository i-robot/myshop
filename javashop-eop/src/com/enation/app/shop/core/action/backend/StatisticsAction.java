package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.statistics.DayAmount;
import com.enation.app.shop.core.model.statistics.MonthAmount;
import com.enation.app.shop.core.service.IStatisticsManager;
import com.enation.framework.action.WWAction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StatisticsAction extends WWAction
{
  private List<MonthAmount> month_AmountList;
  private List<DayAmount> day_AmountList;
  private IStatisticsManager statisticsManager;
  private String year;
  private String month;
  private int daycount;
  private List<Map> orderStatList;

  public String monthamount()
  {
    if ((this.year == null) || (this.year.equals(""))) {
      Date date = new Date();
      SimpleDateFormat sdfyear = new SimpleDateFormat("yyyy");
      this.year = sdfyear.format(date);
      SimpleDateFormat sdfmonth = new SimpleDateFormat("MM");
      this.month = sdfmonth.format(date);
    }
    this.month_AmountList = this.statisticsManager.statisticsMonth_Amount(this.year + "-" + this.month);
    this.day_AmountList = this.statisticsManager.statisticsDay_Amount(this.year + "-" + this.month);
    this.daycount = this.day_AmountList.size();
    return "monthamount";
  }

  public String statByPayment()
  {
    this.orderStatList = this.statisticsManager.orderStatByPayment();
    return "payment";
  }

  public String statByShip() {
    this.orderStatList = this.statisticsManager.orderStatByShip();
    return "ship";
  }

  public List<MonthAmount> getMonth_AmountList()
  {
    return this.month_AmountList;
  }

  public void setMonth_AmountList(List<MonthAmount> monthAmountList)
  {
    this.month_AmountList = monthAmountList;
  }

  public List<DayAmount> getDay_AmountList() {
    return this.day_AmountList;
  }

  public void setDay_AmountList(List<DayAmount> dayAmountList) {
    this.day_AmountList = dayAmountList;
  }

  public IStatisticsManager getStatisticsManager() {
    return this.statisticsManager;
  }

  public void setStatisticsManager(IStatisticsManager statisticsManager) {
    this.statisticsManager = statisticsManager;
  }

  public String getYear() {
    return this.year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getMonth() {
    return this.month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public int getDaycount() {
    return this.daycount;
  }

  public void setDaycount(int daycount) {
    this.daycount = daycount;
  }

  public List<Map> getOrderStatList()
  {
    return this.orderStatList;
  }

  public void setOrderStatList(List<Map> orderStatList)
  {
    this.orderStatList = orderStatList;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.StatisticsAction
 * JD-Core Version:    0.6.1
 */