package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.IAdminThemeInfoFileLoader;
import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.app.base.core.service.solution.InstallUtil;
import com.enation.eop.resource.IAdminThemeManager;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.model.AdminTheme;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AdminThemeInstaller
  implements IInstaller
{
  private final Logger logger = Logger.getLogger(getClass());
  private ISiteManager siteManager;
  private IAdminThemeManager adminThemeManager;
  private IAdminThemeInfoFileLoader adminThemeInfoFileLoader;

  public void install(String productId, Node fragment)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("user install admintheme[" + fragment + "] from " + productId);
    }

    if (fragment == null) throw new RuntimeException("install admintheme error[node is null]");

    NodeList themeList = fragment.getChildNodes();

    install(themeList, productId);
  }

  private void install(Element themeNode, String productId)
  {
    String isdefault = themeNode.getAttribute("default");
    AdminTheme adminTheme = new AdminTheme();
    String path = themeNode.getAttribute("id");
    InstallUtil.putMessaage("正在安装后台主题" + path + "...");
    String commonAttr = themeNode.getAttribute("isCommonTheme");
    commonAttr = commonAttr == null ? "" : commonAttr;
    Boolean isCommonTheme = Boolean.valueOf(commonAttr.toUpperCase().equals("TRUE"));
    Document iniFileDoc = this.adminThemeInfoFileLoader.load(productId, path, isCommonTheme);
    Node themeN = null;
    try {
      themeN = iniFileDoc.getFirstChild();
      if (themeN == null) throw new RuntimeException("adminthem node is null"); 
    }
    catch (Exception e) { e.printStackTrace(); }

    Node authornode = null;
    try {
      NodeList list = ((Element)themeN).getElementsByTagName("author");
      if ((list == null) || (list.item(0) == null)) throw new RuntimeException("author node is null");
      authornode = list.item(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String author = authornode.getTextContent();

    Node versionnode = null;
    try {
      NodeList list = ((Element)themeN).getElementsByTagName("version");
      if ((list == null) || (list.item(0) == null)) throw new RuntimeException("author node is null");
      versionnode = list.item(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String version = versionnode.getTextContent();
    adminTheme.setPath(path);
    adminTheme.setThemename(themeNode.getAttribute("name"));
    adminTheme.setThumb("preview.png");
    adminTheme.setAuthor(author);
    adminTheme.setVersion(version);
    try
    {
      Integer themeid = this.adminThemeManager.add(adminTheme, isCommonTheme.booleanValue());
      if ("yes".equals(isdefault)) {
        if (this.logger.isDebugEnabled())
          this.logger.debug("change theme[" + themeid + "] ");
        this.siteManager.changeAdminTheme(themeid);
      }
      InstallUtil.putMessaage("完成!");
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("install admin theme error");
    }
  }

  private void install(NodeList nodeList, String productId)
  {
    int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == 1)
        install((Element)node, productId);
    }
  }

  public void setAdminThemeInfoFileLoader(IAdminThemeInfoFileLoader adminThemeInfoFileLoader)
  {
    this.adminThemeInfoFileLoader = adminThemeInfoFileLoader;
  }

  public IAdminThemeManager getAdminThemeManager() {
    return this.adminThemeManager;
  }

  public void setAdminThemeManager(IAdminThemeManager adminThemeManager) {
    this.adminThemeManager = adminThemeManager;
  }

  public IAdminThemeInfoFileLoader getAdminThemeInfoFileLoader() {
    return this.adminThemeInfoFileLoader;
  }

  public ISiteManager getSiteManager() {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.AdminThemeInstaller
 * JD-Core Version:    0.6.1
 */