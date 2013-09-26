package com.enation.eop.processor.httpcache;

public class HttpCacheStatus
{
  private boolean isModified;
  private long lasyModified;

  public boolean isModified()
  {
    return this.isModified;
  }
  public void setModified(boolean isModified) {
    this.isModified = isModified;
  }
  public long getLasyModified() {
    return this.lasyModified;
  }
  public void setLasyModified(long lasyModified) {
    this.lasyModified = lasyModified;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.httpcache.HttpCacheStatus
 * JD-Core Version:    0.6.1
 */