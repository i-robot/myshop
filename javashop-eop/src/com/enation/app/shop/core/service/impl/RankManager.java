package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.service.IRankManager;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import java.util.List;
import java.util.Map;

public class RankManager extends BaseSupport
  implements IRankManager
{
  public List rank_goods(int page, int pageSize, String condition, String sort)
  {
    String sql = "select items.name name, sum(items.price * items.num) amount, sum(items.num) num from " + getTableName("order") + " orders , " + getTableName("order_items") + " items where items.order_id = orders.order_id";

    if ((condition == null) || (condition.equals("")))
      condition = " and orders.status = 3";
    else {
      condition = condition + " and orders.status = 3";
    }
    sql = sql + condition;
    sql = sql + " group by name,num";
    sort = sort == null ? "num desc" : sort;
    sql = sql + " order by " + sort;

    List list = this.daoSupport.queryForListPage(sql, page, pageSize, new Object[0]);
    return list;
  }

  public List rank_member(int page, int pageSize, String condition, String sort)
  {
    List list = null;
    String sql = "select member.uname uname, member.name name, sum(items.price * items.num) amount, sum(items.num) num from " + getTableName("order") + " orders , " + getTableName("order_items") + " items, " + getTableName("member") + " member where items.order_id = orders.order_id and member.member_id = orders.member_id";
    if ((condition == null) || (condition.equals("")))
      condition = " and orders.status = 3";
    else {
      condition = condition + " and orders.status = 3";
    }
    sql = sql + condition;
    sql = sql + " group by member.uname, member.name";
    sort = sort == null ? "sum(items.num) desc" : sort;
    sql = sql + " order by " + sort;
    if ("3".equals(EopSetting.DBTYPE))
      list = this.daoSupport.queryForList("select * from (select top " + pageSize + " ROW_NUMBER() Over(order by sum(items.num) desc) as rowNum," + sql.substring(6) + ") tb", new Object[0]);
    else {
      list = this.daoSupport.queryForListPage(sql, page, pageSize, new Object[0]);
    }
    return list;
  }

  public List rank_buy(int page, int pageSize, String sort)
  {
    String mysql = "select name, view_count, buy_count, FORMAT(buy_count / (case view_count when null then 1 when 0 then 1 else view_count end)*100, 2) per from goods where disabled = 0 and buy_count > 0";
    String sqlserver = "select name, view_count, buy_count, Round(buy_count / (case view_count when null then 1 when 0 then 1 else view_count end)*100, 2) per from goods where disabled = 0 and buy_count > 0";
    String groupby = " group by name,view_count,buy_count";
    mysql = mysql + groupby;
    sqlserver = sqlserver + groupby;
    sort = sort == null ? "view_count desc" : sort;
    sort = " order by " + sort;
    mysql = mysql + sort;
    sqlserver = sqlserver + sort;
    List list = null;
    if ("1".equals(EopSetting.DBTYPE))
      list = this.baseDaoSupport.queryForListPage(mysql, page, pageSize, new Object[0]);
    else if ("3".equals(EopSetting.DBTYPE))
      list = this.baseDaoSupport.queryForListPage(sqlserver, page, pageSize, new Object[0]);
    return list;
  }

  public Map rank_all()
  {
    Map map = this.baseDaoSupport.queryForMap("select sum(order_amount) amount, count(order_id) num from order where status = 3", new Object[0]);
    int view_count = this.baseDaoSupport.queryForInt("select sum(view_count) view_count from goods where disabled = 0", new Object[0]);
    map.put("view_count", Integer.valueOf(view_count));
    int member_count = this.baseDaoSupport.queryForInt("select count(member_id) member_count from member", new Object[0]);
    map.put("member_count", Integer.valueOf(member_count));
    int buy_member_count = this.baseDaoSupport.queryForInt("select count(distinct member_id) buy_member_count from order where status = 3 and member_id > 0", new Object[0]);
    map.put("buy_member_count", Integer.valueOf(buy_member_count));
    return map;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.RankManager
 * JD-Core Version:    0.6.1
 */