package com.enation.eop.processor.core;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestWrapper
  implements Request
{
  protected Log logger = LogFactory.getLog(getClass());
  protected Request request;

  public RequestWrapper(Request request)
  {
    this.request = request;
  }

  public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    return this.request.execute(uri, httpResponse, httpRequest);
  }

  public Response execute(String uri)
  {
    return this.request.execute(uri);
  }

  public Request getRequest() {
    return this.request;
  }

  public void setExecuteParams(Map<String, String> params)
  {
    this.request.setExecuteParams(params);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.core.RequestWrapper
 * JD-Core Version:    0.6.1
 */