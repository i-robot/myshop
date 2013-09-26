package com.enation.app.shop.core.model;

import com.enation.app.shop.core.model.support.OrderPrice;
import com.enation.app.shop.core.service.OrderStatus;
import com.enation.framework.database.DynamicField;
import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import com.enation.framework.util.CurrencyUtil;
import java.io.Serializable;

public class Order extends DynamicField
  implements Serializable
{
  private Integer order_id;
  private String sn;
  private Integer member_id;
  private Integer status;
  private Integer pay_status;
  private Integer ship_status;
  private String shipStatus;
  private String payStatus;
  private String orderStatus;
  private Integer regionid;
  private Integer shipping_id;
  private String shipping_type;
  private String shipping_area;
  private String goods;
  private Long create_time;
  private String ship_name;
  private String ship_addr;
  private String ship_zip;
  private String ship_email;
  private String ship_mobile;
  private String ship_tel;
  private String ship_day;
  private String ship_time;
  private Integer is_protect;
  private Double protect_price;
  private Double goods_amount;
  private Double shipping_amount;
  private Double discount;
  private Double order_amount;
  private Double weight;
  private Double paymoney;
  private String remark;
  private Integer disabled;
  private Integer payment_id;
  private String payment_name;
  private String payment_type;
  private Integer goods_num;
  private int gainedpoint;
  private int consumepoint;
  private Integer depotid;
  private String cancel_reason;
  private int sale_cmpl;
  private Integer sale_cmpl_time;
  private Integer ship_provinceid;
  private Integer ship_cityid;
  private Integer ship_regionid;
  private Integer signing_time;
  private String the_sign;
  private Long allocation_time;
  private String admin_remark;
  private OrderPrice orderprice;

  public Long getAllocation_time()
  {
    return this.allocation_time;
  }

  public void setAllocation_time(Long allocation_time) {
    this.allocation_time = allocation_time;
  }

  public Integer getGoods_num() {
    return this.goods_num;
  }

  public void setGoods_num(Integer goodsNum) {
    this.goods_num = goodsNum;
  }

  public Long getCreate_time() {
    return this.create_time;
  }

  public void setCreate_time(Long create_time) {
    this.create_time = create_time;
  }

  public String getGoods() {
    return this.goods;
  }

  public void setGoods(String goods) {
    this.goods = goods;
  }

  public Double getGoods_amount() {
    return this.goods_amount;
  }

  public void setGoods_amount(Double goods_amount) {
    this.goods_amount = goods_amount;
  }

  public Integer getIs_protect() {
    this.is_protect = Integer.valueOf(this.is_protect == null ? 0 : this.is_protect.intValue());
    return this.is_protect;
  }

  public void setIs_protect(Integer is_protect) {
    this.is_protect = is_protect;
  }

  public Integer getMember_id() {
    return this.member_id;
  }

  public void setMember_id(Integer member_id) {
    this.member_id = member_id;
  }

  public Double getOrder_amount()
  {
    return Double.valueOf(this.order_amount == null ? 0.0D : this.order_amount.doubleValue());
  }

  public void setOrder_amount(Double order_amount) {
    this.order_amount = order_amount;
  }

  @PrimaryKeyField
  public Integer getOrder_id() {
    return this.order_id;
  }

  public void setOrder_id(Integer order_id) {
    this.order_id = order_id;
  }

  public Integer getPay_status() {
    return this.pay_status;
  }

  public void setPay_status(Integer pay_status) {
    this.pay_status = pay_status;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getShip_addr() {
    return this.ship_addr;
  }

  public void setShip_addr(String ship_addr) {
    this.ship_addr = ship_addr;
  }

  public String getShip_day() {
    return this.ship_day;
  }

  public void setShip_day(String ship_day) {
    this.ship_day = ship_day;
  }

  public String getShip_email() {
    return this.ship_email;
  }

  public void setShip_email(String ship_email) {
    this.ship_email = ship_email;
  }

  public String getShip_mobile() {
    return this.ship_mobile;
  }

  public void setShip_mobile(String ship_mobile) {
    this.ship_mobile = ship_mobile;
  }

  public String getShip_name() {
    return this.ship_name;
  }

  public void setShip_name(String ship_name) {
    this.ship_name = ship_name;
  }

  public Integer getShip_status() {
    return this.ship_status;
  }

  public void setShip_status(Integer ship_status) {
    this.ship_status = ship_status;
  }

  public String getShip_tel() {
    return this.ship_tel;
  }

  public void setShip_tel(String ship_tel) {
    this.ship_tel = ship_tel;
  }

  public String getShip_time() {
    return this.ship_time;
  }

  public void setShip_time(String ship_time) {
    this.ship_time = ship_time;
  }

  public String getShip_zip() {
    return this.ship_zip;
  }

  public void setShip_zip(String ship_zip) {
    this.ship_zip = ship_zip;
  }

  public Double getShipping_amount() {
    return this.shipping_amount;
  }

  public void setShipping_amount(Double shipping_amount) {
    this.shipping_amount = shipping_amount;
  }

  public String getShipping_area() {
    return this.shipping_area;
  }

  public void setShipping_area(String shipping_area) {
    this.shipping_area = shipping_area;
  }

  public Integer getShipping_id() {
    return this.shipping_id;
  }

  public void setShipping_id(Integer shipping_id) {
    this.shipping_id = shipping_id;
  }

  public String getShipping_type() {
    return this.shipping_type;
  }

  public void setShipping_type(String shipping_type) {
    this.shipping_type = shipping_type;
  }

  public String getSn() {
    return this.sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Double getWeight() {
    return this.weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public Double getProtect_price() {
    return this.protect_price;
  }

  public void setProtect_price(Double protect_price) {
    this.protect_price = protect_price;
  }

  public Integer getDisabled() {
    return this.disabled;
  }

  public void setDisabled(Integer disabled) {
    this.disabled = disabled;
  }

  public Integer getPayment_id() {
    return this.payment_id;
  }

  public void setPayment_id(Integer payment_id) {
    this.payment_id = payment_id;
  }

  public String getPayment_name() {
    return this.payment_name;
  }

  public void setPayment_name(String payment_name) {
    this.payment_name = payment_name;
  }

  public Double getPaymoney() {
    return Double.valueOf(this.paymoney == null ? 0.0D : this.paymoney.doubleValue());
  }

  public void setPaymoney(Double paymoney) {
    this.paymoney = paymoney;
  }

  public int getGainedpoint() {
    return this.gainedpoint;
  }

  public void setGainedpoint(int gainedpoint) {
    this.gainedpoint = gainedpoint;
  }

  public int getConsumepoint() {
    return this.consumepoint;
  }

  public void setConsumepoint(int consumepoint) {
    this.consumepoint = consumepoint;
  }

  @NotDbField
  public Integer getRegionid() {
    return this.regionid;
  }

  public void setRegionid(Integer regionid) {
    this.regionid = regionid;
  }

  @NotDbField
  public String getShipStatus()
  {
    this.shipStatus = OrderStatus.getShipStatusText(this.ship_status.intValue());
    return this.shipStatus;
  }

  public void setShipStatus(String shipStatus) {
    this.shipStatus = shipStatus;
  }

  @NotDbField
  public String getPayStatus()
  {
    this.payStatus = OrderStatus.getPayStatusText(this.pay_status.intValue());

    return this.payStatus;
  }

  public void setPayStatus(String payStatus) {
    this.payStatus = payStatus;
  }

  @NotDbField
  public String getOrderStatus() {
    this.orderStatus = OrderStatus.getOrderStatusText(this.status.intValue());
    return this.orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getPayment_type() {
    return this.payment_type;
  }

  public void setPayment_type(String paymentType) {
    this.payment_type = paymentType;
  }

  public Double getDiscount() {
    return this.discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public Integer getDepotid()
  {
    return this.depotid;
  }

  public void setDepotid(Integer depotid) {
    this.depotid = depotid;
  }

  public String getCancel_reason() {
    return this.cancel_reason;
  }

  public void setCancel_reason(String cancel_reason) {
    this.cancel_reason = cancel_reason;
  }

  public int getSale_cmpl() {
    return this.sale_cmpl;
  }

  public void setSale_cmpl(int sale_cmpl) {
    this.sale_cmpl = sale_cmpl;
  }

  public Integer getShip_provinceid()
  {
    return this.ship_provinceid;
  }

  public void setShip_provinceid(Integer ship_provinceid) {
    this.ship_provinceid = ship_provinceid;
  }

  public Integer getShip_cityid() {
    return this.ship_cityid;
  }

  public void setShip_cityid(Integer ship_cityid) {
    this.ship_cityid = ship_cityid;
  }

  public Integer getShip_regionid() {
    return this.ship_regionid;
  }

  public void setShip_regionid(Integer ship_regionid) {
    this.ship_regionid = ship_regionid;
  }

  public Integer getSale_cmpl_time() {
    return this.sale_cmpl_time;
  }

  public void setSale_cmpl_time(Integer sale_cmpl_time) {
    this.sale_cmpl_time = sale_cmpl_time;
  }

  public Integer getSigning_time() {
    return this.signing_time;
  }

  public void setSigning_time(Integer signing_time) {
    this.signing_time = signing_time;
  }

  public String getThe_sign() {
    return this.the_sign;
  }

  public void setThe_sign(String the_sign) {
    this.the_sign = the_sign;
  }

  public String getAdmin_remark()
  {
    return this.admin_remark;
  }

  public void setAdmin_remark(String admin_remark) {
    this.admin_remark = admin_remark;
  }

  @NotDbField
  public OrderPrice getOrderprice()
  {
    return this.orderprice;
  }

  public void setOrderprice(OrderPrice orderprice) {
    this.orderprice = orderprice;
  }

  @NotDbField
  public boolean getIsCod()
  {
    if ("cod".equals(getPayment_type())) {
      return true;
    }
    return false;
  }

  @NotDbField
  public boolean getIsOnlinePay()
  {
    if ((!"offline".equals(this.payment_type)) && (!"deposit".equals(this.payment_type)) && (!"cod".equals(this.payment_type)))
    {
      return true;
    }

    return false;
  }

  @NotDbField
  public Double getNeedPayMoney()
  {
    return Double.valueOf(CurrencyUtil.sub(getOrder_amount().doubleValue(), getPaymoney().doubleValue()));
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Order
 * JD-Core Version:    0.6.1
 */