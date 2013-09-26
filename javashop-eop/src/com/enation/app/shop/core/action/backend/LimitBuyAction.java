package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.LimitBuy;
import com.enation.app.shop.core.model.LimitBuyGoods;
import com.enation.app.shop.core.service.ILimitBuyManager;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.DateUtil;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class LimitBuyAction extends WWAction
{
  private ILimitBuyManager limitBuyManager;
  private LimitBuy limitBuy;
  private Integer id;
  private boolean isEdit;
  private Integer[] goodsid;
  private Double[] price;
  private String start_time;
  private String end_time;
  private int start_hour;
  private int end_hour;
  private File imgFile;
  private String imgFileFileName;

  public String list()
  {
    this.webpage = this.limitBuyManager.list(getPage(), getPageSize());

    return "list";
  }

  public String add() {
    this.isEdit = false;
    return "input";
  }

  public String edit() {
    this.isEdit = true;
    this.limitBuy = this.limitBuyManager.get(this.id);
    this.start_hour = new Date(this.limitBuy.getStart_time() * 1000L).getHours();
    this.end_hour = new Date(this.limitBuy.getEnd_time() * 1000L).getHours();
    return "input";
  }

  private int getDatelineLong(String date) {
    return (int)(DateUtil.toDate(date, "yyyy-MM-dd HH").getTime() / 1000L);
  }

  public static void main(String[] args)
  {
    int date_int = (int)(DateUtil.toDate("2010-11-11 18", "yyyy-MM-dd HH").getTime() / 1000L);
    System.out.println("date int :" + date_int);
    long d = date_int * 1000L;
    System.out.println(d);
    System.out.println(new Date(d).getHours());
  }

  public String saveAdd()
  {
    try
    {
      if ((this.imgFile != null) && (this.imgFileFileName != null)) {
        String img = UploadUtil.upload(this.imgFile, this.imgFileFileName, "goods");
        this.limitBuy.setImg(img);
      }

      this.limitBuy.setStart_time(getDatelineLong(this.start_time + " " + this.start_hour));
      this.limitBuy.setEnd_time(getDatelineLong(this.end_time + " " + this.end_hour));

      this.limitBuy.setLimitBuyGoodsList(createLimitBuyGoods());
      this.limitBuyManager.add(this.limitBuy);
      this.msgs.add("限时购买添加成功");
      this.urls.put("限时购买列表", "limitBuy!list.do");
    } catch (RuntimeException e) {
      this.logger.error(e.fillInStackTrace());
      this.msgs.add(e.getMessage());
      this.urls.put("返回", "javascript:back();;");
    }
    return "message";
  }

  public String saveEdit()
  {
    try {
      if ((this.imgFile != null) && (this.imgFileFileName != null)) {
        String img = UploadUtil.upload(this.imgFile, this.imgFileFileName, "goods");
        this.limitBuy.setImg(img);
      }

      this.limitBuy.setStart_time(getDatelineLong(this.start_time + " " + this.start_hour));
      this.limitBuy.setEnd_time(getDatelineLong(this.end_time + " " + this.end_hour));

      this.limitBuy.setLimitBuyGoodsList(createLimitBuyGoods());
      this.limitBuyManager.edit(this.limitBuy);
      this.msgs.add("限时购买修改成功");
      this.urls.put("限时购买列表", "limitBuy!list.do");
    } catch (RuntimeException e) {
      this.logger.error(e);
      this.msgs.add(e.getMessage());
      this.urls.put("返回", "javascript:back();;");
    }
    return "message";
  }

  public String delete() {
    this.limitBuyManager.delete(this.id);
    this.msgs.add("限时购买删除成功");
    this.urls.put("限时购买列表", "limitBuy!list.do");
    return "message";
  }

  private List<LimitBuyGoods> createLimitBuyGoods() {
    if (this.goodsid == null)
      throw new RuntimeException("必须选择一个或更多商品");
    if (this.price == null)
      throw new RuntimeException("必须选择一个或更多商品");
    if (this.goodsid.length != this.price.length) {
      throw new RuntimeException("商品价格不正确");
    }
    List goodsList = new ArrayList();
    for (int i = 0; i < this.goodsid.length; i++) {
      LimitBuyGoods buyGoods = new LimitBuyGoods();
      buyGoods.setGoodsid(this.goodsid[i]);
      buyGoods.setPrice(this.price[i]);
      goodsList.add(buyGoods);
    }

    return goodsList;
  }

  public ILimitBuyManager getLimitBuyManager() {
    return this.limitBuyManager;
  }

  public void setLimitBuyManager(ILimitBuyManager limitBuyManager) {
    this.limitBuyManager = limitBuyManager;
  }

  public LimitBuy getLimitBuy() {
    return this.limitBuy;
  }

  public void setLimitBuy(LimitBuy limitBuy) {
    this.limitBuy = limitBuy;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public boolean getIsEdit() {
    return this.isEdit;
  }

  public void setIsEdit(boolean isEdit) {
    this.isEdit = isEdit;
  }

  public Integer[] getGoodsid() {
    return this.goodsid;
  }

  public void setGoodsid(Integer[] goodsid) {
    this.goodsid = goodsid;
  }

  public Double[] getPrice() {
    return this.price;
  }

  public void setPrice(Double[] price) {
    this.price = price;
  }

  public String getStart_time() {
    return this.start_time;
  }

  public void setStart_time(String startTime) {
    this.start_time = startTime;
  }

  public String getEnd_time() {
    return this.end_time;
  }

  public void setEnd_time(String endTime) {
    this.end_time = endTime;
  }

  public void setEdit(boolean isEdit) {
    this.isEdit = isEdit;
  }

  public int getStart_hour() {
    return this.start_hour;
  }

  public void setStart_hour(int startHour) {
    this.start_hour = startHour;
  }

  public int getEnd_hour() {
    return this.end_hour;
  }

  public void setEnd_hour(int endHour) {
    this.end_hour = endHour;
  }

  public File getImgFile() {
    return this.imgFile;
  }

  public void setImgFile(File imgFile) {
    this.imgFile = imgFile;
  }

  public String getImgFileFileName() {
    return this.imgFileFileName;
  }

  public void setImgFileFileName(String imgFileFileName) {
    this.imgFileFileName = imgFileFileName;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.LimitBuyAction
 * JD-Core Version:    0.6.1
 */