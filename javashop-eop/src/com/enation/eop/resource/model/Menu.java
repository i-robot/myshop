package com.enation.eop.resource.model;

import com.enation.framework.database.NotDbField;
import java.util.List;

public class Menu extends Resource
{
  private Integer pid;
  private String title;
  private String url;
  private String target;
  private Integer sorder;
  private Integer menutype;
  private String datatype;
  private String appid;
  private List<Menu> children;
  private boolean hasChildren;
  public static final int MENU_TYPE_SYS = 1;
  public static final int MENU_TYPE_APP = 2;
  public static final int MENU_TYPE_EXT = 3;
  private Integer selected;

  @NotDbField
  public boolean getHasChildren()
  {
    this.hasChildren = ((this.children != null) && (!this.children.isEmpty()));
    return this.hasChildren;
  }

  public String getDatatype()
  {
    return this.datatype;
  }
  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  public Integer getSelected()
  {
    return this.selected;
  }
  public void setSelected(Integer selected) {
    this.selected = selected;
  }
  public Integer getPid() {
    return this.pid;
  }
  public void setPid(Integer pid) {
    this.pid = pid;
  }
  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
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
  public Integer getSorder() {
    return this.sorder;
  }
  public void setSorder(Integer sorder) {
    this.sorder = sorder;
  }
  public Integer getMenutype() {
    return this.menutype;
  }
  public void setMenutype(Integer menutype) {
    this.menutype = menutype;
  }
  public List<Menu> getChildren() {
    return this.children;
  }
  public void setChildren(List<Menu> children) {
    this.children = children;
  }
  public String getAppid() {
    return this.appid;
  }
  public void setAppid(String appid) {
    this.appid = appid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.Menu
 * JD-Core Version:    0.6.1
 */