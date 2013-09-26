package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class Seo
{
  private Integer id;
  private String title;
  private String meta_keywords;
  private String meta_description;
  private Integer use_static;
  private Integer noindex_catalog;
  private Integer userid;
  private Integer siteid;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMeta_keywords() {
    return this.meta_keywords;
  }

  public void setMeta_keywords(String metaKeywords) {
    this.meta_keywords = metaKeywords;
  }

  public String getMeta_description() {
    return this.meta_description;
  }

  public void setMeta_description(String metaDescription) {
    this.meta_description = metaDescription;
  }

  public Integer getUse_static() {
    return this.use_static;
  }

  public void setUse_static(Integer useStatic) {
    this.use_static = useStatic;
  }

  public Integer getNoindex_catalog() {
    return this.noindex_catalog;
  }

  public void setNoindex_catalog(Integer noindexCatalog) {
    this.noindex_catalog = noindexCatalog;
  }

  public Integer getUserid() {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public Integer getSiteid() {
    return this.siteid;
  }

  public void setSiteid(Integer siteid) {
    this.siteid = siteid;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Seo
 * JD-Core Version:    0.6.1
 */