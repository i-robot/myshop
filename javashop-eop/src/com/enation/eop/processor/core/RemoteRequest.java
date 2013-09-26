package com.enation.eop.processor.core;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class RemoteRequest
  implements Request
{
  public HttpClient httpClient;
  private Map<String, String> params;

  public void setExecuteParams(Map<String, String> params)
  {
    this.params = params;
  }

  private HttpClient getHttpClient(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession();
    if (session.getAttribute("httpClient") == null)
    {
      HttpClient httpclient = new DefaultHttpClient();
      session.setAttribute("httpClient", httpclient);
    }

    return (HttpClient)session.getAttribute("httpClient");
  }

  public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    String method = httpRequest.getMethod();

    method = method.toUpperCase();

    HttpClient httpclient = getHttpClient(httpRequest);
    HttpUriRequest httpUriRequest = null;

    HttpPost httppost = new HttpPost(uri);

    HttpEntity entity = HttpEntityFactory.buildEntity(httpRequest, this.params);
    httppost.setEntity(entity);
    httpUriRequest = httppost;
    try
    {
      HttpResponse httpresponse = httpclient.execute(httpUriRequest);
      HttpEntity rentity = httpresponse.getEntity();

      return new InputStreamResponse(rentity.getContent());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return null;
  }

  public Response execute(String uri)
  {
    HttpClient httpclient = new DefaultHttpClient();
    HttpGet httpget = new HttpGet(uri);
    try
    {
      HttpResponse response = httpclient.execute(httpget);
      HttpEntity entity = response.getEntity();
      String content = EntityUtils.toString(entity, "utf-8");
      Response eopresponse = new StringResponse();
      eopresponse.setContent(content);
      return eopresponse;
    }
    catch (ClientProtocolException e)
    {
    }
    catch (IOException e)
    {
    }
    return null;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.core.RemoteRequest
 * JD-Core Version:    0.6.1
 */