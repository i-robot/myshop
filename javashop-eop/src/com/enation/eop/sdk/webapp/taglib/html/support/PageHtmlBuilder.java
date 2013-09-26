package com.enation.eop.sdk.webapp.taglib.html.support;

import com.enation.framework.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;

public class PageHtmlBuilder
{
  protected String url;
  protected HttpServletRequest request;
  private long pageNum;
  private long totalCount;
  private int pageSize;
  private long pageCount;
  private int showCount = 5;

  public PageHtmlBuilder(long _pageNum, long _totalCount, int _pageSize, HttpServletRequest _request) {
    this.pageNum = _pageNum;
    this.totalCount = _totalCount;
    this.pageSize = _pageSize;
    this.request = _request;
  }

  public String buildPageHtml() {
    init();
    StringBuffer pageStr = new StringBuffer("");
    pageStr.append("<div class=\"page\" >");
    pageStr.append(getHeadString());
    pageStr.append(getBodyString());
    pageStr.append(getFooterString());
    pageStr.append("</div>");
    return pageStr.toString() + "\n<script>EopPager.init('" + this.url + "');</script>";
  }

  protected void initUrl()
  {
    this.url = (this.request.getContextPath() + RequestUtil.getRequestUrl(this.request));
    this.url = this.url.replaceAll("(&||\\?)page=(\\d+)", "");
    this.url = this.url.replaceAll("(&||\\?)rmd=(\\d+)", "");
  }

  private void init()
  {
    this.pageSize = (this.pageSize < 1 ? 1 : this.pageSize);

    this.pageCount = (this.totalCount / this.pageSize);
    this.pageCount = (this.totalCount % this.pageSize > 0L ? this.pageCount + 1L : this.pageCount);

    this.pageNum = (this.pageNum > this.pageCount ? this.pageCount : this.pageNum);
    this.pageNum = (this.pageNum < 1L ? 1L : this.pageNum);

    if (this.url == null)
      initUrl();
  }

  protected String getHeadString()
  {
    StringBuffer headString = new StringBuffer("");
    headString.append("<span class=\"info\" >");
    headString.append("共");
    headString.append(this.totalCount);
    headString.append("条记录");
    headString.append("</span>\n");

    headString.append("<span class=\"info\">");
    headString.append(this.pageNum);
    headString.append("/");
    headString.append(this.pageCount);
    headString.append("</span>\n");

    headString.append("<ul>");
    if (this.pageNum > 1L)
    {
      headString.append("<li><a ");

      headString.append(getUrlStr(1L));
      headString.append("|&lt;");
      headString.append("</a></li>\n");

      headString.append("<li><a  ");

      headString.append(getUrlStr(this.pageNum - 1L));

      headString.append("&lt;&lt;");
      headString.append("</a></li>\n");
    }

    return headString.toString();
  }

  protected String getFooterString()
  {
    StringBuffer footerStr = new StringBuffer("");
    if (this.pageNum < this.pageCount)
    {
      footerStr.append("<li><a ");

      footerStr.append(getUrlStr(this.pageNum + 1L));

      footerStr.append("&gt;&gt;");
      footerStr.append("</a></li>\n");

      footerStr.append("<li><a ");

      footerStr.append(getUrlStr(this.pageCount));

      footerStr.append("&gt;|");
      footerStr.append("</a></li>\n");
    }

    footerStr.append("</ul>");
    return footerStr.toString();
  }

  protected String getBodyString()
  {
    StringBuffer pageStr = new StringBuffer();

    long start = this.pageNum - this.showCount / 2;
    start = start <= 1L ? 1L : start;

    long end = start + this.showCount;
    end = end > this.pageCount ? this.pageCount : end;

    for (long i = start; i <= end; i += 1L)
    {
      pageStr.append("<li><a ");
      if (i != this.pageNum)
      {
        pageStr.append(getUrlStr(i));
      }
      else pageStr.append(" class=\"selected\">");

      pageStr.append(i);
      pageStr.append("</a></li>\n");
    }

    return pageStr.toString();
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

  public void setUrl(String _url)
  {
    this.url = _url;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.support.PageHtmlBuilder
 * JD-Core Version:    0.6.1
 */