package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.plugin.setting.SettingPluginBundle;
import com.enation.app.base.core.service.ISettingService;
import com.enation.app.base.core.service.SettingRuntimeException;
import com.enation.framework.database.IDaoSupport;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Deprecated
public class SettingService
  implements ISettingService
{
  private IDaoSupport baseDaoSupport;
  private SettingPluginBundle settingPluginBundle;

  public void add(String groupname, String name, String value)
  {
    Map params = new HashMap();
    params.put(name, value);
  }

  public void save(String groupname, String name, String value)
  {
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void save(Map<String, Map<String, String>> settings)
    throws SettingRuntimeException
  {
    Iterator settingkeyItor = settings.keySet().iterator();

    while (settingkeyItor.hasNext())
    {
      String settingKey = (String)settingkeyItor.next();
      JSONObject jsonObject = JSONObject.fromObject(settings);

      this.baseDaoSupport.execute("update settings set cfg_value=? where cfg_group=?", new Object[] { jsonObject.toString(), settingKey });
    }
  }

  public Map<String, Map<String, String>> getSetting()
  {
    String sql = "select * from settings";
    List<Map> list = this.baseDaoSupport.queryForList(sql, new Object[0]);
    Map cfg = new HashMap();

    for (Map map : list) {
      String setting_value = (String)map.get("cfg_value");
      JSONObject jsonObject = JSONObject.fromObject(setting_value);
      Map itemMap = (Map)JSONObject.toBean(jsonObject, Map.class);
      cfg.put(map.get("cfg_group"), itemMap);
    }

    return cfg;
  }

  public SettingPluginBundle getSettingPluginBundle() {
    return this.settingPluginBundle;
  }

  public void setSettingPluginBundle(SettingPluginBundle settingPluginBundle) {
    this.settingPluginBundle = settingPluginBundle;
  }

  public static void main(String[] args)
  {
    String setting_value = "{\"thumbnail_pic_height\":\"40\",\"thumbnail_pic_width\":\"50\"}";
    JSONObject jsonObject = JSONObject.fromObject(setting_value);
    Map map1 = (Map)JSONObject.toBean(jsonObject, Map.class);
    System.out.println(map1.get("thumbnail_pic_height"));
  }
  public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
    this.baseDaoSupport = baseDaoSupport;
  }

  public String getSetting(String group, String code)
  {
    return (String)((Map)getSetting().get(group)).get(code);
  }

  public void delete(String groupname)
  {
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.SettingService
 * JD-Core Version:    0.6.1
 */