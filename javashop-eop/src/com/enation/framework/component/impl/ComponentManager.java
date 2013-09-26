package com.enation.framework.component.impl;

import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.component.ComponentView;
import com.enation.framework.component.IComponent;
import com.enation.framework.component.IComponentManager;
import com.enation.framework.component.PluginView;
import com.enation.framework.component.WidgetView;
import com.enation.framework.component.context.ComponentContext;
import com.enation.framework.component.context.WidgetContext;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.plugin.IPlugin;
import com.enation.framework.plugin.IPluginBundle;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ComponentManager extends BaseSupport
  implements IComponentManager
{
  public List<ComponentView> list()
  {
    List viewList = new ArrayList();
    List<ComponentView> componentViews = ComponentContext.getComponents();

    List dbList = getDbList();

    if (componentViews != null) {
      for (ComponentView view : componentViews)
      {
        ComponentView componentView = (ComponentView)view.clone();

        if (this.logger.isDebugEnabled()) {
          this.logger.debug("load component[" + componentView.getName() + "] start ");
        }

        try
        {
          componentView.setInstall_state(0);
          componentView.setEnable_state(0);

          loadComponentState(componentView, dbList);

          if (this.logger.isDebugEnabled())
            this.logger.debug("load component[" + componentView.getName() + "] end ");
        }
        catch (Exception e) {
          this.logger.error("加载组件[" + componentView.getName() + "]出错", e);
          componentView.setEnable_state(2);
          componentView.setError_message(e.getMessage());
        }
        viewList.add(componentView);
      }
    }

    return viewList;
  }

  private void loadComponentState(ComponentView componentView, List<ComponentView> dbList)
  {
    for (ComponentView dbView : dbList)
      if (dbView.getComponentid().equals(componentView.getComponentid()))
      {
        if (this.logger.isDebugEnabled()) {
          this.logger.debug("load component[" + componentView.getName() + "]state->install state:" + dbView.getInstall_state() + ",enable_state:" + dbView.getEnable_state());
        }

        componentView.setInstall_state(dbView.getInstall_state());
        componentView.setEnable_state(dbView.getEnable_state());
        componentView.setId(dbView.getId());

        if (componentView.getInstall_state() != 2)
        {
          if (dbView.getEnable_state() != 0)
          {
            if (dbView.getEnable_state() != 1)
            {
              componentView.setError_message(dbView.getError_message());
            }
          }
        }
      }
  }

  public void install(String componentid)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("install component[" + componentid + "]...");
    }
    try
    {
      ComponentView componentView = getComponentView(componentid);

      if (componentView != null) {
        componentView.getComponent().install();

        if (!isInDb(componentView.getComponentid())) {
          ComponentView temp = (ComponentView)componentView.clone();
          temp.setInstall_state(1);

          this.baseDaoSupport.insert("component", temp);
        }
        else {
          this.baseDaoSupport.execute("update component set install_state=1 where componentid=?", new Object[] { componentView.getComponentid() });
        }
      }
    } catch (RuntimeException e) {
      this.logger.error("安装组件[" + componentid + "]出错", e);
    }
  }

  private boolean isInDb(String componentid)
  {
    String sql = "select count(0)  from component where componentid=?";
    return this.baseDaoSupport.queryForInt(sql, new Object[] { componentid }) > 0;
  }

  public void unInstall(String componentid)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("install component[" + componentid + "]...");
    }

    ComponentView componentView = getComponentView(componentid);
    if (componentView != null) {
      componentView.getComponent().unInstall();

      if (!isInDb(componentView.getComponentid())) {
        ComponentView temp = (ComponentView)componentView.clone();
        temp.setInstall_state(0);
        this.baseDaoSupport.insert("component", temp);
      } else {
        this.baseDaoSupport.execute("update component set install_state=0 where componentid=?", new Object[] { componentView.getComponentid() });
      }
    }
  }

  private ComponentView getComponentView(String componentid)
  {
    List<ComponentView> componentList = ComponentContext.getComponents();

    for (ComponentView componentView : componentList) {
      if (componentView.getComponentid().equals(componentid)) {
        return componentView;
      }
    }

    return null;
  }

  public void start(String componentid)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("start component[" + componentid + "]...");
    }

    ComponentView componentView = getComponentView(componentid);

    List<PluginView> pluginList = componentView.getPluginList();
    for (PluginView pluginView : pluginList) {
      String pluginid = pluginView.getId();
      IPlugin plugin = (IPlugin)SpringContextHolder.getBean(pluginid);

      List<String> bundleList = pluginView.getBundleList();
      if (bundleList != null)
        for (String bundleId : bundleList) {
          IPluginBundle pluginBundle = (IPluginBundle)SpringContextHolder.getBean(bundleId);
          pluginBundle.registerPlugin(plugin);
        }
    }
    IPlugin plugin;
    List<WidgetView> widgetList = componentView.getWidgetList();
    for (WidgetView widgetView : widgetList) {
      String widgetid = widgetView.getId();
      WidgetContext.putWidgetState(widgetid, Boolean.valueOf(true));
    }

    String sql = "update component set enable_state=1 where componentid=?";
    this.baseDaoSupport.execute(sql, new Object[] { componentid });

    if (this.logger.isDebugEnabled())
      this.logger.debug("start component[" + componentid + "] complete");
  }

  public void stop(String componentid)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("stop component[" + componentid + "]...");
    }

    ComponentView componentView = getComponentView(componentid);

    List<PluginView> pluginList = componentView.getPluginList();
    for (PluginView pluginView : pluginList) {
      String pluginid = pluginView.getId();
      IPlugin plugin = (IPlugin)SpringContextHolder.getBean(pluginid);
      List<String> bundleList = pluginView.getBundleList();
      for (String bundleId : bundleList) {
        IPluginBundle pluginBundle = (IPluginBundle)SpringContextHolder.getBean(bundleId);
        pluginBundle.unRegisterPlugin(plugin);
      }
    }
    IPlugin plugin;
    List<WidgetView> widgetList = componentView.getWidgetList();
    for (WidgetView widgetView : widgetList) {
      String widgetid = widgetView.getId();
      WidgetContext.putWidgetState(widgetid, Boolean.valueOf(false));
    }

    String sql = "update component set enable_state=0 where componentid=?";
    this.baseDaoSupport.execute(sql, new Object[] { componentid });

    if (this.logger.isDebugEnabled())
      this.logger.debug("stop component[" + componentid + "] complete");
  }

  public void startComponents()
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("start components start...");
    }

    List<ComponentView> dbList = getDbList();
    for (ComponentView componentView : dbList) {
      if ((componentView.getInstall_state() != 2) && 
        (componentView.getEnable_state() == 1)) {
        start(componentView.getComponentid());
      }

    }

    if (this.logger.isDebugEnabled())
      this.logger.debug("start components end!");
  }

  public void saasStartComponents()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    int userid = site.getUserid().intValue();
    int siteid = site.getId().intValue();

    boolean isstart = ComponentContext.getSiteComponentState(userid, siteid);
    if (!isstart) {
      startComponents();
      ComponentContext.siteComponentStart(userid, siteid);
    }
  }

  private List<ComponentView> getDbList()
  {
    String sql = "select * from component ";
    return this.baseDaoSupport.queryForList(sql, ComponentView.class, new Object[0]);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.component.impl.ComponentManager
 * JD-Core Version:    0.6.1
 */