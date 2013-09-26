package com.enation.eop.sdk.listener;

import com.enation.eop.resource.ISiteManager;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.component.IComponentManager;
import com.enation.framework.context.spring.SpringContextHolder;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EopContextLoaderListener
  implements ServletContextListener
{
  public void contextDestroyed(ServletContextEvent event)
  {
  }

  public void contextInitialized(ServletContextEvent event)
  {
    if ((EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")) && ("1".equals(EopSetting.RUNMODE))) {
      IComponentManager componentManager = (IComponentManager)SpringContextHolder.getBean("componentManager");
      componentManager.startComponents();
    }

    if (("2".equals(EopSetting.RUNMODE)) && (EopSetting.INSTALL_LOCK.toUpperCase().equals("YES"))) {
      ISiteManager siteManager = (ISiteManager)SpringContextHolder.getBean("siteManager");
      siteManager.getDnsList();
    }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.listener.EopContextLoaderListener
 * JD-Core Version:    0.6.1
 */