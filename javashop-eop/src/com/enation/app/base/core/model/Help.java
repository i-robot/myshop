package com.enation.app.base.core.model;

import com.enation.framework.database.PrimaryKeyField;

public class Help
{
  private Integer id;
  private String helpid;
  private String title;
  private String content;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getHelpid() {
    return this.helpid;
  }

  public void setHelpid(String helpid) {
    this.helpid = helpid;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.Help
 * JD-Core Version:    0.6.1
 */