package com.enation.framework.cache;

import java.io.PrintStream;
import java.io.Serializable;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheImpl
  implements ICache
{
  private Cache cache;

  public EhCacheImpl(String name)
  {
    try
    {
      CacheManager manager = CacheManager.getInstance();
      this.cache = manager.getCache(name);

      if (this.cache == null) {
        manager.addCache(name);
        this.cache = manager.getCache(name);
      }
    } catch (CacheException e) {
      e.printStackTrace();
    }
  }

  public Object get(Object key)
  {
    Object obj = null;
    try {
      if (key != null) {
        Element element = this.cache.get((Serializable)key);
        if (element != null)
          obj = element.getValue();
      }
    }
    catch (CacheException e) {
      e.printStackTrace();
    }
    return obj;
  }

  public void put(Object key, Object value)
  {
    try
    {
      Element element = new Element((Serializable)key, (Serializable)value);
      this.cache.put(element);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
  }

  public void remove(Object key)
  {
    try
    {
      this.cache.remove((Serializable)key);
    } catch (ClassCastException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
  }

  public void clear()
  {
    try
    {
      this.cache.removeAll();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    EhCacheImpl cache = new EhCacheImpl("queryCache");

    System.out.println(cache.get("test"));
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.cache.EhCacheImpl
 * JD-Core Version:    0.6.1
 */