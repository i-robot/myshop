package com.enation.framework.cache;

public final class CacheFactory
{
  public static final String APP_CACHE_NAME_KEY = "appCache";
  public static final String THEMEURI_CACHE_NAME_KEY = "themeUriCache";
  public static final String SITE_CACHE_NAME_KEY = "siteCache";
  public static final String WIDGET_CACHE_NAME_KEY = "widgetCache";

  public static <T> ICache<T> getCache(String name)
  {
    ICache ehCache = new EhCacheImpl(name);
    return ehCache;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.cache.CacheFactory
 * JD-Core Version:    0.6.1
 */