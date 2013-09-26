package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.Gnotify;
import com.enation.app.shop.core.service.IGnotifyManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;

public class GnotifyManager extends BaseSupport
  implements IGnotifyManager
{
  public Page pageGnotify(int pageNo, int pageSize)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();

    String sql = "select a.*,b.sn, b.image_default image,b.store store, b.name name, b.price price, b.mktprice mktprice,b.cat_id cat_id from " + getTableName("gnotify") + " a inner join " + getTableName("goods") + " b on b.goods_id = a.goods_id";

    sql = sql + " and a.member_id = " + member.getMember_id();
    Page webpage = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
    return webpage;
  }

  public void deleteGnotify(int gnotify_id)
  {
    this.baseDaoSupport.execute("delete from gnotify where gnotify_id = ?", new Object[] { Integer.valueOf(gnotify_id) });
  }

  public void addGnotify(int goodsid)
  {
    String sql = "select count(0) from gnotify where goods_id=?";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(goodsid) });
    if (count > 0) return;

    IUserService userService = UserServiceFactory.getUserService();
    Member member = null;
    if (userService != null) {
      member = userService.getCurrentMember();
    }
    Gnotify gnotify = new Gnotify();
    gnotify.setCreate_time(Long.valueOf(System.currentTimeMillis()));
    gnotify.setGoods_id(goodsid);
    if (member != null) {
      gnotify.setMember_id(member.getMember_id().intValue());
      gnotify.setEmail(member.getEmail());
    }
    this.baseDaoSupport.insert("gnotify", gnotify);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.GnotifyManager
 * JD-Core Version:    0.6.1
 */