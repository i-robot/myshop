package com.enation.eop.sdk.webapp.taglib.html.support;

import com.enation.framework.pager.AbstractPageHtmlBuilder;

public class GridAjaxPageHtmlBuilder extends AbstractPageHtmlBuilder
{
  private String gridid;

  public GridAjaxPageHtmlBuilder(long pageNum, long totalCount, int pageSize, String _gridid)
  {
    super(pageNum, totalCount, pageSize);
    this.gridid = _gridid;
  }

  public String buildPageHtml()
  {
    return super.buildPageHtml() + "<script>$(function(){$(\".gridbody[gridid='" + this.gridid + "']>.page\").gridAjaxPager('" + this.url + "');});</script>";
  }

  protected String getUrlStr(long page)
  {
    StringBuffer linkHtml = new StringBuffer();
    linkHtml.append("href='javascript:;'");
    linkHtml.append(" pageNo='");
    linkHtml.append(page);
    linkHtml.append("'>");
    return linkHtml.toString();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.support.GridAjaxPageHtmlBuilder
 * JD-Core Version:    0.6.1
 */