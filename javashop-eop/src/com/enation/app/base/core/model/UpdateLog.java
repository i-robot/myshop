package com.enation.app.base.core.model;

import java.util.List;

public class UpdateLog
{
  private String appId;
  private List<String> logList;

  public String getAppId()
  {
    return this.appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public List<String> getLogList() {
    return this.logList;
  }

  public void setLogList(List<String> logList) {
    this.logList = logList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.UpdateLog
 * JD-Core Version:    0.6.1
 */