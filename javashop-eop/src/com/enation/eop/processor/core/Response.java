package com.enation.eop.processor.core;

import java.io.InputStream;

public abstract interface Response
{
  public abstract String getContent();

  public abstract InputStream getInputStream();

  public abstract int getStatusCode();

  public abstract String getContentType();

  public abstract void setContent(String paramString);

  public abstract void setStatusCode(int paramInt);

  public abstract void setContentType(String paramString);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.core.Response
 * JD-Core Version:    0.6.1
 */