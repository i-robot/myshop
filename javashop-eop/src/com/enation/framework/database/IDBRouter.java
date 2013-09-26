package com.enation.framework.database;

public abstract interface IDBRouter
{
  public abstract String getTableName(String paramString);

  public abstract void doSaasInstall(String paramString);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.IDBRouter
 * JD-Core Version:    0.6.1
 */