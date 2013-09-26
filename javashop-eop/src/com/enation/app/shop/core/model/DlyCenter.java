package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class DlyCenter
  implements Serializable
{
  private Integer dly_center_id;
  private String name;
  private String address;
  private String province;
  private String city;
  private String region;
  private Integer province_id;
  private Integer city_id;
  private Integer region_id;
  private String zip;
  private String phone;
  private String uname;
  private String cellphone;
  private String sex;
  private String memo;
  private String disabled;

  @PrimaryKeyField
  public Integer getDly_center_id()
  {
    return this.dly_center_id;
  }
  public void setDly_center_id(Integer dlyCenterId) {
    this.dly_center_id = dlyCenterId;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getAddress() {
    return this.address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getProvince() {
    return this.province;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public String getCity() {
    return this.city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getRegion() {
    return this.region;
  }
  public void setRegion(String region) {
    this.region = region;
  }

  public Integer getProvince_id() {
    return this.province_id;
  }
  public void setProvince_id(Integer provinceId) {
    this.province_id = provinceId;
  }
  public Integer getCity_id() {
    return this.city_id;
  }
  public void setCity_id(Integer cityId) {
    this.city_id = cityId;
  }
  public Integer getRegion_id() {
    return this.region_id;
  }
  public void setRegion_id(Integer regionId) {
    this.region_id = regionId;
  }
  public String getZip() {
    return this.zip;
  }
  public void setZip(String zip) {
    this.zip = zip;
  }
  public String getPhone() {
    return this.phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getUname() {
    return this.uname;
  }
  public void setUname(String uname) {
    this.uname = uname;
  }
  public String getCellphone() {
    return this.cellphone;
  }
  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }
  public String getSex() {
    return this.sex;
  }
  public void setSex(String sex) {
    this.sex = sex;
  }
  public String getMemo() {
    return this.memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }
  public String getDisabled() {
    return this.disabled;
  }
  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.DlyCenter
 * JD-Core Version:    0.6.1
 */