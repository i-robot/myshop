package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class GoodsType
  implements Serializable
{
  private Integer type_id;
  private String name;
  private String props;
  private String params;
  private int disabled;
  private int is_physical;
  private int have_prop;
  private int have_parm;
  private int join_brand;
  private int have_field;
  private Integer[] brand_ids;

  public int getDisabled()
  {
    return this.disabled;
  }
  public void setDisabled(int disabled) {
    this.disabled = disabled;
  }
  public int getHave_parm() {
    return this.have_parm;
  }
  public void setHave_parm(int have_parm) {
    this.have_parm = have_parm;
  }
  public int getHave_prop() {
    return this.have_prop;
  }
  public void setHave_prop(int have_prop) {
    this.have_prop = have_prop;
  }
  public int getIs_physical() {
    return this.is_physical;
  }
  public void setIs_physical(int is_physical) {
    this.is_physical = is_physical;
  }
  public int getJoin_brand() {
    return this.join_brand;
  }
  public void setJoin_brand(int join_brand) {
    this.join_brand = join_brand;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getParams() {
    return this.params;
  }
  public void setParams(String params) {
    this.params = params;
  }
  public String getProps() {
    return this.props;
  }
  public void setProps(String props) {
    this.props = props;
  }
  @PrimaryKeyField
  public Integer getType_id() {
    return this.type_id;
  }
  public void setType_id(Integer type_id) {
    this.type_id = type_id;
  }
  public Integer[] getBrand_ids() {
    return this.brand_ids;
  }
  public void setBrand_ids(Integer[] brand_ids) {
    this.brand_ids = brand_ids;
  }
  public int getHave_field() {
    return this.have_field;
  }
  public void setHave_field(int haveField) {
    this.have_field = haveField;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.GoodsType
 * JD-Core Version:    0.6.1
 */