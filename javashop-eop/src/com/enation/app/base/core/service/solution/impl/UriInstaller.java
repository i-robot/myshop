package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.model.SiteMapUrl;
import com.enation.app.base.core.service.ISitemapManager;
import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.app.base.core.service.solution.InstallUtil;
import com.enation.eop.resource.IThemeUriManager;
import com.enation.eop.resource.model.ThemeUri;
import com.enation.framework.util.StringUtil;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UriInstaller
  implements IInstaller
{
  protected final Logger logger = Logger.getLogger(getClass());
  private IThemeUriManager themeUriManager;
  private ISitemapManager sitemapManager;

  public void install(String productId, Node fragment)
  {
    NodeList uriList = fragment.getChildNodes();
    InstallUtil.putMessaage("正在安装uri映射...");
    installUri(uriList);
    InstallUtil.putMessaage("uri映射安装完成!");
  }

  private void installUri(NodeList nodeList)
  {
    int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == 1)
        installUri((Element)node);
    }
  }

  private void installUri(Element ele)
  {
    try
    {
      ThemeUri themeUri = new ThemeUri();

      themeUri.setUri(ele.getAttribute("from"));
      themeUri.setPath(ele.getAttribute("to"));
      String name = ele.getAttribute("name");
      String point = ele.getAttribute("point");
      String sitemaptype = ele.getAttribute("sitemaptype");
      String cache = ele.getAttribute("cache");

      if (name != null) {
        themeUri.setPagename(name);
      }
      if (!StringUtil.isEmpty(point)) {
        themeUri.setPoint(Integer.valueOf(point).intValue());
      }

      if (!StringUtil.isEmpty(sitemaptype)) {
        themeUri.setSitemaptype(Integer.valueOf(sitemaptype).intValue());
      }

      if ((!StringUtil.isEmpty(cache)) && ("1".equals(cache))) {
        themeUri.setHttpcache(1);
      }

      this.themeUriManager.add(themeUri);

      if ("1".equals(sitemaptype)) {
        SiteMapUrl url = new SiteMapUrl();
        url.setLoc(ele.getAttribute("from"));
        url.setLastmod(Long.valueOf(System.currentTimeMillis()));
        this.sitemapManager.addUrl(url);
      }
      NodeList children = ele.getChildNodes();

      if (children != null)
        installUri(children);
    }
    catch (Exception e)
    {
      this.logger.error(e.getMessage(), e);
      e.printStackTrace();
      throw new RuntimeException("install uri error");
    }
  }

  public IThemeUriManager getThemeUriManager()
  {
    return this.themeUriManager;
  }

  public void setThemeUriManager(IThemeUriManager themeUriManager)
  {
    this.themeUriManager = themeUriManager;
  }

  public ISitemapManager getSitemapManager()
  {
    return this.sitemapManager;
  }

  public void setSitemapManager(ISitemapManager sitemapManager)
  {
    this.sitemapManager = sitemapManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.UriInstaller
 * JD-Core Version:    0.6.1
 */