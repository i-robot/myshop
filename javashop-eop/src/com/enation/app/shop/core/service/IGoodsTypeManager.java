package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Attribute;
import com.enation.app.shop.core.model.GoodsType;
import com.enation.app.shop.core.model.support.GoodsTypeDTO;
import com.enation.app.shop.core.model.support.ParamGroup;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IGoodsTypeManager
{
  public abstract boolean checkname(String paramString, Integer paramInteger);

  public abstract List listAll();

  public abstract GoodsType getById(int paramInt);

  public abstract Page pageType(String paramString, int paramInt1, int paramInt2);

  public abstract Integer save(GoodsType paramGoodsType);

  public abstract void clean(Integer[] paramArrayOfInteger);

  public abstract Page pageTrashType(String paramString, int paramInt1, int paramInt2);

  public abstract GoodsTypeDTO get(Integer paramInteger);

  public abstract List getBrandListByTypeId(int paramInt);

  public abstract List listByTypeId(Integer paramInteger);

  public abstract String getParamString(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4);

  public abstract String getPropsString(String[] paramArrayOfString1, int[] paramArrayOfInt1, String[] paramArrayOfString2, String[] paramArrayOfString3, int[] paramArrayOfInt2, String[] paramArrayOfString4);

  public abstract int delete(Integer[] paramArrayOfInteger);

  public abstract void revert(Integer[] paramArrayOfInteger);

  public abstract ParamGroup[] getParamArByTypeId(int paramInt);

  public abstract List<Attribute> getAttrListByTypeId(int paramInt);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IGoodsTypeManager
 * JD-Core Version:    0.6.1
 */