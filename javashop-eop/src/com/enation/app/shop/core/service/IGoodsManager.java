package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.model.GoodsStores;
import com.enation.app.shop.core.model.support.GoodsEditDTO;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IGoodsManager
{
  public static final String plugin_type_berforeadd = "goods_add_berforeadd";
  public static final String plugin_type_afteradd = "goods_add_afteradd";

  public abstract Map get(Integer paramInteger);

  public abstract Goods getGoods(Integer paramInteger);

  public abstract GoodsEditDTO getGoodsEditData(Integer paramInteger);

  public abstract void add(Goods paramGoods);

  public abstract void edit(Goods paramGoods);

  public abstract Page searchGoods(Integer paramInteger1, String paramString1, String paramString2, Integer paramInteger2, Integer[] paramArrayOfInteger, String paramString3, int paramInt1, int paramInt2);

  public abstract Page searchGoods(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString1, String paramString2, Integer paramInteger4, Integer[] paramArrayOfInteger, String paramString3, int paramInt1, int paramInt2, String paramString4);

  public abstract Page searchGoods(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString1, String paramString2, Integer paramInteger4, Integer[] paramArrayOfInteger, String paramString3, int paramInt1, int paramInt2);

  public abstract Page searchBindGoods(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract Page pageTrash(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract List<GoodsStores> storeWarnGoods(int paramInt1, int paramInt2, int paramInt3);

  public abstract void delete(Integer[] paramArrayOfInteger);

  public abstract void revert(Integer[] paramArrayOfInteger);

  public abstract void clean(Integer[] paramArrayOfInteger);

  public abstract List list(Integer[] paramArrayOfInteger);

  public abstract List listByCat(Integer paramInteger);

  public abstract List listByTag(Integer[] paramArrayOfInteger);

  public abstract List<Map> list();

  public abstract void batchEdit();

  public abstract Map census();

  public abstract void getNavdata(Map paramMap);

  public abstract void updateField(String paramString, Object paramObject, Integer paramInteger);

  public abstract List getRecommentList(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract Goods getGoodBySn(String paramString);

  public abstract void incViewCount(Integer paramInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IGoodsManager
 * JD-Core Version:    0.6.1
 */