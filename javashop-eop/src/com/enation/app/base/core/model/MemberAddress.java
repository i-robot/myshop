package com.enation.app.base.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class MemberAddress
  implements Serializable
{
  private Integer addr_id;
  private Integer member_id;
  private String name;
  private String country;
  private Integer province_id;
  private Integer city_id;
  private Integer region_id;
  private String province;
  private String city;
  private String region;
  private String addr;
  private String zip;
  private String tel;
  private String mobile;
  private Integer def_addr;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @PrimaryKeyField
  public Integer getAddr_id() {
    return this.addr_id;
  }

  public void setAddr_id(Integer addr_id) {
    this.addr_id = addr_id;
  }

  public Integer getMember_id() {
    return this.member_id;
  }

  public void setMember_id(Integer member_id) {
    this.member_id = member_id;
  }

  public Integer getProvince_id() {
    return this.province_id;
  }

  public void setProvince_id(Integer province_id) {
    this.province_id = province_id;
  }

  public Integer getCity_id() {
    return this.city_id;
  }

  public void setCity_id(Integer city_id) {
    this.city_id = city_id;
  }

  public Integer getRegion_id() {
    return this.region_id;
  }

  public void setRegion_id(Integer region_id) {
    this.region_id = region_id;
  }

  public Integer getDef_addr() {
    return this.def_addr;
  }

  public void setDef_addr(Integer def_addr) {
    this.def_addr = def_addr;
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

  public String getAddr() {
    return this.addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }

  public String getZip() {
    return this.zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getMobile() {
    return this.mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.MemberAddress
 * JD-Core Version:    0.6.1
 */