package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.service.IFreeOfferManager;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.app.shop.core.service.IProductManager;
import com.enation.app.shop.core.service.ITagManager;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SelectorAction extends WWAction
{
  private IGoodsManager goodsManager;
  private IProductManager productManager;
  private IFreeOfferManager freeOfferManager;
  private String sType;
  private String keyword;
  private String order;
  private Integer catid;
  protected List catList;
  protected IGoodsCatManager goodsCatManager;
  private Integer[] goodsid;
  private Integer[] productid;
  private Integer[] giftid;
  private Integer[] tagid;
  private List tagList;
  private ITagManager tagManager;
  private int isSingle;

  public String goods()
  {
    if ((this.keyword != null) && 
      (!StringUtil.isEmpty(EopSetting.ENCODING))) {
      this.keyword = StringUtil.to(this.keyword, EopSetting.ENCODING);
    }

    String name = "name".equals(this.sType) ? this.keyword : null;
    String sn = "sn".equals(this.sType) ? this.keyword : null;

    this.webpage = this.goodsManager.searchGoods(null, null, this.catid, name, sn, null, null, this.order, getPage(), 5);
    return "goods";
  }

  public String cat() {
    this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
    return "cat";
  }

  public String tag() {
    this.tagList = this.tagManager.list();
    return "tag";
  }

  public String product()
  {
    if ((this.keyword != null) && 
      (!StringUtil.isEmpty(EopSetting.ENCODING))) {
      this.keyword = StringUtil.to(this.keyword, EopSetting.ENCODING);
    }

    String name = "name".equals(this.sType) ? this.keyword : null;
    String sn = "sn".equals(this.sType) ? this.keyword : null;
    this.webpage = this.productManager.list(name, sn, getPage(), 5, this.order);
    return "product";
  }

  public String gift()
  {
    if (this.keyword != null) this.keyword = StringUtil.toUTF8(this.keyword);
    this.webpage = this.freeOfferManager.list(this.keyword, this.order, getPage(), 3);
    return "gift";
  }

  public String listGoods()
  {
    List goodsList = this.goodsManager.list(this.goodsid);

    if (this.isSingle == 0) {
      this.json = JSONArray.fromObject(goodsList).toString();
    }
    else {
      Object goods = goodsList.get(0);
      this.json = JSONObject.fromObject(goods).toString();
    }

    return "json_message";
  }

  public String listGoodsByCat() {
    List goodsList = this.goodsManager.listByCat(this.catid);
    this.json = JSONArray.fromObject(goodsList).toString();

    return "json_message";
  }

  public String listGoodsByTag() {
    List goodsList = this.goodsManager.listByTag(this.tagid);
    this.json = JSONArray.fromObject(goodsList).toString();

    return "json_message";
  }

  public String listProduct()
  {
    List productlist = this.productManager.list(this.productid);
    this.json = JSONArray.fromObject(productlist).toString();
    return "json_message";
  }

  public String listGift()
  {
    List giftList = this.freeOfferManager.list(this.giftid);
    this.json = JSONArray.fromObject(giftList).toString();
    return "json_message";
  }

  public IGoodsManager getGoodsManager()
  {
    return this.goodsManager;
  }

  public void setGoodsManager(IGoodsManager goodsManager) {
    this.goodsManager = goodsManager;
  }

  public IProductManager getProductManager() {
    return this.productManager;
  }

  public void setProductManager(IProductManager productManager) {
    this.productManager = productManager;
  }

  public String getKeyword() {
    return this.keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public String getsType() {
    return this.sType;
  }

  public void setsType(String sType) {
    this.sType = sType;
  }

  public Integer[] getGoodsid() {
    return this.goodsid;
  }

  public void setGoodsid(Integer[] goodsid) {
    this.goodsid = goodsid;
  }

  public IFreeOfferManager getFreeOfferManager()
  {
    return this.freeOfferManager;
  }

  public void setFreeOfferManager(IFreeOfferManager freeOfferManager)
  {
    this.freeOfferManager = freeOfferManager;
  }

  public Integer[] getGiftid()
  {
    return this.giftid;
  }

  public void setGiftid(Integer[] giftid)
  {
    this.giftid = giftid;
  }

  public Integer[] getProductid()
  {
    return this.productid;
  }

  public void setProductid(Integer[] productid)
  {
    this.productid = productid;
  }

  public Integer getCatid()
  {
    return this.catid;
  }

  public void setCatid(Integer catid)
  {
    this.catid = catid;
  }

  public List getCatList() {
    return this.catList;
  }

  public void setCatList(List catList) {
    this.catList = catList;
  }

  public IGoodsCatManager getGoodsCatManager() {
    return this.goodsCatManager;
  }

  public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
    this.goodsCatManager = goodsCatManager;
  }

  public Integer[] getTagid() {
    return this.tagid;
  }

  public void setTagid(Integer[] tagid) {
    this.tagid = tagid;
  }

  public List getTagList() {
    return this.tagList;
  }

  public void setTagList(List tagList) {
    this.tagList = tagList;
  }

  public ITagManager getTagManager() {
    return this.tagManager;
  }

  public void setTagManager(ITagManager tagManager) {
    this.tagManager = tagManager;
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    System.out.println(URLDecoder.decode("测试", "UTF-8"));
  }

  public int getIsSingle()
  {
    return this.isSingle;
  }

  public void setIsSingle(int isSingle) {
    this.isSingle = isSingle;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.SelectorAction
 * JD-Core Version:    0.6.1
 */