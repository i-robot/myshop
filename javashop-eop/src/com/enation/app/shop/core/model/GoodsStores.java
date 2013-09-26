package com.enation.app.shop.core.model;

import java.io.Serializable;

public class GoodsStores
  implements Serializable
{
  private Integer goods_id;
  private String name;
  private String sn;
  private Integer brand_id;
  private Integer cat_id;
  private Integer type_id;
  private String goods_type;
  private String unit;
  private Double weight;
  private Integer market_enable;
  private Double price;
  private Double mktprice;
  private Integer store;
  private Long create_time;
  private Long last_modify;
  private Integer disabled;
  private Integer point;
  private Integer istejia;
  private Integer no_discount;
  private String color;
  private Double sphere;
  private Integer realstore;
  private Double cylinder;
  private String brandname;
  private String catname;

  public Integer getGoods_id()
  {
    return this.goods_id;
  }
  public void setGoods_id(Integer goods_id) {
    this.goods_id = goods_id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }
  public Integer getBrand_id() {
    return this.brand_id;
  }
  public void setBrand_id(Integer brand_id) {
    this.brand_id = brand_id;
  }
  public Integer getCat_id() {
    return this.cat_id;
  }
  public void setCat_id(Integer cat_id) {
    this.cat_id = cat_id;
  }
  public Integer getType_id() {
    return this.type_id;
  }
  public void setType_id(Integer type_id) {
    this.type_id = type_id;
  }
  public String getGoods_type() {
    return this.goods_type;
  }
  public void setGoods_type(String goods_type) {
    this.goods_type = goods_type;
  }
  public String getUnit() {
    return this.unit;
  }
  public void setUnit(String unit) {
    this.unit = unit;
  }
  public Double getWeight() {
    return this.weight;
  }
  public void setWeight(Double weight) {
    this.weight = weight;
  }
  public Integer getMarket_enable() {
    return this.market_enable;
  }
  public void setMarket_enable(Integer market_enable) {
    this.market_enable = market_enable;
  }
  public Double getPrice() {
    return this.price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
  public Double getMktprice() {
    return this.mktprice;
  }
  public void setMktprice(Double mktprice) {
    this.mktprice = mktprice;
  }
  public Integer getStore() {
    return this.store;
  }
  public void setStore(Integer store) {
    this.store = store;
  }
  public Long getCreate_time() {
    return this.create_time;
  }
  public void setCreate_time(Long create_time) {
    this.create_time = create_time;
  }
  public Long getLast_modify() {
    return this.last_modify;
  }
  public void setLast_modify(Long last_modify) {
    this.last_modify = last_modify;
  }
  public Integer getDisabled() {
    return this.disabled;
  }
  public void setDisabled(Integer disabled) {
    this.disabled = disabled;
  }
  public Integer getPoint() {
    return this.point;
  }
  public void setPoint(Integer point) {
    this.point = point;
  }
  public Integer getIstejia() {
    return this.istejia;
  }
  public void setIstejia(Integer istejia) {
    this.istejia = istejia;
  }
  public Integer getNo_discount() {
    return this.no_discount;
  }
  public void setNo_discount(Integer no_discount) {
    this.no_discount = no_discount;
  }
  public String getColor() {
    return this.color;
  }
  public void setColor(String color) {
    this.color = color;
  }
  public Integer getRealstore() {
    return this.realstore;
  }
  public void setRealstore(Integer realstore) {
    this.realstore = realstore;
  }
  public String getBrandname() {
    return this.brandname;
  }
  public void setBrandname(String brandname) {
    this.brandname = brandname;
  }
  public String getCatname() {
    return this.catname;
  }
  public void setCatname(String catname) {
    this.catname = catname;
  }
  public Double getSphere() {
    return this.sphere;
  }
  public void setSphere(Double sphere) {
    this.sphere = sphere;
  }
  public Double getCylinder() {
    return this.cylinder;
  }
  public void setCylinder(Double cylinder) {
    this.cylinder = cylinder;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.GoodsStores
 * JD-Core Version:    0.6.1
 */