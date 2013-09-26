package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class Depot
{
  public Integer id;
  public String name;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Depot
 * JD-Core Version:    0.6.1
 */