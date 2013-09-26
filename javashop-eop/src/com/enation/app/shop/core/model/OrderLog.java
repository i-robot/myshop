package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class OrderLog
{
  private Integer log_id;
  private Integer order_id;
  private Integer op_id;
  private String op_name;
  private String message;
  private Long op_time;

  @PrimaryKeyField
  public Integer getLog_id()
  {
    return this.log_id;
  }
  public void setLog_id(Integer logId) {
    this.log_id = logId;
  }
  public Integer getOrder_id() {
    return this.order_id;
  }
  public void setOrder_id(Integer orderId) {
    this.order_id = orderId;
  }
  public Integer getOp_id() {
    return this.op_id;
  }
  public void setOp_id(Integer opId) {
    this.op_id = opId;
  }
  public String getOp_name() {
    return this.op_name;
  }
  public void setOp_name(String opName) {
    this.op_name = opName;
  }
  public String getMessage() {
    return this.message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public Long getOp_time() {
    return this.op_time;
  }
  public void setOp_time(Long opTime) {
    this.op_time = opTime;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.OrderLog
 * JD-Core Version:    0.6.1
 */