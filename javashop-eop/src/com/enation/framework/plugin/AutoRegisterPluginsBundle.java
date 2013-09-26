package com.enation.framework.plugin;

import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AutoRegisterPluginsBundle
  implements IPluginBundle
{
  protected static final Log loger = LogFactory.getLog(AutoRegisterPluginsBundle.class);
  private List<IPlugin> plugins;
  private Map<String, List<IPlugin>> saasPlugins;

  public synchronized void registerPlugin(IPlugin plugin)
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      registerPlugin2(plugin);
    }

    if ("1".equals(EopSetting.RUNMODE))
      registerPlugin1(plugin);
  }

  private void registerPlugin1(IPlugin plugin)
  {
    if (this.plugins == null) {
      this.plugins = new ArrayList();
    }

    if (!this.plugins.contains(plugin)) {
      this.plugins.add(plugin);
    }

    if (loger.isDebugEnabled())
      loger.debug("为插件桩" + getName() + "注册插件：" + plugin.getClass());
  }

  private void registerPlugin2(IPlugin plugin)
  {
    if (this.saasPlugins == null) {
      this.saasPlugins = new HashMap();
    }

    String key = getKey();

    List pluginList = (List)this.saasPlugins.get(key);

    if (pluginList == null) {
      pluginList = new ArrayList();
      this.saasPlugins.put(key, pluginList);
    }
    if (!pluginList.contains(plugin))
      pluginList.add(plugin);
  }

  public synchronized void unRegisterPlugin(IPlugin _plugin)
  {
    if ("2".equals(EopSetting.RUNMODE))
    {
      if (this.saasPlugins == null) {
        return;
      }

      String key = getKey();
      List pluginList = (List)this.saasPlugins.get(key);
      if (pluginList == null) {
        return;
      }
      pluginList.remove(_plugin);
    }
    else if (this.plugins != null) {
      this.plugins.remove(_plugin);
    }
  }

  public synchronized List<IPlugin> getPlugins()
  {
    if ("2".equals(EopSetting.RUNMODE))
    {
      if (this.saasPlugins == null) {
        return new ArrayList();
      }

      String key = getKey();
      List pluginList = (List)this.saasPlugins.get(key);
      if (pluginList == null) {
        return new ArrayList();
      }
      return pluginList;
    }

    return this.plugins;
  }

  private String getKey()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    int userid = site.getUserid().intValue();
    int siteid = site.getId().intValue();
    String key = userid + "_" + siteid;

    return key;
  }

  public abstract String getName();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.plugin.AutoRegisterPluginsBundle
 * JD-Core Version:    0.6.1
 */