package com.enation.framework.component;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.util.ArrayList;
import java.util.List;

public class ComponentView
  implements Cloneable
{
  private String name;
  private int id;
  private String componentid;
  private String version;
  private String javashop_version;
  private String author;
  private String description;
  private IComponent component;
  private List<PluginView> pluginList;
  private List<WidgetView> widgetList;
  private int install_state;
  private int enable_state;
  private String error_message;

  public ComponentView()
  {
    this.pluginList = new ArrayList();
    this.widgetList = new ArrayList();
  }

  public void addPlugin(PluginView plugin)
  {
    this.pluginList.add(plugin);
  }

  public void addWidget(WidgetView widget)
  {
    this.widgetList.add(widget);
  }

  public void setComponent(IComponent component) {
    this.component = component;
  }

  @NotDbField
  public IComponent getComponent()
  {
    return this.component;
  }

  @NotDbField
  public List<PluginView> getPluginList() {
    return this.pluginList;
  }

  public void setPluginList(List<PluginView> pluginList)
  {
    this.pluginList = pluginList;
  }

  @NotDbField
  public List<WidgetView> getWidgetList() {
    return this.widgetList;
  }

  public void setWidgetList(List<WidgetView> widgetList)
  {
    this.widgetList = widgetList;
  }

  public int getInstall_state()
  {
    return this.install_state;
  }

  public void setInstall_state(int install_state)
  {
    this.install_state = install_state;
  }

  public int getEnable_state()
  {
    return this.enable_state;
  }

  public void setEnable_state(int enable_state)
  {
    this.enable_state = enable_state;
  }

  public String getError_message()
  {
    return this.error_message;
  }

  public void setError_message(String error_message)
  {
    this.error_message = error_message;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @PrimaryKeyField
  public int getId()
  {
    return this.id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getComponentid()
  {
    return this.componentid;
  }

  public void setComponentid(String componentid)
  {
    this.componentid = componentid;
  }

  public String getVersion()
  {
    return this.version;
  }

  public void setVersion(String version)
  {
    this.version = version;
  }

  public String getJavashop_version()
  {
    return this.javashop_version;
  }

  public void setJavashop_version(String javashop_version)
  {
    this.javashop_version = javashop_version;
  }

  public String getAuthor()
  {
    return this.author;
  }

  public void setAuthor(String author)
  {
    this.author = author;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public Object clone()
  {
    try
    {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }return null;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.component.ComponentView
 * JD-Core Version:    0.6.1
 */