package com.enation.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class IntegerMapper
  implements ParameterizedRowMapper
{
  public Object mapRow(ResultSet rs, int rowNum)
    throws SQLException
  {
    Integer v = Integer.valueOf(rs.getInt(1));
    return v;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.IntegerMapper
 * JD-Core Version:    0.6.1
 */