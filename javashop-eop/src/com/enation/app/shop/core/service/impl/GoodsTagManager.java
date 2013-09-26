package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Cat;
import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.app.shop.core.service.IGoodsTagManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;

public class GoodsTagManager extends BaseSupport<Goods>
  implements IGoodsTagManager
{
  private IGoodsCatManager goodsCatManager;

  public Page getGoodsList(int tagid, int page, int pageSize)
  {
    StringBuffer sql = new StringBuffer();
    sql.append("select g.goods_id,g.name,r.tag_id,r.ordernum from " + getTableName("tag_rel") + " r LEFT JOIN " + getTableName("goods") + " g ON g.goods_id=r.rel_id where g.disabled=0 and g.market_enable=1");
    sql.append(" and r.tag_id = ?");
    sql.append(" order by r.ordernum desc");
    Page webpage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[] { Integer.valueOf(tagid) });

    return webpage;
  }

  public Page getGoodsList(int tagid, int catid, int page, int pageSize)
  {
    Cat cat = this.goodsCatManager.getById(Integer.valueOf(catid).intValue());
    if (cat == null) {
      return null;
    }
    StringBuffer sql = new StringBuffer();
    sql.append("select g.goods_id,g.name,r.tag_id,r.ordernum from " + getTableName("tag_rel") + " r LEFT JOIN " + getTableName("goods") + " g ON g.goods_id=r.rel_id where g.disabled=0 and g.market_enable=1");
    sql.append(" and r.tag_id = ?");

    sql.append(" and  g.cat_id in(");
    sql.append("select c.cat_id from " + getTableName("goods_cat") + " ");
    sql.append(" c where c.cat_path like '" + cat.getCat_path() + "%')");

    sql.append(" order by r.ordernum desc");
    Page webpage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[] { Integer.valueOf(tagid) });

    return webpage;
  }

  public void addTag(int tagId, int goodsId)
  {
    if (this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM tag_rel WHERE tag_id=? AND rel_id=?", new Object[] { Integer.valueOf(tagId), Integer.valueOf(goodsId) }) <= 0)
    {
      this.baseDaoSupport.execute("INSERT INTO tag_rel(tag_id,rel_id,ordernum) VALUES(?,?,0)", new Object[] { Integer.valueOf(tagId), Integer.valueOf(goodsId) });

      if (tagId == 4)
        this.baseDaoSupport.execute("update goods set istejia = 1 where goods_id=?", new Object[] { Integer.valueOf(goodsId) });
    }
  }

  public void removeTag(int tagId, int goodsId)
  {
    this.baseDaoSupport.execute("DELETE FROM tag_rel WHERE tag_id=? AND rel_id=?", new Object[] { Integer.valueOf(tagId), Integer.valueOf(goodsId) });

    this.baseDaoSupport.execute("update goods set istejia = ? where goods_id = ?", new Object[] { Integer.valueOf(0), Integer.valueOf(goodsId) });
  }

  public void updateOrderNum(Integer[] goodsIds, Integer[] tagids, Integer[] ordernum)
  {
    if ((goodsIds != null) && (goodsIds.length > 0))
      for (int i = 0; i < goodsIds.length; i++)
        if ((goodsIds[i] != null) && (tagids[i] != null) && (ordernum[i] != null))
          try
          {
            this.baseDaoSupport.execute("UPDATE tag_rel set ordernum=? WHERE tag_id=? AND rel_id=?", new Object[] { ordernum[i], tagids[i], goodsIds[i] });
          }
          catch (Exception ex)
          {
          }
  }

  public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
  {
    this.goodsCatManager = goodsCatManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsTagManager
 * JD-Core Version:    0.6.1
 */