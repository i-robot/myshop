package com.enation.eop.resource.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;

public class Resource
{
  private Integer id;
  private Integer deleteflag = Integer.valueOf(0);
  private String productId;

  public Integer getDeleteflag()
  {
    return this.deleteflag;
  }

  public void setDeleteflag(Integer deleteflag) {
    this.deleteflag = deleteflag;
  }
  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  @NotDbField
  public String getProductId()
  {
    return this.productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.Resource
 * JD-Core Version:    0.6.1
 */