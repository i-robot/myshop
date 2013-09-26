package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.app.shop.core.service.batchimport.IGoodsDataBatchManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import org.apache.log4j.Logger;

public class GoodsImportAction extends WWAction
{
  private IGoodsDataBatchManager goodsDataBatchManager;
  private IGoodsCatManager goodsCatManager;
  private String path;
  private List catList;
  private int imptype;
  private int catid;
  private Integer startNum;
  private Integer endNum;

  public String execute()
  {
    this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
    return "input";
  }

  public String imported() {
    try {
      this.logger.debug("startNum[" + this.startNum + "]endNum[" + this.endNum + "]");
      this.goodsDataBatchManager.batchImport(this.path, this.imptype, this.catid, this.startNum, this.endNum);
      this.json = "{result:1}";
    }
    catch (RuntimeException e) {
      this.logger.error("商品导入", e);
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public IGoodsDataBatchManager getGoodsDataBatchManager() {
    return this.goodsDataBatchManager;
  }

  public void setGoodsDataBatchManager(IGoodsDataBatchManager goodsDataBatchManager)
  {
    this.goodsDataBatchManager = goodsDataBatchManager;
  }

  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public List getCatList() {
    return this.catList;
  }

  public void setCatList(List catList) {
    this.catList = catList;
  }

  public IGoodsCatManager getGoodsCatManager() {
    return this.goodsCatManager;
  }

  public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
    this.goodsCatManager = goodsCatManager;
  }

  public int getImptype() {
    return this.imptype;
  }

  public void setImptype(int imptype) {
    this.imptype = imptype;
  }

  public int getCatid() {
    return this.catid;
  }

  public void setCatid(int catid) {
    this.catid = catid;
  }

  public Integer getStartNum() {
    return this.startNum;
  }

  public void setStartNum(Integer startNum) {
    this.startNum = startNum;
  }

  public Integer getEndNum() {
    return this.endNum;
  }

  public void setEndNum(Integer endNum) {
    this.endNum = endNum;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsImportAction
 * JD-Core Version:    0.6.1
 */