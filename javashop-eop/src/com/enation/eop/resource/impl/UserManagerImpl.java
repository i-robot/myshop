package com.enation.eop.resource.impl;

import com.enation.app.base.core.service.auth.IAdminUserManager;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.EopUser;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserManagerImpl
  implements IUserManager
{
  private IDaoSupport daoSupport;
  private ISiteManager siteManager;
  private IAdminUserManager adminUserManager;
  protected final Logger logger = Logger.getLogger(getClass());

  public void changeDefaultSite(Integer userid, Integer managerid, Integer defaultsiteid)
  {
    UserUtil.validUser(userid);
    String sql = "update eop_user set defaultsiteid=? where id=?";
    this.daoSupport.execute(sql, new Object[] { defaultsiteid, userid });
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public Integer createUser(EopUser user)
  {
    String sql = "select count(0) from eop_user where username=?";
    int count = this.daoSupport.queryForInt(sql, new Object[] { user.getUsername() });
    if (count > 0) throw new RuntimeException("用户" + user.getUsername() + "已存在");
    user.setPassword(StringUtil.md5(user.getPassword()));

    this.daoSupport.insert("eop_user", user);
    Integer userid = Integer.valueOf(this.daoSupport.getLastId("eop_user"));

    return userid;
  }

  private boolean checkUserName(String username)
  {
    String sql = "select * from eop_user where username=?";
    List list = this.daoSupport.queryForList(sql, new Object[] { username });
    if ((list == null) || (list.isEmpty()) || (list.size() == 0)) {
      return false;
    }
    return true;
  }

  public int login(String username, String password)
  {
    int result = 0;
    try {
      EopUser user = get(username);
      if (user.getPassword().equals(StringUtil.md5(password))) {
        result = 1;
        WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();

        sessonContext.setAttribute("eop_user_key", user);
      }
      else {
        result = 0;
      }
    } catch (RuntimeException e) {
      this.logger.error(e.fillInStackTrace());
    }

    return result;
  }

  public int checkIsLogin()
  {
    WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();

    EopUser user = (EopUser)sessonContext.getAttribute("eop_user_key");
    if (user != null) {
      return 1;
    }
    return 0;
  }

  public void logout()
  {
    WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
    sessonContext.removeAttribute("eop_user_key");

    ThreadContextHolder.getSessionContext().removeAttribute("userAdmin");
  }

  public EopUser getCurrentUser()
  {
    WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
    EopUser user = (EopUser)sessonContext.getAttribute("eop_user_key");
    return user;
  }

  public EopUser get(Integer userid)
  {
    String sql = "select * from eop_user where deleteflag = 0 and id = ?";
    return (EopUser)this.daoSupport.queryForObject(sql, EopUser.class, new Object[] { userid });
  }

  public IDaoSupport<EopUser> getDaoSupport()
  {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport<EopUser> daoSupport)
  {
    this.daoSupport = daoSupport;
  }

  public ISiteManager getSiteManager()
  {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager)
  {
    this.siteManager = siteManager;
  }

  public IAdminUserManager getAdminUserManager()
  {
    return this.adminUserManager;
  }

  public void setAdminUserManager(IAdminUserManager adminUserManager)
  {
    this.adminUserManager = adminUserManager;
  }

  public void edit(EopUser user)
  {
    this.daoSupport.update("eop_user", user, "id = " + user.getId());
  }

  public EopUser get(String username)
  {
    return (EopUser)this.daoSupport.queryForObject("select * from eop_user where username=?", EopUser.class, new Object[] { username });
  }

  public Page list(String keyword, int pageNo, int pageSize)
  {
    String sql = "select u.*,d.regdate  regdate from eop_user u left join eop_userdetail d on  u.id= d.userid";
    if (!StringUtil.isEmpty(keyword)) {
      sql = sql + " where  u.username like '%" + keyword + "%'";
    }

    sql = sql + " order by  d.regdate desc";
    return this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer userid)
  {
    List<Map> siteList = this.siteManager.list(userid);
    for (Map site : siteList) {
      this.siteManager.delete((Integer)site.get("id"));
    }

    this.daoSupport.execute("delete from eop_userdetail where userid = ?", new Object[] { userid });
    this.daoSupport.execute("delete from eop_useradmin where userid = ?", new Object[] { userid });
    this.daoSupport.execute("delete from eop_user where id = ?", new Object[] { userid });
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.UserManagerImpl
 * JD-Core Version:    0.6.1
 */