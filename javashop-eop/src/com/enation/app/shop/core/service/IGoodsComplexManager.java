package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.GoodsComplex;
import java.util.List;

public abstract interface IGoodsComplexManager
{
  public abstract List listAllComplex(int paramInt);

  public abstract List listComplex(int paramInt);

  public abstract void addCoodsComplex(GoodsComplex paramGoodsComplex);

  public abstract void globalGoodsComplex(int paramInt, List<GoodsComplex> paramList);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IGoodsComplexManager
 * JD-Core Version:    0.6.1
 */