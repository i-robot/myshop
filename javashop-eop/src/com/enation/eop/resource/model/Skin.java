package com.enation.eop.resource.model;

public class Skin extends Resource
{
  private String skinname;
  private String path;
  private String thumb = "preview.png";

  public String getSkinname()
  {
    return this.skinname;
  }

  public void setSkinname(String skinname) {
    this.skinname = skinname;
  }

  public String getThumb()
  {
    return this.thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.Skin
 * JD-Core Version:    0.6.1
 */