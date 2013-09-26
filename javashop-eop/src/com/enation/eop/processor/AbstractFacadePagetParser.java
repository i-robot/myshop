package com.enation.eop.processor;

import com.enation.eop.processor.core.Request;

public abstract class AbstractFacadePagetParser extends AbstractParser
{
  protected FacadePage page;

  public AbstractFacadePagetParser(FacadePage page, Request request)
  {
    super(request);
    this.page = page;
  }

  public FacadePage getPage() {
    return this.page;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.AbstractFacadePagetParser
 * JD-Core Version:    0.6.1
 */