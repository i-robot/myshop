package com.enation.eop.processor;

import com.enation.eop.resource.model.EopApp;
import com.enation.eop.resource.model.EopSite;
import java.util.HashMap;
import java.util.Map;

public class Widget
{
  private Map<String, String> attrMap;
  private String widgetType;
  private EopApp app;
  private EopSite site;

  public Map<String, String> getAttrMap()
  {
    return this.attrMap;
  }

  public void setAttrMap(Map<String, String> attrMap)
  {
    this.attrMap = attrMap;
  }

  public EopSite getSite()
  {
    return this.site;
  }

  public void setSite(EopSite site)
  {
    this.site = site;
  }

  public Widget()
  {
    this.attrMap = new HashMap();
  }

  public Widget(EopApp app)
  {
    this.app = app;
    this.attrMap = new HashMap();
  }

  public void setAttribute(String name, String value)
  {
    this.attrMap.put(name, value);
  }

  public void setAttributes(Map<String, String> attrs) {
    this.attrMap = attrs;
  }

  public String getAttribute(String name)
  {
    name = name == null ? null : name.toLowerCase();
    return (String)this.attrMap.get(name);
  }

  public Map<String, String> getAtributes()
  {
    return this.attrMap;
  }

  public String getWidgetType()
  {
    return this.widgetType;
  }

  public void setWidgetType(String widgetType) {
    this.widgetType = widgetType;
  }

  public EopApp getApp() {
    return this.app;
  }

  public void setApp(EopApp app) {
    this.app = app;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.Widget
 * JD-Core Version:    0.6.1
 */