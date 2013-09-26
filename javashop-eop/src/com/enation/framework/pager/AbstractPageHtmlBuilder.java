package com.enation.framework.pager;

import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractPageHtmlBuilder
  implements IPageHtmlBuilder
{
  protected String url;
  protected HttpServletRequest request;
  protected long pageNum;
  protected long totalCount;
  protected int pageSize;
  protected long pageCount;
  private int showCount = 5;

  public AbstractPageHtmlBuilder(long _pageNum, long _totalCount, int _pageSize) {
    this.pageNum = _pageNum;
    this.totalCount = _totalCount;
    this.pageSize = _pageSize;
    this.request = ThreadContextHolder.getHttpRequest();
  }

  public String buildPageHtml() {
    init();
    StringBuffer pageStr = new StringBuffer("");
    pageStr.append("<div class=\"page\" >");
    pageStr.append(getHeadString());
    pageStr.append(getBodyString());
    pageStr.append(getFooterString());
    pageStr.append("</div>");
    return pageStr.toString();
  }

  protected void initUrl()
  {
    this.url = (this.request.getContextPath() + RequestUtil.getRequestUrl(this.request));

    this.url = this.url.replaceAll("(&||\\?)page=(\\d+)", "");
    this.url = this.url.replaceAll("(&||\\?)rmd=(\\d+)", "");
    this.url += "?";
  }

  protected void init()
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
      headString.append(" class=\"unselected\" ");
      headString.append(getUrlStr(1L));
      headString.append("|&lt;");
      headString.append("</a></li>\n");

      headString.append("<li><a  ");
      headString.append(" class=\"unselected\" ");
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
      footerStr.append(" class=\"unselected\" ");
      footerStr.append(getUrlStr(this.pageNum + 1L));
      footerStr.append("&gt;&gt;");
      footerStr.append("</a></li>\n");

      footerStr.append("<li><a ");
      footerStr.append(" class=\"unselected\" ");
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
      if (i != this.pageNum) {
        pageStr.append(" class=\"unselected\"");
        pageStr.append(getUrlStr(i));
      } else {
        pageStr.append(" class=\"selected\">");
      }

      pageStr.append(i);
      pageStr.append("</a></li>\n");
    }

    return pageStr.toString();
  }

  protected abstract String getUrlStr(long paramLong);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.pager.AbstractPageHtmlBuilder
 * JD-Core Version:    0.6.1
 */