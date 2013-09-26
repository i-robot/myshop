package com.enation.app.shop.core.model;

import java.util.List;
import org.apache.poi.ss.usermodel.Row;

public class ImportDataSource
{
  private List<Brand> brandList;
  private List<Attribute> propList;
  private String datafolder;
  private int goodsNum;
  private boolean isNewGoods;
  private Row rowData;

  public List<Brand> getBrandList()
  {
    return this.brandList;
  }
  public void setBrandList(List<Brand> brandList) {
    this.brandList = brandList;
  }
  public List<Attribute> getPropList() {
    return this.propList;
  }
  public void setPropList(List<Attribute> propList) {
    this.propList = propList;
  }
  public String getDatafolder() {
    return this.datafolder;
  }
  public void setDatafolder(String datafolder) {
    this.datafolder = datafolder;
  }
  public int getGoodsNum() {
    return this.goodsNum;
  }
  public void setGoodsNum(int goodsNum) {
    this.goodsNum = goodsNum;
  }
  public boolean isNewGoods() {
    return this.isNewGoods;
  }
  public void setNewGoods(boolean isNewGoods) {
    this.isNewGoods = isNewGoods;
  }
  public Row getRowData() {
    return this.rowData;
  }
  public void setRowData(Row rowData) {
    this.rowData = rowData;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.ImportDataSource
 * JD-Core Version:    0.6.1
 */