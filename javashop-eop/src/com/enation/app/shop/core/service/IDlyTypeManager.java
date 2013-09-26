package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.DlyType;
import com.enation.app.shop.core.model.support.DlyTypeConfig;
import com.enation.app.shop.core.model.support.TypeAreaConfig;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IDlyTypeManager
{
  public abstract void add(DlyType paramDlyType, DlyTypeConfig paramDlyTypeConfig);

  public abstract void add(DlyType paramDlyType, DlyTypeConfig paramDlyTypeConfig, TypeAreaConfig[] paramArrayOfTypeAreaConfig);

  public abstract void edit(DlyType paramDlyType, DlyTypeConfig paramDlyTypeConfig);

  public abstract void edit(DlyType paramDlyType, DlyTypeConfig paramDlyTypeConfig, TypeAreaConfig[] paramArrayOfTypeAreaConfig);

  public abstract void delete(String paramString);

  public abstract List listByAreaId(Integer paramInteger);

  public abstract Page pageDlyType(int paramInt1, int paramInt2);

  public abstract List<DlyType> list(Double paramDouble1, Double paramDouble2, String paramString);

  public abstract List<DlyType> list();

  public abstract Double[] countPrice(Integer paramInteger, Double paramDouble1, Double paramDouble2, String paramString, boolean paramBoolean);

  public abstract DlyType getDlyTypeById(Integer paramInteger);

  public abstract Double countExp(String paramString, Double paramDouble1, Double paramDouble2);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IDlyTypeManager
 * JD-Core Version:    0.6.1
 */