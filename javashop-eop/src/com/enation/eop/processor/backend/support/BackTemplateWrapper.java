package com.enation.eop.processor.backend.support;

import com.enation.eop.processor.AbstractFacadePagetParser;
import com.enation.eop.processor.FacadePage;
import com.enation.eop.processor.core.Request;
import com.enation.eop.processor.core.Response;
import com.enation.eop.sdk.utils.JspUtil;
import javax.servlet.http.HttpServletRequest;

public class BackTemplateWrapper extends AbstractFacadePagetParser
{
  public BackTemplateWrapper(FacadePage page, Request request)
  {
    super(page, request);
  }

  protected Response parse(Response response)
  {
    this.httpRequest.setAttribute("content", response.getContent());
    String content = JspUtil.getJspOutput("/admin/main_frame.jsp", this.httpRequest, this.httpResponse);
    response.setContent(content);
    return response;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.backend.support.BackTemplateWrapper
 * JD-Core Version:    0.6.1
 */