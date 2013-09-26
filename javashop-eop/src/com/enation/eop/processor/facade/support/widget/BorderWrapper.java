package com.enation.eop.processor.facade.support.widget;

import com.enation.eop.processor.widget.IWidgetPaser;
import com.enation.eop.processor.widget.WidgetWrapper;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.utils.FreeMarkerUtil;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class BorderWrapper extends WidgetWrapper
{
  public BorderWrapper(IWidgetPaser paser)
  {
    super(paser);
  }

  public String pase(Map<String, String> params)
  {
    String content = super.pase(params);
    String border = (String)params.get("border");
    String widgetid = "widget_" + (String)params.get("widgetid");
    if ((border == null) || (border.equals("")) || (border.equals("none")))
    {
      if (("yes".equals(ThreadContextHolder.getHttpRequest().getParameter("ajax"))) || ("widget_header".equals(widgetid)))
      {
        return content;
      }
      return content;
    }

    EopSite site = EopContext.getContext().getCurrentSite();
    String contextPath = EopContext.getContext().getContextPath();
    String borderPath = contextPath + "/themes/" + site.getThemepath() + "/borders/";
    borderPath = EopSetting.EOP_PATH + borderPath;
    try
    {
      Map data = new HashMap();

      data.put("widgetid", widgetid);
      data.put("body", content);
      data.put("title", params.get("bordertitle"));
      data.putAll(params);
      Configuration cfg = FreeMarkerUtil.getFolderCfg(borderPath);
      Template temp = cfg.getTemplate(border + ".html");
      ByteOutputStream stream = new ByteOutputStream();
      Writer out = new OutputStreamWriter(stream);
      temp.process(data, out);
      out.flush();
      return stream.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }return content;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.BorderWrapper
 * JD-Core Version:    0.6.1
 */