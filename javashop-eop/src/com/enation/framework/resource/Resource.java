package com.enation.framework.resource;

public class Resource
{
  private String src;
  private int compress;
  private int merge;
  private String type;
  private boolean iscommon;

  public String getSrc()
  {
    return this.src;
  }
  public void setSrc(String src) {
    this.src = src;
  }
  public int getCompress() {
    return this.compress;
  }
  public void setCompress(int compress) {
    this.compress = compress;
  }
  public int getMerge() {
    return this.merge;
  }
  public void setMerge(int merge) {
    this.merge = merge;
  }
  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public boolean isIscommon() {
    return this.iscommon;
  }
  public void setIscommon(boolean iscommon) {
    this.iscommon = iscommon;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.resource.Resource
 * JD-Core Version:    0.6.1
 */