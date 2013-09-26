package com.enation.eop.processor;

import com.opensymphony.util.TextUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class SafeHttpRequestWrapper extends HttpServletRequestWrapper
{
  public SafeHttpRequestWrapper(HttpServletRequest request)
  {
    super(request);
  }

  private String safeFilter(String value)
  {
    if (value == null) return null;

    value = TextUtils.htmlEncode(value);
    return value;
  }

  private void safeFilter(String[] values)
  {
    if (values != null)
      for (int i = 0; i < values.length; i++)
        values[i] = safeFilter(values[i]);
  }

  public String getParameter(String name)
  {
    String value = super.getParameter(name);
    value = safeFilter(value);
    return value;
  }

  public Map getParameterMap()
  {
    Map map = super.getParameterMap();
    Iterator keiter = map.keySet().iterator();
    while (keiter.hasNext()) {
      String name = keiter.next().toString();
      Object value = map.get(name);
      if ((value instanceof String)) {
        value = safeFilter((String)value);
      }
      if ((value instanceof String[])) {
        String[] values = (String[])value;
        safeFilter(values);
      }
    }

    return map;
  }
  public String[] getParameterValues(String arg0) {
    String[] values = super.getParameterValues(arg0);
    safeFilter(values);
    return values;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.SafeHttpRequestWrapper
 * JD-Core Version:    0.6.1
 */