package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.io.File;
import java.io.Serializable;

public class Brand
  implements Serializable
{
  private Integer brand_id;
  private String name;
  private String logo;
  private String keywords;
  private String brief;
  private String url;
  private Integer disabled;
  private File file;
  private String fileFileName;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogo() {
    return this.logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getBrief() {
    return this.brief;
  }

  public void setBrief(String brief) {
    this.brief = brief;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getDisabled()
  {
    return this.disabled;
  }

  public void setDisabled(Integer disabled)
  {
    this.disabled = disabled;
  }

  @PrimaryKeyField
  public Integer getBrand_id() {
    return this.brand_id;
  }

  public void setBrand_id(Integer brand_id) {
    this.brand_id = brand_id;
  }

  @NotDbField
  public File getFile()
  {
    return this.file;
  }

  public void setFile(File file)
  {
    this.file = file;
  }

  @NotDbField
  public String getFileFileName()
  {
    return this.fileFileName;
  }

  public void setFileFileName(String fileFileName)
  {
    this.fileFileName = fileFileName;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Brand
 * JD-Core Version:    0.6.1
 */