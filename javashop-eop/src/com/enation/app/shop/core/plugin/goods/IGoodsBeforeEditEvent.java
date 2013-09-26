package com.enation.app.shop.core.plugin.goods;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IGoodsBeforeEditEvent
{
  public abstract void onBeforeGoodsEdit(Map paramMap, HttpServletRequest paramHttpServletRequest);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.IGoodsBeforeEditEvent
 * JD-Core Version:    0.6.1
 */