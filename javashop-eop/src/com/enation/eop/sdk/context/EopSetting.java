package com.enation.eop.sdk.context;

import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EopSetting
{
  public static boolean TEST_MODE = false;

  public static boolean DEVELOPMENT_MODEL = true;

  public static String EOP_PATH = "";

  public static String IMG_SERVER_DOMAIN = "static.eop.com";

  public static String IMG_SERVER_PATH = "";

  public static String APP_DATA_STORAGE_PATH = "e:/eop";

  public static String PRODUCTS_STORAGE_PATH = "E:/workspace/eop3/eop/src/products";

  public static String APP_DOMAIN_NAME = "eop.com";

  public static String SERVICE_DOMAIN_NAME = "service.enationsoft.com";

  public static String USERDATA_STORAGE_PATH = "/user";

  public static String ADMINTHEMES_STORAGE_PATH = "/adminthemes";

  public static String THEMES_STORAGE_PATH = "/themes";

  public static String CONTEXT_PATH = "/";

  public static String RESOURCEMODE = "1";

  public static String RUNMODE = "2";

  public static String DBTYPE = "1";

  public static String EXTENSION = "do";

  public static String TEMPLATEENGINE = "on";

  public static String THUMBNAILCREATOR = "javaimageio";

  public static String FILE_STORE_PREFIX = "fs:";

  public static String VERSION = "";
  public static String PRODUCTID = "";

  public static String INSTALL_LOCK = "NO";
  public static List<String> safeUrlList;
  public static String BACKEND_PAGESIZE = "15";
  public static String ENCODING;
  public static boolean SCRIPT_COMPRESS = true;
  public static boolean SCRIPT_CACHE = true;

  public static int HTTPCACHE = 0;
  public static String DEFAULT_IMG_URL = IMG_SERVER_DOMAIN + "/images/no_picture.jpg";

  public static void init(Properties props)
  {
    String script_compress = props.getProperty("script_compress");
    String script_cache = props.getProperty("script_cache");
    String http_cache = props.getProperty("http_cache");

    String development_model = props.getProperty("development_model");

    DEVELOPMENT_MODEL = "true".equals(development_model);

    if ("false".equals(script_compress))
      SCRIPT_COMPRESS = false;
    else {
      SCRIPT_COMPRESS = true;
    }

    if ("false".equals(script_cache))
      SCRIPT_CACHE = false;
    else {
      SCRIPT_CACHE = true;
    }

    if ("1".equals(http_cache))
      HTTPCACHE = 1;
    else {
      HTTPCACHE = 0;
    }

    String encoding = props.getProperty("encoding");
    ENCODING = StringUtil.isEmpty(encoding) ? "" : encoding;

    String domain = props.getProperty("domain.imageserver");
    IMG_SERVER_DOMAIN = StringUtil.isEmpty(domain) ? IMG_SERVER_DOMAIN : domain;
    IMG_SERVER_DOMAIN = "http://" + IMG_SERVER_DOMAIN;

    String userdata_storage_path = props.getProperty("storage.userdata");
    USERDATA_STORAGE_PATH = StringUtil.isEmpty(userdata_storage_path) ? USERDATA_STORAGE_PATH : userdata_storage_path;

    String adminthemes_storage_path = props.getProperty("storage.adminthemes");
    ADMINTHEMES_STORAGE_PATH = StringUtil.isEmpty(adminthemes_storage_path) ? ADMINTHEMES_STORAGE_PATH : adminthemes_storage_path;

    String themes_storage_path = props.getProperty("storage.themes");
    THEMES_STORAGE_PATH = StringUtil.isEmpty(themes_storage_path) ? THEMES_STORAGE_PATH : themes_storage_path;

    String eop_path = props.getProperty("storage.EOPServer");
    EOP_PATH = StringUtil.isEmpty(eop_path) ? EOP_PATH : eop_path;

    String imageserver_path = props.getProperty("path.imageserver");
    IMG_SERVER_PATH = StringUtil.isEmpty(imageserver_path) ? IMG_SERVER_PATH : imageserver_path;

    String app_data = props.getProperty("storage.app_data");
    APP_DATA_STORAGE_PATH = StringUtil.isEmpty(app_data) ? APP_DATA_STORAGE_PATH : app_data;

    String context_path = props.getProperty("path.context_path");
    CONTEXT_PATH = StringUtil.isEmpty(context_path) ? CONTEXT_PATH : context_path;

    String products_path = props.getProperty("storage.products");
    PRODUCTS_STORAGE_PATH = StringUtil.isEmpty(products_path) ? PRODUCTS_STORAGE_PATH : products_path;

    String resoucemode = props.getProperty("resourcemode");
    RESOURCEMODE = StringUtil.isEmpty(resoucemode) ? RESOURCEMODE : resoucemode;

    String runmode = props.getProperty("runmode");
    RUNMODE = StringUtil.isEmpty(runmode) ? RUNMODE : runmode;

    String dbtype = props.getProperty("dbtype");
    DBTYPE = StringUtil.isEmpty(dbtype) ? DBTYPE : dbtype;

    String extension = props.getProperty("extension");
    EXTENSION = StringUtil.isEmpty(extension) ? EXTENSION : extension;

    String templateengine = props.getProperty("templateengine");
    TEMPLATEENGINE = StringUtil.isEmpty(templateengine) ? TEMPLATEENGINE : templateengine;

    String thumbnailcreator = props.getProperty("thumbnailcreator");
    THUMBNAILCREATOR = StringUtil.isEmpty(thumbnailcreator) ? THUMBNAILCREATOR : thumbnailcreator;
    com.enation.framework.image.ThumbnailCreatorFactory.CREATORTYPE = THUMBNAILCREATOR;

    VERSION = props.getProperty("version");
    if (VERSION == null) VERSION = "";

    PRODUCTID = props.getProperty("productid");
    if (PRODUCTID == null) PRODUCTID = "";

    File installLockFile = new File(StringUtil.getRootPath() + "/install/install.lock");
    if (installLockFile.exists())
      INSTALL_LOCK = "YES";
    else {
      INSTALL_LOCK = "NO";
    }

    String servicedomain = props.getProperty("domain.service");
    SERVICE_DOMAIN_NAME = StringUtil.isEmpty(servicedomain) ? SERVICE_DOMAIN_NAME : servicedomain;

    String backend_pagesize = props.getProperty("backend.pagesize");
    BACKEND_PAGESIZE = StringUtil.isEmpty(backend_pagesize) ? BACKEND_PAGESIZE : backend_pagesize;

    com.enation.eop.processor.facade.support.HeaderResourcePageWrapper.THE_SSO_SCRIPT = "<script>eval(\"\\x64\\x6f\\x63\\x75\\x6d\\x65\\x6e\\x74\\x2e\\x77\\x72\\x69\\x74\\x65\\x28\\x27\\u672c\\u7f51\\u7ad9\\u57fa\\u4e8e\\u3010\\u6613\\u65cf\\u667a\\u6c47\\u7f51\\u7edc\\u5546\\u5e97\\u7cfb\\u7edf\\x56\\x33\\x2e\\x30\\u3011\\u5f00\\u53d1\\uff0c\\x3c\\x62\\x72\\x3e\\u3010\\u6613\\u65cf\\u667a\\u6c47\\u7f51\\u7edc\\u5546\\u5e97\\u7cfb\\u7edf\\u3011\\u8457\\u4f5c\\u6743\\u5df2\\u5728\\u4e2d\\u534e\\u4eba\\u6c11\\u5171\\u548c\\u56fd\\u56fd\\u5bb6\\u7248\\u6743\\u5c40\\u6ce8\\u518c\\u3002\\x3c\\x62\\x72\\x3e\\u672a\\u7ecf\\u6613\\u65cf\\u667a\\u6c47\\uff08\\u5317\\u4eac\\uff09\\u79d1\\u6280\\u6709\\u9650\\u516c\\u53f8\\u4e66\\u9762\\u6388\\u6743\\uff0c\\x3c\\x62\\x72\\x3e\\u4efb\\u4f55\\u7ec4\\u7ec7\\u6216\\u4e2a\\u4eba\\u4e0d\\u5f97\\u4f7f\\u7528\\uff0c\\x3c\\x62\\x72\\x3e\\u8fdd\\u8005\\u672c\\u516c\\u53f8\\u5c06\\u4f9d\\u6cd5\\u8ffd\\u7a76\\u8d23\\u4efb\\u3002\\x3c\\x62\\x72\\x3e\\x27\\x29\");</script>";
    com.enation.eop.processor.backend.support.HelpDivWrapper.THE_Help_SCRIPT = "<script>eval(\"\\x64\\x6f\\x63\\x75\\x6d\\x65\\x6e\\x74\\x2e\\x77\\x72\\x69\\x74\\x65\\x28\\x27\\u672c\\u7f51\\u7ad9\\u57fa\\u4e8e\\u3010\\u6613\\u65cf\\u667a\\u6c47\\u7f51\\u7edc\\u5546\\u5e97\\u7cfb\\u7edf\\x56\\x33\\x2e\\x30\\u3011\\u5f00\\u53d1\\uff0c\\x3c\\x62\\x72\\x3e\\u3010\\u6613\\u65cf\\u667a\\u6c47\\u7f51\\u7edc\\u5546\\u5e97\\u7cfb\\u7edf\\u3011\\u8457\\u4f5c\\u6743\\u5df2\\u5728\\u4e2d\\u534e\\u4eba\\u6c11\\u5171\\u548c\\u56fd\\u56fd\\u5bb6\\u7248\\u6743\\u5c40\\u6ce8\\u518c\\u3002\\x3c\\x62\\x72\\x3e\\u672a\\u7ecf\\u6613\\u65cf\\u667a\\u6c47\\uff08\\u5317\\u4eac\\uff09\\u79d1\\u6280\\u6709\\u9650\\u516c\\u53f8\\u4e66\\u9762\\u6388\\u6743\\uff0c\\x3c\\x62\\x72\\x3e\\u4efb\\u4f55\\u7ec4\\u7ec7\\u6216\\u4e2a\\u4eba\\u4e0d\\u5f97\\u4f7f\\u7528\\uff0c\\x3c\\x62\\x72\\x3e\\u8fdd\\u8005\\u672c\\u516c\\u53f8\\u5c06\\u4f9d\\u6cd5\\u8ffd\\u7a76\\u8d23\\u4efb\\u3002\\x3c\\x62\\x72\\x3e\\x27\\x29\");</script>";
    initSafeUrl();
  }

  private static void initSafeUrl()
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(FileUtil.getResourceAsStream("safeurl.xml"));
      NodeList urlNodeList = document.getElementsByTagName("urls").item(0).getChildNodes();
      safeUrlList = new ArrayList();
      for (int i = 0; i < urlNodeList.getLength(); i++) {
        Node node = urlNodeList.item(i);
        safeUrlList.add(node.getTextContent());
      }
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } catch (SAXException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public static int getDepotType(int depotid)
  {
    if (depotid == 6)
      return 0;
    return 1;
  }

  static
  {
    try
    {
      InputStream in = FileUtil.getResourceAsStream("eop.properties");
      Properties props = new Properties();
      props.load(in);
      init(props);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.context.EopSetting
 * JD-Core Version:    0.6.1
 */