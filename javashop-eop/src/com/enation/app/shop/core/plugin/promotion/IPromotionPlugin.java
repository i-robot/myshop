package com.enation.app.shop.core.plugin.promotion;

import com.enation.framework.plugin.IPlugin;

public abstract interface IPromotionPlugin extends IPlugin
{
  public abstract String[] getConditions();

  public abstract String getMethods();

  public abstract String getId();

  public abstract String getType();
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.promotion.IPromotionPlugin
 * JD-Core Version:    0.6.1
 */