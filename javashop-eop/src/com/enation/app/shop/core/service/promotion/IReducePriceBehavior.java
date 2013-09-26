package com.enation.app.shop.core.service.promotion;

import com.enation.app.shop.core.model.Promotion;

public abstract interface IReducePriceBehavior extends IPromotionBehavior
{
  public abstract Double reducedPrice(Promotion paramPromotion, Double paramDouble);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.promotion.IReducePriceBehavior
 * JD-Core Version:    0.6.1
 */