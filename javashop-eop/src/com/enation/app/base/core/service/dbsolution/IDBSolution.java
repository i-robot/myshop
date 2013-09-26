package com.enation.app.base.core.service.dbsolution;

import java.sql.Connection;

public abstract interface IDBSolution
{
  public abstract void setConnection(Connection paramConnection);

  public abstract boolean dbImport(String paramString);

  public abstract boolean dbExport(String[] paramArrayOfString, String paramString);

  public abstract String dbExport(String[] paramArrayOfString, boolean paramBoolean);

  public abstract String dbSaasExport(String[] paramArrayOfString, boolean paramBoolean, int paramInt1, int paramInt2);

  public abstract int dropTable(String paramString);

  public abstract boolean dbSaasImport(String paramString, int paramInt1, int paramInt2);

  public abstract void setPrefix(String paramString);

  public abstract String toLocalType(String paramString1, String paramString2);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.IDBSolution
 * JD-Core Version:    0.6.1
 */