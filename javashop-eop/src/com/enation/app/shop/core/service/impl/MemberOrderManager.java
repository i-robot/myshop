package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.service.IMemberOrderManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

public class MemberOrderManager extends BaseSupport
  implements IMemberOrderManager
{
  public Page pageOrders(int pageNo, int pageSize)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();

    String sql = "select * from order where member_id = ? and disabled=0 order by create_time desc";
    Page rpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });
    List list = (List)rpage.getResult();
    return rpage;
  }

  public Page pageOrders(int pageNo, int pageSize, String status, String keyword) {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();

    String sql = "select * from " + getTableName("order") + " where member_id = '" + member.getMember_id() + "' and disabled=0";
    if (!StringUtil.isEmpty(status)) {
      int statusNumber = -999;
      statusNumber = StringUtil.toInt(status);

      if (statusNumber == 0)
        sql = sql + " AND status!=8 AND pay_status=0";
      else {
        sql = sql + " AND status='" + statusNumber + "'";
      }
    }
    if (!StringUtil.isEmpty(keyword)) {
      sql = sql + " AND order_id in (SELECT i.order_id FROM " + getTableName("order_items") + " i LEFT JOIN order o ON i.order_id=o.order_id WHERE o.member_id='" + member.getMember_id() + "' AND i.name like '%" + keyword + "%')";
    }
    sql = sql + " order by create_time desc";

    Page rpage = this.daoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);

    return rpage;
  }

  public Page pageGoods(int pageNo, int pageSize, String keyword) {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();

    String sql = "select * from goods where goods_id in (SELECT i.goods_id FROM es_order_items i LEFT JOIN order o ON i.order_id=o.order_id WHERE o.member_id='" + member.getMember_id() + "' AND o.status in (" + 7 + "," + 6 + " )) ";

    if (!StringUtil.isEmpty(keyword)) {
      sql = sql + " AND (sn='" + keyword + "' OR name like '%" + keyword + "%')";
    }
    sql = sql + " order by goods_id desc";
    Page rpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
    List list = (List)rpage.getResult();
    return rpage;
  }

  public Order getOrder(int order_id)
  {
    Order order = (Order)this.baseDaoSupport.queryForObject("select * from order where order_id = ?", Order.class, new Object[] { Integer.valueOf(order_id) });
    return order;
  }

  public Delivery getOrderDelivery(int order_id)
  {
    Delivery delivery = (Delivery)this.baseDaoSupport.queryForObject("select * from delivery where order_id = ?", Delivery.class, new Object[] { Integer.valueOf(order_id) });
    return delivery;
  }

  public List listOrderLog(int order_id)
  {
    String sql = "select * from order_log where order_id = ?";
    List list = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(order_id) });
    list = list == null ? new ArrayList() : list;
    return list;
  }

  public List listGoodsItems(int order_id)
  {
    String sql = "select * from order_items where order_id = ?";
    List list = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(order_id) });
    list = list == null ? new ArrayList() : list;
    return list;
  }

  public List listGiftItems(int orderid)
  {
    String sql = "select * from order_gift where order_id=?";
    return this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(orderid) });
  }

  public boolean isBuy(int goodsid)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    if (member == null) return false;
    String sql = "select count(0) from " + getTableName("order_items") + " where  order_id in(select order_id from " + getTableName("order") + " where member_id=?) and goods_id =? ";

    int count = this.daoSupport.queryForInt(sql, new Object[] { member.getMember_id(), Integer.valueOf(goodsid) });
    return count > 0;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberOrderManager
 * JD-Core Version:    0.6.1
 */