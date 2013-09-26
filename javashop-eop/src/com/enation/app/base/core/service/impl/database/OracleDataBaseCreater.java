package com.enation.app.base.core.service.impl.database;

import com.enation.app.base.core.service.IDataBaseCreater;
import com.enation.framework.database.ISqlFileExecutor;

public class OracleDataBaseCreater
  implements IDataBaseCreater
{
  private ISqlFileExecutor sqlFileExecutor;

  public void create()
  {
    this.sqlFileExecutor.execute("file:com/enation/eop/eop_mysql.sql");
    this.sqlFileExecutor.execute("file:com/enation/app/shop/javashop_mysql.sql");
    this.sqlFileExecutor.execute("file:com/enation/app/cms/cms_mysql.sql");
  }
  public ISqlFileExecutor getSqlFileExecutor() {
    return this.sqlFileExecutor;
  }
  public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
    this.sqlFileExecutor = sqlFileExecutor;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.database.OracleDataBaseCreater
 * JD-Core Version:    0.6.1
 */