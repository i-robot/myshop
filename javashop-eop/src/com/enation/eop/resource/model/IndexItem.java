package com.enation.eop.resource.model;

import com.enation.framework.database.PrimaryKeyField;

public class IndexItem
{
  private Integer id;
  private String title;
  private String url;
  private int sort;

  public String getTitle()
  {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public int getSort() {
    return this.sort;
  }
  public void setSort(int sort) {
    this.sort = sort;
  }
  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.IndexItem
 * JD-Core Version:    0.6.1
 */