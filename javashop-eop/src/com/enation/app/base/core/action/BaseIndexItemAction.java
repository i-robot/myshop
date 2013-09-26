package com.enation.app.base.core.action;

import com.enation.app.base.core.service.IAccessRecorder;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.action.WWAction;
import java.util.Map;

public class BaseIndexItemAction extends WWAction
{
  private ISiteManager siteManager;
  private IAccessRecorder accessRecorder;
  private Map access;
  private EopSite site;
  private int canget;

  public String base()
  {
    this.site = EopContext.getContext().getCurrentSite();
    return "base";
  }

  public String access() {
    this.access = this.accessRecorder.census();
    return "access";
  }

  public String point()
  {
    this.site = EopContext.getContext().getCurrentSite();
    EopSite site = EopContext.getContext().getCurrentSite();
    long lastgetpoint = site.getLastgetpoint();
    long now = System.currentTimeMillis() / 1000L;
    int onemonth = 2592000;
    if (now - lastgetpoint > onemonth)
      this.canget = 1;
    else {
      this.canget = 0;
    }
    return "point";
  }

  public ISiteManager getSiteManager()
  {
    return this.siteManager;
  }
  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }
  public EopSite getSite() {
    return this.site;
  }
  public void setSite(EopSite site) {
    this.site = site;
  }

  public IAccessRecorder getAccessRecorder() {
    return this.accessRecorder;
  }

  public void setAccessRecorder(IAccessRecorder accessRecorder) {
    this.accessRecorder = accessRecorder;
  }

  public Map getAccess() {
    return this.access;
  }

  public void setAccess(Map access) {
    this.access = access;
  }

  public int getCanget() {
    return this.canget;
  }

  public void setCanget(int canget) {
    this.canget = canget;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.BaseIndexItemAction
 * JD-Core Version:    0.6.1
 */