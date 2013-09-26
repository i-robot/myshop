package com.enation.app.base.core.model;

import com.enation.eop.sdk.context.EopSetting;
import java.util.List;

public class VersionState
{
  private String productid;
  private boolean haveNewVersion;
  private String newVersion;
  private List<UpdateLog> updateLogList;

  @Deprecated
  private String updateLog;

  public VersionState()
  {
    this.haveNewVersion = false;
    this.productid = EopSetting.PRODUCTID;
  }

  public boolean getHaveNewVersion()
  {
    return this.haveNewVersion;
  }

  public void setHaveNewVersion(boolean haveNewVersion) {
    this.haveNewVersion = haveNewVersion;
  }

  public String getNewVersion() {
    return this.newVersion;
  }

  public void setNewVersion(String newVersion) {
    this.newVersion = newVersion;
  }

  public List<UpdateLog> getUpdateLogList() {
    return this.updateLogList;
  }

  public void setUpdateLogList(List<UpdateLog> updateLogList) {
    this.updateLogList = updateLogList;
  }

  public String getProductid() {
    return this.productid;
  }

  public void setProductid(String productid) {
    this.productid = productid;
  }

  @Deprecated
  public String getUpdateLog()
  {
    return this.updateLog;
  }

  @Deprecated
  public void setUpdateLog(String updateLog) {
    this.updateLog = updateLog;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.VersionState
 * JD-Core Version:    0.6.1
 */