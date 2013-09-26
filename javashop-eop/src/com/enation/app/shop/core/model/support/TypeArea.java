package com.enation.app.shop.core.model.support;

import com.enation.framework.database.NotDbField;

public class TypeArea
{
  private Integer type_id;
  private String area_id_group;
  private String area_name_group;
  private String expressions;
  private Integer has_cod;
  private String config;
  private TypeAreaConfig typeAreaConfig;

  public Integer getType_id()
  {
    return this.type_id;
  }
  public void setType_id(Integer typeId) {
    this.type_id = typeId;
  }
  public String getArea_id_group() {
    return this.area_id_group;
  }
  public void setArea_id_group(String areaIdGroup) {
    this.area_id_group = areaIdGroup;
  }
  public String getArea_name_group() {
    return this.area_name_group;
  }
  public void setArea_name_group(String areaNameGroup) {
    this.area_name_group = areaNameGroup;
  }
  public String getExpressions() {
    return this.expressions;
  }
  public void setExpressions(String expressions) {
    this.expressions = expressions;
  }
  public Integer getHas_cod() {
    return this.has_cod;
  }
  public void setHas_cod(Integer hasCod) {
    this.has_cod = hasCod;
  }
  public String getConfig() {
    return this.config;
  }
  public void setConfig(String config) {
    this.config = config;
  }

  @NotDbField
  public TypeAreaConfig getTypeAreaConfig() {
    return this.typeAreaConfig;
  }
  public void setTypeAreaConfig(TypeAreaConfig typeAreaConfig) {
    this.typeAreaConfig = typeAreaConfig;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.TypeArea
 * JD-Core Version:    0.6.1
 */