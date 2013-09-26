package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.LimitBuy;
import com.enation.app.shop.core.model.LimitBuyGoods;
import com.enation.app.shop.core.service.ILimitBuyManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class LimitBuyManager extends BaseSupport
  implements ILimitBuyManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public void add(LimitBuy limitBuy)
  {
    List limitBuyGoodsList = limitBuy.getLimitBuyGoodsList();

    limitBuy.setAdd_time(DateUtil.getDatelineLong());
    this.baseDaoSupport.insert("limitbuy", limitBuy);
    Integer limitBuyId = Integer.valueOf(this.baseDaoSupport.getLastId("limitbuy"));
    addGoods(limitBuyGoodsList, limitBuyId);
  }

  private void addGoods(List<LimitBuyGoods> limitBuyGoodsList, Integer limitBuyId)
  {
    if ((limitBuyGoodsList == null) || (limitBuyGoodsList.isEmpty())) throw new RuntimeException("添加限时购买的商品列表不能为空");
    for (LimitBuyGoods limitBuyGoods : limitBuyGoodsList) {
      this.baseDaoSupport.execute("insert into limitbuy_goods (limitbuyid,goodsid,price)values(?,?,?)", new Object[] { limitBuyId, limitBuyGoods.getGoodsid(), limitBuyGoods.getPrice() });

      updateGoodsLimit(limitBuyId, 1);
    }
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer id) {
    updateGoodsLimit(id, 0);
    this.baseDaoSupport.execute("delete from limitbuy_goods where limitbuyid=?", new Object[] { id });
    this.baseDaoSupport.execute("delete from limitbuy where id=?", new Object[] { id });
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void edit(LimitBuy limitBuy)
  {
    List limitBuyGoodsList = limitBuy.getLimitBuyGoodsList();
    this.baseDaoSupport.update("limitbuy", limitBuy, "id=" + limitBuy.getId());
    Integer limitBuyId = limitBuy.getId();
    this.baseDaoSupport.execute("delete from limitbuy_goods where limitbuyid=?", new Object[] { limitBuyId });

    updateGoodsLimit(limitBuyId, 0);
    addGoods(limitBuyGoodsList, limitBuyId);
  }

  private void updateGoodsLimit(Integer limitid, int islimit)
  {
    this.daoSupport.execute("update " + getTableName("goods") + " set islimit=? where goods_id in(select goodsid from " + getTableName("limitbuy_goods") + " where limitbuyid=?)", new Object[] { Integer.valueOf(islimit), limitid });
  }

  public Page list(int pageNo, int pageSize)
  {
    String sql = "select * from limitbuy order by add_time desc";
    return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, LimitBuy.class, new Object[0]);
  }

  public List<LimitBuy> listEnable()
  {
    long now = DateUtil.getDatelineLong();

    String sql = "select * from limitbuy where start_time<? and end_time>? order by add_time desc";
    List<LimitBuy> limitBuyBuyList = this.baseDaoSupport.queryForList(sql, LimitBuy.class, new Object[] { Long.valueOf(now), Long.valueOf(now) });

    sql = "select g.* ,lg.limitbuyid limitbuyid ,lg.price limitprice  from " + getTableName("limitbuy_goods") + " lg  ," + getTableName("goods") + " g where lg.goodsid= g.goods_id and lg.limitbuyid in";
    sql = sql + "(select id from " + getTableName("limitbuy") + " where start_time<? and end_time>?)";
    List goodsList = this.daoSupport.queryForList(sql, new Object[] { Long.valueOf(now), Long.valueOf(now) });
    for (LimitBuy limitBuy : limitBuyBuyList) {
      List list = findGoods(goodsList, limitBuy.getId());
      limitBuy.setGoodsList(list);
    }
    return limitBuyBuyList;
  }
  public List<Map> listEnableGoods() {
    long now = DateUtil.getDatelineLong();

    String sql = "select g.* ,lg.limitbuyid limitbuyid ,lg.price limitprice  from " + getTableName("limitbuy_goods") + " lg  ," + getTableName("goods") + " g where lg.goodsid= g.goods_id and lg.limitbuyid in";
    sql = sql + "(select id from " + getTableName("limitbuy") + " where start_time<? and end_time>?)";
    List goodsList = this.daoSupport.queryForList(sql, new Object[] { Long.valueOf(now), Long.valueOf(now) });
    return goodsList;
  }
  private List<Map> findGoods(List<Map> goodsList, Integer limitbuyid) {
    List list = new ArrayList();
    for (Map goods : goodsList) {
      if (limitbuyid.compareTo((Integer)goods.get("limitbuyid")) == 0) {
        list.add(goods);
      }
    }

    return list;
  }

  public LimitBuy get(Integer id) {
    String sql = "select * from limitbuy where  id=?";
    LimitBuy limitBuy = (LimitBuy)this.baseDaoSupport.queryForObject(sql, LimitBuy.class, new Object[] { id });
    sql = "select g.* ,lg.limitbuyid limitbuyid from " + getTableName("limitbuy_goods") + " lg  ," + getTableName("goods") + " g where lg.goodsid= g.goods_id and lg.limitbuyid =?";
    List goodsList = this.daoSupport.queryForList(sql, new Object[] { id });
    limitBuy.setGoodsList(goodsList);
    return limitBuy;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.LimitBuyManager
 * JD-Core Version:    0.6.1
 */