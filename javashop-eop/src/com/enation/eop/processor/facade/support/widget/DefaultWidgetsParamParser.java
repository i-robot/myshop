package com.enation.eop.processor.facade.support.widget;

import com.enation.eop.processor.widget.IWidgetParamParser;
import com.enation.eop.processor.widget.WidgetXmlUtil;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DefaultWidgetsParamParser
  implements IWidgetParamParser
{
  private IWidgetParamParser widgetParamParser;

  public DefaultWidgetsParamParser(IWidgetParamParser widgetParamParser)
  {
    this.widgetParamParser = widgetParamParser;
  }

  public Map<String, Map<String, Map<String, String>>> parse()
  {
    String contextPath = EopContext.getContext().getContextPath();
    String path = EopSetting.EOP_PATH + contextPath + EopSetting.THEMES_STORAGE_PATH + "/widgets_default.xml";

    Map pageParams = this.widgetParamParser.parse();
    File file = new File(path);

    if (!file.exists()) {
      return pageParams;
    }

    Map defaultPageParams = WidgetXmlUtil.parse(path);

    Iterator pageIdItor = pageParams.keySet().iterator();

    while (pageIdItor.hasNext()) {
      String pageId = (String)pageIdItor.next();

      Map defaultWidgetParams = (Map)defaultPageParams.get(pageId);
      Map widgetParams = (Map)pageParams.get(pageId);

      if (defaultWidgetParams == null) {
        defaultPageParams.put(pageId, widgetParams);
      }
      else
      {
        Iterator widgetItor = widgetParams.keySet().iterator();
        while (widgetItor.hasNext()) {
          String widgetId = (String)widgetItor.next();

          Map oneDefWgtParams = (Map)defaultWidgetParams.get(widgetId);
          Map oneWgtParams = (Map)widgetParams.get(widgetId);

          if (oneDefWgtParams == null) {
            defaultWidgetParams.put(widgetId, oneWgtParams);
          }
          else {
            oneDefWgtParams.putAll(oneWgtParams);
          }

        }

      }

    }

    return defaultPageParams;
  }

  public static void main(String[] args) {
    Map params1 = new HashMap();
    Map params2 = new HashMap();

    params1.put("p1", "p1_1");
    params1.put("p2", "p1_2");
    params1.put("p3", "p1_3");

    params2.put("p2", "p1_2");
    params2.put("p3", "p2_3");
    params2.put("p4", "p2_3");

    params2.putAll(params1);

    System.out.println((String)params2.get("p4"));
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.DefaultWidgetsParamParser
 * JD-Core Version:    0.6.1
 */