package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.FreeOfferCategory;
import com.enation.app.shop.core.service.IFreeOfferCategoryManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import java.util.List;

public class FreeOfferCategoryManager extends BaseSupport<FreeOfferCategory>
  implements IFreeOfferCategoryManager
{
  public void clean(String bid)
  {
    if ((bid == null) || (bid.equals("")))
      return;
    String sql = "delete  from  freeoffer_category   where cat_id in (" + bid + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public void delete(String bid)
  {
    if ((bid == null) || (bid.equals("")))
      return;
    String sql = "update freeoffer_category set disabled=1  where cat_id in (" + bid + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public FreeOfferCategory get(int cat_id)
  {
    String sql = "select * from freeoffer_category where cat_id=?";
    return (FreeOfferCategory)this.baseDaoSupport.queryForObject(sql, FreeOfferCategory.class, new Object[] { Integer.valueOf(cat_id) });
  }

  public List getFreeOfferCategoryList()
  {
    String sql = "select * from freeoffer_category";
    return this.baseDaoSupport.queryForList(sql, new Object[0]);
  }

  public Page pageTrash(String name, String order, int page, int pageSize)
  {
    order = order == null ? " cat_id desc" : order;
    String sql = "select * from freeoffer_category";
    name = " disabled=1 and cat_name like '%" + name + "%'";
    sql = sql + " where " + name;
    sql = sql + " order by  " + order;
    Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public void revert(String bid)
  {
    if ((bid == null) || (bid.equals("")))
      return;
    String sql = "update freeoffer_category set disabled=0  where cat_id in (" + bid + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public void saveAdd(FreeOfferCategory freeOfferCategory)
  {
    this.baseDaoSupport.insert("freeoffer_category", freeOfferCategory);
  }

  public Page searchFreeOfferCategory(String name, String order, int page, int pageSize)
  {
    order = order == null ? " cat_id desc" : order;
    String sql = "select * from freeoffer_category";
    name = " disabled=0 and cat_name like '%" + name + "%'";
    sql = sql + " where " + name;
    sql = sql + " order by  " + order;
    Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public void update(FreeOfferCategory freeOfferCategory)
  {
    this.baseDaoSupport.update("freeoffer_category", freeOfferCategory, "cat_id=" + freeOfferCategory.getCat_id());
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.FreeOfferCategoryManager
 * JD-Core Version:    0.6.1
 */