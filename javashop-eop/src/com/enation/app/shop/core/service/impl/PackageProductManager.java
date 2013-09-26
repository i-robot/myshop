package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.model.PackageProduct;
import com.enation.app.shop.core.model.Product;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.app.shop.core.service.IPackageProductManager;
import com.enation.app.shop.core.service.IProductManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PackageProductManager extends BaseSupport
  implements IPackageProductManager
{
  private IGoodsManager goodsManager;
  private IProductManager productManager;

  public void add(PackageProduct packageProduct)
  {
    this.baseDaoSupport.insert("package_product", packageProduct);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void add(Goods goods, int[] product_id, int[] pkgnum)
  {
    String sn = "P" + DateUtil.toString(new Date(System.currentTimeMillis()), "yyyyMMddhhmmss");
    goods.setSn(sn);
    goods.setPoint(Integer.valueOf(0));
    this.baseDaoSupport.insert("goods", goods);
    Integer goods_id = Integer.valueOf(this.baseDaoSupport.getLastId("goods"));
    for (int i = 0; i < product_id.length; i++) {
      PackageProduct product = new PackageProduct();
      product.setGoods_id(goods_id.intValue());
      product.setProduct_id(product_id[i]);
      product.setPkgnum(pkgnum[i]);
      add(product);
    }

    Product product = new Product();
    product.setGoods_id(goods_id);

    product.setName(goods.getName());
    product.setPrice(goods.getPrice());
    product.setSn(sn);
    product.setStore(goods.getStore());
    product.setWeight(goods.getWeight());

    List productList = new ArrayList();
    productList.add(product);
    this.productManager.add(productList);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void edit(Goods goods, int[] product_id, int[] pkgnum)
  {
    this.baseDaoSupport.update("goods", goods, "goods_id=" + goods.getGoods_id());
    this.baseDaoSupport.execute("delete from package_product where goods_id = ?", new Object[] { goods.getGoods_id() });
    for (int i = 0; i < product_id.length; i++) {
      PackageProduct product = new PackageProduct();
      product.setGoods_id(goods.getGoods_id().intValue());
      product.setProduct_id(product_id[i]);
      product.setPkgnum(pkgnum[i]);
      add(product);
    }

    Product product = new Product();
    product.setGoods_id(goods.getGoods_id());
    product.setName(goods.getName());
    product.setPrice(goods.getPrice());
    product.setStore(goods.getStore());
    product.setWeight(goods.getWeight());
    product.setSn(goods.getSn());
    List productList = new ArrayList();
    productList.add(product);
    this.productManager.add(productList);
  }

  public List list(int goods_id)
  {
    String sql = "select pp.*, p.product_id, p.sn, p.price, p.goods_id as pgoods_id, p.weight, g.name from " + getTableName("package_product") + " pp left join " + getTableName("product") + " p on p.product_id = pp.product_id left join " + getTableName("goods") + " g on g.goods_id = p.goods_id";
    sql = sql + " where pp.goods_id = " + goods_id;
    List list = this.daoSupport.queryForList(sql, new Object[0]);
    return list;
  }

  public IGoodsManager getGoodsManager() {
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
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.PackageProductManager
 * JD-Core Version:    0.6.1
 */