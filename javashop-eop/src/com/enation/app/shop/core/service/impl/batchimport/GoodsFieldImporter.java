package com.enation.app.shop.core.service.impl.batchimport;

import com.enation.app.shop.core.model.ImportDataSource;
import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
import java.util.Map;
import org.w3c.dom.Element;

public class GoodsFieldImporter
  implements IGoodsDataImporter
{
  public void imported(Object value, Element node, ImportDataSource importConfig, Map goods)
  {
    String fieldname = node.getAttribute("fieldname");
    if (importConfig.isNewGoods())
      goods.put(fieldname, value);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsFieldImporter
 * JD-Core Version:    0.6.1
 */