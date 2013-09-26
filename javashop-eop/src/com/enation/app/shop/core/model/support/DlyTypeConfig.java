package com.enation.app.shop.core.model.support;

public class DlyTypeConfig
{
  private Integer firstunit;
  private Integer continueunit;
  private Integer is_same;
  private Integer defAreaFee;
  private Double firstprice;
  private Double continueprice;
  private String expression;
  private Integer useexp;
  private Integer have_cod;

  public Integer getFirstunit()
  {
    return this.firstunit;
  }

  public void setFirstunit(Integer firstunit)
  {
    this.firstunit = firstunit;
  }

  public Integer getContinueunit()
  {
    return this.continueunit;
  }

  public void setContinueunit(Integer continueunit)
  {
    this.continueunit = continueunit;
  }

  public Integer getIs_same()
  {
    return this.is_same;
  }

  public void setIs_same(Integer isSame)
  {
    this.is_same = isSame;
  }

  public Integer getDefAreaFee()
  {
    return Integer.valueOf(this.defAreaFee == null ? 0 : this.defAreaFee.intValue());
  }

  public void setDefAreaFee(Integer defAreaFee)
  {
    this.defAreaFee = defAreaFee;
  }

  public Double getFirstprice()
  {
    return this.firstprice;
  }

  public void setFirstprice(Double firstprice)
  {
    this.firstprice = firstprice;
  }

  public Double getContinueprice()
  {
    return this.continueprice;
  }

  public void setContinueprice(Double continueprice)
  {
    this.continueprice = continueprice;
  }

  public String getExpression()
  {
    return this.expression;
  }

  public void setExpression(String expression)
  {
    this.expression = expression;
  }

  public Integer getUseexp()
  {
    this.useexp = Integer.valueOf(this.useexp == null ? 0 : this.useexp.intValue());
    return this.useexp;
  }

  public void setUseexp(Integer useexp)
  {
    this.useexp = useexp;
  }

  public Integer getHave_cod()
  {
    return this.have_cod;
  }

  public void setHave_cod(Integer haveCod)
  {
    this.have_cod = haveCod;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.DlyTypeConfig
 * JD-Core Version:    0.6.1
 */