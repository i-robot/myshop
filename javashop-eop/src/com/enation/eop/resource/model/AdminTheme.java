package com.enation.eop.resource.model;

public class AdminTheme extends Resource
{
  private String themename;
  private String path;
  private String author;
  private String version;
  private String thumb = "preview.png";
  private int framemode;

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

  public int getFramemode() {
    return this.framemode;
  }

  public void setFramemode(int framemode) {
    this.framemode = framemode;
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
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.AdminTheme
 * JD-Core Version:    0.6.1
 */