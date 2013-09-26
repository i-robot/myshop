package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Promotion;
import com.enation.app.shop.core.model.support.CartItem;
import com.enation.app.shop.core.model.support.DiscountPrice;
import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
import java.util.List;
import java.util.Map;

public abstract interface IPromotionManager
{
  public abstract Integer add(Promotion paramPromotion, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract Integer add(Promotion paramPromotion, Integer[] paramArrayOfInteger1, int paramInt, Integer[] paramArrayOfInteger2, Integer[] paramArrayOfInteger3, Integer[] paramArrayOfInteger4);

  public abstract List<Promotion> list(Integer paramInteger1, Integer paramInteger2);

  public abstract List<Promotion> list(Double paramDouble, Integer paramInteger);

  public abstract void applyGoodsPmt(List<CartItem> paramList, Integer paramInteger);

  public abstract DiscountPrice applyOrderPmt(Double paramDouble1, Double paramDouble2, Integer paramInteger1, Integer paramInteger2);

  public abstract void applyOrderPmt(Integer paramInteger1, Double paramDouble, Integer paramInteger2);

  public abstract List listGift(List<Promotion> paramList);

  public abstract List listPmtPlugins();

  public abstract IPromotionPlugin getPlugin(String paramString);

  public abstract List listByActivityId(Integer paramInteger);

  public abstract void delete(Integer[] paramArrayOfInteger);

  public abstract Promotion get(Integer paramInteger);

  public abstract List<Integer> listMemberLvId(Integer paramInteger);

  public abstract List listGoodsId(Integer paramInteger);

  public abstract void edit(Promotion paramPromotion, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract List<Map> listOrderPmt(Integer paramInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IPromotionManager
 * JD-Core Version:    0.6.1
 */