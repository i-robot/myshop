package com.enation.app.shop.core.service.impl.batchimport;

import com.enation.app.shop.core.model.Attribute;
import com.enation.app.shop.core.model.ImportDataSource;
import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
import com.enation.framework.util.StringUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

public class GoodsPropImporter
  implements IGoodsDataImporter
{
  protected final Logger logger = Logger.getLogger(getClass());

  public void imported(Object value, Element node, ImportDataSource importConfig, Map goods)
  {
    List<Attribute> list = importConfig.getPropList();
    if (value == null) value = "";
    if ("null".equals(value)) value = "";

    String dataType = node.getAttribute("dataType");
    if ((!StringUtil.isEmpty(dataType)) && 
      ("int".equals(dataType)) && 
      (!StringUtil.isEmpty(value.toString()))) {
      value = Integer.valueOf(Double.valueOf(value.toString()).intValue());
    }

    int propindex = Integer.valueOf(node.getAttribute("propindex")).intValue();

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("开始导入商品属性[" + propindex + "]...");
    }
    int i = 1;
    for (Attribute attr : list) {
      if (propindex == i)
      {
        if (this.logger.isDebugEnabled()) {
          this.logger.debug("属性名为[" + attr.getName() + "],值为[" + value + "]");
        }

        if (attr.getType() >= 3) {
          String[] options = attr.getOptionAr();
          if (options != null) {
            int index = 0;
            for (String op : options) {
              if (value.equals(op)) {
                goods.put("p" + i, Integer.valueOf(index));
                if (!this.logger.isDebugEnabled()) break;
                this.logger.debug("找到商品属性[" + propindex + "]值为[" + index + "]..."); break;
              }

              index++;
            }
            break;
          } } else { if (this.logger.isDebugEnabled()) {
            this.logger.debug("找到商品属性[" + propindex + "]值为[" + value + "]...");
          }

          goods.put("p" + i, value);

          break; }
      }
      else {
        i++;
      }
    }
    if (this.logger.isDebugEnabled())
      this.logger.debug("导入商品属性[" + propindex + "]完成");
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsPropImporter
 * JD-Core Version:    0.6.1
 */