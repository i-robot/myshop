package com.enation.app.shop.core.model.support;

import com.enation.app.shop.core.model.Attribute;
import com.enation.app.shop.core.model.GoodsType;
import java.util.List;

public class GoodsTypeDTO extends GoodsType
{
  private List<Attribute> propList;
  private ParamGroup[] paramGroups;
  private List brandList;

  public ParamGroup[] getParamGroups()
  {
    return this.paramGroups;
  }
  public void setParamGroups(ParamGroup[] paramGroups) {
    this.paramGroups = paramGroups;
  }
  public List<Attribute> getPropList() {
    return this.propList;
  }
  public void setPropList(List<Attribute> propList) {
    this.propList = propList;
  }
  public List getBrandList() {
    return this.brandList;
  }
  public void setBrandList(List brandList) {
    this.brandList = brandList;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.GoodsTypeDTO
 * JD-Core Version:    0.6.1
 */