package com.enation.eop.processor.facade.support;

import com.enation.eop.processor.IPagePaser;
import com.enation.eop.processor.PageWrapper;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.resource.impl.ResourceBuilder;
import com.enation.framework.util.StringUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.mozilla.javascript.EvaluatorException;

public class HeaderResourcePageWrapper extends PageWrapper
{
  public static String THE_SSO_SCRIPT = "";

  public HeaderResourcePageWrapper(IPagePaser paser)
  {
    super(paser);
  }

  public String pase(String url)
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String content = super.pase(url);
    String pageid = (String)request.getAttribute("pageid");
    String tplFileName = (String)request.getAttribute("tplFileName");

    if ((StringUtil.isEmpty(pageid)) || (StringUtil.isEmpty(tplFileName))) {
      return content;
    }

    try
    {
      ResourceBuilder.reCreate(pageid, tplFileName);
    } catch (EvaluatorException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    StringBuffer headerhtml = new StringBuffer();
    EopContext ectx = EopContext.getContext();
    EopSite site = ectx.getCurrentSite();

    String resdomain = ectx.getResDomain();

    String scriptpath = "";
    String csspath = "";

    if (!EopSetting.DEVELOPMENT_MODEL) {
      if ("2".equals(EopSetting.RESOURCEMODE))
      {
        resdomain = resdomain + "/themes/" + site.getThemepath();
        scriptpath = resdomain + "/js/headerresource?type=javascript&id=" + pageid;
        csspath = resdomain + "/css/headerresource?type=css&id=" + pageid;
        headerhtml.append("<script src=\"" + scriptpath + "\" type=\"text/javascript\"></script>");
        headerhtml.append("<link href=\"" + csspath + "\" rel=\"stylesheet\" type=\"text/css\" />");
      }
      else
      {
        if (ResourceBuilder.haveCommonScript()) {
          String commonjs = resdomain + "/js/" + site.getThemepath() + "_common.js";
          headerhtml.append("<script src=\"" + commonjs + "\" type=\"text/javascript\"></script>");
        }

        if (ResourceBuilder.haveCommonCss()) {
          String commoncss = resdomain + "/css/" + site.getThemepath() + "_common.css";
          headerhtml.append("<link href=\"" + commoncss + "\" rel=\"stylesheet\" type=\"text/css\" />");
        }

        if (ResourceBuilder.haveScript()) {
          scriptpath = resdomain + "/js/" + site.getThemepath() + "_" + pageid + ".js";
          headerhtml.append("<script src=\"" + scriptpath + "\" type=\"text/javascript\"></script>");
        }

        if (ResourceBuilder.haveCss()) {
          csspath = resdomain + "/css/" + site.getThemepath() + "_" + tplFileName + ".css";
          headerhtml.append("<link href=\"" + csspath + "\" rel=\"stylesheet\" type=\"text/css\" />");
        }

      }

    }

    headerhtml.append(ResourceBuilder.getNotMergeResource());

    content = content.replaceAll("#headerscript#", headerhtml.toString());

    if ("y".equals(request.getParameter("cpr"))) {
      content = content + THE_SSO_SCRIPT;
    }

    return content;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.HeaderResourcePageWrapper
 * JD-Core Version:    0.6.1
 */