package com.enation.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StringMapper
  implements ParameterizedRowMapper
{
  public Object mapRow(ResultSet rs, int rowNum)
    throws SQLException
  {
    String str = rs.getString(1);
    return str;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.StringMapper
 * JD-Core Version:    0.6.1
 */