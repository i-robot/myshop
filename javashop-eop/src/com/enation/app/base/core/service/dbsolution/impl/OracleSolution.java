package com.enation.app.base.core.service.dbsolution.impl;

import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import oracle.sql.TIMESTAMP;
import org.dom4j.Element;

public class OracleSolution extends DBSolution
{
  public String toLocalType(String type, String size)
  {
    if ("int".equals(type)) {
      if ("1".equals(size))
        return "NUMBER(2)";
      return "NUMBER(" + size + ")";
    }
    if ("memo".equals(type)) {
      return "CLOB";
    }
    if ("datetime".equals(type)) {
      return "TIMESTAMP";
    }
    if ("long".equals(type)) {
      return "NUMBER(20)";
    }
    if ("decimal".equals(type)) {
      return "NUMBER(20,2)";
    }
    return type.toUpperCase() + "(" + size + ")";
  }

  private String getBlockSQL(String sql) {
    return "BEGIN\n\tEXECUTE IMMEDIATE '" + sql + "';\n" + "\tEXCEPTION WHEN OTHERS THEN NULL;\n" + "END;" + "!-->" + "\n";
  }

  private String getTriggerSQL(String table, String field)
  {
    String trigger = getBlockSQL(new StringBuilder().append("DROP TRIGGER TIB_").append(table).toString()) + "CREATE TRIGGER \"TIB_" + table + "\" BEFORE INSERT\n" + "\tON " + table + " FOR EACH ROW\n" + "\tDECLARE\n" + "\tINTEGRITY_ERROR  EXCEPTION;\n" + "\tERRNO            INTEGER;\n" + "\tERRMSG           CHAR(200);\n" + "\tMAXID\t\t\tINTEGER;\n" + "BEGIN\n" + "\tIF :NEW." + field + " IS NULL THEN\n" + "\t\tSELECT MAX(" + field + ") INTO MAXID FROM " + table + ";\n" + "\t\tSELECT S_" + table + ".NEXTVAL INTO :NEW." + field + " FROM DUAL;\n" + "\t\tIF MAXID>:NEW." + field + " THEN\n" + "\t\t\tLOOP\n" + "\t\t\t\tSELECT S_" + table + ".NEXTVAL INTO :NEW." + field + " FROM DUAL;\n" + "\t\t\t\tEXIT WHEN MAXID<:NEW." + field + ";\n" + "\t\t\tEND LOOP;\n" + "\t\tEND IF;\n" + "\tEND IF;\n" + "EXCEPTION\n" + "\tWHEN INTEGRITY_ERROR THEN\n" + "\t\tRAISE_APPLICATION_ERROR(ERRNO, ERRMSG);\n" + "END;";

    return trigger;
  }

  public String getCreateSQL(Element action)
  {
    String table = getTableName(action.elementText("table").toUpperCase());
    List fields = action.elements("field");

    String sql = getDropSQL(table) + "!-->";

    sql = sql + "CREATE TABLE " + table + " (";

    String sequence = "";
    String key = "";
    for (int i = 0; i < fields.size(); i++) {
      String nl = "";
      Element field = (Element)fields.get(i);
      String name = "\"" + field.elementText("name").toUpperCase() + "\"";
      String size = field.elementText("size");
      String type = toLocalType(field.elementText("type").toLowerCase(), size);

      String option = field.elementText("option");
      String def = field.elementText("default");

      if ("1".equals(option.substring(0, 1))) {
        sequence = getBlockSQL("DROP SEQUENCE S_" + table);
        sequence = sequence + "CREATE SEQUENCE S_" + table + "!-->" + "\n";

        sequence = sequence + getTriggerSQL(table, name);

        key = "CONSTRAINT PK_" + table + " PRIMARY KEY (" + name + ")";
      }

      if ("1".equals(option.substring(1, 2))) {
        nl = " NOT NULL";
      }
      if (def != null) {
        nl = " default " + def + nl;
      }
      sql = sql + name + " " + type + nl + ",";
    }

    if (!StringUtil.isEmpty(key)) {
      sql = sql + key + ")" + "!-->" + "\n";
    }
    else
      sql = sql.substring(0, sql.length() - 1) + ")" + "!-->" + "\n";
    sql = sql + sequence;
    return sql;
  }

  public void setPrefix(String prefix)
  {
    this.prefix = prefix.toUpperCase();
  }

  protected String getConvertedSQL(String sql)
  {
    sql = sql.replaceAll("&", "&'||'");
    System.out.println(sql);
    return sql;
  }

  protected Object getFuncValue(String name, String value)
  {
    if ("time".equals(name)) {
      Date date = new Date(Long.parseLong(value));
      return "TIMESTAMP'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "'";
    }

    return super.getFuncValue(name, value);
  }

  public String getFieldValue(int fieldType, Object fieldValue)
  {
    Object value = fieldValue;
    if ((fieldValue instanceof Clob)) {
      try {
        Clob clob = (Clob)fieldValue;
        Reader inStream = clob.getCharacterStream();
        char[] buf = new char[(int)clob.length()];
        inStream.read(buf);
        value = new String(buf);
        inStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if ((fieldValue instanceof TIMESTAMP)) {
      TIMESTAMP time = (TIMESTAMP)fieldValue;
      try {
        value = "time(" + time.dateValue().getTime() + ")";
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return super.getFieldValue(fieldType, value);
  }

  public String[] getFuncName()
  {
    String[] name = { "time" };
    return name;
  }

  public String getDropSQL(String table)
  {
    table = table.toUpperCase();
    String sql = getBlockSQL(new StringBuilder().append("DROP TRIGGER TIB_").append(table).toString()) + getBlockSQL(new StringBuilder().append("DROP TABLE ").append(table).toString()) + getBlockSQL(new StringBuilder().append("DROP SEQUENCE S_").append(table).toString());

    return sql;
  }

  public String getSaasCreateSQL(Element action, int userid, int siteid)
  {
    String table = getSaasTableName(action.elementText("table").toUpperCase(), userid, siteid);
    List fields = action.elements("field");

    String sql = getDropSQL(table) + "!-->";

    sql = sql + "CREATE TABLE " + table + " (";

    String sequence = "";
    String key = "";
    for (int i = 0; i < fields.size(); i++) {
      String nl = "";
      Element field = (Element)fields.get(i);
      String name = "\"" + field.elementText("name").toUpperCase() + "\"";
      String size = field.elementText("size");
      String type = toLocalType(field.elementText("type").toLowerCase(), size);

      String option = field.elementText("option");
      String def = field.elementText("default");

      if ("1".equals(option.substring(0, 1))) {
        sequence = getBlockSQL("DROP SEQUENCE S_" + table);
        sequence = sequence + "CREATE SEQUENCE S_" + table + "!-->" + "\n";

        sequence = sequence + getTriggerSQL(table, name);

        key = "CONSTRAINT PK_" + table + " PRIMARY KEY (" + name + ")";
      }

      if ("1".equals(option.substring(1, 2))) {
        nl = " NOT NULL";
      }
      if (def != null) {
        nl = " default " + def + nl;
      }
      sql = sql + name + " " + type + nl + ",";
    }

    if (!StringUtil.isEmpty(key)) {
      sql = sql + key + ")" + "!-->" + "\n";
    }
    else
      sql = sql.substring(0, sql.length() - 1) + ")" + "!-->" + "\n";
    sql = sql + sequence;
    return sql;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.OracleSolution
 * JD-Core Version:    0.6.1
 */