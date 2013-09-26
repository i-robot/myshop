package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class Favorite
  implements Serializable
{
  private int favorite_id;
  private int member_id;
  private int goods_id;

  @PrimaryKeyField
  public int getFavorite_id()
  {
    return this.favorite_id;
  }

  public void setFavorite_id(int favoriteId) {
    this.favorite_id = favoriteId;
  }

  public int getMember_id() {
    return this.member_id;
  }

  public void setMember_id(int memberId) {
    this.member_id = memberId;
  }

  public int getGoods_id() {
    return this.goods_id;
  }

  public void setGoods_id(int goodsId) {
    this.goods_id = goodsId;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Favorite
 * JD-Core Version:    0.6.1
 */