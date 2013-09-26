package com.enation.app.base.core.action;

import com.enation.app.base.core.plugin.SitemapPluginBundle;
import com.enation.app.base.core.service.ISitemapManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class CreateSitemapAction extends WWAction
{
  private ISitemapManager sitemapManager;
  private SitemapPluginBundle sitemapPluginBundle;

  public String recreate()
  {
    clean();
    this.sitemapPluginBundle.onRecreateMap();
    this.msgs.add("站点地图创建成功");
    this.urls.put("访问站点地图", "/sitemap.xml");
    return "message";
  }
  private void clean() {
    this.sitemapManager.clean();
  }

  public ISitemapManager getSitemapManager() {
    return this.sitemapManager;
  }
  public void setSitemapManager(ISitemapManager sitemapManager) {
    this.sitemapManager = sitemapManager;
  }
  public SitemapPluginBundle getSitemapPluginBundle() {
    return this.sitemapPluginBundle;
  }
  public void setSitemapPluginBundle(SitemapPluginBundle sitemapPluginBundle) {
    this.sitemapPluginBundle = sitemapPluginBundle;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.CreateSitemapAction
 * JD-Core Version:    0.6.1
 */