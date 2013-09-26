package com.enation.app.base.core.service.solution;

public class Installer
{
  private Integer id;
  private String ip;
  private String version;
  private String remark;
  private Long installtime;
  private String domain;
  private String area;

  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getIp() {
    return this.ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public String getVersion() {
    return this.version;
  }
  public void setVersion(String version) {
    this.version = version;
  }
  public String getRemark() {
    return this.remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public Long getInstalltime() {
    return this.installtime;
  }
  public void setInstalltime(Long installtime) {
    this.installtime = installtime;
  }
  public String getDomain() {
    return this.domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
  }
  public String getArea() {
    return this.area;
  }
  public void setArea(String area) {
    this.area = area;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.Installer
 * JD-Core Version:    0.6.1
 */