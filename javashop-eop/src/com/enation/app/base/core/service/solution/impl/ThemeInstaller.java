package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.app.base.core.service.solution.InstallUtil;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.model.Theme;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.util.FileUtil;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ThemeInstaller
  implements IInstaller
{
  private IInstaller borderInstaller;
  private IThemeManager themeManager;
  private ISiteManager siteManager;
  private String productId;
  protected final Logger logger = Logger.getLogger(getClass());

  public void install(String productId, Node fragment)
  {
    this.productId = productId;

    String contextPath = EopContext.getContext().getContextPath();

    String targetPath = EopSetting.EOP_PATH + contextPath + "/themes/";
    String sourcePath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/themes/";

    FileUtil.copyFile(sourcePath + "findpass_email_template.html", targetPath + "findpass_email_template.html");
    FileUtil.copyFile(sourcePath + "order_create_email_template.html", targetPath + "order_create_email_template.html");
    FileUtil.copyFile(sourcePath + "price_filter.xml", targetPath + "price_filter.xml");
    FileUtil.copyFile(sourcePath + "reg_email_template.html", targetPath + "reg_email_template.html");
    FileUtil.copyFile(sourcePath + "success.html", targetPath + "success.html");
    FileUtil.copyFile(sourcePath + "widgets_default.xml", targetPath + "widgets_default.xml");

    NodeList themeList = fragment.getChildNodes();
    install(themeList);
  }

  private void install(Element themeNode) {
    String isdefault = themeNode.getAttribute("default");
    Theme theme = new Theme();
    theme.setProductId(this.productId);
    theme.setPath(themeNode.getAttribute("id"));
    theme.setThemename(themeNode.getAttribute("name"));
    theme.setThumb("preview.png");
    theme.setSiteid(Integer.valueOf(0));
    InstallUtil.putMessaage("安装主题" + theme.getThemename() + "...");
    String commonAttr = themeNode.getAttribute("isCommonTheme");
    commonAttr = commonAttr == null ? "" : commonAttr;
    Boolean isCommonTheme = Boolean.valueOf(commonAttr.toUpperCase().equals("TRUE"));
    Integer themeid = this.themeManager.add(theme, isCommonTheme.booleanValue());
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("install " + theme.getThemename() + ",default :" + isdefault);
    }
    if ("yes".equals(isdefault)) {
      if (this.logger.isDebugEnabled())
        this.logger.debug("change theme[" + themeid + "] ");
      this.siteManager.changeTheme(themeid);
    }

    this.borderInstaller.install(this.productId, themeNode);
    InstallUtil.putMessaage("完成!");
  }

  private void install(NodeList nodeList) {
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == 1)
        install((Element)node);
    }
  }

  public IInstaller getBorderInstaller()
  {
    return this.borderInstaller;
  }

  public void setBorderInstaller(IInstaller borderInstaller) {
    this.borderInstaller = borderInstaller;
  }

  public IThemeManager getThemeManager() {
    return this.themeManager;
  }

  public void setThemeManager(IThemeManager themeManager) {
    this.themeManager = themeManager;
  }

  public ISiteManager getSiteManager() {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.ThemeInstaller
 * JD-Core Version:    0.6.1
 */