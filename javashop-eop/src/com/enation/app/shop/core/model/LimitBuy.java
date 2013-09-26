package com.enation.app.shop.core.model;

import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import com.enation.framework.util.DateUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LimitBuy
{
  private Integer id;
  private String name;
  private long start_time;
  private long end_time;
  private long add_time;
  private String img;
  private int is_index;
  private List<LimitBuyGoods> limitBuyGoodsList;
  private List<Map> goodsList;

  @NotDbField
  public String getEndTime()
  {
    return DateUtil.toString(new Date(this.end_time * 1000L), "yyyy/MM/dd,HH:00:00");
  }

  @NotDbField
  public List<Map> getGoodsList() {
    return this.goodsList;
  }

  public void setGoodsList(List<Map> goodsList) {
    this.goodsList = goodsList;
  }

  @NotDbField
  public List<LimitBuyGoods> getLimitBuyGoodsList() {
    return this.limitBuyGoodsList;
  }

  public void setLimitBuyGoodsList(List<LimitBuyGoods> limitBuyGoodsList) {
    this.limitBuyGoodsList = limitBuyGoodsList;
  }

  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getStart_time() {
    return this.start_time;
  }

  public void setStart_time(long startTime) {
    this.start_time = startTime;
  }

  public long getEnd_time() {
    return this.end_time;
  }

  public void setEnd_time(long endTime) {
    this.end_time = endTime;
  }

  public long getAdd_time() {
    return this.add_time;
  }

  public void setAdd_time(long addTime) {
    this.add_time = addTime;
  }

  public String getImg() {
    return this.img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  @NotDbField
  public String getImage()
  {
    return UploadUtil.replacePath(this.img);
  }

  public int getIs_index() {
    return this.is_index;
  }

  public void setIs_index(int isIndex) {
    this.is_index = isIndex;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.LimitBuy
 * JD-Core Version:    0.6.1
 */