package com.enation.app.base.core.action;

import com.enation.app.base.core.service.EopInstallManager;
import com.enation.app.base.core.service.IDataSourceCreator;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.action.WWAction;
import com.enation.framework.component.IComponentManager;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class EopInstallAction extends WWAction
{
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcTemplate simpleJdbcTemplate;
  private EopInstallManager eopInstallManager;
  private DataSource dataSource;
  private IDataSourceCreator dataSourceCreator;
  private IComponentManager componentManager;
  private String dbhost;
  private String uname;
  private String pwd;
  private String dbtype;
  private String dbname;
  private String domain;
  private String productid;
  private String staticdomain;
  private String staticpath;
  private String solutionpath;
  private int resourcemode;
  private int devmodel;
  private String osVersion;
  private String javaVersion;

  public String execute()
  {
    return "step1";
  }

  public String step2()
  {
    return "step2";
  }

  public String step3()
  {
    saveEopParams();
    if ("mysql".equals(this.dbtype))
      saveMysqlDBParams();
    else if ("oracle".equals(this.dbtype))
      saveOracleDBParams();
    else if ("sqlserver".equals(this.dbtype)) {
      saveSQLServerDBParams();
    }
    Properties props = System.getProperties();
    this.osVersion = (props.getProperty("os.name") + "(" + props.getProperty("os.version") + ")");
    this.javaVersion = props.getProperty("java.version");
    return "step3";
  }

  public String installSuccess() {
    FileUtil.write(EopSetting.EOP_PATH + "/install/install.lock", "如果要重新安装，请删除此文件，并重新起动web容器");
    EopSetting.INSTALL_LOCK = "yes";
    if ("1".equals(EopSetting.RUNMODE)) {
      this.componentManager.startComponents();
    }
    return "success";
  }

  private void saveMysqlDBParams()
  {
    Properties props = new Properties();
    props.setProperty("jdbc.driverClassName", "com.mysql.jdbc.Driver");
    props.setProperty("jdbc.url", "jdbc:mysql://" + this.dbhost + "/" + this.dbname + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true");
    props.setProperty("jdbc.username", this.uname);
    props.setProperty("jdbc.password", this.pwd);
    saveProperties(props);
  }

  private void saveProperties(Properties props)
  {
    try
    {
      String path = StringUtil.getRootPath("config/jdbc.properties");
      File file = new File(path);

      props.store(new FileOutputStream(file), "jdbc.properties");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveOracleDBParams()
  {
    Properties props = new Properties();
    props.setProperty("jdbc.driverClassName", "oracle.jdbc.driver.OracleDriver");
    props.setProperty("jdbc.url", "jdbc:oracle:thin:@" + this.dbhost + ":" + this.dbname);
    props.setProperty("jdbc.username", this.uname);
    props.setProperty("jdbc.password", this.pwd);
    saveProperties(props);
  }

  private void saveSQLServerDBParams()
  {
    Properties props = new Properties();
    props.setProperty("jdbc.driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    props.setProperty("jdbc.url", "jdbc:sqlserver://" + this.dbhost + ";databaseName=" + this.dbname);
    props.setProperty("jdbc.username", this.uname);
    props.setProperty("jdbc.password", this.pwd);
    saveProperties(props);
  }

  private void prosessPath()
  {
    if (this.staticpath != null) {
      this.staticpath = (this.staticpath.endsWith("/") ? this.staticpath.substring(0, this.staticpath.length() - 1) : this.staticpath);
    }
    if (this.solutionpath != null) {
      this.solutionpath = (this.solutionpath.endsWith("/") ? this.solutionpath.substring(0, this.solutionpath.length() - 1) : this.solutionpath);
    }

    if (this.staticdomain != null)
      this.staticdomain = (this.staticdomain.endsWith("/") ? this.staticdomain.substring(0, this.staticdomain.length() - 1) : this.staticdomain);
  }

  private void saveEopParams()
  {
    prosessPath();
    InputStream in = FileUtil.getResourceAsStream("eop.properties");
    String webroot = StringUtil.getRootPath();
    Properties props = new Properties();
    try {
      props.load(in);

      props.setProperty("script_cache", "false");
      props.setProperty("script_compress", "true");
      props.setProperty("http_cache", "0");

      if (this.devmodel == 1) {
        props.setProperty("development_model", "true");
        this.resourcemode = 2;
      } else {
        props.setProperty("development_model", "false");
      }

      if (this.resourcemode == 2) {
        props.setProperty("path.imageserver", webroot + "/statics");
        props.setProperty("domain.imageserver", "http://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath() + "/statics");
      }

      if (this.resourcemode == 1) {
        try {
          FileUtil.copyFolder(webroot + "/statics", this.staticpath);
          props.setProperty("path.imageserver", this.staticpath);
          props.setProperty("domain.imageserver", this.staticdomain);
        } catch (Exception e) {
          throw new RuntimeException();
        }

      }

      if ("2".equals(EopSetting.RUNMODE)) {
        props.setProperty("storage.app_data", this.solutionpath + "/commons");
        props.setProperty("storage.products", this.solutionpath);
      } else {
        props.setProperty("storage.app_data", webroot + "/products/commons");
        props.setProperty("storage.products", webroot + "/products");
      }
      props.setProperty("resourcemode", "" + this.resourcemode);
      if ("mysql".equals(this.dbtype))
        props.setProperty("dbtype", "1");
      else if ("oracle".equals(this.dbtype))
        props.setProperty("dbtype", "2");
      else if ("sqlserver".equals(this.dbtype))
        props.setProperty("dbtype", "3");
      props.setProperty("storage.EOPServer", webroot);
      props.setProperty("path.context_path", getRequest().getContextPath());

      String path = StringUtil.getRootPath("eop.properties");
      File file = new File(path);
      props.store(new FileOutputStream(file), "eop.properties");
      EopSetting.init(props);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String doInstall()
  {
    try {
      if ("2".equals(EopSetting.RUNMODE))
        this.eopInstallManager.install(this.uname, this.pwd, this.domain, this.productid);
      else {
        this.eopInstallManager.install(this.uname, this.pwd, "localhost", this.productid);
      }
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.json = "{result:0}";
    }
    return "json_message";
  }

  private boolean createAndTest(String driver, String url)
  {
    try
    {
      DataSource newDataSource = this.dataSourceCreator.createDataSource(driver, url, this.uname, this.pwd);

      if ("mysql".equals(this.dbtype)) {
        this.jdbcTemplate.setDataSource(newDataSource);
        this.jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS `" + this.dbname + "` DEFAULT CHARACTER SET UTF8");
        newDataSource = this.dataSourceCreator.createDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://" + this.dbhost + "/" + this.dbname + "?useUnicode=true&characterEncoding=utf8", this.uname, this.pwd);
        this.jdbcTemplate.execute("use " + this.dbname);
      }

      this.dataSource = newDataSource;
      this.jdbcTemplate.setDataSource(newDataSource);
      this.simpleJdbcTemplate = new SimpleJdbcTemplate(newDataSource);
      this.jdbcTemplate.execute("CREATE TABLE JAVAMALLTESTTABLE (ID INT not null)");
      this.jdbcTemplate.execute("DROP TABLE JAVAMALLTESTTABLE");

      return true;
    }
    catch (RuntimeException e) {
      e.printStackTrace();
      this.logger.error(e.fillInStackTrace());
    }return false;
  }

  private boolean mysqlTestConnection()
  {
    return createAndTest("com.mysql.jdbc.Driver", "jdbc:mysql://" + this.dbhost + "/test?useUnicode=true&characterEncoding=utf8");
  }

  private boolean oracleTestConnection()
  {
    return createAndTest("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@" + this.dbhost + ":" + this.dbname);
  }

  private boolean sqlserverTestConnection() {
    return createAndTest("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://" + this.dbhost + ";databaseName=" + this.dbname);
  }

  public String testConnection() {
    boolean result = false;

    if ("mysql".equals(this.dbtype))
      result = mysqlTestConnection();
    else if ("oracle".equals(this.dbtype))
      result = oracleTestConnection();
    else if ("sqlserver".equals(this.dbtype)) {
      result = sqlserverTestConnection();
    }
    if (result)
      this.json = "{result:1}";
    else {
      this.json = "{result:0}";
    }

    return "json_message";
  }

  public String testReady() {
    try {
      if ("mysql".equals(this.dbtype))
        this.jdbcTemplate.execute("drop table if exists test");
      this.json = "{result:1}";
    }
    catch (RuntimeException e) {
      this.json = "{result:0}";
    }

    return "json_message";
  }

  private DataSource switchNewDBSource()
  {
    testConnection();
    DataSource newDataSource = this.dataSourceCreator.createDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://" + this.dbhost + "/" + this.dbname + "?useUnicode=true&characterEncoding=utf8", this.uname, this.pwd);
    this.dataSource = newDataSource;
    this.jdbcTemplate.setDataSource(newDataSource);
    this.jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS `" + this.dbname + "` DEFAULT CHARACTER SET UTF8");
    this.jdbcTemplate.execute("use " + this.dbname);
    return newDataSource;
  }

  public JdbcTemplate getJdbcTemplate() {
    return this.jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public String getDbhost() {
    return this.dbhost;
  }

  public void setDbhost(String dbhost) {
    this.dbhost = dbhost;
  }

  public String getUname() {
    return this.uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public String getPwd() {
    return this.pwd;
  }

  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }

  public String getDbtype()
  {
    return this.dbtype;
  }

  public void setDbtype(String dbtype)
  {
    this.dbtype = dbtype;
  }

  public String getDbname()
  {
    return this.dbname;
  }

  public void setDbname(String dbname)
  {
    this.dbname = dbname;
  }

  public EopInstallManager getEopInstallManager()
  {
    return this.eopInstallManager;
  }

  public void setEopInstallManager(EopInstallManager eopInstallManager)
  {
    this.eopInstallManager = eopInstallManager;
  }

  public String getOsVersion()
  {
    return this.osVersion;
  }

  public void setOsVersion(String osVersion)
  {
    this.osVersion = osVersion;
  }

  public String getJavaVersion()
  {
    return this.javaVersion;
  }

  public void setJavaVersion(String javaVersion)
  {
    this.javaVersion = javaVersion;
  }

  public String getDomain()
  {
    return this.domain;
  }

  public void setDomain(String domain)
  {
    this.domain = domain;
  }

  public String getProductid()
  {
    return this.productid;
  }

  public void setProductid(String productid)
  {
    this.productid = productid;
  }

  public SimpleJdbcTemplate getSimpleJdbcTemplate()
  {
    return this.simpleJdbcTemplate;
  }

  public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate)
  {
    this.simpleJdbcTemplate = simpleJdbcTemplate;
  }

  public DataSource getDataSource()
  {
    return this.dataSource;
  }

  public void setDataSource(DataSource dataSource)
  {
    this.dataSource = dataSource;
  }

  public String getStaticdomain()
  {
    return this.staticdomain;
  }

  public void setStaticdomain(String staticdomain)
  {
    this.staticdomain = staticdomain;
  }

  public String getStaticpath()
  {
    return this.staticpath;
  }

  public void setStaticpath(String staticpath)
  {
    this.staticpath = staticpath;
  }

  public int getResourcemode()
  {
    return this.resourcemode;
  }

  public void setResourcemode(int resourcemode)
  {
    this.resourcemode = resourcemode;
  }

  public String getSolutionpath()
  {
    return this.solutionpath;
  }

  public void setSolutionpath(String solutionpath)
  {
    this.solutionpath = solutionpath;
  }

  public IDataSourceCreator getDataSourceCreator()
  {
    return this.dataSourceCreator;
  }

  public void setDataSourceCreator(IDataSourceCreator dataSourceCreator)
  {
    this.dataSourceCreator = dataSourceCreator;
  }

  public IComponentManager getComponentManager()
  {
    return this.componentManager;
  }

  public void setComponentManager(IComponentManager componentManager)
  {
    this.componentManager = componentManager;
  }

  public int getDevmodel()
  {
    return this.devmodel;
  }

  public void setDevmodel(int devmodel)
  {
    this.devmodel = devmodel;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.EopInstallAction
 * JD-Core Version:    0.6.1
 */