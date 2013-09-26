package com.enation.app.base.core.service;

import com.enation.app.base.core.model.SiteMapUrl;

public abstract interface ISitemapManager
{
  public abstract String getsitemap();

  public abstract void addUrl(SiteMapUrl paramSiteMapUrl);

  public abstract void editUrl(String paramString, Long paramLong);

  public abstract int delete(String paramString);

  public abstract void clean();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.ISitemapManager
 * JD-Core Version:    0.6.1
 */