package com.enation.app.base.core.action;

import com.enation.app.base.core.service.IBatchSqlExecutor;
import com.enation.framework.action.WWAction;
import java.util.List;

public class BatchSqlExecuteAction extends WWAction
{
  private IBatchSqlExecutor batchSqlExecutor;
  private String sql;
  private String toCms;

  public String toExe()
  {
    return "input";
  }

  public String execute() {
    try {
      if (this.toCms == null)
        this.batchSqlExecutor.exeucte(this.sql);
      else
        this.batchSqlExecutor.executeForCms(this.sql);
      this.msgs.add("执行成功");
    } catch (RuntimeException e) {
      this.msgs.add(e.getMessage());
    }
    return "message";
  }

  public String getSql()
  {
    return this.sql;
  }
  public void setSql(String sql) {
    this.sql = sql;
  }
  public IBatchSqlExecutor getBatchSqlExecutor() {
    return this.batchSqlExecutor;
  }
  public void setBatchSqlExecutor(IBatchSqlExecutor batchSqlExecutor) {
    this.batchSqlExecutor = batchSqlExecutor;
  }

  public String getToCms() {
    return this.toCms;
  }

  public void setToCms(String toCms) {
    this.toCms = toCms;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.BatchSqlExecuteAction
 * JD-Core Version:    0.6.1
 */