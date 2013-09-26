package com.enation.app.base.core.service;

import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.context.spring.SpringContextHolder;

public abstract class DataBaseCreaterFactory
{
  public static IDataBaseCreater getDataBaseCreater()
  {
    if (EopSetting.DBTYPE.equals("1"))
      return (IDataBaseCreater)SpringContextHolder.getBean("mysqlDataBaseCreater");
    if (EopSetting.DBTYPE.equals("2"))
      return (IDataBaseCreater)SpringContextHolder.getBean("oracleDataBaseCreater");
    if (EopSetting.DBTYPE.equals("3")) {
      return (IDataBaseCreater)SpringContextHolder.getBean("mssqlDataBaseCreater");
    }
    throw new RuntimeException("未知的数据库类型");
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.DataBaseCreaterFactory
 * JD-Core Version:    0.6.1
 */