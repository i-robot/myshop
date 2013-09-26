package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.model.MemberLv;
import com.enation.app.base.core.service.ISettingService;
import com.enation.app.shop.core.model.FreezePoint;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.OrderLog;
import com.enation.app.shop.core.model.PointHistory;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IMemberPointManger;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.app.shop.core.service.IPointHistoryManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MemberPointManger extends BaseSupport
  implements IMemberPointManger
{
  private IPointHistoryManager pointHistoryManager;
  private IMemberManager memberManager;
  private IMemberLvManager memberLvManager;
  private ISettingService settingService;
  private IOrderManager orderManager;

  public void thaw(FreezePoint fp, boolean ismanual)
  {
    String reson = "";
    if ("register_link".equals(fp.getType())) {
      reson = "推荐会员购买商品完成，积分解冻";
    }

    if ("buygoods".equals(fp.getType())) {
      if (ismanual)
        reson = "购买商品,用户提前解冻积分";
      else {
        reson = "购买商品满15天积分解冻";
      }

    }

    if ("onlinepay".equals(fp.getType())) {
      if (ismanual)
        reson = "在线支付购买商品,用户提前解冻积分";
      else {
        reson = "在线支付购买商品满15天积分解冻";
      }
    }

    add(fp.getMemberid(), fp.getPoint(), reson, fp.getOrderid(), fp.getMp());
    this.baseDaoSupport.execute("delete from freeze_point where id=?", new Object[] { Integer.valueOf(fp.getId()) });

    if ("register_link".equals(fp.getType()))
    {
      this.baseDaoSupport.execute("update member set recommend_point_state=1 where member_id=?", new Object[] { fp.getChildid() });
    }
  }

  public void thaw(Integer orderId)
  {
    Order order = this.orderManager.get(orderId);
    if (order == null) {
      throw new RuntimeException("对不起，此订单不存在！");
    }
    if ((order.getStatus() == null) || (order.getStatus().intValue() != 6)) {
      throw new RuntimeException("对不起，此订单不能解冻！");
    }
    IUserService userService = UserServiceFactory.getUserService();
    if (order.getMember_id().intValue() != userService.getCurrentMember().getMember_id().intValue()) {
      throw new RuntimeException("对不起，您没有权限进行此项操作！");
    }

    List<FreezePoint> list = listByOrderId(orderId);
    for (FreezePoint fp : list)
    {
      if (fp.getOrder_status() == 6) {
        thaw(fp, true);
      }
    }

    OrderLog orderLog = new OrderLog();
    orderLog.setMessage("用户[" + userService.getCurrentMember().getUname() + "]解冻订单[" + orderId + "]积分，并将订单置为完成状态");
    orderLog.setOp_id(Integer.valueOf(0));
    orderLog.setOp_name("用户提前解冻积分");
    orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
    orderLog.setOrder_id(orderId);
    this.baseDaoSupport.insert("order_log", orderLog);
    long unix_timestamp = DateUtil.getDatelineLong();
    String sql = "update order set status =7,complete_time=" + unix_timestamp + " where order_id =" + orderId;

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  private List<FreezePoint> listByOrderId(Integer orderId)
  {
    String sql = "select fp.*,o.status as order_status from " + getTableName("freeze_point") + "  fp inner join " + getTableName("order") + " o on fp.orderid= o.order_id  where o.order_id=?";

    return this.daoSupport.queryForList(sql, FreezePoint.class, new Object[] { orderId });
  }

  public List<FreezePoint> listByBeforeDay(int beforeDayNum)
  {
    int f = beforeDayNum * 24 * 60 * 60;

    int now = DateUtil.getDateline();
    String sql = "select fp.*,o.status as order_status from " + getTableName("freeze_point") + "  fp inner join " + getTableName("order") + " o on fp.orderid= o.order_id  where  " + now + "-dateline>=" + f;

    return this.daoSupport.queryForList(sql, FreezePoint.class, new Object[0]);
  }

  public void addFreezePoint(FreezePoint freezePoint, String memberName)
  {
    if (freezePoint == null) throw new IllegalArgumentException("param freezePoint is NULL");
    if (freezePoint.getMemberid() == 0) throw new IllegalArgumentException("param freezePoint.memberid is zero");

    String reson = "";
    if ("register_link".equals(freezePoint.getType()))
    {
      reson = "register_link";
    }

    if ("buygoods".equals(freezePoint.getType()))
    {
      reson = "buygoods";
    }

    if ("onlinepay".equals(freezePoint.getType()))
    {
      reson = "onlinepay";
    }

    freezePoint.setDateline(DateUtil.getDateline());
    this.baseDaoSupport.insert("freeze_point", freezePoint);
  }

  public int getFreezeMpByMemberId(int memberid)
  {
    return this.baseDaoSupport.queryForInt("SELECT SUM(mp) FROM freeze_point WHERE memberid=?", new Object[] { Integer.valueOf(memberid) });
  }

  public int getFreezePointByMemberId(int memberid) {
    return this.baseDaoSupport.queryForInt("SELECT SUM(point) FROM freeze_point WHERE memberid=?", new Object[] { Integer.valueOf(memberid) });
  }

  public boolean checkIsOpen(String itemname) {
    String value = this.settingService.getSetting("point", itemname);

    return "1".equals(value);
  }

  public int getItemPoint(String itemname)
  {
    String value = this.settingService.getSetting("point", itemname);
    if (StringUtil.isEmpty(value)) {
      return 0;
    }
    return Integer.valueOf(value).intValue();
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void add(int memberid, int point, String itemname, Integer relatedId, int mp)
  {
    PointHistory pointHistory = new PointHistory();
    pointHistory.setMember_id(memberid);
    pointHistory.setOperator("member");
    pointHistory.setPoint(point);
    pointHistory.setReason(itemname);
    pointHistory.setType(1);
    pointHistory.setTime(Long.valueOf(System.currentTimeMillis()));
    pointHistory.setRelated_id(relatedId);
    pointHistory.setMp(Integer.valueOf(mp));

    this.pointHistoryManager.addPointHistory(pointHistory);

    Member member = this.memberManager.get(Integer.valueOf(memberid));
    if (member == null) {
      this.logger.debug("获取用户失败 memberid is " + memberid);
      System.out.println("获取用户失败memberid is" + memberid);
      this.baseDaoSupport.execute("delete from freeze_point where memberid=?", new Object[] { Integer.valueOf(memberid) });
    } else {
      Integer memberpoint = member.getPoint();

      this.baseDaoSupport.execute("update member set point=point+?,mp=mp+? where member_id=?", new Object[] { Integer.valueOf(point), Integer.valueOf(mp), Integer.valueOf(memberid) });

      if (memberpoint != null) {
        MemberLv lv = this.memberLvManager.getByPoint(memberpoint.intValue() + point);
        if ((lv != null) && (
          (member.getLv_id() == null) || (lv.getLv_id().intValue() > member.getLv_id().intValue())))
        {
          this.memberManager.updateLv(memberid, lv.getLv_id().intValue());
        }
      }
    }
  }

  public void deleteByOrderId(Integer orderId)
  {
    String sql = "delete from freeze_point where orderid=" + orderId;
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public IPointHistoryManager getPointHistoryManager()
  {
    return this.pointHistoryManager;
  }

  public void setPointHistoryManager(IPointHistoryManager pointHistoryManager)
  {
    this.pointHistoryManager = pointHistoryManager;
  }

  public ISettingService getSettingService()
  {
    return this.settingService;
  }

  public void setSettingService(ISettingService settingService)
  {
    this.settingService = settingService;
  }

  public IMemberManager getMemberManager()
  {
    return this.memberManager;
  }

  public void setMemberManager(IMemberManager memberManager)
  {
    this.memberManager = memberManager;
  }

  public IMemberLvManager getMemberLvManager()
  {
    return this.memberLvManager;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager)
  {
    this.memberLvManager = memberLvManager;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void useMarketPoint(int memberid, int point, String reson, Integer relatedId)
  {
    PointHistory pointHistory = new PointHistory();
    pointHistory.setMember_id(memberid);
    pointHistory.setOperator("member");
    pointHistory.setPoint(point);
    pointHistory.setReason(reson);
    pointHistory.setType(0);
    pointHistory.setPoint_type(1);
    pointHistory.setTime(Long.valueOf(System.currentTimeMillis()));
    pointHistory.setRelated_id(relatedId);

    this.pointHistoryManager.addPointHistory(pointHistory);
    this.baseDaoSupport.execute("update member set mp=mp-? where member_id=?", new Object[] { Integer.valueOf(point), Integer.valueOf(memberid) });
  }

  public Double pointToPrice(int point)
  {
    return Double.valueOf(point);
  }

  public int priceToPoint(Double price)
  {
    if (price == null) return 0;
    return price.intValue();
  }

  public IOrderManager getOrderManager() {
    return this.orderManager;
  }

  public void setOrderManager(IOrderManager orderManager) {
    this.orderManager = orderManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberPointManger
 * JD-Core Version:    0.6.1
 */