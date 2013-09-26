package com.enation.app.shop.core.plugin.search;

import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import java.util.List;

public class GoodsDataFilterBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "商品数据过滤插件桩";
  }

  public List getPluginList()
  {
    return getPlugins();
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.search.GoodsDataFilterBundle
 * JD-Core Version:    0.6.1
 */