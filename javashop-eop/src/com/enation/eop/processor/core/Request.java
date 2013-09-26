package com.enation.eop.processor.core;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface Request
{
  public abstract Response execute(String paramString, HttpServletResponse paramHttpServletResponse, HttpServletRequest paramHttpServletRequest);

  public abstract Response execute(String paramString);

  public abstract void setExecuteParams(Map<String, String> paramMap);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.core.Request
 * JD-Core Version:    0.6.1
 */