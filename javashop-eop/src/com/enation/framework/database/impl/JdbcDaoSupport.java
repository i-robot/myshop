package com.enation.framework.database.impl;

import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.DBRuntimeException;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.ObjectNotFoundException;
import com.enation.framework.database.Page;
import com.enation.framework.util.ReflectionUtil;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.util.Assert;

public class JdbcDaoSupport<T>
  implements IDaoSupport<T>
{
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcTemplate simpleJdbcTemplate;
  protected final Logger logger = Logger.getLogger(getClass());

  public void execute(String sql, Object[] args) {
    try {
      this.simpleJdbcTemplate.update(sql, args);
    } catch (Exception e) {
      throw new DBRuntimeException(e, sql);
    }
  }

  public int getLastId(String table) {
    if (EopSetting.DBTYPE.equals("1")) {
      return this.jdbcTemplate.queryForInt("SELECT last_insert_id() as id");
    }
    if (EopSetting.DBTYPE.equals("2")) {
      int result = 0;
      result = this.jdbcTemplate.queryForInt("SELECT s_" + table + ".currval as id from DUAL");

      return result;
    }if (EopSetting.DBTYPE.equals("3")) {
      int result = 0;
      result = this.jdbcTemplate.queryForInt("select @@identity");
      return result;
    }

    throw new RuntimeException("未知的数据库类型");
  }

  public void insert(String table, Map fields) {
    String sql = "";
    try
    {
      Assert.hasText(table, "表名不能为空");
      Assert.notEmpty(fields, "字段不能为空");
      table = quoteCol(table);

      Object[] cols = fields.keySet().toArray();
      Object[] values = new Object[cols.length];
      for (int i = 0; i < cols.length; i++) {
        if (fields.get(cols[i]) == null)
          values[i] = null;
        else {
          values[i] = fields.get(cols[i]).toString();
        }
        cols[i] = quoteCol(cols[i].toString());
      }

      sql = "INSERT INTO " + table + " (" + StringUtil.implode(", ", cols);

      sql = sql + ") VALUES (" + StringUtil.implodeValue(", ", values);

      sql = sql + ")";

      this.jdbcTemplate.update(sql, values);
    }
    catch (Exception e) {
      throw new DBRuntimeException(e, sql);
    }
  }

  public void insert(String table, Object po) {
    insert(table, ReflectionUtil.po2Map(po));
  }

  public int queryForInt(String sql, Object[] args) {
    try {
      return this.simpleJdbcTemplate.queryForInt(sql, args);
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e);
      throw e;
    }
  }

  public String queryForString(String sql)
  {
    String s = "";
    try {
      s = (String)this.jdbcTemplate.queryForObject(sql, String.class);
    } catch (RuntimeException e) {
      e.printStackTrace();
    }

    return s;
  }

  public List queryForList(String sql, Object[] args)
  {
    return this.jdbcTemplate.queryForList(sql, args);
  }

  public List<T> queryForList(String sql, RowMapper mapper, Object[] args) {
    try {
      return this.jdbcTemplate.query(sql, args, mapper);
    } catch (Exception ex) {
      throw new DBRuntimeException(ex, sql);
    }
  }

  public List<T> queryForList(String sql, Class clazz, Object[] args) {
    return this.simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
  }

  public List queryForListPage(String sql, int pageNo, int pageSize, Object[] args)
  {
    try
    {
      Assert.hasText(sql, "SQL语句不能为空");
      Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
      String listSql = buildPageSql(sql, pageNo, pageSize);
      return queryForList(listSql, args);
    } catch (Exception ex) {
      throw new DBRuntimeException(ex, sql);
    }
  }

  public List<T> queryForList(String sql, int pageNo, int pageSize, RowMapper mapper)
  {
    try
    {
      Assert.hasText(sql, "SQL语句不能为空");
      Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
      String listSql = buildPageSql(sql, pageNo, pageSize);
      return queryForList(listSql, mapper, new Object[0]);
    } catch (Exception ex) {
      throw new DBRuntimeException(ex, sql);
    }
  }

  public long queryForLong(String sql, Object[] args)
  {
    return this.jdbcTemplate.queryForLong(sql, args);
  }

  public Map queryForMap(String sql, Object[] args)
  {
    try {
      Map map = this.jdbcTemplate.queryForMap(sql, args);
      if (EopSetting.DBTYPE.equals("2")) {
        Map newMap = new HashMap();
        Iterator keyItr = map.keySet().iterator();
        while (keyItr.hasNext()) {
          String key = (String)keyItr.next();
          Object value = map.get(key);
          newMap.put(key.toLowerCase(), value);
        }
        return newMap;
      }
      return map;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new ObjectNotFoundException(ex, sql);
    }
  }

  public T queryForObject(String sql, Class clazz, Object[] args) {
    try {
      return (T) this.simpleJdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
    }
    catch (Exception ex)
    {
      this.logger.error("查询出错", ex);
    }return null;
  }

  public Page queryForPage(String sql, int pageNo, int pageSize, Object[] args)
  {
    try
    {
      Assert.hasText(sql, "SQL语句不能为空");
      Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
      String listSql = buildPageSql(sql, pageNo, pageSize);
      String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));

      List list = queryForList(listSql, args);
      int totalCount = queryForInt(countSql, args);
      return new Page(0L, totalCount, pageSize, list);
    } catch (Exception ex) {
      throw new DBRuntimeException(ex, sql);
    }
  }

  public Page queryForPage(String sql, int pageNo, int pageSize, RowMapper rowMapper, Object[] args)
  {
    try
    {
      Assert.hasText(sql, "SQL语句不能为空");
      Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
      String listSql = buildPageSql(sql, pageNo, pageSize);
      String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));

      List list = queryForList(listSql, rowMapper, args);
      int totalCount = queryForInt(countSql, args);
      return new Page(0L, totalCount, pageSize, list);
    } catch (Exception ex) {
      throw new DBRuntimeException(ex, sql);
    }
  }

  public Page queryForPage(String sql, int pageNo, int pageSize, Class<T> clazz, Object[] args)
  {
    try
    {
      Assert.hasText(sql, "SQL语句不能为空");
      Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
      String listSql = buildPageSql(sql, pageNo, pageSize);
      String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));

      List list = queryForList(listSql, clazz, args);
      int totalCount = queryForInt(countSql, args);
      return new Page(0L, totalCount, pageSize, list);
    } catch (Exception ex) {
      throw new DBRuntimeException(ex, sql);
    }
  }

  public void update(String table, Map fields, Map where)
  {
    String whereSql = "";

    if (where != null) {
      Object[] wherecols = where.keySet().toArray();
      for (int i = 0; i < wherecols.length; i++) {
        wherecols[i] = (quoteCol(wherecols[i].toString()) + "=" + quoteValue(where.get(wherecols[i]).toString()));
      }

      whereSql = whereSql + StringUtil.implode(" AND ", wherecols);
    }
    update(table, fields, whereSql);
  }

  public void update(String table, T po, Map where)
  {
    String whereSql = "";

    if (where != null) {
      Object[] wherecols = where.keySet().toArray();
      for (int i = 0; i < wherecols.length; i++) {
        wherecols[i] = (quoteCol(wherecols[i].toString()) + "=" + quoteValue(where.get(wherecols[i]).toString()));
      }

      whereSql = whereSql + StringUtil.implode(" AND ", wherecols);
    }
    update(table, ReflectionUtil.po2Map(po), whereSql);
  }

  public void update(String table, T po, String where)
  {
    update(table, ReflectionUtil.po2Map(po), where);
  }

  public void update(String table, Map fields, String where)
  {
    String sql = "";
    try {
      Assert.hasText(table, "表名不能为空");
      Assert.notEmpty(fields, "字段不能为空");
      Assert.hasText(where, "where条件不能为空");
      table = quoteCol(table);

      String key = null;
      Object value = null;

      if ("3".equals(EopSetting.DBTYPE)) {
        try {
          where = where.replaceAll(" ", "");
          key = org.apache.commons.lang.StringUtils.split(where, "=")[0];
          if (key != null)
            value = fields.get(key);
          fields.remove(key);
        } catch (Exception e) {
          e.printStackTrace();
          System.out.println("SQLServer数据库更新异常，可能需要单独处理！");
        }

      }

      Object[] cols = fields.keySet().toArray();

      Object[] values = new Object[cols.length];
      for (int i = 0; i < cols.length; i++) {
        if (fields.get(cols[i]) == null)
          values[i] = null;
        else {
          values[i] = fields.get(cols[i]).toString();
        }
        cols[i] = (quoteCol(cols[i].toString()) + "=?");
      }

      sql = "UPDATE " + table + " SET " + StringUtil.implode(", ", cols) + " WHERE " + where;

      this.simpleJdbcTemplate.update(sql, values);
      if (value != null)
        fields.put(key, value);
    } catch (Exception e) {
      throw new DBRuntimeException(e, sql);
    }
  }

  public String buildPageSql(String sql, int page, int pageSize)
  {
    String sql_str = null;

    String db_type = EopSetting.DBTYPE;
    if (db_type.equals("1"))
      db_type = "mysql";
    else if (db_type.equals("2"))
      db_type = "oracle";
    else if (db_type.equals("3")) {
      db_type = "sqlserver";
    }

    if (db_type.equals("mysql")) {
      sql_str = sql + " LIMIT " + (page - 1) * pageSize + "," + pageSize;
    } else if (db_type.equals("oracle")) {
      StringBuffer local_sql = new StringBuffer("SELECT * FROM (SELECT t1.*,rownum sn1 FROM (");

      local_sql.append(sql);
      local_sql.append(") t1) t2 WHERE t2.sn1 BETWEEN ");
      local_sql.append((page - 1) * pageSize + 1);
      local_sql.append(" AND ");
      local_sql.append(page * pageSize);
      sql_str = local_sql.toString();
    } else if (db_type.equals("sqlserver")) {
      StringBuffer local_sql = new StringBuffer();

      String order = SqlPaser.findOrderStr(sql);

      if (order != null)
        sql = sql.replaceAll(order, "");
      else {
        order = "order by id desc";
      }

      local_sql.append("select * from (");
      local_sql.append(SqlPaser.insertSelectField("ROW_NUMBER() Over(" + order + ") as rowNum", sql));

      local_sql.append(") tb where rowNum between ");
      local_sql.append((page - 1) * pageSize + 1);
      local_sql.append(" AND ");
      local_sql.append(page * pageSize);

      return local_sql.toString();
    }

    return sql_str.toString();
  }

  private String quoteCol(String col)
  {
    if ((col == null) || (col.equals(""))) {
      return "";
    }

    return col;
  }

  private String quoteValue(String value)
  {
    if ((value == null) || (value.equals(""))) {
      return "''";
    }
    return "'" + value.replaceAll("'", "''") + "'";
  }

  private String getStr(int num, String str)
  {
    StringBuffer sb = new StringBuffer("");
    for (int i = 0; i < num; i++) {
      sb.append(str);
    }
    return sb.toString();
  }

  private String removeSelect(String sql)
  {
    sql = sql.toLowerCase();
    Pattern p = Pattern.compile("\\(.*\\)", 2);
    Matcher m = p.matcher(sql);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      int c = m.end() - m.start();
      m.appendReplacement(sb, getStr(c, "~"));
    }
    m.appendTail(sb);

    String replacedSql = sb.toString();

    return sql.substring(replacedSql.indexOf("from"));
  }

  private String removeOrders(String hql)
  {
    Assert.hasText(hql);
    Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);

    Matcher m = p.matcher(hql);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      m.appendReplacement(sb, "");
    }
    m.appendTail(sb);
    return sb.toString();
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
    this.simpleJdbcTemplate = simpleJdbcTemplate;
  }

  public T queryForObject(String sql, ParameterizedRowMapper mapper, Object[] args)
  {
    try {
      return (T) this.simpleJdbcTemplate.queryForObject(sql, mapper, args);
    } catch (RuntimeException e) {
    }
    return null;
  }

  public List<T> queryForList(String sql, IRowMapperColumnFilter filter, Object[] args)
  {
    FilterColumnMapRowMapper filterColumnMapRowMapper = new FilterColumnMapRowMapper(filter);
    return queryForList(sql, filterColumnMapRowMapper, args);
  }

  public List<T> queryForList(String sql, int pageNo, int pageSize, IRowMapperColumnFilter filter)
  {
    FilterColumnMapRowMapper filterColumnMapRowMapper = new FilterColumnMapRowMapper(filter);
    return queryForList(sql, pageNo, pageSize, filterColumnMapRowMapper);
  }

  public Page queryForPage(String sql, int pageNo, int pageSize, IRowMapperColumnFilter filter, Object[] args)
  {
    FilterColumnMapRowMapper filterColumnMapRowMapper = new FilterColumnMapRowMapper(filter);
    return queryForPage(sql, pageNo, pageSize, filterColumnMapRowMapper, args);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.impl.JdbcDaoSupport
 * JD-Core Version:    0.6.1
 */