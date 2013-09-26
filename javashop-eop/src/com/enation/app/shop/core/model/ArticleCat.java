package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.util.List;

public class ArticleCat
{
  protected Integer cat_id;
  protected String name;
  protected Integer parent_id;
  protected String cat_path;
  protected int cat_order;
  private boolean hasChildren;
  private List<ArticleCat> children;

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

  @NotDbField
  public boolean getHasChildren()
  {
    this.hasChildren = ((this.children != null) && (!this.children.isEmpty()));
    return this.hasChildren;
  }

  public void setHasChildren(boolean hasChildren)
  {
    this.hasChildren = hasChildren;
  }

  @NotDbField
  public List<ArticleCat> getChildren()
  {
    return this.children;
  }

  public void setChildren(List<ArticleCat> children)
  {
    this.children = children;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.ArticleCat
 * JD-Core Version:    0.6.1
 */