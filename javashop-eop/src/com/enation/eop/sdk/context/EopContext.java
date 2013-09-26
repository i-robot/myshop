package com.enation.eop.sdk.context;

import com.enation.app.base.core.model.MultiSite;
import com.enation.eop.resource.model.EopSite;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import javax.servlet.http.HttpServletRequest;

public class EopContext
{
  private static ThreadLocal<HttpServletRequest> HttpRequestHolder = new ThreadLocal();
  private static ThreadLocal<EopContext> EopContextHolder = new ThreadLocal();
  private EopSite currentSite;
  private MultiSite currentChildSite;

  public static void setContext(EopContext context)
  {
    EopContextHolder.set(context);
  }

  public static void remove()
  {
    EopContextHolder.remove();
  }

  public static EopContext getContext()
  {
    EopContext context = (EopContext)EopContextHolder.get();
    return context;
  }

  public static void setHttpRequest(HttpServletRequest request) {
    HttpRequestHolder.set(request);
  }

  public static HttpServletRequest getHttpRequest()
  {
    return (HttpServletRequest)HttpRequestHolder.get();
  }

  public EopSite getCurrentSite()
  {
    return this.currentSite;
  }

  public void setCurrentSite(EopSite site) {
    this.currentSite = site;
  }

  public MultiSite getCurrentChildSite()
  {
    return this.currentChildSite;
  }

  public void setCurrentChildSite(MultiSite currentChildSite) {
    this.currentChildSite = currentChildSite;
  }

  public String getContextPath()
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      EopSite site = getCurrentSite();
      StringBuffer context = new StringBuffer("/user");
      context.append("/");
      context.append(site.getUserid());
      context.append("/");
      context.append(site.getId());
      return context.toString();
    }
    return "";
  }

  public String getResDomain()
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String domain = null;

    if (("1".equals(EopSetting.RESOURCEMODE)) && (!EopSetting.DEVELOPMENT_MODEL))
      domain = EopSetting.IMG_SERVER_DOMAIN;
    else {
      domain = request.getContextPath();
    }

    if (domain.endsWith("/")) domain = domain.substring(0, domain.length() - 1);

    domain = domain + getContext().getContextPath();
    return domain;
  }

  public String getResPath()
  {
    String path = EopSetting.IMG_SERVER_PATH;

    if (path.endsWith("/")) path = path.substring(0, path.length() - 1);

    path = path + getContext().getContextPath();
    return path;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.context.EopContext
 * JD-Core Version:    0.6.1
 */