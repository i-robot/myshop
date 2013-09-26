package com.enation.app.base.core.service.dbsolution.impl;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DBExporter extends DBPorter
{
  private String prefix = "";
  private String beginLine = "\t";
  private String endLine = "\n";

  public DBExporter(DBSolution solution) {
    super(solution);
  }

  private String beginLine(int count)
  {
    String result = "";
    for (int i = 0; i < count; i++) {
      result = new StringBuilder().append(result).append(this.beginLine).toString();
    }
    return result;
  }

  protected String getFieldOption(ResultSetMetaData rsmd, int index)
    throws SQLException
  {
    String auto = "0";
    String nullable = "0";

    if (rsmd.isAutoIncrement(index))
      auto = "1";
    if (rsmd.isNullable(index) == 0) {
      nullable = "1";
    }
    return new StringBuilder().append(auto).append(nullable).toString();
  }

  private void createAction(String table, StringBuilder xmlFile, String command)
  {
    xmlFile.append(new StringBuilder().append(this.beginLine).append("<action>").append(this.endLine).toString());
    xmlFile.append(new StringBuilder().append(beginLine(2)).append("<command>").append(command).append("</command>").append(this.endLine).toString());

    xmlFile.append(new StringBuilder().append(beginLine(2)).append("<table>").append(table.toLowerCase()).append("</table>").append(this.endLine).toString());
  }

  private boolean createTableXml(String table, StringBuilder xmlFile)
  {
    createAction(table, xmlFile, "create");
    try
    {
      createFieldXml(table, xmlFile);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }

    xmlFile.append(new StringBuilder().append(this.beginLine).append("</action>").append(this.endLine).toString());
    return true;
  }

  private boolean createTableXml(String table, int userid, int siteid, StringBuilder xmlFile) {
    createAction(table, xmlFile, "create");
    try
    {
      createFieldXml(new StringBuilder().append(table).append("_").append(userid).append("_").append(siteid).toString(), xmlFile);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }

    xmlFile.append(new StringBuilder().append(this.beginLine).append("</action>").append(this.endLine).toString());
    return true;
  }

  protected String getFieldTypeName(int type)
  {
    String result = "";
    switch (type) {
    case 4:
    case 5:
      result = "int";
      break;
    case 12:
      result = "varchar";
      break;
    case -16:
    case -1:
    case 2005:
      result = "memo";
      break;
    case -5:
      result = "long";
      break;
    case 3:
      result = "decimal";
      break;
    case 91:
      result = "date";
      break;
    case 93:
      result = "datetime";
      break;
    default:
      result = "varchar";
    }
    return result;
  }

  private void createFieldXml(String table, StringBuilder xmlFile)
    throws SQLException
  {
    Statement st = this.solution.conn.createStatement();
    ResultSet rs = st.executeQuery(new StringBuilder().append("select * from ").append(this.prefix).append(table).toString());

    DatabaseMetaData metaData = this.solution.conn.getMetaData();
    Map columns = new HashMap();
    ResultSet mdrs = metaData.getColumns(null, null, table.toUpperCase(), "%");

    while (mdrs.next()) {
      columns.put(mdrs.getString("COLUMN_NAME"), mdrs.getString("COLUMN_DEF"));
    }

    ResultSetMetaData rsmd = rs.getMetaData();
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      String columnName = rsmd.getColumnName(i);

      xmlFile.append(new StringBuilder().append(beginLine(2)).append("<field>").toString());

      xmlFile.append(new StringBuilder().append("<name>").append(rsmd.getColumnName(i).toLowerCase()).append("</name>").toString());
      xmlFile.append(new StringBuilder().append("<type>").append(getFieldTypeName(rsmd.getColumnType(i))).append("</type>").toString());

      xmlFile.append(new StringBuilder().append("<size>").append(rsmd.getPrecision(i)).append("</size>").toString());
      xmlFile.append(new StringBuilder().append("<option>").append(getFieldOption(rsmd, i)).append("</option>").toString());

      if (columns.get(columnName) != null) {
        String value = this.solution.getFieldValue(rsmd.getColumnType(i), columns.get(columnName));
        value = value.replaceAll("\\(", "");
        value = value.replaceAll("\\)", "");
        xmlFile.append(new StringBuilder().append("<default>").append(value).append("</default>").toString());
      }
      xmlFile.append(new StringBuilder().append("</field>").append(this.endLine).toString());
    }
  }

  private boolean saveDocument(String xml, String text)
  {
    try
    {
      FileWriter file = new FileWriter(xml);
      file.write(text);
      file.close();
      return true; } catch (Exception e) {
    }
    return false;
  }

  private boolean insertDataXml(String table, StringBuilder xmlFile)
  {
    try
    {
      Statement st = this.solution.conn.createStatement();
      ResultSet rs = st.executeQuery(new StringBuilder().append("select * from ").append(table).toString());
      ResultSetMetaData rsmd = rs.getMetaData();

      while (rs.next()) {
        String fields = "";
        String values = "";
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          Object value = rs.getObject(i);
          if (value != null) {
            fields = new StringBuilder().append(fields).append(rsmd.getColumnName(i)).append(",").toString();
            values = new StringBuilder().append(values).append(this.solution.getFieldValue(rsmd.getColumnType(i), value)).append(",").toString();
          }
        }
        createAction(table, xmlFile, "insert");
        xmlFile.append(new StringBuilder().append(beginLine(2)).append("<fields>").append(fields.substring(0, fields.length() - 1).toLowerCase()).append("</fields>").append(this.endLine).toString());

        xmlFile.append(new StringBuilder().append(beginLine(2)).append("<values>").append(this.solution.encode(values.substring(0, values.length() - 1))).append("</values>").append(this.endLine).toString());

        xmlFile.append(new StringBuilder().append(this.beginLine).append("</action>").append(this.endLine).toString());
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  private boolean insertDataXml(String table, int userid, int siteid, StringBuilder xmlFile) {
    try {
      Statement st = this.solution.conn.createStatement();
      ResultSet rs = st.executeQuery(new StringBuilder().append("select * from ").append(table).append("_").append(userid).append("_").append(siteid).toString());
      ResultSetMetaData rsmd = rs.getMetaData();

      while (rs.next()) {
        String fields = "";
        String values = "";
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          Object value = rs.getObject(i);
          if (value != null) {
            fields = new StringBuilder().append(fields).append(rsmd.getColumnName(i)).append(",").toString();
            values = new StringBuilder().append(values).append(this.solution.getFieldValue(rsmd.getColumnType(i), value)).append(",").toString();
          }
        }
        createAction(table, xmlFile, "insert");
        xmlFile.append(new StringBuilder().append(beginLine(2)).append("<fields>").append(fields.substring(0, fields.length() - 1).toLowerCase()).append("</fields>").append(this.endLine).toString());

        xmlFile.append(new StringBuilder().append(beginLine(2)).append("<values>").append(this.solution.encode(values.substring(0, values.length() - 1))).append("</values>").append(this.endLine).toString());

        xmlFile.append(new StringBuilder().append(this.beginLine).append("</action>").append(this.endLine).toString());
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  public String doExport(String prefix, String[] tables, boolean dataOnly)
  {
    StringBuilder xml = new StringBuilder();

    if (!dataOnly) {
      for (int i = 0; i < tables.length; i++) {
        if (tables[i].toUpperCase().startsWith("EOP_"))
          createTableXml(tables[i], xml);
        else
          createTableXml(new StringBuilder().append(prefix).append(tables[i]).toString(), xml);
      }
    }
    for (int i = 0; i < tables.length; i++) {
      if (tables[i].toUpperCase().startsWith("EOP_"))
        insertDataXml(tables[i], xml);
      else
        insertDataXml(new StringBuilder().append(prefix).append(tables[i]).toString(), xml);
    }
    return xml.toString();
  }

  public String doExport(String prefix, String[] tables, boolean dataOnly, int userid, int siteid) {
    StringBuilder xml = new StringBuilder();

    if (!dataOnly) {
      for (int i = 0; i < tables.length; i++) {
        if (tables[i].toUpperCase().startsWith("EOP_"))
          createTableXml(tables[i], xml);
        else
          createTableXml(new StringBuilder().append(prefix).append(tables[i]).toString(), userid, siteid, xml);
      }
    }
    for (int i = 0; i < tables.length; i++) {
      if (tables[i].toUpperCase().startsWith("EOP_"))
        insertDataXml(tables[i], xml);
      else
        insertDataXml(new StringBuilder().append(prefix).append(tables[i]).toString(), userid, siteid, xml);
    }
    return xml.toString();
  }

  public boolean doExport(String prefix, String[] tables, String xml)
  {
    this.prefix = prefix;

    StringBuilder xmlFile = new StringBuilder();
    xmlFile.append(new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(this.endLine).toString());
    xmlFile.append(new StringBuilder().append("<dbsolution>").append(this.endLine).toString());
    xmlFile.append(doExport(prefix, tables, false));
    xmlFile.append(new StringBuilder().append("</dbsolution>").append(this.endLine).toString());

    return saveDocument(xml, xmlFile.toString());
  }

  public boolean doExport(String prefix, String[] tables, String xml, int userid, int siteid) {
    this.prefix = prefix;

    StringBuilder xmlFile = new StringBuilder();
    xmlFile.append(new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(this.endLine).toString());
    xmlFile.append(new StringBuilder().append("<dbsolution>").append(this.endLine).toString());
    xmlFile.append(doExport(prefix, tables, false, userid, siteid));
    xmlFile.append(new StringBuilder().append("</dbsolution>").append(this.endLine).toString());

    return saveDocument(xml, xmlFile.toString());
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.DBExporter
 * JD-Core Version:    0.6.1
 */