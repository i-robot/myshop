package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class WarnTask
  implements Serializable
{
  private Integer id;
  private Integer catid;
  private Integer goodsid;
  private Integer depotid;
  private Integer flag;
  private String sphere;
  private String cylinder;
  private String productids;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public Integer getCatid() {
    return this.catid;
  }
  public void setCatid(Integer catid) {
    this.catid = catid;
  }
  public Integer getGoodsid() {
    return this.goodsid;
  }
  public void setGoodsid(Integer goodsid) {
    this.goodsid = goodsid;
  }
  public Integer getDepotid() {
    return this.depotid;
  }
  public void setDepotid(Integer depotid) {
    this.depotid = depotid;
  }
  public Integer getFlag() {
    return this.flag;
  }
  public void setFlag(Integer flag) {
    this.flag = flag;
  }
  public String getSphere() {
    return this.sphere;
  }
  public void setSphere(String sphere) {
    this.sphere = sphere;
  }
  public String getCylinder() {
    return this.cylinder;
  }
  public void setCylinder(String cylinder) {
    this.cylinder = cylinder;
  }
  public String getProductids() {
    return this.productids;
  }
  public void setProductids(String productids) {
    this.productids = productids;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.WarnTask
 * JD-Core Version:    0.6.1
 */