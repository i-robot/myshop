package com.enation.app.shop.core.service.impl.cache;

import com.enation.app.base.core.model.MemberLv;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.cache.AbstractCacheProxy;
import com.enation.framework.cache.ICache;
import com.enation.framework.database.Page;
import java.util.List;
import org.apache.log4j.Logger;

public class MemberLvCacheProxy extends AbstractCacheProxy<List<MemberLv>>
  implements IMemberLvManager
{
  private IMemberLvManager memberLvManager;
  public static final String CACHE_KEY = "member_lv";

  public MemberLvCacheProxy(IMemberLvManager memberLvManager)
  {
    super("member_lv");
    this.memberLvManager = memberLvManager;
  }

  private void cleanCache() {
    EopSite site = EopContext.getContext().getCurrentSite();
    this.cache.remove("member_lv_" + site.getUserid() + "_" + site.getId() + "_0");
  }

  public void add(MemberLv lv) {
    this.memberLvManager.add(lv);
    cleanCache();
  }

  public void edit(MemberLv lv)
  {
    this.memberLvManager.edit(lv);
    cleanCache();
  }

  public Integer getDefaultLv()
  {
    return this.memberLvManager.getDefaultLv();
  }

  public Page list(String order, int page, int pageSize)
  {
    return this.memberLvManager.list(order, page, pageSize);
  }

  public MemberLv get(Integer lvId)
  {
    return this.memberLvManager.get(lvId);
  }

  public void delete(String id)
  {
    this.memberLvManager.delete(id);
    cleanCache();
  }

  public List<MemberLv> list()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    List memberLvList = (List)this.cache.get("member_lv_" + site.getUserid() + "_" + site.getId());
    if (memberLvList == null) {
      memberLvList = this.memberLvManager.list();
      this.cache.put("member_lv_" + site.getUserid() + "_" + site.getId(), memberLvList);
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("load memberLvList from database");
      }
    }
    else if (this.logger.isDebugEnabled()) {
      this.logger.debug("load memberLvList from cache");
    }

    return memberLvList;
  }

  public MemberLv getNextLv(int point) {
    return this.memberLvManager.getNextLv(point);
  }

  public List<MemberLv> list(String ids)
  {
    return this.memberLvManager.list(ids);
  }

  public MemberLv getByPoint(int point)
  {
    return this.memberLvManager.getByPoint(point);
  }

  public List getCatDiscountByLv(int lv_id)
  {
    return this.memberLvManager.getCatDiscountByLv(lv_id);
  }

  public List getHaveCatDiscountByLv(int lv_id)
  {
    return this.memberLvManager.getHaveCatDiscountByLv(lv_id);
  }

  public void saveCatDiscountByLv(int[] cat_ids, int[] discounts, int lv_id)
  {
    this.memberLvManager.saveCatDiscountByLv(cat_ids, discounts, lv_id);
  }

  public List getHaveLvDiscountByCat(int cat_id)
  {
    return this.memberLvManager.getHaveLvDiscountByCat(cat_id);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.cache.MemberLvCacheProxy
 * JD-Core Version:    0.6.1
 */