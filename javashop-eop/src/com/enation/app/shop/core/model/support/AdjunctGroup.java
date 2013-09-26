package com.enation.app.shop.core.model.support;

public class AdjunctGroup
{
  private Integer couptype;
  private Integer minnum;
  private Integer maxnum;
  private Double coup;

  public Double getCoup()
  {
    return this.coup;
  }

  public void setCoup(Double coup) {
    this.coup = coup;
  }

  public Integer getCouptype() {
    return this.couptype;
  }

  public void setCouptype(Integer couptype) {
    this.couptype = couptype;
  }

  public Integer getMaxnum() {
    return this.maxnum;
  }

  public void setMaxnum(Integer maxnum) {
    this.maxnum = maxnum;
  }

  public Integer getMinnum() {
    return this.minnum;
  }

  public void setMinnum(Integer minnum) {
    this.minnum = minnum;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.AdjunctGroup
 * JD-Core Version:    0.6.1
 */