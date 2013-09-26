package com.enation.eop.processor.facade.support;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.model.MultiSite;
import com.enation.app.base.core.service.IAccessRecorder;
import com.enation.eop.processor.IPagePaser;
import com.enation.eop.processor.core.UrlNotFoundException;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.eop.processor.facade.support.widget.SaasWdgtHtmlGetterCacheProxy;
import com.enation.eop.processor.facade.support.widget.WidgetHtmlGetter;
import com.enation.eop.processor.widget.IWidgetHtmlGetter;
import com.enation.eop.processor.widget.IWidgetParamParser;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.IThemeUriManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.Theme;
import com.enation.eop.resource.model.ThemeUri;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.eop.sdk.utils.FreeMarkerUtil;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacadePagePaser
  implements IPagePaser
{
  private IWidgetParamParser widgetParamParser;

  public synchronized String pase(String uri)
  {
    try
    {
      return doPase(uri);
    } catch (UrlNotFoundException e) {
      HttpServletResponse httpResponse = ThreadContextHolder.getHttpResponse();
      httpResponse.setStatus(404);
    }return get404Html();
  }

  public String get404Html()
  {
    EopSite site = EopContext.getContext().getCurrentSite();

    Map widgetData = new HashMap();
    widgetData.put("site", site);
    String originalUri = "/404.html";

    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    request.setAttribute("pageid", "404");
    request.setAttribute("tplFileName", "404");

    Map pages = this.widgetParamParser.parse();

    IWidgetHtmlGetter htmlGetter = new WidgetHtmlGetter();
    htmlGetter = new SaasWdgtHtmlGetterCacheProxy(htmlGetter);

    Map commonWidgets = (Map)pages.get("common");
    if (commonWidgets != null)
    {
      Set<String> idSet = commonWidgets.keySet();
      for (String id : idSet) {
        Map params = (Map)commonWidgets.get(id);
        String content = htmlGetter.process(params, originalUri);
        widgetData.put("widget_" + id, content);
      }
    }
    return parse(originalUri, widgetData);
  }

  public String doPase(String uri) {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();

    String originalUri = uri;
    if (EopSetting.EXTENSION.equals("action")) {
      uri = uri.replace(".do", ".action");
    }

    EopSite site = EopContext.getContext().getCurrentSite();
    if ((site.getIsimported() == 1) && (site.getSitestate() == 0)) {
      long now = DateUtil.getDatelineLong();
      int day_7 = 604800;
      if (site.getCreatetime().longValue() + day_7 < now) {
        return getOverdueHtml(site);
      }

    }

    if (site.getSiteon().intValue() == 1) {
      return site.getClosereson() == null ? "" : site.getClosereson();
    }

    Map widgetData = new HashMap();
    widgetData.put("site", site);
    String mode = "no";

    if (uri.indexOf("?mode") > 0) {
      mode = "edit";
    }

    if (uri.indexOf(63) > 0) {
      uri = uri.substring(0, uri.indexOf(63));
    }

    IThemeUriManager themeUriManager = (IThemeUriManager)SpringContextHolder.getBean("themeUriManager");

    ThemeUri themeUri = themeUriManager.getPath(uri);

    String tplFileName = themeUri.getPath();
    String pageid = tplFileName.substring(1, tplFileName.indexOf("."));
    request.setAttribute("pageid", pageid);
    request.setAttribute("tplFileName", pageid);

    if ((!StringUtil.isEmpty(themeUri.getKeywords())) || (!StringUtil.isEmpty(themeUri.getDescription()))) {
      FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
      if (!StringUtil.isEmpty(themeUri.getKeywords())) {
        freeMarkerPaser.putData("keywords", themeUri.getKeywords());
      }
      if (!StringUtil.isEmpty(themeUri.getDescription()))
        freeMarkerPaser.putData("description", themeUri.getDescription());
    }
    int result;
    if (EopSetting.RUNMODE.equals("2"))
    {
      IAccessRecorder accessRecorder = (IAccessRecorder)SpringContextHolder.getBean("accessRecorder");
      result = accessRecorder.record(themeUri);
    }

    Map pages = this.widgetParamParser.parse();

    Map widgets = (Map)pages.get(tplFileName);

    IWidgetHtmlGetter htmlGetter = new WidgetHtmlGetter();
    htmlGetter = new SaasWdgtHtmlGetterCacheProxy(htmlGetter);

    String ajax = request.getParameter("ajax");

    if (widgets != null)
    {
      String widgetid = request.getParameter("widgetid");
      if (("yes".equals(ajax)) && (!StringUtil.isEmpty(widgetid))) {
        Map wgtParams = (Map)widgets.get(widgetid);
        String content = htmlGetter.process(wgtParams, originalUri);
        return content;
      }

      Map mainparams = (Map)widgets.get("main");
      if (mainparams != null)
      {
        String content = htmlGetter.process(mainparams, originalUri);
        widgetData.put("widget_" + (String)mainparams.get("widgetid"), content);
        widgets.remove("main");
      }

      Set<String> idSet = widgets.keySet();

      for (String id : idSet)
      {
        boolean isCurrUrl = matchUrl(uri, id);

        if (((!tplFileName.equals("/default.html")) && (!tplFileName.equals("/member.html"))) || (!id.startsWith("/")) || (isCurrUrl))
        {
          Map params = (Map)widgets.get(id);
          params.put("mode", mode);
          String content = htmlGetter.process(params, originalUri);

          if (((tplFileName.equals("/default.html")) || (tplFileName.equals("/member.html"))) && (id.startsWith("/")) && (isCurrUrl)) {
            request.setAttribute("pageid", params.get("type"));
            widgetData.put("widget_main", content);
          } else {
            widgetData.put("widget_" + id, content);
          }
        }
      }
    }
    Map commonWidgets;
    if (!"yes".equals(ajax))
    {
      commonWidgets = (Map)pages.get("common");
      if (commonWidgets != null)
      {
        Set<String> idSet = commonWidgets.keySet();
        for (String id : idSet) {
          Map params = (Map)commonWidgets.get(id);
          String content = htmlGetter.process(params, originalUri);
          widgetData.put("widget_" + id, content);
        }
      }
    }
    return parse(tplFileName, widgetData);
  }

  public String parse(String tplFileName, Map<String, Object> widgetData)
  {
    try {
      HttpServletRequest request = ThreadContextHolder.getHttpRequest();
      IThemeManager themeManager = (IThemeManager)SpringContextHolder.getBean("themeManager");
      EopSite site = EopContext.getContext().getCurrentSite();
      Integer themeid = null;

      if (site.getMulti_site().intValue() == 1) {
        MultiSite childSite = EopContext.getContext().getCurrentChildSite();
        themeid = childSite.getThemeid();
      } else {
        themeid = site.getThemeid();
      }

      Theme theme = themeManager.getTheme(themeid);
      String themePath = theme.getPath();

      String contextPath = EopContext.getContext().getContextPath();
      String themeFld = EopSetting.EOP_PATH + contextPath + EopSetting.THEMES_STORAGE_PATH + "/" + themePath;

      StringBuffer context = new StringBuffer();

      if (EopSetting.RESOURCEMODE.equals("1")) {
        context.append(EopSetting.IMG_SERVER_DOMAIN);
      }
      if (EopSetting.RESOURCEMODE.equals("2")) {
        if ("/".equals(EopSetting.CONTEXT_PATH))
          context.append("");
        else {
          context.append(EopSetting.CONTEXT_PATH);
        }
      }
      context.append(contextPath);
      context.append(EopSetting.THEMES_STORAGE_PATH);
      context.append("/");
      context.append(themePath + "/");
      widgetData.put("context", context.toString());
      widgetData.put("staticserver", EopSetting.IMG_SERVER_DOMAIN);
      widgetData.put("logo", site.getLogofile());
      widgetData.put("ctx", request.getContextPath());
      Member member = UserServiceFactory.getUserService().getCurrentMember();
      widgetData.put("member", member);

      Object params_temp = request.getAttribute("eop_page_params");

      if (params_temp != null) {
        widgetData.putAll((Map)params_temp);
      }

      Configuration cfg = FreeMarkerUtil.getFolderCfg(themeFld);
      Template temp = cfg.getTemplate(tplFileName);
      ByteOutputStream stream = new ByteOutputStream();

      Writer out = new OutputStreamWriter(stream);
      temp.process(widgetData, out);

      out.flush();
      return stream.toString();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }return "page pase error";
  }

  private boolean matchUrl(String uri, String targetUri)
  {
    Pattern p = Pattern.compile(targetUri, 34);
    Matcher m = p.matcher(uri);
    return m.find();
  }

  public static void main(String[] args)
  {
    String url = "/goods-1.html";
    if (url.indexOf(63) > 0)
      url = url.substring(0, url.indexOf(63));
  }

  public void setWidgetParamParser(IWidgetParamParser widgetParamParser) {
    this.widgetParamParser = widgetParamParser;
  }

  private String getWidgetHtml(String themePath, EopSite site) {
    String contextPath = EopContext.getContext().getContextPath();
    try {
      String themeFld = EopSetting.EOP_PATH + "/user/1/1" + EopSetting.THEMES_STORAGE_PATH + "/default/";

      Configuration cfg = FreeMarkerUtil.getFolderCfg(themeFld);
      Template temp = cfg.getTemplate("gameover.html");
      ByteOutputStream stream = new ByteOutputStream();

      Writer out = new OutputStreamWriter(stream);
      Map map = new HashMap();
      map.put("site", site);
      map.put("content", "");
      temp.process(map, out);

      out.flush();
      return stream.toString();
    }
    catch (Exception e)
    {
      return "挂件解析出错" + e.getMessage();
    }
  }

  private String getOverdueHtml(EopSite site) {
    try {
      String themeFld = EopSetting.EOP_PATH + "/user/1/1" + EopSetting.THEMES_STORAGE_PATH + "/default/";

      Configuration cfg = FreeMarkerUtil.getFolderCfg(themeFld);
      Template temp = cfg.getTemplate("overdue.html");
      ByteOutputStream stream = new ByteOutputStream();

      Writer out = new OutputStreamWriter(stream);
      Map map = new HashMap();
      map.put("site", site);
      temp.process(map, out);

      out.flush();
      return stream.toString();
    }
    catch (Exception e)
    {
      return "挂件解析出错" + e.getMessage();
    }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.FacadePagePaser
 * JD-Core Version:    0.6.1
 */