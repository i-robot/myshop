package com.enation.eop.processor.facade.support;

import com.enation.eop.processor.IPageParamJsonGetter;
import com.enation.eop.processor.widget.IWidgetParamParser;
import com.enation.eop.processor.widget.WidgetXmlUtil;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.IThemeUriManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.ThemeUri;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.context.spring.SpringContextHolder;
import java.util.Map;

public class PageParamJsonGetter
  implements IPageParamJsonGetter
{
  private IWidgetParamParser widgetParamParser;
  private IThemeManager themeManager;

  public String getJson(String uri)
  {
    if (uri.indexOf('?') > 0) {
      uri = uri.substring(0, uri.indexOf('?'));
    }

    EopSite site = EopContext.getContext().getCurrentSite();

    IThemeUriManager themeUriManager = (IThemeUriManager)SpringContextHolder.getBean("themeUriManager");
    ThemeUri themeUri = themeUriManager.getPath(uri);
    uri = themeUri.getPath();

    Map pages = this.widgetParamParser.parse();

    Map params = (Map)pages.get(uri);
    String json = WidgetXmlUtil.mapToJson(params);
    json = "{'pageId':'" + uri + "',params:" + json + "}";
    return json;
  }

  public void setWidgetParamParser(IWidgetParamParser widgetParamParser)
  {
    this.widgetParamParser = widgetParamParser;
  }

  public void setThemeManager(IThemeManager themeManager)
  {
    this.themeManager = themeManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.PageParamJsonGetter
 * JD-Core Version:    0.6.1
 */