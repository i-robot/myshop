package com.enation.app.shop.core.utils;

import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UrlUtils
{
  private static String[] searchFields = { "cat", "brand", "price", "sort", "prop", "nattr", "page", "tag", "keyword" };

  public static String getParamStr(String servletPath)
  {
    String pattern = "/search-(.*).html";
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(servletPath);
    String str = servletPath;
    if (m.find()) {
      str = m.replaceAll("$1");
    }
    return str;
  }

  public static String addUrl(String url, String name, String value)
  {
    String pattern = "/search-(.*)";
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(url);
    String str = "";
    if (m.find()) {
      str = m.replaceAll("$1");
    }
    if ((url != null) && (url.equals("/search"))) {
      return "/search-" + name + "-" + value + ".html";
    }
    if ((str == null) || (str.equals(""))) {
      return url;
    }
    str = getExParamUrl(str, "page");

    String newUrl = "";
    String temp = null;
    for (String field : searchFields) {
      temp = getParamStringValue(str, field);
      if (temp != null) {
        newUrl = newUrl + "-" + field + "-" + temp;
      }
      if ((name != null) && (name.equals(field))) {
        newUrl = newUrl + "-" + field + "-" + value;
      }
    }
    return "search" + newUrl + ".html";
  }

  public static String getParamStringValue(String param, String name)
  {
    param = getParamStr(param);
    String pattern = "(.*)" + name + "\\-(.[^\\-]*)(.*)";
    String value = null;
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(param);
    if (m.find()) {
      value = m.replaceAll("$2");
    }
    return value;
  }

  public static int getParamInitValue(String param, String name)
  {
    String temp_str = getParamStringValue(param, name);

    int value = Integer.valueOf(temp_str).intValue();
    return value;
  }

  public static String getExParamUrl(String url, String name)
  {
    String pattern = "(.*)" + name + "\\-(.[^\\-]*)(\\-|.*)(.*)";

    String value = "";
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(url);

    if (m.find())
      value = m.replaceAll("$1$4");
    else {
      value = url;
    }
    if (value != null) {
      if (value.startsWith("-")) {
        value = value.substring(1, value.length());
      }
      if (value.endsWith("-")) {
        value = value.substring(0, value.length() - 1);
      }
    }
    return value;
  }

  public static String appendParamValue(String url, String name, String value)
  {
    String old_value = getParamStringValue(url, name);
    String new_url = getExParamUrl(url, name);

    if (old_value != null) {
      if ("prop".equals(name)) {
        old_value = old_value.replaceAll(value.split("_")[0] + "_(\\d+)", "");
        old_value = old_value.replace(",,", ",");
        if (old_value.startsWith(",")) {
          old_value = old_value.substring(1, old_value.length());
        }

        if (old_value.endsWith(",")) {
          old_value = old_value.substring(0, old_value.length() - 1);
        }

      }

      if (!old_value.equals(""))
        value = "," + value;
    }
    else {
      old_value = "";
    }

    new_url = addUrl(new_url, name, old_value + value);

    return new_url;
  }

  public static String getUrlPrefix(String url)
  {
    String pattern = "/(.*)-(.*)";

    String value = null;
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(url);

    if (m.find())
      value = m.replaceAll("$1");
    else {
      value = null;
    }
    return value;
  }

  public static String getPropExSelf(int index, String url)
  {
    if (StringUtil.isEmpty(url)) {
      return url;
    }
    String propstr = getParamStringValue(url, "prop");
    if (StringUtil.isEmpty(propstr)) return url;

    String newprostr = "";
    String[] propar = propstr.split(",");
    for (String prop : propar) {
      String[] ar = prop.split("_");
      if (!ar[0].equals("" + index)) {
        if (!newprostr.equals(""))
          newprostr = newprostr + ",";
        newprostr = newprostr + prop;
      }
    }
    if (!StringUtil.isEmpty(newprostr))
      url = url.replaceAll(propstr, newprostr);
    else {
      url = url.replaceAll("-prop-" + propstr, "");
    }
    return url;
  }

  public static void main(String[] args)
  {
    String url = "cat{102}prop{2_1,1_2,0_3}name{2010}page{9}";
    String newUrl = "cat-102-page-9-prop-2_1,1_2,0_3";

    String temp = "/search-cat-1";
    System.out.println(addUrl(temp, "circlar", "3"));
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.utils.UrlUtils
 * JD-Core Version:    0.6.1
 */