package com.enation.app.base.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class User
{
  private Integer userId;
  private String name;
  private String code;

  public String getCode()
  {
    return this.code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  public void finalize()
    throws Throwable
  {
  }

  @PrimaryKeyField
  public Integer getUserId()
  {
    return this.userId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String newVal)
  {
    this.name = newVal;
  }

  public void setUserId(Integer newVal)
  {
    this.userId = newVal;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.User
 * JD-Core Version:    0.6.1
 */