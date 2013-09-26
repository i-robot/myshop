package com.enation.eop.processor.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class StringResponse
  implements Response
{
  private String content;
  private String contentType;
  private int statusCode;

  public StringResponse()
  {
    this.contentType = "text/html";
  }

  public void finalize() throws Throwable
  {
  }

  public String getContent()
  {
    return this.content;
  }

  public int getStatusCode() {
    return this.statusCode;
  }

  public String getContentType() {
    return this.contentType;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  public void setStatusCode(int code)
  {
    this.statusCode = code;
  }

  public void setContentType(String contentType)
  {
    this.contentType = contentType;
  }

  public InputStream getInputStream()
  {
    try
    {
      return new ByteArrayInputStream(this.content.getBytes("UTF-8"));
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.core.StringResponse
 * JD-Core Version:    0.6.1
 */