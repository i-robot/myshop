package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class DlyArea
  implements Serializable
{
  private Integer area_id;
  private String name;

  @PrimaryKeyField
  public Integer getArea_id()
  {
    return this.area_id;
  }

  public void setArea_id(Integer areaId) {
    this.area_id = areaId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.DlyArea
 * JD-Core Version:    0.6.1
 */