package com.enation.app.shop.core.service;

public class SnDuplicateException extends RuntimeException
{
  public SnDuplicateException(String sn)
  {
    super("货号[" + sn + "]重复");
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.SnDuplicateException
 * JD-Core Version:    0.6.1
 */