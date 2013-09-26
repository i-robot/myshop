package com.enation.app.shop.core.model.support;

import com.enation.app.shop.core.model.Goods;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.util.CurrencyUtil;
import java.util.List;
import java.util.Map;

public class GoodsView extends Goods
{
  private Double save_price;
  private Double agio;
  private String brand_name;
  private Map propMap;
  private int hasSpec;
  private List specList;
  private Integer productid;
  private boolean isLast = false;
  private boolean isFirst = false;

  public Double getAgio()
  {
    this.agio = Double.valueOf(CurrencyUtil.div(getPrice().doubleValue(), getMktprice().doubleValue()));
    return this.agio;
  }

  public void setAgio(Double agio) {
    this.agio = agio;
  }

  public Double getSave_price()
  {
    this.save_price = Double.valueOf(CurrencyUtil.sub(getMktprice().doubleValue(), getPrice().doubleValue()));
    return this.save_price;
  }

  public void setSave_price(Double save_price) {
    this.save_price = save_price;
  }

  public String getImage_default()
  {
    String image_default = super.getImage_default();
    if ((image_default == null) || (image_default.equals(""))) {
      return EopSetting.IMG_SERVER_DOMAIN + "/images/no_picture.jpg";
    }
    image_default = UploadUtil.replacePath(image_default);

    return image_default;
  }

  public String getThumbnail_pic()
  {
    String thumbnail = super.getImage_default();
    if ((thumbnail == null) || (thumbnail.equals(""))) {
      return EopSetting.IMG_SERVER_DOMAIN + "/images/no_picture.jpg";
    }
    thumbnail = getImage_default();

    thumbnail = UploadUtil.getThumbPath(thumbnail, "_thumbnail");
    return thumbnail;
  }

  public String getBrand_name()
  {
    return this.brand_name;
  }

  public void setBrand_name(String brand_name) {
    this.brand_name = brand_name;
  }

  public Map getPropMap() {
    return this.propMap;
  }

  public void setPropMap(Map propMap) {
    this.propMap = propMap;
  }

  public List getSpecList() {
    return this.specList;
  }

  public void setSpecList(List specList) {
    this.specList = specList;
  }

  public int getHasSpec() {
    return this.hasSpec;
  }

  public void setHasSpec(int hasSpec) {
    this.hasSpec = hasSpec;
  }

  public Integer getProductid() {
    return this.productid;
  }

  public void setProductid(Integer productid) {
    this.productid = productid;
  }

  public boolean getIsLast() {
    return this.isLast;
  }

  public void setIsLast(boolean isLast) {
    this.isLast = isLast;
  }

  public boolean getIsFirst() {
    return this.isFirst;
  }

  public void setIsFirst(boolean isFirst) {
    this.isFirst = isFirst;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.GoodsView
 * JD-Core Version:    0.6.1
 */