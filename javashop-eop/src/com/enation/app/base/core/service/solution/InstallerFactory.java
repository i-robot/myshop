package com.enation.app.base.core.service.solution;

public class InstallerFactory
{
  public static final String TYPE_APP = "apps";
  public static final String TYPE_MENU = "menus";
  public static final String TYPE_ADMINTHEME = "adminThemes";
  public static final String TYPE_THEME = "themes";
  public static final String TYPE_URL = "urls";
  public static final String TYPE_WIDGET = "widgets";
  public static final String TYPE_INDEX_ITEM = "indexitems";
  public static final String TYPE_COMPONENT = "components";
  public static final String TYPE_SITE = "site";
  private IInstaller menuInstaller;
  private IInstaller adminThemeInstaller;
  private IInstaller themeInstaller;
  private IInstaller uriInstaller;
  private IInstaller widgetInstaller;
  private IInstaller appInstaller;
  private IInstaller indexItemInstaller;
  private IInstaller componentInstaller;
  private IInstaller siteInstaller;

  public IInstaller getInstaller(String type)
  {
    if ("apps".equals(type)) {
      return this.appInstaller;
    }

    if ("menus".equals(type)) {
      return this.menuInstaller;
    }

    if ("adminThemes".equals(type)) {
      return this.adminThemeInstaller;
    }

    if ("themes".equals(type)) {
      return this.themeInstaller;
    }

    if ("urls".equals(type)) {
      return this.uriInstaller;
    }

    if ("widgets".equals(type)) {
      return this.widgetInstaller;
    }

    if ("indexitems".equals(type)) {
      return this.indexItemInstaller;
    }

    if ("components".equals(type)) {
      return this.componentInstaller;
    }

    if ("site".equals(type)) {
      return this.siteInstaller;
    }
    throw new RuntimeException(" get Installer instance error[incorrect type param]");
  }

  public void setMenuInstaller(IInstaller menuInstaller) {
    this.menuInstaller = menuInstaller;
  }

  public void setAdminThemeInstaller(IInstaller adminThemeInstaller) {
    this.adminThemeInstaller = adminThemeInstaller;
  }

  public void setThemeInstaller(IInstaller themeInstaller) {
    this.themeInstaller = themeInstaller;
  }

  public void setUriInstaller(IInstaller uriInstaller) {
    this.uriInstaller = uriInstaller;
  }

  public void setWidgetInstaller(IInstaller widgetInstaller) {
    this.widgetInstaller = widgetInstaller;
  }

  public IInstaller getAppInstaller() {
    return this.appInstaller;
  }

  public void setAppInstaller(IInstaller appInstaller) {
    this.appInstaller = appInstaller;
  }

  public void setIndexItemInstaller(IInstaller indexItemInstaller) {
    this.indexItemInstaller = indexItemInstaller;
  }

  public void setComponentInstaller(IInstaller componentInstaller) {
    this.componentInstaller = componentInstaller;
  }

  public IInstaller getSiteInstaller() {
    return this.siteInstaller;
  }

  public void setSiteInstaller(IInstaller siteInstaller) {
    this.siteInstaller = siteInstaller;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.InstallerFactory
 * JD-Core Version:    0.6.1
 */