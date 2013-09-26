package com.enation.app.shop.core.model;

import java.io.Serializable;

public class GoodsComplex
  implements Serializable
{
  private int goods_1;
  private int goods_2;
  private String manual;
  private int rate;

  public int getGoods_1()
  {
    return this.goods_1;
  }

  public void setGoods_1(int goods_1) {
    this.goods_1 = goods_1;
  }

  public int getGoods_2() {
    return this.goods_2;
  }

  public void setGoods_2(int goods_2) {
    this.goods_2 = goods_2;
  }

  public String getManual() {
    return this.manual;
  }

  public void setManual(String manual) {
    this.manual = manual;
  }

  public int getRate() {
    return this.rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.GoodsComplex
 * JD-Core Version:    0.6.1
 */