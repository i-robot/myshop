package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.ISetupCreator;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dom4j.Document;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class SqlExportService
{
  private SimpleJdbcTemplate simpleJdbcTemplate;
  private ISetupCreator setupCreator;

  public String dumpSql()
  {
    List tables = getAllTableNames();
    return dumpSql();
  }

  public String dumpSql(List<String> tables)
  {
    return dumpSql(tables, "es");
  }

  public String dumpSql(List<String> tables, String prefix) {
    String sql = "";

    if (tables == null)
      throw new RuntimeException("不存在数据库或者是没有表");
    int i = 0; for (int len = tables.size(); i < len; i++) {
      String tabname = ((String)tables.get(i)).toString();

      if ((!tabname.startsWith("eop_")) && 
        (!tabname.endsWith("widgetbundle")))
      {
        if ((!tabname.endsWith("border")) && 
          ((!tabname.endsWith("menu")) || (tabname.endsWith("site_menu"))) && 
          (!tabname.endsWith("themeuri")) && 
          (!tabname.endsWith("theme")) && 
          (!tabname.endsWith("admintheme")))
        {
          String querySql;
          if ("2".equals(EopSetting.RUNMODE)) {
            EopSite site = EopContext.getContext().getCurrentSite();
            querySql = "show tables like '" + prefix + tabname + "_" + site.getUserid() + "_" + site.getId() + "'";
          } else {
            querySql = "show tables like '" + prefix + tabname + "'";
          }

          List tblist = this.simpleJdbcTemplate.queryForList(querySql, new Object[0]);
          if ((tblist != null) && (!tblist.isEmpty()))
          {
            sql = sql + dumpTableSql(new StringBuilder().append(prefix).append(tabname).toString());
          }
        }
      }
    }
    return sql;
  }

  public String dumpSql(List<String> tables, String target, Document setup) {
    String sql = "";

    if (tables == null)
      throw new RuntimeException("不存在数据库或者是没有表");
    int i = 0; for (int len = tables.size(); i < len; i++) {
      String tabname = ((String)tables.get(i)).toString();

      if ((!tabname.startsWith("eop_")) && 
        (!tabname.endsWith("widgetbundle")))
      {
        if ((!tabname.endsWith("border")) && 
          ((!tabname.endsWith("menu")) || (tabname.endsWith("site_menu"))) && 
          (!tabname.endsWith("themeuri")) && 
          (!tabname.endsWith("theme")) && 
          (!tabname.endsWith("admintheme")))
        {
          String querySql;
          if ("2".equals(EopSetting.RUNMODE)) {
            EopSite site = EopContext.getContext().getCurrentSite();
            querySql = "show tables like 'es_" + tabname + "_" + site.getUserid() + "_" + site.getId() + "'";
          } else {
            querySql = "show tables like 'es_" + tabname + "'";
          }

          List tblist = this.simpleJdbcTemplate.queryForList(querySql, new Object[0]);
          if ((tblist != null) && (!tblist.isEmpty()))
          {
            if ((tabname.toLowerCase().equals("data_cat")) || (tabname.toLowerCase().equals("data_model")) || (tabname.toLowerCase().equals("data_field")))
              this.setupCreator.addTable(setup, "clean", "es_" + tabname);
            else {
              this.setupCreator.addTable(setup, target, "es_" + tabname);
            }

            sql = sql + dumpTableSql(new StringBuilder().append("es_").append(tabname).toString());
          }
        }
      }
    }
    return sql;
  }

  public List getAllTableNames()
  {
    List tableList = new ArrayList();
    List list = this.simpleJdbcTemplate.queryForList("show tables", new Object[0]);
    int i = 0; for (int len = list.size(); i < len; i++) {
      Map map = (Map)list.get(i);
      Object[] keyArray = map.keySet().toArray();
      for (int j = 0; j < keyArray.length; j++) {
        if (map.get(keyArray[j].toString()) != null)
          tableList.add(map.get(keyArray[j].toString()).toString());
      }
    }
    return tableList;
  }

  public String dumpTableSql(String table)
    throws BadSqlGrammarException, EmptyResultDataAccessException
  {
    String sql = "";

    sql = sql + getInsertSql(table);

    return sql;
  }

  public String getCreateTableSql(String table)
    throws BadSqlGrammarException
  {
    Map map = this.simpleJdbcTemplate.queryForMap("SHOW CREATE TABLE " + table + ";", new Object[0]);
    Object temp = map.get("Create Table");
    String sql;
    if ((temp instanceof String))
      sql = (String)temp;
    else {
      sql = new String((byte[])temp);
    }

    return sql + ";\n";
  }

  public String getTableStatus(String table)
    throws EmptyResultDataAccessException
  {
    String sql = "";
    Map map = this.simpleJdbcTemplate.queryForMap("SHOW TABLE STATUS LIKE '" + table + "'", new Object[0]);
    if ((map != null) && 
      (map.get("Auto_increment") != null) && (!map.get("Auto_increment").equals(""))) {
      sql = "  AUTO_INCREMENT=" + map.get("Auto_increment").toString();
    }

    sql = sql + ";\n\n";
    return sql;
  }

  public String getInsertSql(final String table)
    throws BadSqlGrammarException
  {
    String rname = table;
    EopSite site = EopContext.getContext().getCurrentSite();

    Integer userid = site.getUserid();
    Integer siteid = site.getId();
    if ("2".equals(EopSetting.RUNMODE)) {
      rname = table + "_" + userid + "_" + siteid;
    }
    StringBuffer sql = new StringBuffer();

    int total = this.simpleJdbcTemplate.queryForInt("SELECT COUNT(0) FROM " + rname, new Object[0]);
    int pageSize = 200;
    int pageTotal = (int)Math.ceil(total / pageSize);

    ParameterizedRowMapper mapper = new ParameterizedRowMapper()
    {
      private String getValueString(String value, int type) {
        String separator = "";

        switch (type) {
        case 12:
          separator = "'";
          break;
        case -1:
          separator = "'";
          break;
        case 1:
          separator = "'";
          break;
        case 91:
          separator = "'";
          break;
        case 92:
          separator = "'";
          break;
        case 93:
          separator = "'";
        }

        return separator + value + separator;
      }

      public Object mapRow(ResultSet rs, int arg1) throws SQLException
      {
        StringBuffer fieldstr = new StringBuffer();
        StringBuffer valuestr = new StringBuffer();
        StringBuffer sb = new StringBuffer();

        ResultSetMetaData rsmd = rs.getMetaData();
        sb.append("INSERT INTO " + table + "_<userid>_<siteid> (");
        int count = rsmd.getColumnCount();
        String comma = "";
        for (int i = 1; i <= count; i++)
        {
          String fieldname = rsmd.getColumnName(i);
          int type = rsmd.getColumnType(i);

          if (i != 1) {
            fieldstr.append(",");
          }
          fieldstr.append("" + fieldname + "");

          String value = rs.getString(fieldname);
          if (value != null)
            valuestr.append(comma + getValueString(SqlExportService.this.mysql_escape_string(value), type));
          else {
            valuestr.append(comma + "null");
          }
          comma = ",";
        }

        sb.append(fieldstr);
        sb.append(") VALUES (");
        sb.append(valuestr);
        sb.append(");\n");
        return sb.toString();
      }
    };
    List list = null;

    for (int i = 1; i <= pageTotal; i++) {
      String querysql = "SELECT * FROM " + rname + " LIMIT " + (i - 1) * pageSize + ", " + pageSize;
      list = this.simpleJdbcTemplate.query(querysql, mapper, new Object[0]);
      for (int j = 0; j < list.size(); j++) {
        sql.append(list.get(j).toString());
      }
    }

    return sql.toString();
  }

  public String getMySqlVersion()
  {
    String version = "";
    Map map = this.simpleJdbcTemplate.queryForMap("SELECT version() as version", new Object[0]);
    version = map.get("version").toString();
    return version;
  }

  private String mysql_escape_string(String str)
  {
    if ((str == null) || (str.length() == 0))
      return str;
    if ("1".equals(EopSetting.DBTYPE)) {
      str = str.replaceAll("'", "\\\\'");
    }

    if ("3".equals(EopSetting.DBTYPE)) {
      str = str.replaceAll("'", "''");
    }

    str = str.replaceAll("\"", "\\\"");
    str = str.replaceAll("\r", "");
    str = str.replaceAll("\n", "");
    return str;
  }

  public static void main(String[] args)
  {
    System.out.println("<p'dd'a>".replaceAll("'", "\\\\'"));
  }

  public SimpleJdbcTemplate getSimpleJdbcTemplate() {
    return this.simpleJdbcTemplate;
  }

  public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
    this.simpleJdbcTemplate = simpleJdbcTemplate;
  }

  public ISetupCreator getSetupCreator()
  {
    return this.setupCreator;
  }

  public void setSetupCreator(ISetupCreator setupCreator)
  {
    this.setupCreator = setupCreator;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SqlExportService
 * JD-Core Version:    0.6.1
 */