package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.DepotUser;
import com.enation.app.shop.core.service.IDepotUserManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import java.util.List;

public class DepotUserManager extends BaseSupport<DepotUser>
  implements IDepotUserManager
{
  public void add(DepotUser depotUser)
  {
    this.daoSupport.execute("insert into es_depot_user(userid,depotid)values(?,?)", new Object[] { depotUser.getUserid(), depotUser.getDepotid() });
  }

  public void edit(DepotUser depotUser)
  {
    this.daoSupport.execute("update es_depot_user set roomid=? where userid=?", new Object[] { depotUser.getDepotid(), depotUser.getUserid() });
  }

  public void delete(int userid)
  {
    this.daoSupport.execute("delete from es_depot_user where userid=?", new Object[] { Integer.valueOf(userid) });
  }

  public DepotUser get(int userid)
  {
    String sql = "select * from es_depot_user where userid=?";
    return (DepotUser)this.daoSupport.queryForObject(sql, DepotUser.class, new Object[] { Integer.valueOf(userid) });
  }

  public List<DepotUser> listByDepotId(int depotid)
  {
    String sql = "select * from es_depot_user where depotid=?";
    return this.daoSupport.queryForList(sql, DepotUser.class, new Object[] { Integer.valueOf(depotid) });
  }

  public List<DepotUser> listByRoleId(int roleid)
  {
    String sql = "select u.*,du.depotid from " + getTableName("adminuser") + " u ," + getTableName("user_role") + " ur, " + getTableName("depot_user") + " du  where ur.userid=u.userid and du.userid = u.userid and ur.roleid=?";
    return this.daoSupport.queryForList(sql, DepotUser.class, new Object[] { Integer.valueOf(roleid) });
  }

  public List<DepotUser> listByRoleId(int depotid, int roleid) {
    String sql = "select u.*,du.depotid from " + getTableName("adminuser") + " u ," + getTableName("user_role") + " ur, " + getTableName("depot_user") + " du  where ur.userid=u.userid and du.userid = u.userid and  du.depotid=? and ur.roleid=?";
    return this.daoSupport.queryForList(sql, DepotUser.class, new Object[] { Integer.valueOf(depotid), Integer.valueOf(roleid) });
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.DepotUserManager
 * JD-Core Version:    0.6.1
 */