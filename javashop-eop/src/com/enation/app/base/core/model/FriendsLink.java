package com.enation.app.base.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class FriendsLink
  implements Serializable
{
  private Integer link_id;
  private String name;
  private String url;
  private String logo;
  private Integer sort;

  @PrimaryKeyField
  public Integer getLink_id()
  {
    return this.link_id;
  }

  public void setLink_id(Integer link_id) {
    this.link_id = link_id;
  }

  public String getLogo() {
    return this.logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getSort() {
    return this.sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.FriendsLink
 * JD-Core Version:    0.6.1
 */