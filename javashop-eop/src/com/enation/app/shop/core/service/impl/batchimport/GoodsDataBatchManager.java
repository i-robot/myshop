package com.enation.app.shop.core.service.impl.batchimport;

import com.enation.app.shop.core.model.Cat;
import com.enation.app.shop.core.model.ImportDataSource;
import com.enation.app.shop.core.model.support.GoodsTypeDTO;
import com.enation.app.shop.core.plugin.goodsimp.GoodsImportPluginBundle;
import com.enation.app.shop.core.service.IBrandManager;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.app.shop.core.service.IGoodsTypeManager;
import com.enation.app.shop.core.service.batchimport.IGoodsDataBatchManager;
import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.ExcelUtil;
import com.enation.framework.util.XMLUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GoodsDataBatchManager
  implements IGoodsDataBatchManager
{
  protected final Logger logger = Logger.getLogger(getClass());
  private IBrandManager brandManager;
  private IGoodsTypeManager goodsTypeManager;
  private IGoodsCatManager goodsCatManager;
  private GoodsImportPluginBundle goodsImportPluginBundle;
  private IDaoSupport daoSupport;
  private IDaoSupport baseDaoSupport;

  @Transactional(propagation=Propagation.REQUIRED)
  public void batchImport(String path, int imptype, int impcatid, Integer startNum, Integer endNum)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("开始导入商品数据...");
    }

    Document configDoc = load(path);

    this.goodsImportPluginBundle.onBeforeImport(configDoc);

    Element configEl = XMLUtil.getChildByTagName(configDoc, "config");

    String datafolder = configEl.getAttribute("datafolder");

    String excel = configEl.getAttribute("excel");
    Workbook goodsWb = openExcel(excel);

    NodeList catNodeList = configEl.getElementsByTagName("cat");
    int i = 0; for (int len = catNodeList.getLength(); i < len; i++) {
      Node catNode = catNodeList.item(i);

      if (imptype == 2) {
        int catid = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "id").getTextContent()).intValue();
        if (catid != impcatid);
      }
      else {
        processSheet(datafolder, goodsWb, catNode, startNum, endNum);
      }
    }
  }

  private void processSheet(String datafolder, Workbook goodsWb, Node catNode, Integer startNum, Integer endNum)
  {
    Element beforeSheetNode = XMLUtil.getChildByTagName(catNode, "beforesheet");

    int sheetIndex = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "sheet_index").getTextContent()).intValue();

    int rowStartNum = 0;
    if (startNum != null)
      rowStartNum = startNum.intValue();
    else {
      rowStartNum = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "start_rouwnum").getTextContent()).intValue();
    }

    int catid = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "id").getTextContent()).intValue();

    int goodsIdCluNum = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "goodsid_column").getTextContent()).intValue();

    ImportDataSource importDs = new ImportDataSource();

    Cat cat = this.goodsCatManager.getById(catid);

    GoodsTypeDTO typeDTO = this.goodsTypeManager.get(cat.getType_id());
    importDs.setBrandList(this.brandManager.list());
    importDs.setPropList(typeDTO.getPropList());

    Sheet sheet = goodsWb.getSheetAt(sheetIndex);
    int lastRowNum = 0;
    if (endNum != null)
      lastRowNum = endNum.intValue();
    else {
      lastRowNum = sheet.getLastRowNum();
    }

    NodeList rowList = beforeSheetNode.getElementsByTagName("column");
    Element processorNode = XMLUtil.getChildByTagName(catNode, "processors");
    NodeList importerNodeList = processorNode.getElementsByTagName("importer");

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("开始导入类别[" + cat.getName() + "]rowStartNum[" + rowStartNum + "]lastRowNum[" + lastRowNum + "]...");
    }

    Map goods = null;

    for (int i = rowStartNum; i < lastRowNum + 1; i++)
    {
      Row row = sheet.getRow(i);
      importDs.setRowData(row);

      int goodsNum = 0;
      if (goodsIdCluNum == -1)
        goodsNum = row.getRowNum() + 1;
      else {
        goodsNum = Double.valueOf(row.getCell(goodsIdCluNum).getNumericCellValue()).intValue();
      }

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("开始行号[" + goodsNum + "]...");
      }

      if (goodsNum != 0)
      {
        importDs.setDatafolder(datafolder + "/" + XMLUtil.getChildByTagName(catNode, "name").getTextContent() + "/" + goodsNum);
        importDs.setNewGoods(true);
        importDs.setGoodsNum(goodsNum);
        goods = new HashMap();
        goods.put("market_enable", Integer.valueOf(1));
        goods.put("cat_id", Integer.valueOf(catid));
        goods.put("type_id", cat.getType_id());
        goods.put("have_spec", Integer.valueOf(0));
        goods.put("cost", Integer.valueOf(0));
        goods.put("store", Integer.valueOf(0));
        goods.put("weight", Integer.valueOf(0));
        goods.put("disabled", Integer.valueOf(0));
        goods.put("create_time", Long.valueOf(System.currentTimeMillis()));
        goods.put("view_count", Integer.valueOf(0));
        goods.put("buy_count", Integer.valueOf(0));
        goods.put("last_modify", Long.valueOf(System.currentTimeMillis()));
      }
      else
      {
        importDs.setNewGoods(false);
      }

      for (int j = 0; j < rowList.getLength(); j++) {
        Element rowNode = (Element)rowList.item(j);
        String index = rowNode.getAttribute("index");
        String importer = rowNode.getAttribute("importer");

        Object value = ExcelUtil.getCellValue(row.getCell(Integer.valueOf(index).intValue()));
        IGoodsDataImporter goodsDataImporter = (IGoodsDataImporter)SpringContextHolder.getBean(importer);
        goodsDataImporter.imported(value, rowNode, importDs, goods);
      }

      if (goodsNum != 0) {
        this.baseDaoSupport.insert("goods", goods);
        int goodsid = this.baseDaoSupport.getLastId("goods");
        goods.put("goods_id", Integer.valueOf(goodsid));
      }

      Element afterSheetNode = XMLUtil.getChildByTagName(catNode, "aftersheet");

      if (afterSheetNode != null)
      {
        NodeList afterRowList = afterSheetNode.getElementsByTagName("column");

        for (int j = 0; j < afterRowList.getLength(); j++) {
          Element rowNode = (Element)afterRowList.item(j);
          String index = rowNode.getAttribute("index");
          String importer = rowNode.getAttribute("importer");

          Object value = ExcelUtil.getCellValue(row.getCell(Integer.valueOf(index).intValue()));
          IGoodsDataImporter goodsDataImporter = (IGoodsDataImporter)SpringContextHolder.getBean(importer);
          goodsDataImporter.imported(value, rowNode, importDs, goods);
        }

      }

      for (int j = 0; j < importerNodeList.getLength(); j++) {
        Element node = (Element)importerNodeList.item(j);
        String importer = node.getTextContent();
        IGoodsDataImporter goodsDataImporter = (IGoodsDataImporter)SpringContextHolder.getBean(importer);

        goodsDataImporter.imported(null, node, importDs, goods);
      }

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("行号[" + goodsNum + "]导入完成");
      }

    }

    if (this.logger.isDebugEnabled())
      this.logger.debug("导入类别[" + cat.getName() + "]完成...");
  }

  private Workbook openExcel(String excelPath)
  {
    try
    {
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelPath));
      return new HSSFWorkbook(fs);
    }
    catch (IOException e) {
      e.printStackTrace();
    }return null;
  }

  private Document load(String path)
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(path);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }throw new RuntimeException("load [" + path + "]    error");
  }

  public IDaoSupport getDaoSupport()
  {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport daoSupport)
  {
    this.daoSupport = daoSupport;
  }

  public IDaoSupport getBaseDaoSupport()
  {
    return this.baseDaoSupport;
  }

  public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
  {
    this.baseDaoSupport = baseDaoSupport;
  }

  public IGoodsTypeManager getGoodsTypeManager()
  {
    return this.goodsTypeManager;
  }

  public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager)
  {
    this.goodsTypeManager = goodsTypeManager;
  }

  public IGoodsCatManager getGoodsCatManager()
  {
    return this.goodsCatManager;
  }

  public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
  {
    this.goodsCatManager = goodsCatManager;
  }

  public IBrandManager getBrandManager()
  {
    return this.brandManager;
  }

  public void setBrandManager(IBrandManager brandManager)
  {
    this.brandManager = brandManager;
  }

  public GoodsImportPluginBundle getGoodsImportPluginBundle()
  {
    return this.goodsImportPluginBundle;
  }

  public void setGoodsImportPluginBundle(GoodsImportPluginBundle goodsImportPluginBundle)
  {
    this.goodsImportPluginBundle = goodsImportPluginBundle;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsDataBatchManager
 * JD-Core Version:    0.6.1
 */