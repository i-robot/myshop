package com.enation.eop.resource.impl;

import com.enation.app.base.core.model.DataLog;
import com.enation.app.base.core.model.DataLogMapper;
import com.enation.eop.resource.IDataLogManager;
import com.enation.eop.resource.IDomainManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopSiteDomain;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.util.Date;
import java.util.List;

public class DataLogManager extends BaseSupport
  implements IDataLogManager
{
  private IDomainManager domainManager;

  public void add(DataLog datalog)
  {
    if (datalog == null) throw new IllegalArgumentException("参数datalog为空");
    EopSite site = EopContext.getContext().getCurrentSite();

    datalog.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
    datalog.setUserid(site.getUserid());
    datalog.setSiteid(site.getId());
    datalog.setSitename(site.getSitename());

    List domains = this.domainManager.listSiteDomain(site.getId());
    if ((domains != null) && (domains.size() > 0)) {
      datalog.setDomain(((EopSiteDomain)domains.get(0)).getDomain());
    }
    this.daoSupport.insert("eop_data_log", datalog);
  }

  public Page list(String start, String end, int pageNo, int pageSize)
  {
    Date startDate = start == null ? null : DateUtil.toDate(start, "yyyy-MM-dd");
    Date endDate = end == null ? null : DateUtil.toDate(end, "yyyy-MM-dd");

    endDate = endDate == null ? new Date() : endDate;

    Integer startSec = null;
    if (startDate != null)
      startSec = Integer.valueOf((int)(startDate.getTime() / 1000L));
    int endSec = (int)(endDate.getTime() / 1000L);

    String sql = "select * from eop_data_log where dateline<=" + endSec;
    if (startDate != null) sql = sql + " and dateline>=" + startSec;
    sql = sql + " order by dateline desc";

    return this.daoSupport.queryForPage(sql, pageNo, pageSize, new DataLogMapper(), new Object[0]);
  }

  public IDomainManager getDomainManager() {
    return this.domainManager;
  }

  public void setDomainManager(IDomainManager domainManager) {
    this.domainManager = domainManager;
  }

  public void delete(Integer[] ids) {
    String idstr = StringUtil.arrayToString(ids, ",");
    this.daoSupport.execute("delete from eop_data_log where id in(" + idstr + ")", new Object[0]);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.DataLogManager
 * JD-Core Version:    0.6.1
 */