package com.enation.app.base.core.action;

import com.enation.app.base.core.model.MultiSite;
import com.enation.app.base.core.service.IMultiSiteManager;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.Theme;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

public class MultiSiteAction extends WWAction
{
  private IMultiSiteManager multiSiteManager;
  private EopSite eopSite;
  private MultiSite site;
  private String parentname;
  private int siteid;
  private int multi_site;
  private IThemeManager themeManager;
  private List<Theme> themeList;
  private String previewBasePath;
  private String domain;
  private List<MultiSite> siteList;

  public String execute()
  {
    this.eopSite = EopContext.getContext().getCurrentSite();

    this.siteList = this.multiSiteManager.list();
    return "main";
  }

  public String save()
  {
    try
    {
      if (this.multi_site == 1) {
        this.multiSiteManager.open(this.domain);
        this.json = "{result:1,message:'多站点功能开启成功'}";
      } else {
        this.multiSiteManager.close();
        this.json = "{result:1,message:'多站点功能关闭成功'}";
      }
    }
    catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e.fillInStackTrace());
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String listJson()
  {
    try
    {
      JSONArray jsonArray1 = JSONArray.fromObject(this.multiSiteManager.list());
      this.json = ("{'result':'0','data':" + jsonArray1.toString() + "}");
    } catch (Exception e) {
      this.json = "{'result':'1'}";
    }
    return "json_message";
  }

  public String list() {
    this.siteList = this.multiSiteManager.list();
    return "list";
  }

  public String add() {
    this.themeList = this.themeManager.list(0);
    String contextPath = EopContext.getContext().getContextPath();
    this.previewBasePath = (EopSetting.IMG_SERVER_DOMAIN + contextPath + "/themes/");
    return "add";
  }

  public String edit() {
    this.site = this.multiSiteManager.get(this.siteid);
    if (this.site.getParentid().intValue() != 0)
      this.parentname = this.multiSiteManager.get(this.site.getParentid().intValue()).getName();
    else
      this.parentname = "顶级站点";
    return "edit";
  }

  public String addSave() {
    try {
      this.multiSiteManager.add(this.site);
      this.msgs.add("新增子站点成功");
    }
    catch (RuntimeException e) {
      this.msgs.add("操作失败：" + e.getMessage());
    }
    this.urls.put("多站点管理", "multiSite.do");
    return "message";
  }

  public String editSave() {
    this.multiSiteManager.update(this.site);
    this.msgs.add("编辑子站点成功");
    this.urls.put("多站点管理", "multiSite.do");
    return "message";
  }

  public String delete() {
    try {
      this.multiSiteManager.delete(this.siteid);
      this.msgs.add("站点成功删除");
    }
    catch (Exception e) {
      this.msgs.add("站点删除失败");
    }
    this.urls.put("多站点管理", "multiSite.do");
    return "message";
  }

  public IMultiSiteManager getMultiSiteManager() {
    return this.multiSiteManager;
  }

  public void setMultiSiteManager(IMultiSiteManager multiSiteManager) {
    this.multiSiteManager = multiSiteManager;
  }

  public MultiSite getSite() {
    return this.site;
  }

  public void setSite(MultiSite site) {
    this.site = site;
  }

  public int getSiteid() {
    return this.siteid;
  }

  public void setSiteid(int siteid) {
    this.siteid = siteid;
  }

  public IThemeManager getThemeManager() {
    return this.themeManager;
  }

  public void setThemeManager(IThemeManager themeManager) {
    this.themeManager = themeManager;
  }

  public List<Theme> getThemeList() {
    return this.themeList;
  }

  public void setThemeList(List<Theme> themeList) {
    this.themeList = themeList;
  }

  public String getPreviewBasePath() {
    return this.previewBasePath;
  }

  public void setPreviewBasePath(String previewBasePath) {
    this.previewBasePath = previewBasePath;
  }

  public List<MultiSite> getSiteList() {
    return this.siteList;
  }

  public void setSiteList(List<MultiSite> siteList) {
    this.siteList = siteList;
  }

  public EopSite getEopSite() {
    return this.eopSite;
  }

  public void setEopSite(EopSite eopSite) {
    this.eopSite = eopSite;
  }

  public String getDomain() {
    return this.domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public int getMulti_site() {
    return this.multi_site;
  }

  public void setMulti_site(int multiSite) {
    this.multi_site = multiSite;
  }

  public String getParentname() {
    return this.parentname;
  }

  public void setParentname(String parentname) {
    this.parentname = parentname;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.MultiSiteAction
 * JD-Core Version:    0.6.1
 */