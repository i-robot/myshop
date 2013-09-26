package com.enation.app.shop.core.service.promotion;

public class PromotionConditions
{
  public static final String ORDER = "order";
  public static final String MEMBERLV = "memberLv";
  public static final String GOODS = "goods";
  private boolean hasOrder = false;
  private boolean hasMemberLv = false;
  private boolean hasGoods = false;

  public PromotionConditions(String[] conditions) {
    if (conditions != null)
      for (String cond : conditions) {
        if ("order".equals(cond)) {
          this.hasOrder = true;
        }

        if ("memberLv".equals(cond)) {
          this.hasMemberLv = true;
        }

        if ("goods".equals(cond))
          this.hasGoods = true;
      }
  }

  public boolean getHasOrder()
  {
    return this.hasOrder;
  }

  public boolean getHasMemberLv()
  {
    return this.hasMemberLv;
  }

  public void setHasMemberLv(boolean hasMemberLv)
  {
    this.hasMemberLv = hasMemberLv;
  }

  public void setHasOrder(boolean hasOrder)
  {
    this.hasOrder = hasOrder;
  }

  public boolean getHasGoods()
  {
    return this.hasGoods;
  }

  public void setHasGoods(boolean hasGoods)
  {
    this.hasGoods = hasGoods;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.promotion.PromotionConditions
 * JD-Core Version:    0.6.1
 */