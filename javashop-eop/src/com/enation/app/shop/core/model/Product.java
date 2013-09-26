package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.util.ArrayList;
import java.util.List;

public class Product
{
  private Integer product_id;
  private Integer goods_id;
  private String name;
  private String sn;
  private Integer store;
  private Double price;
  private Double cost;
  private Double weight;
  private String specs;
  private List<GoodsLvPrice> goodsLvPrices;
  private List<SpecValue> specList;

  public Product()
  {
    this.specList = new ArrayList();
  }

  @NotDbField
  public List<SpecValue> getSpecList() {
    return this.specList;
  }
  public void setSpecList(List<SpecValue> specList) {
    this.specList = specList;
  }

  public void addSpec(SpecValue spec)
  {
    this.specList.add(spec);
  }

  @NotDbField
  public String getSpecsvIdJson()
  {
    String json = "[";
    int i = 0;
    for (SpecValue value : this.specList) {
      if (i != 0) json = json + ",";
      json = json + value.getSpec_value_id();
      i++;
    }
    json = json + "]";
    return json;
  }

  @PrimaryKeyField
  public Integer getProduct_id() {
    return this.product_id;
  }
  public void setProduct_id(Integer productId) {
    this.product_id = productId;
  }
  public Integer getGoods_id() {
    return this.goods_id;
  }
  public void setGoods_id(Integer goodsId) {
    this.goods_id = goodsId;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }
  public Integer getStore() {
    return this.store;
  }
  public void setStore(Integer store) {
    this.store = store;
  }
  public Double getPrice() {
    return this.price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getCost() {
    return this.cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public Double getWeight() {
    return this.weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public String getSpecs() {
    return this.specs;
  }

  public void setSpecs(String specs) {
    this.specs = specs;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @NotDbField
  public List<GoodsLvPrice> getGoodsLvPrices() {
    return this.goodsLvPrices;
  }

  public void setGoodsLvPrices(List<GoodsLvPrice> goodsLvPrices) {
    this.goodsLvPrices = goodsLvPrices;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Product
 * JD-Core Version:    0.6.1
 */