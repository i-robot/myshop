package com.enation.eop.resource.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;
import java.util.List;

public class EopApp
  implements Serializable
{
  private static final long serialVersionUID = 9088172178237139695L;
  private Integer id;
  private String appid;
  private String app_name;
  private String author;
  private String descript;
  private int deployment;
  private String path;
  private String installuri;
  private String version;
  private List<String> updateLogList;
  private String authorizationcode;

  public String getAppid()
  {
    return this.appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getApp_name()
  {
    return this.app_name;
  }

  public void setApp_name(String appName)
  {
    this.app_name = appName;
  }

  public String getAuthor()
  {
    return this.author;
  }

  public void setAuthor(String author)
  {
    this.author = author;
  }

  public String getDescript()
  {
    return this.descript;
  }

  public void setDescript(String descript)
  {
    this.descript = descript;
  }

  public int getDeployment()
  {
    return this.deployment;
  }

  public void setDeployment(int deployment)
  {
    this.deployment = deployment;
  }

  public String getAuthorizationcode()
  {
    return this.authorizationcode;
  }

  public void setAuthorizationcode(String authorizationcode)
  {
    this.authorizationcode = authorizationcode;
  }

  public String getInstalluri() {
    return this.installuri;
  }

  public void setInstalluri(String installuri) {
    this.installuri = installuri;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @NotDbField
  public List<String> getUpdateLogList() {
    return this.updateLogList;
  }

  public void setUpdateLogList(List<String> updateLogList) {
    this.updateLogList = updateLogList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.EopApp
 * JD-Core Version:    0.6.1
 */