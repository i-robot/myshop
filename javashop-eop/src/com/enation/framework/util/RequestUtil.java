package com.enation.framework.util;

import com.enation.framework.context.webcontext.ThreadContextHolder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public abstract class RequestUtil
{
  public static Map<String, String> paramToMap(HttpServletRequest request)
  {
    Map params = new HashMap();

    Map rMap = request.getParameterMap();
    Iterator rIter = rMap.keySet().iterator();

    while (rIter.hasNext()) {
      Object key = rIter.next();
      String value = request.getParameter(key.toString());
      if ((key != null) && (value != null)) {
        params.put(key.toString(), value.toString());
      }
    }
    return params;
  }

  public static String getRequestUrl(HttpServletRequest request)
  {
    String pathInfo = request.getPathInfo();
    String queryString = request.getQueryString();

    String uri = request.getServletPath();

    if (uri == null) {
      uri = request.getRequestURI();
      uri = uri.substring(request.getContextPath().length());
    }

    return new StringBuilder().append(uri).append(pathInfo == null ? "" : pathInfo).append(queryString == null ? "" : new StringBuilder().append("?").append(queryString).toString()).toString();
  }

  public static String getWholeUrl(HttpServletRequest request)
  {
    String servername = request.getServerName();
    String path = request.getServletPath();
    int port = request.getServerPort();

    String portstr = "";
    if (port != 80) {
      portstr = new StringBuilder().append(":").append(port).toString();
    }
    String contextPath = request.getContextPath();
    if (contextPath.equals("/")) {
      contextPath = "";
    }

    String url = new StringBuilder().append("http://").append(servername).append(portstr).append(contextPath).append("/").append(path).toString();

    return url;
  }

  public static String getDomain()
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String port = new StringBuilder().append("").append(request.getServerPort()).toString();
    if (port.equals("80"))
      port = "";
    else {
      port = new StringBuilder().append(":").append(port).toString();
    }

    String contextPath = request.getContextPath();
    if (contextPath.equals("/")) {
      contextPath = "";
    }

    String domain = new StringBuilder().append("http://").append(request.getServerName()).append(port).toString();
    domain = new StringBuilder().append(domain).append(contextPath).toString();
    return domain;
  }

  public static Integer getIntegerValue(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    if (StringUtil.isEmpty(value)) {
      return null;
    }
    return Integer.valueOf(value);
  }

  public static Double getDoubleValue(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    if (StringUtil.isEmpty(value)) {
      return null;
    }
    return Double.valueOf(value);
  }

  public static int getIntValue(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    if (StringUtil.isEmpty(value)) {
      return 0;
    }
    return Integer.valueOf(value).intValue();
  }

  public static String getRequestMethod(HttpServletRequest request)
  {
    String method = request.getParameter("_method");
    method = method == null ? "get" : method;
    method = method.toUpperCase();
    return method;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.RequestUtil
 * JD-Core Version:    0.6.1
 */