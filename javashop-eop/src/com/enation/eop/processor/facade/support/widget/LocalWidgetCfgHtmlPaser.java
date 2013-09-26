package com.enation.eop.processor.facade.support.widget;

import com.enation.eop.processor.widget.IWidgetCfgHtmlParser;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.utils.FreeMarkerUtil;
import com.enation.eop.sdk.widget.IWidget;
import com.enation.framework.context.spring.SpringContextHolder;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

public class LocalWidgetCfgHtmlPaser
  implements IWidgetCfgHtmlParser
{
  public String pase(Map<String, String> widgetParams)
  {
    String type = (String)widgetParams.get("type");

    IWidget widget = (IWidget)SpringContextHolder.getBean(type);
    if (widget == null) throw new RuntimeException("widget[" + type + "]not found");

    String content = widget.setting(widgetParams);
    widgetParams.put("content", content);
    try
    {
      String fld = EopSetting.EOP_PATH + "/eop/";
      Configuration cfg = FreeMarkerUtil.getFolderCfg(fld);
      Template temp = cfg.getTemplate("widget_setting.html");
      ByteOutputStream stream = new ByteOutputStream();
      Writer out = new OutputStreamWriter(stream);
      temp.process(widgetParams, out);
      out.flush();
      content = stream.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return content;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.LocalWidgetCfgHtmlPaser
 * JD-Core Version:    0.6.1
 */