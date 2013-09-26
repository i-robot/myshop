package com.enation.app.base.core.service.auth.impl;

import com.enation.app.base.core.model.MultiSite;
import com.enation.app.base.core.plugin.user.AdminUserPluginBundle;
import com.enation.app.base.core.service.auth.IAdminUserManager;
import com.enation.app.base.core.service.auth.IPermissionManager;
import com.enation.eop.resource.model.AdminUser;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.UserContext;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AdminUserManagerImpl extends BaseSupport<AdminUser>
  implements IAdminUserManager
{
  private AdminUserPluginBundle adminUserPluginBundle;
  private IPermissionManager permissionManager;

  public void clean()
  {
    this.baseDaoSupport.execute("truncate table adminuser", new Object[0]);
  }
  @Transactional(propagation=Propagation.REQUIRED)
  public Integer add(AdminUser adminUser) {
    adminUser.setPassword(StringUtil.md5(adminUser.getPassword()));

    this.baseDaoSupport.insert("adminuser", adminUser);
    int userid = this.baseDaoSupport.getLastId("adminuser");

    this.permissionManager.giveRolesToUser(userid, adminUser.getRoleids());
    this.adminUserPluginBundle.onAdd(Integer.valueOf(userid));
    return Integer.valueOf(userid);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public Integer add(int userid, int siteid, AdminUser adminUser)
  {
    adminUser.setState(1);
    this.baseDaoSupport.insert("adminuser", adminUser);
    return Integer.valueOf(this.baseDaoSupport.getLastId("adminuser"));
  }

  public int checkLast() {
    int count = this.baseDaoSupport.queryForInt("select count(0) from adminuser", new Object[0]);
    return count;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer id)
  {
    if (checkLast() == 1) {
      throw new RuntimeException("必须最少保留一个管理员");
    }

    this.permissionManager.cleanUserRoles(id.intValue());

    this.baseDaoSupport.execute("delete from adminuser where userid=?", new Object[] { id });
    this.adminUserPluginBundle.onDelete(id);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void edit(AdminUser adminUser)
  {
    this.permissionManager.giveRolesToUser(adminUser.getUserid().intValue(), adminUser.getRoleids());

    if (!StringUtil.isEmpty(adminUser.getPassword()))
      adminUser.setPassword(StringUtil.md5(adminUser.getPassword()));
    int userId = adminUser.getUserid().intValue();
    adminUser.setUserid(null);
    this.baseDaoSupport.update("adminuser", adminUser, "userid=" + userId);
    this.adminUserPluginBundle.onEdit(Integer.valueOf(userId));
  }

  public AdminUser get(Integer id) {
    return (AdminUser)this.baseDaoSupport.queryForObject("select * from adminuser where userid=?", AdminUser.class, new Object[] { id });
  }

  public List list() {
    return this.baseDaoSupport.queryForList("select * from adminuser order by dateline", AdminUser.class, new Object[0]);
  }

  public List<Map> list(Integer userid, Integer siteid)
  {
    String sql = "select * from es_adminuser_" + userid + "_" + siteid;
    return this.daoSupport.queryForList(sql, new Object[0]);
  }

  public List<AdminUser> listByRoleId(int roleid)
  {
    String sql = "select u.* from " + getTableName("adminuser") + " u ," + getTableName("user_role") + " ur where ur.userid=u.userid and ur.roleid=?";
    return this.daoSupport.queryForList(sql, AdminUser.class, new Object[] { Integer.valueOf(roleid) });
  }

  public int login(String username, String password)
  {
    return loginBySys(username, StringUtil.md5(password));
  }

  public int loginBySys(String username, String password)
  {
    String sql = "select * from adminuser where username=?";
    List userList = this.baseDaoSupport.queryForList(sql, AdminUser.class, new Object[] { username });
    if ((userList == null) || (userList.size() == 0)) {
      throw new RuntimeException("此用户不存在");
    }
    AdminUser user = (AdminUser)userList.get(0);

    if (!password.equals(user.getPassword())) {
      throw new RuntimeException("密码错误");
    }

    if (user.getState() == 0) {
      throw new RuntimeException("此用户已经被禁用");
    }

    EopSite site = EopContext.getContext().getCurrentSite();

    if ((site.getMulti_site().intValue() == 1) && 
      (user.getFounder() != 1)) {
      MultiSite childSite = EopContext.getContext().getCurrentChildSite();
      if ((user.getSiteid() == null) || (childSite.getSiteid() != user.getSiteid())) {
        throw new RuntimeException("非此站点管理员");
      }

    }

    List authList = this.permissionManager.getUesrAct(user.getUserid().intValue());
    user.setAuthList(authList);

    int logincount = site.getLogincount();
    long lastlogin = site.getLastlogin().longValue() * 1000L;
    Date today = new Date();
    if (DateUtil.toString(new Date(lastlogin), "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM")))
      logincount++;
    else {
      logincount = 1;
    }

    site.setLogincount(logincount);
    site.setLastlogin(Long.valueOf(DateUtil.getDatelineLong()));
    this.daoSupport.execute("update eop_site set logincount=?, lastlogin=? where id=?", new Object[] { Integer.valueOf(logincount), site.getLastlogin(), site.getId() });

    WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();

    UserContext userContext = new UserContext(site.getUserid(), site.getId(), user.getUserid());

    sessonContext.setAttribute("usercontext", userContext);
    sessonContext.setAttribute("admin_user_key", user);
    this.adminUserPluginBundle.onLogin(user);
    return user.getUserid().intValue();
  }

  public AdminUser getCurrentUser()
  {
    WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();

    return (AdminUser)sessonContext.getAttribute("admin_user_key");
  }

  public IPermissionManager getPermissionManager()
  {
    return this.permissionManager;
  }

  public void setPermissionManager(IPermissionManager permissionManager) {
    this.permissionManager = permissionManager;
  }
  public AdminUserPluginBundle getAdminUserPluginBundle() {
    return this.adminUserPluginBundle;
  }
  public void setAdminUserPluginBundle(AdminUserPluginBundle adminUserPluginBundle) {
    this.adminUserPluginBundle = adminUserPluginBundle;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.auth.impl.AdminUserManagerImpl
 * JD-Core Version:    0.6.1
 */