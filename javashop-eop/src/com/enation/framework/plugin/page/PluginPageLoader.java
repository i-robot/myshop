package com.enation.framework.plugin.page;

import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.PrintStream;

public class PluginPageLoader
{
  private String path;
  private String type;

  public PluginPageLoader(String type, String path)
  {
    path = path.endsWith("/") ? (path = path.substring(0, path.length() - 1)) : path;
    this.type = type;
    this.path = path;
    initPages();
  }

  private void initPages()
  {
    String root_path = StringUtil.getRootPath();
    System.out.println("加载插件文件夹：" + root_path + this.path);
    File file = new File(root_path + this.path);
    File[] files = file.listFiles();
    for (File f : files)
    {
      PluginPageContext.addPage(this.type, this.path + "/" + f.getName());
    }
  }

  public static void main(String[] args)
  {
    PluginPageLoader pluginPageLoader = new PluginPageLoader("userlist", "/admin/user/plugin");
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.plugin.page.PluginPageLoader
 * JD-Core Version:    0.6.1
 */