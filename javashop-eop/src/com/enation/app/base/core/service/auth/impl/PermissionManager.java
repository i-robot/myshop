package com.enation.app.base.core.service.auth.impl;

import com.enation.app.base.core.model.AuthAction;
import com.enation.app.base.core.model.Role;
import com.enation.app.base.core.service.auth.IPermissionManager;
import com.enation.eop.resource.model.AdminUser;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.enation.framework.database.IDaoSupport;
import java.util.List;

public class PermissionManager extends BaseSupport
  implements IPermissionManager
{
  public void giveRolesToUser(int userid, int[] roleids)
  {
    this.baseDaoSupport.execute("delete from user_role where userid=?", new Object[] { Integer.valueOf(userid) });
    if (roleids == null) return;
    for (int roleid : roleids)
      this.baseDaoSupport.execute("insert into user_role(roleid,userid)values(?,?)", new Object[] { Integer.valueOf(roleid), Integer.valueOf(userid) });
  }

  public List<AuthAction> getUesrAct(int userid, String acttype)
  {
    String sql = "select * from " + getTableName("auth_action") + " where type=? ";

    sql = sql + " and actid in(select authid from  " + getTableName("role_auth") + " where roleid in ";

    sql = sql + " (select roleid from " + getTableName("user_role") + " where userid=?)";
    sql = sql + " )";

    return this.daoSupport.queryForList(sql, AuthAction.class, new Object[] { acttype, Integer.valueOf(userid) });
  }

  public List<AuthAction> getUesrAct(int userid)
  {
    String sql = "select * from " + getTableName("auth_action") + " where ";

    sql = sql + "   actid in(select authid from  " + getTableName("role_auth") + " where roleid in ";

    sql = sql + " (select roleid from " + getTableName("user_role") + " where userid=?)";
    sql = sql + " )";

    return this.daoSupport.queryForList(sql, AuthAction.class, new Object[] { Integer.valueOf(userid) });
  }

  public boolean checkHaveAuth(int actid)
  {
    WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();

    AdminUser user = (AdminUser)sessonContext.getAttribute("admin_user_key");
    if (user.getFounder() == 1) return true;
    List<AuthAction> authList = user.getAuthList();
    for (AuthAction auth : authList) {
      if (auth.getActid().intValue() == actid) {
        return true;
      }
    }

    return false;
  }

  public List<Role> getUserRoles(int userid)
  {
    return this.baseDaoSupport.queryForList("select roleid from  user_role where userid=?", new Object[] { Integer.valueOf(userid) });
  }

  public void cleanUserRoles(int userid)
  {
    this.baseDaoSupport.execute("delete from user_role where userid=?", new Object[] { Integer.valueOf(userid) });
  }

  public boolean checkHaveRole(int userid, int roleid)
  {
    int count = this.baseDaoSupport.queryForInt("select count(0) from  user_role where userid=? and roleid=?", new Object[] { Integer.valueOf(userid), Integer.valueOf(roleid) });
    return count > 0;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.auth.impl.PermissionManager
 * JD-Core Version:    0.6.1
 */