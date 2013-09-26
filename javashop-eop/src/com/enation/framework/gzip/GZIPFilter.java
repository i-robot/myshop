package com.enation.framework.gzip;

import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GZIPFilter
  implements Filter
{
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException
  {
    if ((req instanceof HttpServletRequest)) {
      HttpServletRequest request = (HttpServletRequest)req;
      HttpServletResponse response = (HttpServletResponse)res;

      String ae = request.getHeader("accept-encoding");
      if ((ae != null) && (ae.indexOf("gzip") != -1)) {
        GZIPResponseWrapper gZIPResponseWrapper = new GZIPResponseWrapper(response);
        chain.doFilter(req, gZIPResponseWrapper);
        gZIPResponseWrapper.finishResponse();
        return;
      }
      chain.doFilter(req, res);
    } else {
      System.out.println("not servlet request...");
    }
  }

  public void init(FilterConfig filterConfig) {
    System.out.println("The GZIP Is Enable");
  }

  public void destroy()
  {
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.gzip.GZIPFilter
 * JD-Core Version:    0.6.1
 */