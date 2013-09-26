package com.enation.framework.database.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

public class FilterColumnMapRowMapper extends ColumnMapRowMapper
{
  private IRowMapperColumnFilter filter;

  public FilterColumnMapRowMapper(IRowMapperColumnFilter _filter)
  {
    this.filter = _filter;
  }

  public Object mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    ResultSetMetaData rsmd = rs.getMetaData();
    int columnCount = rsmd.getColumnCount();
    Map mapOfColValues = createColumnMap(columnCount);
    for (int i = 1; i <= columnCount; i++) {
      String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));

      key = key.toLowerCase();
      Object obj = getColumnValue(rs, i);
      mapOfColValues.put(key, obj);
      this.filter.filter(mapOfColValues, rs);
    }
    return mapOfColValues;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.impl.FilterColumnMapRowMapper
 * JD-Core Version:    0.6.1
 */