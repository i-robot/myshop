package com.enation.app.base.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class AuthAction
  implements Serializable
{
  private static final long serialVersionUID = 4831369457068242964L;
  private Integer actid;
  private String name;
  private String type;
  private String objvalue;

  @PrimaryKeyField
  public Integer getActid()
  {
    return this.actid;
  }
  public void setActid(Integer actid) {
    this.actid = actid;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getObjvalue() {
    return this.objvalue;
  }
  public void setObjvalue(String objvalue) {
    this.objvalue = objvalue;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.AuthAction
 * JD-Core Version:    0.6.1
 */