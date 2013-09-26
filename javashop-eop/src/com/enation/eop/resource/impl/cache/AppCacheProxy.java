package com.enation.eop.resource.impl.cache;

import com.enation.eop.processor.core.EopException;
import com.enation.eop.resource.IAppManager;
import com.enation.eop.resource.model.EopApp;
import com.enation.framework.cache.AbstractCacheProxy;
import com.enation.framework.cache.ICache;
import java.util.List;
import org.apache.log4j.Logger;

public class AppCacheProxy extends AbstractCacheProxy<List<EopApp>>
  implements IAppManager
{
  private IAppManager appManager;
  private static final String APP_LIST_CACHE_KEY = "applist";

  public AppCacheProxy(IAppManager appManager)
  {
    super("appCache");
    this.appManager = appManager;
  }

  public void add(EopApp app)
  {
    this.cache.clear();
    this.appManager.add(app);
  }

  public EopApp get(String appid)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("get app : " + appid);
    }
    List<EopApp> appList = list();

    for (EopApp app : appList) {
      if (app.getAppid().equals(appid)) {
        return app;
      }

    }

    throw new EopException("App not found");
  }

  public List<EopApp> list()
  {
    List appList = (List)this.cache.get("applist");

    if (appList == null) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("get applist from database");
      }
      appList = this.appManager.list();
      this.cache.put("applist", appList);
    }

    return appList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.cache.AppCacheProxy
 * JD-Core Version:    0.6.1
 */