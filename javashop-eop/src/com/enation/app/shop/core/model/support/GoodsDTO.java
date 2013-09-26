package com.enation.app.shop.core.model.support;

import com.enation.app.shop.core.model.Goods;

public class GoodsDTO
{
  private Goods goods;
  private String[] photos;

  public Goods getGoods()
  {
    return this.goods;
  }
  public void setGoods(Goods goods) {
    this.goods = goods;
  }
  public String[] getPhotos() {
    return this.photos;
  }
  public void setPhotos(String[] photos) {
    this.photos = photos;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.GoodsDTO
 * JD-Core Version:    0.6.1
 */