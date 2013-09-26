package com.enation.app.base.core.service.dbsolution.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import org.dom4j.Element;

public class MySQLSolution extends DBSolution
{
  public String toLocalType(String type, String size)
  {
    if ("int".equals(type)) {
      if ("1".equals(size)) {
        return "smallint(1)";
      }
      return "int(" + size + ")";
    }

    if ("memo".equals(type)) {
      return "longtext";
    }
    if ("datetime".equals(type)) {
      return "datetime";
    }
    if ("long".equals(type)) {
      return "bigint";
    }
    return type + "(" + size + ")";
  }

  public String getCreateSQL(Element action)
  {
    String table = getTableName(action.elementText("table"));
    List fields = action.elements("field");

    String sql = getDropSQL(table) + "!-->";
    sql = sql + "create table " + table + " (";

    String pk = "";
    for (int i = 0; i < fields.size(); i++) {
      String nl = "";
      Element field = (Element)fields.get(i);
      String name = field.elementText("name");
      String size = field.elementText("size");
      String type = toLocalType(field.elementText("type").toLowerCase(), size);

      String option = field.elementText("option");
      String def = field.elementText("default");

      if ("1".equals(option.substring(1, 2))) {
        nl = " not null";
      }
      if (def != null) {
        nl = nl + " default " + def;
      }
      if ("1".equals(option.substring(0, 1))) {
        pk = "primary key (" + name + "),";
        nl = nl + " auto_increment";
      }

      sql = sql + name + " " + type + nl + ",";
    }
    sql = sql + pk;
    sql = sql.substring(0, sql.length() - 1) + ") ENGINE = MYISAM;";
    return sql;
  }

  protected Object getFuncValue(String name, String value)
  {
    if ("time".equals(name)) {
      Date date = new Date(Long.parseLong(value));
      return "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "'";
    }

    return super.getFuncValue(name, value);
  }

  public String[] getFuncName()
  {
    String[] name = { "time" };
    return name;
  }

  public String getFieldValue(int fieldType, Object fieldValue)
  {
    if (93 == fieldType) {
      Timestamp value = (Timestamp)fieldValue;
      return "time(" + value.getTime() + ")";
    }
    return super.getFieldValue(fieldType, fieldValue);
  }

  public String getDropSQL(String table)
  {
    String sql = "drop table if exists " + table + ";\n" + "!-->";
    return sql;
  }

  public String getSaasCreateSQL(Element action, int userid, int siteid)
  {
    String table = getSaasTableName(action.elementText("table"), userid, siteid);
    List fields = action.elements("field");

    String sql = getDropSQL(table) + "!-->";
    sql = sql + "create table " + table + " (";

    String pk = "";
    for (int i = 0; i < fields.size(); i++) {
      String nl = "";
      Element field = (Element)fields.get(i);
      String name = field.elementText("name");
      String size = field.elementText("size");
      String type = toLocalType(field.elementText("type").toLowerCase(), size);

      String option = field.elementText("option");
      String def = field.elementText("default");

      if ("1".equals(option.substring(1, 2))) {
        nl = " not null";
      }
      if (def != null) {
        nl = nl + " default " + def;
      }
      if ("1".equals(option.substring(0, 1))) {
        pk = "primary key (" + name + "),";
        nl = nl + " auto_increment";
      }

      sql = sql + name + " " + type + nl + ",";
    }
    sql = sql + pk;
    sql = sql.substring(0, sql.length() - 1) + ") ENGINE = MYISAM;";
    return sql;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.MySQLSolution
 * JD-Core Version:    0.6.1
 */