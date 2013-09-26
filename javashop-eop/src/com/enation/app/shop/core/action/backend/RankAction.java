package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.service.IRankManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.DateUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RankAction extends WWAction
{
  private static final int PAGESIZE = 20;
  private IRankManager rankManager;
  private String order;
  private String beginTime;
  private String endTime;
  private List list;
  private Map rankall;

  public String execute()
  {
    String condition = "";
    if ((this.beginTime != null) && (!this.beginTime.equals(""))) {
      condition = condition + " and orders.create_time > " + DateUtil.toDate(this.beginTime, "yyyy-MM-dd").getTime();
    }
    if ((this.endTime != null) && (!this.endTime.equals(""))) {
      condition = condition + " and orders.create_time <" + DateUtil.toDate(this.endTime, "yyyy-MM-dd").getTime();
    }
    this.list = this.rankManager.rank_goods(1, 20, condition, this.order);
    return "success";
  }

  public String rankmember() {
    String condition = "";
    if ((this.beginTime != null) && (!this.beginTime.equals(""))) {
      condition = condition + " and orders.create_time > " + DateUtil.toDate(this.beginTime, "yyyy-MM-dd").getTime();
    }
    if ((this.endTime != null) && (!this.endTime.equals(""))) {
      condition = condition + " and orders.create_time <" + DateUtil.toDate(this.endTime, "yyyy-MM-dd").getTime();
    }
    this.list = this.rankManager.rank_member(1, 20, condition, this.order);
    return "rankmember";
  }

  public String rankbuy() {
    this.list = this.rankManager.rank_buy(1, 20, this.order);
    return "rankbuy";
  }

  public String rankall() {
    this.rankall = this.rankManager.rank_all();
    return "rankall";
  }

  public IRankManager getRankManager() {
    return this.rankManager;
  }

  public void setRankManager(IRankManager rankManager) {
    this.rankManager = rankManager;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public String getBeginTime() {
    return this.beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getEndTime() {
    return this.endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public List getList() {
    return this.list;
  }

  public void setList(List list) {
    this.list = list;
  }

  public Map getRankall() {
    return this.rankall;
  }

  public void setRankall(Map rankall) {
    this.rankall = rankall;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.RankAction
 * JD-Core Version:    0.6.1
 */