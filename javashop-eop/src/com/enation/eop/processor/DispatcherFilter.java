package com.enation.eop.processor;

import com.enation.eop.processor.core.Response;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.eop.processor.httpcache.HttpCacheManager;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopContextIniter;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.context.SaasEopContextIniter;
import com.enation.eop.sdk.utils.FreeMarkerUtil;
import com.enation.eop.sdk.utils.JspUtil;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javazoom.upload.UploadException;
import org.apache.commons.io.IOUtils;

public class DispatcherFilter
  implements Filter
{
  public void init(FilterConfig config)
  {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    HttpServletResponse httpResponse = (HttpServletResponse)response;
    String uri = httpRequest.getServletPath();
    try
    {
      if (uri.startsWith("/statics")) {
        chain.doFilter(httpRequest, httpResponse);
      }
      else if ((!uri.startsWith("/install")) && (EopSetting.INSTALL_LOCK.toUpperCase().equals("NO"))) {
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/install");
      }
      else if ((!uri.startsWith("/install.html")) && (uri.startsWith("/install")) && (!uri.startsWith("/install/images")) && (EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")))
      {
        httpResponse.getWriter().write("如要重新安装，请先删除/install/install.lock文件，并重起web容器");
      }
      else
      {
        if ("2".equals(EopSetting.RUNMODE))
          SaasEopContextIniter.init(httpRequest, httpResponse);
        else {
          EopContextIniter.init(httpRequest, httpResponse);
        }

        Processor loginprocessor = (Processor)SpringContextHolder.getBean("autoLoginProcessor");
        if (loginprocessor != null) {
          loginprocessor.process(1, httpResponse, httpRequest);
        }

        Processor processor = ProcessorFactory.newProcessorInstance(uri, httpRequest);

        if (processor == null) {
          chain.doFilter(request, response);
        }
        else
        {
          if ((uri.equals("/")) || (uri.endsWith(".html"))) {
            boolean result = HttpCacheManager.getIsCached(uri);
            if (result) return;
          }
          Response eopResponse = processor.process(0, httpResponse, httpRequest);

          InputStream in = eopResponse.getInputStream();

          if (in != null)
          {
            byte[] inbytes = IOUtils.toByteArray(in);
            httpResponse.setContentType(eopResponse.getContentType());
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setHeader("Content-Length", "" + inbytes.length);

            OutputStream output = httpResponse.getOutputStream();
            IOUtils.write(inbytes, output);
          } else {
            chain.doFilter(request, response);
          }
        }
      }
    }
    catch (RuntimeException exception)
    {
      exception.printStackTrace();
      response.setContentType("text/html; charset=UTF-8");
      request.setAttribute("message", exception.getMessage());
      String content = JspUtil.getJspOutput("/commons/error.jsp", httpRequest, httpResponse);

      response.getWriter().print(content);
    }
    finally
    {
      ThreadContextHolder.remove();
      FreeMarkerPaser.remove();
      EopContext.remove();
    }
  }

  public void destroy()
  {
  }

  private HttpServletRequest wrapRequest(HttpServletRequest request, String url)
    throws UploadException, IOException
  {
    List<String> safeUrlList = EopSetting.safeUrlList;
    for (String safeUrl : safeUrlList) {
      if (safeUrl.equals(url)) {
        return request;
      }
    }
    return new SafeHttpRequestWrapper(request);
  }

  public String get404Html(String url) {
    String themeFld = EopSetting.EOP_PATH + "/themes/";
    Map data = new HashMap();
    data.put("url", url);
    try
    {
      Configuration cfg = FreeMarkerUtil.getFolderCfg(themeFld);
      Template temp = cfg.getTemplate("404.html");
      ByteOutputStream stream = new ByteOutputStream();

      Writer out = new OutputStreamWriter(stream);
      temp.process(data, out);

      out.flush();
      return stream.toString();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }return "";
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.DispatcherFilter
 * JD-Core Version:    0.6.1
 */