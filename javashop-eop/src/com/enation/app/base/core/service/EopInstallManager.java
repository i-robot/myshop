package com.enation.app.base.core.service;

import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
import com.enation.app.base.core.service.solution.ISolutionInstaller;
import com.enation.eop.resource.IAppManager;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopUser;
import com.enation.eop.sdk.context.EopContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class EopInstallManager
{
  private JdbcTemplate jdbcTemplate;
  private IAppManager appManager;
  private ApplicationContext context;
  private IUserManager userManager;
  private ISiteManager siteManager;
  private ISolutionInstaller solutionInstaller;

  public void install(String username, String password, String domain, String productid)
  {
    EopSite site = new EopSite();
    site.setUserid(Integer.valueOf(1));
    site.setId(Integer.valueOf(1));
    EopContext context = new EopContext();
    context.setCurrentSite(site);
    EopContext.setContext(context);

    installUser(username, password, domain, productid);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void installUser(String username, String password, String domain, String productid)
  {
    DBSolutionFactory.dbImport("file:com/enation/app/base/init.xml", "");

    EopUser eopUser = new EopUser();
    eopUser.setAddress("在这里输入详细地址");
    eopUser.setUsername(username);
    eopUser.setCompanyname("单机版用户");
    eopUser.setLinkman("在这里输入联系人信息");
    eopUser.setTel("010-12345678");
    eopUser.setMobile("13888888888");
    eopUser.setEmail("youmail@domain.com");
    eopUser.setUsertype(Integer.valueOf(1));
    eopUser.setPassword(password);
    Integer userid = this.userManager.createUser(eopUser);
    this.userManager.login(username, password);

    EopSite site = new EopSite();
    site.setSitename("javashop");
    site.setThemeid(Integer.valueOf(1));
    site.setAdminthemeid(Integer.valueOf(1));
    site.setSitename(productid + "新建站点");
    site.setUserid(userid);
    site.setUsername(eopUser.getUsername());
    site.setUsertel(eopUser.getTel());
    site.setUsermobile(eopUser.getMobile());
    site.setUseremail(eopUser.getEmail());

    Integer siteid = this.siteManager.add(site, domain);

    this.solutionInstaller.install(userid, siteid, productid);
    this.solutionInstaller.install(userid, siteid, "base");
  }

  public JdbcTemplate getJdbcTemplate() {
    return this.jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public IAppManager getAppManager() {
    return this.appManager;
  }

  public void setAppManager(IAppManager appManager) {
    this.appManager = appManager;
  }

  public ApplicationContext getContext() {
    return this.context;
  }

  public void setContext(ApplicationContext context) {
    this.context = context;
  }

  public ISolutionInstaller getSolutionInstaller() {
    return this.solutionInstaller;
  }

  public void setSolutionInstaller(ISolutionInstaller solutionInstaller) {
    this.solutionInstaller = solutionInstaller;
  }

  public IUserManager getUserManager() {
    return this.userManager;
  }

  public void setUserManager(IUserManager userManager) {
    this.userManager = userManager;
  }

  public ISiteManager getSiteManager() {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.EopInstallManager
 * JD-Core Version:    0.6.1
 */