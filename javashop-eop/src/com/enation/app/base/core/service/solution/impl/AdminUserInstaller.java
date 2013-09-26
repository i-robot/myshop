package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.auth.IAdminUserManager;
import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.AdminUser;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopUser;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.IDaoSupport;
import org.w3c.dom.Node;

public class AdminUserInstaller
  implements IInstaller
{
  private IUserManager userManager;
  private IAdminUserManager adminUserManager;
  private IDaoSupport daoSupport;

  public void install(String productId, Node fragment)
  {
    if ("base".equals(productId)) {
      EopUser user = this.userManager.getCurrentUser();
      EopSite site = EopContext.getContext().getCurrentSite();
      int userid = site.getUserid().intValue();
      int siteid = site.getId().intValue();
      if (user != null)
      {
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(user.getUsername());
        adminUser.setPassword(user.getPassword());
        adminUser.setFounder(1);
        int adminUserId = this.adminUserManager.add(site.getUserid().intValue(), siteid, adminUser).intValue();

        if (EopSetting.RUNMODE.equals("2")) {
          this.daoSupport.execute("update es_adminuser_" + userid + "_" + siteid + " set password=? where userid=?", new Object[] { user.getPassword(), Integer.valueOf(adminUserId) });
        }
        else
        {
          this.daoSupport.execute("update es_adminuser  set password=? where userid=?", new Object[] { user.getPassword(), Integer.valueOf(adminUserId) });
        }

      }
      else
      {
        AdminUser adminUser = this.adminUserManager.getCurrentUser();
        String tablename = "es_adminuser";
        if (EopSetting.RUNMODE.equals("2")) {
          tablename = tablename + "_" + userid + "_" + siteid;
        }
        this.daoSupport.insert(tablename, adminUser);
        Integer adminuserid = adminUser.getUserid();

        if (EopSetting.RUNMODE.equals("2")) {
          this.daoSupport.execute("update es_adminuser_" + userid + "_" + siteid + " set password=? where userid=?", new Object[] { adminUser.getPassword(), adminuserid });
        }
        else
        {
          this.daoSupport.execute("update es_adminuser  set password=? where userid=?", new Object[] { adminUser.getPassword(), Integer.valueOf(userid) });
        }

      }

      AdminUser orderUser = new AdminUser();
      orderUser.setUsername("order");
      orderUser.setPassword("order");
      orderUser.setState(1);
      orderUser.setRoleids(new int[] { 2 });
      this.adminUserManager.add(orderUser);

      AdminUser devUser = new AdminUser();
      devUser.setUsername("developer");
      devUser.setPassword("developer");
      devUser.setState(1);
      devUser.setRoleids(new int[] { 3 });
      this.adminUserManager.add(devUser);
    }
  }

  public IUserManager getUserManager()
  {
    return this.userManager;
  }

  public void setUserManager(IUserManager userManager) {
    this.userManager = userManager;
  }

  public IAdminUserManager getAdminUserManager() {
    return this.adminUserManager;
  }

  public void setAdminUserManager(IAdminUserManager adminUserManager) {
    this.adminUserManager = adminUserManager;
  }

  public IDaoSupport getDaoSupport() {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport daoSupport) {
    this.daoSupport = daoSupport;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.AdminUserInstaller
 * JD-Core Version:    0.6.1
 */