package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.Depot;
import com.enation.app.shop.core.model.DlyCenter;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.PrintTmpl;
import com.enation.app.shop.core.service.IDepotManager;
import com.enation.app.shop.core.service.IDlyCenterManager;
import com.enation.app.shop.core.service.IFreeOfferManager;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IOrderAllocationManager;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.app.shop.core.service.IPrintTmplManager;
import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopUser;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.StringUtil;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderPrintAction extends WWAction
{
  private IOrderManager orderManager;
  private IFreeOfferManager freeOfferManager;
  private IMemberManager memberManager;
  private IDlyCenterManager dlyCenterManager;
  private IPrintTmplManager printTmplManager;
  private Member member;
  private Integer orderId;
  private Order ord;
  private List itemList;
  private Map ordermap;
  private List dlyCenterList;
  private Integer dly_center_id;
  private DlyCenter dlyCenter;
  private List printTmplList;
  private Integer prt_tmpl_id;
  private PrintTmpl printTmpl;
  private String saveAddr;
  private EopSite site;
  private EopUser user;
  private IUserManager userManager;
  private long all_time;
  private String all_depotname;
  private IOrderAllocationManager orderAllocationManager;
  private IDepotManager depotManager;
  private List allocationList;
  private String printXml;
  private String printData;
  private String printBg;

  public List getAllocationList()
  {
    return this.allocationList;
  }

  public void setAllocationList(List allocationList) {
    this.allocationList = allocationList;
  }

  public long getAll_time() {
    return this.all_time;
  }

  public void setAll_time(long all_time) {
    this.all_time = all_time;
  }

  public String getAll_depotname() {
    return this.all_depotname;
  }

  public void setAll_depotname(String all_depotname) {
    this.all_depotname = all_depotname;
  }

  public IOrderAllocationManager getOrderAllocationManager() {
    return this.orderAllocationManager;
  }

  public void setOrderAllocationManager(IOrderAllocationManager orderAllocationManager)
  {
    this.orderAllocationManager = orderAllocationManager;
  }

  public IDepotManager getDepotManager() {
    return this.depotManager;
  }

  public void setDepotManager(IDepotManager depotManager) {
    this.depotManager = depotManager;
  }

  public String order_prnt() {
    this.site = EopContext.getContext().getCurrentSite();
    this.user = this.userManager.get(this.site.getUserid());
    this.itemList = this.orderManager.listGoodsItems(this.orderId);
    this.ord = this.orderManager.get(this.orderId);

    if (this.ord.getMember_id() != null) {
      this.member = this.memberManager.get(this.ord.getMember_id());
      this.ordermap = this.orderManager.mapOrderByMemberId(this.ord.getMember_id().intValue());
    }

    return "order_prnt";
  }

  public String delivery_prnt() {
    this.site = EopContext.getContext().getCurrentSite();
    this.user = this.userManager.get(this.site.getUserid());

    this.all_time = this.orderManager.get(this.orderId).getAllocation_time().longValue();
    this.all_depotname = this.depotManager.get(this.orderManager.get(this.orderId).getDepotid().intValue()).getName();
    this.allocationList = this.orderAllocationManager.listAllocation(this.orderId.intValue());

    this.ord = this.orderManager.get(this.orderId);

    if (this.ord.getMember_id() != null) {
      this.member = this.memberManager.get(this.ord.getMember_id());
    }
    return "delivery_prnt";
  }

  public String global_prnt() {
    this.site = EopContext.getContext().getCurrentSite();
    this.user = this.userManager.get(this.site.getUserid());

    this.itemList = this.orderAllocationManager.listAllocation(this.orderId.intValue());
    this.ord = this.orderManager.get(this.orderId);
    if (this.ord.getMember_id() != null) {
      this.member = this.memberManager.get(this.ord.getMember_id());
      this.ordermap = this.orderManager.mapOrderByMemberId(this.ord.getMember_id().intValue());
    }

    return "global_prnt";
  }

  public String ship_prnt_step1() {
    this.ord = this.orderManager.get(this.orderId);
    this.dlyCenterList = this.dlyCenterManager.list();
    this.printTmplList = this.printTmplManager.listCanUse();
    return "ship_prnt_step1";
  }

  public String ship_prnt_step2() throws UnsupportedEncodingException {
    this.site = EopContext.getContext().getCurrentSite();
    this.user = this.userManager.get(this.site.getUserid());
    Order order = this.orderManager.get(this.orderId);
    order.setShip_addr(this.ord.getShip_addr());
    order.setShip_name(this.ord.getShip_name());
    order.setShipping_area(this.ord.getShipping_area());
    order.setShip_zip(this.ord.getShip_zip());
    order.setShip_mobile(this.ord.getShip_mobile());
    order.setShip_tel(this.ord.getShip_tel());
    order.setRemark(this.ord.getRemark());
    if (StringUtil.equals(this.saveAddr, "1")) {
      this.orderManager.edit(order);
    }
    this.dlyCenter = this.dlyCenterManager.get(this.dly_center_id);
    this.printTmpl = this.printTmplManager.get(this.prt_tmpl_id.intValue());

    this.printXml = this.printTmpl.getPrt_tmpl_data();
    if (this.printXml != null) {
      this.printXml = this.printXml.replaceAll("picpos", "picposition");
      this.printXml = this.printXml.replaceAll("letterspace", "fontspace");
      this.printXml = this.printXml.replaceAll("items", "item");
      this.printXml = this.printXml.replaceAll("bold", "border");
    }

    this.printBg = this.printTmpl.getBgimage();
    this.printData = processTmplData(order, this.dlyCenter);

    return "ship_prnt_step2";
  }

  private String processTmplData(Order order, DlyCenter dlyCenter) {
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int year = cal.get(1);
    int month = cal.get(2) + 1;
    int day = cal.get(5);
    String ship_area = order.getShipping_area();
    String ship_province = "";
    String ship_city = "";
    String ship_region = "";
    if (!StringUtil.isEmpty(ship_area)) {
      String[] area_ar = ship_area.split("-");
      if (area_ar.length >= 1) {
        ship_province = area_ar[0];
      }
      if (area_ar.length >= 2) {
        ship_city = area_ar[1];
      }
      if (area_ar.length >= 3) {
        ship_region = area_ar[2];
      }

    }

    StringBuffer result = new StringBuffer();
    result.append("<data>");
    result.append("<order_id>" + order.getSn() + "</order_id>");
    result.append("<order_count>0</order_count>");
    result.append("<ship_time><![CDATA[" + order.getShip_day() + order.getShip_time() + " ]]></ship_time>");
    result.append("<order_price>" + order.getOrder_amount() + "</order_price>");
    result.append("<order_weight>" + order.getWeight() + "</order_weight>");
    result.append("<ship_name><![CDATA[" + order.getShip_name() + "]]></ship_name>");
    result.append("<ship_zip>" + order.getShip_zip() + "</ship_zip>");
    result.append("<ship_addr><![CDATA[" + order.getShip_addr() + "]]></ship_addr>");
    result.append("<ship_mobile>" + order.getShip_mobile() + "</ship_mobile>");
    result.append("<ship_tel><![CDATA[" + order.getShip_tel() + "]]></ship_tel>");
    result.append("<order_memo><![CDATA[" + order.getRemark() + "]]></order_memo>");
    result.append("<shop_name><![CDATA[" + EopContext.getContext().getCurrentSite().getSitename() + "]]></shop_name>");
    result.append("<dly_name><![CDATA[" + dlyCenter.getUname() + "]]></dly_name>");
    result.append("<ship_province><![CDATA[" + ship_province + "]]></ship_province>");
    result.append("<ship_city><![CDATA[" + ship_city + "]]></ship_city>");
    result.append("<ship_region><![CDATA[" + ship_region + "]]></ship_region>");
    result.append("<dly_province><![CDATA[" + dlyCenter.getProvince() + "]]></dly_province>");
    result.append("<dly_city><![CDATA[" + dlyCenter.getCity() + "]]></dly_city>");
    result.append("<dly_region><![CDATA[" + dlyCenter.getRegion() + "]]></dly_region>");
    result.append("<dly_address><![CDATA[" + dlyCenter.getAddress() + "]]></dly_address>");
    result.append("<dly_tel><![CDATA[" + dlyCenter.getPhone() + "]]></dly_tel>");
    result.append("<dly_mobile>" + dlyCenter.getCellphone() + "</dly_mobile>");
    result.append("<dly_zip>" + dlyCenter.getZip() + "</dly_zip>");
    result.append("<date_y>" + String.valueOf(year) + "</date_y>");
    result.append("<date_m>" + String.valueOf(month) + "</date_m>");
    result.append("<date_d>" + String.valueOf(day) + "</date_d>");
    result.append("</data>");

    return result.toString();
  }

  public IOrderManager getOrderManager() {
    return this.orderManager;
  }

  public void setOrderManager(IOrderManager orderManager) {
    this.orderManager = orderManager;
  }

  public IFreeOfferManager getFreeOfferManager() {
    return this.freeOfferManager;
  }

  public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
    this.freeOfferManager = freeOfferManager;
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

  public Integer getOrderId() {
    return this.orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public Order getOrd() {
    return this.ord;
  }

  public void setOrd(Order ord) {
    this.ord = ord;
  }

  public List getItemList() {
    return this.itemList;
  }

  public void setItemList(List itemList) {
    this.itemList = itemList;
  }

  public Map getOrdermap()
  {
    return this.ordermap;
  }

  public void setOrdermap(Map ordermap) {
    this.ordermap = ordermap;
  }

  public List getDlyCenterList() {
    return this.dlyCenterList;
  }

  public void setDlyCenterList(List dlyCenterList) {
    this.dlyCenterList = dlyCenterList;
  }

  public IDlyCenterManager getDlyCenterManager() {
    return this.dlyCenterManager;
  }

  public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) {
    this.dlyCenterManager = dlyCenterManager;
  }

  public IPrintTmplManager getPrintTmplManager() {
    return this.printTmplManager;
  }

  public void setPrintTmplManager(IPrintTmplManager printTmplManager) {
    this.printTmplManager = printTmplManager;
  }

  public List getPrintTmplList() {
    return this.printTmplList;
  }

  public void setPrintTmplList(List printTmplList) {
    this.printTmplList = printTmplList;
  }

  public PrintTmpl getPrintTmpl() {
    return this.printTmpl;
  }

  public void setPrintTmpl(PrintTmpl printTmpl) {
    this.printTmpl = printTmpl;
  }

  public Integer getDly_center_id() {
    return this.dly_center_id;
  }

  public void setDly_center_id(Integer dlyCenterId) {
    this.dly_center_id = dlyCenterId;
  }

  public DlyCenter getDlyCenter() {
    return this.dlyCenter;
  }

  public void setDlyCenter(DlyCenter dlyCenter) {
    this.dlyCenter = dlyCenter;
  }

  public Integer getPrt_tmpl_id() {
    return this.prt_tmpl_id;
  }

  public void setPrt_tmpl_id(Integer prtTmplId) {
    this.prt_tmpl_id = prtTmplId;
  }

  public String getSaveAddr() {
    return this.saveAddr;
  }

  public void setSaveAddr(String saveAddr) {
    this.saveAddr = saveAddr;
  }

  public EopSite getSite() {
    return this.site;
  }

  public void setSite(EopSite site) {
    this.site = site;
  }

  public EopUser getUser() {
    return this.user;
  }

  public void setUser(EopUser user) {
    this.user = user;
  }

  public IUserManager getUserManager() {
    return this.userManager;
  }

  public void setUserManager(IUserManager userManager) {
    this.userManager = userManager;
  }

  public String getPrintXml() {
    return this.printXml;
  }

  public void setPrintXml(String printXml) {
    this.printXml = printXml;
  }

  public String getPrintData() {
    return this.printData;
  }

  public void setPrintData(String printData) {
    this.printData = printData;
  }

  public String getPrintBg() {
    return this.printBg;
  }

  public void setPrintBg(String printBg) {
    this.printBg = printBg;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.OrderPrintAction
 * JD-Core Version:    0.6.1
 */