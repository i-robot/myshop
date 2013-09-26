package com.enation.app.base.core.action;

import com.enation.app.base.core.plugin.setting.SettingPluginBundle;
import com.enation.app.base.core.service.ISettingService;
import com.enation.framework.action.WWAction;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class SettingAction extends WWAction
{
  private String showtab;
  private ISettingService settingService;
  private SettingPluginBundle settingPluginBundle;
  private Map<Integer, String> htmls;
  private String[] codes;
  private String[] cfg_values;
  private Map tabs;
  public static final String SETTING_PAGE_ID = "setting_input";

  public Map getTabs()
  {
    return this.tabs;
  }

  public String edit_input()
  {
    Map settings = this.settingService.getSetting();
    this.htmls = this.settingPluginBundle.onInputShow(settings);
    this.tabs = this.settingPluginBundle.getTabs();

    return "input";
  }

  public String save()
  {
    HttpServletRequest request = getRequest();
    Enumeration names = request.getParameterNames();
    Map settings = new HashMap();

    while (names.hasMoreElements())
    {
      String name = (String)names.nextElement();
      String[] name_ar = name.split("\\.");
      if (name_ar.length == 2)
      {
        String groupName = name_ar[0];
        String paramName = name_ar[1];
        String paramValue = request.getParameter(name);

        Map params = (Map)settings.get(groupName);
        if (params == null) {
          params = new HashMap();
          settings.put(groupName, params);
        }
        params.put(paramName, paramValue);
      }
    }

    this.settingService.save(settings);
    this.msgs.add("配置修改成功");
    this.urls.put("系统设置", "setting!edit_input.do");
    return "message";
  }

  public ISettingService getSettingService() {
    return this.settingService;
  }

  public void setSettingService(ISettingService settingService) {
    this.settingService = settingService;
  }

  public String[] getCfg_values()
  {
    return this.cfg_values;
  }

  public void setCfg_values(String[] cfg_values)
  {
    this.cfg_values = cfg_values;
  }

  public String[] getCodes()
  {
    return this.codes;
  }

  public void setCodes(String[] codes)
  {
    this.codes = codes;
  }

  public void setTabs(Map tabs)
  {
    this.tabs = tabs;
  }

  public String getShowtab()
  {
    return this.showtab;
  }

  public void setShowtab(String showtab) {
    this.showtab = showtab;
  }

  public SettingPluginBundle getSettingPluginBundle() {
    return this.settingPluginBundle;
  }

  public void setSettingPluginBundle(SettingPluginBundle settingPluginBundle) {
    this.settingPluginBundle = settingPluginBundle;
  }

  public Map<Integer, String> getHtmls() {
    return this.htmls;
  }

  public void setHtmls(Map<Integer, String> htmls) {
    this.htmls = htmls;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.SettingAction
 * JD-Core Version:    0.6.1
 */