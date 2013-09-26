package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.Favorite;
import com.enation.app.shop.core.service.IFavoriteManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import java.util.List;

public class FavoriteManager extends BaseSupport
  implements IFavoriteManager
{
  public Page list(int pageNo, int pageSize)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    String sql = "select g.*, f.favorite_id from " + getTableName("favorite") + " f left join " + getTableName("goods") + " g on g.goods_id = f.goods_id";

    sql = sql + " where f.member_id = ?";
    Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });

    return page;
  }

  public Page list(int memberid, int pageNo, int pageSize)
  {
    String sql = "select g.*, f.favorite_id from " + getTableName("favorite") + " f left join " + getTableName("goods") + " g on g.goods_id = f.goods_id";

    sql = sql + " where f.member_id = ?";
    Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { Integer.valueOf(memberid) });
    return page;
  }

  public void delete(int favorite_id)
  {
    this.baseDaoSupport.execute("delete from favorite where favorite_id = ?", new Object[] { Integer.valueOf(favorite_id) });
  }

  public void add(Integer goodsid)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    Favorite favorite = new Favorite();
    favorite.setGoods_id(goodsid.intValue());
    favorite.setMember_id(member.getMember_id().intValue());
    this.baseDaoSupport.insert("favorite", favorite);
  }

  public int getCount(Integer goodsid, Integer memeberid)
  {
    return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM favorite WHERE goods_id=? AND member_id=?", new Object[] { goodsid, memeberid });
  }

  public int getCount(Integer memberId) {
    return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM favorite WHERE  member_id=?", new Object[] { memberId });
  }

  public List list()
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();

    return this.baseDaoSupport.queryForList("select * from favorite where member_id=?", Favorite.class, new Object[] { member.getMember_id() });
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.FavoriteManager
 * JD-Core Version:    0.6.1
 */