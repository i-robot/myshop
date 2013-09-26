package com.enation.app.base.core.action;

import com.enation.app.base.core.plugin.user.AdminUserPluginBundle;
import com.enation.app.base.core.service.auth.IAdminUserManager;
import com.enation.app.base.core.service.auth.IPermissionManager;
import com.enation.app.base.core.service.auth.IRoleManager;
import com.enation.eop.resource.model.AdminUser;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class UserAdminAction extends WWAction
{
  private AdminUserPluginBundle adminUserPluginBundle;
  private IAdminUserManager adminUserManager;
  private IRoleManager roleManager;
  private IPermissionManager permissionManager;
  private AdminUser adminUser;
  private Integer id;
  private List roleList;
  private List userRoles;
  private int[] roleids;
  private List userList;
  private String newPassword;
  private String updatePwd;
  private int multiSite;
  private List<String> htmlList;

  public String execute()
  {
    this.userList = this.adminUserManager.list();
    return "success";
  }

  public String add() throws Exception
  {
    this.multiSite = EopContext.getContext().getCurrentSite().getMulti_site().intValue();
    this.roleList = this.roleManager.list();
    this.htmlList = this.adminUserPluginBundle.getInputHtml(null);
    return "add";
  }

  public String addSave() throws Exception {
    try {
      this.adminUser.setRoleids(this.roleids);
      this.adminUserManager.add(this.adminUser);
      this.msgs.add("新增管理员成功");
      this.urls.put("管理员列表", "userAdmin.do");
    } catch (RuntimeException e) {
      this.msgs.add(e.getMessage());
    }
    return "message";
  }

  public String edit() throws Exception {
    this.multiSite = EopContext.getContext().getCurrentSite().getMulti_site().intValue();
    this.roleList = this.roleManager.list();
    this.userRoles = this.permissionManager.getUserRoles(this.id.intValue());
    this.adminUser = this.adminUserManager.get(this.id);
    this.htmlList = this.adminUserPluginBundle.getInputHtml(this.adminUser);
    return "edit";
  }

  public String editSave() throws Exception {
    try {
      if (this.updatePwd != null) {
        this.adminUser.setPassword(this.newPassword);
      }
      this.adminUser.setRoleids(this.roleids);
      this.adminUserManager.edit(this.adminUser);
      this.msgs.add("修改管理员成功");
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.logger.error(e, e.fillInStackTrace());
      this.msgs.add("管理员修改失败:" + e.getMessage());
    }
    this.urls.put("管理员列表", "userAdmin.do");

    return "message";
  }

  public String delete() throws Exception
  {
    try {
      this.adminUserManager.delete(this.id);
      this.msgs.add("管理员删除成功");
      this.urls.put("管理员列表", "userAdmin.do");
    } catch (RuntimeException e) {
      this.msgs.add("管理员删除失败:" + e.getMessage());
      this.urls.put("管理员列表", "userAdmin.do");
    }

    return "message";
  }

  public String editPassword() throws Exception {
    return "editPassword";
  }

  public IAdminUserManager getAdminUserManager()
  {
    return this.adminUserManager;
  }

  public void setAdminUserManager(IAdminUserManager adminUserManager) {
    this.adminUserManager = adminUserManager;
  }

  public IRoleManager getRoleManager() {
    return this.roleManager;
  }

  public void setRoleManager(IRoleManager roleManager) {
    this.roleManager = roleManager;
  }

  public IPermissionManager getPermissionManager() {
    return this.permissionManager;
  }

  public void setPermissionManager(IPermissionManager permissionManager) {
    this.permissionManager = permissionManager;
  }

  public AdminUser getAdminUser() {
    return this.adminUser;
  }

  public void setAdminUser(AdminUser adminUser) {
    this.adminUser = adminUser;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List getRoleList() {
    return this.roleList;
  }

  public void setRoleList(List roleList) {
    this.roleList = roleList;
  }

  public List getUserRoles() {
    return this.userRoles;
  }

  public void setUserRoles(List userRoles) {
    this.userRoles = userRoles;
  }

  public int[] getRoleids() {
    return this.roleids;
  }

  public void setRoleids(int[] roleids) {
    this.roleids = roleids;
  }

  public List getUserList() {
    return this.userList;
  }

  public void setUserList(List userList) {
    this.userList = userList;
  }

  public String getNewPassword() {
    return this.newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getUpdatePwd() {
    return this.updatePwd;
  }

  public void setUpdatePwd(String updatePwd) {
    this.updatePwd = updatePwd;
  }

  public int getMultiSite() {
    return this.multiSite;
  }

  public void setMultiSite(int multiSite) {
    this.multiSite = multiSite;
  }

  public AdminUserPluginBundle getAdminUserPluginBundle() {
    return this.adminUserPluginBundle;
  }

  public void setAdminUserPluginBundle(AdminUserPluginBundle adminUserPluginBundle) {
    this.adminUserPluginBundle = adminUserPluginBundle;
  }

  public List<String> getHtmlList() {
    return this.htmlList;
  }

  public void setHtmlList(List<String> htmlList) {
    this.htmlList = htmlList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.UserAdminAction
 * JD-Core Version:    0.6.1
 */