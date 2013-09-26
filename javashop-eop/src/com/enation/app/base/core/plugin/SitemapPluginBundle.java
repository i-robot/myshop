package com.enation.app.base.core.plugin;

import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import com.enation.framework.plugin.IPlugin;
import java.util.List;

public class SitemapPluginBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "站点地图插件桩";
  }

  public void onRecreateMap()
  {
    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
        if ((plugin instanceof IRecreateMapEvent)) {
          IRecreateMapEvent event = (IRecreateMapEvent)plugin;
          event.onRecreateMap();
        }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.plugin.SitemapPluginBundle
 * JD-Core Version:    0.6.1
 */