package com.enation.eop.resource.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class EopUser
  implements Serializable
{
  private Integer id;
  private String username;
  private String companyname;
  private String password;
  private String address;
  private String legalrepresentative;
  private String linkman;
  private String tel;
  private String mobile;
  private String email;
  private String logofile;
  private String licensefile;
  private Integer defaultsiteid;
  private Integer usertype;
  private EopUserDetail userDetail;

  public Integer getDefaultsiteid()
  {
    return this.defaultsiteid;
  }

  public void setDefaultsiteid(Integer defaultsiteid) {
    this.defaultsiteid = defaultsiteid;
  }

  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLegalrepresentative() {
    return this.legalrepresentative;
  }

  public void setLegalrepresentative(String legalrepresentative) {
    this.legalrepresentative = legalrepresentative;
  }

  public String getLinkman() {
    return this.linkman;
  }

  public void setLinkman(String linkman) {
    this.linkman = linkman;
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

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLogofile() {
    return this.logofile;
  }

  public void setLogofile(String logofile) {
    this.logofile = logofile;
  }

  public String getLicensefile() {
    return this.licensefile;
  }

  public void setLicensefile(String licensefile) {
    this.licensefile = licensefile;
  }

  public Integer getUsertype() {
    return this.usertype;
  }

  public void setUsertype(Integer usertype) {
    this.usertype = usertype;
  }

  @NotDbField
  public EopUserDetail getUserDetail() {
    return this.userDetail;
  }

  public void setUserDetail(EopUserDetail userDetail) {
    this.userDetail = userDetail;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCompanyname() {
    return this.companyname;
  }

  public void setCompanyname(String companyname) {
    this.companyname = companyname;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.EopUser
 * JD-Core Version:    0.6.1
 */