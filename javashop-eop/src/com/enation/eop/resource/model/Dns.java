package com.enation.eop.resource.model;

import java.io.Serializable;

public class Dns
  implements Serializable
{
  private static final long serialVersionUID = 7525130004L;
  private String domain;
  private EopSite site;

  public String getDomain()
  {
    return this.domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
  }
  public EopSite getSite() {
    return this.site;
  }
  public void setSite(EopSite site) {
    this.site = site;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.Dns
 * JD-Core Version:    0.6.1
 */