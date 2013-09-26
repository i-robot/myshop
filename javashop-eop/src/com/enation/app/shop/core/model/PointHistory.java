package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;

public class PointHistory
{
  private int id;
  private int member_id;
  private int point;
  private Long time;
  private String reason;
  private String cnreason;
  private Integer related_id;
  private int type;
  private String operator;
  private Integer mp;
  private int point_type;

  @PrimaryKeyField
  public int getId()
  {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getMember_id() {
    return this.member_id;
  }
  public void setMember_id(int memberId) {
    this.member_id = memberId;
  }
  public int getPoint() {
    return this.point;
  }
  public void setPoint(int point) {
    this.point = point;
  }
  public Long getTime() {
    return this.time;
  }
  public void setTime(Long time) {
    this.time = time;
  }
  public String getReason() {
    return this.reason;
  }
  public void setReason(String reason) {
    this.reason = reason;
  }

  public int getType() {
    return this.type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public String getOperator() {
    return this.operator;
  }
  public void setOperator(String operator) {
    this.operator = operator;
  }
  @NotDbField
  public String getCnreason() {
    if (this.reason.equals("order_pay_use")) this.cnreason = "订单消费积分";
    if (this.reason.equals("order_pay_get")) this.cnreason = "订单获得积分";
    if (this.reason.equals("order_refund_use")) this.cnreason = "退还订单消费积分";
    if (this.reason.equals("order_refund_get")) this.cnreason = "扣掉订单所得积分";
    if (this.reason.equals("order_cancel_refund_consume_gift")) this.cnreason = "Score deduction for gifts refunded for order cancelling.";
    if (this.reason.equals("exchange_coupon")) this.cnreason = "兑换优惠券";
    if (this.reason.equals("operator_adjust")) this.cnreason = "管理员改变积分";
    if (this.reason.equals("consume_gift")) this.cnreason = "积分换赠品";
    if (this.reason.equals("recommend_member")) this.cnreason = "发表评论奖励积分";
    return this.cnreason;
  }
  public void setCnreason(String cnreason) {
    this.cnreason = cnreason;
  }
  public Integer getRelated_id() {
    return this.related_id;
  }
  public void setRelated_id(Integer relatedId) {
    this.related_id = relatedId;
  }
  public Integer getMp() {
    return this.mp;
  }
  public void setMp(Integer mp) {
    this.mp = mp;
  }
  public int getPoint_type() {
    return this.point_type;
  }
  public void setPoint_type(int point_type) {
    this.point_type = point_type;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.PointHistory
 * JD-Core Version:    0.6.1
 */