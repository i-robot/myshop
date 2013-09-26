package com.enation.framework.cache;

import org.apache.log4j.Logger;

public abstract class AbstractCacheProxy<T>
{
  protected final Logger logger = Logger.getLogger(getClass());
  protected ICache<T> cache;

  public AbstractCacheProxy(String cacheName)
  {
    this.cache = CacheFactory.getCache(cacheName);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.cache.AbstractCacheProxy
 * JD-Core Version:    0.6.1
 */