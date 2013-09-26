package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.service.IRegionsManager;
import com.enation.app.shop.component.coupon.MemberCoupon;
import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
import com.enation.app.shop.core.service.IFreeOfferManager;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IOrderFlowManager;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.app.shop.core.service.IOrderReportManager;
import com.enation.app.shop.core.service.IPromotionManager;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.action.WWAction;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class OrderAction extends WWAction
{
  private String sn;
  private String logi_no;
  private String uname;
  private String ship_name;
  private int status;
  private Integer paystatus;
  private int shipping_id;
  private Integer orderId;
  private String searchKey;
  private String searchValue;
  private String order;
  private IOrderManager orderManager;
  private IMemberManager memberManager;
  private IRegionsManager regionsManager;
  private IOrderFlowManager orderFlowManager;
  private IFreeOfferManager freeOfferManager;
  private IOrderReportManager orderReportManager;
  private IPromotionManager promotionManager;
  private OrderPluginBundle orderPluginBundle;
  protected Map<Integer, String> pluginTabs;
  protected Map<Integer, String> pluginHtmls;
  private Order ord;
  private List itemList;
  private List payLogList;
  private List refundList;
  private List shipLogList;
  private List reshipLogList;
  private List chshipLogList;
  private List logList;
  private List provinceList;
  private List orderGiftList;
  private List pmtList;
  private Integer[] id;
  private int giftCount;
  private double price;
  private double shipmoney;
  private String remark;
  private Integer state;
  private Map ordermap;
  private List goodIdS;
  private Member member;
  private String refuse_reson;
  private String start;
  private String end;
  private String next;
  private String alert_null;
  private List<Delivery> deliveryList;
  private String addr;
  private String ship_day;
  private String ship_tel;
  private String ship_mobile;
  private String ship_zip;
  private MemberCoupon memberCoupon;

  public Integer getPaystatus()
  {
    return this.paystatus;
  }

  public void setPaystatus(Integer paystatus) {
    this.paystatus = paystatus;
  }

  public String getShip_day()
  {
    return this.ship_day;
  }

  public void setShip_day(String ship_day) {
    this.ship_day = ship_day;
  }

  public String getShip_tel() {
    return this.ship_tel;
  }

  public void setShip_tel(String ship_tel) {
    this.ship_tel = ship_tel;
  }

  public String getShip_mobile() {
    return this.ship_mobile;
  }

  public void setShip_mobile(String ship_mobile) {
    this.ship_mobile = ship_mobile;
  }

  public String getShip_zip() {
    return this.ship_zip;
  }

  public void setShip_zip(String ship_zip) {
    this.ship_zip = ship_zip;
  }

  public String getAddr() {
    return this.addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }

  public double getShipmoney() {
    return this.shipmoney;
  }

  public void setShipmoney(double shipmoney) {
    this.shipmoney = shipmoney;
  }

  public String getAlert_null() {
    return this.alert_null;
  }

  public void setAlert_null(String alert_null) {
    this.alert_null = alert_null;
  }

  public String getNext() {
    return this.next;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public List getGoodIdS()
  {
    return this.goodIdS;
  }

  public void setGoodIdS(List goodIdS) {
    this.goodIdS = goodIdS;
  }

  public int getShipping_id()
  {
    return this.shipping_id;
  }

  public void setShipping_id(int shipping_id) {
    this.shipping_id = shipping_id;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getSn() {
    return this.sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getLogi_no() {
    return this.logi_no;
  }

  public void setLogi_no(String logi_no) {
    this.logi_no = logi_no;
  }

  public String getUname() {
    return this.uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public String getShip_name() {
    return this.ship_name;
  }

  public void setShip_name(String ship_name) {
    this.ship_name = ship_name;
  }

  public String savePrice() {
    try {
      this.orderManager.savePrice(this.price, this.orderId.intValue());
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e);
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String saveShipMoney()
  {
    try {
      double price = this.orderManager.saveShipmoney(getShipmoney(), this.orderId.intValue());
      this.json = ("{result:1,price:" + price + "}");
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e);
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String saveAddrDetail() {
    try {
      boolean addr = this.orderManager.saveAddrDetail(getAddr(), getOrderId().intValue());
      if (addr)
        this.json = "{result:1,message:'成功！'}";
      else
        this.json = "{result:0,message:'失败！'}";
    }
    catch (RuntimeException e)
    {
      this.logger.error(e.getMessage(), e);
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String saveShipInfo() {
    try {
      boolean addr = this.orderManager.saveShipInfo(this.remark, this.ship_day, this.ship_name, this.ship_tel, this.ship_mobile, this.ship_zip, getOrderId().intValue());
      if (addr)
        this.json = "{result:1,message:'成功！'}";
      else
        this.json = "{result:0,message:'失败！'}";
    }
    catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e);
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String list()
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    setStatus(-100);
    String optype = request.getParameter("optype");
    if ((optype != null) && ("monitor".equals(optype)))
    {
      String depotid = request.getParameter("depotid");
      depotid = StringUtil.isEmpty(depotid) ? "0" : depotid;
      String status = request.getParameter("status");
      this.webpage = this.orderManager.list(getPage(), getPageSize(), Integer.parseInt(status), Integer.parseInt(depotid), this.order);
      return "list";
    }
    if (this.state != null) {
      this.searchKey = "status";
      this.searchValue = ("" + this.state);
    }
    this.webpage = this.orderManager.list(getPage(), getPageSize(), 0, this.searchKey, this.searchValue, this.order);

    return "list";
  }

  public String trash_list() {
    this.webpage = this.orderManager.list(getPage(), getPageSize(), 1, this.searchKey, this.searchValue, this.order);

    return "trash_list";
  }

  public String listbyship() {
    this.webpage = this.orderManager.listbyshipid(getPage(), getPageSize(), 0, this.shipping_id, this.order);

    return "list";
  }

  public String listConfirmPay()
  {
    this.webpage = this.orderManager.listConfirmPay(getPage(), getPageSize(), this.order);
    return "list";
  }

  public String saveRemark()
  {
    this.ord = this.orderManager.get(this.orderId);
    this.ord.setRemark(this.remark);
    try {
      this.orderManager.edit(this.ord);
      this.json = "{result:0,message:'修改成功'}";
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:1,message:\"修改失败：" + e.getMessage() + "\"}");
    }
    return "json_message";
  }

  public String delete() {
    try {
      this.orderManager.delete(this.id);
      this.json = "{result:0,message:'订单删除成功'}";
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:1,message:\"订单删除失败：" + e.getMessage() + "\"}");
    }

    return "json_message";
  }

  public String revert()
  {
    try {
      this.orderManager.revert(this.id);
      this.json = "{result:0,message:'订单还原成功'}";
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:1,message:\"订单还原失败：" + e.getMessage() + "\"}");
    }

    return "json_message";
  }

  public String clean()
  {
    try {
      this.orderManager.clean(this.id);
      this.json = "{result:0,message:'订单清除成功'}";
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:1,message:\"订单清除失败：" + e.getMessage() + "\"}");
    }

    return "json_message";
  }

  public String pmt()
  {
    this.pmtList = this.promotionManager.listOrderPmt(this.orderId);
    return "pmt";
  }

  public String complete()
  {
    try
    {
      this.orderFlowManager.complete(this.orderId);
      Order order = this.orderManager.get(this.orderId);
      this.json = ("{result:1,message:'订单[" + order.getSn() + "]成功标记为完成状态',orderStatus:" + order.getStatus() + "}");
    }
    catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:0,message:\"订单完成失败：" + e.getMessage() + "\"}");
    }

    return "json_message";
  }

  public String cancel()
  {
    try
    {
      this.orderFlowManager.cancel(this.orderId);
      Order order = this.orderManager.get(this.orderId);
      this.json = ("{result:1,message:'订单[" + order.getSn() + "]成功作废',orderStatus:" + order.getStatus() + "}");
    }
    catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:0,message:\"订单作废失败：" + e.getMessage() + "\"}");
    }

    return "json_message";
  }

  public String confirmOrder()
  {
    try
    {
      this.orderFlowManager.confirmOrder(this.orderId);
      Order order = this.orderManager.get(this.orderId);
      this.orderFlowManager.addCodPaymentLog(order);
      this.json = ("{result:1,message:'订单[" + order.getSn() + "]成功确认',orderStatus:" + order.getStatus() + "}");
    }
    catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:0,message:\"订单确认失败：" + e.getMessage() + "\"}");
    }
    return "json_message";
  }

  public String toExport()
  {
    return "export";
  }

  public String export()
  {
    try {
      Date startDate = null;
      Date endDate = null;

      if (!StringUtil.isEmpty(this.start)) {
        startDate = DateUtil.toDate(this.start, "yyyy-MM-dd");
      }
      if (!StringUtil.isEmpty(this.end)) {
        endDate = DateUtil.toDate(this.end, "yyyy-MM-dd");
      }
      String url = this.orderManager.export(startDate, endDate);
      this.json = ("{result:1,url:'" + url + "'}");
    } catch (RuntimeException e) {
      this.logger.error("导出订单出错", e);
      showErrorJson(e.getMessage());
    }
    return "json_message";
  }

  public String detail()
  {
    if (this.ship_name != null) this.ship_name = StringUtil.toUTF8(this.ship_name);
    if (this.uname != null) this.uname = StringUtil.toUTF8(this.uname);
    this.ord = this.orderManager.get(this.orderId);
    this.provinceList = this.regionsManager.listProvince();

    this.pluginTabs = this.orderPluginBundle.getTabList(this.ord);
    this.pluginHtmls = this.orderPluginBundle.getDetailHtml(this.ord);

    return "detail";
  }

  public String nextDetail()
  {
    if (this.orderManager.getNext(this.next, this.orderId, Integer.valueOf(this.status), 0, this.sn, this.logi_no, this.uname, this.ship_name) == null) {
      this.alert_null = "kong";
    }
    this.ord = (this.orderManager.getNext(this.next, this.orderId, Integer.valueOf(this.status), 0, this.sn, this.logi_no, this.uname, this.ship_name) == null ? this.orderManager.get(this.orderId) : this.orderManager.getNext(this.next, this.orderId, Integer.valueOf(this.status), 0, this.sn, this.logi_no, this.uname, this.ship_name));
    this.orderId = (this.ord == null ? this.orderId : this.ord.getOrder_id());
    this.provinceList = this.regionsManager.listProvince();

    this.pluginTabs = this.orderPluginBundle.getTabList(this.ord);
    this.pluginHtmls = this.orderPluginBundle.getDetailHtml(this.ord);

    return "detail";
  }

  public String search()
  {
    this.webpage = this.orderManager.search(getPage(), getPageSize(), 0, this.sn, this.logi_no, this.uname, this.ship_name, this.status, this.paystatus);
    return "list";
  }

  public String saveAdminRemark() {
    String encoding = EopSetting.ENCODING;
    if ((this.remark != null) && (!StringUtil.isEmpty(encoding))) {
      this.remark = StringUtil.to(this.remark, encoding);
    }
    this.ord = this.orderManager.get(this.orderId);
    this.ord.setAdmin_remark(this.remark);
    try {
      this.orderManager.edit(this.ord);
      this.json = "{result:0,message:'修改成功'}";
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:1,message:\"修改失败：" + e.getMessage() + "\"}");
    }
    return "json_message";
  }

  public IOrderManager getOrderManager()
  {
    return this.orderManager;
  }

  public void setOrderManager(IOrderManager orderManager) {
    this.orderManager = orderManager;
  }

  public String getSearchKey() {
    return this.searchKey;
  }

  public void setSearchKey(String searchKey) {
    this.searchKey = searchKey;
  }

  public String getSearchValue() {
    return this.searchValue;
  }

  public void setSearchValue(String searchValue) {
    this.searchValue = searchValue;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public Order getOrd() {
    return this.ord;
  }

  public void setOrd(Order ord) {
    this.ord = ord;
  }

  public Integer getOrderId() {
    return this.orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public List getItemList() {
    return this.itemList;
  }

  public void setItemList(List itemList) {
    this.itemList = itemList;
  }

  public IMemberManager getMemberManager() {
    return this.memberManager;
  }

  public void setMemberManager(IMemberManager memberManager) {
    this.memberManager = memberManager;
  }

  public Member getMember() {
    return this.member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public List getLogList() {
    return this.logList;
  }

  public void setLogList(List logList) {
    this.logList = logList;
  }

  public IRegionsManager getRegionsManager() {
    return this.regionsManager;
  }

  public void setRegionsManager(IRegionsManager regionsManager) {
    this.regionsManager = regionsManager;
  }

  public List getProvinceList() {
    return this.provinceList;
  }

  public void setProvinceList(List provinceList) {
    this.provinceList = provinceList;
  }

  public IOrderFlowManager getOrderFlowManager() {
    return this.orderFlowManager;
  }

  public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
    this.orderFlowManager = orderFlowManager;
  }

  public Integer[] getId() {
    return this.id;
  }

  public void setId(Integer[] id) {
    this.id = id;
  }

  public Map getOrdermap() {
    return this.ordermap;
  }

  public void setOrdermap(Map ordermap) {
    this.ordermap = ordermap;
  }

  public IFreeOfferManager getFreeOfferManager() {
    return this.freeOfferManager;
  }

  public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
    this.freeOfferManager = freeOfferManager;
  }

  public List getOrderGiftList() {
    return this.orderGiftList;
  }

  public void setOrderGiftList(List orderGiftList) {
    this.orderGiftList = orderGiftList;
  }

  public int getGiftCount() {
    return this.giftCount;
  }

  public void setGiftCount(int giftCount) {
    this.giftCount = giftCount;
  }

  public IOrderReportManager getOrderReportManager() {
    return this.orderReportManager;
  }

  public void setOrderReportManager(IOrderReportManager orderReportManager) {
    this.orderReportManager = orderReportManager;
  }

  public List getPayLogList() {
    return this.payLogList;
  }

  public void setPayLogList(List payLogList) {
    this.payLogList = payLogList;
  }

  public List getRefundList() {
    return this.refundList;
  }

  public void setRefundList(List refundList) {
    this.refundList = refundList;
  }

  public List getShipLogList() {
    return this.shipLogList;
  }

  public void setShipLogList(List shipLogList) {
    this.shipLogList = shipLogList;
  }

  public List getReshipLogList() {
    return this.reshipLogList;
  }

  public void setReshipLogList(List reshipLogList) {
    this.reshipLogList = reshipLogList;
  }

  public IPromotionManager getPromotionManager() {
    return this.promotionManager;
  }

  public void setPromotionManager(IPromotionManager promotionManager) {
    this.promotionManager = promotionManager;
  }

  public List getPmtList() {
    return this.pmtList;
  }

  public void setPmtList(List pmtList) {
    this.pmtList = pmtList;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Integer getState() {
    return this.state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getRefuse_reson()
  {
    return this.refuse_reson;
  }

  public void setRefuse_reson(String refuseReson) {
    this.refuse_reson = refuseReson;
  }

  public List getChshipLogList() {
    return this.chshipLogList;
  }

  public void setChshipLogList(List chshipLogList) {
    this.chshipLogList = chshipLogList;
  }

  public String getStart() {
    return this.start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return this.end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public MemberCoupon getMemberCoupon() {
    return this.memberCoupon;
  }

  public void setMemberCoupon(MemberCoupon memberCoupon) {
    this.memberCoupon = memberCoupon;
  }

  public List<Delivery> getDeliveryList() {
    return this.deliveryList;
  }

  public void setDeliveryList(List<Delivery> deliveryList) {
    this.deliveryList = deliveryList;
  }

  public OrderPluginBundle getOrderPluginBundle() {
    return this.orderPluginBundle;
  }

  public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
    this.orderPluginBundle = orderPluginBundle;
  }

  public Map<Integer, String> getPluginTabs() {
    return this.pluginTabs;
  }

  public void setPluginTabs(Map<Integer, String> pluginTabs) {
    this.pluginTabs = pluginTabs;
  }

  public Map<Integer, String> getPluginHtmls() {
    return this.pluginHtmls;
  }

  public void setPluginHtmls(Map<Integer, String> pluginHtmls) {
    this.pluginHtmls = pluginHtmls;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.OrderAction
 * JD-Core Version:    0.6.1
 */