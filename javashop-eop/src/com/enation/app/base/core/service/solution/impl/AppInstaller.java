package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.eop.resource.IAppManager;
import com.enation.eop.resource.model.EopApp;
import com.enation.eop.sdk.IApp;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.database.IDaoSupport;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AppInstaller
  implements IInstaller
{
  protected final Logger logger = Logger.getLogger(getClass());
  private IAppManager appManager;
  private IDaoSupport daoSupport;

  private void saasInstall(IApp app)
  {
    app.saasInstall();
  }

  private void install(IApp app, Element el)
  {
    app.install();

    EopApp eopApp = new EopApp();
    eopApp.setApp_name(app.getName());
    eopApp.setAppid(app.getId());
    eopApp.setPath(app.getNameSpace());
    eopApp.setDeployment(0);
    eopApp.setDescript(app.getName());

    eopApp.setVersion(el.getAttribute("version"));
    this.appManager.add(eopApp);
  }

  public void install(String productId, Node fragment)
  {
    if (!EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")) {
      this.daoSupport.execute("truncate table eop_app", new Object[0]);
    }

    NodeList nodeList = fragment.getChildNodes();
    int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == 1) {
        Element el = (Element)node;
        String appid = el.getAttribute("id");

        if (this.logger.isDebugEnabled()) {
          this.logger.debug("安装应用[" + appid + "]...");
        }

        IApp app = (IApp)SpringContextHolder.getBean(appid);
        if (app != null)
        {
          if (!EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")) {
            install(app, el);
          }

          if ("2".equals(EopSetting.RUNMODE)) {
            saasInstall(app);
          }
        }

        if (this.logger.isDebugEnabled())
          this.logger.debug("安装应用[" + appid + "]完成.");
      }
    }
  }

  public IAppManager getAppManager() {
    return this.appManager;
  }
  public void setAppManager(IAppManager appManager) {
    this.appManager = appManager;
  }
  public IDaoSupport getDaoSupport() {
    return this.daoSupport;
  }
  public void setDaoSupport(IDaoSupport daoSupport) {
    this.daoSupport = daoSupport;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.AppInstaller
 * JD-Core Version:    0.6.1
 */