package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;

public class FreeOffer
{
  private Integer fo_id;
  private Integer fo_category_id;
  private String cat_name;
  private String fo_name;
  private Integer publishable;
  private Integer recommend;
  private Integer sorder;
  private Integer limit_purchases;
  private Long startdate;
  private Long enddate;
  private String lv_ids;
  private Double price;
  private String synopsis;
  private String list_thumb;
  private String pic;
  private Integer score;
  private Double weight;
  private Integer repertory;
  private String descript;
  private Integer disabled;

  @PrimaryKeyField
  public Integer getFo_id()
  {
    return this.fo_id;
  }
  public void setFo_id(Integer foId) {
    this.fo_id = foId;
  }
  public Integer getFo_category_id() {
    return this.fo_category_id;
  }
  public void setFo_category_id(Integer foCategoryId) {
    this.fo_category_id = foCategoryId;
  }
  public String getFo_name() {
    return this.fo_name;
  }
  public void setFo_name(String foName) {
    this.fo_name = foName;
  }
  public Integer getPublishable() {
    return this.publishable;
  }
  public void setPublishable(Integer publishable) {
    this.publishable = publishable;
  }
  public Integer getRecommend() {
    return this.recommend;
  }
  public void setRecommend(Integer recommend) {
    this.recommend = recommend;
  }
  public Integer getSorder() {
    return this.sorder;
  }
  public void setSorder(Integer sorder) {
    this.sorder = sorder;
  }
  public Integer getLimit_purchases() {
    return this.limit_purchases;
  }
  public void setLimit_purchases(Integer limitPurchases) {
    this.limit_purchases = limitPurchases;
  }
  public Long getStartdate() {
    return this.startdate;
  }
  public void setStartdate(Long startdate) {
    this.startdate = startdate;
  }
  public Long getEnddate() {
    return this.enddate;
  }
  public void setEnddate(Long enddate) {
    this.enddate = enddate;
  }

  public String getLv_ids() {
    return this.lv_ids;
  }
  public void setLv_ids(String lvIds) {
    this.lv_ids = lvIds;
  }
  public Double getPrice() {
    return this.price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
  public String getSynopsis() {
    return this.synopsis;
  }
  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }
  public String getList_thumb() {
    return this.list_thumb;
  }
  public void setList_thumb(String listThumb) {
    this.list_thumb = listThumb;
  }
  public String getPic() {
    return this.pic;
  }
  public void setPic(String pic) {
    this.pic = pic;
  }
  public Integer getScore() {
    if (this.score == null)
      this.score = Integer.valueOf(0);
    return this.score;
  }
  public void setScore(Integer score) {
    this.score = score;
  }
  public Double getWeight() {
    return this.weight;
  }
  public void setWeight(Double weight) {
    this.weight = weight;
  }
  public Integer getRepertory() {
    return this.repertory;
  }
  public void setRepertory(Integer repertory) {
    this.repertory = repertory;
  }
  public String getDescript() {
    return this.descript;
  }
  public void setDescript(String descript) {
    this.descript = descript;
  }

  public Integer getDisabled() {
    return this.disabled;
  }
  public void setDisabled(Integer disabled) {
    this.disabled = disabled;
  }
  @NotDbField
  public String getCat_name() {
    return this.cat_name;
  }
  public void setCat_name(String catname) {
    this.cat_name = catname;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.FreeOffer
 * JD-Core Version:    0.6.1
 */