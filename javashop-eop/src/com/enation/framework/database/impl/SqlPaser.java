package com.enation.framework.database.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlPaser
{
  public static String insertSelectField(String field, String sql)
  {
    sql = "select " + field + "," + sql.substring(6, sql.length());
    return sql;
  }

  public static String findOrderStr(String sql)
  {
    String pattern = "(order\\s*by[\\w|\\W|\\s|\\S]*)";
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(sql);

    if (m.find()) {
      return m.group();
    }

    return null;
  }

  public static void main(String[] args)
  {
    String sql = "select * from abc where 12=12 order by id asc ";
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.impl.SqlPaser
 * JD-Core Version:    0.6.1
 */