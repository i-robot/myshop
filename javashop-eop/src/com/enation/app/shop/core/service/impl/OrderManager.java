package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.service.auth.IAdminUserManager;
import com.enation.app.base.core.service.auth.IPermissionManager;
import com.enation.app.base.core.service.auth.IRoleManager;
import com.enation.app.base.core.service.auth.impl.PermissionConfig;
import com.enation.app.shop.core.model.DepotUser;
import com.enation.app.shop.core.model.DlyType;
import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.OrderItem;
import com.enation.app.shop.core.model.OrderLog;
import com.enation.app.shop.core.model.PayCfg;
import com.enation.app.shop.core.model.Promotion;
import com.enation.app.shop.core.model.support.CartItem;
import com.enation.app.shop.core.model.support.OrderPrice;
import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
import com.enation.app.shop.core.service.ICartManager;
import com.enation.app.shop.core.service.IDlyTypeManager;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.app.shop.core.service.IOrderAllocationManager;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.app.shop.core.service.IPaymentManager;
import com.enation.app.shop.core.service.IPromotionManager;
import com.enation.eop.processor.httpcache.HttpCacheManager;
import com.enation.eop.resource.model.AdminUser;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.DoubleMapper;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.database.StringMapper;
import com.enation.framework.util.CurrencyUtil;
import com.enation.framework.util.ExcelUtil;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class OrderManager extends BaseSupport
  implements IOrderManager
{
  private ICartManager cartManager;
  private IDlyTypeManager dlyTypeManager;
  private IPaymentManager paymentManager;
  private IPromotionManager promotionManager;
  private OrderPluginBundle orderPluginBundle;
  private IPermissionManager permissionManager;
  private IAdminUserManager adminUserManager;
  private IRoleManager roleManager;
  private IGoodsManager goodsManager;
  private IOrderAllocationManager orderAllocationManager;

  public IOrderAllocationManager getOrderAllocationManager()
  {
    return this.orderAllocationManager;
  }

  public void setOrderAllocationManager(IOrderAllocationManager orderAllocationManager) {
    this.orderAllocationManager = orderAllocationManager;
  }
  public IGoodsManager getGoodsManager() {
    return this.goodsManager;
  }
  public void setGoodsManager(IGoodsManager goodsManager) {
    this.goodsManager = goodsManager;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void savePrice(double price, int orderid) {
    Order order = get(Integer.valueOf(orderid));
    double amount = order.getOrder_amount().doubleValue();

    double discount = CurrencyUtil.sub(amount, price);
    this.baseDaoSupport.execute("update order set order_amount=? where order_id=?", new Object[] { Double.valueOf(price), Integer.valueOf(orderid) });

    this.baseDaoSupport.execute("update order set discount=discount+? where order_id=?", new Object[] { Double.valueOf(discount), Integer.valueOf(orderid) });

    AdminUser adminUser = this.adminUserManager.getCurrentUser();
    double nowamount = get(Integer.valueOf(orderid)).getOrder_amount().doubleValue();
    log(Integer.valueOf(orderid), new StringBuilder().append("修改订单价格从").append(amount).append("修改为").append(nowamount).toString(), null, adminUser.getUsername());
  }
  @Transactional(propagation=Propagation.REQUIRED)
  public double saveShipmoney(double shipmoney, int orderid) {
    Order order = get(Integer.valueOf(orderid));
    double currshipamount = order.getShipping_amount().doubleValue();

    double shortship = CurrencyUtil.sub(shipmoney, currshipamount);
    double discount = CurrencyUtil.sub(currshipamount, shipmoney);
    this.baseDaoSupport.execute("update order set order_amount=order_amount+? where order_id=?", new Object[] { Double.valueOf(shortship), Integer.valueOf(orderid) });

    this.baseDaoSupport.execute("update order set shipping_amount=? where order_id=?", new Object[] { Double.valueOf(shipmoney), Integer.valueOf(orderid) });

    this.baseDaoSupport.execute("update order set discount=discount+? where order_id=?", new Object[] { Double.valueOf(discount), Integer.valueOf(orderid) });

    AdminUser adminUser = this.adminUserManager.getCurrentUser();

    double lastestShipmoney = get(Integer.valueOf(orderid)).getShipping_amount().doubleValue();
    log(Integer.valueOf(orderid), new StringBuilder().append("运费从").append(currshipamount).append("修改为").append(lastestShipmoney).toString(), null, adminUser.getUsername());
    return get(Integer.valueOf(orderid)).getOrder_amount().doubleValue();
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
  public Order add(Order order, String sessionid) {
    String opname = "游客";

    if (order == null) {
      throw new RuntimeException("error: order is null");
    }

    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();

    if (member != null) {
      order.setMember_id(member.getMember_id());
      opname = member.getUname();
    }

    boolean isProtected = order.getIs_protect().compareTo(Integer.valueOf(1)) == 0;

    OrderPrice orderPrice = this.cartManager.countPrice(sessionid, order.getShipping_id(), new StringBuilder().append("").append(order.getRegionid()).toString(), Boolean.valueOf(isProtected));

    order.setGoods_amount(orderPrice.getGoodsPrice());
    order.setWeight(orderPrice.getWeight());

    order.setDiscount(orderPrice.getDiscountPrice());
    order.setOrder_amount(orderPrice.getOrderPrice());
    order.setProtect_price(orderPrice.getProtectPrice());
    order.setShipping_amount(orderPrice.getShippingPrice());
    order.setGainedpoint(orderPrice.getPoint().intValue());

    DlyType dlyType = this.dlyTypeManager.getDlyTypeById(order.getShipping_id());
    if (dlyType == null)
      throw new RuntimeException("shipping not found count error");
    order.setShipping_type(dlyType.getName());

    PayCfg payCfg = this.paymentManager.get(order.getPayment_id());
    order.setPaymoney(this.paymentManager.countPayPrice(order.getOrder_id()));
    order.setPayment_name(payCfg.getName());

    order.setPayment_type(payCfg.getType());

    order.setCreate_time(Long.valueOf(System.currentTimeMillis()));
    order.setSn(createSn());
    order.setStatus(Integer.valueOf(0));
    order.setDisabled(Integer.valueOf(0));
    order.setPay_status(Integer.valueOf(0));
    order.setShip_status(Integer.valueOf(0));

    List itemList = this.cartManager.listGoods(sessionid);

    this.orderPluginBundle.onBeforeCreate(order, itemList, sessionid);

    this.baseDaoSupport.insert("order", order);

    if (itemList.isEmpty()) {
      throw new RuntimeException("创建订单失败，购物车为空");
    }
    Integer orderId = Integer.valueOf(this.baseDaoSupport.getLastId("order"));

    saveGoodsItem(itemList, orderId);

    if (member != null) {
      this.promotionManager.applyOrderPmt(orderId, orderPrice.getOrderPrice(), member.getLv_id());

      List<Promotion> pmtList = this.promotionManager.list(orderPrice.getOrderPrice(), member.getLv_id());

      for (Promotion pmt : pmtList) {
        String sql = "insert into order_pmt(pmt_id,order_id,pmt_describe)values(?,?,?)";
        this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(pmt.getPmt_id()), orderId, pmt.getPmt_describe() });
      }

    }

    OrderLog log = new OrderLog();
    log.setMessage("订单创建");
    log.setOp_name(opname);
    log.setOrder_id(orderId);
    addLog(log);
    order.setOrder_id(orderId);
    order.setOrderprice(orderPrice);
    this.orderPluginBundle.onAfterCreate(order, itemList, sessionid);

    this.cartManager.clean(sessionid);

    HttpCacheManager.sessionChange();

    return order;
  }

  private void addLog(OrderLog log)
  {
    log.setOp_time(Long.valueOf(System.currentTimeMillis()));
    this.baseDaoSupport.insert("order_log", log);
  }

  private void saveGoodsItem(List<CartItem> itemList, Integer order_id)
  {
    for (int i = 0; i < itemList.size(); i++)
    {
      OrderItem orderItem = new OrderItem();

      CartItem cartItem = (CartItem)itemList.get(i);
      orderItem.setPrice(cartItem.getCoupPrice());
      orderItem.setName(cartItem.getName());
      orderItem.setNum(Integer.valueOf(cartItem.getNum()));

      orderItem.setGoods_id(cartItem.getGoods_id());
      orderItem.setShip_num(Integer.valueOf(0));
      orderItem.setProduct_id(cartItem.getProduct_id());
      orderItem.setOrder_id(order_id);
      orderItem.setGainedpoint(cartItem.getPoint().intValue());
      orderItem.setAddon(cartItem.getAddon());

      orderItem.setSn(cartItem.getSn());
      orderItem.setImage(cartItem.getImage_default());
      orderItem.setCat_id(cartItem.getCatid());

      orderItem.setUnit(cartItem.getUnit());

      this.baseDaoSupport.insert("order_items", orderItem);
    }
  }

  @Transactional(propagation=Propagation.REQUIRED)
  private void saveGiftItem(List<CartItem> itemList, Integer orderid)
  {
    Member member = UserServiceFactory.getUserService().getCurrentMember();
    if (member == null) {
      throw new IllegalStateException("会员尚未登录,不能兑换赠品!");
    }

    int point = 0;
    for (CartItem item : itemList) {
      point += item.getSubtotal().intValue();
      this.baseDaoSupport.execute("insert into order_gift(order_id,gift_id,gift_name,point,num,shipnum,getmethod)values(?,?,?,?,?,?,?)", new Object[] { orderid, item.getProduct_id(), item.getName(), item.getPoint(), Integer.valueOf(item.getNum()), Integer.valueOf(0), "exchange" });
    }

    if (member.getPoint().intValue() < point) {
      throw new IllegalStateException("会员积分不足,不能兑换赠品!");
    }
    member.setPoint(Integer.valueOf(member.getPoint().intValue() - point));
    this.baseDaoSupport.execute("update member set point=? where member_id=? ", new Object[] { member.getPoint(), member.getMember_id() });
  }

  public Page list(int pageNO, int pageSize, int disabled, String searchkey, String searchValue, String order)
  {
    StringBuffer sql = new StringBuffer("select * from order where disabled=? ");

    if ((!StringUtil.isEmpty(searchkey)) && (!StringUtil.isEmpty(searchValue))) {
      sql.append(" and ");
      sql.append(searchkey);
      sql.append("=?");
    }

    AdminUser adminUser = this.adminUserManager.getCurrentUser();
    if (adminUser.getFounder() != 1) {
      boolean isShiper = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_ship"));
      boolean haveAllo = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("allocation"));

      boolean haveOrder = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("order"));

      if ((isShiper) && (!haveAllo) && (!haveOrder)) {
        DepotUser depotUser = (DepotUser)adminUser;
        int depotid = depotUser.getDepotid().intValue();
        sql.append(new StringBuilder().append(" and depotid=").append(depotid).toString());
      }
    }

    order = StringUtil.isEmpty(order) ? "order_id desc" : order;
    sql.append(new StringBuilder().append(" order by ").append(order).toString());
    Page page = null;

    if ((!StringUtil.isEmpty(searchkey)) && (!StringUtil.isEmpty(searchValue)))
    {
      page = this.baseDaoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class, new Object[] { Integer.valueOf(disabled), searchValue });
    }
    else {
      page = this.baseDaoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class, new Object[] { Integer.valueOf(disabled) });
    }

    return page;
  }

  public Page list(int pageNo, int pageSize, int status, int depotid, String order)
  {
    order = StringUtil.isEmpty(order) ? "order_id desc" : order;
    String sql = new StringBuilder().append("select * from order where disabled=0 and status=").append(status).toString();

    if (depotid > 0) {
      sql = new StringBuilder().append(sql).append(" and depotid=").append(depotid).toString();
    }
    sql = new StringBuilder().append(sql).append(" order by ").append(order).toString();
    Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);

    return page;
  }

  public Page listbyshipid(int pageNo, int pageSize, int status, int shipping_id, String order)
  {
    order = StringUtil.isEmpty(order) ? "order_id desc" : order;
    String sql = new StringBuilder().append("select * from order where disabled=0 and status=").append(status).append(" and shipping_id= ").append(shipping_id).toString();

    sql = new StringBuilder().append(sql).append(" order by ").append(order).toString();
    Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);

    return page;
  }

  public Page listConfirmPay(int pageNo, int pageSize, String order) {
    order = StringUtil.isEmpty(order) ? "order_id desc" : order;
    String sql = "select * from order where disabled=0 and ((status = 5 and payment_type = 'cod') or status= 1  )";

    sql = new StringBuilder().append(sql).append(" order by ").append(order).toString();
    Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);

    return page;
  }

  public Order get(Integer orderId) {
    String sql = "select * from order where order_id=?";
    Order order = (Order)this.baseDaoSupport.queryForObject(sql, Order.class, new Object[] { orderId });

    return order;
  }
  public Order get(String ordersn) {
    String sql = "select * from order where sn=?";
    Order order = (Order)this.baseDaoSupport.queryForObject(sql, Order.class, new Object[] { ordersn });
    return order;
  }

  public List<OrderItem> listGoodsItems(Integer orderId)
  {
    String sql = new StringBuilder().append("select * from ").append(getTableName("order_items")).toString();
    sql = new StringBuilder().append(sql).append(" where order_id = ?").toString();
    List itemList = this.daoSupport.queryForList(sql, OrderItem.class, new Object[] { orderId });
    this.orderPluginBundle.onFilter(orderId, itemList);
    return itemList;
  }

  public List listGiftItems(Integer orderId)
  {
    String sql = "select * from order_gift where order_id=?";
    return this.baseDaoSupport.queryForList(sql, new Object[] { orderId });
  }

  public List listLogs(Integer orderId)
  {
    String sql = "select * from order_log where order_id=?";
    return this.baseDaoSupport.queryForList(sql, new Object[] { orderId });
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void clean(Integer[] orderId) {
    String ids = StringUtil.arrayToString(orderId, ",");
    String sql = new StringBuilder().append("delete from order where order_id in (").append(ids).append(")").toString();
    this.baseDaoSupport.execute(sql, new Object[0]);

    sql = new StringBuilder().append("delete from order_items where order_id in (").append(ids).append(")").toString();
    this.baseDaoSupport.execute(sql, new Object[0]);

    sql = new StringBuilder().append("delete from order_log where order_id in (").append(ids).append(")").toString();
    this.baseDaoSupport.execute(sql, new Object[0]);

    sql = new StringBuilder().append("delete from payment_logs where order_id in (").append(ids).append(")").toString();
    this.baseDaoSupport.execute(sql, new Object[0]);

    sql = new StringBuilder().append("delete from ").append(getTableName("delivery_item")).append(" where delivery_id in (select delivery_id from ").append(getTableName("delivery")).append(" where order_id in (").append(ids).append("))").toString();

    this.daoSupport.execute(sql, new Object[0]);

    sql = new StringBuilder().append("delete from delivery where order_id in (").append(ids).append(")").toString();
    this.baseDaoSupport.execute(sql, new Object[0]);

    this.orderAllocationManager.clean(orderId);

    this.orderPluginBundle.onDelete(orderId);
  }

  private void exec(Integer[] orderId, int disabled)
  {
    String ids = StringUtil.arrayToString(orderId, ",");
    String sql = new StringBuilder().append("update order set disabled = ? where order_id in (").append(ids).append(")").toString();

    this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(disabled) });
  }

  public void delete(Integer[] orderId) {
    exec(orderId, 1);
  }

  public void revert(Integer[] orderId)
  {
    exec(orderId, 0);
  }

  public String createSn()
  {
    Date now = new Date();
    String sn = com.enation.framework.util.DateUtil.toString(now, "yyyyMMddhhmmss");

    return sn;
  }

  public ICartManager getCartManager() {
    return this.cartManager;
  }

  public void setCartManager(ICartManager cartManager) {
    this.cartManager = cartManager;
  }

  public IDlyTypeManager getDlyTypeManager() {
    return this.dlyTypeManager;
  }

  public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
    this.dlyTypeManager = dlyTypeManager;
  }

  public IPaymentManager getPaymentManager() {
    return this.paymentManager;
  }

  public void setPaymentManager(IPaymentManager paymentManager) {
    this.paymentManager = paymentManager;
  }

  public List listOrderByMemberId(int member_id) {
    String sql = "select * from order where member_id = ? order by create_time desc";
    List list = this.baseDaoSupport.queryForList(sql, Order.class, new Object[] { Integer.valueOf(member_id) });

    return list;
  }

  public Map mapOrderByMemberId(int memberId) {
    Integer buyTimes = Integer.valueOf(this.baseDaoSupport.queryForInt("select count(0) from order where member_id = ?", new Object[] { Integer.valueOf(memberId) }));

    Double buyAmount = (Double)this.baseDaoSupport.queryForObject("select sum(paymoney) from order where member_id = ?", new DoubleMapper(), new Object[] { Integer.valueOf(memberId) });

    Map map = new HashMap();
    map.put("buyTimes", buyTimes);
    map.put("buyAmount", buyAmount);
    return map;
  }

  public IPromotionManager getPromotionManager() {
    return this.promotionManager;
  }

  public void setPromotionManager(IPromotionManager promotionManager) {
    this.promotionManager = promotionManager;
  }

  public void edit(Order order) {
    this.baseDaoSupport.update("order", order, new StringBuilder().append("order_id = ").append(order.getOrder_id()).toString());
  }

  public List<Map> listAdjItem(Integer orderid)
  {
    String sql = "select * from order_items where order_id=? and addon!=''";
    return this.baseDaoSupport.queryForList(sql, new Object[] { orderid });
  }

  public Map censusState()
  {
    Map stateMap = new HashMap(7);
    String[] states = { "cancel_ship", "cancel_pay", "pay", "ship", "complete", "allocation_yes" };

    for (String s : states) {
      stateMap.put(s, Integer.valueOf(0));
    }

    String sql = new StringBuilder().append("select count(0) num,status  from ").append(getTableName("order")).append(" where disabled = 0 group by status").toString();

    List<Map> list = this.daoSupport.queryForList(sql, new RowMapper()
    {
      public Object mapRow(ResultSet rs, int arg1)
        throws SQLException
      {
        Map map = new HashMap();
        map.put("status", Integer.valueOf(rs.getInt("status")));
        map.put("num", Integer.valueOf(rs.getInt("num")));
        return map;
      }
    }
    , new Object[0]);

    for (Map state : list) {
      stateMap.put(getStateString((Integer)state.get("status")), state.get("num"));
    }

    sql = new StringBuilder().append("select count(0) num  from ").append(getTableName("order")).append(" where disabled = 0  and ((payment_type='cod' and status=0 )  or (payment_type!='cod' and status=1 ))").toString();

    int count = this.daoSupport.queryForInt(sql, new Object[0]);

    stateMap.put("wait", Integer.valueOf(count));

    sql = new StringBuilder().append("select count(0) num  from ").append(getTableName("order")).append(" where disabled = 0  and pay_status = 0 ").toString();

    count = this.daoSupport.queryForInt(sql, new Object[0]);

    stateMap.put("not_pay", Integer.valueOf(count));

    return stateMap;
  }

  private String getStateString(Integer state)
  {
    String str = null;
    switch (state.intValue()) {
    case -2:
      str = "cancel_ship";
      break;
    case -1:
      str = "cancel_pay";
      break;
    case 1:
      str = "pay";
      break;
    case 2:
      str = "ship";
      break;
    case 4:
      str = "allocation_yes";
      break;
    case 7:
      str = "complete";
      break;
    case 0:
    case 3:
    case 5:
    case 6:
    default:
      str = null;
    }

    return str;
  }

  public OrderPluginBundle getOrderPluginBundle()
  {
    return this.orderPluginBundle;
  }

  public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
    this.orderPluginBundle = orderPluginBundle;
  }

  public String export(Date start, Date end)
  {
    String sql = "select * from order where disabled=0 ";
    if (start != null) {
      sql = new StringBuilder().append(sql).append(" and create_time>").append(start.getTime()).toString();
    }

    if (end != null) {
      sql = new StringBuilder().append(sql).append("  and create_timecreate_time<").append(end.getTime()).toString();
    }

    List<Order> orderList = this.baseDaoSupport.queryForList(sql, Order.class, new Object[0]);

    ExcelUtil excelUtil = new ExcelUtil();

    InputStream in = FileUtil.getResourceAsStream("com/enation/app/shop/core/service/impl/order.xls");

    excelUtil.openModal(in);
    int i = 1;
    for (Order order : orderList)
    {
      excelUtil.writeStringToCell(i, 0, order.getSn());
      excelUtil.writeStringToCell(i, 1, com.enation.eop.sdk.utils.DateUtil.toString(new Date(order.getCreate_time().longValue()), "yyyy-MM-dd HH:mm:ss"));
      excelUtil.writeStringToCell(i, 2, order.getOrderStatus());
      excelUtil.writeStringToCell(i, 3, new StringBuilder().append("").append(order.getOrder_amount()).toString());
      excelUtil.writeStringToCell(i, 4, order.getShip_name());
      excelUtil.writeStringToCell(i, 5, order.getPayStatus());
      excelUtil.writeStringToCell(i, 6, order.getShipStatus());
      excelUtil.writeStringToCell(i, 7, order.getShipping_type());
      excelUtil.writeStringToCell(i, 8, order.getPayment_name());
      i++;
    }

    String filename = "";
    if ("2".equals(EopSetting.RUNMODE)) {
      EopSite site = EopContext.getContext().getCurrentSite();
      filename = new StringBuilder().append("/user/").append(site.getUserid()).append("/").append(site.getId()).append("/order").toString();
    } else {
      filename = "/order";
    }
    File file = new File(new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(filename).toString());
    if (!file.exists()) file.mkdirs();

    filename = new StringBuilder().append(filename).append("/order").append(com.enation.framework.util.DateUtil.getDatelineLong()).append(".xls").toString();
    excelUtil.writeToFile(new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(filename).toString());

    return new StringBuilder().append(EopSetting.IMG_SERVER_DOMAIN).append(filename).toString();
  }

  public OrderItem getItem(int itemid)
  {
    String sql = new StringBuilder().append("select items.*,p.store as store from ").append(getTableName("order_items")).append(" items ").toString();

    sql = new StringBuilder().append(sql).append(" left join ").append(getTableName("product")).append(" p on p.product_id = items.product_id ").toString();

    sql = new StringBuilder().append(sql).append(" where items.item_id = ?").toString();

    OrderItem item = (OrderItem)this.daoSupport.queryForObject(sql, OrderItem.class, new Object[] { Integer.valueOf(itemid) });

    return item;
  }

  public IAdminUserManager getAdminUserManager()
  {
    return this.adminUserManager;
  }

  public void setAdminUserManager(IAdminUserManager adminUserManager) {
    this.adminUserManager = adminUserManager;
  }

  public IPermissionManager getPermissionManager() {
    return this.permissionManager;
  }

  public void setPermissionManager(IPermissionManager permissionManager) {
    this.permissionManager = permissionManager;
  }

  public IRoleManager getRoleManager() {
    return this.roleManager;
  }

  public void setRoleManager(IRoleManager roleManager) {
    this.roleManager = roleManager;
  }

  public int getMemberOrderNum(int member_id, int payStatus)
  {
    return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM order WHERE member_id=? AND pay_status=?", new Object[] { Integer.valueOf(member_id), Integer.valueOf(payStatus) });
  }

  public List<Map> getItemsByOrderid(Integer order_id)
  {
    String sql = "select * from order_items where order_id=?";
    return this.baseDaoSupport.queryForList(sql, new Object[] { order_id });
  }

  public void refuseReturn(String orderSn)
  {
    this.baseDaoSupport.execute("update order set state = -5 where sn = ?", new Object[] { orderSn });
  }

  public void updateOrderPrice(double price, int orderid)
  {
    this.baseDaoSupport.execute("update order set order_amount = order_amount-?,goods_amount = goods_amount- ? where order_id = ?", new Object[] { Double.valueOf(price), Double.valueOf(price), Integer.valueOf(orderid) });
  }

  public String queryLogiNameById(Integer logi_id)
  {
    return (String)this.baseDaoSupport.queryForObject("select name from logi_company where id=?", new StringMapper(), new Object[] { logi_id });
  }

  public Page searchForGuest(int pageNo, int pageSize, String ship_name, String ship_tel)
  {
    String sql = "select * from order where ship_name=? AND (ship_mobile=? OR ship_tel=?) ORDER BY order_id DESC";
    Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, Order.class, new Object[] { ship_name, ship_tel, ship_tel });

    return page;
  }

  public Page listByStatus(int pageNo, int pageSize, int status, int memberid) {
    String filedname = "status";
    if (status == 0)
    {
      filedname = " status!=8 AND pay_status";
    }

    String sql = new StringBuilder().append("select * from order where ").append(filedname).append("=? AND member_id=? ORDER BY order_id DESC").toString();

    Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, Order.class, new Object[] { Integer.valueOf(status), Integer.valueOf(memberid) });

    return page;
  }

  public List<Order> listByStatus(int status, int memberid)
  {
    String filedname = "status";
    if (status == 0)
    {
      filedname = " status!=8 AND pay_status";
    }

    String sql = new StringBuilder().append("select * from order where ").append(filedname).append("=? AND member_id=? ORDER BY order_id DESC").toString();

    return this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(status), Integer.valueOf(memberid) });
  }

  public int getMemberOrderNum(int member_id)
  {
    return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM order WHERE member_id=?", new Object[] { Integer.valueOf(member_id) });
  }

  public Page search(int pageNO, int pageSize, int disabled, String sn, String logi_no, String uname, String ship_name, int status, Integer paystatus)
  {
    StringBuffer sql = new StringBuffer(new StringBuilder().append("select * from ").append(getTableName("order")).append(" where disabled=?  ").toString());

    if (status != -100) {
      if (status == -99)
      {
        sql.append(" and ((payment_type='cod' and status=0 )  or (payment_type!='cod' and status=1 )) ");
      }
      else {
        sql.append(new StringBuilder().append(" and status = ").append(status).append(" ").toString());
      }
    }
    if ((paystatus != null) && (paystatus.intValue() != -100)) {
      sql.append(new StringBuilder().append(" and pay_status = ").append(paystatus).append(" ").toString());
    }

    if (!StringUtil.isEmpty(sn)) {
      sql.append(new StringBuilder().append(" and sn = '").append(sn).append("' ").toString());
    }
    if (!StringUtil.isEmpty(uname)) {
      sql.append(new StringBuilder().append(" and member_id  in ( SELECT  member_id FROM ").append(getTableName("member")).append(" where uname = '").append(uname).append("' )  ").toString());
    }

    if (!StringUtil.isEmpty(ship_name)) {
      sql.append(new StringBuilder().append(" and  ship_name = '").append(ship_name).append("' ").toString());
    }
    if (!StringUtil.isEmpty(logi_no)) {
      sql.append(new StringBuilder().append(" and order_id in (SELECT order_id FROM ").append(getTableName("delivery")).append(" where logi_no = '").append(logi_no).append("') ").toString());
    }

    sql.append(" order by create_time desc ");
    Page page = this.daoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class, new Object[] { Integer.valueOf(disabled) });

    return page;
  }

  public Page search(int pageNO, int pageSize, int disabled, String sn, String logi_no, String uname, String ship_name, int status)
  {
    return search(pageNO, pageSize, disabled, sn, logi_no, uname, ship_name, status, null);
  }

  public Order getNext(String next, Integer orderId, Integer status, int disabled, String sn, String logi_no, String uname, String ship_name)
  {
    StringBuffer sql = new StringBuffer(new StringBuilder().append("select * from ").append(getTableName("order")).append(" where  1=1  ").toString());

    StringBuffer depotsql = new StringBuffer("  ");
    AdminUser adminUser = this.adminUserManager.getCurrentUser();
    if (adminUser.getFounder() != 1)
    {
      boolean isShiper = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_ship"));

      boolean haveAllo = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("allocation"));

      boolean haveOrder = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("order"));

      if ((isShiper) && (!haveAllo) && (!haveOrder)) {
        DepotUser depotUser = (DepotUser)adminUser;
        int depotid = depotUser.getDepotid().intValue();
        depotsql.append(new StringBuilder().append(" and depotid=").append(depotid).append("  ").toString());
      }
    }

    StringBuilder sbsql = new StringBuilder("  ");
    if ((status != null) && (status.intValue() != -100)) {
      sbsql.append(new StringBuilder().append(" and status = ").append(status).append(" ").toString());
    }
    if (!StringUtil.isEmpty(sn)) {
      sbsql.append(new StringBuilder().append(" and sn = '").append(sn.trim()).append("' ").toString());
    }
    if (!StringUtil.isEmpty(uname)) {
      sbsql.append(new StringBuilder().append(" and member_id  in ( SELECT  member_id FROM ").append(getTableName("member")).append(" where uname = '").append(uname).append("' )  ").toString());
    }

    if (!StringUtil.isEmpty(ship_name)) {
      sbsql.append(new StringBuilder().append("  and  ship_name = '").append(ship_name.trim()).append("'  ").toString());
    }
    if (!StringUtil.isEmpty(logi_no)) {
      sbsql.append(new StringBuilder().append("  and order_id in (SELECT order_id FROM ").append(getTableName("delivery")).append(" where logi_no = '").append(logi_no).append("')  ").toString());
    }
    if (next.equals("previous")) {
      sql.append(new StringBuilder().append("  and order_id IN (SELECT CASE WHEN SIGN(order_id - ").append(orderId).append(") < 0 THEN MAX(order_id)  END AS order_id FROM ").append(getTableName("order")).append(" WHERE order_id <> ").append(orderId).append(depotsql.toString()).append(" and disabled=? ").append(sbsql.toString()).append(" GROUP BY SIGN(order_id - ").append(orderId).append(") ORDER BY SIGN(order_id - ").append(orderId).append("))   ").toString());
    }
    else if (next.equals("next")) {
      sql.append(new StringBuilder().append("  and  order_id in (SELECT CASE WHEN SIGN(order_id - ").append(orderId).append(") > 0 THEN MIN(order_id) END AS order_id FROM ").append(getTableName("order")).append(" WHERE order_id <> ").append(orderId).append(depotsql.toString()).append(" and disabled=? ").append(sbsql.toString()).append(" GROUP BY SIGN(order_id - ").append(orderId).append(") ORDER BY SIGN(order_id - ").append(orderId).append("))   ").toString());
    }
    else
    {
      return null;
    }
    sql.append(" order by create_time desc ");

    Order order = (Order)this.daoSupport.queryForObject(sql.toString(), Order.class, new Object[] { Integer.valueOf(disabled) });

    return order;
  }

  private double getOrderTotal(String sessionid)
  {
    List goodsItemList = this.cartManager.listGoods(sessionid);
    double orderTotal = 0.0D;
    if ((goodsItemList != null) && (goodsItemList.size() > 0)) {
      for (int i = 0; i < goodsItemList.size(); i++) {
        CartItem cartItem = (CartItem)goodsItemList.get(i);
        orderTotal += cartItem.getCoupPrice().doubleValue() * cartItem.getNum();
      }
    }
    return orderTotal;
  }

  private OrderItem getOrderItem(Integer itemid)
  {
    return (OrderItem)this.baseDaoSupport.queryForObject("select * from order_items where item_id = ?", OrderItem.class, new Object[] { itemid });
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public boolean delItem(Integer itemid, Integer itemnum) {
    OrderItem item = getOrderItem(itemid);
    Order order = get(item.getOrder_id());
    boolean flag = false;
    int paymentid = order.getPayment_id().intValue();
    int status = order.getStatus().intValue();
    if (((paymentid == 1) || (paymentid == 3) || (paymentid == 4) || (paymentid == 5)) && ((status == 0) || (status == 1) || (status == 2) || (status == 3) || (status == 4))) {
      flag = true;
    }
    if ((paymentid == 2) && ((status == 0) || (status == 9) || (status == 3) || (status == 4))) {
      flag = true;
    }
    if (flag) {
      try {
        if (itemnum.intValue() <= item.getNum().intValue()) {
          Goods goods = this.goodsManager.getGoods(item.getGoods_id());
          double order_amount = order.getOrder_amount().doubleValue();
          double itemprice = item.getPrice().doubleValue() * itemnum.intValue();
          double leftprice = CurrencyUtil.sub(order_amount, itemprice);
          int difpoint = (int)Math.floor(leftprice);
          Double[] dlyprice = this.dlyTypeManager.countPrice(order.getShipping_id(), Double.valueOf(order.getWeight().doubleValue() - goods.getWeight().doubleValue() * itemnum.intValue()), Double.valueOf(leftprice), order.getShip_regionid().toString(), false);
          double sumdlyprice = dlyprice[0].doubleValue();
          this.baseDaoSupport.execute("update order set goods_amount = goods_amount- ?,shipping_amount = ?,order_amount =  ?,weight =  weight - ?,gainedpoint =  ? where order_id = ?", new Object[] { Double.valueOf(itemprice), Double.valueOf(sumdlyprice), Double.valueOf(leftprice), Double.valueOf(goods.getWeight().doubleValue() * itemnum.intValue()), Integer.valueOf(difpoint), order.getOrder_id() });

          this.baseDaoSupport.execute("update freeze_point set mp =?,point =?  where orderid = ? and type = ?", new Object[] { Integer.valueOf(difpoint), Integer.valueOf(difpoint), order.getOrder_id(), "buygoods" });
          if (itemnum.intValue() == item.getNum().intValue())
            this.baseDaoSupport.execute("delete from order_items where item_id = ?", new Object[] { itemid });
          else
            this.baseDaoSupport.execute("update order_items set num = num - ? where item_id = ?", new Object[] { Integer.valueOf(itemnum.intValue()), itemid });
        }
        else
        {
          return false;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }

    return flag;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public boolean saveAddrDetail(String addr, int orderid)
  {
    Order order = get(Integer.valueOf(orderid));
    String oldAddr = order.getShip_addr();
    if ((addr == null) || (StringUtil.isEmpty(addr))) {
      return false;
    }
    this.baseDaoSupport.execute("update order set ship_addr=?  where order_id=?", new Object[] { addr, Integer.valueOf(orderid) });
    AdminUser adminUser = this.adminUserManager.getCurrentUser();
    log(Integer.valueOf(orderid), new StringBuilder().append("收货人详细地址从['").append(oldAddr).append("']修改为['").append(addr).append("']").toString(), null, adminUser.getUsername());
    return true;
  }

  public boolean saveShipInfo(String remark, String ship_day, String ship_name, String ship_tel, String ship_mobile, String ship_zip, int orderid)
  {
    Order order = get(Integer.valueOf(orderid));
    AdminUser adminUser = this.adminUserManager.getCurrentUser();
    try {
      if ((ship_day != null) && (!StringUtil.isEmpty(ship_day))) {
        String oldShip_day = order.getShip_day();
        this.baseDaoSupport.execute("update order set ship_day=?  where order_id=?", new Object[] { ship_day, Integer.valueOf(orderid) });
        if ((remark != null) && (!StringUtil.isEmpty(remark)) && (!remark.equals("undefined"))) {
          StringBuilder sb = new StringBuilder("");
          sb.append("【配送时间：");
          sb.append(remark.trim());
          sb.append("】");
          this.baseDaoSupport.execute(new StringBuilder().append("update order set remark= concat(remark,'").append(sb.toString()).append("')   where order_id=?").toString(), new Object[] { Integer.valueOf(orderid) });
        }
        log(Integer.valueOf(orderid), new StringBuilder().append("收货日期从['").append(oldShip_day).append("']修改为['").append(ship_day).append("']").toString(), null, adminUser.getUsername());
        return true;
      }
      if ((ship_name != null) && (!StringUtil.isEmpty(ship_name))) {
        String oldship_name = order.getShip_name();
        this.baseDaoSupport.execute("update order set ship_name=?  where order_id=?", new Object[] { ship_name, Integer.valueOf(orderid) });
        log(Integer.valueOf(orderid), new StringBuilder().append("收货人姓名从['").append(oldship_name).append("']修改为['").append(ship_name).append("']").toString(), null, adminUser.getUsername());
        return true;
      }
      if ((ship_tel != null) && (!StringUtil.isEmpty(ship_tel))) {
        String oldship_tel = order.getShip_tel();
        this.baseDaoSupport.execute("update order set ship_tel=?  where order_id=?", new Object[] { ship_tel, Integer.valueOf(orderid) });
        log(Integer.valueOf(orderid), new StringBuilder().append("收货人电话从['").append(oldship_tel).append("']修改为['").append(ship_tel).append("']").toString(), null, adminUser.getUsername());
        return true;
      }
      if ((ship_mobile != null) && (!StringUtil.isEmpty(ship_mobile))) {
        String oldship_mobile = order.getShip_mobile();
        this.baseDaoSupport.execute("update order set ship_mobile=?  where order_id=?", new Object[] { ship_mobile, Integer.valueOf(orderid) });
        log(Integer.valueOf(orderid), new StringBuilder().append("收货人手机从['").append(oldship_mobile).append("']修改为['").append(ship_mobile).append("']").toString(), null, adminUser.getUsername());
        return true;
      }
      if ((ship_zip != null) && (!StringUtil.isEmpty(ship_zip))) {
        String oldship_zip = order.getShip_zip();
        this.baseDaoSupport.execute("update order set ship_zip=?  where order_id=?", new Object[] { ship_zip, Integer.valueOf(orderid) });
        log(Integer.valueOf(orderid), new StringBuilder().append("收货人邮编从['").append(oldship_zip).append("']修改为['").append(ship_zip).append("']").toString(), null, adminUser.getUsername());
        return true;
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
    }return false;
  }

  public void updatePayMethod(int orderid, int payid, String paytype, String payname)
  {
    this.baseDaoSupport.execute("update order set payment_id=?,payment_type=?,payment_name=? where order_id=?", new Object[] { Integer.valueOf(payid), paytype, payname, Integer.valueOf(orderid) });
  }

  public boolean checkProInOrder(int productid)
  {
    String sql = "select count(0) from order_items where product_id=?";
    return this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(productid) }) > 0;
  }

  public boolean checkGoodsInOrder(int goodsid)
  {
    String sql = "select count(0) from order_items where goods_id=?";
    return this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(goodsid) }) > 0;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.OrderManager
 * JD-Core Version:    0.6.1
 */