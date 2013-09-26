package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.plugin.setting.SettingPluginBundle;
import com.enation.app.base.core.service.ISettingService;
import com.enation.app.base.core.service.SettingRuntimeException;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class SaasSettingService extends BaseSupport
  implements ISettingService
{
  private SettingPluginBundle settingPluginBundle;

  public void add(String groupname, String name, String value)
  {
    Map params = new HashMap();
    params.put(name, value);
    JSONObject jsonObject = JSONObject.fromObject(params);
    this.baseDaoSupport.execute("insert into settings(cfg_value,cfg_group)values(?,?)", new Object[] { jsonObject.toString(), groupname });
  }

  public void save(String groupname, String name, String value)
  {
    Map params = new HashMap();
    params.put(name, value);
    JSONObject jsonObject = JSONObject.fromObject(params);
    this.baseDaoSupport.execute("update settings set cfg_value=? where cfg_group=?", new Object[] { jsonObject.toString(), groupname });
  }

  public void delete(String groupname)
  {
    this.baseDaoSupport.execute("delete from settings where cfg_group=?", new Object[] { groupname });
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void save(Map<String, Map<String, String>> settings)
    throws SettingRuntimeException
  {
    Iterator settingkeyItor = settings.keySet().iterator();

    while (settingkeyItor.hasNext())
    {
      String settingKey = (String)settingkeyItor.next();
      JSONObject jsonObject = JSONObject.fromObject(settings.get(settingKey));

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
      if (StringUtil.isEmpty(setting_value)) {
        cfg.put(map.get("cfg_group"), new HashMap());
      } else {
        JSONObject jsonObject = JSONObject.fromObject(setting_value);
        Map itemMap = (Map)JSONObject.toBean(jsonObject, Map.class);
        cfg.put(map.get("cfg_group"), itemMap);
      }

    }

    return cfg;
  }

  public SettingPluginBundle getSettingPluginBundle()
  {
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

  public String getSetting(String group, String code)
  {
    Map settings = getSetting();
    if (settings == null) return null;

    Map setting = (Map)settings.get(group);
    if (setting == null) return null;

    return (String)setting.get(code);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.SaasSettingService
 * JD-Core Version:    0.6.1
 */