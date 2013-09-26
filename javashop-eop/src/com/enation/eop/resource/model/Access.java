package com.enation.eop.resource.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class Access
  implements Serializable
{
  private static final long serialVersionUID = -4339848792738875940L;
  private Integer id;
  private String ip;
  private String url;
  private String page;
  private String area;
  private int access_time;
  private int stay_time;
  private int point;
  private String membername;

  public String getIp()
  {
    return this.ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getPage() {
    return this.page;
  }
  public void setPage(String page) {
    this.page = page;
  }
  public String getArea() {
    return this.area;
  }
  public void setArea(String area) {
    this.area = area;
  }
  public int getAccess_time() {
    return this.access_time;
  }

  public void setAccess_time(int accessTime) {
    this.access_time = accessTime;
  }
  public int getStay_time() {
    return this.stay_time;
  }
  public void setStay_time(int stayTime) {
    this.stay_time = stayTime;
  }
  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public int getPoint() {
    return this.point;
  }
  public void setPoint(int point) {
    this.point = point;
  }
  public String getMembername() {
    return this.membername;
  }
  public void setMembername(String membername) {
    this.membername = membername;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.Access
 * JD-Core Version:    0.6.1
 */