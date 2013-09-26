package com.enation.app.shop.core.plugin.goods;

import com.enation.framework.plugin.AutoRegisterPlugin;
import com.enation.framework.plugin.IPlugin;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractGoodsSnCreator extends AutoRegisterPlugin
  implements IPlugin, IGoodsBeforeAddEvent, IGoodsBeforeEditEvent
{
  public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
  {
    goods.put("sn", createSn(goods));
  }

  public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
  {
    goods.put("sn", createSn(goods));
  }

  public abstract String createSn(Map paramMap);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.AbstractGoodsSnCreator
 * JD-Core Version:    0.6.1
 */