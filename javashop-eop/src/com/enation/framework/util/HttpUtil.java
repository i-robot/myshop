package com.enation.framework.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtil
{
  public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int time)
  {
    try
    {
      if (cookieValue != null)
        cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setMaxAge(time);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  public static void addCookie(HttpServletResponse response, String domain, String path, String cookieName, String cookieValue, int time) {
    try {
      cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
    } catch (Exception ex) {
    }
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setMaxAge(time);
    cookie.setDomain(domain);
    cookie.setPath(path);
    response.addCookie(cookie);
  }

  public static void addCookie1(HttpServletResponse response, String cookieName, String cookieValue, int time)
  {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setMaxAge(time);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  public static String getCookieValue(HttpServletRequest request, String cookieName, String domain, String path) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if ((domain.equals(cookies[i].getDomain())) && (path.equals(cookies[i].getPath())) && (cookieName.equals(cookies[i].getName()))) {
          return cookies[i].getValue();
        }
      }
    }
    return null;
  }

  public static String getCookieValue(HttpServletRequest request, String cookieName)
  {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++)
      {
        if (cookieName.equals(cookies[i].getName())) {
          return cookies[i].getValue();
        }
      }
    }
    return null;
  }

  public static void main(String[] args) {
    String value = "%40g.139-341-na-1%2C183-493-na-1%3B";
    try {
      value = URLDecoder.decode(value, "UTF-8");
      System.out.println(value);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.HttpUtil
 * JD-Core Version:    0.6.1
 */