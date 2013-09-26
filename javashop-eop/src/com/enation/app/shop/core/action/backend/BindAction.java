package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.app.shop.core.service.IPackageProductManager;
import com.enation.app.shop.core.service.IProductManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public class BindAction extends WWAction
{
  protected String name;
  protected String sn;
  protected String order;
  protected IGoodsManager goodsManager;
  protected int goods_id;
  protected Page productPage;
  protected IProductManager productManager;
  protected IPackageProductManager packageProductManager;
  protected int[] product_id;
  protected int[] pkgnum;
  protected Goods bind;
  protected List packageList;

  public String bindlist()
  {
    this.webpage = this.goodsManager.searchBindGoods(this.name, this.sn, this.order, getPage(), getPageSize());

    return "bindlist";
  }

  public String delete() {
    try {
      this.goodsManager.delete(new Integer[] { Integer.valueOf(this.goods_id) });
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String add()
  {
    return "add";
  }

  public String edit()
  {
    this.bind = this.goodsManager.getGoods(Integer.valueOf(this.goods_id));
    this.packageList = this.packageProductManager.list(this.goods_id);
    return "edit";
  }

  public String addSave() {
    try {
      this.bind.setCreate_time(Long.valueOf(System.currentTimeMillis()));
      this.bind.setLast_modify(Long.valueOf(System.currentTimeMillis()));
      this.bind.setView_count(Integer.valueOf(0));
      this.bind.setBuy_count(Integer.valueOf(0));
      this.bind.setGoods_type("bind");
      this.bind.setDisabled(Integer.valueOf(0));
      this.packageProductManager.add(this.bind, this.product_id, this.pkgnum);
      this.msgs.add("捆绑商品添加成功");
      this.urls.put("捆绑商品列表", "bind!bindlist.do");
    } catch (RuntimeException e) {
      this.msgs.add("捆绑商品添加失败");
      this.urls.put("捆绑商品列表", "bind!bindlist.do");
    }
    return "message";
  }

  public String editSave() {
    try {
      this.bind.setLast_modify(Long.valueOf(System.currentTimeMillis()));
      this.packageProductManager.edit(this.bind, this.product_id, this.pkgnum);
      this.msgs.add("捆绑商品修改成功");
      this.urls.put("捆绑商品列表", "bind!bindlist.do");
    } catch (RuntimeException e) {
      this.msgs.add("捆绑商品修改失败");
      this.urls.put("捆绑商品列表", "bind!bindlist.do");
    }
    return "message";
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSn() {
    return this.sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public IGoodsManager getGoodsManager() {
    return this.goodsManager;
  }

  public void setGoodsManager(IGoodsManager goodsManager) {
    this.goodsManager = goodsManager;
  }

  public int getGoods_id() {
    return this.goods_id;
  }

  public void setGoods_id(int goodsId) {
    this.goods_id = goodsId;
  }

  public Page getProductPage() {
    return this.productPage;
  }

  public void setProductPage(Page productPage) {
    this.productPage = productPage;
  }

  public IProductManager getProductManager() {
    return this.productManager;
  }

  public void setProductManager(IProductManager productManager) {
    this.productManager = productManager;
  }

  public int[] getProduct_id() {
    return this.product_id;
  }

  public void setProduct_id(int[] productId) {
    this.product_id = productId;
  }

  public int[] getPkgnum() {
    return this.pkgnum;
  }

  public void setPkgnum(int[] pkgnum) {
    this.pkgnum = pkgnum;
  }

  public Goods getBind() {
    return this.bind;
  }

  public void setBind(Goods bind) {
    this.bind = bind;
  }

  public IPackageProductManager getPackageProductManager() {
    return this.packageProductManager;
  }

  public void setPackageProductManager(IPackageProductManager packageProductManager)
  {
    this.packageProductManager = packageProductManager;
  }

  public List getPackageList() {
    return this.packageList;
  }

  public void setPackageList(List packageList) {
    this.packageList = packageList;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.BindAction
 * JD-Core Version:    0.6.1
 */