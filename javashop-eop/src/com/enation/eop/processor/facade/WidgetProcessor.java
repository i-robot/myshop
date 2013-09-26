package com.enation.eop.processor.facade;

import com.enation.eop.processor.Processor;
import com.enation.eop.processor.SafeHttpRequestWrapper;
import com.enation.eop.processor.core.Response;
import com.enation.eop.processor.core.StringResponse;
import com.enation.eop.processor.facade.support.LocalWidgetPaser;
import com.enation.eop.processor.widget.IWidgetPaser;
import com.enation.framework.util.RequestUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WidgetProcessor
  implements Processor
{
  public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    httpRequest = new SafeHttpRequestWrapper(httpRequest);

    Map widgetParams = RequestUtil.paramToMap(httpRequest);

    IWidgetPaser widgetPaser = new LocalWidgetPaser();
    String content = widgetPaser.pase(widgetParams);
    Response response = new StringResponse();

    response.setContent(content);
    return response;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.WidgetProcessor
 * JD-Core Version:    0.6.1
 */