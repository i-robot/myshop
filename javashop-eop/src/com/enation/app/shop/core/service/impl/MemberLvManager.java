package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.MemberLv;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

public class MemberLvManager extends BaseSupport<MemberLv>
  implements IMemberLvManager
{
  public void add(MemberLv lv)
  {
    this.baseDaoSupport.insert("member_lv", lv);
  }

  public void edit(MemberLv lv)
  {
    this.baseDaoSupport.update("member_lv", lv, "lv_id=" + lv.getLv_id());
  }

  public Integer getDefaultLv()
  {
    String sql = "select * from member_lv where default_lv=1";
    List lvList = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[0]);
    if ((lvList == null) || (lvList.isEmpty())) {
      return null;
    }

    return ((MemberLv)lvList.get(0)).getLv_id();
  }

  public Page list(String order, int page, int pageSize)
  {
    order = order == null ? " lv_id desc" : order;
    String sql = "select * from member_lv ";
    sql = sql + " order by  " + order;
    Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public MemberLv get(Integer lvId)
  {
    if ((lvId != null) && (lvId.intValue() != 0)) {
      String sql = "select * from member_lv where lv_id=?";
      MemberLv lv = (MemberLv)this.baseDaoSupport.queryForObject(sql, MemberLv.class, new Object[] { lvId });

      return lv;
    }
    return null;
  }

  public void delete(String id)
  {
    if ((id == null) || (id.equals("")))
      return;
    String sql = "delete from member_lv where lv_id in (" + id + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public List<MemberLv> list()
  {
    String sql = "select * from member_lv order by lv_id";
    List lvlist = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[0]);
    return lvlist;
  }

  public List<MemberLv> list(String ids)
  {
    if (StringUtil.isEmpty(ids)) return new ArrayList();

    String sql = "select * from member_lv where  lv_id in(" + ids + ") order by lv_id";
    List lvlist = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[0]);
    return lvlist;
  }

  public MemberLv getByPoint(int point)
  {
    String sql = "select * from member_lv where  point<=? order by point desc";
    List list = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[] { Integer.valueOf(point) });
    if ((list == null) || (list.isEmpty())) {
      return null;
    }
    return (MemberLv)list.get(0);
  }

  public MemberLv getNextLv(int point) {
    String sql = "select * from member_lv where  point>? order by point ASC";
    List list = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[] { Integer.valueOf(point) });
    if ((list == null) || (list.isEmpty())) {
      return null;
    }
    return (MemberLv)list.get(0);
  }

  public List getCatDiscountByLv(int lv_id)
  {
    String sql = "select d.*,c.name from " + getTableName("member_lv_discount") + " d inner join " + getTableName("goods_cat") + " c on d.cat_id=c.cat_id where d.lv_id=" + lv_id;
    return this.daoSupport.queryForList(sql, new Object[0]);
  }

  public List getHaveCatDiscountByLv(int lv_id)
  {
    String sql = "select * from member_lv_discount where discount>0 and lv_id=" + lv_id;
    return this.baseDaoSupport.queryForList(sql, new Object[0]);
  }

  public void saveCatDiscountByLv(int[] cat_ids, int[] discounts, int lv_id)
  {
    if (cat_ids.length != discounts.length) {
      throw new RuntimeException("非法参数");
    }
    for (int i = 0; i < discounts.length; i++)
      if (discounts[i] != 0)
      {
        String sql = "update member_lv_discount set discount=" + discounts[i] + " where cat_id=" + cat_ids[i] + " and lv_id=" + lv_id;

        this.baseDaoSupport.execute(sql, new Object[0]);
      }
  }

  public List getHaveLvDiscountByCat(int cat_id)
  {
    String sql = "select * from member_lv_discount where discount>0 and cat_id=" + cat_id;
    return this.baseDaoSupport.queryForList(sql, new Object[0]);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberLvManager
 * JD-Core Version:    0.6.1
 */