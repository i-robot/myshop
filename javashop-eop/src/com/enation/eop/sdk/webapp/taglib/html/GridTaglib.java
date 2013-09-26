package com.enation.eop.sdk.webapp.taglib.html;

import com.enation.eop.sdk.webapp.bean.Grid;
import com.enation.eop.sdk.webapp.taglib.HtmlTaglib;
import com.enation.eop.sdk.webapp.taglib.html.support.GridAjaxPageHtmlBuilder;
import com.enation.framework.database.Page;
import com.enation.framework.pager.IPageHtmlBuilder;
import com.enation.framework.pager.impl.SimplePageHtmlBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class GridTaglib extends HtmlTaglib
{
  private String from;
  private String pager;
  private String gridid;
  private String ajax;

  protected String postStart()
  {
    this.gridid = ("" + System.currentTimeMillis());
    return "<div class=\"gridbody\"  gridid='" + this.gridid + "' ><table>";
  }

  protected String postEnd()
  {
    StringBuffer str = new StringBuffer();
    str.append("</table>");
    if ((this.pager == null) || (this.pager.equals("yes"))) {
      str.append(buildPageHtml());
    }
    str.append("</div>");
    return str.toString();
  }

  private String buildPageHtml()
  {
    String tempPage = getRequest().getParameter("page");
    int pageNo = 1;
    if ((tempPage != null) && (!tempPage.toString().equals(""))) {
      pageNo = Integer.valueOf(tempPage.toString()).intValue();
    }

    Object obj = this.pageContext.getAttribute(this.from);
    if (obj == null) {
      obj = getRequest().getAttribute(this.from);
      if (obj == null) {
        return "";
      }
      this.pageContext.setAttribute(this.from, obj);
    }

    Page page = null;
    if ((obj instanceof Page))
      page = (Page)obj;
    else if ((obj instanceof Grid))
      page = ((Grid)obj).getWebpage();
    else {
      return "";
    }
    int pageSize = page.getPageSize();
    long totalCount = page.getTotalCount();

    IPageHtmlBuilder pageHtmlBuilder = null;
    if ("yes".equals(this.ajax))
    {
      pageHtmlBuilder = new GridAjaxPageHtmlBuilder(pageNo, totalCount, pageSize, this.gridid);
    }
    else
    {
      pageHtmlBuilder = new SimplePageHtmlBuilder(pageNo, totalCount, pageSize);
    }

    return pageHtmlBuilder.buildPageHtml();
  }

  public String getFrom()
  {
    return this.from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getPager() {
    return this.pager;
  }

  public void setPager(String pager) {
    this.pager = pager;
  }

  public String getAjax() {
    return this.ajax;
  }

  public void setAjax(String ajax) {
    this.ajax = ajax;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.GridTaglib
 * JD-Core Version:    0.6.1
 */