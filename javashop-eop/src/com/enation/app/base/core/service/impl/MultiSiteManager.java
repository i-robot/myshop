package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.MultiSite;
import com.enation.app.base.core.service.IMultiSiteManager;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopSiteDomain;
import com.enation.eop.resource.model.Theme;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MultiSiteManager extends BaseSupport<MultiSite>
  implements IMultiSiteManager
{
  private IThemeManager themeManager;
  private ISiteManager siteManager;

  public IThemeManager getThemeManager()
  {
    return this.themeManager;
  }

  public void setThemeManager(IThemeManager themeManager) {
    this.themeManager = themeManager;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void open(String domain)
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    if (site.getMulti_site().intValue() == 1) return;

    Integer siteid = site.getId();
    String sql = "update eop_site set multi_site=1 where id=?";
    this.daoSupport.execute(sql, new Object[] { siteid });
    site.setMulti_site(Integer.valueOf(1));

    sql = "select count(0) from site";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
    if (count > 0) {
      sql = "update site set domain=? where code=?";
      this.baseDaoSupport.execute(sql, new Object[] { domain, Integer.valueOf(100000) });
      return;
    }

    MultiSite mainChildSite = new MultiSite();
    mainChildSite.setCode(Integer.valueOf(100000));
    mainChildSite.setSitelevel(Integer.valueOf(1));
    mainChildSite.setDomain(domain);
    mainChildSite.setName(site.getSitename());
    mainChildSite.setParentid(Integer.valueOf(0));
    mainChildSite.setThemeid(site.getThemeid());
    this.baseDaoSupport.insert("site", mainChildSite);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void close()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    Integer siteid = site.getId();
    String sql = "update eop_site set multi_site=0 where id=?";
    this.daoSupport.execute(sql, new Object[] { siteid });

    site.setMulti_site(Integer.valueOf(0));
  }

  private int createCode(int maxcode, int level)
  {
    if (level == 1) {
      return maxcode + 100000;
    }

    if (level == 2) {
      return maxcode + 1000;
    }

    if (level == 3) {
      return maxcode + 10;
    }

    return 0;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void add(MultiSite site)
  {
    MultiSite parent = get(site.getParentid().intValue());

    String sql = "select max(code) code from site where parentid=? ";
    int maxcode = this.baseDaoSupport.queryForInt(sql, new Object[] { site.getParentid() });
    maxcode = maxcode == 0 ? (maxcode = parent.getCode().intValue()) : maxcode;
    int level = parent.getSitelevel().intValue() + 1;
    site.setCode(Integer.valueOf(createCode(maxcode, level)));
    site.setSitelevel(Integer.valueOf(level));

    this.baseDaoSupport.insert("site", site);
    int siteid = this.baseDaoSupport.getLastId("site");

    Integer userid = EopContext.getContext().getCurrentSite().getUserid();
    EopSiteDomain eopSiteDomain = new EopSiteDomain();
    eopSiteDomain.setUserid(userid);
    eopSiteDomain.setDomain(site.getDomain());
    eopSiteDomain.setSiteid(EopContext.getContext().getCurrentSite().getId());

    this.siteManager.addDomain(eopSiteDomain);
    try
    {
      site.setSiteid(Integer.valueOf(siteid));
      Theme theme = this.themeManager.getTheme(site.getThemeid());
      String contextPath = EopContext.getContext().getContextPath();

      String basePath = EopSetting.IMG_SERVER_PATH + contextPath + "/themes/" + theme.getPath();

      String targetPath = EopSetting.IMG_SERVER_PATH + contextPath + "/themes/" + theme.getPath() + "_" + siteid;

      FileUtil.copyFolder(basePath, targetPath);

      basePath = EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath();

      targetPath = EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath() + "_" + siteid;

      FileUtil.copyFolder(basePath, targetPath);

      theme.setPath(theme.getPath() + "_" + siteid);
      theme.setSiteid(Integer.valueOf(siteid));
      theme.setId(null);
      this.baseDaoSupport.insert("theme", theme);
      int themeid = this.baseDaoSupport.getLastId("theme");
      site.setThemeid(Integer.valueOf(themeid));
      update(site);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("创建主题出错");
    }
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(int id)
  {
    MultiSite childsite = get(id);
    List<Theme> list = this.themeManager.list(id);
    String contextPath = EopContext.getContext().getContextPath();
    for (Theme theme : list)
    {
      String targetPath = EopSetting.IMG_SERVER_PATH + contextPath + "/themes/" + theme.getPath() + "_" + id;

      FileUtil.removeFile(new File(targetPath));

      targetPath = EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath() + "_" + id;

      FileUtil.removeFile(new File(targetPath));
    }

    this.siteManager.deleteDomain(childsite.getDomain());

    this.baseDaoSupport.execute("delete from theme where siteid = ?", new Object[] { Integer.valueOf(id) });

    this.baseDaoSupport.execute("delete from site where siteid = ?", new Object[] { Integer.valueOf(id) });
  }

  public List list()
  {
    List<Map> list = this.baseDaoSupport.queryForList("select * from site ", new Object[0]);
    List siteList = new ArrayList();
    for (Map site : list) {
      Long parentid = (Long)site.get("parentid");
      if (parentid.intValue() == 0) {
        putChildren(site, list);
        siteList.add(site);
      }
    }
    return siteList;
  }

  public void putChildren(Map site, List<Map> sitelist) {
    List children = new ArrayList();
    for (Map child : sitelist) {
      Long parentid = (Long)child.get("parentid");
      Long siteid = (Long)site.get("siteid");
      if (parentid.compareTo(siteid) == 0) {
        putChildren(child, sitelist);
        children.add(child);
      }
    }
    site.put("children", children);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void update(MultiSite site) {
    MultiSite site_old = get(site.getSiteid().intValue());
    String domain = site_old.getDomain();
    this.siteManager.editDomain(domain, site.getDomain());

    this.baseDaoSupport.update("site", site, "siteid = " + site.getSiteid());
  }

  public MultiSite get(int id)
  {
    return (MultiSite)this.baseDaoSupport.queryForObject("select * from site where siteid = ?", MultiSite.class, new Object[] { Integer.valueOf(id) });
  }

  public ISiteManager getSiteManager()
  {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }

  public MultiSite get(String domain) {
    return (MultiSite)this.baseDaoSupport.queryForObject("select * from site where domain = ?", MultiSite.class, new Object[] { domain });
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.MultiSiteManager
 * JD-Core Version:    0.6.1
 */