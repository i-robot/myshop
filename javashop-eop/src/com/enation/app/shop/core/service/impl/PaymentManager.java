package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.PayCfg;
import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
import com.enation.app.shop.core.plugin.payment.PaymentPluginBundle;
import com.enation.app.shop.core.service.IPaymentManager;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.ObjectNotFoundException;
import com.enation.framework.plugin.IPlugin;
import com.enation.framework.util.StringUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;

public class PaymentManager extends BaseSupport<PayCfg>
  implements IPaymentManager
{
  private PaymentPluginBundle paymentPluginBundle;

  public List list()
  {
    String sql = "select * from payment_cfg";
    return this.baseDaoSupport.queryForList(sql, PayCfg.class, new Object[0]);
  }

  public PayCfg get(Integer id)
  {
    String sql = "select * from payment_cfg where id=?";
    PayCfg payment = (PayCfg)this.baseDaoSupport.queryForObject(sql, PayCfg.class, new Object[] { id });
    return payment;
  }

  public PayCfg get(String pluginid) {
    String sql = "select * from payment_cfg where type=?";
    PayCfg payment = (PayCfg)this.baseDaoSupport.queryForObject(sql, PayCfg.class, new Object[] { pluginid });
    return payment;
  }

  public Double countPayPrice(Integer id)
  {
    return Double.valueOf(0.0D);
  }

  public void add(String name, String type, String biref, Map<String, String> configParmas)
  {
    if (StringUtil.isEmpty(name)) throw new IllegalArgumentException("payment name is  null");
    if (StringUtil.isEmpty(type)) throw new IllegalArgumentException("payment type is  null");
    if (configParmas == null) throw new IllegalArgumentException("configParmas  is  null");

    PayCfg payCfg = new PayCfg();
    payCfg.setName(name);
    payCfg.setType(type);
    payCfg.setBiref(biref);
    payCfg.setConfig(JSONObject.fromObject(configParmas).toString());
    this.baseDaoSupport.insert("payment_cfg", payCfg);
  }

  public Map<String, String> getConfigParams(Integer paymentId)
  {
    PayCfg payment = get(paymentId);
    String config = payment.getConfig();
    if (null == config) return new HashMap();
    JSONObject jsonObject = JSONObject.fromObject(config);
    Map itemMap = (Map)JSONObject.toBean(jsonObject, Map.class);
    return itemMap;
  }

  public Map<String, String> getConfigParams(String pluginid)
  {
    PayCfg payment = get(pluginid);
    String config = payment.getConfig();
    if (null == config) return new HashMap();
    JSONObject jsonObject = JSONObject.fromObject(config);
    Map itemMap = (Map)JSONObject.toBean(jsonObject, Map.class);
    return itemMap;
  }

  public void edit(Integer paymentId, String name, String type, String biref, Map<String, String> configParmas)
  {
    if (StringUtil.isEmpty(name)) throw new IllegalArgumentException("payment name is  null");
    if (configParmas == null) throw new IllegalArgumentException("configParmas  is  null");

    PayCfg payCfg = new PayCfg();
    payCfg.setName(name);
    payCfg.setBiref(biref);
    payCfg.setType(type);
    payCfg.setConfig(JSONObject.fromObject(configParmas).toString());
    this.baseDaoSupport.update("payment_cfg", payCfg, "id=" + paymentId);
  }

  public void delete(Integer[] idArray)
  {
    if ((idArray == null) || (idArray.length == 0)) return;

    String idStr = StringUtil.arrayToString(idArray, ",");
    String sql = "delete from payment_cfg where id in(" + idStr + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public List<IPlugin> listAvailablePlugins()
  {
    return this.paymentPluginBundle.getPluginList();
  }

  public String getPluginInstallHtml(String pluginId, Integer paymentId)
  {
    IPlugin installPlugin = null;
    List<IPlugin> plguinList = listAvailablePlugins();
    for (IPlugin plugin : plguinList) {
      if ((plugin instanceof AbstractPaymentPlugin))
      {
        if (((AbstractPaymentPlugin)plugin).getId().equals(pluginId)) {
          installPlugin = plugin;
          break;
        }

      }

    }

    if (installPlugin == null) throw new ObjectNotFoundException("plugin[" + pluginId + "] not found!");
    FreeMarkerPaser fp = FreeMarkerPaser.getInstance();
    fp.setClz(installPlugin.getClass());

    if (paymentId != null) {
      Map params = getConfigParams(paymentId);
      Iterator keyIter = params.keySet().iterator();

      while (keyIter.hasNext()) {
        String key = (String)keyIter.next();
        String value = (String)params.get(key);
        fp.putData(key, value);
      }
    }
    return fp.proessPageContent();
  }

  public PaymentPluginBundle getPaymentPluginBundle()
  {
    return this.paymentPluginBundle;
  }

  public void setPaymentPluginBundle(PaymentPluginBundle paymentPluginBundle) {
    this.paymentPluginBundle = paymentPluginBundle;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.PaymentManager
 * JD-Core Version:    0.6.1
 */