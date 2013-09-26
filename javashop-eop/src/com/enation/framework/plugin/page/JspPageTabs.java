package com.enation.framework.plugin.page;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JspPageTabs
{
  private static final Log loger = LogFactory.getLog(JspPageTabs.class);

  private static Map<String, Map> tabs = new HashMap();

  public static void addTab(String plugintype, Integer tabid, String tabname)
  {
    Map plugin_tab = tabs.get(plugintype) == null ? new LinkedHashMap() : (Map)tabs.get(plugintype);
    plugin_tab.put(tabid, tabname);

    tabs.put(plugintype, plugin_tab);
    if (loger.isDebugEnabled())
      loger.debug("添加" + plugintype + "选项卡" + tabid + " tabname is  " + tabname);
  }

  public static Map getTabs(String plugintype)
  {
    return (Map)tabs.get(plugintype);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.plugin.page.JspPageTabs
 * JD-Core Version:    0.6.1
 */