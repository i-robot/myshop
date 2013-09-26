package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.GoodsLvPrice;
import com.enation.app.shop.core.service.IMemberPriceManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import java.util.List;

public class MemberPriceManager extends BaseSupport<GoodsLvPrice>
  implements IMemberPriceManager
{
  public List<GoodsLvPrice> listPriceByGid(int goodsid)
  {
    String sql = "select * from goods_lv_price where goodsid =?";
    return this.baseDaoSupport.queryForList(sql, GoodsLvPrice.class, new Object[] { Integer.valueOf(goodsid) });
  }

  public List<GoodsLvPrice> listPriceByPid(int productid)
  {
    String sql = "select * from goods_lv_price where productid =?";
    return this.baseDaoSupport.queryForList(sql, GoodsLvPrice.class, new Object[] { Integer.valueOf(productid) });
  }

  public List<GoodsLvPrice> listPriceByLvid(int lvid)
  {
    String sql = "select * from goods_lv_price where lvid =?";
    return this.baseDaoSupport.queryForList(sql, GoodsLvPrice.class, new Object[] { Integer.valueOf(lvid) });
  }

  public void save(List<GoodsLvPrice> goodsPriceList)
  {
    if ((goodsPriceList != null) && (goodsPriceList.size() > 0)) {
      this.baseDaoSupport.execute("delete from  goods_lv_price  where goodsid=?", new Object[] { Integer.valueOf(((GoodsLvPrice)goodsPriceList.get(0)).getGoodsid()) });

      for (GoodsLvPrice goodsPrice : goodsPriceList)
        this.baseDaoSupport.insert("goods_lv_price", goodsPrice);
    }
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberPriceManager
 * JD-Core Version:    0.6.1
 */