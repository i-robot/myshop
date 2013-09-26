package com.enation.app.base.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;

public class Role
{
  private Integer roleid;
  private String rolename;
  private String rolememo;
  private int[] actids;

  @PrimaryKeyField
  public Integer getRoleid()
  {
    return this.roleid;
  }

  public void setRoleid(Integer roleid) {
    this.roleid = roleid;
  }

  public String getRolename() {
    return this.rolename;
  }

  public void setRolename(String rolename) {
    this.rolename = rolename;
  }

  public String getRolememo() {
    return this.rolememo;
  }

  public void setRolememo(String rolememo) {
    this.rolememo = rolememo;
  }

  @NotDbField
  public int[] getActids() {
    return this.actids;
  }

  public void setActids(int[] actids) {
    this.actids = actids;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.Role
 * JD-Core Version:    0.6.1
 */