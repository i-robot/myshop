package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class Article
  implements Serializable
{
  private Integer id;
  private String title;
  private String content;
  private Long create_time;
  private Integer cat_id;

  public Integer getCat_id()
  {
    return this.cat_id;
  }

  public void setCat_id(Integer cat_id) {
    this.cat_id = cat_id;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getCreate_time() {
    return this.create_time;
  }

  public void setCreate_time(Long create_time) {
    this.create_time = create_time;
  }

  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Article
 * JD-Core Version:    0.6.1
 */