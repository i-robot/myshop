package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.VersionState;
import com.enation.app.base.core.service.IBatchSqlExecutor;
import com.enation.app.base.core.service.IUpdateManager;
import com.enation.eop.processor.core.RemoteRequest;
import com.enation.eop.processor.core.Request;
import com.enation.eop.processor.core.Response;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.ISqlFileExecutor;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateManager
  implements IUpdateManager
{
  private ISqlFileExecutor sqlFileExecutor;
  private IBatchSqlExecutor batchSqlExecutor;

  public VersionState checkNewVersion()
  {
    VersionState versionState = new VersionState();

    String serviceDomain = EopSetting.SERVICE_DOMAIN_NAME;

    Request request = new RemoteRequest();
    Response response = request.execute(serviceDomain + "/lastVersion.txt");
    if (response != null) {
      String currentVersion = EopSetting.VERSION;
      String newVersion = response.getContent();
      if (!StringUtil.isEmpty(newVersion))
      {
        if (versionLargerThen(newVersion, currentVersion)) {
          versionState.setHaveNewVersion(true);
          versionState.setNewVersion(newVersion);
          response = request.execute(serviceDomain + "/lastUpdate.txt");
          versionState.setUpdateLog(response.getContent());
        }
      }

    }

    return versionState;
  }

  public void update()
  {
    VersionState versionState = checkNewVersion();
    if (versionState.getHaveNewVersion())
    {
      String serviceDomain = EopSetting.SERVICE_DOMAIN_NAME;
      Request request = new RemoteRequest();
      Response response = request.execute(serviceDomain + "/patch/update/");
      String content = response.getContent();
      List zipList = findPatchZipList(content);
      update(zipList);
    }
  }

  private void update(List<String> zipList)
  {
    try
    {
      for (String version : zipList) {
        String zipName = version + ".zip";

        download(zipName);

        unZip(zipName);

        update(version);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("更新版本文件出错", e);
    }
  }

  private void update(String version)
    throws Exception
  {
    String patchPath = EopSetting.EOP_PATH + "/patch/" + version;
    String updateSql = patchPath + "/sql/update.sql";
    String cmsUpdateSql = patchPath + "/sql/cms_update.sql";

    if (new File(updateSql).exists()) {
      String content = FileUtil.read(updateSql, "UTF-8");
      executeSql(content);
    }

    if (new File(cmsUpdateSql).exists()) {
      String content = FileUtil.read(cmsUpdateSql, "UTF-8");
      this.batchSqlExecutor.executeForCms(content);
    }

    FileUtil.copyFolder(patchPath + "/web", EopSetting.EOP_PATH);

    updateVersion(version);
  }

  private void updateVersion(String version)
  {
    EopSetting.VERSION = version;
    InputStream in = FileUtil.getResourceAsStream("eop.properties");
    Properties props = new Properties();
    try {
      props.load(in);
      props.setProperty("version", version);
      String path = StringUtil.getRootPath("eop.properties");
      File file = new File(path);
      props.store(new FileOutputStream(file), "eop.properties");
    }
    catch (IOException e) {
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

  private void download(String zipName)
    throws Exception
  {
    URL url = new URL(EopSetting.SERVICE_DOMAIN_NAME + "/patch/update/" + zipName);
    String outPath = EopSetting.EOP_PATH + "/patch";
    URLConnection conn = url.openConnection();
    InputStream is = conn.getInputStream();
    byte[] bts = new byte[2048];
    File file = new File(outPath);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileOutputStream fout = new FileOutputStream(outPath + "/" + zipName);
    int n;
    while ((n = is.read(bts)) != -1) {
      fout.write(bts, 0, n);
      fout.flush();
      bts = new byte[2048];
    }
  }

  private void unZip(String zipName)
  {
    String zipPath = EopSetting.EOP_PATH + "/patch/" + zipName;
    FileUtil.unZip(zipPath, EopSetting.EOP_PATH + "/patch/" + zipName.replaceAll(".zip", ""), true);
  }

  private List<String> findPatchZipList(String content)
  {
    List zipList = new ArrayList();
    String pattern = "<a href=\"(.*?).zip\">(.*?).zip<\\/a>";
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(content);
    while (m.find()) {
      String item = m.group();
      zipList.add(findZipName(item));
    }
    String currentVersion = EopSetting.VERSION;
    zipList = sortVersionList(currentVersion, zipList);
    return zipList;
  }

  private String findZipName(String item)
  {
    String pattern = "<a href=\"(.*?).zip\">(.*?).zip<\\/a>";
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(item);
    if (m.find()) {
      return m.replaceAll("$2").trim();
    }
    return null;
  }

  public List<String> sortVersionList(String currentVersion, List<String> zipList)
  {
    List lst = new ArrayList();

    for (String n : zipList) {
      String[] num = n.split("\\.");
      Integer result = Integer.valueOf(Integer.valueOf(num[0]).intValue() * 1000000 + Integer.valueOf(num[1]).intValue() * 1000 + Integer.valueOf(num[2]).intValue());
      Map m = new HashMap();
      m.put("string", n);
      m.put("result", result);
      lst.add(m);
    }

    List list = new ArrayList();

    int i = 0; for (int len = lst.size(); i < len; i++) {
      for (int j = i; j < lst.size(); j++) {
        if (Integer.valueOf(((Map)lst.get(i)).get("result").toString()).intValue() > Integer.valueOf(((Map)lst.get(j)).get("result").toString()).intValue()) {
          Map m = new HashMap();
          m.put("string", ((Map)lst.get(i)).get("string").toString());
          m.put("result", ((Map)lst.get(i)).get("result").toString());
          ((Map)lst.get(i)).put("string", ((Map)lst.get(j)).get("string"));
          ((Map)lst.get(i)).put("result", ((Map)lst.get(j)).get("result"));
          ((Map)lst.get(j)).put("string", m.get("string"));
          ((Map)lst.get(j)).put("result", m.get("result"));
        }
      }
      if (versionLargerThen(((Map)lst.get(i)).get("string").toString(), currentVersion)) {
        list.add(((Map)lst.get(i)).get("string").toString());
      }
    }
    return list;
  }

  public boolean versionLargerThen(String ver1, String ver2)
  {
    String[] ver1a = ver1.split("\\.");
    Integer ver1i = Integer.valueOf(Integer.valueOf(ver1a[0]).intValue() * 1000000 + Integer.valueOf(ver1a[1]).intValue() * 1000 + Integer.valueOf(ver1a[2]).intValue());
    String[] ver2a = ver2.split("\\.");
    Integer ver2i = Integer.valueOf(Integer.valueOf(ver2a[0]).intValue() * 1000000 + Integer.valueOf(ver2a[1]).intValue() * 1000 + Integer.valueOf(ver2a[2]).intValue());
    return ver1i.intValue() > ver2i.intValue();
  }

  public static void main(String[] args)
  {
    List<String> zipList = new ArrayList();
    zipList.add("2.1.6");
    zipList.add("2.2.0");
    zipList.add("2.1.3");
    zipList.add("2.1.2");
    zipList.add("2.2.6");
    zipList.add("2.3.14");
    zipList.add("2.0.6");
    zipList.add("1.1.6");
    zipList.add("2.1.16");
    UpdateManager manager = new UpdateManager();
    zipList = manager.sortVersionList("2.1.6", zipList);
    for (String zipName : zipList)
      System.out.println(zipName);
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
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.UpdateManager
 * JD-Core Version:    0.6.1
 */