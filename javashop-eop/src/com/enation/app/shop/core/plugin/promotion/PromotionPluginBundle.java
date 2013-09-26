package com.enation.app.shop.core.plugin.promotion;

import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import java.util.List;

public class PromotionPluginBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "优惠规则插件桩";
  }

  public List getPluginList()
  {
    return getPlugins();
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.promotion.PromotionPluginBundle
 * JD-Core Version:    0.6.1
 */