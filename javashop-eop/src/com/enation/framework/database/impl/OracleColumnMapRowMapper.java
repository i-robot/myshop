package com.enation.framework.database.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

public class OracleColumnMapRowMapper extends ColumnMapRowMapper
{
  public Object mapRow(ResultSet rs, int rowNum)
    throws SQLException
  {
    ResultSetMetaData rsmd = rs.getMetaData();
    int columnCount = rsmd.getColumnCount();
    Map mapOfColValues = createColumnMap(columnCount);
    for (int i = 1; i <= columnCount; i++) {
      String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
      key = key.toLowerCase();
      Object obj = null;
      String typename = rsmd.getColumnTypeName(i);
      if ("NUMBER".equals(typename)) {
        int scale = rsmd.getScale(i);
        int precision = rsmd.getPrecision(i);
        if (scale == 0) {
          if (precision < 10)
            obj = Integer.valueOf(rs.getInt(i));
          else
            obj = Long.valueOf(rs.getLong(i));
        } else if (scale > 0)
          obj = Double.valueOf(rs.getDouble(i));
        else
          obj = Long.valueOf(rs.getLong(i));
      } else {
        obj = getColumnValue(rs, i);
      }

      mapOfColValues.put(key, obj);
    }
    return mapOfColValues;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.impl.OracleColumnMapRowMapper
 * JD-Core Version:    0.6.1
 */