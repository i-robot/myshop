package com.enation.eop.sdk.widget;

import com.enation.app.base.component.widget.nav.Nav;
import com.enation.app.base.core.model.Member;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.RequestUtil;
import com.enation.framework.util.StringUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractWidget extends BaseSupport
  implements IWidget
{
  protected boolean showHtml = true;
  protected FreeMarkerPaser freeMarkerPaser;
  private Map<String, String> urls;
  protected String folder;
  protected boolean disableCustomPage = false;
  protected String action;
  private boolean enable = true;
  protected String pageName;
  private Map<String, String> actionPageMap;

  public String process(Map<String, String> params)
  {
    this.actionPageMap = new HashMap();

    HttpServletRequest request = ThreadContextHolder.getHttpRequest();

    String mustbelogin = (String)params.get("mustbelogin");
    if ("yes".equals(mustbelogin)) {
      Member member = UserServiceFactory.getUserService().getCurrentMember();
      if (member == null) {
        String forward = RequestUtil.getRequestUrl(request);
        if (!StringUtil.isEmpty(forward)) {
          try {
            if (forward.startsWith("/")) forward = forward.substring(1, forward.length());
            forward = URLEncoder.encode(forward, "UTF-8");
          }
          catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
        }

        HttpServletResponse response = ThreadContextHolder.getHttpResponse();
        try {
          response.sendRedirect("login.html?forward=" + forward);
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        return null;
      }
    }

    this.action = request.getParameter("action");

    String html = show(params);
    return html;
  }

  public void update(Map<String, String> params)
  {
  }

  public boolean cacheAble()
  {
    return true;
  }

  private void putRequestParam(String reqparams, Map<String, String> params)
  {
    if (!StringUtil.isEmpty(reqparams)) {
      HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
      String[] reqparamArray = StringUtils.split(reqparams, ",");
      for (String paramname : reqparamArray) {
        String value = httpRequest.getParameter(paramname);
        params.put(paramname, value);
      }
    }
  }

  private String show(Map<String, String> params)
  {
    this.freeMarkerPaser = FreeMarkerPaser.getInstance();
    this.freeMarkerPaser.setClz(getClass());
    this.freeMarkerPaser.setPageFolder(null);
    this.freeMarkerPaser.setPageName(null);

    String reqparams = (String)params.get("reqparams");
    putRequestParam(reqparams, params);

    this.freeMarkerPaser.putData(params);

    String customPage = (String)params.get("custom_page");
    this.folder = ((String)params.get("folder"));

    String showHtmlStr = (String)params.get("showhtml");
    this.showHtml = true;

    this.disableCustomPage = false;
    display(params);

    if (!this.disableCustomPage)
    {
      if (!StringUtil.isEmpty(customPage)) {
        this.pageName = customPage;
      }

      if ("yes".equals(params.get("actionpage"))) {
        if (!StringUtil.isEmpty(this.action)) {
          this.pageName = (customPage + "_" + this.action);
        }
      }
      else if (!StringUtil.isEmpty(this.action)) {
        String actionPage = (String)params.get("action_" + this.action);
        if (StringUtil.isEmpty(actionPage)) {
          actionPage = (String)this.actionPageMap.get(this.action);
        }
        if (!StringUtil.isEmpty(actionPage)) {
          this.pageName = actionPage;
        }

      }

      if (!StringUtil.isEmpty(this.pageName)) {
        this.freeMarkerPaser.setPageName(this.pageName);
      }

      if (!StringUtil.isEmpty(this.folder)) {
        EopSite site = EopContext.getContext().getCurrentSite();
        String contextPath = EopContext.getContext().getContextPath();
        this.freeMarkerPaser.setPageFolder(contextPath + "/themes/" + site.getThemepath() + "/" + this.folder);
      }
      else if (!StringUtil.isEmpty(customPage)) {
        EopSite site = EopContext.getContext().getCurrentSite();
        String contextPath = EopContext.getContext().getContextPath();
        this.freeMarkerPaser.setPageFolder(contextPath + "/themes/" + site.getThemepath());
      }

    }

    if ((!StringUtil.isEmpty(showHtmlStr)) && (showHtmlStr.equals("false"))) {
      this.showHtml = false;
    }

    if ((this.showHtml) || ("yes".equals(params.get("ischild"))))
    {
      String html = this.freeMarkerPaser.proessPageContent();
      if ("yes".equals(params.get("ischild"))) {
        putData("widget_" + (String)params.get("widgetid"), html);
      }
      return html;
    }
    return "";
  }

  protected String getThemePath()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    String contextPath = EopContext.getContext().getContextPath();
    return contextPath + "/themes/" + site.getThemepath();
  }

  protected void disableCustomPage()
  {
    this.disableCustomPage = true;
  }
  protected void enableCustomPage() {
    this.disableCustomPage = false;
  }

  public String setting(Map<String, String> params) {
    this.freeMarkerPaser = FreeMarkerPaser.getInstance();
    this.freeMarkerPaser.setClz(getClass());
    config(params);

    if (this.showHtml) {
      return this.freeMarkerPaser.proessPageContent();
    }
    return "";
  }

  protected abstract void display(Map<String, String> paramMap);

  protected abstract void config(Map<String, String> paramMap);

  protected void putData(String key, Object value)
  {
    this.freeMarkerPaser.putData(key, value);
  }

  protected void putData(Map<String, Object> map)
  {
    this.freeMarkerPaser.putData(map);
  }

  protected Object getData(String key)
  {
    return this.freeMarkerPaser.getData(key);
  }

  protected void setPathPrefix(String path)
  {
    this.freeMarkerPaser.setPathPrefix(path);
  }

  public void setPageName(String pageName)
  {
    this.disableCustomPage = false;
    this.pageName = pageName;
  }

  public void setActionPageName(String pageName)
  {
    this.disableCustomPage = false;
    this.actionPageMap.put(this.action, pageName);
  }

  public void makeSureSetPageName(String pageName)
  {
    this.freeMarkerPaser.setPageName(pageName);
  }

  public void setPageExt(String pageExt)
  {
    this.freeMarkerPaser.setPageExt(pageExt);
  }

  public void setPageFolder(String pageFolder) {
    this.freeMarkerPaser.setPageFolder(pageFolder);
  }

  protected void putNav(Nav nav)
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    List navList = (List)request.getAttribute("site_nav_list");
    navList = navList == null ? new ArrayList() : navList;
    navList.add(nav);
    request.setAttribute("site_nav_list", navList);
  }

  protected void setMsg(String msg)
  {
    putData("msg", msg);
  }

  protected void putUrl(String text, String url)
  {
    if (this.urls == null) {
      this.urls = new HashMap();
    }
    this.urls.put(text, url);
    putData("urls", this.urls);
    putData("jumpurl", url);
  }

  protected void showError(String msg)
  {
    disableCustomPage();
    setPageFolder(getThemePath());
    this.freeMarkerPaser.setPageName("error");
    setMsg(msg);
  }

  protected void showJson(String json)
  {
    disableCustomPage();
    setPageFolder("/commons/");
    this.freeMarkerPaser.setPageName("json");
    putData("json", json);
  }

  protected void showErrorJson(String message)
  {
    showJson("{result:0,message:'" + message + "'}");
  }

  protected void showSuccessJson(String message) {
    showJson("{result:1,message:'" + message + "'}");
  }

  protected void showError(String msg, String urlText, String url)
  {
    disableCustomPage();
    this.pageName = null;
    setPageFolder(getThemePath());
    this.freeMarkerPaser.setPageName("error");
    setMsg(msg);
    if ((urlText != null) && (url != null))
      putUrl(urlText, url);
  }

  protected void showSuccess(String msg)
  {
    disableCustomPage();
    this.pageName = null;
    setPageFolder(getThemePath());
    this.freeMarkerPaser.setPageName("success");
    setMsg(msg);
  }

  protected void showSuccess(String msg, String urlText, String url)
  {
    disableCustomPage();
    setPageFolder(getThemePath());
    this.freeMarkerPaser.setPageName("success");
    setMsg(msg);
    if ((urlText != null) && (url != null))
      putUrl(urlText, url);
  }

  protected void putData2(String key, Object value)
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    Object params_temp = request.getAttribute("eop_page_params");

    Map pageParams = null;

    if (params_temp == null) pageParams = new HashMap(); else {
      pageParams = (Map)params_temp;
    }
    pageParams.put(key, value);
    request.setAttribute("eop_page_params", pageParams);
  }

  protected int getPageNo()
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String page = request.getParameter("page");
    return StringUtil.toInt(page, 1);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.widget.AbstractWidget
 * JD-Core Version:    0.6.1
 */