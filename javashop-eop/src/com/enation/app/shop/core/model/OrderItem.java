package com.enation.app.shop.core.model;

import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import com.enation.framework.util.StringUtil;
import java.io.Serializable;

public class OrderItem
  implements Serializable
{
  private static final long serialVersionUID = 8531790664258169824L;
  private Integer item_id;
  private Integer order_id;
  private Integer goods_id;
  private Integer product_id;
  private Integer num;
  private Integer ship_num;
  private String name;
  private String sn;
  private String image;
  private Integer store;
  private String addon;
  private int cat_id;
  private Double price;
  private int gainedpoint;
  private int state;
  private String change_goods_name;
  private Integer change_goods_id;
  private String unit;
  private String other;

  public int getState()
  {
    return this.state;
  }
  public void setState(int state) {
    this.state = state;
  }
  public String getChange_goods_name() {
    return this.change_goods_name;
  }
  public void setChange_goods_name(String change_goods_name) {
    this.change_goods_name = change_goods_name;
  }
  public Integer getChange_goods_id() {
    return this.change_goods_id;
  }
  public void setChange_goods_id(Integer change_goods_id) {
    this.change_goods_id = change_goods_id;
  }
  public Integer getGoods_id() {
    return this.goods_id;
  }
  public void setGoods_id(Integer goods_id) {
    this.goods_id = goods_id;
  }
  @PrimaryKeyField
  public Integer getItem_id() {
    return this.item_id;
  }
  public void setItem_id(Integer item_id) {
    this.item_id = item_id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Integer getNum() {
    return this.num;
  }
  public void setNum(Integer num) {
    this.num = num;
  }
  public Integer getOrder_id() {
    return this.order_id;
  }
  public void setOrder_id(Integer order_id) {
    this.order_id = order_id;
  }
  public Integer getShip_num() {
    return this.ship_num;
  }
  public void setShip_num(Integer ship_num) {
    this.ship_num = ship_num;
  }

  public Double getPrice()
  {
    return this.price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
  public int getGainedpoint() {
    return this.gainedpoint;
  }
  public void setGainedpoint(int gainedpoint) {
    this.gainedpoint = gainedpoint;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }

  @NotDbField
  public Integer getStore() {
    return this.store;
  }
  public void setStore(Integer store) {
    this.store = store;
  }
  public String getAddon() {
    return this.addon;
  }
  public void setAddon(String addon) {
    this.addon = addon;
  }

  public int getCat_id() {
    return this.cat_id;
  }
  public void setCat_id(int cat_id) {
    this.cat_id = cat_id;
  }

  @NotDbField
  public String getOther() {
    return this.other;
  }
  public void setOther(String other) {
    this.other = other;
  }

  public String getImage()
  {
    if (!StringUtil.isEmpty(this.image)) {
      this.image = UploadUtil.replacePath(this.image);
    }

    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }
  public Integer getProduct_id() {
    return this.product_id;
  }
  public void setProduct_id(Integer product_id) {
    this.product_id = product_id;
  }
  public String getUnit() {
    return this.unit;
  }
  public void setUnit(String unit) {
    this.unit = unit;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.OrderItem
 * JD-Core Version:    0.6.1
 */