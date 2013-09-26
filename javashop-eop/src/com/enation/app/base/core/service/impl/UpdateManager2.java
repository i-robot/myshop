package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.UpdateLog;
import com.enation.app.base.core.model.VersionState;
import com.enation.app.base.core.service.IBatchSqlExecutor;
import com.enation.app.base.core.service.IUpdateManager;
import com.enation.eop.processor.core.RemoteRequest;
import com.enation.eop.processor.core.Request;
import com.enation.eop.processor.core.Response;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.model.EopApp;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.ISqlFileExecutor;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import com.enation.framework.util.XMLUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateManager2
  implements IUpdateManager
{
  private IDaoSupport daoSupport;
  protected final Logger logger = Logger.getLogger(getClass());
  private ISiteManager siteManager;
  private ISqlFileExecutor sqlFileExecutor;
  private IBatchSqlExecutor batchSqlExecutor;

  public VersionState checkNewVersion()
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("检测当前产品是否有新版本...");
    }

    VersionState versionState = new VersionState();
    String newVersion = getNewVersion();
    String currentVersion = EopSetting.VERSION;

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("产品当前版本为：[" + currentVersion + "]最新版本为：[" + newVersion + "]");
    }

    if (!StringUtil.isEmpty(newVersion))
    {
      if (versionLargerThen(newVersion, currentVersion)) {
        versionState.setHaveNewVersion(true);
        versionState.setNewVersion(newVersion);
        List updateLogList = getUpdateLog();
        versionState.setUpdateLogList(updateLogList);
      }

    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("检测新版本完成.");
    }

    return versionState;
  }

  public void update()
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("执行升级操作...");
    }

    String newVersion = getNewVersion();
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("产品最新版本为：[" + newVersion + "]");
    }

    Document document = loadUpdateXmlDoc("/version.xml");

    Element appsNode = XMLUtil.getChildByTagName(document, "apps");

    List<EopApp> appList = this.siteManager.getSiteApps();

    for (EopApp app : appList)
    {
      String appVersion = app.getVersion();

      Element appNode = XMLUtil.getChildByTagName(appsNode, app.getAppid());

      String appLastVersion = appNode.getAttribute("lastversion");

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("升级应用[" + app.getAppid() + "]，产品中此应用当前版本[" + appVersion + "]，最新版本[" + appLastVersion + "]");
      }

      update(app.getAppid(), appVersion, appNode);

      this.daoSupport.execute("update eop_app set version=? where appid=?", new Object[] { appLastVersion, app.getAppid() });

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("升级应用[" + app.getAppid() + "]完成。");
      }

    }

    updateVersion(newVersion);

    if (this.logger.isDebugEnabled())
      this.logger.debug("产品升级完成，当前版本为:[" + EopSetting.VERSION + "]");
  }

  private void update(String appid, String currentVersion, Element appNode)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("升级应用[" + appid + "]...");
    }

    NodeList versionNodeList = appNode.getChildNodes();

    if ((versionNodeList == null) || (versionNodeList.getLength() == 0)) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("服务器端应用[" + appid + "]的版本列表为空...");
      }
      return;
    }

    for (int i = 0; i < versionNodeList.getLength(); i++) {
      Node node = versionNodeList.item(i);
      if (node.getNodeType() == 1) {
        String version = node.getTextContent();

        if (versionLargerThen(version, currentVersion)) {
          if (this.logger.isDebugEnabled()) {
            this.logger.debug("为应用[" + appid + "]升级版本[" + version + "]...");
          }

          update(appid, version);
        }
        else if (this.logger.isDebugEnabled()) {
          this.logger.debug("应用[" + appid + "]版本小于此版本，[" + version + "]跳过...");
        }
      }
    }
  }

  private void update(String appid, String version)
  {
    download(appid, version);
    unZip(appid, version);
    exeUpdate(appid, version);
  }

  private void exeUpdate(String appid, String version)
  {
    String patchPath = EopSetting.EOP_PATH + "/patch/" + appid + "/" + version;
    String updateSql = patchPath + "/sql/update.sql";
    String cmsUpdateSql = patchPath + "/sql/cms_update.sql";

    if (new File(updateSql).exists())
    {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("执行[" + updateSql + "]...");
      }

      String content = FileUtil.read(updateSql, "UTF-8");
      executeSql(content);

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("执行[" + updateSql + "]成功.");
      }

    }
    else if (this.logger.isDebugEnabled()) {
      this.logger.debug("[" + updateSql + "]不存在跳过.");
    }

    if (new File(cmsUpdateSql).exists()) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("执行[" + cmsUpdateSql + "]...");
      }

      String content = FileUtil.read(cmsUpdateSql, "UTF-8");

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("执行内容[" + content + "]...");
      }

      this.batchSqlExecutor.executeForCms(content);

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("执行[" + cmsUpdateSql + "]成功.");
      }

    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("复制[" + patchPath + "/web" + "]...");
    }

    FileUtil.copyFolder(patchPath + "/web", EopSetting.EOP_PATH);
    if (this.logger.isDebugEnabled())
      this.logger.debug("复制[" + patchPath + "/web" + "]成功.");
  }

  private void updateVersion(String version)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("更新eop.properties中的版本信息...");
    }
    try
    {
      String path = StringUtil.getRootPath("eop.properties");
      File file = new File(path);

      EopSetting.VERSION = version;
      InputStream in = new FileInputStream(file);
      Properties props = new Properties();
      props.load(in);
      props.setProperty("version", version);
      props.store(new FileOutputStream(file), "eop.properties");
      if (this.logger.isDebugEnabled())
        this.logger.debug("更新eop.properties中的版本成功.");
    }
    catch (Exception e) {
      this.logger.error("更新eop.properties出错", e.fillInStackTrace());
      e.printStackTrace();
    }
  }

  private void executeSql(String content)
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      this.batchSqlExecutor.exeucte(content);
    } else {
      content = content.replaceAll("_<userid>", "");
      content = content.replaceAll("_<siteid>", "");
      content = content.replaceAll("/user/<userid>/<siteid>", "");
      content = content.replaceAll("<userid>", "1");
      content = content.replaceAll("<siteid>", "1");
      this.sqlFileExecutor.execute(content);
    }
  }

  private void download(String appid, String version)
  {
    String remoteZip = EopSetting.SERVICE_DOMAIN_NAME + "/" + appid + "/patch/" + version + ".zip";
    try
    {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("由[" + remoteZip + "]下载升级包...");
      }

      URL url = new URL(remoteZip);
      String outPath = EopSetting.EOP_PATH + "/patch/" + appid;
      URLConnection conn = url.openConnection();
      InputStream is = conn.getInputStream();
      byte[] bts = new byte[2048];
      File file = new File(outPath);
      if (!file.exists()) {
        file.mkdirs();
      }

      FileOutputStream fout = new FileOutputStream(outPath + "/" + version + ".zip");
      int n;
      while ((n = is.read(bts)) != -1) {
        fout.write(bts, 0, n);
        fout.flush();
        bts = new byte[2048];
      }
    } catch (Exception e) {
      this.logger.error("下载升级包[" + remoteZip + "]出错");
      e.printStackTrace();
      throw new RuntimeException("下载升级版本zip出错", e);
    }
  }

  private void unZip(String appid, String version)
  {
    String zipPath = EopSetting.EOP_PATH + "/patch/" + appid + "/" + version + ".zip";
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("解压升级包[" + zipPath + "]...");
    }

    FileUtil.unZip(zipPath, EopSetting.EOP_PATH + "/patch/" + appid + "/" + version, true);
    if (this.logger.isDebugEnabled())
      this.logger.debug("解压升级包[" + zipPath + "]完成");
  }

  private List<UpdateLog> getUpdateLog()
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("获取产品所有应用的更新日志...");
    }

    List updateLogList = new ArrayList();

    Document document = loadUpdateXmlDoc("/version.xml");

    Element appsNode = XMLUtil.getChildByTagName(document, "apps");

    List<EopApp> appList = this.siteManager.getSiteApps();
    for (EopApp app : appList)
    {
      String verison = app.getVersion();

      Element appNode = XMLUtil.getChildByTagName(appsNode, app.getAppid());

      UpdateLog updateLog = getAppUpdateLog(app.getAppid(), verison, appNode);
      if (updateLog != null) {
        updateLogList.add(updateLog);
      }

    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("获取产品所有应用的更新日志完成.");
    }

    return updateLogList;
  }

  private UpdateLog getAppUpdateLog(String appid, String currentVersion, Node appNode)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("获取获取应用[" + appid + "]的日志...");
    }

    NodeList versionNodeList = appNode.getChildNodes();

    if ((versionNodeList == null) || (versionNodeList.getLength() == 0)) return null;
    UpdateLog updateLog = new UpdateLog();
    updateLog.setAppId(appid);
    List logList = new ArrayList();
    for (int i = 0; i < versionNodeList.getLength(); i++) {
      Node node = versionNodeList.item(i);
      if (node.getNodeType() == 1) {
        String version = node.getTextContent();

        if (versionLargerThen(version, currentVersion))
        {
          List oneLogList = getVersionUpdateLog(appid, version);
          logList.addAll(oneLogList);
        }
      }
    }

    updateLog.setLogList(logList);
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("获取获取应用[" + appid + "]的日志成功.");
    }

    return updateLog;
  }

  private List<String> getVersionUpdateLog(String appid, String version)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("获取获取应用[" + appid + "]版本[" + version + "]的日志...");
    }
    List logList = new ArrayList();
    Document doc = loadUpdateXmlDoc("/" + appid + "/" + version + ".xml");
    NodeList itemList = doc.getElementsByTagName("item");

    if (itemList != null) {
      int i = 0; for (int len = itemList.getLength(); i < len; i++) {
        Node node = itemList.item(i);
        if (node.getNodeType() == 1) {
          String log = node.getTextContent();
          logList.add(log);
        }
      }
    }
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("获取获取应用[" + appid + "]版本[" + version + "]的日志成功.");
    }
    return logList;
  }

  private String getNewVersion()
  {
    String productid = EopSetting.PRODUCTID;
    Document document = loadUpdateXmlDoc("/version.xml");

    Node productNode = XMLUtil.getChildByTagName(document, "products");
    NodeList productList = productNode.getChildNodes();
    int i = 0; for (int len = productList.getLength(); i < len; i++) {
      Node proNode = productList.item(i);
      if (proNode.getNodeType() == 1) {
        String proName = proNode.getNodeName();
        if (productid.equals(proName)) {
          return proNode.getTextContent();
        }
      }
    }
    throw new RuntimeException("产品ID不正确");
  }

  private Document loadUpdateXmlDoc(String filePath)
  {
    String serviceDomain = EopSetting.SERVICE_DOMAIN_NAME;

    Request request = new RemoteRequest();
    Response response = request.execute(serviceDomain + filePath);
    if (response != null) {
      InputStream xmlStream = response.getInputStream();
      try
      {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(xmlStream);
      }
      catch (Exception e)
      {
        throw new RuntimeException("load version.xml error");
      }
    }
    throw new RuntimeException("load version.xml error");
  }

  private boolean versionLargerThen(String ver1, String ver2)
  {
    if (StringUtil.isEmpty(ver1)) throw new IllegalArgumentException("ver1版本不能为空");
    if (StringUtil.isEmpty(ver2)) throw new IllegalArgumentException("ver2版本不能为空");
    if (ver1.length() != ver2.length()) throw new IllegalArgumentException("ver2与ver2版本号格式不相同");
    if (ver1.length() != 5) throw new IllegalArgumentException("版本号格式不正确，应为如：2.1.0");

    String[] ver1a = ver1.split("\\.");
    Integer ver1i = Integer.valueOf(Integer.valueOf(ver1a[0]).intValue() * 1000000 + Integer.valueOf(ver1a[1]).intValue() * 1000 + Integer.valueOf(ver1a[2]).intValue());
    String[] ver2a = ver2.split("\\.");
    Integer ver2i = Integer.valueOf(Integer.valueOf(ver2a[0]).intValue() * 1000000 + Integer.valueOf(ver2a[1]).intValue() * 1000 + Integer.valueOf(ver2a[2]).intValue());

    return ver1i.intValue() > ver2i.intValue();
  }

  public ISiteManager getSiteManager()
  {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }

  public ISqlFileExecutor getSqlFileExecutor()
  {
    return this.sqlFileExecutor;
  }

  public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor)
  {
    this.sqlFileExecutor = sqlFileExecutor;
  }

  public IBatchSqlExecutor getBatchSqlExecutor()
  {
    return this.batchSqlExecutor;
  }

  public void setBatchSqlExecutor(IBatchSqlExecutor batchSqlExecutor)
  {
    this.batchSqlExecutor = batchSqlExecutor;
  }

  public IDaoSupport getDaoSupport()
  {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport daoSupport)
  {
    this.daoSupport = daoSupport;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.UpdateManager2
 * JD-Core Version:    0.6.1
 */