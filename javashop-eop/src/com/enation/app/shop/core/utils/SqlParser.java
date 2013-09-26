package com.enation.app.shop.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParser
{
  private static final String Comma = ",";
  private static final String FourSpace = "    ";
  private static boolean isSingleLine = true;
  private String sql;
  private String cols;
  private String tables;
  private String conditions;
  private String groupCols;
  private String orderCols;

  public SqlParser(String sql)
  {
    this.sql = sql.trim();

    parseCols();
    parseTables();
    parseConditions();
    parseGroupCols();
    parseOrderCols();
  }

  private void parseCols()
  {
    String regex = "(select)(.+)(from)";

    this.cols = getMatchedString(regex, this.sql);
  }

  private void parseTables()
  {
    String regex = "";

    if (isContains(this.sql, "\\s+where\\s+")) {
      regex = "(from)(.+)(where)";
    }
    else {
      regex = "(from)(.+)($)";
    }

    this.tables = getMatchedString(regex, this.sql);
  }

  private void parseConditions()
  {
    String regex = "";

    if (isContains(this.sql, "\\s+where\\s+"))
    {
      if (isContains(this.sql, "group\\s+by"))
      {
        regex = "(where)(.+)(group\\s+by)";
      }
      else if (isContains(this.sql, "order\\s+by"))
      {
        regex = "(where)(.+)(order\\s+by)";
      }
      else
      {
        regex = "(where)(.+)($)";
      }
    }
    else
    {
      return;
    }

    this.conditions = getMatchedString(regex, this.sql);
  }

  private void parseGroupCols()
  {
    String regex = "";

    if (isContains(this.sql, "group\\s+by"))
    {
      if (isContains(this.sql, "order\\s+by"))
      {
        regex = "(group\\s+by)(.+)(order\\s+by)";
      }
      else
      {
        regex = "(group\\s+by)(.+)($)";
      }
    }
    else
    {
      return;
    }

    this.groupCols = getMatchedString(regex, this.sql);
  }

  private void parseOrderCols()
  {
    String regex = "";

    if (isContains(this.sql, "order\\s+by"))
    {
      regex = "(order\\s+by)(.+)($)";
    }
    else
    {
      return;
    }

    this.orderCols = getMatchedString(regex, this.sql);
  }

  private static String getMatchedString(String regex, String text)
  {
    Pattern pattern = Pattern.compile(regex, 2);

    Matcher matcher = pattern.matcher(text);

    if (matcher.find()) {
      return matcher.group(2);
    }

    return null;
  }

  private static boolean isContains(String lineText, String word)
  {
    Pattern pattern = Pattern.compile(word, 2);
    Matcher matcher = pattern.matcher(lineText);

    return matcher.find();
  }

  public String toString()
  {
    if ((this.cols == null) && (this.tables == null) && (this.conditions == null) && (this.groupCols == null) && (this.orderCols == null)) {
      return this.sql;
    }

    StringBuffer sb = new StringBuffer();
    sb.append("原SQL为" + this.sql + "\n");
    sb.append("解析后的SQL为\n");

    for (String str : getParsedSqlList()) {
      sb.append(str);
    }

    sb.append("\n");

    return sb.toString();
  }

  private static String getAddEnterStr(String str, String splitStr)
  {
    Pattern p = Pattern.compile(splitStr, 2);

    Matcher m = p.matcher(str);
    StringBuffer sb = new StringBuffer();

    boolean result = m.find();

    while (result) {
      m.appendReplacement(sb, m.group(0) + "\n     ");
      result = m.find();
    }

    m.appendTail(sb);

    return "    " + sb.toString();
  }

  public List<String> getParsedSqlList()
  {
    List sqlList = new ArrayList();

    if ((this.cols == null) && (this.tables == null) && (this.conditions == null) && (this.groupCols == null) && (this.orderCols == null)) {
      sqlList.add(this.sql);
      return sqlList;
    }

    if (this.cols != null) {
      sqlList.add("select\n");

      if (isSingleLine) {
        sqlList.add(getAddEnterStr(this.cols, ","));
      }
      else {
        sqlList.add("    " + this.cols);
      }
    }

    if (this.tables != null) {
      sqlList.add(" \nfrom\n");

      if (isSingleLine) {
        sqlList.add(getAddEnterStr(this.tables, ","));
      }
      else {
        sqlList.add("    " + this.tables);
      }
    }

    if (this.conditions != null) {
      sqlList.add(" \nwhere\n");

      if (isSingleLine) {
        sqlList.add(getAddEnterStr(this.conditions, "(and|or)"));
      }
      else
      {
        sqlList.add("    " + this.conditions);
      }
    }

    if (this.groupCols != null) {
      sqlList.add(" \ngroup by\n");

      if (isSingleLine) {
        sqlList.add(getAddEnterStr(this.groupCols, ","));
      }
      else {
        sqlList.add("    " + this.groupCols);
      }

    }

    if (this.orderCols != null) {
      sqlList.add(" \norder by\n");

      if (isSingleLine) {
        sqlList.add(getAddEnterStr(this.orderCols, ","));
      }
      else {
        sqlList.add("    " + this.orderCols);
      }
    }

    return sqlList;
  }

  public static void setSingleLine(boolean isSingleLine)
  {
    isSingleLine = isSingleLine;
  }

  public static void main(String[] args)
  {
    List ls = new ArrayList();
    ls.add("select * from dual");
    ls.add("SELECT * frOm dual");
    ls.add("Select C1,c2 From tb");
    ls.add("select c1,c2 from tb");
    ls.add("select count(*) from t1");
    ls.add("select c1,c2,c3 from t1 where condi1=1 ");
    ls.add("Select c1,c2,c3 From t1 Where condi1=1 ");
    ls.add("select c1,c2,c3 from t1,t2 where condi3=3 or condi4=5 order   by o1,o2");
    ls.add("Select c1,c2,c3 from t1,t2 Where condi3=3 or condi4=5 Order   by o1,o2");
    ls.add("select c1,c2,c3 from t1,t2,t3 where condi1=5 and condi6=6 or condi7=7 group  by g1,g2");
    ls.add("Select c1,c2,c3 From t1,t2,t3 Where condi1=5 and condi6=6 or condi7=7 Group  by g1,g2");
    ls.add("Select c1,c2,c3 From t1,t2,t3 Where condi1=5 and condi6=6 or condi7=7 Group  by g1,g2,g3 order  by g2,g3");
    String sql;
    for (Iterator i$ = ls.iterator(); i$.hasNext(); sql = (String)i$.next());
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.utils.SqlParser
 * JD-Core Version:    0.6.1
 */