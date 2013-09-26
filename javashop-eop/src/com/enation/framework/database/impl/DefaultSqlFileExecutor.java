package com.enation.framework.database.impl;

import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.ISqlFileExecutor;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public final class DefaultSqlFileExecutor
  implements ISqlFileExecutor
{
  private JdbcTemplate jdbcTemplate;
  protected final Logger logger = Logger.getLogger(getClass());

  public void execute(String sqlPath)
  {
    execute(sqlPath, false);
  }

  public void execute(String sqlPath, boolean exampleData)
  {
    String content;
    if (sqlPath.startsWith("file:")) {
      content = FileUtil.readFile(sqlPath.replaceAll("file:", ""));
    }
    else {
      content = sqlPath;
    }

    batchExecute(content, exampleData);
  }

  private void batchExecute(String content)
  {
    batchExecute(content, false);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  private void batchExecute(String content, boolean exampleData)
  {
    content = StringUtil.delSqlComment(content);
    content = content.replaceAll("\r", "");
    String spliter = ";\n";
    if ((EopSetting.DBTYPE.equals("2")) || (EopSetting.DBTYPE.equals("3"))) {
      if (EopSetting.DBTYPE.equals("2"))
        spliter = "\n/\n";
      else {
        spliter = "\ngo\n";
      }
    }
    String[] sql_ar = StringUtils.split(content, spliter);

    if ((EopSetting.DBTYPE.equals("3")) && 
      (sql_ar.length == 1)) {
      sql_ar = content.split(";\n");
    }

    if ((StringUtil.isEmpty(content)) || (sql_ar == null) || (sql_ar.length == 0)) return;

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("开始执行sql....");
    }

    try
    {
      for (int i = 0; i < sql_ar.length; i++) {
        String s = sql_ar[i];
        if (!StringUtil.isEmpty(s)) {
          s = s.trim().replaceAll("\n", " ");

          if (!s.startsWith("declare"))
            this.jdbcTemplate.execute(s);
        }
        else {
          System.out.println("is empty");
        }
      }
    } catch (RuntimeException e) {
      this.logger.error("执行sql出错", e.fillInStackTrace());
      throw e;
    }

    if (this.logger.isDebugEnabled())
      this.logger.debug("执行完成");
  }

  private String mysql_escape_string(String str)
  {
    if ((str == null) || (str.length() == 0))
      return str;
    str = str.replaceAll("'", "\\'");
    str = str.replaceAll("\"", "\\\"");
    return str;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public static void main(String[] args) {
    String str = "abcajfjf[user]fj;ksafj;sajfoiju[rule]rrifj[delete]sdjfdf";
    System.out.println(str.replaceAll("([)(.*)(])", "$1"));
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.impl.DefaultSqlFileExecutor
 * JD-Core Version:    0.6.1
 */