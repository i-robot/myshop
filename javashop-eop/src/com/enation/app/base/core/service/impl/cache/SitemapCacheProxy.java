package com.enation.app.base.core.service.impl.cache;

import com.enation.app.base.core.model.SiteMapUrl;
import com.enation.app.base.core.service.ISitemapManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.cache.AbstractCacheProxy;
import com.enation.framework.cache.ICache;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.FileUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class SitemapCacheProxy extends AbstractCacheProxy
  implements ISitemapManager
{
  public static final String CACHE_KEY = "sitemap";

  public SitemapCacheProxy()
  {
    super("sitemap");
  }

  public int delete(String loc) {
    EopSite site = EopContext.getContext().getCurrentSite();
    Document document = (Document)this.cache.get("sitemap_" + site.getUserid() + "_" + site.getId());

    document = document == null ? init() : document;

    List list = list = document.getRootElement().elements();
    for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
      Element urlElement = (Element)o;
      String mloc = urlElement.element("loc").getText();
      if (mloc.equals(loc)) {
        document.getRootElement().remove(urlElement);
        this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);

        return 1;
      }
    }
    return 0;
  }

  public void addUrl(SiteMapUrl url) {
    EopSite site = EopContext.getContext().getCurrentSite();
    Document document = (Document)this.cache.get("sitemap_" + site.getUserid() + "_" + site.getId());

    document = document == null ? init() : document;
    List list = list = document.getRootElement().elements();
    for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
      Element urlElement = (Element)o;
      String mloc = urlElement.element("loc").getText();
      if (mloc.equals(url.getLoc())) {
        return;
      }
    }
    Element urlsetElement = document.getRootElement();
    Element urlElement = urlsetElement.addElement("url");
    Element locElement = urlElement.addElement("loc");
    Element lastmodElement = urlElement.addElement("lastmod");
    Element changefreqElement = urlElement.addElement("changefreq");
    Element priorityElement = urlElement.addElement("priority");
    locElement.setText(url.getLoc());
    lastmodElement.setText(DateUtil.toString(new Date(url.getLastmod().longValue()), "yyyy-MM-dd"));

    changefreqElement.setText("weekly");

    priorityElement.setText("0.5");
    write(document);
    this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);
  }

  public void editUrl(String loc, Long lastmod)
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    Document document = (Document)this.cache.get("sitemap_" + site.getUserid() + "_" + site.getId());

    document = document == null ? init() : document;
    List list = list = document.getRootElement().elements();
    for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
      Element urlElement = (Element)o;
      String mloc = urlElement.element("loc").getText();
      if (mloc.equals(loc)) {
        urlElement.element("lastmod").setText(DateUtil.toString(new Date(lastmod.longValue()), "yyyy-MM-dd"));

        write(document);
        this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);

        break;
      } }
  }

  public String getsitemap()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    Document document = (Document)this.cache.get("sitemap_" + site.getUserid() + "_" + site.getId());

    document = document == null ? init() : document;
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    return document.asXML().replaceAll("<loc>/", "<loc>http://" + request.getServerName() + "/");
  }

  private Document read()
  {
    Document document = null;
    EopSite site = EopContext.getContext().getCurrentSite();
    SAXReader saxReader = new SAXReader();
    try {
      if (FileUtil.exist(EopSetting.EOP_PATH + "/user/" + site.getUserid() + "/" + site.getId() + "/sitemap.xml"))
      {
        document = saxReader.read(new File(EopSetting.EOP_PATH + "/user/" + site.getUserid() + "/" + site.getId() + "/sitemap.xml"));
      }

    }
    catch (DocumentException e)
    {
      System.out.println(e.getMessage());
    }
    return document;
  }

  private void write(Document document)
  {
    String contextPath = EopContext.getContext().getContextPath();
    try
    {
      if (!contextPath.startsWith("/")) {
        contextPath = "/" + contextPath;
      }

      XMLWriter output = new XMLWriter(new FileWriter(new File(EopSetting.EOP_PATH + contextPath + "/sitemap.xml")));

      output.write(document);
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Document init()
  {
    Document document = read();
    if (null == document) {
      String docStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
      docStr = docStr + "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";
      docStr = docStr + "</urlset>";
      try {
        document = DocumentHelper.parseText(docStr);
      } catch (DocumentException e) {
        e.printStackTrace();
      }
      write(document);
    }
    EopSite site = EopContext.getContext().getCurrentSite();
    this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);

    return document;
  }

  public void clean() {
    Document document = null;
    String docStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    docStr = docStr + "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";
    docStr = docStr + "</urlset>";
    try {
      document = DocumentHelper.parseText(docStr);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    write(document);
    EopSite site = EopContext.getContext().getCurrentSite();
    this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.cache.SitemapCacheProxy
 * JD-Core Version:    0.6.1
 */