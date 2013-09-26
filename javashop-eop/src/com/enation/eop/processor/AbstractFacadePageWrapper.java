package com.enation.eop.processor;

import com.enation.eop.processor.core.Request;

public abstract class AbstractFacadePageWrapper extends AbstractWrapper
{
  protected FacadePage page;

  public AbstractFacadePageWrapper(FacadePage page, Request request)
  {
    super(request);
    this.page = page;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.AbstractFacadePageWrapper
 * JD-Core Version:    0.6.1
 */