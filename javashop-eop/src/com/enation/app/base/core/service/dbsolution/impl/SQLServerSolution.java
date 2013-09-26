package com.enation.app.base.core.service.dbsolution.impl;

import com.enation.eop.sdk.utils.DateUtil;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.dom4j.Element;

public class SQLServerSolution extends DBSolution
{
  protected boolean setIdentity(String table, boolean on)
  {
    this.sqlExchange = new StringBuilder().append("SET IDENTITY_INSERT ").append(table).append(" ").append(on ? "ON" : "OFF").toString();
    return true;
  }

  protected boolean beforeInsert(String table, String fields, String values)
  {
    return setIdentity(table, true);
  }

  protected void afterInsert(String table, String field, String values)
  {
    setIdentity(table, false);
  }

  public String toLocalType(String type, String size)
  {
    if ("int".equals(type)) {
      if ("1".equals(size)) {
        return "smallint";
      }
      return "int";
    }

    if ("memo".equals(type)) {
      return "text";
    }
    if ("datetime".equals(type)) {
      return "datetime";
    }
    if ("long".equals(type)) {
      return "bigint";
    }
    return new StringBuilder().append(type).append("(").append(size).append(")").toString();
  }

  public String getCreateSQL(Element action)
  {
    String table = getTableName(action.elementText("table"));
    List fields = action.elements("field");
    String sql = new StringBuilder().append(getDropSQL(table)).append("!-->").toString();
    sql = new StringBuilder().append(sql).append("create table ").append(table).append(" (").toString();

    String pk = "";
    for (int i = 0; i < fields.size(); i++) {
      String nl = "";
      Element field = (Element)fields.get(i);
      String name = new StringBuilder().append("[").append(field.elementText("name")).append("]").toString();
      String size = field.elementText("size");
      String type = toLocalType(field.elementText("type").toLowerCase(), size);

      String option = field.elementText("option");
      String def = field.elementText("default");

      if ("1".equals(option.substring(1, 2))) {
        nl = " not null";
      }
      if (def != null) {
        nl = new StringBuilder().append(nl).append(" default ").append(def).toString();
      }
      if ("1".equals(option.substring(0, 1))) {
        pk = new StringBuilder().append("constraint PK_").append(table.toUpperCase()).append(" primary key nonclustered (").append(name).append("),").toString();

        nl = new StringBuilder().append(nl).append(" identity").toString();
      }

      sql = new StringBuilder().append(sql).append(name).append(" ").append(type).append(nl).append(",").toString();
    }
    sql = new StringBuilder().append(sql).append(pk).toString();
    sql = new StringBuilder().append(sql.substring(0, sql.length() - 1)).append(")").toString();

    return sql;
  }

  public String[] getFuncName()
  {
    String[] name = { "time" };
    return name;
  }

  public String getFieldValue(int fieldType, Object fieldValue)
  {
    if ((fieldValue instanceof Timestamp)) {
      Date value = DateUtil.toDate(fieldValue.toString(), "yyyy-MM-dd HH:mm:ss.S");
      return new StringBuilder().append("time(").append(value.getTime()).append(")").toString();
    }
    return super.getFieldValue(fieldType, fieldValue);
  }

  public String getDropSQL(String table)
  {
    String sql = new StringBuilder().append("if exists (select 1 from sysobjects where id = object_id('").append(table).append("')").append("and type = 'U') drop table ").append(table).toString();

    return sql;
  }

  public String getSaasCreateSQL(Element action, int userid, int siteid)
  {
    String table = getSaasTableName(action.elementText("table"), userid, siteid);
    List fields = action.elements("field");
    String sql = new StringBuilder().append(getDropSQL(table)).append("!-->").toString();
    sql = new StringBuilder().append(sql).append("create table ").append(table).append(" (").toString();

    String pk = "";
    for (int i = 0; i < fields.size(); i++) {
      String nl = "";
      Element field = (Element)fields.get(i);
      String name = new StringBuilder().append("[").append(field.elementText("name")).append("]").toString();
      String size = field.elementText("size");
      String type = toLocalType(field.elementText("type").toLowerCase(), size);

      String option = field.elementText("option");
      String def = field.elementText("default");

      if ("1".equals(option.substring(1, 2))) {
        nl = " not null";
      }
      if (def != null) {
        nl = new StringBuilder().append(nl).append(" default ").append(def).toString();
      }
      if ("1".equals(option.substring(0, 1))) {
        pk = new StringBuilder().append("constraint PK_").append(table.toUpperCase()).append(" primary key nonclustered (").append(name).append("),").toString();

        nl = new StringBuilder().append(nl).append(" identity").toString();
      }

      sql = new StringBuilder().append(sql).append(name).append(" ").append(type).append(nl).append(",").toString();
    }
    sql = new StringBuilder().append(sql).append(pk).toString();
    sql = new StringBuilder().append(sql.substring(0, sql.length() - 1)).append(")").toString();

    return sql;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.SQLServerSolution
 * JD-Core Version:    0.6.1
 */