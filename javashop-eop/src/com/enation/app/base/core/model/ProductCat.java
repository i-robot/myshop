package com.enation.app.base.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class ProductCat
{
  private Integer id;
  private String name;
  private Integer num;
  private Integer sort;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getNum() {
    return this.num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Integer getSort() {
    return this.sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.ProductCat
 * JD-Core Version:    0.6.1
 */