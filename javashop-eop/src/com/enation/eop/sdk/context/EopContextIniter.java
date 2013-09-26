package com.enation.eop.sdk.context;

import com.enation.app.base.core.model.MultiSite;
import com.enation.app.base.core.service.IMultiSiteManager;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EopContextIniter
{
  public static void init(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
  {
    FreeMarkerPaser.set(new FreeMarkerPaser());
    FreeMarkerPaser fmp = FreeMarkerPaser.getInstance();

    HttpSession session = httpRequest.getSession();
    ThreadContextHolder.getSessionContext().setSession(session);
    EopContext.setHttpRequest(httpRequest);
    ThreadContextHolder.setHttpRequest(httpRequest);
    ThreadContextHolder.setHttpResponse(httpResponse);
    httpRequest.setAttribute("staticserver", EopSetting.IMG_SERVER_DOMAIN);
    httpRequest.setAttribute("ext", EopSetting.EXTENSION);
    String servletPath = httpRequest.getServletPath();

    if (servletPath.startsWith("/statics")) {
      return;
    }
    if (servletPath.startsWith("/install")) {
      EopSite site = new EopSite();
      site.setUserid(Integer.valueOf(1));
      site.setId(Integer.valueOf(1));
      site.setThemeid(Integer.valueOf(1));
      EopContext context = new EopContext();
      context.setCurrentSite(site);
      EopContext.setContext(context);
    } else {
      EopContext context = new EopContext();
      EopSite site = new EopSite();
      site.setUserid(Integer.valueOf(1));
      site.setId(Integer.valueOf(1));
      site.setThemeid(Integer.valueOf(1));
      context.setCurrentSite(site);
      EopContext.setContext(context);

      ISiteManager siteManager = (ISiteManager)SpringContextHolder.getBean("siteManager");
      site = siteManager.get("localhost");

      context.setCurrentSite(site);
      if (site.getMulti_site().intValue() == 1) {
        String domain = httpRequest.getServerName();
        IMultiSiteManager multiSiteManager = (IMultiSiteManager)SpringContextHolder.getBean("multiSiteManager");
        MultiSite multiSite = multiSiteManager.get(domain);
        context.setCurrentChildSite(multiSite);
      }

      EopContext.setContext(context);
      fmp.putData("site", site);
    }

    fmp.putData("ctx", httpRequest.getContextPath());
    fmp.putData("ext", EopSetting.EXTENSION);
    fmp.putData("staticserver", EopSetting.IMG_SERVER_DOMAIN);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.context.EopContextIniter
 * JD-Core Version:    0.6.1
 */