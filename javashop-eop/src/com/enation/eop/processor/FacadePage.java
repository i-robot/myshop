package com.enation.eop.processor;

import com.enation.eop.resource.model.EopApp;
import com.enation.eop.resource.model.EopSite;

public class FacadePage
{
  private Integer id;
  private EopSite site;
  private String uri;
  private EopApp app;

  public FacadePage()
  {
  }

  public FacadePage(EopSite site)
  {
    this.site = site;
  }

  public EopSite getSite() {
    return this.site;
  }

  public void setSite(EopSite site) {
    this.site = site;
  }

  public String getUri() {
    return this.uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public EopApp getApp() {
    return this.app;
  }

  public void setApp(EopApp app) {
    this.app = app;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.FacadePage
 * JD-Core Version:    0.6.1
 */