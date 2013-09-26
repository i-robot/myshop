package com.enation.app.shop.core.model.support;

import com.enation.app.shop.core.model.GoodsParam;
import java.util.List;

public class ParamGroup
{
  private String name;
  private List<GoodsParam> paramList;

  public String getName()
  {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public List<GoodsParam> getParamList() {
    return this.paramList;
  }
  public void setParamList(List<GoodsParam> paramList) {
    this.paramList = paramList;
  }

  public int getParamNum()
  {
    if (this.paramList == null) return 0;
    return this.paramList.size();
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.ParamGroup
 * JD-Core Version:    0.6.1
 */