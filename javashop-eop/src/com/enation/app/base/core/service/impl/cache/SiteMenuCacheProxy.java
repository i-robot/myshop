package com.enation.app.base.core.service.impl.cache;

import com.enation.app.base.core.model.SiteMenu;
import com.enation.app.base.core.service.ISiteMenuManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.cache.AbstractCacheProxy;
import com.enation.framework.cache.ICache;
import java.util.List;
import org.apache.log4j.Logger;

public class SiteMenuCacheProxy extends AbstractCacheProxy<List<SiteMenu>>
  implements ISiteMenuManager
{
  public static final String MENU_LIST_CACHE_KEY = "siteMenuList";
  private ISiteMenuManager siteMenuManager;

  public SiteMenuCacheProxy(ISiteMenuManager siteMenuManager)
  {
    super("siteMenuList");
    this.siteMenuManager = siteMenuManager;
  }

  private void cleanCache()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    this.cache.remove("siteMenuList_" + site.getUserid() + "_" + site.getId());
  }

  public void add(SiteMenu siteMenu)
  {
    this.siteMenuManager.add(siteMenu);
    cleanCache();
  }

  public void delete(Integer id)
  {
    this.siteMenuManager.delete(id);
    cleanCache();
  }

  public void edit(SiteMenu siteMenu)
  {
    this.siteMenuManager.edit(siteMenu);
    cleanCache();
  }

  public SiteMenu get(Integer menuid)
  {
    return this.siteMenuManager.get(menuid);
  }

  public List<SiteMenu> list(Integer parentid)
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    List menuList = (List)this.cache.get("siteMenuList_" + site.getUserid() + "_" + site.getId());

    if (menuList == null) {
      menuList = this.siteMenuManager.list(parentid);
      this.cache.put("siteMenuList_" + site.getUserid() + "_" + site.getId(), menuList);
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("load sitemenu from database");
      }
    }
    else if (this.logger.isDebugEnabled()) {
      this.logger.debug("load sitemenu from cache");
    }

    return menuList;
  }

  public void updateSort(Integer[] menuid, Integer[] sort)
  {
    this.siteMenuManager.updateSort(menuid, sort);
    cleanCache();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.cache.SiteMenuCacheProxy
 * JD-Core Version:    0.6.1
 */