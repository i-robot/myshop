package com.enation.app.shop.core.service.impl.batchimport;

import com.enation.app.shop.core.model.Depot;
import com.enation.app.shop.core.model.ImportDataSource;
import com.enation.app.shop.core.model.Product;
import com.enation.app.shop.core.service.IDepotManager;
import com.enation.app.shop.core.service.IProductManager;
import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
import com.enation.framework.database.IDaoSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;

public class GoodsSpecImporter
  implements IGoodsDataImporter
{
  protected IProductManager productManager;
  protected IDaoSupport baseDaoSupport;
  private IDepotManager depotManager;

  public void imported(Object value, Element node, ImportDataSource importDs, Map goods)
  {
    Integer goodsid = (Integer)goods.get("goods_id");
    Product product = new Product();
    product.setGoods_id(goodsid);
    product.setCost(Double.valueOf("" + goods.get("cost")));
    product.setPrice(Double.valueOf("" + goods.get("price")));
    product.setSn((String)goods.get("sn"));
    product.setStore(Integer.valueOf(100));
    product.setWeight(Double.valueOf("" + goods.get("weight")));
    product.setName((String)goods.get("name"));

    List productList = new ArrayList();
    productList.add(product);
    this.productManager.add(productList);

    List<Depot> depotList = this.depotManager.list();
    for (Depot depot : depotList)
      this.baseDaoSupport.execute("insert into goods_depot(goodsid,depotid,iscmpl)values(?,?,?)", new Object[] { goodsid, depot.getId(), Integer.valueOf(0) });
  }

  public IProductManager getProductManager()
  {
    return this.productManager;
  }
  public void setProductManager(IProductManager productManager) {
    this.productManager = productManager;
  }
  public IDaoSupport getBaseDaoSupport() {
    return this.baseDaoSupport;
  }
  public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
    this.baseDaoSupport = baseDaoSupport;
  }
  public IDepotManager getDepotManager() {
    return this.depotManager;
  }
  public void setDepotManager(IDepotManager depotManager) {
    this.depotManager = depotManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsSpecImporter
 * JD-Core Version:    0.6.1
 */