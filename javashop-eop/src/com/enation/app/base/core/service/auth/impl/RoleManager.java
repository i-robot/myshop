package com.enation.app.base.core.service.auth.impl;

import com.enation.app.base.core.model.AuthAction;
import com.enation.app.base.core.model.Role;
import com.enation.app.base.core.service.auth.IRoleManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.StringUtil;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RoleManager extends BaseSupport<Role>
  implements IRoleManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public void add(Role role, int[] authids)
  {
    this.baseDaoSupport.insert("role", role);

    if (authids == null) return;

    int roleid = this.baseDaoSupport.getLastId("role");

    for (int authid : authids)
      this.baseDaoSupport.execute("insert into role_auth(roleid,authid)values(?,?)", new Object[] { Integer.valueOf(roleid), Integer.valueOf(authid) });
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(int roleid)
  {
    this.baseDaoSupport.execute("delete from user_role where roleid=?", new Object[] { Integer.valueOf(roleid) });

    this.baseDaoSupport.execute("delete from role_auth where roleid =?", new Object[] { Integer.valueOf(roleid) });

    this.baseDaoSupport.execute("delete from role where roleid =?", new Object[] { Integer.valueOf(roleid) });
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void edit(Role role, int[] authids)
  {
    if (role.getRoleid().intValue() == 0) throw new IllegalArgumentException("编辑角色时id不可为空");
    if (StringUtil.isEmpty(role.getRolename())) throw new IllegalArgumentException("编辑角色时名称不可为空");

    this.baseDaoSupport.execute("delete from role_auth where roleid=?", new Object[] { role.getRoleid() });

    for (int authid : authids) {
      this.baseDaoSupport.execute("insert into role_auth(roleid,authid)values(?,?)", new Object[] { role.getRoleid(), Integer.valueOf(authid) });
    }

    this.baseDaoSupport.update("role", role, "roleid=" + role.getRoleid());
  }

  public List<Role> list()
  {
    return this.baseDaoSupport.queryForList("select * from role", Role.class, new Object[0]);
  }

  public Role get(int roleid)
  {
    String sql = "select * from " + getTableName("auth_action") + " where actid in(select authid from " + getTableName("role_auth") + " where roleid =?)";
    List actlist = this.daoSupport.queryForList(sql, AuthAction.class, new Object[] { Integer.valueOf(roleid) });
    Role role = (Role)this.baseDaoSupport.queryForObject("select * from role where roleid=?", Role.class, new Object[] { Integer.valueOf(roleid) });

    if (actlist != null) {
      int[] actids = new int[actlist.size()];
      int i = 0; for (int len = actlist.size(); i < len; i++) {
        actids[i] = ((AuthAction)actlist.get(i)).getActid().intValue();
      }
      role.setActids(actids);
    }
    return role;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.auth.impl.RoleManager
 * JD-Core Version:    0.6.1
 */