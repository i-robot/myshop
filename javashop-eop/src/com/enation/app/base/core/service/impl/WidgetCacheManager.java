package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.service.ISettingService;
import com.enation.app.base.core.service.IWidgetCacheManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.cache.CacheFactory;
import com.enation.framework.cache.ICache;
import java.util.HashMap;
import java.util.Map;

public class WidgetCacheManager
  implements IWidgetCacheManager
{
  private ISettingService settingService;

  public void close()
  {
    if ("2".equals(EopSetting.RUNMODE))
      cleanSaasCache();
    else {
      cleanSlCache();
    }

    Map settings = new HashMap();
    Map value = new HashMap();
    value.put("state", "close");
    settings.put("widgetCache", value);
    this.settingService.save(settings);
  }

  private void cleanSaasCache()
  {
    ICache widgetCache = CacheFactory.getCache("widgetCache");

    EopSite site = EopContext.getContext().getCurrentSite();
    String site_key = "widget_" + site.getUserid() + "_" + site.getId();

    widgetCache.remove(site_key);
  }

  private void cleanSlCache()
  {
    ICache widgetCache = CacheFactory.getCache("widgetCache");
    widgetCache.clear();
  }

  public void open()
  {
    Map settings = new HashMap();
    Map value = new HashMap();
    value.put("state", "open");
    settings.put("widgetCache", value);
    this.settingService.save(settings);
  }

  public boolean isOpen()
  {
    Map settings = new HashMap();
    String state = this.settingService.getSetting("widgetCache", "state");
    return "open".equals(state);
  }

  public ISettingService getSettingService()
  {
    return this.settingService;
  }

  public void setSettingService(ISettingService settingService)
  {
    this.settingService = settingService;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.WidgetCacheManager
 * JD-Core Version:    0.6.1
 */