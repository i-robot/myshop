package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.service.ISettingService;
import com.enation.app.base.core.service.SettingRuntimeException;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.cache.ICache;
import java.util.Map;

public class SaasSettingCacheProxy
  implements ISettingService
{
  private ISettingService settingService;
  private ICache<Map<String, Map<String, String>>> cache;

  public SaasSettingCacheProxy()
  {
  }

  public SaasSettingCacheProxy(ISettingService settingService)
  {
    this.settingService = settingService;
  }

  public void add(String groupname, String name, String value)
  {
    this.settingService.add(groupname, name, value);
    String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
    Map settings = (Map)this.cache.get(uKey);

    settings = this.settingService.getSetting();
    this.cache.put(uKey, settings);
  }

  public void save(String groupname, String name, String value)
  {
    this.settingService.save(groupname, name, value);
    String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
    Map settings = (Map)this.cache.get(uKey);

    settings = this.settingService.getSetting();
    this.cache.put(uKey, settings);
  }

  public void delete(String groupname)
  {
    this.settingService.delete(groupname);
    String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
    Map settings = (Map)this.cache.get(uKey);

    if ((settings != null) || (settings.size() <= 0))
      settings.remove(groupname);
  }

  public Map<String, Map<String, String>> getSetting()
  {
    String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
    Map settings = (Map)this.cache.get(uKey);

    if ((settings == null) || (settings.size() <= 0)) {
      settings = this.settingService.getSetting();
      this.cache.put(uKey, settings);
    }
    return settings;
  }

  public void save(Map<String, Map<String, String>> settings)
    throws SettingRuntimeException
  {
    String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
    this.settingService.save(settings);
    this.cache.put(uKey, this.settingService.getSetting());
  }

  protected Integer getCurrentUserid()
  {
    EopSite site = EopContext.getContext().getCurrentSite();

    Integer userid = site.getUserid();
    return userid;
  }

  protected Integer getCurrentSiteid()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    return site.getId();
  }

  public String getSetting(String group, String code)
  {
    Map settings = getSetting();
    if (settings == null) return null;

    Map setting = (Map)settings.get(group);
    if (setting == null) return null;

    return (String)setting.get(code);
  }

  public void setCache(ICache<Map<String, Map<String, String>>> cache) {
    this.cache = cache;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.SaasSettingCacheProxy
 * JD-Core Version:    0.6.1
 */