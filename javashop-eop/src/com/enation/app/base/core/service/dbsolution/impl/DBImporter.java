package com.enation.app.base.core.service.dbsolution.impl;

import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DBImporter extends DBPorter
{
  private Document xmlDoc;

  public DBImporter(DBSolution solution)
  {
    super(solution);
  }

  private Document loadDocument(String xmlFile)
    throws DocumentException
  {
    Document document = null;
    SAXReader saxReader = new SAXReader();
    File file = new File(xmlFile);
    if (file.exists())
      document = saxReader.read(new File(xmlFile));
    return document;
  }

  private List<Object> prepareValue(String values) {
    List objects = new ArrayList();
    String[] value = values.split(",");
    for (int i = 0; i < value.length; i++) {
      objects.add(this.solution.getFuncValue(this.solution.decodeValue(value[i].replaceAll("'", ""))));
    }

    return objects;
  }

  private void doExecute(Statement state, String sql) {
    try {
      if (sql != null)
        state.execute(sql);
    }
    catch (SQLException e)
    {
    }
  }

  private boolean doInsert(Element action) {
    String table = this.solution.getTableName(action.elementText("table"));
    String fields = action.elementText("fields");
    String values = action.elementText("values");

    List objects = prepareValue(values);

    String sql = "insert into " + table + " (" + fields + ") values (";
    String[] value = values.split(",");
    for (int i = 0; i < value.length; i++) {
      sql = sql + "?,";
    }
    sql = sql.substring(0, sql.length() - 1) + ")";
    try
    {
      Statement state = this.solution.conn.createStatement();
      PreparedStatement ps = this.solution.conn.prepareStatement(sql);

      for (int i = 1; i <= value.length; i++) {
        ps.setObject(i, objects.get(i - 1));
      }

      if (this.solution.beforeInsert(table, fields, values)) {
        doExecute(state, this.solution.getSqlExchange());
        ps.execute();
        this.solution.afterInsert(table, fields, values);
        doExecute(state, this.solution.getSqlExchange());
        ps.close();
        state.close();
      } else {
        System.out.println("beforeInsert返回false，insert被阻止！");
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("出错语句：" + sql);
      System.out.println("出错值：" + values);
      return false;
    }
    return true;
  }
  private boolean doTruncate(Element action) {
    String table = this.solution.getTableName(action.elementText("table"));
    String sql = "";
    sql = sql + "truncate table " + table;
    return this.solution.executeSqls(sql);
  }
  private boolean doDrop(Element action) {
    String table = this.solution.getTableName(action.elementText("table"));
    String sql = this.solution.getDropSQL(table);
    return this.solution.executeSqls(sql);
  }

  private boolean doCreate(Element action) {
    String sql = this.solution.getCreateSQL(action);

    return this.solution.executeSqls(sql);
  }

  private boolean doIndex(Element action) {
    return doIndex(action, 0, 0);
  }
  private boolean doUnindex(Element action) {
    return doUnindex(action, 0, 0);
  }

  private boolean doInsert(Element action, int userid, int siteid) {
    String table = this.solution.getSaasTableName(action.elementText("table"), userid, siteid);
    String fields = action.elementText("fields");
    String values = action.elementText("values");

    List objects = prepareValue(values);

    String sql = "insert into " + table + " (" + fields + ") values (";
    String[] value = values.split(",");
    for (int i = 0; i < value.length; i++) {
      sql = sql + "?,";
    }
    sql = sql.substring(0, sql.length() - 1) + ")";
    try
    {
      Statement state = this.solution.conn.createStatement();
      PreparedStatement ps = this.solution.conn.prepareStatement(sql);

      for (int i = 1; i <= value.length; i++) {
        ps.setObject(i, objects.get(i - 1));
      }

      if (this.solution.beforeInsert(table, fields, values)) {
        doExecute(state, this.solution.getSqlExchange());
        ps.execute();
        this.solution.afterInsert(table, fields, values);
        doExecute(state, this.solution.getSqlExchange());
        ps.close();
        state.close();
      } else {
        System.out.println("beforeInsert返回false，insert被阻止！");
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("出错语句：" + sql);
      System.out.println("出错值：" + values);
      return false;
    }
    return true;
  }

  private boolean doTruncate(Element action, int userid, int siteid) {
    String table = this.solution.getSaasTableName(action.elementText("table"), userid, siteid);
    String sql = "";
    sql = sql + "truncate table " + table;
    return this.solution.executeSqls(sql);
  }

  private boolean doDrop(Element action, int userid, int siteid) {
    String table = this.solution.getSaasTableName(action.elementText("table"), userid, siteid);
    String sql = this.solution.getDropSQL(table);
    return this.solution.executeSqls(sql);
  }

  private boolean doCreate(Element action, int userid, int siteid) {
    String sql = this.solution.getSaasCreateSQL(action, userid, siteid);
    return this.solution.executeSqls(sql);
  }

  private boolean doIndex(Element action, int userid, int siteid)
  {
    String sql = "create index ";
    String table;
    if ((userid == 0) && (siteid == 0))
      table = this.solution.getTableName(action.elementText("table"));
    else
      table = this.solution.getSaasTableName(action.elementText("table"), userid, siteid);
    List fields = action.elements("field");
    String field = " (";
    String name = "_";
    int i = 0; for (int len = fields.size(); i < len; i++) {
      Element element = (Element)fields.get(i);
      field = field + element.elementText("name") + ",";
      name = name + element.elementText("name") + "_";
    }
    field = field.substring(0, field.length() - 1) + ")";
    name = name.substring(0, name.length() - 1);

    sql = sql + "idx" + name + " on " + table + field;
    return this.solution.executeSqls(sql);
  }

  private boolean doAlter(Element action, int userid, int siteid)
  {
    try
    {
      String sql = "";
      String table;
      if ((userid == 0) && (siteid == 0))
        table = this.solution.getTableName(action.elementText("table"));
      else {
        table = this.solution.getSaasTableName(action.elementText("table"), userid, siteid);
      }
      List fields = action.elements("field");
      int i = 0; for (int len = fields.size(); i < len; i++) {
        Element element = (Element)fields.get(i);
        String type = element.attributeValue("type");
        String name = element.elementText("name");
        String size = element.elementText("size");

        if (i != 0) {
          sql = sql + ",";
        }
        if ("add".equals(type)) {
          String datatype = element.elementText("type");

          if (EopSetting.DBTYPE.equals("2"))
            sql = sql + " add  " + name + " ";
          else {
            sql = sql + " add column " + name + " ";
          }

          sql = sql + this.solution.toLocalType(datatype, size);

          String def = element.elementText("default");
          if (!StringUtil.isEmpty(def)) {
            sql = sql + " default " + def;
          }
        }

        if ("drop".equals(type)) {
          sql = sql + " drop column " + name;
        }
      }

      sql = "alter table " + table + " " + sql;
      this.solution.executeSqls(sql);
      return true;
    } catch (RuntimeException e) {
      e.printStackTrace();
    }return false;
  }

  private boolean doUnindex(Element action, int userid, int siteid)
  {
    System.out.println("unindex command,support later");
    return true;
  }

  private boolean doAction(Element action)
  {
    String command = action.elementText("command").toLowerCase();
    if ("create".equals(command))
      return doCreate(action);
    if ("insert".equals(command))
      return doInsert(action);
    if ("drop".equals(command))
      return doDrop(action);
    if ("index".equals(command))
      return doIndex(action);
    if ("unindex".equals(command))
      return doUnindex(action);
    if ("alter".equals(command))
      return doAlter(action, 0, 0);
    if ("truncate".equals(command)) {
      return doTruncate(action);
    }
    return true;
  }

  private boolean doSaasAction(Element action, int userid, int siteid) {
    String command = action.elementText("command").toLowerCase();
    if ("create".equals(command))
      return doCreate(action, userid, siteid);
    if ("insert".equals(command))
      return doInsert(action, userid, siteid);
    if ("drop".equals(command))
      return doDrop(action, userid, siteid);
    if ("index".equals(command))
      return doIndex(action, userid, siteid);
    if ("unindex".equals(command))
      return doUnindex(action, userid, siteid);
    if ("alter".equals(command))
      return doAlter(action, userid, siteid);
    if ("truncate".equals(command)) {
      return doTruncate(action, userid, siteid);
    }
    return true;
  }

  public boolean doImport(String xml)
  {
    this.solution.beforeImport();
    try {
      if (xml.startsWith("file:")) {
        xml = FileUtil.readFile(xml.replaceAll("file:", ""));
        this.xmlDoc = DocumentHelper.parseText(xml);
      } else if (xml.startsWith("<?xml version")) {
        this.xmlDoc = DocumentHelper.parseText(xml);
      }
      else {
        this.xmlDoc = loadDocument(xml);
      }
    } catch (DocumentException e) {
      e.printStackTrace();
      return false;
    }
    List<Element> actions = this.xmlDoc.getRootElement().elements("action");
    for (Element action : actions) {
      if (!doAction(action))
        return false;
    }
    this.solution.afterImport();
    return true;
  }

  public boolean doSaasImport(String xml, int userid, int siteid)
  {
    this.solution.beforeImport();
    try {
      if (xml.startsWith("file:")) {
        xml = FileUtil.readFile(xml.replaceAll("file:", ""));
        this.xmlDoc = DocumentHelper.parseText(xml);
      } else if (xml.startsWith("<?xml version")) {
        this.xmlDoc = DocumentHelper.parseText(xml);
      }
      else {
        this.xmlDoc = loadDocument(xml);
      }
    } catch (DocumentException e) {
      e.printStackTrace();
      return false;
    }
    List<Element> actions = this.xmlDoc.getRootElement().elements("action");
    for (Element action : actions) {
      if (!doSaasAction(action, userid, siteid))
        return false;
    }
    this.solution.afterImport();
    return true;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.DBImporter
 * JD-Core Version:    0.6.1
 */