package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.service.IBatchSqlExecutor;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.ISqlFileExecutor;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class BatchSqlExecutor
  implements IBatchSqlExecutor
{
  protected final Logger logger = Logger.getLogger(getClass());
  private ISiteManager siteManager;
  private ISqlFileExecutor sqlFileExecutor;
  private IDaoSupport daoSupport;

  public void exeucte(String sql)
  {
    String baseSql = sql.replaceAll("_<userid>", "");
    baseSql = baseSql.replaceAll("_<siteid>", "");
    this.sqlFileExecutor.execute(baseSql);

    sql = getSiteUpdateSql(sql);

    List siteList = this.siteManager.list();

    int i = 0; for (int len = siteList.size(); i < len; i++)
    {
      Map site = (Map)siteList.get(i);
      String exesql = sql.replaceAll("<userid>", site.get("userid").toString());
      exesql = exesql.replaceAll("<siteid>", site.get("id").toString());
      try {
        this.sqlFileExecutor.execute(exesql);
      } catch (RuntimeException e) {
        this.logger.error("为站点userid[" + site.get("userid") + "]siteid[" + site.get("id") + "]执行sql出错，跳过...");
      }
    }
  }

  private String getSiteUpdateSql(String content)
  {
    StringBuffer sql = new StringBuffer();

    content = StringUtil.delSqlComment(content);

    content = content.replaceAll("\r", "");
    String[] sql_ar = content.split(";\n");

    for (String s : sql_ar) {
      if ((s != null) && ((s.indexOf("<userid>") > 0) || (s.indexOf("<siteid>") > 0))) {
        sql.append(s + ";\n");
      }
    }
    return sql.toString();
  }

  public static void main(String[] args) {
    String content = FileUtil.read("D:/work/eopnew/docs/version/2.2/update.sql", "UTF-8");
  }

  public void executeForCms(String sql)
  {
    List siteList = this.siteManager.list();
    int i = 0; for (int len = siteList.size(); i < len; i++) {
      Map site = (Map)siteList.get(i);
      String userid = site.get("userid").toString();
      String siteid = site.get("id").toString();
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("为站点userid[" + userid + "]siteid[" + siteid + "]执行cms sql ");
      }
      executeForCms(sql, userid, siteid);
    }
  }

  private void executeForCms(String sql, String userid, String siteid)
  {
    try
    {
      List<Map> modelList = listCmsDataModel(userid, siteid);
      for (Map model : modelList) {
        String tablename = model.get("english_name").toString();

        if ("2".equals(EopSetting.RUNMODE)) {
          String exesql = sql.replaceAll("<tbname>", "es_" + tablename + "_" + userid + "_" + siteid);
          this.sqlFileExecutor.execute(exesql);
        } else {
          String exesql = sql.replaceAll("<tbname>", "es_" + tablename);
          if (this.logger.isDebugEnabled()) {
            this.logger.debug("执行sql[" + exesql + "] ");
          }
          this.sqlFileExecutor.execute(exesql);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<Map> listCmsDataModel(String userid, String siteid)
  {
    try
    {
      if ("2".equals(EopSetting.RUNMODE)) {
        String sql = "select english_name from es_data_model_" + userid + "_" + siteid;
        return this.daoSupport.queryForList(sql, new Object[0]);
      }
      String sql = "select english_name from es_data_model";
      return this.daoSupport.queryForList(sql, new Object[0]);
    }
    catch (Exception e) {
      e.printStackTrace();
    }return new ArrayList();
  }

  public ISiteManager getSiteManager()
  {
    return this.siteManager;
  }
  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }
  public ISqlFileExecutor getSqlFileExecutor() {
    return this.sqlFileExecutor;
  }
  public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
    this.sqlFileExecutor = sqlFileExecutor;
  }

  public IDaoSupport getDaoSupport() {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport daoSupport) {
    this.daoSupport = daoSupport;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.BatchSqlExecutor
 * JD-Core Version:    0.6.1
 */