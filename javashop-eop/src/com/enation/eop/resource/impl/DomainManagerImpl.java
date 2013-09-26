package com.enation.eop.resource.impl;

import com.enation.eop.resource.IDomainManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopSiteDomain;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.database.IDaoSupport;
import java.util.List;

public class DomainManagerImpl
  implements IDomainManager
{
  private IDaoSupport<EopSiteDomain> daoSupport;

  public EopSiteDomain get(Integer id)
  {
    String sql = "select * from eop_sitedomain where id = ?";
    return (EopSiteDomain)this.daoSupport.queryForObject(sql, EopSiteDomain.class, new Object[] { id });
  }

  public List<EopSiteDomain> listUserDomain()
  {
    Integer userid = EopContext.getContext().getCurrentSite().getUserid();
    String sql = "select * from eop_sitedomain where userid=?";
    return this.daoSupport.queryForList(sql, EopSiteDomain.class, new Object[] { userid });
  }

  public List<EopSiteDomain> listSiteDomain()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    String sql = "select * from eop_sitedomain where userid=? and siteid =?";
    return this.daoSupport.queryForList(sql, EopSiteDomain.class, new Object[] { site.getUserid(), site.getId() });
  }

  public List<EopSiteDomain> listSiteDomain(Integer siteid)
  {
    String sql = "select * from eop_sitedomain where  siteid =?";
    return this.daoSupport.queryForList(sql, EopSiteDomain.class, new Object[] { siteid });
  }

  public void edit(EopSiteDomain domain) {
    this.daoSupport.update("eop_sitedomain", domain, " id = " + domain.getId());
  }

  public IDaoSupport<EopSiteDomain> getDaoSupport()
  {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport<EopSiteDomain> daoSupport) {
    this.daoSupport = daoSupport;
  }

  public int getDomainCount(Integer siteid)
  {
    String sql = "select count(0)  from eop_sitedomain where  siteid =?";
    return this.daoSupport.queryForInt(sql, new Object[] { siteid });
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.DomainManagerImpl
 * JD-Core Version:    0.6.1
 */