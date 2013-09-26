package com.enation.app.base;

import com.enation.app.base.core.service.solution.impl.SqlExportService;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.App;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.cache.CacheFactory;
import com.enation.framework.cache.ICache;
import com.enation.framework.database.IDBRouter;
import com.enation.framework.database.ISqlFileExecutor;
import java.util.List;
import org.dom4j.Document;

public class BaseApp extends App
{
  private IDBRouter baseDBRouter;
  private SqlExportService sqlExportService;
  private ISqlFileExecutor sqlFileExecutor;

  public BaseApp()
  {
    this.tables.add("adv");
    this.tables.add("access");
    this.tables.add("adcolumn");
    this.tables.add("admintheme");
    this.tables.add("auth_action");
    this.tables.add("friends_link");
    this.tables.add("guestbook");
    this.tables.add("menu");
    this.tables.add("theme");
    this.tables.add("themeuri");

    this.tables.add("adminuser");
    this.tables.add("role");
    this.tables.add("role_auth");
    this.tables.add("settings");
    this.tables.add("site_menu");
    this.tables.add("user_role");
    this.tables.add("smtp");
  }

  public String getId()
  {
    return "base";
  }

  public String getName() {
    return "base应用";
  }

  public String getNameSpace() {
    return "/core";
  }

  public void saasInstall()
  {
    this.baseDBRouter.doSaasInstall("file:com/enation/app/base/base.xml");
  }

  @Deprecated
  public String dumpSql()
  {
    return "";
  }

  public String dumpSql(Document setup) {
    StringBuffer sql = new StringBuffer();

    sql.append(this.sqlExportService.dumpSql(this.tables, "clean", setup));
    sql.append(createBaseAppSql());
    return sql.toString();
  }

  private String createBaseAppSql()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    String logofile = site.getLogofile();
    String icofile = site.getIcofile();
    String upath = EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath();

    if (icofile != null) {
      icofile = icofile.replaceAll(upath, "fs:");
    }
    if (logofile != null) {
      logofile = logofile.replaceAll(upath, "fs:");
    }
    String sql = "update eop_site set sitename='" + site.getSitename() + "',logofile='" + logofile + "',icofile='" + icofile + "',keywords='" + site.getKeywords() + "',descript='" + site.getDescript() + "' where userid=<userid> and id=<siteid>;\n";

    return sql;
  }

  public void install()
  {
    doInstall("file:com/enation/app/base/base.xml");
  }

  protected void cleanCache() {
    super.cleanCache();

    CacheFactory.getCache("widgetCache").remove("widget_" + this.userid + "_" + this.siteid);

    CacheFactory.getCache("sitemap").remove("sitemap_" + this.userid + "_" + this.siteid);

    CacheFactory.getCache("themeUriCache").remove("theme_uri_list_" + this.userid + "_" + this.siteid);

    CacheFactory.getCache("siteMenuList").remove("siteMenuList_" + this.userid + "_" + this.siteid);
  }

  public void sessionDestroyed(String seesionid, EopSite site)
  {
  }

  public IDBRouter getBaseDBRouter()
  {
    return this.baseDBRouter;
  }

  public void setBaseDBRouter(IDBRouter baseDBRouter) {
    this.baseDBRouter = baseDBRouter;
  }

  public IDBRouter getBaseSaasDBRouter() {
    return this.baseDBRouter;
  }

  public void setBaseSaasDBRouter(IDBRouter baseSaasDBRouter) {
    this.baseDBRouter = baseSaasDBRouter;
  }

  public ISqlFileExecutor getSqlFileExecutor() {
    return this.sqlFileExecutor;
  }

  public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
    this.sqlFileExecutor = sqlFileExecutor;
  }

  public SqlExportService getSqlExportService() {
    return this.sqlExportService;
  }

  public void setSqlExportService(SqlExportService sqlExportService) {
    this.sqlExportService = sqlExportService;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.BaseApp
 * JD-Core Version:    0.6.1
 */