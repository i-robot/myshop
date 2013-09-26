package com.enation.eop.processor;

import com.enation.eop.processor.core.Request;
import com.enation.eop.processor.core.RequestFactory;
import com.enation.eop.processor.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractFacadeProcessor
  implements Processor
{
  protected FacadePage page;
  protected HttpServletRequest httpRequest;
  protected HttpServletResponse httpResponse;
  protected int mode;
  protected Request request;

  public AbstractFacadeProcessor(FacadePage page)
  {
    this.page = page;
  }

  public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    this.mode = mode;
    this.httpRequest = httpRequest;
    this.httpResponse = httpResponse;
    this.request = RequestFactory.getRequest(mode);

    return process();
  }

  protected abstract Response process();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.AbstractFacadeProcessor
 * JD-Core Version:    0.6.1
 */