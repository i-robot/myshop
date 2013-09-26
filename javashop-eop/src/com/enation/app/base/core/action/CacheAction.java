package com.enation.app.base.core.action;

import com.enation.framework.action.WWAction;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.statistics.LiveCacheStatistics;

public class CacheAction extends WWAction
{
  public String execute()
  {
    CacheManager manager = CacheManager.getInstance();
    Cache cache = manager.getCache("widgetCache");

    LiveCacheStatistics statistis = cache.getLiveCacheStatistics();
    boolean memory = statistis.isStatisticsEnabled();

    return "list";
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.CacheAction
 * JD-Core Version:    0.6.1
 */