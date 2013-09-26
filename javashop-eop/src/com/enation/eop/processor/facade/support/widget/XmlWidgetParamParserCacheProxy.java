package com.enation.eop.processor.facade.support.widget;

import com.enation.eop.processor.widget.IWidgetParamParser;
import com.enation.framework.cache.AbstractCacheProxy;
import com.enation.framework.cache.ICache;
import java.util.Map;

public class XmlWidgetParamParserCacheProxy extends AbstractCacheProxy
  implements IWidgetParamParser
{
  private static String cacheName = "widget_key";
  private IWidgetParamParser xmlWidgetParamParserImpl;

  public XmlWidgetParamParserCacheProxy(IWidgetParamParser _xmlWidgetParamParserImpl)
  {
    super(cacheName);
    this.xmlWidgetParamParserImpl = _xmlWidgetParamParserImpl;
  }

  public Map<String, Map<String, Map<String, String>>> parse()
  {
    Object obj = this.cache.get("obj");

    if (obj == null) {
      obj = this.xmlWidgetParamParserImpl.parse();
      this.cache.put("obj", obj);
    }

    return (Map)obj;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.XmlWidgetParamParserCacheProxy
 * JD-Core Version:    0.6.1
 */