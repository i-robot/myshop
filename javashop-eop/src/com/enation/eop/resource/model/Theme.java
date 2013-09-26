package com.enation.eop.resource.model;

public class Theme extends Resource
{
  private String themename;
  private String path;
  private String author;
  private String version;
  private String thumb = "preview.png";
  private Integer siteid;

  public String getThumb()
  {
    return this.thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getThemename() {
    return this.themename;
  }

  public void setThemename(String themename) {
    this.themename = themename;
  }

  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Integer getSiteid() {
    return this.siteid;
  }

  public void setSiteid(Integer siteid) {
    this.siteid = siteid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.Theme
 * JD-Core Version:    0.6.1
 */