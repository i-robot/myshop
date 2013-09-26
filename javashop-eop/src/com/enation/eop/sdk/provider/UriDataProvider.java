package com.enation.eop.sdk.provider;

import com.enation.eop.sdk.utils.JspUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UriDataProvider
  implements DataProvider
{
  private HttpServletRequest httpRequest;
  private HttpServletResponse httpResponse;
  private String url;

  public UriDataProvider(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String url)
  {
    this.httpRequest = httpRequest;
    this.httpResponse = httpResponse;
    this.url = url;
  }

  public Object load(Map<String, String> params)
  {
    String content = JspUtil.getJspOutput(this.url, this.httpRequest, this.httpResponse);

    return content;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.provider.UriDataProvider
 * JD-Core Version:    0.6.1
 */