package com.enation.framework.database.impl;

import com.enation.eop.sdk.context.EopSetting;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class LowerCaseJdbcTemplate extends JdbcTemplate
{
  protected RowMapper getColumnMapRowMapper()
  {
    if ("2".equals(EopSetting.DBTYPE))
      return new OracleColumnMapRowMapper();
    if ("1".equals(EopSetting.DBTYPE)) {
      return new MySqlColumnMapRowMapper();
    }
    return new ColumnMapRowMapper();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.impl.LowerCaseJdbcTemplate
 * JD-Core Version:    0.6.1
 */