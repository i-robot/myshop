package com.enation.eop.sdk.webapp.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPluginsBundle
  implements IPluginBundle
{
  protected Map<String, List<IPlugin>> plugins;

  public void registerPlugin(IPlugin plugin)
  {
    if (this.plugins == null) {
      this.plugins = new HashMap();
    }

    List plugin_list = (List)this.plugins.get(plugin.getType());

    if (plugin_list == null) {
      plugin_list = new ArrayList();
    }

    plugin_list.add(plugin);
    this.plugins.put(plugin.getType(), plugin_list);
  }

  protected void performPlugins(String type, Object[] param)
  {
    List<IPlugin> plugin_list = (List)this.plugins.get(type);
    if (plugin_list != null)
      for (IPlugin plugin : plugin_list)
        plugin.perform(param);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.plugin.AbstractPluginsBundle
 * JD-Core Version:    0.6.1
 */