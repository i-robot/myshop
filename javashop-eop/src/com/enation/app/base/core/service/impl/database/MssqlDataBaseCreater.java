package com.enation.app.base.core.service.impl.database;

import com.enation.app.base.core.service.IDataBaseCreater;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.ISqlFileExecutor;

public class MssqlDataBaseCreater
  implements IDataBaseCreater
{
  private ISqlFileExecutor sqlFileExecutor;

  public void create()
  {
    if (EopSetting.RUNMODE.equals("2")) {
      this.sqlFileExecutor.execute("USE [master];");

      this.sqlFileExecutor.execute("IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'eop')  DROP DATABASE [eop]");
      this.sqlFileExecutor.execute("CREATE DATABASE [eop]");
      this.sqlFileExecutor.execute("USE [eop]");
    }

    this.sqlFileExecutor.execute("file:com/enation/eop/eop_mssql.sql");
    this.sqlFileExecutor.execute("file:com/enation/app/shop/javashop_mssql.sql");
    this.sqlFileExecutor.execute("file:com/enation/app/cms/cms_mssql.sql");
  }
  public ISqlFileExecutor getSqlFileExecutor() {
    return this.sqlFileExecutor;
  }
  public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
    this.sqlFileExecutor = sqlFileExecutor;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.database.MssqlDataBaseCreater
 * JD-Core Version:    0.6.1
 */