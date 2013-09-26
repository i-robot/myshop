package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.PromotionActivity;
import com.enation.app.shop.core.service.IPromotionActivityManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.ObjectNotFoundException;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PromotionActivityManager extends BaseSupport<PromotionActivity>
  implements IPromotionActivityManager
{
  public void add(PromotionActivity activity)
  {
    if (activity == null) throw new IllegalArgumentException("param activity is NULL");
    if (activity.getName() == null) throw new IllegalArgumentException("param activity.name is NULL");
    if (activity.getBegin_time() == null) throw new IllegalArgumentException("param activity.begin_time is NULL");
    if (activity.getEnd_time() == null) throw new IllegalArgumentException("param activity.end_time is NULL");
    this.baseDaoSupport.insert("promotion_activity", activity);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer[] idArray)
  {
    if ((idArray != null) && (idArray.length > 0)) {
      String ids = StringUtil.arrayToString(idArray, ",");
      this.baseDaoSupport.execute("delete from promotion_activity where id in (" + ids + ")", new Object[0]);

      this.daoSupport.execute("delete from " + getTableName("pmt_member_lv") + " where pmt_id in(select pmt_id from " + getTableName("promotion") + " where pmta_id in(?))", new Object[] { ids });
      this.daoSupport.execute("delete from " + getTableName("pmt_goods") + " where pmt_id in(select pmt_id from " + getTableName("promotion") + " where pmta_id in(?))", new Object[] { ids });
      this.baseDaoSupport.execute("delete from promotion where pmta_id in(?)", new Object[] { ids });
    }
  }

  public void edit(PromotionActivity activity)
  {
    if (activity.getId() == null) throw new IllegalArgumentException("param activity.id is NULL");
    if (activity.getName() == null) throw new IllegalArgumentException("param activity.name is NULL");
    if (activity.getBegin_time() == null) throw new IllegalArgumentException("param activity.begin_time is NULL");
    if (activity.getEnd_time() == null) throw new IllegalArgumentException("param activity.end_time is NULL");
    this.baseDaoSupport.update("promotion_activity", activity, "id=" + activity.getId());
  }

  public PromotionActivity get(Integer id)
  {
    if (id == null) throw new IllegalArgumentException("param activity.id is NULL");
    PromotionActivity activity = (PromotionActivity)this.baseDaoSupport.queryForObject("select * from promotion_activity where id = ?", PromotionActivity.class, new Object[] { id });

    if (activity == null) throw new ObjectNotFoundException("activity is NULL");
    return activity;
  }

  public Page list(int pageNo, int pageSize)
  {
    String sql = "select * from promotion_activity order by id desc";
    return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.PromotionActivityManager
 * JD-Core Version:    0.6.1
 */