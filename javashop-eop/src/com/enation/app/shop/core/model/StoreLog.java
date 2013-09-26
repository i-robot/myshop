package com.enation.app.shop.core.model;

public class StoreLog
{
  private Integer logid;
  private String goodsname;
  private Integer goodsid;
  private Integer productid;
  private Integer depot_type;
  private Integer op_type;
  private Integer num;
  private String remark;
  private Integer dateline;
  private Integer userid;
  private String username;
  private Integer depotid;

  public Integer getLogid()
  {
    return this.logid;
  }
  public void setLogid(Integer logid) {
    this.logid = logid;
  }
  public String getGoodsname() {
    return this.goodsname;
  }
  public void setGoodsname(String goodsname) {
    this.goodsname = goodsname;
  }
  public Integer getProductid() {
    return this.productid;
  }
  public void setProductid(Integer productid) {
    this.productid = productid;
  }

  public Integer getOp_type() {
    return this.op_type;
  }
  public void setOp_type(Integer op_type) {
    this.op_type = op_type;
  }
  public Integer getNum() {
    return this.num;
  }
  public void setNum(Integer num) {
    this.num = num;
  }
  public String getRemark() {
    return this.remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public Integer getDateline() {
    return this.dateline;
  }
  public void setDateline(Integer dateline) {
    this.dateline = dateline;
  }
  public Integer getUserid() {
    return this.userid;
  }
  public void setUserid(Integer userid) {
    this.userid = userid;
  }
  public String getUsername() {
    return this.username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public Integer getDepotid() {
    return this.depotid;
  }
  public void setDepotid(Integer depotid) {
    this.depotid = depotid;
  }
  public Integer getDepot_type() {
    return this.depot_type;
  }
  public void setDepot_type(Integer depot_type) {
    this.depot_type = depot_type;
  }
  public Integer getGoodsid() {
    return this.goodsid;
  }
  public void setGoodsid(Integer goodsid) {
    this.goodsid = goodsid;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.StoreLog
 * JD-Core Version:    0.6.1
 */