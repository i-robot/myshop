package com.enation.eop.resource.model;

public class WidgetBundle extends Resource
{
  private String widgetname;
  private String widgettype;
  private String settingurl;

  public String getWidgetname()
  {
    return this.widgetname;
  }
  public void setWidgetname(String widgetname) {
    this.widgetname = widgetname;
  }
  public String getWidgettype() {
    return this.widgettype;
  }
  public void setWidgettype(String widgettype) {
    this.widgettype = widgettype;
  }
  public String getSettingurl() {
    return this.settingurl;
  }
  public void setSettingurl(String settingurl) {
    this.settingurl = settingurl;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.WidgetBundle
 * JD-Core Version:    0.6.1
 */