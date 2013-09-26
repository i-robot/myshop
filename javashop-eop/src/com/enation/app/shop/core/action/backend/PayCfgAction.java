package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.PayCfg;
import com.enation.app.shop.core.service.IPaymentManager;
import com.enation.framework.action.WWAction;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class PayCfgAction extends WWAction
{
  private IPaymentManager paymentManager;
  private List list;
  private List pluginList;
  private Integer paymentId;
  private String pluginId;
  private Integer[] id;
  private String name;
  private String type;
  private String biref;

  public String list()
  {
    this.list = this.paymentManager.list();
    return "list";
  }

  public String add()
  {
    this.pluginList = this.paymentManager.listAvailablePlugins();
    return "input";
  }

  public String getPluginHtml() {
    try {
      this.json = this.paymentManager.getPluginInstallHtml(this.pluginId, this.paymentId);
    } catch (RuntimeException e) {
      this.json = e.getMessage();
    }
    return "json_message";
  }

  public String edit()
  {
    this.pluginList = this.paymentManager.listAvailablePlugins();
    PayCfg cfg = this.paymentManager.get(this.paymentId);
    this.name = cfg.getName();
    this.type = cfg.getType();
    this.biref = cfg.getBiref();
    return "input";
  }

  public String saveAdd()
  {
    try
    {
      HttpServletRequest request = getRequest();
      Enumeration names = request.getParameterNames();
      Map params = new HashMap();
      while (names.hasMoreElements()) {
        String name = (String)names.nextElement();

        if ((!"name".equals(name)) && 
          (!"type".equals(name)) && 
          (!"biref".equals(name)) && 
          (!"paymentId".equals(name)) && 
          (!"submit".equals(name))) {
          String value = request.getParameter(name);
          params.put(name, value);
        }
      }
      this.paymentManager.add(this.name, this.type, this.biref, params);
      this.msgs.add("支付方式添加成功");
      this.urls.put("支付方式列表", "payCfg!list.do");
    } catch (RuntimeException e) {
      this.msgs.add(e.getMessage());
    }
    return "message";
  }

  public String save()
  {
    if ((this.paymentId == null) || ("".equals(this.paymentId))) {
      return saveAdd();
    }
    return saveEdit();
  }

  public String saveEdit()
  {
    try
    {
      HttpServletRequest request = getRequest();
      Enumeration names = request.getParameterNames();
      Map params = new HashMap();
      while (names.hasMoreElements()) {
        String name = (String)names.nextElement();

        if ((!"name".equals(name)) && 
          (!"type".equals(name)) && 
          (!"biref".equals(name)) && 
          (!"paymentId".equals(name)) && 
          (!"submit".equals(name))) {
          String value = request.getParameter(name);
          params.put(name, value);
        }
      }
      this.paymentManager.edit(this.paymentId, this.name, this.type, this.biref, params);
      this.msgs.add("支付方式修改成功");
      this.urls.put("支付方式列表", "payCfg!list.do");
    } catch (RuntimeException e) {
      this.msgs.add(e.getMessage());
    }
    return "message";
  }

  public String delete()
  {
    try
    {
      this.paymentManager.delete(this.id);
      this.json = "{result:0,message:'支付方式删除成功'}";
    } catch (RuntimeException e) {
      this.json = ("{result:1,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public IPaymentManager getPaymentManager() {
    return this.paymentManager;
  }

  public void setPaymentManager(IPaymentManager paymentManager) {
    this.paymentManager = paymentManager;
  }

  public List getList() {
    return this.list;
  }

  public void setList(List list) {
    this.list = list;
  }

  public Integer getPaymentId() {
    return this.paymentId;
  }

  public void setPaymentId(Integer paymentId) {
    this.paymentId = paymentId;
  }

  public Integer[] getId() {
    return this.id;
  }

  public void setId(Integer[] id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBiref() {
    return this.biref;
  }

  public void setBiref(String biref) {
    this.biref = biref;
  }

  public List getPluginList()
  {
    return this.pluginList;
  }

  public void setPluginList(List pluginList)
  {
    this.pluginList = pluginList;
  }

  public String getPluginId()
  {
    return this.pluginId;
  }

  public void setPluginId(String pluginId)
  {
    this.pluginId = pluginId;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.PayCfgAction
 * JD-Core Version:    0.6.1
 */