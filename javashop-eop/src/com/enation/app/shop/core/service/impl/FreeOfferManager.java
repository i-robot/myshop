package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.FreeOffer;
import com.enation.app.shop.core.model.mapper.GiftMapper;
import com.enation.app.shop.core.service.IFreeOfferManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

public class FreeOfferManager extends BaseSupport<FreeOffer>
  implements IFreeOfferManager
{
  public void delete(String bid)
  {
    if ((bid == null) || (bid.equals("")))
      return;
    String sql = "update freeoffer set disabled=1  where fo_id in (" + bid + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public void revert(String bid)
  {
    if ((bid == null) || (bid.equals("")))
      return;
    String sql = "update freeoffer set disabled=0  where fo_id in (" + bid + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public void clean(String bid)
  {
    if ((bid == null) || (bid.equals("")))
      return;
    String sql = "delete  from  freeoffer   where fo_id in (" + bid + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public FreeOffer get(int fo_id)
  {
    String sql = "select * from freeoffer where fo_id=?";
    FreeOffer freeOffer = (FreeOffer)this.baseDaoSupport.queryForObject(sql, FreeOffer.class, new Object[] { Integer.valueOf(fo_id) });
    String pic = freeOffer.getPic();
    if (pic != null) {
      pic = UploadUtil.replacePath(pic);
    }
    freeOffer.setPic(pic);
    return freeOffer;
  }

  public Page list(int page, int pageSize)
  {
    long now = System.currentTimeMillis();
    String sql = "select f.*,'' cat_name from freeoffer f where disabled=0 and publishable=0 and startdate<=" + now + " and enddate>=" + now + "  order by sorder asc ";
    return this.baseDaoSupport.queryForPage(sql, page, pageSize, new GiftMapper(), new Object[0]);
  }

  public void saveAdd(FreeOffer freeOffer)
  {
    this.baseDaoSupport.insert("freeoffer", freeOffer);
  }

  public void update(FreeOffer freeOffer)
  {
    this.baseDaoSupport.update("freeoffer", freeOffer, "fo_id=" + freeOffer.getFo_id());
  }

  private String getListSql()
  {
    String sql = "select fo.*,c.cat_name as cat_name from " + getTableName("freeoffer") + " fo left join " + getTableName("freeoffer_category") + " c on fo.fo_category_id=c.cat_id ";

    return sql;
  }

  public Page pageTrash(String name, String order, int page, int pageSize)
  {
    order = order == null ? " fo_id desc" : order;
    String sql = getListSql();
    name = " fo.disabled=1 and fo_name like '%" + name + "%'";
    sql = sql + " and " + name;
    sql = sql + " order by  " + order;
    Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new GiftMapper(), new Object[0]);
    return webpage;
  }

  public Page list(String name, String order, int page, int pageSize)
  {
    order = order == null ? " fo_id desc" : order;
    String sql = getListSql();
    name = " fodisabled=0 and fo_name like '%" + name + "%'";
    sql = sql + " and " + name;
    sql = sql + " order by  " + order;
    Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new GiftMapper(), new Object[0]);
    return webpage;
  }

  public List getOrderGift(int orderId)
  {
    String sql = "select * from order_gift where order_id = ?";
    return this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(orderId) });
  }

  public List list(Integer[] ids)
  {
    if ((ids == null) || (ids.length == 0)) return new ArrayList();

    return this.baseDaoSupport.queryForList("select * from freeoffer where fo_id in(" + StringUtil.arrayToString(ids, ",") + ") ", new GiftMapper(), new Object[0]);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.FreeOfferManager
 * JD-Core Version:    0.6.1
 */