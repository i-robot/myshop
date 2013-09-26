package com.enation.app.shop.core.service.impl.batchimport;

import com.enation.app.shop.core.model.Brand;
import com.enation.app.shop.core.model.ImportDataSource;
import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;

public class GoodsBrandImporter
  implements IGoodsDataImporter
{
  public void imported(Object name, Element node, ImportDataSource importDs, Map goods)
  {
    if (!importDs.isNewGoods()) return;

    String brandname = (String)name;
    if (brandname == null) brandname = "";
    brandname = brandname.trim();

    List<Brand> brandList = importDs.getBrandList();
    for (Brand brand : brandList)
      if (brand.getName().equals(brandname))
        goods.put("brand_id", brand.getBrand_id());
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsBrandImporter
 * JD-Core Version:    0.6.1
 */