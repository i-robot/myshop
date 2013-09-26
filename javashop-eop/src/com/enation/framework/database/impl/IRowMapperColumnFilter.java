package com.enation.framework.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract interface IRowMapperColumnFilter
{
  public abstract void filter(Map paramMap, ResultSet paramResultSet)
    throws SQLException;
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.impl.IRowMapperColumnFilter
 * JD-Core Version:    0.6.1
 */