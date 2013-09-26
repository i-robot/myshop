package com.enation.eop.resource.model;

import com.enation.app.base.core.model.AuthAction;
import com.enation.framework.database.DynamicField;
import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;
import java.util.List;

public class AdminUser extends DynamicField
  implements Serializable
{
  private Integer userid;
  private String username;
  private String password;
  private int state;
  private String realname;
  private String userno;
  private String userdept;
  private String remark;
  private Long dateline;
  private int[] roleids;
  private int founder;
  private Integer siteid;
  private List<AuthAction> authList;

  @PrimaryKeyField
  public Integer getUserid()
  {
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
  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public int getState() {
    return this.state;
  }
  public void setState(int state) {
    this.state = state;
  }
  public String getRealname() {
    return this.realname;
  }
  public void setRealname(String realname) {
    this.realname = realname;
  }
  public String getUserno() {
    return this.userno;
  }
  public void setUserno(String userno) {
    this.userno = userno;
  }
  public String getUserdept() {
    return this.userdept;
  }
  public void setUserdept(String userdept) {
    this.userdept = userdept;
  }
  public String getRemark() {
    return this.remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public Long getDateline() {
    return this.dateline;
  }
  public void setDateline(Long dateline) {
    this.dateline = dateline;
  }

  public int getFounder() {
    return this.founder;
  }
  public void setFounder(int founder) {
    this.founder = founder;
  }
  @NotDbField
  public int[] getRoleids() {
    return this.roleids;
  }
  public void setRoleids(int[] roleids) {
    this.roleids = roleids;
  }
  public Integer getSiteid() {
    return this.siteid;
  }
  public void setSiteid(Integer siteid) {
    this.siteid = siteid;
  }
  @NotDbField
  public List<AuthAction> getAuthList() {
    return this.authList;
  }
  public void setAuthList(List<AuthAction> authList) {
    this.authList = authList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.AdminUser
 * JD-Core Version:    0.6.1
 */