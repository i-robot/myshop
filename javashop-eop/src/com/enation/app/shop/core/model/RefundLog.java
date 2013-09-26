package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;

public class RefundLog
{
  private Integer refund_id;
  private int order_id;
  private String order_sn;
  private Integer member_id;
  private int type;
  private String pay_method;
  private String pay_user;
  private String account;
  private Double money;
  private long pay_date;
  private String remark;
  private String op_user;
  private Long create_time;
  private String sn;
  private String pay_date_str;

  @PrimaryKeyField
  public Integer getRefund_id()
  {
    return this.refund_id;
  }
  public void setRefund_id(Integer refundId) {
    this.refund_id = refundId;
  }
  public int getOrder_id() {
    return this.order_id;
  }
  public void setOrder_id(int order_id) {
    this.order_id = order_id;
  }
  public String getOrder_sn() {
    return this.order_sn;
  }
  public void setOrder_sn(String order_sn) {
    this.order_sn = order_sn;
  }
  public Integer getMember_id() {
    return this.member_id;
  }
  public void setMember_id(Integer member_id) {
    this.member_id = member_id;
  }
  public int getType() {
    return this.type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public String getPay_method() {
    return this.pay_method;
  }
  public void setPay_method(String pay_method) {
    this.pay_method = pay_method;
  }
  public String getPay_user() {
    return this.pay_user;
  }
  public void setPay_user(String pay_user) {
    this.pay_user = pay_user;
  }
  public String getAccount() {
    return this.account;
  }
  public void setAccount(String account) {
    this.account = account;
  }
  public Double getMoney() {
    return this.money;
  }
  public void setMoney(Double money) {
    this.money = money;
  }
  public long getPay_date() {
    return this.pay_date;
  }
  public void setPay_date(long pay_date) {
    this.pay_date = pay_date;
  }
  public String getRemark() {
    return this.remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public String getOp_user() {
    return this.op_user;
  }
  public void setOp_user(String op_user) {
    this.op_user = op_user;
  }
  public Long getCreate_time() {
    return this.create_time;
  }
  public void setCreate_time(Long create_time) {
    this.create_time = create_time;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }
  @NotDbField
  public String getPay_date_str() {
    return this.pay_date_str;
  }

  public void setPay_date_str(String pay_date_str) {
    this.pay_date_str = pay_date_str;

    if (!StringUtil.isEmpty(pay_date_str))
      this.pay_date = DateUtil.getDatelineLong(pay_date_str);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.RefundLog
 * JD-Core Version:    0.6.1
 */