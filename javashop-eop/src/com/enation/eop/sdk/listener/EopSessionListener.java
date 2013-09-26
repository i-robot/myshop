package com.enation.eop.sdk.listener;

import com.enation.eop.resource.IAppManager;
import com.enation.eop.resource.model.EopApp;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.IApp;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.context.spring.SpringContextHolder;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

public class EopSessionListener
  implements HttpSessionListener
{
  protected final Logger logger = Logger.getLogger(getClass());

  public void sessionCreated(HttpSessionEvent se)
  {
  }

  public void sessionDestroyed(HttpSessionEvent se)
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("session destroyed..");
    EopSite site;
    String sessionid;
    if ("YES".equals(EopSetting.INSTALL_LOCK.toUpperCase()))
    {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("installed...");
      }

      site = (EopSite)se.getSession().getAttribute("site_key");
      sessionid = se.getSession().getId();
      IAppManager appManager = (IAppManager)SpringContextHolder.getBean("appManager");
      List<EopApp> appList = appManager.list();
      for (EopApp eopApp : appList)
      {
        String appid = eopApp.getAppid();

        if (this.logger.isDebugEnabled()) {
          this.logger.debug("call app[" + appid + "] destory...");
        }

        IApp app = (IApp)SpringContextHolder.getBean(appid);
        app.sessionDestroyed(sessionid, site);
      }
    }
    else if (this.logger.isDebugEnabled()) {
      this.logger.debug("not installed...");
    }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.listener.EopSessionListener
 * JD-Core Version:    0.6.1
 */