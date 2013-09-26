package com.enation.app.base.core.service.dbsolution.impl;

import com.enation.app.base.core.service.dbsolution.IDBSolution;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;

public abstract class DBSolution
  implements IDBSolution
{
  public static final String EXECUTECHAR = "!-->";
  protected String prefix = "";

  protected List<String> functions = new ArrayList();
  protected Connection conn;
  protected String sqlExchange;

  public String getSqlExchange()
  {
    return this.sqlExchange;
  }

  public void setSqlExchange(String sqlExchange) {
    this.sqlExchange = sqlExchange;
  }

  protected String getTableName(String table)
  {
    return StringUtil.addPrefix(table, this.prefix);
  }

  protected String getSaasTableName(String table, int userid, int siteid) {
    if (table.toLowerCase().startsWith("eop_")) {
      return getTableName(table);
    }
    return getTableName(table) + "_" + userid + "_" + siteid;
  }

  public String encode(String text)
  {
    text = text.replaceAll("&", "&amp;");
    text = text.replaceAll("<", "&lt;");
    text = text.replaceAll(">", "&gt;");
    return text;
  }

  public String decode(String text)
  {
    text = text.replaceAll("&amp;", "&");
    text = text.replaceAll("&lt;", "<");
    text = text.replaceAll("&gt;", ">");
    return text;
  }

  public String encodeValue(String value)
  {
    value = value.replace("'", "!quote;");
    return value.replaceAll(",", "!comma;");
  }

  public String decodeValue(String value)
  {
    value = value.replace("!quote;", "'");
    return value.replaceAll("!comma;", ",");
  }

  protected String[] getFuncName()
  {
    return null;
  }

  protected Object getFuncValue(String name, String value)
  {
    return value;
  }

  protected String getConvertedSQL(String sql)
  {
    return sql;
  }

  protected void initFunctions() {
    this.functions.add("time");
  }

  protected Object doFunction(String name, String value) {
    if ("time".equals(name)) {
      return new Timestamp(Long.parseLong(value));
    }
    return null;
  }

  protected boolean beforeInsert(String table, String fields, String values)
  {
    return true;
  }

  protected void afterInsert(String table, String fields, String values)
  {
  }

  protected void beforeImport()
  {
  }

  protected void afterImport()
  {
  }

  protected Object getFuncValue(String value)
  {
    for (int i = 0; i < this.functions.size(); i++) {
      if (((String)this.functions.get(i)).length() < value.length()) {
        String prefix = value.substring(0, ((String)this.functions.get(i)).length()).toLowerCase();

        if (((String)this.functions.get(i)).equals(prefix)) {
          String param = value.substring(prefix.length() + 1);
          param = param.substring(0, param.length() - 1);
          return doFunction(prefix, param);
        }
      }
    }
    return value;
  }

  public String getFieldValue(int fieldType, Object fieldValue)
  {
    String value = encodeValue("" + fieldValue);
    if ((12 == fieldType) || (-9 == fieldType) || (1 == fieldType))
    {
      return "'" + value + "'";
    }if ((-9 == fieldType) || (-1 == fieldType) || (2005 == fieldType))
    {
      return "'" + value + "'";
    }return "" + value;
  }

  public boolean dbExport(String[] tables, String xml)
  {
    DBExporter dbExporter = new DBExporter(this);
    return dbExporter.doExport(this.prefix, tables, xml);
  }

  public String dbExport(String[] tables, boolean dataOnly)
  {
    DBExporter dbExporter = new DBExporter(this);
    return dbExporter.doExport(this.prefix, tables, dataOnly);
  }

  public String dbSaasExport(String[] tables, boolean dataOnly, int userid, int siteid)
  {
    DBExporter dbExporter = new DBExporter(this);
    return dbExporter.doExport(this.prefix, tables, dataOnly, userid, siteid);
  }

  public boolean dbImport(String xml)
  {
    initFunctions();
    DBImporter dbImporter = new DBImporter(this);

    return dbImporter.doImport(xml);
  }

  public boolean dbSaasImport(String xml, int userid, int siteid)
  {
    initFunctions();
    DBImporter dbImporter = new DBImporter(this);

    return dbImporter.doSaasImport(xml, userid, siteid);
  }

  public void setPrefix(String prefix)
  {
    this.prefix = prefix;
  }

  public boolean executeSqls(String sql)
  {
    String[] sqls = sql.split("!-->");
    try
    {
      Statement stat = this.conn.createStatement();
      for (int i = 0; i < sqls.length; i++) {
        sql = sqls[i].trim();
        if (!"".equals(sql)) {
          stat.execute(sql);
        }
      }
      stat.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("出错语句：" + sql);
      return false;
    }
    return true;
  }

  public String getInertSQL(Element action) {
    String sql = "insert into " + getTableName(action.elementText("table")) + " (";

    sql = sql + action.elementText("fields") + ") values (";
    sql = sql + decode(action.elementText("values")) + ")" + "!-->";

    return sql;
  }

  public int dropTable(String table)
  {
    if (executeSqls(getDropSQL(table)))
      return 1;
    return 0;
  }

  public void setConnection(Connection conn)
  {
    this.conn = conn;
  }

  public abstract String getCreateSQL(Element paramElement);

  public abstract String getSaasCreateSQL(Element paramElement, int paramInt1, int paramInt2);

  public abstract String getDropSQL(String paramString);

  public abstract String toLocalType(String paramString1, String paramString2);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.DBSolution
 * JD-Core Version:    0.6.1
 */