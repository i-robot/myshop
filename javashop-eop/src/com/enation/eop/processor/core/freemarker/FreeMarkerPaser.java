package com.enation.eop.processor.core.freemarker;

import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.utils.EopUtil;
import com.enation.eop.sdk.utils.FreeMarkerUtil;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class FreeMarkerPaser
{
  private static final Log log = LogFactory.getLog(FreeMarkerPaser.class);
  private static ThreadLocal<FreeMarkerPaser> managerLocal = new ThreadLocal();
  private Class clazz;
  private boolean wrapPath = true;
  private static Configuration cfg;
  private Map<String, Object> data;
  private String pathPrefix;
  private String pageName;
  private String pageExt;
  private String pageFolder;

  public FreeMarkerPaser()
  {
    this.data = new HashMap();
    this.clazz = null;
    this.pageFolder = null;
  }

  public void setClz(Class clz) {
    this.clazz = clz;
  }

  public static final FreeMarkerPaser getInstance()
  {
    if (managerLocal.get() == null) {
      throw new RuntimeException("freemarker paser is null");
    }
    FreeMarkerPaser fmp = (FreeMarkerPaser)managerLocal.get();

    fmp.setPageFolder(null);
    fmp.setWrapPath(true);

    return fmp;
  }

  public static final FreeMarkerPaser getCurrInstance() {
    if (managerLocal.get() == null) {
      throw new RuntimeException("freemarker paser is null");
    }
    FreeMarkerPaser fmp = (FreeMarkerPaser)managerLocal.get();

    return fmp;
  }

  public static final void set(FreeMarkerPaser fp) {
    managerLocal.set(fp);
  }
  public static final void remove() {
    managerLocal.remove();
  }

  public FreeMarkerPaser(Class clz)
  {
    this.clazz = clz;
    this.data = new HashMap();
  }

  public FreeMarkerPaser(Class clz, String folder) {
    this.clazz = clz;
    this.pageFolder = folder;
    this.data = new HashMap();
  }

  public void putData(String key, Object value)
  {
    if ((key != null) && (value != null))
      this.data.put(key, value);
  }

  public void putData(Map map) {
    if (map != null)
      this.data.putAll(map);
  }

  public Object getData(String key) {
    if (key == null) return null;

    return this.data.get(key);
  }

  public void setWrapPath(boolean wp)
  {
    this.wrapPath = wp;
  }

  public String proessPageContent()
  {
    try
    {
      String name = this.clazz.getSimpleName();

      int pos = name.indexOf("$$EnhancerByCGLIB$$");
      if (pos > 0) {
        name = name.substring(0, pos);
      }

      this.pageExt = (this.pageExt == null ? ".html" : this.pageExt);
      name = this.pageName == null ? name : this.pageName;

      cfg = getCfg();
      cfg.setNumberFormat("0.##");
      Template temp = cfg.getTemplate(name + this.pageExt);
      ByteOutputStream stream = new ByteOutputStream();
      Writer out = new OutputStreamWriter(stream);
      temp.process(this.data, out);
      out.flush();
      String content = stream.toString();
      if (this.wrapPath)
      {
        content = EopUtil.wrapjavascript(content, getResPath());
      }return EopUtil.wrapcss(content, getResPath());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    } catch (TemplateException e) {
      e.printStackTrace();
    }

    return "widget  processor error";
  }

  private Configuration getCfg()
  {
    if (cfg == null) {
      cfg = FreeMarkerUtil.getCfg();
    }

    this.pathPrefix = (this.pathPrefix == null ? "" : this.pathPrefix);

    if (this.pageFolder == null)
    {
      cfg.setClassForTemplateLoading(this.clazz, this.pathPrefix);
    }
    else
    {
      cfg.setServletContextForTemplateLoading(ThreadContextHolder.getHttpRequest().getSession().getServletContext(), this.pageFolder);
    }
    cfg.setObjectWrapper(new DefaultObjectWrapper());
    cfg.setDefaultEncoding("UTF-8");
    cfg.setLocale(Locale.CHINA);
    cfg.setEncoding(Locale.CHINA, "UTF-8");
    return cfg;
  }

  public void setPathPrefix(String path)
  {
    this.pathPrefix = path;
  }

  public void setPageName(String pageName)
  {
    this.pageName = pageName;
  }

  public void setPageExt(String pageExt)
  {
    this.pageExt = pageExt;
  }

  public void setPageFolder(String pageFolder) {
    this.pageFolder = pageFolder;
  }

  private String getResPath()
  {
    String ctx = EopSetting.CONTEXT_PATH;
    ctx = ctx.equals("/") ? "" : ctx;
    if (this.pageFolder == null) {
      return ctx + "/resource/" + this.clazz.getPackage().getName().replaceAll("\\.", "/") + "/";
    }
    return ctx + this.pageFolder + "/";
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.core.freemarker.FreeMarkerPaser
 * JD-Core Version:    0.6.1
 */