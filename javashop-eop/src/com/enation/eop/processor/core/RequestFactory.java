package com.enation.eop.processor.core;

public abstract class RequestFactory
{
  public static Request getRequest(int model)
  {
    Request request = null;

    if (model == 1) {
      request = new RemoteRequest();
    }
    if (model == 0) {
      request = new LocalRequest();
    }
    return request;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.core.RequestFactory
 * JD-Core Version:    0.6.1
 */