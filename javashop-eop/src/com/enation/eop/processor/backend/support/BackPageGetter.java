package com.enation.eop.processor.backend.support;

import com.enation.app.base.core.service.auth.IAdminUserManager;
import com.enation.eop.processor.AbstractFacadeProcessor;
import com.enation.eop.processor.FacadePage;
import com.enation.eop.processor.core.Request;
import com.enation.eop.processor.core.Response;
import com.enation.eop.resource.IAdminThemeManager;
import com.enation.eop.resource.model.AdminTheme;
import com.enation.eop.resource.model.AdminUser;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.HttpUtil;
import javax.servlet.http.HttpServletRequest;

public class BackPageGetter extends AbstractFacadeProcessor
{
  private IAdminThemeManager adminThemeManager;
  private IAdminUserManager adminUserManager;

  public BackPageGetter(FacadePage page)
  {
    super(page);
  }

  protected Response process()
  {
    EopSite site = EopContext.getContext().getCurrentSite();

    this.adminThemeManager = ((IAdminThemeManager)SpringContextHolder.getBean("adminThemeManager"));
    this.adminUserManager = ((IAdminUserManager)SpringContextHolder.getBean("adminUserManager"));

    AdminTheme theme = this.adminThemeManager.get(site.getAdminthemeid());
    String path = "default";
    if (theme != null) {
      path = theme.getPath();
    }
    StringBuffer context = new StringBuffer();

    context.append(EopSetting.ADMINTHEMES_STORAGE_PATH);
    context.append("/");
    context.append(path);

    HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
    String contextPath = httpRequest.getContextPath();

    httpRequest.setAttribute("context", contextPath + context.toString());
    httpRequest.setAttribute("title", site.getSitename());
    httpRequest.setAttribute("ico", site.getIcofile());
    httpRequest.setAttribute("logo", site.getLogofile());
    httpRequest.setAttribute("version", EopSetting.VERSION);
    httpRequest.setAttribute("bkloginpicfile", site.getBkloginpicfile());
    httpRequest.setAttribute("bklogo", site.getBklogofile() == null ? site.getLogofile() : site.getBklogofile());

    String uri = this.page.getUri();

    if (uri.startsWith("/admin/main")) {
      AdminUser user = this.adminUserManager.getCurrentUser();
      user = this.adminUserManager.get(user.getUserid());
      httpRequest.setAttribute("user", user);
      uri = context.toString() + "/main.jsp";

      this.request = new HelpDivWrapper(this.page, this.request);
    }
    else if ((uri.equals("/admin/")) || (uri.equals("/admin/index.jsp")))
    {
      String username = HttpUtil.getCookieValue(httpRequest, "loginname");
      httpRequest.setAttribute("username", username);
      uri = context.toString() + "/login.jsp";
    }
    else {
      if (EopSetting.EXTENSION.equals("action")) {
        uri = uri.replace(".do", ".action");
      }

      String ajax = httpRequest.getParameter("ajax");
      if (!"yes".equals(ajax)) {
        this.request = new BackTemplateWrapper(this.page, this.request);
        this.request = new HelpDivWrapper(this.page, this.request);
      }

    }

    Response response = this.request.execute(uri, this.httpResponse, httpRequest);

    return response;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.backend.support.BackPageGetter
 * JD-Core Version:    0.6.1
 */