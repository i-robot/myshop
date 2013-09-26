package com.enation.app.base.core.model;

import java.io.Serializable;

public class SiteMapUrl
  implements Serializable
{
  private String loc;
  private Long lastmod;
  private String changefreq;
  private String priority;

  public String getLoc()
  {
    return this.loc;
  }

  public void setLoc(String loc) {
    this.loc = loc;
  }

  public Long getLastmod() {
    return this.lastmod;
  }

  public void setLastmod(Long lastmod) {
    this.lastmod = lastmod;
  }

  public String getChangefreq() {
    return this.changefreq;
  }

  public void setChangefreq(String changefreq) {
    this.changefreq = changefreq;
  }

  public String getPriority() {
    return this.priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.SiteMapUrl
 * JD-Core Version:    0.6.1
 */