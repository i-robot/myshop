package com.enation.eop.processor.facade.support.widget;

import com.enation.app.base.core.service.IWidgetCacheManager;
import com.enation.eop.processor.widget.IWidgetHtmlGetter;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.widget.IWidget;
import com.enation.framework.cache.AbstractCacheProxy;
import com.enation.framework.cache.ICache;
import com.enation.framework.context.spring.SpringContextHolder;
import java.util.HashMap;
import java.util.Map;

public class SaasWdgtHtmlGetterCacheProxy extends AbstractCacheProxy<Map<String, String>>
  implements IWidgetHtmlGetter
{
  private IWidgetHtmlGetter widgetHtmlGetter;

  public SaasWdgtHtmlGetterCacheProxy(IWidgetHtmlGetter _widgetHtmlGetter)
  {
    super("widgetCache");
    this.widgetHtmlGetter = _widgetHtmlGetter;
  }

  private boolean getCacheOpen() {
    IWidgetCacheManager widgetCacheManager = (IWidgetCacheManager)SpringContextHolder.getBean("widgetCacheManager");
    return widgetCacheManager.isOpen();
  }

  public String process(Map<String, String> params, String pageUri)
  {
    String widgetType = (String)params.get("type");
    String html = null;

    IWidget widget = (IWidget)SpringContextHolder.getBean(widgetType);
    if (widget == null) {
      return "widget[" + widgetType + "] is null";
    }

    if ((getCacheOpen()) && (widget.cacheAble()))
    {
      EopSite site = EopContext.getContext().getCurrentSite();
      String site_key = "widget_" + site.getUserid() + "_" + site.getId();

      Map htmlCache = (Map)this.cache.get(site_key);

      if (htmlCache == null) {
        htmlCache = new HashMap();
        this.cache.put(site_key, htmlCache);
      }

      String key = pageUri + "_" + (String)params.get("widgetid");

      html = (String)htmlCache.get(key);

      if (html == null)
      {
        html = this.widgetHtmlGetter.process(params, pageUri);

        htmlCache.put(key, html);
      }
      else
      {
        widget.update(params);
      }

    }
    else
    {
      html = this.widgetHtmlGetter.process(params, pageUri);
    }

    return html;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.SaasWdgtHtmlGetterCacheProxy
 * JD-Core Version:    0.6.1
 */