package com.enation.app.base.core.service.solution.impl;

import com.enation.eop.resource.IIndexItemManager;
import com.enation.eop.resource.IMenuManager;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.IThemeUriManager;
import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.EopApp;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.IndexItem;
import com.enation.eop.resource.model.Menu;
import com.enation.eop.resource.model.Theme;
import com.enation.eop.resource.model.ThemeUri;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.util.StringUtil;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ProfileCreator
{
  private IThemeUriManager themeUriManager;
  private IMenuManager menuManager;
  private IThemeManager themeManager;
  private IIndexItemManager indexItemManager;
  private IUserManager userManager;
  private ISiteManager siteManager;

  public void createProfile(String path)
  {
    Element product = new Element("product");

    List<EopApp> appList = this.siteManager.getSiteApps();

    Element apps = new Element("apps");
    for (EopApp app : appList)
    {
      Element appEl = new Element("app");
      appEl.setAttribute("id", app.getAppid());
      appEl.setAttribute("version", app.getVersion());
      apps.addContent(appEl);
    }

    product.addContent(apps);

    Element site = new Element("site");
    fillSiteElement(site);
    product.addContent(site);

    Element urlsEl = new Element("urls");
    fillUrlElement(urlsEl);
    product.addContent(urlsEl);

    Element menusEl = new Element("menus");
    fillMenuElement(menusEl);
    product.addContent(menusEl);

    Element themesEl = new Element("themes");
    fillThemesElement(themesEl);
    product.addContent(themesEl);

    Element indexItemEl = new Element("indexitems");
    fillIndexItemElement(indexItemEl);
    product.addContent(indexItemEl);

    Document pfDocument = new Document(product);
    outputDocumentToFile(pfDocument, path);
  }

  private void addSiteElement(Element site, String name, String value)
  {
    if (value != null) {
      Element element = new Element("field");
      element.setAttribute("name", name);
      element.setAttribute("value", value);
      site.addContent(element);
    }
  }

  private void fillSiteElement(Element site)
  {
    EopSite eopSite = EopContext.getContext().getCurrentSite();
    addSiteElement(site, "sitename", eopSite.getSitename());
    addSiteElement(site, "title", eopSite.getTitle());
    addSiteElement(site, "keywords", eopSite.getKeywords());
    addSiteElement(site, "descript", eopSite.getDescript());
    addSiteElement(site, "copyright", eopSite.getCopyright());
    addSiteElement(site, "logofile", eopSite.getLogofile());
    addSiteElement(site, "bklogofile", eopSite.getBklogofile());
    addSiteElement(site, "bkloginpicfile", eopSite.getBkloginpicfile());
    addSiteElement(site, "icofile", eopSite.getIcofile());
    addSiteElement(site, "username", eopSite.getUsername());
    addSiteElement(site, "usersex", eopSite.getUsersex() == null ? null : eopSite.getUsersex().toString());
    addSiteElement(site, "usertel", eopSite.getUsertel());
    addSiteElement(site, "usermobile", eopSite.getUsermobile());
    addSiteElement(site, "usertel1", eopSite.getUsertel1());
    addSiteElement(site, "useremail", eopSite.getUseremail());
    addSiteElement(site, "state", eopSite.getState() == null ? null : eopSite.getState().toString());
    addSiteElement(site, "qqlist", eopSite.getQqlist());
    addSiteElement(site, "msnlist", eopSite.getMsnlist());
    addSiteElement(site, "wwlist", eopSite.getWwlist());
    addSiteElement(site, "tellist", eopSite.getTellist());
    addSiteElement(site, "worktime", eopSite.getWorktime());
    addSiteElement(site, "address", eopSite.getAddress());
    addSiteElement(site, "zipcode", eopSite.getZipcode());
    addSiteElement(site, "linkman", eopSite.getLinkman());
    addSiteElement(site, "email", eopSite.getEmail());
    addSiteElement(site, "siteon", eopSite.getSiteon().toString() == null ? null : eopSite.getSiteon().toString());
    addSiteElement(site, "closereson", eopSite.getClosereson());
  }

  private void fillUrlElement(Element uris)
  {
    List<ThemeUri> uriList = this.themeUriManager.list();

    for (ThemeUri themeUri : uriList)
    {
      String uri = themeUri.getUri();
      String path = themeUri.getPath();
      String pagename = themeUri.getPagename();
      pagename = pagename == null ? "" : pagename;
      Integer sitemaptype = Integer.valueOf(themeUri.getSitemaptype());
      Integer point = Integer.valueOf(themeUri.getPoint());
      Element urlEl = new Element("url");
      urlEl.setAttribute("from", uri);
      urlEl.setAttribute("to", path);
      urlEl.setAttribute("name", pagename);
      urlEl.setAttribute("point", point.toString());
      urlEl.setAttribute("sitemaptype", sitemaptype.toString());
      uris.addContent(urlEl);
    }
  }

  private void fillMenuElement(Element menuParentEl)
  {
    List menuTree = this.menuManager.getMenuTree(Integer.valueOf(0));
    fillChildMenu(menuTree, menuParentEl);
  }

  private void fillChildMenu(List<Menu> menuList, Element parentEl)
  {
    for (Menu menu : menuList)
      if (menu.getMenutype().intValue() != 1) {
        Element menuEl = new Element("menu");
        menuEl.setAttribute("text", menu.getTitle());
        String url = menu.getUrl();
        if (!StringUtil.isEmpty(url)) {
          menuEl.setAttribute("url", url);
        }

        String target = menu.getTarget();
        if (!StringUtil.isEmpty(target)) {
          menuEl.setAttribute("target", target);
        }

        List children = menu.getChildren();
        if ((children != null) && (!children.isEmpty())) {
          fillChildMenu(children, menuEl);
        }
        parentEl.addContent(menuEl);
      }
  }

  private void fillThemesElement(Element parentEl)
  {
    List<Theme> themeList = this.themeManager.list();
    EopSite site = EopContext.getContext().getCurrentSite();
    for (Theme theme : themeList) {
      Element themeEl = new Element("theme");
      themeEl.setAttribute("id", theme.getPath());
      themeEl.setAttribute("name", theme.getThemename());
      if (site.getThemeid().intValue() == theme.getId().intValue()) {
        themeEl.setAttribute("default", "yes");
      }
      parentEl.addContent(themeEl);
    }
  }

  private void fillIndexItemElement(Element parentEl)
  {
    List<IndexItem> itemList = this.indexItemManager.list();
    if (itemList == null)
      return;
    for (IndexItem item : itemList) {
      Element itemEl = new Element("item");
      Element titleEl = new Element("title");
      titleEl.setText(item.getTitle());
      itemEl.addContent(titleEl);

      Element urlEl = new Element("url");
      urlEl.setText(item.getUrl());
      itemEl.addContent(urlEl);
      parentEl.addContent(itemEl);
    }
  }

  private void outputDocumentToFile(Document myDocument, String path) {
    try {
      Format format = Format.getCompactFormat();
      format.setEncoding("UTF-8");
      format.setIndent("    ");
      XMLOutputter outputter = new XMLOutputter(format);
      outputter.output(myDocument, new FileOutputStream(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public IThemeUriManager getThemeUriManager() {
    return this.themeUriManager;
  }

  public void setThemeUriManager(IThemeUriManager themeUriManager) {
    this.themeUriManager = themeUriManager;
  }

  public IMenuManager getMenuManager() {
    return this.menuManager;
  }

  public void setMenuManager(IMenuManager menuManager) {
    this.menuManager = menuManager;
  }

  public IThemeManager getThemeManager() {
    return this.themeManager;
  }

  public void setThemeManager(IThemeManager themeManager) {
    this.themeManager = themeManager;
  }

  public IIndexItemManager getIndexItemManager() {
    return this.indexItemManager;
  }

  public void setIndexItemManager(IIndexItemManager indexItemManager) {
    this.indexItemManager = indexItemManager;
  }

  public ISiteManager getSiteManager() {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }

  public IUserManager getUserManager() {
    return this.userManager;
  }

  public void setUserManager(IUserManager userManager) {
    this.userManager = userManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.ProfileCreator
 * JD-Core Version:    0.6.1
 */