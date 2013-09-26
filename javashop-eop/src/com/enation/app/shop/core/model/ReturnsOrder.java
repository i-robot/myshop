package com.enation.app.shop.core.model;

import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.NotDbField;

public class ReturnsOrder
{
  private Integer id;
  private String ordersn;
  private Integer memberid;
  private Integer state;
  private String goodsns;
  private Integer type;
  private Integer add_time;
  private String photo;
  private String refuse_reason;
  private String apply_reason;
  private String membername;

  public Integer getType()
  {
    return this.type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
  public String getApply_reason() {
    return this.apply_reason;
  }
  public void setApply_reason(String apply_reason) {
    this.apply_reason = apply_reason;
  }
  public Integer getId() {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getOrdersn() {
    return this.ordersn;
  }
  public void setOrdersn(String ordersn) {
    this.ordersn = ordersn;
  }
  public Integer getMemberid() {
    return this.memberid;
  }
  public void setMemberid(Integer memberid) {
    this.memberid = memberid;
  }
  public Integer getState() {
    return this.state;
  }
  public void setState(Integer state) {
    this.state = state;
  }
  public String getGoodsns() {
    return this.goodsns;
  }
  public void setGoodsns(String goodsns) {
    this.goodsns = goodsns;
  }
  public Integer getAdd_time() {
    return this.add_time;
  }
  public void setAdd_time(Integer add_time) {
    this.add_time = add_time;
  }
  public String getPhoto() {
    if ((this.photo != null) && (!this.photo.equals(""))) {
      this.photo = UploadUtil.replacePath(this.photo);
    }
    return this.photo;
  }
  public void setPhoto(String photo) {
    this.photo = photo;
  }
  public String getRefuse_reason() {
    return this.refuse_reason;
  }
  public void setRefuse_reason(String refuse_reason) {
    this.refuse_reason = refuse_reason;
  }

  @NotDbField
  public String getMembername()
  {
    return this.membername;
  }
  public void setMembername(String membername) {
    this.membername = membername;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.ReturnsOrder
 * JD-Core Version:    0.6.1
 */