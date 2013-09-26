package com.enation.app.shop.core.model;

import com.enation.eop.resource.model.AdminUser;

public class DepotUser extends AdminUser
{
  private Integer depotid;

  public Integer getDepotid()
  {
    return this.depotid;
  }

  public void setDepotid(Integer depotid) {
    this.depotid = depotid;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.DepotUser
 * JD-Core Version:    0.6.1
 */