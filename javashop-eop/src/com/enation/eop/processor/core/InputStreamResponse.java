package com.enation.eop.processor.core;

import com.enation.framework.util.StringUtil;
import java.io.InputStream;

public class InputStreamResponse
  implements Response
{
  private String contentType;
  private InputStream inputStream;
  private int statusCode;

  public InputStreamResponse(InputStream in)
  {
    this.inputStream = in;
  }

  public String getContent()
  {
    if (this.inputStream == null) {
      return "";
    }
    return StringUtil.inputStream2String(this.inputStream);
  }

  public int getStatusCode() {
    return this.statusCode;
  }

  public String getContentType() {
    return this.contentType;
  }

  public void setContent(String content)
  {
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
    return this.inputStream;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.core.InputStreamResponse
 * JD-Core Version:    0.6.1
 */