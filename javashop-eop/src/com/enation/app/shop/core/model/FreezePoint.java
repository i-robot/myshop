package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;

public class FreezePoint
{
  private int id;
  private int memberid;
  private Integer childid;
  private int point;
  private int mp;
  private Integer orderid;
  private int dateline;
  private String type;
  private int order_status;

  @PrimaryKeyField
  public int getId()
  {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPoint()
  {
    return this.point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public int getMp() {
    return this.mp;
  }

  public void setMp(int mp) {
    this.mp = mp;
  }

  public int getDateline()
  {
    return this.dateline;
  }

  public void setDateline(int dateline) {
    this.dateline = dateline;
  }

  public int getMemberid()
  {
    return this.memberid;
  }

  public void setMemberid(int memberid) {
    this.memberid = memberid;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @NotDbField
  public int getOrder_status() {
    return this.order_status;
  }

  public void setOrder_status(int order_status) {
    this.order_status = order_status;
  }

  public Integer getChildid() {
    return this.childid;
  }

  public void setChildid(Integer childid) {
    this.childid = childid;
  }

  public Integer getOrderid() {
    return this.orderid;
  }

  public void setOrderid(Integer orderid) {
    this.orderid = orderid;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.FreezePoint
 * JD-Core Version:    0.6.1
 */