package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class Template
  implements Serializable
{
  private Integer template_id;
  private String name;
  private String theme;
  private String author;
  private String site;
  private Long create_time;
  private Integer is_active;
  private String version;

  public String getAuthor()
  {
    return this.author;
  }

  public void setAuthor(String author)
  {
    this.author = author;
  }

  public Long getCreate_time()
  {
    return this.create_time;
  }

  public void setCreate_time(Long create_time)
  {
    this.create_time = create_time;
  }

  public Integer getIs_active()
  {
    return this.is_active;
  }

  public void setIs_active(Integer is_active)
  {
    this.is_active = is_active;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getSite()
  {
    return this.site;
  }

  public void setSite(String site)
  {
    this.site = site;
  }

  @PrimaryKeyField
  public Integer getTemplate_id()
  {
    return this.template_id;
  }

  public void setTemplate_id(Integer template_id)
  {
    this.template_id = template_id;
  }

  public String getTheme()
  {
    return this.theme;
  }

  public void setTheme(String theme)
  {
    this.theme = theme;
  }

  public String getVersion()
  {
    return this.version;
  }

  public void setVersion(String version)
  {
    this.version = version;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Template
 * JD-Core Version:    0.6.1
 */