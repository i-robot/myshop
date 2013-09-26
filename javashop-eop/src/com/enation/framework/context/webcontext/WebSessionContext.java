package com.enation.framework.context.webcontext;

import java.util.Set;
import javax.servlet.http.HttpSession;

public abstract interface WebSessionContext<T>
{
  public static final String sessionAttributeKey = "EOPSessionKey";

  public abstract HttpSession getSession();

  public abstract void setSession(HttpSession paramHttpSession);

  public abstract void invalidateSession();

  public abstract void setAttribute(String paramString, T paramT);

  public abstract T getAttribute(String paramString);

  public abstract Set<T> getAttributeNames();

  public abstract void removeAttribute(String paramString);

  public abstract void destory();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.context.webcontext.WebSessionContext
 * JD-Core Version:    0.6.1
 */