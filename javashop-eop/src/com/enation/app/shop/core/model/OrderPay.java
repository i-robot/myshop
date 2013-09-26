package com.enation.app.shop.core.model;

public class OrderPay
{
  private int payid;
  private int order_id;
  private String bank;
  private String paydate;
  private String sn;
  private Double paymoney;
  private String remark;
  private long add_time;
  private int confirmed;

  public int getPayid()
  {
    return this.payid;
  }
  public void setPayid(int payid) {
    this.payid = payid;
  }
  public int getOrder_id() {
    return this.order_id;
  }
  public void setOrder_id(int order_id) {
    this.order_id = order_id;
  }
  public String getBank() {
    return this.bank;
  }
  public void setBank(String bank) {
    this.bank = bank;
  }
  public String getPaydate() {
    return this.paydate;
  }
  public void setPaydate(String paydate) {
    this.paydate = paydate;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }
  public Double getPaymoney() {
    return this.paymoney;
  }
  public void setPaymoney(Double paymoney) {
    this.paymoney = paymoney;
  }
  public String getRemark() {
    return this.remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public long getAdd_time() {
    return this.add_time;
  }
  public void setAdd_time(long add_time) {
    this.add_time = add_time;
  }
  public int getConfirmed() {
    return this.confirmed;
  }
  public void setConfirmed(int confirmed) {
    this.confirmed = confirmed;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.OrderPay
 * JD-Core Version:    0.6.1
 */