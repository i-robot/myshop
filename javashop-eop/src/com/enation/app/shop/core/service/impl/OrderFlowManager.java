package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.service.auth.IAdminUserManager;
import com.enation.app.shop.core.model.Allocation;
import com.enation.app.shop.core.model.AllocationItem;
import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import com.enation.app.shop.core.model.Logi;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.OrderItem;
import com.enation.app.shop.core.model.OrderLog;
import com.enation.app.shop.core.model.PaymentLog;
import com.enation.app.shop.core.model.Product;
import com.enation.app.shop.core.model.RefundLog;
import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
import com.enation.app.shop.core.service.ILogiManager;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IMemberPointManger;
import com.enation.app.shop.core.service.IOrderFlowManager;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.app.shop.core.service.IPointHistoryManager;
import com.enation.app.shop.core.service.IPromotionManager;
import com.enation.eop.resource.model.AdminUser;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.DoubleMapper;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.DateUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class OrderFlowManager extends BaseSupport
  implements IOrderFlowManager
{
  private IOrderManager orderManager;
  private IMemberManager memberManager;
  private IPointHistoryManager pointHistoryManager;
  private IMemberPointManger memberPointManger;
  private OrderPluginBundle orderPluginBundle;
  private ILogiManager logiManager;
  private IAdminUserManager adminUserManager;
  private IPromotionManager promotionManager;

  @Transactional(propagation=Propagation.REQUIRED)
  public void pay(PaymentLog payment, boolean isOnline)
  {
    if (payment == null) throw new IllegalArgumentException("param paymentLog is NULL");
    if (payment.getOrder_sn() == null) throw new IllegalArgumentException("param PaymentLog's order_sn is NULL");
    if (payment.getMoney() == null) throw new IllegalArgumentException("param  PaymentLog's money is NULL");
    Order order = this.orderManager.get(payment.getOrder_sn());

    checkDisabled(order);
    if (order.getPay_status().intValue() == 1) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("订单[" + order.getSn() + "]支付状态为[已经支付]，不能再对其进行支付操作");
      }
      return;
    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("支付订单:" + order.getOrder_id());
    }

    if (isOnline)
      payment.setType(1);
    else {
      payment.setType(2);
    }

    payment.setStatus(Integer.valueOf(0));
    payment.setCreate_time(Long.valueOf(System.currentTimeMillis()));

    this.baseDaoSupport.insert("payment_logs", payment);

    this.baseDaoSupport.execute("update order set status=?,pay_status=?  where order_id=?", new Object[] { Integer.valueOf(1), Integer.valueOf(1), order.getOrder_id() });

    Member member = UserServiceFactory.getUserService().getCurrentMember();
    if (member == null) {
      if ((order.getMember_id() != null) && (order.getMember_id().intValue() > 0))
      {
        log(order.getOrder_id(), "支付订单，金额" + payment.getMoney(), order.getMember_id(), "会员：" + this.memberManager.get(order.getMember_id()).getUname());
      }
      else log(order.getOrder_id(), "支付订单，金额" + payment.getMoney(), null, "游客");
    }
    else {
      log(order.getOrder_id(), "支付订单，金额" + payment.getMoney(), member.getMember_id(), "会员：" + member.getUname());
    }

    this.orderPluginBundle.onPay(order, isOnline);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void refund(RefundLog refund)
  {
    if (refund == null) throw new IllegalArgumentException("param paymentLog is NULL");

    if (refund.getMoney() == null) throw new IllegalArgumentException("param PaymentLog's money is NULL");
    Order order = this.orderManager.get(Integer.valueOf(refund.getOrder_id()));
    checkDisabled(order);
    if (order.getPay_status().intValue() == 3) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("订单[" + order.getSn() + "]支付状态为[已经退款]，不能再对其进行退款操作");
      }
      throw new IllegalStateException("订单[" + order.getSn() + "]支付状态为[已经退款]，不能再对其进行退款操作");
    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("订单:" + order.getOrder_id() + "退款");
    }

    double m = order.getOrder_amount().doubleValue();
    double nm = refund.getMoney().doubleValue();
    double om = order.getPaymoney().doubleValue();

    int payStatus = 0;
    if (nm == om)
      payStatus = 3;
    if (nm < om)
      payStatus = 4;
    if (nm > om) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("退款金额[" + nm + "]超过订单支付金额[" + m + "]");
      }
      throw new RuntimeException("退款金额[" + nm + "]超过订单支付金额[" + om + "]");
    }
    refund.setOrder_sn(order.getSn());
    refund.setCreate_time(Long.valueOf(System.currentTimeMillis()));
    refund.setMember_id(order.getMember_id());
    this.baseDaoSupport.insert("refund_logs", refund);

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("更新订单状态[-1],支付状态[" + payStatus + "]");
    }
    this.baseDaoSupport.execute("update order set status=?,pay_status=?,paymoney=paymoney-? where order_id=?", new Object[] { Integer.valueOf(-1), Integer.valueOf(payStatus), refund.getMoney(), order.getOrder_id() });

    log(order.getOrder_id(), "订单退款，金额" + refund.getMoney());
  }

  private void log(Integer order_id, String message)
  {
    AdminUser adminUser = this.adminUserManager.getCurrentUser();
    OrderLog orderLog = new OrderLog();
    orderLog.setMessage(message);
    orderLog.setOp_id(adminUser.getUserid());
    orderLog.setOp_name(adminUser.getUsername());
    orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
    orderLog.setOrder_id(order_id);
    this.baseDaoSupport.insert("order_log", orderLog);
  }

  private void log(Integer order_id, String message, Integer op_id, String op_name)
  {
    OrderLog orderLog = new OrderLog();
    orderLog.setMessage(message);
    orderLog.setOp_id(op_id);
    orderLog.setOp_name(op_name);
    orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
    orderLog.setOrder_id(order_id);
    this.baseDaoSupport.insert("order_log", orderLog);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void allocation(Allocation allocation)
  {
    List<AllocationItem> itemList = allocation.getItemList();
    int orderid = allocation.getOrderid();

    for (AllocationItem item : itemList) {
      item.setOrderid(orderid);

      this.orderPluginBundle.onAllocationItem(item);

      this.baseDaoSupport.insert("allocation_item", item);
    }

    this.baseDaoSupport.execute("update order set depotid=?,status=?,ship_status=?,allocation_time=? where order_id=?", new Object[] { Integer.valueOf(allocation.getShipDepotId()), Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(DateUtil.getDateline()), Integer.valueOf(allocation.getOrderid()) });

    this.orderPluginBundle.onAllocation(allocation);

    if (this.logger.isDebugEnabled())
      this.logger.debug("订单[" + allocation.getOrderid() + "]配货成功");
  }

  public String getAllocationHtml(int itemid)
  {
    OrderItem item = this.orderManager.getItem(itemid);
    String html = this.orderPluginBundle.getAllocationHtml(item);
    return html;
  }

  public String getAllocationViewHtml(int itemid)
  {
    OrderItem item = this.orderManager.getItem(itemid);
    String html = this.orderPluginBundle.getAllocationViewHtml(item);
    return html;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void shipping(Delivery delivery, List<DeliveryItem> itemList)
  {
    if (delivery == null) throw new IllegalArgumentException("param delivery is NULL");
    if (itemList == null) throw new IllegalArgumentException("param itemList is NULL");
    if (delivery.getOrder_id() == null) throw new IllegalArgumentException("param order id is null");

    if (delivery.getMoney() == null) delivery.setMoney(Double.valueOf(0.0D));
    if (delivery.getProtect_price() == null) delivery.setProtect_price(Double.valueOf(0.0D));

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("订单[" + delivery.getOrder_id() + "]发货");
    }

    Integer orderId = delivery.getOrder_id();
    Order order = this.orderManager.get(orderId);
    delivery.setOrder(order);

    checkDisabled(order);
    if (order.getShip_status().intValue() == 3) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("订单[" + order.getSn() + "]状态已经为【已发货】，不能再对其进行发货操作");
      }
      throw new IllegalStateException("订单[" + order.getSn() + "]状态已经为【已发货】，不能再对其进行发货操作");
    }

    if (delivery.getLogi_id() != null) {
      Logi logi = this.logiManager.getLogiById(delivery.getLogi_id());
      delivery.setLogi_code(logi.getCode());
      delivery.setLogi_name(logi.getName());
    }

    delivery.setType(Integer.valueOf(1));
    delivery.setMember_id(order.getMember_id());
    delivery.setCreate_time(Long.valueOf(System.currentTimeMillis()));
    this.baseDaoSupport.insert("delivery", delivery);
    Integer delivery_id = Integer.valueOf(this.baseDaoSupport.getLastId("delivery"));

    int shipStatus = 3;

    int goodsShipStatus = processGoodsShipItem(orderId, delivery_id, itemList);
    shipStatus = goodsShipStatus;

    shipStatus = shipStatus == 3 ? 3 : 5;

    for (Iterator i$ = itemList.iterator(); i$.hasNext(); ) { DeliveryItem deliverItem = (DeliveryItem)i$.next();
      List<AllocationItem> alloitemList = getAllocationList(orderId.intValue(), deliverItem.getOrder_itemid());
      for (AllocationItem alloItem : alloitemList)
        this.orderPluginBundle.onItemShip(order, deliverItem, alloItem);
    }
    DeliveryItem deliverItem;
    this.orderPluginBundle.onShip(delivery, itemList);

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("更新订单[" + orderId + "]状态[" + 5 + "]，发货状态[" + shipStatus + "]");
    }

    this.baseDaoSupport.execute("update order set status=?,ship_status=? where order_id=?", new Object[] { Integer.valueOf(5), Integer.valueOf(shipStatus), orderId });
    log(delivery.getOrder_id(), "订单发货，物流公司：" + delivery.getLogi_name() + "，物流单据号：" + delivery.getLogi_no());
  }

  private List<AllocationItem> getAllocationList(int orderid, int itemid)
  {
    String sql = "select a.*,g.cat_id from " + getTableName("allocation_item") + "  a ," + getTableName("goods") + " g where a.orderid=? and a.itemid=? and a.goodsid=g.goods_id ";
    return this.daoSupport.queryForList(sql, AllocationItem.class, new Object[] { Integer.valueOf(orderid), Integer.valueOf(itemid) });
  }

  private int processGoodsReturnItem(Integer orderId, Integer delivery_id, List<DeliveryItem> itemList)
  {
    List orderItemList = this.orderManager.listGoodsItems(orderId);
    int shipStatus = 4;
    for (DeliveryItem item : itemList)
    {
      if (item.getGoods_id() == null) throw new IllegalArgumentException(item.getName() + " goods id is  NULL");
      if (item.getProduct_id() == null) throw new IllegalArgumentException(item.getName() + " product id is  NULL");
      if (item.getNum() == null) throw new IllegalArgumentException(item.getName() + " num id is  NULL");

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("检测item[" + item.getName() + "]退货数量是否合法");
      }

      int itemStatus = checkGoodsReturnNum(orderItemList, item);

      shipStatus = (shipStatus == 4) && (itemStatus == 4) ? 4 : itemStatus;

      item.setDelivery_id(delivery_id);

      this.baseDaoSupport.insert("delivery_item", item);

      this.baseDaoSupport.execute("update order_items set ship_num=ship_num-? where order_id=? and product_id=?", new Object[] { item.getNum(), orderId, item.getProduct_id() });

      this.baseDaoSupport.execute("update goods set store=store+? where goods_id=?", new Object[] { item.getNum(), item.getGoods_id() });
      this.baseDaoSupport.execute("update product set store=store+? where product_id=?", new Object[] { item.getNum(), item.getProduct_id() });
    }

    return shipStatus;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  private int processGoodsShipItem(Integer orderId, Integer delivery_id, List<DeliveryItem> itemList)
  {
    List productList = listProductbyOrderId(orderId);
    List orderItemList = this.orderManager.listGoodsItems(orderId);

    int shipStatus = 3;

    for (DeliveryItem item : itemList)
    {
      if (item.getGoods_id() == null) throw new IllegalArgumentException(item.getName() + " goods id is  NULL");
      if (item.getProduct_id() == null) throw new IllegalArgumentException(item.getName() + " product id is  NULL");
      if (item.getNum() == null) throw new IllegalArgumentException(item.getName() + " num id is  NULL");

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("检测item[" + item.getName() + "]发货数量是否合法");
      }

      int itemStatus = checkGoodsShipNum(orderItemList, item);

      shipStatus = (shipStatus == 3) && (itemStatus == 3) ? 3 : itemStatus;

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("检测item[" + item.getName() + "]库存");
      }

      item.setDelivery_id(delivery_id);

      this.baseDaoSupport.insert("delivery_item", item);

      this.baseDaoSupport.execute("update order_items set ship_num=ship_num+? where order_id=? and product_id=?", new Object[] { item.getNum(), orderId, item.getProduct_id() });

      this.baseDaoSupport.execute("update goods set store=store-? where goods_id=?", new Object[] { item.getNum(), item.getGoods_id() });
      this.baseDaoSupport.execute("update product set store=store-? where product_id=?", new Object[] { item.getNum(), item.getProduct_id() });

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("更新" + item.getName() + "[" + item.getProduct_id() + "," + item.getGoods_id() + "-[" + item.getNum() + "]");
      }

    }

    return shipStatus;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void returned(Delivery delivery, List<DeliveryItem> itemList, List<DeliveryItem> giftItemList)
  {
    if (delivery == null) throw new IllegalArgumentException("param delivery is NULL");
    if (itemList == null) throw new IllegalArgumentException("param itemList is NULL");
    if (delivery.getOrder_id() == null) throw new IllegalArgumentException("param order id is null");

    if (delivery.getMoney() == null) delivery.setMoney(Double.valueOf(0.0D));
    if (delivery.getProtect_price() == null) delivery.setProtect_price(Double.valueOf(0.0D));

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("订单[" + delivery.getOrder_id() + "]退货");
    }

    Integer orderId = delivery.getOrder_id();
    Order order = this.orderManager.get(orderId);
    checkDisabled(order);
    if (order.getShip_status().intValue() == 4) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("订单[" + order.getSn() + "]状态已经为【已退货】，不能再对其进行退货操作");
      }
      throw new IllegalStateException("订单[" + order.getSn() + "]状态已经为【已退货】，不能再对其进行退货操作");
    }

    if (delivery.getLogi_id() != null) {
      Logi logi = this.logiManager.getLogiById(delivery.getLogi_id());
      delivery.setLogi_code(logi.getCode());
      delivery.setLogi_name(logi.getName());
    }

    delivery.setType(Integer.valueOf(2));
    delivery.setMember_id(order.getMember_id());
    delivery.setCreate_time(Long.valueOf(System.currentTimeMillis()));
    this.baseDaoSupport.insert("delivery", delivery);
    Integer delivery_id = Integer.valueOf(this.baseDaoSupport.getLastId("delivery"));

    int shipStatus = 4;
    int goodsShipStatus = processGoodsReturnItem(orderId, delivery_id, itemList);
    shipStatus = goodsShipStatus;

    shipStatus = shipStatus == 4 ? 4 : 6;

    this.orderPluginBundle.onReturned(delivery, itemList);

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("更新订单[" + orderId + "]状态[" + -2 + "]，发货状态[" + shipStatus + "]");
    }

    this.baseDaoSupport.execute("update order set status=?,ship_status=? where order_id=?", new Object[] { Integer.valueOf(-2), Integer.valueOf(shipStatus), orderId });

    log(delivery.getOrder_id(), "订单退货，物流单据号：" + delivery.getLogi_no());
  }

  public void change(Delivery delivery, List<DeliveryItem> itemList, List<DeliveryItem> gifitemList)
  {
    if (delivery == null) throw new IllegalArgumentException("param delivery is NULL");
    if (itemList == null) throw new IllegalArgumentException("param itemList is NULL");
    if (delivery.getOrder_id() == null) throw new IllegalArgumentException("param order id is null");

    if (delivery.getMoney() == null) delivery.setMoney(Double.valueOf(0.0D));
    if (delivery.getProtect_price() == null) delivery.setProtect_price(Double.valueOf(0.0D));

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("订单[" + delivery.getOrder_id() + "]换货");
    }

    Integer orderId = delivery.getOrder_id();
    Order order = this.orderManager.get(orderId);
    checkDisabled(order);

    delivery.setType(Integer.valueOf(3));
    delivery.setMember_id(order.getMember_id());
    delivery.setCreate_time(Long.valueOf(System.currentTimeMillis()));
    this.baseDaoSupport.insert("delivery", delivery);

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("更新订单[" + orderId + "]状态[" + -7 + "]");
    }

    this.baseDaoSupport.execute("update order set status=?, ship_status=? where order_id=?", new Object[] { Integer.valueOf(-7), Integer.valueOf(8), orderId });
    log(delivery.getOrder_id(), "订单换货，物流单据号：" + delivery.getLogi_no());
  }

  private int checkGoodsReturnNum(List<OrderItem> orderItemList, DeliveryItem item)
  {
    int status = 3;
    for (OrderItem orderItem : orderItemList)
    {
      if (orderItem.getProduct_id().compareTo(item.getProduct_id()) == 0)
      {
        Integer shipNum = orderItem.getShip_num();
        if (shipNum.intValue() < item.getNum().intValue()) {
          if (this.logger.isDebugEnabled()) {
            this.logger.debug("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
          }
          throw new RuntimeException("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
        }
        if (shipNum.compareTo(item.getNum()) == 0) {
          status = 4;
        }

        if (shipNum.intValue() > item.getNum().intValue()) {
          status = 6;
        }
      }
    }
    return status;
  }

  private int checkGiftReturnNum(List<Map> orderItemList, DeliveryItem item)
  {
    int status = 3;
    for (Map orderItem : orderItemList)
    {
      if (Integer.valueOf(orderItem.get("gift_id").toString()).compareTo(item.getGoods_id()) == 0)
      {
        Integer shipNum = Integer.valueOf(orderItem.get("shipnum").toString());
        if (shipNum.intValue() < item.getNum().intValue()) {
          if (this.logger.isDebugEnabled()) {
            this.logger.debug("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
          }
          throw new RuntimeException("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
        }
        if (shipNum.compareTo(item.getNum()) == 0) {
          status = 4;
        }

        if (shipNum.intValue() > item.getNum().intValue()) {
          status = 6;
        }
      }
    }
    return status;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void complete(Integer orderId)
  {
    if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
    this.baseDaoSupport.execute("update order set status=? where order_id=?", new Object[] { Integer.valueOf(7), orderId });
    this.baseDaoSupport.execute("update order set complete_time=? where order_id=?", new Object[] { Long.valueOf(DateUtil.getDatelineLong()), orderId });
    log(orderId, "订单完成");
  }

  public void cancel(Integer orderId)
  {
    if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
    this.baseDaoSupport.execute("update order set status=? where order_id=?", new Object[] { Integer.valueOf(8), orderId });
    log(orderId, "订单作废");
    Order order = this.orderManager.get(orderId);
    this.orderPluginBundle.onCanel(order);
  }

  public void confirmOrder(Integer orderId)
  {
    if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
    this.baseDaoSupport.execute("update order set status=?,sale_cmpl=1,sale_cmpl_time=? where order_id=?", new Object[] { Integer.valueOf(9), Integer.valueOf(DateUtil.getDateline()), orderId });
    log(orderId, "订单确认");
  }

  public void addCodPaymentLog(Order order) {
    if (order.getIsCod()) {
      PaymentLog paymentLog = new PaymentLog();
      Member member = null;
      if (order.getMember_id() != null)
        member = this.memberManager.get(order.getMember_id());
      if (member != null) {
        paymentLog.setMember_id(member.getMember_id());
        paymentLog.setPay_user(member.getUname());
      } else {
        paymentLog.setPay_user("匿名购买者");
      }
      paymentLog.setPay_date(DateUtil.getDatelineLong());
      paymentLog.setMoney(order.getOrder_amount());
      paymentLog.setOrder_sn(order.getSn());
      paymentLog.setSn(this.orderManager.createSn());
      paymentLog.setPay_method("货到付款");
      paymentLog.setOrder_id(order.getOrder_id().intValue());
      paymentLog.setType(2);
      paymentLog.setStatus(Integer.valueOf(0));
      paymentLog.setCreate_time(Long.valueOf(System.currentTimeMillis()));
      this.baseDaoSupport.insert("payment_logs", paymentLog);
    }
  }

  public List<OrderItem> listNotShipGoodsItem(Integer orderId)
  {
    String sql = "select oi.*,p.store,p.sn from " + getTableName("order_items") + "  oi left join " + getTableName("product") + " p on oi.product_id = p.product_id";
    sql = sql + "  where order_id=? and oi.ship_num<oi.num";
    List itemList = this.daoSupport.queryForList(sql, OrderItem.class, new Object[] { orderId });
    this.orderPluginBundle.onFilter(orderId, itemList);
    return itemList;
  }

  public List<OrderItem> listNotAllocationGoodsItem(Integer orderid)
  {
    String sql = "select oi.*,p.store,p.sn from " + getTableName("order_items") + "  oi  left join " + getTableName("product") + " p on oi.product_id = p.product_id";
    sql = sql + " left join on " + getTableName("order") + " o on oi.order_id = o.order_id  where o.order_id=?";
    List itemList = this.daoSupport.queryForList(sql, OrderItem.class, new Object[] { orderid });
    this.orderPluginBundle.onFilter(orderid, itemList);
    return itemList;
  }

  public List<OrderItem> listShipGoodsItem(Integer orderId)
  {
    String sql = "select oi.*,p.store,p.sn from " + getTableName("order_items") + "  oi left join " + getTableName("product") + " p on oi.product_id = p.product_id";
    sql = sql + "  where order_id=? and ship_num!=0";
    List itemList = this.daoSupport.queryForList(sql, OrderItem.class, new Object[] { orderId });
    this.orderPluginBundle.onFilter(orderId, itemList);
    return itemList;
  }

  private int checkGoodsShipNum(List<OrderItem> orderItemList, DeliveryItem item)
  {
    int status = 2;
    for (OrderItem orderItem : orderItemList)
    {
      if (orderItem.getItem_id().compareTo(Integer.valueOf(item.getOrder_itemid())) == 0)
      {
        Integer num = orderItem.getNum();
        Integer shipNum = orderItem.getShip_num();
        if (num.intValue() < item.getNum().intValue() + shipNum.intValue()) {
          if (this.logger.isDebugEnabled()) {
            this.logger.debug("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
          }
          throw new RuntimeException("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
        }
        if (num.compareTo(Integer.valueOf(item.getNum().intValue() + shipNum.intValue())) == 0) {
          status = 3;
        }

        if (num.intValue() > item.getNum().intValue() + shipNum.intValue()) {
          status = 5;
        }
      }
    }
    return status;
  }

  private int checkGiftShipNum(List<Map> orderItemList, DeliveryItem item)
  {
    int status = 2;
    for (Map orderItem : orderItemList)
    {
      if (Integer.valueOf(orderItem.get("gift_id").toString()).compareTo(item.getGoods_id()) == 0)
      {
        Integer num = Integer.valueOf(orderItem.get("num").toString());
        Integer shipNum = Integer.valueOf(orderItem.get("shipnum").toString());
        if (num.intValue() < item.getNum().intValue() + shipNum.intValue()) {
          if (this.logger.isDebugEnabled()) {
            this.logger.debug("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]后超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
          }
          throw new RuntimeException("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]后超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
        }
        if (num.compareTo(Integer.valueOf(item.getNum().intValue() + shipNum.intValue())) == 0) {
          status = 3;
        }

        if (num.intValue() > item.getNum().intValue() + shipNum.intValue()) {
          status = 5;
        }
      }
    }
    return status;
  }

  private void checkGoodsStore(Integer orderId, List<Product> productList, DeliveryItem item)
  {
    for (Product product : productList)
      if ((product.getProduct_id().compareTo(item.getProduct_id()) == 0) && 
        (product.getStore().compareTo(item.getNum()) < 0)) {
        if (this.logger.isDebugEnabled()) {
          this.logger.debug("商品[" + item.getName() + "]库存[" + product.getStore() + "]不足->发货量[" + item.getNum() + "]");
        }

        throw new RuntimeException("商品[" + item.getName() + "]库存[" + product.getStore() + "]不足->发货量[" + item.getNum() + "]");
      }
  }

  private List<Product> listProductbyOrderId(Integer orderId)
  {
    String sql = "select * from " + getTableName("product") + " where product_id in (select product_id from " + getTableName("order_items") + " where order_id=?)";

    List productList = this.daoSupport.queryForList(sql, Product.class, new Object[] { orderId });
    return productList;
  }

  private void checkDisabled(Order order)
  {
    if ((order.getStatus().intValue() == 7) || (order.getStatus().intValue() == 8))
      throw new IllegalStateException("订单已经完成或作废，不能完成操作");
  }

  public void updateAllocation(int allocationId, int orderId)
  {
    String sql = "update " + getTableName("allocation_item") + " set iscmpl=1 where allocationid=" + allocationId;
    this.daoSupport.execute(sql, new Object[0]);
    sql = "update  " + getTableName("order") + " set status=" + 4 + ",ship_status=" + 2 + " where order_id=" + orderId + " and (select count(allocationid) from " + getTableName("allocation_item") + " where orderid =" + orderId + ")=(select sum(iscmpl) from  " + getTableName("allocation_item") + " where  orderid =" + orderId + ")";
    this.daoSupport.execute(sql, new Object[0]);
  }

  public Order payConfirm(int orderId)
  {
    Order order = this.orderManager.get(Integer.valueOf(orderId));

    AdminUser adminUser = this.adminUserManager.getCurrentUser();
    if (adminUser != null)
      log(Integer.valueOf(orderId), "确认付款");
    else {
      log(Integer.valueOf(orderId), "确认付款", null, "系统");
    }

    String sql = "select sum(money) money from payment_logs where order_sn=?";
    double paymoney = ((Double)this.baseDaoSupport.queryForObject(sql, new DoubleMapper(), new Object[] { order.getSn() })).doubleValue();
    double ordermoney = order.getOrder_amount().doubleValue();

    int payStatus = 0;
    int orderStatus = 1;
    if (paymoney < ordermoney) {
      payStatus = 5;
    }
    if (paymoney >= ordermoney) {
      payStatus = 2;
      if (!order.getIsCod()) {
        orderStatus = 2;
      }
      else if (order.getShip_status() != null) {
        if (order.getShip_status().intValue() == 1)
          orderStatus = 3;
        else if (order.getShip_status().intValue() == 2)
          orderStatus = 4;
        else if (order.getShip_status().intValue() == 3)
          orderStatus = 5;
        else
          orderStatus = 2;
      }
      else orderStatus = 2;

    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("更新订单状态[" + orderStatus + "],支付状态[" + payStatus + "]");
    }

    sql = "update " + getTableName("order") + " set paymoney=?, status=" + orderStatus + ",pay_status=" + payStatus + " where order_id=?";
    this.daoSupport.execute(sql, new Object[] { Double.valueOf(paymoney), order.getOrder_id() });

    sql = "update payment_logs set status=1 where order_id=?";
    this.baseDaoSupport.execute(sql, new Object[] { order.getOrder_id() });

    order.setStatus(Integer.valueOf(orderStatus));
    order.setPay_status(Integer.valueOf(payStatus));
    this.orderPluginBundle.onConfirmPay(order);
    return order;
  }

  public void rogConfirm(int orderId, Integer op_id, String op_name, String sign_name, int sign_time)
  {
    Order order = this.orderManager.get(Integer.valueOf(orderId));
    if ((order.getPayment_id().intValue() == 2) && (order.getStatus().intValue() == 5))
    {
      payConfirm(orderId);
    }

    String sql = "update order set status=6,the_sign='" + sign_name + "',signing_time=" + sign_time + " , sale_cmpl=1 , sale_cmpl_time=" + DateUtil.getDateline() + " where order_id=" + orderId;
    this.baseDaoSupport.execute(sql, new Object[0]);

    log(Integer.valueOf(orderId), "确认收货成功", op_id, op_name);

    List<Map> itemList = this.orderManager.getItemsByOrderid(Integer.valueOf(orderId));
    for (Map map : itemList) {
      this.baseDaoSupport.execute("INSERT INTO member_order_item(member_id,goods_id,order_id,item_id,commented,comment_time) VALUES(?,?,?,?,0,0)", new Object[] { order.getMember_id(), map.get("goods_id").toString(), map.get("order_id").toString(), map.get("item_id").toString() });

      String updatesql = "update goods set buy_count=buy_count+" + map.get("num").toString() + " where goods_id=" + map.get("goods_id").toString();
      this.baseDaoSupport.execute(updatesql, new Object[0]);
    }
  }

  public void cancel(String sn, String reason)
  {
    Order order = this.orderManager.get(sn);
    if (order == null) {
      throw new RuntimeException("对不起，此订单不存在！");
    }
    if ((order.getStatus() == null) || (order.getStatus().intValue() != 0)) {
      throw new RuntimeException("对不起，此订单不能取消！");
    }
    IUserService userService = UserServiceFactory.getUserService();
    if (order.getMember_id().intValue() != userService.getCurrentMember().getMember_id().intValue()) {
      throw new RuntimeException("对不起，您没有权限进行此项操作！");
    }
    order.setStatus(Integer.valueOf(8));
    order.setCancel_reason(reason);
    this.orderManager.edit(order);

    Member member = UserServiceFactory.getUserService().getCurrentMember();

    if (member == null)
      log(order.getOrder_id(), "取消订单", null, "游客");
    else {
      log(order.getOrder_id(), "取消订单", member.getMember_id(), member.getUname());
    }

    this.orderPluginBundle.onCanel(order);
  }

  public void restore(String sn)
  {
    Order order = this.orderManager.get(sn);
    if (order == null) {
      throw new RuntimeException("对不起，此订单不存在！");
    }
    if ((order.getStatus() == null) || (order.getStatus().intValue() != 8)) {
      throw new RuntimeException("对不起，此订单不能还原！");
    }
    IUserService userService = UserServiceFactory.getUserService();
    if (order.getMember_id().intValue() != userService.getCurrentMember().getMember_id().intValue()) {
      throw new RuntimeException("对不起，您没有权限进行此项操作！");
    }
    order.setStatus(Integer.valueOf(0));
    order.setCancel_reason("");
    this.orderManager.edit(order);

    this.orderPluginBundle.onRestore(order);
  }

  public IOrderManager getOrderManager()
  {
    return this.orderManager;
  }

  public void setOrderManager(IOrderManager orderManager) {
    this.orderManager = orderManager;
  }

  public IMemberManager getMemberManager() {
    return this.memberManager;
  }

  public void setMemberManager(IMemberManager memberManager) {
    this.memberManager = memberManager;
  }

  public IPointHistoryManager getPointHistoryManager() {
    return this.pointHistoryManager;
  }

  public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
    this.pointHistoryManager = pointHistoryManager;
  }

  public IMemberPointManger getMemberPointManger()
  {
    return this.memberPointManger;
  }

  public void setMemberPointManger(IMemberPointManger memberPointManger)
  {
    this.memberPointManger = memberPointManger;
  }

  public OrderPluginBundle getOrderPluginBundle()
  {
    return this.orderPluginBundle;
  }

  public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle)
  {
    this.orderPluginBundle = orderPluginBundle;
  }

  public ILogiManager getLogiManager()
  {
    return this.logiManager;
  }

  public void setLogiManager(ILogiManager logiManager)
  {
    this.logiManager = logiManager;
  }

  public IAdminUserManager getAdminUserManager()
  {
    return this.adminUserManager;
  }

  public void setAdminUserManager(IAdminUserManager adminUserManager)
  {
    this.adminUserManager = adminUserManager;
  }

  public IPromotionManager getPromotionManager()
  {
    return this.promotionManager;
  }

  public void setPromotionManager(IPromotionManager promotionManager)
  {
    this.promotionManager = promotionManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.OrderFlowManager
 * JD-Core Version:    0.6.1
 */