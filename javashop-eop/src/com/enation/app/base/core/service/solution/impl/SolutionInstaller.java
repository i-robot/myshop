package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
import com.enation.app.base.core.service.dbsolution.IDBSolution;
import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.app.base.core.service.solution.IProfileLoader;
import com.enation.app.base.core.service.solution.ISetupLoader;
import com.enation.app.base.core.service.solution.ISolutionInstaller;
import com.enation.app.base.core.service.solution.InstallerFactory;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.model.EopProduct;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.FileUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.NodeList;

public class SolutionInstaller
  implements ISolutionInstaller
{
  protected final Logger logger = Logger.getLogger(getClass());
  private IDaoSupport<EopProduct> daoSupport;
  private ISiteManager siteManager;
  private IProfileLoader profileLoader;
  private InstallerFactory installerFactory;
  private ISetupLoader setupLoader;

  @Transactional(propagation=Propagation.REQUIRED)
  public void install(Integer userid, Integer siteid, String productId)
  {
    if ((!productId.toUpperCase().equals("BASE")) && (!productId.startsWith("temp_")))
    {
      this.siteManager.setSiteProduct(userid, siteid, productId);
    }

    String[] types = { "apps", "menus", "adminThemes", "themes", "urls", "widgets", "indexitems", "site" };

    org.w3c.dom.Document proFileDoc = this.profileLoader.load(productId);

    for (String type : types) {
      install(type, proFileDoc, productId);
    }

    org.dom4j.Document setupDoc = this.setupLoader.load(productId);

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("execute setup.xml...");
    }

    String tablenameperfix = "";
    if ("2".equals(EopSetting.RUNMODE)) {
      EopSite site = EopContext.getContext().getCurrentSite();
      tablenameperfix = "_" + site.getUserid() + "_" + site.getId();
    }

    List listRecreate = setupDoc.getRootElement().element("recreate").elements();

    IDBSolution dbsolution = DBSolutionFactory.getDBSolution();
    Connection conn = DBSolutionFactory.getConnection(null);
    dbsolution.setConnection(conn);
    for (Iterator i$ = listRecreate.iterator(); i$.hasNext(); ) { Object o = i$.next();
      Element table = (Element)o;
      dbsolution.dropTable(table.getText() + tablenameperfix); }
    try
    {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    IInstaller installer = (IInstaller)SpringContextHolder.getBean("exampleDataInstaller");

    installer.install(productId, null);

    installer = (IInstaller)SpringContextHolder.getBean("adminUserInstaller");
    installer.install(productId, null);

    install("components", proFileDoc, productId);

    if (EopSetting.RUNMODE.equals("2"))
    {
      if (!"base".equals(productId))
        FileUtil.copyFile(EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/profile.xml", EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/profile.xml");
    }
  }

  private void install(String type, org.w3c.dom.Document proFileDoc, String productId)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("install [" + type + "]");
    }

    NodeList nodeList = proFileDoc.getElementsByTagName(type);
    if ((nodeList == null) || (nodeList.getLength() <= 0)) {
      return;
    }
    if (nodeList != null) {
      IInstaller installer = this.installerFactory.getInstaller(type);
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("user installer [" + installer + "]");
      }
      installer.install(productId, nodeList.item(0));
    }
  }

  public IDaoSupport<EopProduct> getDaoSupport() {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport<EopProduct> daoSupport) {
    this.daoSupport = daoSupport;
  }

  public ISiteManager getSiteManager() {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }

  public IProfileLoader getProfileLoader() {
    return this.profileLoader;
  }

  public void setProfileLoader(IProfileLoader profileLoader) {
    this.profileLoader = profileLoader;
  }

  public InstallerFactory getInstallerFactory() {
    return this.installerFactory;
  }

  public void setInstallerFactory(InstallerFactory installerFactory) {
    this.installerFactory = installerFactory;
  }

  public ISetupLoader getSetupLoader() {
    return this.setupLoader;
  }

  public void setSetupLoader(ISetupLoader setupLoader) {
    this.setupLoader = setupLoader;
  }

  public Logger getLogger() {
    return this.logger;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SolutionInstaller
 * JD-Core Version:    0.6.1
 */