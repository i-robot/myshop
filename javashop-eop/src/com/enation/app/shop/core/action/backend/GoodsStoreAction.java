package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.service.IGoodsStoreManager;
import com.enation.framework.action.WWAction;
import org.apache.log4j.Logger;

public class GoodsStoreAction extends WWAction
{
  private IGoodsStoreManager goodsStoreManager;
  private int goodsid;
  private String html;

  public String getStoreDialogHtml()
  {
    this.html = this.goodsStoreManager.getStoreHtml(Integer.valueOf(this.goodsid));
    return "dialog_html";
  }

  public String getStockDialogHtml()
  {
    this.html = this.goodsStoreManager.getStockHtml(Integer.valueOf(this.goodsid));
    return "dialog_html";
  }

  public String getShipDialogHtml()
  {
    this.html = this.goodsStoreManager.getShipHtml(Integer.valueOf(this.goodsid));
    return "dialog_html";
  }

  public String saveStore()
  {
    try
    {
      this.goodsStoreManager.saveStore(this.goodsid);
      showSuccessJson("保存商品库存成功");
    } catch (RuntimeException e) {
      this.logger.error("保存商品库存出错", e);
      showErrorJson(e.getMessage());
    }

    return "json_message";
  }

  public String saveStock()
  {
    try
    {
      this.goodsStoreManager.saveStock(this.goodsid);
      showSuccessJson("保存进货成功");
    } catch (RuntimeException e) {
      this.logger.error("保存进货出错", e);
      showErrorJson(e.getMessage());
    }
    return "json_message";
  }

  public String getWarnDialogHtml()
  {
    this.html = this.goodsStoreManager.getWarnHtml(Integer.valueOf(this.goodsid));
    return "dialog_html";
  }

  public String saveWarn()
  {
    try
    {
      this.goodsStoreManager.saveWarn(this.goodsid);
      showSuccessJson("保存报警成功");
    } catch (RuntimeException e) {
      this.logger.error("保存报警出错", e);
      showErrorJson(e.getMessage());
    }
    return "json_message";
  }

  public String saveShip()
  {
    try
    {
      this.goodsStoreManager.saveShip(this.goodsid);
      showSuccessJson("保存出货成功");
    } catch (RuntimeException e) {
      this.logger.error("保存出货出错", e);
      showErrorJson(e.getMessage());
    }
    return "json_message";
  }

  public String saveCmpl()
  {
    try {
      this.goodsStoreManager.saveCmpl(this.goodsid);
      showSuccessJson("更新状态成功");
    } catch (RuntimeException e) {
      this.logger.error("保更新状态出错", e);
      showErrorJson(e.getMessage());
    }
    return "json_message";
  }

  public IGoodsStoreManager getGoodsStoreManager() {
    return this.goodsStoreManager;
  }
  public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
    this.goodsStoreManager = goodsStoreManager;
  }
  public int getGoodsid() {
    return this.goodsid;
  }
  public void setGoodsid(int goodsid) {
    this.goodsid = goodsid;
  }

  public String getHtml()
  {
    return this.html;
  }

  public void setHtml(String html)
  {
    this.html = html;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsStoreAction
 * JD-Core Version:    0.6.1
 */