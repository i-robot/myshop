package com.enation.eop.sdk.webapp.bean;

import com.enation.framework.database.Page;

public abstract class Grid
{
  private int iDisplayStart;
  private int iDisplayLength;
  private String iSortCol_0;
  private int iSortingCols;
  private String sSearch;
  private String sEcho;
  private String json;
  private Page webpage;

  public void setJson(String json)
  {
    this.json = json;
  }

  public Page getWebpage()
  {
    if (this.webpage == null)
      this.webpage = execute(getPageNo(), getPageSize(), getOrder());
    return this.webpage;
  }
  public void setWebpage(Page webpage) {
    this.webpage = webpage;
  }

  public int getiDisplayStart() {
    return this.iDisplayStart;
  }
  public void setiDisplayStart(int iDisplayStart) {
    this.iDisplayStart = iDisplayStart;
  }
  public int getiDisplayLength() {
    return this.iDisplayLength;
  }
  public void setiDisplayLength(int iDisplayLength) {
    this.iDisplayLength = iDisplayLength;
  }
  public String getiSortCol_0() {
    return this.iSortCol_0;
  }
  public void setiSortCol_0(String iSortCol_0) {
    this.iSortCol_0 = iSortCol_0;
  }
  public int getiSortingCols() {
    return this.iSortingCols;
  }
  public void setiSortingCols(int iSortingCols) {
    this.iSortingCols = iSortingCols;
  }
  public String getsSearch() {
    return this.sSearch;
  }
  public void setsSearch(String sSearch) {
    this.sSearch = sSearch;
  }
  public String getsEcho() {
    return this.sEcho;
  }
  public void setsEcho(String sEcho) {
    this.sEcho = sEcho;
  }

  public int getPageNo()
  {
    if (getiDisplayLength() == 0) {
      return 1;
    }
    return getiDisplayStart() / getiDisplayLength() + 1;
  }
  public int getPageSize() {
    if (getiDisplayLength() == 0) {
      return 10;
    }
    return getiDisplayLength();
  }
  public String getOrder() {
    return "";
  }

  public abstract Page execute(int paramInt1, int paramInt2, String paramString);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.bean.Grid
 * JD-Core Version:    0.6.1
 */