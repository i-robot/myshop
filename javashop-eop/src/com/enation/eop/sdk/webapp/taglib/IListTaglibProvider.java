package com.enation.eop.sdk.webapp.taglib;

import java.util.List;
import javax.servlet.jsp.PageContext;

public abstract interface IListTaglibProvider
{
  public abstract List getData(IListTaglibParam paramIListTaglibParam, PageContext paramPageContext);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.IListTaglibProvider
 * JD-Core Version:    0.6.1
 */