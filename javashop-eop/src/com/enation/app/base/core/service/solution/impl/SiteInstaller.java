package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SiteInstaller
  implements IInstaller
{
  private ISiteManager siteManager;

  private boolean setProperty(EopSite site, String name, String value)
  {
    try
    {
      BeanUtils.setProperty(site, name, value);
      return true; } catch (Exception e) {
    }
    return false;
  }

  public void install(String productId, Node fragment)
  {
    EopSite site = EopContext.getContext().getCurrentSite();

    NodeList nodeList = fragment.getChildNodes();
    int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == 1) {
        Element element = (Element)node;
        String name = element.getAttribute("name");
        String value = element.getAttribute("value");
        setProperty(site, name, value);
      }
    }
    this.siteManager.edit(site);
  }

  public ISiteManager getSiteManager() {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SiteInstaller
 * JD-Core Version:    0.6.1
 */