package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.framework.action.WWAction;
import java.util.Map;

public class ShopIndexItemAction extends WWAction
{
  private IOrderManager orderManager;
  private IGoodsManager goodsManager;
  private Map orderss;
  private Map goodsss;

  public String order()
  {
    this.orderss = this.orderManager.censusState();
    return "order";
  }

  public String goods()
  {
    this.goodsss = this.goodsManager.census();
    return "goods";
  }

  public IOrderManager getOrderManager()
  {
    return this.orderManager;
  }

  public void setOrderManager(IOrderManager orderManager)
  {
    this.orderManager = orderManager;
  }

  public IGoodsManager getGoodsManager()
  {
    return this.goodsManager;
  }

  public void setGoodsManager(IGoodsManager goodsManager)
  {
    this.goodsManager = goodsManager;
  }

  public Map getOrderss()
  {
    return this.orderss;
  }

  public void setOrderss(Map orderss)
  {
    this.orderss = orderss;
  }

  public Map getGoodsss()
  {
    return this.goodsss;
  }

  public void setGoodsss(Map goodsss)
  {
    this.goodsss = goodsss;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.ShopIndexItemAction
 * JD-Core Version:    0.6.1
 */