package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.model.MemberLv;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IMemberPriceManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.CurrencyUtil;
import java.util.List;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

public class MemberPriceAction extends WWAction
{
  private Double price;
  private int goodsid;
  private int productid;
  private IMemberLvManager memberLvManager;
  private IMemberPriceManager memberPriceManager;
  private List<MemberLv> lvList;

  public String execute()
  {
    processLv();
    return "list";
  }

  public String disLvInput()
  {
    processLv();
    return "dis_lv_input";
  }

  public String getLvPriceJson()
  {
    try {
      List priceList = this.memberPriceManager.listPriceByGid(this.goodsid);
      JSONArray jsonArray = JSONArray.fromObject(priceList);
      this.json = ("{result:1,priceData:" + jsonArray.toString() + "}");
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e.fillInStackTrace());
      this.json = ("{result:0,message:" + e.getMessage() + "}");
    }
    return "json_message";
  }

  private void processLv() {
    this.lvList = this.memberLvManager.list();
    this.price = Double.valueOf(this.price == null ? 0.0D : this.price.doubleValue());
    for (MemberLv lv : this.lvList) {
      double discount = lv.getDiscount().intValue() / 100.0D;

      double lvprice = CurrencyUtil.mul(this.price.doubleValue(), discount).doubleValue();
      lv.setLvPrice(Double.valueOf(lvprice));
    }
  }

  public Double getPrice()
  {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public int getGoodsid() {
    return this.goodsid;
  }

  public void setGoodsid(int goodsid) {
    this.goodsid = goodsid;
  }

  public int getProductid() {
    return this.productid;
  }

  public void setProductid(int productid) {
    this.productid = productid;
  }

  public IMemberLvManager getMemberLvManager() {
    return this.memberLvManager;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager) {
    this.memberLvManager = memberLvManager;
  }

  public List<MemberLv> getLvList() {
    return this.lvList;
  }

  public void setLvList(List<MemberLv> lvList) {
    this.lvList = lvList;
  }

  public IMemberPriceManager getMemberPriceManager() {
    return this.memberPriceManager;
  }

  public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
    this.memberPriceManager = memberPriceManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.MemberPriceAction
 * JD-Core Version:    0.6.1
 */