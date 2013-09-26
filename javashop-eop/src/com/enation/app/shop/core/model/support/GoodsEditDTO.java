package com.enation.app.shop.core.model.support;

import java.util.Map;

public class GoodsEditDTO
{
  private Map goods;
  private Map<Integer, String> htmlMap;

  public Map getGoods()
  {
    return this.goods;
  }
  public void setGoods(Map goods) {
    this.goods = goods;
  }
  public Map<Integer, String> getHtmlMap() {
    return this.htmlMap;
  }
  public void setHtmlMap(Map<Integer, String> htmlMap) {
    this.htmlMap = htmlMap;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.GoodsEditDTO
 * JD-Core Version:    0.6.1
 */