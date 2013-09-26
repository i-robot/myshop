package com.enation.app.base.core.action;

import com.enation.eop.resource.IAdminThemeManager;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.model.AdminTheme;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.action.WWAction;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class SiteAdminThemeAction extends WWAction
{
  private IAdminThemeManager adminThemeManager;
  private ISiteManager siteManager;
  private List<AdminTheme> listTheme;
  private AdminTheme adminTheme;
  private EopSite eopSite;
  private String previewpath;
  private String previewBasePath;
  private Integer themeid;

  public String execute()
    throws Exception
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    String contextPath = getRequest().getContextPath();
    this.previewBasePath = (contextPath + "/adminthemes/");
    this.adminTheme = this.adminThemeManager.get(site.getAdminthemeid());
    this.listTheme = this.adminThemeManager.list();
    this.previewpath = (this.previewBasePath + this.adminTheme.getPath() + "/preview.png");
    return "success";
  }

  public String change() throws Exception {
    this.siteManager.changeAdminTheme(this.themeid);
    return execute();
  }

  public EopSite getEopSite() {
    return this.eopSite;
  }

  public void setEopSite(EopSite eopSite) {
    this.eopSite = eopSite;
  }

  public ISiteManager getSiteManager() {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }

  public String getPreviewpath() {
    return this.previewpath;
  }

  public void setPreviewpath(String previewpath) {
    this.previewpath = previewpath;
  }

  public String getPreviewBasePath() {
    return this.previewBasePath;
  }

  public void setPreviewBasePath(String previewBasePath) {
    this.previewBasePath = previewBasePath;
  }

  public Integer getThemeid() {
    return this.themeid;
  }

  public void setThemeid(Integer themeid) {
    this.themeid = themeid;
  }

  public List<AdminTheme> getListTheme() {
    return this.listTheme;
  }

  public void setListTheme(List<AdminTheme> listTheme) {
    this.listTheme = listTheme;
  }

  public AdminTheme getAdminTheme() {
    return this.adminTheme;
  }

  public void setAdminTheme(AdminTheme adminTheme) {
    this.adminTheme = adminTheme;
  }

  public IAdminThemeManager getAdminThemeManager() {
    return this.adminThemeManager;
  }

  public void setAdminThemeManager(IAdminThemeManager adminThemeManager) {
    this.adminThemeManager = adminThemeManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.SiteAdminThemeAction
 * JD-Core Version:    0.6.1
 */