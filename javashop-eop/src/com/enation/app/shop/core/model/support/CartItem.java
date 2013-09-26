package com.enation.app.shop.core.model.support;

import com.enation.app.shop.core.model.Promotion;
import com.enation.framework.database.NotDbField;
import com.enation.framework.util.CurrencyUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItem
{
  private Integer id;
  private Integer product_id;
  private Integer goods_id;
  private String name;
  private Double mktprice;
  private Double price;
  private Double coupPrice;
  private Double subtotal;
  private int num;
  private Integer limitnum;
  private String image_default;
  private Integer point;
  private Integer itemtype;
  private String sn;
  private String addon;
  private String specs;
  private int catid;
  private Map others;
  private Integer istejia;
  private Integer no_discount;
  private String unit;
  private List<Promotion> pmtList;

  public void setPmtList(List<Promotion> pmtList)
  {
    this.pmtList = pmtList;
  }

  @NotDbField
  public List<Promotion> getPmtList() {
    return this.pmtList;
  }

  @NotDbField
  public Map getOthers()
  {
    if (this.others == null) this.others = new HashMap();
    return this.others;
  }

  public void setOthers(Map others) {
    this.others = others;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProduct_id() {
    return this.product_id;
  }

  public void setProduct_id(Integer productId) {
    this.product_id = productId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getMktprice() {
    return this.mktprice;
  }

  public void setMktprice(Double mktprice) {
    this.mktprice = mktprice;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getCoupPrice() {
    return this.coupPrice;
  }

  public void setCoupPrice(Double coupPrice) {
    this.coupPrice = coupPrice;
  }

  public Double getSubtotal() {
    this.subtotal = CurrencyUtil.mul(this.num, this.coupPrice.doubleValue());
    return this.subtotal;
  }

  public void setSubtotal(Double subtotal) {
    this.subtotal = subtotal;
  }

  public int getNum() {
    return this.num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public String getImage_default() {
    return this.image_default;
  }

  public void setImage_default(String imageDefault) {
    this.image_default = imageDefault;
  }

  public Integer getGoods_id() {
    return this.goods_id;
  }

  public void setGoods_id(Integer goodsId) {
    this.goods_id = goodsId;
  }

  public Integer getPoint() {
    return this.point;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }

  public Integer getLimitnum()
  {
    return this.limitnum;
  }

  public void setLimitnum(Integer limitnum) {
    this.limitnum = limitnum;
  }

  public Integer getItemtype() {
    return this.itemtype;
  }

  public void setItemtype(Integer itemtype) {
    this.itemtype = itemtype;
  }

  public String getSn() {
    return this.sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getAddon() {
    return this.addon;
  }

  public void setAddon(String addon) {
    this.addon = addon;
  }

  public String getSpecs() {
    return this.specs;
  }

  public void setSpecs(String specs) {
    this.specs = specs;
  }

  public int getCatid() {
    return this.catid;
  }

  public void setCatid(int catid) {
    this.catid = catid;
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

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.CartItem
 * JD-Core Version:    0.6.1
 */