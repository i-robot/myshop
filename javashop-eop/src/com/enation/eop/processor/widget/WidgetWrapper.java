package com.enation.eop.processor.widget;

import java.util.Map;

public class WidgetWrapper
  implements IWidgetPaser
{
  protected IWidgetPaser widgetPaser;

  public WidgetWrapper(IWidgetPaser paser)
  {
    this.widgetPaser = paser;
  }

  public String pase(Map<String, String> params)
  {
    return this.widgetPaser.pase(params);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.widget.WidgetWrapper
 * JD-Core Version:    0.6.1
 */