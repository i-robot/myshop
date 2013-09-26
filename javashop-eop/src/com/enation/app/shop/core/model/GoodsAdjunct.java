package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class GoodsAdjunct
  implements Serializable
{
  private int adjunct_id;
  private int goods_id;
  private String adjunct_name;
  private int min_num;
  private int max_num;
  private String set_price;
  private Double price;
  private String items;

  @PrimaryKeyField
  public int getAdjunct_id()
  {
    return this.adjunct_id;
  }
  public void setAdjunct_id(int adjunctId) {
    this.adjunct_id = adjunctId;
  }
  public int getGoods_id() {
    return this.goods_id;
  }
  public void setGoods_id(int goodsId) {
    this.goods_id = goodsId;
  }
  public String getAdjunct_name() {
    return this.adjunct_name;
  }
  public void setAdjunct_name(String adjunctName) {
    this.adjunct_name = adjunctName;
  }
  public int getMin_num() {
    return this.min_num;
  }
  public void setMin_num(int minNum) {
    this.min_num = minNum;
  }
  public int getMax_num() {
    return this.max_num;
  }
  public void setMax_num(int maxNum) {
    this.max_num = maxNum;
  }
  public String getSet_price() {
    return this.set_price;
  }
  public void setSet_price(String setPrice) {
    this.set_price = setPrice;
  }
  public Double getPrice() {
    return this.price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
  public String getItems() {
    return this.items;
  }
  public void setItems(String items) {
    this.items = items;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.GoodsAdjunct
 * JD-Core Version:    0.6.1
 */