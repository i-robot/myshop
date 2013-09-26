package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;
import java.util.List;

public class Cat
  implements Serializable
{
  private static final long serialVersionUID = 5115916366849703086L;
  protected Integer cat_id;
  protected String name;
  protected Integer parent_id;
  protected String cat_path;
  protected Integer goods_count;
  protected int cat_order;
  protected Integer type_id;
  protected String list_show;
  protected String image;
  private boolean hasChildren;
  private List<Cat> children;
  private String type_name;

  public Cat()
  {
    this.hasChildren = false;
  }

  @PrimaryKeyField
  public Integer getCat_id()
  {
    return this.cat_id;
  }

  public void setCat_id(Integer cat_id)
  {
    this.cat_id = cat_id;
  }

  public int getCat_order()
  {
    return this.cat_order;
  }

  public void setCat_order(int cat_order)
  {
    this.cat_order = cat_order;
  }

  public String getCat_path()
  {
    return this.cat_path;
  }

  public void setCat_path(String cat_path)
  {
    this.cat_path = cat_path;
  }

  public Integer getGoods_count()
  {
    return this.goods_count;
  }

  public void setGoods_count(Integer goods_count)
  {
    this.goods_count = goods_count;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Integer getParent_id()
  {
    return this.parent_id;
  }

  public void setParent_id(Integer parent_id)
  {
    this.parent_id = parent_id;
  }

  public Integer getType_id()
  {
    return this.type_id;
  }

  public void setType_id(Integer type_id)
  {
    this.type_id = type_id;
  }

  public String getList_show()
  {
    return this.list_show;
  }

  public void setList_show(String listShow)
  {
    this.list_show = listShow;
  }

  @NotDbField
  public List<Cat> getChildren() {
    return this.children;
  }

  public void setChildren(List<Cat> children)
  {
    this.children = children;
  }

  @NotDbField
  public String getType_name()
  {
    return this.type_name;
  }

  public void setType_name(String typeName)
  {
    this.type_name = typeName;
  }

  @NotDbField
  public boolean getHasChildren() {
    this.hasChildren = ((this.children != null) && (!this.children.isEmpty()));
    return this.hasChildren;
  }

  public void setHasChildren(boolean hasChildren)
  {
    this.hasChildren = hasChildren;
  }

  public String getImage()
  {
    return this.image;
  }

  public void setImage(String image)
  {
    this.image = image;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Cat
 * JD-Core Version:    0.6.1
 */