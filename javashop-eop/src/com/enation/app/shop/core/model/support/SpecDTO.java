package com.enation.app.shop.core.model.support;

import java.util.ArrayList;
import java.util.List;

public class SpecDTO
{
  private String[] spec_store;
  private String[] spec_price;
  private String[] spec_sn;
  private String spec_str;
  private List specs;

  public SpecDTO()
  {
    this.specs = new ArrayList();
  }

  public void addSpec(String spec) {
    this.specs.add(spec);
  }
  public String[] getSpec_price() {
    return this.spec_price;
  }
  public void setSpec_price(String[] spec_price) {
    this.spec_price = spec_price;
  }
  public String[] getSpec_store() {
    return this.spec_store;
  }
  public void setSpec_store(String[] spec_store) {
    this.spec_store = spec_store;
  }
  public String getSpec_str() {
    return this.spec_str;
  }
  public void setSpec_str(String spec_str) {
    this.spec_str = spec_str;
  }
  public String[] getSpec_sn() {
    return this.spec_sn;
  }
  public void setSpec_sn(String[] spec_sn) {
    this.spec_sn = spec_sn;
  }
  public List getSpecs() {
    return this.specs;
  }
  public void setSpecs(List specs) {
    this.specs = specs;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.SpecDTO
 * JD-Core Version:    0.6.1
 */