package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class FlashAdv
  implements Serializable
{
  private Integer id;
  private String remark;
  private String url;
  private String pic;
  private Integer sort;

  @PrimaryKeyField
  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getPic() {
    return this.pic;
  }
  public void setPic(String pic) {
    this.pic = pic;
  }
  public String getRemark() {
    return this.remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public Integer getSort() {
    return this.sort;
  }
  public void setSort(Integer sort) {
    this.sort = sort;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.FlashAdv
 * JD-Core Version:    0.6.1
 */