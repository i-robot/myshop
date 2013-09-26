package com.enation.eop.resource.impl.cache;

import com.enation.eop.processor.core.EopException;
import com.enation.eop.resource.IDomainManager;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.model.Dns;
import com.enation.eop.resource.model.EopApp;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopSiteDomain;
import com.enation.eop.resource.model.EopUser;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.cache.AbstractCacheProxy;
import com.enation.framework.cache.ICache;
import com.enation.framework.database.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class SiteCacheProxy extends AbstractCacheProxy<EopSite>
  implements ISiteManager
{
  private ISiteManager siteManager;
  private IDomainManager domainManager;
  private static final String SITE_LIST_CACHE_KEY = "eopDNS";

  public SiteCacheProxy(ISiteManager siteManager)
  {
    super("siteCache");
    this.siteManager = siteManager;
  }

  public int addDomain(EopSiteDomain eopSiteDomain)
  {
    EopSite site = this.siteManager.get(eopSiteDomain.getSiteid());
    this.cache.put(eopSiteDomain.getDomain(), site);
    return this.siteManager.addDomain(eopSiteDomain);
  }

  public void deleteDomain(String domain)
  {
    this.siteManager.deleteDomain(domain);
    this.cache.remove(domain);
  }

  public Integer add(EopSite newSite, String domain)
  {
    EopSite site = (EopSite)this.cache.get(domain);
    if (site == null) {
      return this.siteManager.add(newSite, domain);
    }
    throw new EopException("域名:" + domain + " 已经存在！");
  }

  public void deleteDomain(Integer domainid)
  {
    EopSiteDomain siteDomain = this.domainManager.get(domainid);
    this.siteManager.deleteDomain(domainid);
    this.cache.remove(siteDomain.getDomain());
  }

  public void delete(Integer id)
  {
    List<EopSiteDomain> list = this.domainManager.listSiteDomain(id);
    for (EopSiteDomain siteDomain : list) {
      this.cache.remove(siteDomain.getDomain());
    }
    this.siteManager.delete(id);
  }

  public List<Dns> getDnsList()
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("query all DNS form datagae,then put them in cache ");
    }
    List<Dns> dnsList = this.siteManager.getDnsList();
    for (Dns dns : dnsList) {
      this.cache.put(dns.getDomain(), dns.getSite());
    }
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("DNS put in cache complete!");
    }
    return dnsList;
  }

  public EopSite get(Integer id)
  {
    return this.siteManager.get(id);
  }

  public void editDomain(String domainOld, String domainNew) {
    EopSite site = this.siteManager.get(domainOld);
    this.siteManager.editDomain(domainOld, domainNew);
    this.cache.remove(domainOld);
    this.cache.put(domainNew, site);
  }

  public EopSite get(String domain)
  {
    EopSite site = (EopSite)this.cache.get(domain);

    if (site == null)
    {
      site = this.siteManager.get(domain);
      this.cache.put(domain, site);
    }

    if (site == null)
    {
      throw new EopException("域名" + domain + "不存在");
    }

    return site;
  }

  public Boolean checkInDomain(String domain)
  {
    return this.siteManager.checkInDomain(domain);
  }

  public Page list(String keyword, int pageNo, int pageSize)
  {
    Page page = this.siteManager.list(keyword, pageNo, pageSize);
    List<Map> listsite = (List)page.getResult();

    List<Dns> dnsList = getDnsList();
    for (Map site : listsite) {
      List domainList = new ArrayList();

      for (Dns siteDomain : dnsList) {
        if (site.get("id").toString().equals(siteDomain.getSite().getId().toString()))
        {
          domainList.add(siteDomain);
        }
      }
      site.put("eopSiteDomainList", domainList);
    }
    return page;
  }

  public Page list(int pageNo, int pageSize, String order, String search)
  {
    return this.siteManager.list(pageNo, pageSize, order, search);
  }

  public void edit(EopSite eopSite)
  {
    this.siteManager.edit(eopSite);
    EopSite site = this.siteManager.get(eopSite.getId());
    refreshCache(site);
  }

  private void refreshCache(EopSite site)
  {
    List<EopSiteDomain> list = this.domainManager.listSiteDomain();
    for (EopSiteDomain siteDomain : list) {
      this.cache.put(siteDomain.getDomain(), site);
    }
    EopContext.getContext().setCurrentSite(site);
  }

  public void updatePoint(Integer id, int point, int sitestate) {
    this.siteManager.updatePoint(id, point, sitestate);

    EopSite site = get(id);
    List list = this.domainManager.listSiteDomain(id);
    if ((list != null) && (!list.isEmpty())) {
      String domain = ((EopSiteDomain)list.get(0)).getDomain();
      site = get(domain);
      if (site != null) {
        if (point == -1)
          site.setPoint(point);
        else {
          site.setPoint(site.getPoint() + point);
        }

        site.setSitestate(sitestate);
      }
    }
  }

  public int getPoint(Integer id, int point)
  {
    int result = this.siteManager.getPoint(id, point);

    if (result == 1)
    {
      EopSite site = get(id);
      List list = this.domainManager.listSiteDomain(id);
      if ((list != null) && (!list.isEmpty())) {
        String domain = ((EopSiteDomain)list.get(0)).getDomain();
        site = get(domain);
        if (site != null) {
          if (point == -1)
            site.setPoint(point);
          else {
            site.setPoint(site.getPoint() + point);
          }
        }
      }
    }
    return result;
  }

  public List<EopApp> getSiteApps() {
    return this.siteManager.getSiteApps();
  }

  public void setSiteProduct(Integer userid, Integer siteid, String productid)
  {
    this.siteManager.setSiteProduct(userid, siteid, productid);
  }

  public void changeAdminTheme(Integer themeid)
  {
    this.siteManager.changeAdminTheme(themeid);
  }

  public void changeTheme(Integer themeid)
  {
    this.siteManager.changeTheme(themeid);
  }

  public List listDomain(Integer userid, Integer siteid)
  {
    return this.siteManager.listDomain(userid, siteid);
  }

  public IDomainManager getDomainManager() {
    return this.domainManager;
  }

  public void setDomainManager(IDomainManager domainManager) {
    this.domainManager = domainManager;
  }

  public void initData()
  {
    this.siteManager.initData();
  }

  public List list()
  {
    return this.siteManager.list();
  }

  public List list(Integer userid)
  {
    return this.siteManager.list(userid);
  }

  public Integer add(EopUser user, EopSite site, String domain)
  {
    return this.siteManager.add(user, site, domain);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.cache.SiteCacheProxy
 * JD-Core Version:    0.6.1
 */