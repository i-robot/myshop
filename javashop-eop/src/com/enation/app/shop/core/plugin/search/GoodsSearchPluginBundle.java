package com.enation.app.shop.core.plugin.search;

import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import java.util.List;

public class GoodsSearchPluginBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "商品搜索插件桩";
  }

  public List getPluginList()
  {
    return getPlugins();
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.search.GoodsSearchPluginBundle
 * JD-Core Version:    0.6.1
 */