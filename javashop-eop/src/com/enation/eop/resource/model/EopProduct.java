package com.enation.eop.resource.model;

import com.enation.framework.database.PrimaryKeyField;

public class EopProduct
{
  private Integer id;
  private String productid;
  private String product_name;
  private String author;
  private String descript;
  private Long createtime;
  private String version;
  private String preview;
  private Integer catid;
  private Integer typeid;
  private Integer colorid;
  private Integer pstate;
  private String copyright;
  private Integer sort;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getProductid() {
    return this.productid;
  }

  public void setProductid(String productid) {
    this.productid = productid;
  }

  public String getProduct_name() {
    return this.product_name;
  }

  public void setProduct_name(String productName) {
    this.product_name = productName;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDescript() {
    return this.descript;
  }

  public void setDescript(String descript) {
    this.descript = descript;
  }

  public Long getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(Long createtime) {
    this.createtime = createtime;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getPreview() {
    return this.preview;
  }

  public void setPreview(String preview) {
    this.preview = preview;
  }

  public Integer getCatid() {
    return this.catid;
  }

  public void setCatid(Integer catid) {
    this.catid = catid;
  }

  public Integer getTypeid() {
    return this.typeid;
  }

  public void setTypeid(Integer typeid) {
    this.typeid = typeid;
  }

  public Integer getSort() {
    return this.sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public Integer getColorid() {
    return this.colorid;
  }

  public void setColorid(Integer colorid) {
    this.colorid = colorid;
  }

  public Integer getPstate() {
    return this.pstate;
  }

  public void setPstate(Integer pstate) {
    this.pstate = pstate;
  }

  public String getCopyright() {
    return this.copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.EopProduct
 * JD-Core Version:    0.6.1
 */