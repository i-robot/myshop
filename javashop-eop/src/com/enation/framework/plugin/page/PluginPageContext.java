package com.enation.framework.plugin.page;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginPageContext
{
  private static Map<String, List<String>> plugin_pages = new HashMap();

  public static void addPage(String type, String page)
  {
    List pagelist = (List)plugin_pages.get(type);
    if (pagelist == null)
      pagelist = new ArrayList();
    pagelist.add(page);
    plugin_pages.put(type, pagelist);
    System.out.println("加载" + type + " 类页面： " + page);
  }

  public static List<String> getPages(String page_type)
  {
    return (List)plugin_pages.get(page_type);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.plugin.page.PluginPageContext
 * JD-Core Version:    0.6.1
 */