package com.enation.eop.processor.facade;

import com.enation.app.base.core.service.ISitemapManager;
import com.enation.eop.processor.Processor;
import com.enation.eop.processor.core.Response;
import com.enation.eop.processor.core.StringResponse;
import com.enation.framework.context.spring.SpringContextHolder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SiteMapProcessor
  implements Processor
{
  public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    ISitemapManager siteMapManager = (ISitemapManager)SpringContextHolder.getBean("sitemapManager");
    String siteMapStr = siteMapManager.getsitemap();
    Response response = new StringResponse();
    response.setContent(siteMapStr);
    response.setContentType("text/xml");
    return response;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.SiteMapProcessor
 * JD-Core Version:    0.6.1
 */