package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.service.IGoodsAlbumManager;
import com.enation.framework.action.WWAction;
import org.apache.log4j.Logger;

public class GalleryAction extends WWAction
{
  private IGoodsAlbumManager goodsAlbumManager;
  private int total;
  private int start;
  private int end;

  public int getStart()
  {
    return this.start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getEnd() {
    return this.end;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  public int getTotal() {
    return this.total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String recreate() {
    try {
      this.goodsAlbumManager.recreate(this.start, this.end);
      showSuccessJson("生成商品相册缩略图成功 ");
    } catch (RuntimeException e) {
      this.logger.error("生成商品相册缩略图错误", e);
      showErrorJson("生成商品相册缩略图错误" + e.getMessage());
    }
    return "json_message";
  }

  public String execute() {
    this.total = this.goodsAlbumManager.getTotal();
    return "input";
  }

  public IGoodsAlbumManager getGoodsAlbumManager() {
    return this.goodsAlbumManager;
  }
  public void setGoodsAlbumManager(IGoodsAlbumManager goodsAlbumManager) {
    this.goodsAlbumManager = goodsAlbumManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.GalleryAction
 * JD-Core Version:    0.6.1
 */