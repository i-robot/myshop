package com.enation.app.shop.core.service.promotion;

import com.enation.app.shop.core.model.Promotion;

public abstract interface IDiscountBehavior extends IPromotionBehavior
{
  public abstract Double discount(Promotion paramPromotion, Double paramDouble);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.promotion.IDiscountBehavior
 * JD-Core Version:    0.6.1
 */