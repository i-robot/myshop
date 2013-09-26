package com.enation.app.base.core.action;

import com.enation.framework.action.WWAction;
import com.enation.framework.component.ComponentView;
import com.enation.framework.component.IComponentManager;
import java.util.List;
import org.apache.log4j.Logger;

public class ComponentAction extends WWAction
{
  private IComponentManager componentManager;
  private List<ComponentView> componentList;
  private String componentid;

  public String list()
  {
    this.componentList = this.componentManager.list();
    return "list";
  }

  public String install()
  {
    try
    {
      this.componentManager.install(this.componentid);
      showSuccessJson("安装成功");
    } catch (RuntimeException e) {
      this.logger.error("安装组件[" + this.componentid + "]", e);
      showErrorJson(e.getMessage());
    }
    return "json_message";
  }

  public String unInstall()
  {
    try
    {
      this.componentManager.unInstall(this.componentid);
      showSuccessJson("卸载成功");
    } catch (RuntimeException e) {
      this.logger.error("卸载组件[" + this.componentid + "]", e);
      showErrorJson(e.getMessage());
    }
    return "json_message";
  }

  public String start()
  {
    try
    {
      this.componentManager.start(this.componentid);
      showSuccessJson("启动成功");
    } catch (RuntimeException e) {
      this.logger.error("启动组件[" + this.componentid + "]", e);
      showErrorJson(e.getMessage());
    }
    return "json_message";
  }

  public String stop()
  {
    try
    {
      this.componentManager.stop(this.componentid);
      showSuccessJson("停用成功");
    } catch (RuntimeException e) {
      this.logger.error("停用组件[" + this.componentid + "]", e);
      showErrorJson(e.getMessage());
    }
    return "json_message";
  }

  public IComponentManager getComponentManager()
  {
    return this.componentManager;
  }
  public void setComponentManager(IComponentManager componentManager) {
    this.componentManager = componentManager;
  }

  public List<ComponentView> getComponentList() {
    return this.componentList;
  }

  public void setComponentList(List<ComponentView> componentList) {
    this.componentList = componentList;
  }

  public String getComponentid() {
    return this.componentid;
  }

  public void setComponentid(String componentid) {
    this.componentid = componentid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.ComponentAction
 * JD-Core Version:    0.6.1
 */