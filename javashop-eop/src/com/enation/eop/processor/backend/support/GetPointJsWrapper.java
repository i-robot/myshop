package com.enation.eop.processor.backend.support;

import com.enation.eop.processor.AbstractFacadePagetParser;
import com.enation.eop.processor.FacadePage;
import com.enation.eop.processor.core.Request;
import com.enation.eop.processor.core.Response;

public class GetPointJsWrapper extends AbstractFacadePagetParser
{
  public GetPointJsWrapper(FacadePage page, Request request)
  {
    super(page, request);
  }

  protected Response parse(Response response) {
    String content = response.getContent();
    content = content + "<script>$(function(){Eop.Point.init();});</script>";
    response.setContent(content);
    return response;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.backend.support.GetPointJsWrapper
 * JD-Core Version:    0.6.1
 */