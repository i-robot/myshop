package com.enation.framework.pager.impl;

import com.enation.framework.pager.AbstractPageHtmlBuilder;

public class WidgetPagerHtmlBuilder extends AbstractPageHtmlBuilder
{
  public WidgetPagerHtmlBuilder(long _pageNum, long _totalCount, int _pageSize)
  {
    super(_pageNum, _totalCount, _pageSize);
  }

  protected String getUrlStr(long page)
  {
    return null;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.pager.impl.WidgetPagerHtmlBuilder
 * JD-Core Version:    0.6.1
 */