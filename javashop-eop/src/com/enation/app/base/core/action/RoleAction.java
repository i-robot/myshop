package com.enation.app.base.core.action;

import com.enation.app.base.core.model.Role;
import com.enation.app.base.core.service.auth.IAuthActionManager;
import com.enation.app.base.core.service.auth.IRoleManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class RoleAction extends WWAction
{
  private IRoleManager roleManager;
  private IAuthActionManager authActionManager;
  private List roleList;
  private List authList;
  private int roleid;
  private Role role;
  private int[] acts;
  private int isEdit;

  public String list()
  {
    this.roleList = this.roleManager.list();
    return "list";
  }

  public String add()
  {
    this.authList = this.authActionManager.list();
    return "input";
  }

  public String edit()
  {
    this.authList = this.authActionManager.list();
    this.isEdit = 1;
    this.role = this.roleManager.get(this.roleid);
    return "input";
  }

  public String saveAdd()
  {
    this.roleManager.add(this.role, this.acts);
    this.msgs.add("角色添加成功");
    this.urls.put("角色列表", "role!list.do");

    return "message";
  }

  public String saveEdit()
  {
    this.roleManager.edit(this.role, this.acts);
    this.msgs.add("角色修改成功");
    this.urls.put("角色列表", "role!list.do");
    return "message";
  }

  public String delete()
  {
    this.roleManager.delete(this.roleid);
    this.msgs.add("角色删除成功");
    this.urls.put("角色列表", "role!list.do");
    return "message";
  }

  public IRoleManager getRoleManager()
  {
    return this.roleManager;
  }
  public void setRoleManager(IRoleManager roleManager) {
    this.roleManager = roleManager;
  }

  public IAuthActionManager getAuthActionManager()
  {
    return this.authActionManager;
  }

  public void setAuthActionManager(IAuthActionManager authActionManager)
  {
    this.authActionManager = authActionManager;
  }

  public List getRoleList()
  {
    return this.roleList;
  }

  public void setRoleList(List roleList)
  {
    this.roleList = roleList;
  }

  public List getAuthList()
  {
    return this.authList;
  }

  public void setAuthList(List authList)
  {
    this.authList = authList;
  }

  public int getRoleid()
  {
    return this.roleid;
  }

  public void setRoleid(int roleid)
  {
    this.roleid = roleid;
  }

  public Role getRole()
  {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public int[] getActs() {
    return this.acts;
  }
  public void setActs(int[] acts) {
    this.acts = acts;
  }

  public int getIsEdit() {
    return this.isEdit;
  }

  public void setIsEdit(int isEdit) {
    this.isEdit = isEdit;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.RoleAction
 * JD-Core Version:    0.6.1
 */