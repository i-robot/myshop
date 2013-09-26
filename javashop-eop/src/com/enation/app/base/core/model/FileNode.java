package com.enation.app.base.core.model;

import com.enation.framework.util.StringUtil;

public class FileNode
{
  private static final String[] IMAGE_EXT = { "jpg", "gif", "bmp", "png" };
  private String name;
  private Long size;
  private Long lastmodify;
  private Integer isfolder;
  private String content;
  private String ext;

  public String getName()
  {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Long getSize() {
    return this.size;
  }
  public void setSize(Long size) {
    this.size = size;
  }
  public Long getLastmodify() {
    return this.lastmodify;
  }
  public void setLastmodify(Long lastmodify) {
    this.lastmodify = lastmodify;
  }
  public Integer getIsfolder() {
    return this.isfolder;
  }
  public void setIsfolder(Integer isfolder) {
    this.isfolder = isfolder;
  }
  public String getContent() {
    return this.content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getExt() {
    return this.ext;
  }
  public void setExt(String ext) {
    this.ext = ext;
  }

  public int getIsImage() {
    if (StringUtil.isEmpty(this.ext)) {
      return 0;
    }

    for (String imgExt : IMAGE_EXT) {
      if (imgExt.equals(this.ext)) {
        return 1;
      }
    }

    return 0;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.FileNode
 * JD-Core Version:    0.6.1
 */