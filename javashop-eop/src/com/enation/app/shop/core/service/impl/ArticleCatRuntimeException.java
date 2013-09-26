package com.enation.app.shop.core.service.impl;

public class ArticleCatRuntimeException extends RuntimeException
{
  private int error_code;

  public ArticleCatRuntimeException(int code)
  {
    this.error_code = code;
  }

  public int getError_code()
  {
    return this.error_code;
  }

  public void setError_code(int error_code) {
    this.error_code = error_code;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.ArticleCatRuntimeException
 * JD-Core Version:    0.6.1
 */