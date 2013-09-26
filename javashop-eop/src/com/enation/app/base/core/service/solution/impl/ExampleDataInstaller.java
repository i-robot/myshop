package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.app.base.core.service.solution.InstallUtil;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.ISqlFileExecutor;
import com.enation.framework.util.FileUtil;
import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;

public class ExampleDataInstaller
  implements IInstaller
{
  private ISqlFileExecutor sqlFileExecutor;
  protected final Logger logger = Logger.getLogger(getClass());
  protected String eopsiteSQL;

  public String parseConst(String content)
  {
    this.eopsiteSQL = "";
    Map constMap = new HashMap();
    char[] buf = new char[content.length()];
    content.getChars(0, content.length(), buf, 0);
    CharArrayReader in = new CharArrayReader(buf);
    BufferedReader reader = new BufferedReader(in);
    Iterator it;
    try
    {
      while (true)
      {
        String line = reader.readLine();
        if (line == null)
          break;
        if (line.startsWith("CONST")) {
          String[] value = StringUtils.split(line.substring(line.indexOf(33)), "=");
          constMap.put(value[0], value[1]);
          content = content.replaceFirst(line + "\n", "");
        } else if (line.startsWith("EOPSITE")) {
          this.eopsiteSQL = ("\n" + line.replaceFirst("EOPSITE", "update eop_site"));
          content = content.replaceFirst(line + "\n", "");
        }
        else {
          break;
        }
      }
    }
    catch (Exception e) {
      Set keys = constMap.keySet();
      for (it = keys.iterator(); it.hasNext(); ) {
        String key = (String)it.next();
        String value = (String)constMap.get(key);
        content = content.replaceAll(key, value);
        this.eopsiteSQL = this.eopsiteSQL.replaceAll(key, value);
      }
    }
    return content;
  }

  protected String parseUserSiteID(String content) {
    if ("2".equals(EopSetting.RUNMODE)) {
      EopSite site = EopContext.getContext().getCurrentSite();
      content = content.replaceAll("<userid>", String.valueOf(site.getUserid()));
      content = content.replaceAll("<siteid>", String.valueOf(site.getId()));
    } else {
      content = content.replaceAll("_<userid>", "");
      content = content.replaceAll("_<siteid>", "");
      content = content.replaceAll("/user/<userid>/<siteid>", "");
      content = content.replaceAll("<userid>", "1");
      content = content.replaceAll("<siteid>", "1");
    }
    return content;
  }

  private void anyDBInstall(String dataSqlPath)
    throws Exception
  {
    if (!DBSolutionFactory.dbImport(dataSqlPath, ""))
      throw new RuntimeException("安装示例数据出错...");
  }

  private String getDataSqlPath(String productId) {
    String dataSqlPath = "";
    if (EopSetting.DBTYPE.equals("1"))
      dataSqlPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/example_data_mysql.sql";
    else if (EopSetting.DBTYPE.equals("2"))
      dataSqlPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/example_data_oracle.sql";
    else if (EopSetting.DBTYPE.equals("3")) {
      dataSqlPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/example_data_mssql.sql";
    }
    return dataSqlPath;
  }

  private void loggerPrint(String text) {
    if (this.logger.isDebugEnabled())
      this.logger.debug(text);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void install(String productId, Node fragment)
  {
    boolean xmlData = true;
    String dataSqlPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/example_data.xml";
    try {
      File xmlFile = new File(dataSqlPath);
      if (!xmlFile.exists()) {
        xmlData = false;
        dataSqlPath = getDataSqlPath(productId);
      }

      loggerPrint("安装datasqlPath:" + dataSqlPath);

      if (!"base".equals(productId))
        InstallUtil.putMessaage("正在安装示例数据，可能要花费较长时间，请稍后 <img src='resource/com/enation/app/saas/component/widget/product/loading.gif'");
      else {
        InstallUtil.putMessaage("正在安装基础数据...");
      }

      if (xmlData) {
        anyDBInstall(dataSqlPath);
      }
      else if (new File(dataSqlPath).exists())
      {
        String content = FileUtil.read(dataSqlPath, "UTF-8");

        content = parseConst(content);

        content = parseUserSiteID(content);
        content = filter(content);
        content = content + parseUserSiteID(this.eopsiteSQL);

        this.sqlFileExecutor.execute(content);
      }
      else {
        System.out.println(dataSqlPath + " not exist");
      }

      loggerPrint("示例数据安装完毕");

      FileUtil.copyFolder(EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/attachment/", EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/attachment/");

      if (!"base".equals(productId))
        FileUtil.copyFile(EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/init.sql", EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/init.sql");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.debug(e.getMessage(), e);
      throw new RuntimeException("安装示例数据出错...");
    }
  }

  private String filter(String input)
  {
    Pattern pattern = Pattern.compile("delete\\s?.*?\\s?;", 2);
    Matcher matcher = pattern.matcher(input);
    input = matcher.replaceAll("");
    pattern = Pattern.compile("truncate\\s?.*?\\s?;", 2);
    matcher = pattern.matcher(input);
    input = matcher.replaceAll("");
    pattern = Pattern.compile("drop\\s?.*?\\s?;", 2);
    matcher = pattern.matcher(input);
    input = matcher.replaceAll("");
    pattern = Pattern.compile("(delete|drop|truncate|insert|update)\\s?eop_.*?;", 2);
    matcher = pattern.matcher(input);
    input = matcher.replaceAll("");
    return input;
  }

  public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
    this.sqlFileExecutor = sqlFileExecutor;
  }

  public static void main(String[] args) {
    String content = FileUtil.read("D:/Works/Trunk/javamall/WebContent/products/eopsaler/example_data_mysql.sql", "UTF-8");

    System.out.println(content.substring(0, 50));
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.ExampleDataInstaller
 * JD-Core Version:    0.6.1
 */