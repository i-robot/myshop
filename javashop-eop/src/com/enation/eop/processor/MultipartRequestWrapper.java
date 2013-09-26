package com.enation.eop.processor;

import com.opensymphony.util.TextUtils;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javazoom.upload.MultipartFormDataRequest;

public class MultipartRequestWrapper extends HttpServletRequestWrapper
{
  private MultipartFormDataRequest mrequest;

  public MultipartRequestWrapper(HttpServletRequest request, MultipartFormDataRequest _mrequest)
  {
    super(request);
    this.mrequest = _mrequest;
  }

  public String getParameter(String name)
  {
    String value = this.mrequest.getParameter(name);
    value = TextUtils.htmlEncode(value);

    return value;
  }

  public Enumeration getParameterNames()
  {
    return this.mrequest.getParameterNames();
  }

  public String[] getParameterValues(String name)
  {
    return this.mrequest.getParameterValues(name);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.MultipartRequestWrapper
 * JD-Core Version:    0.6.1
 */