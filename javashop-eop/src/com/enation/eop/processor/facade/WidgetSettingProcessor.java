package com.enation.eop.processor.facade;

import com.enation.eop.processor.Processor;
import com.enation.eop.processor.core.Response;
import com.enation.eop.processor.core.StringResponse;
import com.enation.eop.processor.widget.IWidgetCfgHtmlParser;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.util.RequestUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class WidgetSettingProcessor
  implements Processor
{
  protected final Logger logger = Logger.getLogger(getClass());

  public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    Map params = RequestUtil.paramToMap(httpRequest);
    IWidgetCfgHtmlParser widgetCfgParser = (IWidgetCfgHtmlParser)SpringContextHolder.getBean("localWidgetCfgPaser");

    String content = widgetCfgParser.pase(params);
    Response response = new StringResponse();
    response.setContent(content);
    return response;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.WidgetSettingProcessor
 * JD-Core Version:    0.6.1
 */