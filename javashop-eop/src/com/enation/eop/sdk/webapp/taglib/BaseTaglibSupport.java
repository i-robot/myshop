package com.enation.eop.sdk.webapp.taglib;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class BaseTaglibSupport extends BodyTagSupport
{
  private static final long serialVersionUID = -8939393391060656141L;

  protected ServletContext getServletContext()
  {
    ServletContext servletContext = this.pageContext.getServletContext();

    return servletContext;
  }

  public HttpServletRequest getRequest()
  {
    HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();

    return request;
  }

  public HttpServletResponse getResponse() {
    HttpServletResponse response = (HttpServletResponse)this.pageContext.getResponse();

    return response;
  }

  protected JspWriter getWriter()
  {
    return this.pageContext.getOut();
  }

  protected void print(String s)
  {
    try
    {
      getWriter().write(s);
      getWriter().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void println(String s)
  {
    print(s + "\n");
  }

  protected String getContextPath() {
    return getRequest().getContextPath();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.BaseTaglibSupport
 * JD-Core Version:    0.6.1
 */