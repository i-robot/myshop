package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.component.spec.service.ISpecManager;
import com.enation.app.shop.component.spec.service.ISpecValueManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class GoodsSpecAction extends WWAction
{
  private ISpecManager specManager;
  private ISpecValueManager specValueManager;
  private List specList;
  private Integer spec_id;
  private Integer value_id;
  private Map spec;
  private List valueList;

  public String execute()
  {
    this.specList = this.specManager.list();
    return "select";
  }

  public String getValues() {
    this.spec = this.specManager.get(this.spec_id);
    this.valueList = this.specValueManager.list(this.spec_id);
    return "values";
  }

  public String addOne() {
    this.spec = this.specValueManager.get(this.value_id);
    return "add_one";
  }

  public String addAll()
  {
    return "add_all";
  }

  public ISpecManager getSpecManager() {
    return this.specManager;
  }

  public void setSpecManager(ISpecManager specManager) {
    this.specManager = specManager;
  }

  public List getSpecList() {
    return this.specList;
  }

  public void setSpecList(List specList) {
    this.specList = specList;
  }

  public ISpecValueManager getSpecValueManager() {
    return this.specValueManager;
  }

  public void setSpecValueManager(ISpecValueManager specValueManager) {
    this.specValueManager = specValueManager;
  }

  public Map getSpec() {
    return this.spec;
  }

  public void setSpec(Map spec) {
    this.spec = spec;
  }

  public List getValueList() {
    return this.valueList;
  }

  public void setValueList(List valueList) {
    this.valueList = valueList;
  }

  public Integer getSpec_id() {
    return this.spec_id;
  }

  public void setSpec_id(Integer specId) {
    this.spec_id = specId;
  }

  public Integer getValue_id() {
    return this.value_id;
  }

  public void setValue_id(Integer valueId) {
    this.value_id = valueId;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsSpecAction
 * JD-Core Version:    0.6.1
 */