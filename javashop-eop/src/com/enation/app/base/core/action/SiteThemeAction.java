package com.enation.app.base.core.action;

import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.Theme;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.action.WWAction;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class SiteThemeAction extends WWAction
{
  private List<Theme> listTheme;
  private Theme theme;
  private IThemeManager themeManager;
  private EopSite eopSite;
  private ISiteManager siteManager;
  private String previewpath;
  private String previewBasePath;
  private Integer themeid;

  public String execute()
    throws Exception
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String ctx = request.getContextPath();
    EopSite site = EopContext.getContext().getCurrentSite();
    String contextPath = EopContext.getContext().getContextPath();

    this.previewBasePath = (ctx + contextPath + "/themes/");

    this.theme = this.themeManager.getTheme(site.getThemeid());
    this.listTheme = this.themeManager.list();
    this.previewpath = (this.previewBasePath + this.theme.getPath() + "/preview.png");
    return "success";
  }

  public String add()
  {
    return "input";
  }

  public String save() {
    this.msgs.add("模板创建成功");
    this.urls.put("模板列表", "siteTheme.do");
    this.themeManager.addBlank(this.theme);
    return "message";
  }

  public String change() throws Exception {
    this.siteManager.changeTheme(this.themeid);
    return execute();
  }

  public List<Theme> getListTheme() {
    return this.listTheme;
  }

  public void setListTheme(List<Theme> listTheme) {
    this.listTheme = listTheme;
  }

  public Theme getTheme() {
    return this.theme;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  public IThemeManager getThemeManager() {
    return this.themeManager;
  }

  public void setThemeManager(IThemeManager themeManager) {
    this.themeManager = themeManager;
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
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.SiteThemeAction
 * JD-Core Version:    0.6.1
 */