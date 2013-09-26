package com.enation.app.base.core.service;

import com.enation.app.base.core.model.ErrorReport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;

public class ErrorReportManager
{
  private IDaoSupport<ErrorReport> daoSupport;

  public void add(ErrorReport report)
  {
    this.daoSupport.insert("eop_error_report", report);
  }

  public ErrorReport get(Integer id) {
    return (ErrorReport)this.daoSupport.queryForObject("select * from eop_error_report where id=?", ErrorReport.class, new Object[] { id });
  }

  public Page list(int pageNo, int pageSize)
  {
    return this.daoSupport.queryForPage("select * from eop_error_report order by dateline desc", pageNo, pageSize, new Object[0]);
  }

  public IDaoSupport getDaoSupport() {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport daoSupport) {
    this.daoSupport = daoSupport;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.ErrorReportManager
 * JD-Core Version:    0.6.1
 */