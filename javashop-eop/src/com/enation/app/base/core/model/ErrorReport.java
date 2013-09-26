package com.enation.app.base.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class ErrorReport
{
  private Integer id;
  private String error;
  private String info;
  private long dateline;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getError() {
    return this.error;
  }
  public void setError(String error) {
    this.error = error;
  }
  public long getDatelineLong() {
    return this.dateline;
  }
  public void setDateline(long dateline) {
    this.dateline = dateline;
  }
  public String getInfo() {
    return this.info;
  }
  public void setInfo(String info) {
    this.info = info;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.ErrorReport
 * JD-Core Version:    0.6.1
 */