package com.enation.eop.processor.facade.support.widget;

import com.enation.eop.processor.facade.support.LocalWidgetPaser;
import com.enation.eop.processor.facade.support.WidgetEditModeWrapper;
import com.enation.eop.processor.widget.IWidgetHtmlGetter;
import com.enation.eop.processor.widget.IWidgetPaser;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import java.util.Map;

public class WidgetHtmlGetter
  implements IWidgetHtmlGetter
{
  public String process(Map<String, String> params, String page)
  {
    IWidgetPaser widgetPaser = new LocalWidgetPaser();
    widgetPaser = new BorderWrapper(widgetPaser);
    if ((UserServiceFactory.getUserService().isUserLoggedIn()) && ("edit".equals(params.get("mode")))) {
      widgetPaser = new WidgetEditModeWrapper(widgetPaser);
    }
    String html = widgetPaser.pase(params);
    return html;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.WidgetHtmlGetter
 * JD-Core Version:    0.6.1
 */