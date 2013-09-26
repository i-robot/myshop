package com.enation.framework.pager.impl;

import com.enation.framework.pager.AbstractPageHtmlBuilder;

public class SimplePageHtmlBuilder extends AbstractPageHtmlBuilder
{
  public SimplePageHtmlBuilder(long _pageNum, long _totalCount, int _pageSize)
  {
    super(_pageNum, _totalCount, _pageSize);
  }

  protected String getUrlStr(long page)
  {
    StringBuffer linkHtml = new StringBuffer();
    linkHtml.append("href='");
    linkHtml.append(this.url);
    linkHtml.append("page=");
    linkHtml.append(page);
    linkHtml.append("'>");
    return linkHtml.toString();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.pager.impl.SimplePageHtmlBuilder
 * JD-Core Version:    0.6.1
 */