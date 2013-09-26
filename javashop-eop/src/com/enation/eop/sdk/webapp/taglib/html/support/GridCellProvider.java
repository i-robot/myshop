package com.enation.eop.sdk.webapp.taglib.html.support;

import com.enation.eop.sdk.webapp.plugin.AbstractPluginsBundle;
import com.enation.eop.sdk.webapp.taglib.html.GridCellTaglib;
import com.enation.eop.sdk.webapp.taglib.html.GridTaglib;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.Tag;

public class GridCellProvider extends AbstractPluginsBundle
{
  private String sort;
  private String sortDefault;
  private String order;
  private int isSort;
  private String style;
  private String width;
  private String height;
  private String align;
  private String clazz;
  private GridCellTaglib celltaglib;
  private String plugin_type;
  private HttpServletRequest request;
  private HttpServletResponse response;

  public void initCellProvider(GridCellTaglib _celltaglib)
  {
    this.celltaglib = _celltaglib;
    this.sort = this.celltaglib.getSort();
    this.sortDefault = this.celltaglib.getSortDefault();

    this.isSort = this.celltaglib.getIsSort();
    this.style = this.celltaglib.getStyle();
    this.width = this.celltaglib.getWidth();
    this.height = this.celltaglib.getHeight();
    this.align = this.celltaglib.getAlign();
    this.request = this.celltaglib.getRequest();
    this.response = this.celltaglib.getResponse();
    this.order = this.request.getParameter("order");
    this.clazz = this.celltaglib.getClazz();
    this.plugin_type = this.celltaglib.getPlugin_type();
  }

  public String getStartHtml() {
    if (this.order == null) this.isSort = 0; else this.isSort = 1;

    this.order = ((this.order == null) || (this.order.equals("")) ? this.sort + " desc" : this.order);
    String str = "";
    String parentName = this.celltaglib.getParent().getClass().getName();

    if ((parentName != null) && (parentName.endsWith("GridHeaderTaglib"))) {
      str = "<th ";
      if (this.width != null) {
        str = str + "width='" + this.width + "'";
      }

      if (this.height != null) {
        str = str + "height='" + this.height + "'";
      }

      if (this.align != null)
        str = str + "align='" + this.align + "'";
    }
    else {
      str = "<td ";
      if (this.align != null) {
        str = str + "align='" + this.align + "'";
      }

    }

    if (this.clazz != null) {
      str = str + " class='" + this.clazz + "'";
    }

    if (this.style != null) {
      str = str + " style='" + this.style + "'";
    }
    str = str + ">";

    init();
    if (this.sort != null) {
      str = str + getSortOpStart();
    }
    return str;
  }

  private void init() {
    GridTaglib tag = (GridTaglib)this.celltaglib.getParent().getParent();
  }

  private int getCanSort()
  {
    if (this.isSort == 0) {
      if ((this.sortDefault != null) && (this.sortDefault.equals("yes"))) {
        return 1;
      }
      return 0;
    }

    String regEx = "(\\s+)";
    Pattern p = Pattern.compile(regEx);
    String[] op = p.split(this.order);

    if (op[0].equals(this.sort)) {
      return 1;
    }

    return 0;
  }

  private String getUrl()
  {
    String contextPath = this.request.getContextPath();
    String queryString = this.request.getQueryString();

    String servletPath = null;

    servletPath = this.request.getServletPath();

    queryString = queryString == null ? "" : queryString;
    String regEx = "(&||\\?)order=.*";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(queryString);
    queryString = m.replaceAll("");

    queryString = "?" + queryString + "&";

    return contextPath + servletPath + queryString;
  }

  private String getSortOpStart()
  {
    StringBuffer buffer = new StringBuffer();

    if (this.order.endsWith("asc")) {
      buffer.append("<a href=\"");

      buffer.append(getUrl());
      buffer.append("order=");
      buffer.append(this.sort);
      buffer.append(" desc");
      buffer.append("\"");

      if (getCanSort() == 1) {
        buffer.append(" class=\"desc\"");
      }
      buffer.append(">");
    }

    if (this.order.endsWith("desc"))
    {
      buffer.append("<a href=\"");

      buffer.append(getUrl());
      buffer.append("order=");
      buffer.append(this.sort);
      buffer.append(" asc");
      buffer.append("\"");

      if (getCanSort() == 1) {
        buffer.append(" class=\"asc\"");
      }
      buffer.append(">");
    }

    return buffer.toString();
  }

  private String getSortOpEnd()
  {
    StringBuffer buffer = new StringBuffer();

    buffer.append("</a>");

    return buffer.toString();
  }

  public String getEndHtml()
  {
    String str = "";
    if (this.sort != null) {
      if (getCanSort() == 1)
        str = str + getSortOpEnd();
      else {
        str = str + "</a>";
      }
    }
    int isTh = 0;

    String parentName = this.celltaglib.getParent().getClass().getName();
    if ((parentName != null) && (parentName.endsWith("GridHeaderTaglib"))) {
      str = str + "</th>";
      isTh = 1;
    } else {
      str = str + "</td>";
    }
    StringBuffer buffer = new StringBuffer();
    if (this.plugin_type != null)
      performPlugins("grid_cell_plugin", new Object[] { this.plugin_type, this.request, this.response, buffer, Integer.valueOf(isTh) });
    str = str + buffer.toString();

    return str;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.support.GridCellProvider
 * JD-Core Version:    0.6.1
 */