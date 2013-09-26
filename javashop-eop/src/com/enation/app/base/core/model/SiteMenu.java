package com.enation.app.base.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;
import java.util.List;

public class SiteMenu
  implements Serializable
{
  private static final long serialVersionUID = 6671862044724283307L;
  private Integer menuid;
  private Integer parentid;
  private String name;
  private String url;
  private String target;
  private Integer sort;
  private List<SiteMenu> children;
  private boolean hasChildren;

  public SiteMenu()
  {
    this.hasChildren = false;
  }

  @PrimaryKeyField
  public Integer getMenuid()
  {
    return this.menuid;
  }

  public void setMenuid(Integer menuid) {
    this.menuid = menuid;
  }

  public Integer getParentid() {
    return this.parentid;
  }

  public void setParentid(Integer parentid) {
    this.parentid = parentid;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTarget() {
    return this.target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  @NotDbField
  public List<SiteMenu> getChildren() {
    return this.children;
  }

  public void setChildren(List<SiteMenu> children) {
    this.children = children;
  }

  public Integer getSort() {
    return this.sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  @NotDbField
  public boolean getHasChildren() {
    this.hasChildren = ((this.children != null) && (!this.children.isEmpty()));

    return this.hasChildren;
  }

  public void setHasChildren(boolean hasChildren) {
    this.hasChildren = hasChildren;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.SiteMenu
 * JD-Core Version:    0.6.1
 */