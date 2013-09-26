package com.enation.eop.sdk.webapp.taglib.html;

import com.enation.eop.sdk.webapp.taglib.HtmlTaglib;
import com.enation.eop.sdk.webapp.taglib.html.support.GridCellProvider;

public class GridCellTaglib extends HtmlTaglib
{
  private String sort;
  private String sortDefault;
  private String order;
  private int isSort;
  private int isAjax;
  private String style;
  private String width;
  private String height;
  private String align;
  private String plugin_type;
  private String clazz;
  private GridCellProvider cellProvider;

  public GridCellTaglib()
  {
    this.cellProvider = new GridCellProvider();
  }

  protected String postStart()
  {
    this.cellProvider.initCellProvider(this);

    return this.cellProvider.getStartHtml();
  }

  protected String postEnd()
  {
    return this.cellProvider.getEndHtml();
  }

  public String getSort() {
    return this.sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getSortDefault() {
    return this.sortDefault;
  }

  public void setSortDefault(String sortDefault) {
    this.sortDefault = sortDefault;
  }

  public String getStyle() {
    return this.style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public String getHeight() {
    return this.height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getWidth() {
    return this.width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getAlign() {
    return this.align;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public String getPlugin_type() {
    return this.plugin_type;
  }

  public void setPlugin_type(String plugin_type) {
    this.plugin_type = plugin_type;
  }

  public int getIsAjax() {
    return this.isAjax;
  }

  public void setIsAjax(int isAjax) {
    this.isAjax = isAjax;
  }

  public int getIsSort() {
    return this.isSort;
  }

  public void setIsSort(int isSort) {
    this.isSort = isSort;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }
  public String getClazz() {
    return this.clazz;
  }
  public void setClazz(String clazz) {
    this.clazz = clazz;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.GridCellTaglib
 * JD-Core Version:    0.6.1
 */