package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class Gnotify
  implements Serializable
{
  private int gnotify_id;
  private int goods_id;
  private int member_id;
  private int product_id;
  private String email;
  private String status;
  private Long send_time;
  private Long create_time;
  private String disabled;
  private String remark;

  @PrimaryKeyField
  public int getGnotify_id()
  {
    return this.gnotify_id;
  }

  public void setGnotify_id(int gnotifyId) {
    this.gnotify_id = gnotifyId;
  }

  public int getGoods_id() {
    return this.goods_id;
  }

  public void setGoods_id(int goodsId) {
    this.goods_id = goodsId;
  }

  public int getMember_id() {
    return this.member_id;
  }

  public void setMember_id(int memberId) {
    this.member_id = memberId;
  }

  public int getProduct_id() {
    return this.product_id;
  }

  public void setProduct_id(int productId) {
    this.product_id = productId;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getSend_time() {
    return this.send_time;
  }

  public void setSend_time(Long sendTime) {
    this.send_time = sendTime;
  }

  public Long getCreate_time() {
    return this.create_time;
  }

  public void setCreate_time(Long createTime) {
    this.create_time = createTime;
  }

  public String getDisabled() {
    return this.disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Gnotify
 * JD-Core Version:    0.6.1
 */