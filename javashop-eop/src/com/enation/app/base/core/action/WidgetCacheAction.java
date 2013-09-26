package com.enation.app.base.core.action;

import com.enation.app.base.core.service.IWidgetCacheManager;
import com.enation.framework.action.WWAction;

public class WidgetCacheAction extends WWAction
{
  private IWidgetCacheManager widgetCacheManager;

  public String open()
  {
    try
    {
      this.widgetCacheManager.open();
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String close()
  {
    try
    {
      this.widgetCacheManager.close();
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String getState()
  {
    try {
      boolean isOpen = this.widgetCacheManager.isOpen();
      String state = isOpen ? "open" : "close";
      this.json = ("{result:1,state:'" + state + "'}");
    } catch (RuntimeException e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public IWidgetCacheManager getWidgetCacheManager()
  {
    return this.widgetCacheManager;
  }

  public void setWidgetCacheManager(IWidgetCacheManager widgetCacheManager)
  {
    this.widgetCacheManager = widgetCacheManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.WidgetCacheAction
 * JD-Core Version:    0.6.1
 */