package com.enation.app.shop.core.service.impl.batchimport;

import com.enation.app.shop.core.model.ImportDataSource;
import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
import com.enation.app.shop.core.service.impl.batchimport.util.GoodsImageReader;
import com.enation.framework.database.IDaoSupport;
import java.util.Map;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

public class GoodsImageImporter
  implements IGoodsDataImporter
{
  protected final Logger logger = Logger.getLogger(getClass());
  private IDaoSupport baseDaoSupport;
  private GoodsImageReader goodsImageReader;

  public void imported(Object value, Element node, ImportDataSource importDs, Map goods)
  {
    Integer goodsid = (Integer)goods.get("goods_id");
    String excludeStr = node.getAttribute("exclude");

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("开始导入商品[" + goodsid + "]图片...");
    }

    String[] images = this.goodsImageReader.read(importDs.getDatafolder(), goodsid.toString(), excludeStr);
    if (images != null) {
      this.baseDaoSupport.execute("update goods set image_file=? ,image_default=? where goods_id=?", new Object[] { images[0], images[1], goodsid });
    }
    if (this.logger.isDebugEnabled())
      this.logger.debug(" 商品[" + goodsid + "]图片导入完成");
  }

  public IDaoSupport getBaseDaoSupport()
  {
    return this.baseDaoSupport;
  }

  public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
    this.baseDaoSupport = baseDaoSupport;
  }

  public GoodsImageReader getGoodsImageReader() {
    return this.goodsImageReader;
  }

  public void setGoodsImageReader(GoodsImageReader goodsImageReader) {
    this.goodsImageReader = goodsImageReader;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsImageImporter
 * JD-Core Version:    0.6.1
 */