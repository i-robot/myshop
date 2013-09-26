package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class MemberOrderItem
{
  private int id;
  private Integer member_id;
  private Integer goods_id;
  private Integer order_id;
  private Integer item_id;
  private Integer commented;
  private Long comment_time;

  public MemberOrderItem()
  {
  }

  public MemberOrderItem(int id, Integer member_id, Integer goods_id, Integer order_id, Integer item_id, Integer commented, Long comment_time)
  {
    this.id = id;
    this.member_id = member_id;
    this.goods_id = goods_id;
    this.order_id = order_id;
    this.item_id = item_id;
    this.commented = commented;
    this.comment_time = comment_time;
  }
  @PrimaryKeyField
  public int getId() {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Integer getMember_id() {
    return this.member_id;
  }
  public void setMember_id(Integer member_id) {
    this.member_id = member_id;
  }
  public Integer getOrder_id() {
    return this.order_id;
  }
  public void setOrder_id(Integer order_id) {
    this.order_id = order_id;
  }
  public Integer getItem_id() {
    return this.item_id;
  }
  public void setItem_id(Integer item_id) {
    this.item_id = item_id;
  }
  public Integer getCommented() {
    return this.commented;
  }
  public void setCommented(Integer commented) {
    this.commented = commented;
  }
  public Long getComment_time() {
    return this.comment_time;
  }
  public void setComment_time(Long comment_time) {
    this.comment_time = comment_time;
  }

  public Integer getGoods_id() {
    return this.goods_id;
  }

  public void setGoods_id(Integer goods_id) {
    this.goods_id = goods_id;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.MemberOrderItem
 * JD-Core Version:    0.6.1
 */