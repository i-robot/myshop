package com.enation.eop.resource.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class EopUserAdmin
  implements Serializable
{
  private static final long serialVersionUID = -5111983361750207866L;
  private Integer id;
  private Integer userid;
  private String username;
  private String realname;
  private String password;
  private String tel;
  private String mobile;
  private String email;
  private String qq;
  private Integer defaultsiteid;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserid() {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRealname() {
    return this.realname;
  }

  public void setRealname(String realname) {
    this.realname = realname;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public String getQq() {
    return this.qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public Integer getDefaultsiteid() {
    return this.defaultsiteid;
  }

  public void setDefaultsiteid(Integer defaultsiteid) {
    this.defaultsiteid = defaultsiteid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.EopUserAdmin
 * JD-Core Version:    0.6.1
 */