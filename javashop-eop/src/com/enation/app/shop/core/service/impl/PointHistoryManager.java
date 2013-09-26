package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.PointHistory;
import com.enation.app.shop.core.service.IPointHistoryManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import java.util.List;

public class PointHistoryManager extends BaseSupport
  implements IPointHistoryManager
{
  public Page pagePointHistory(int pageNo, int pageSize, int pointType)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();

    String sql = "select * from point_history where member_id = ? and point_type=? order by time desc";

    Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id(), Integer.valueOf(pointType) });

    return webpage;
  }

  public Page pagePointHistory(int pageNo, int pageSize) {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();

    String sql = "select * from point_history where member_id = ? order by time desc";

    Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });

    return webpage;
  }

  public Long getConsumePoint(int pointType)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    Long result = Long.valueOf(this.baseDaoSupport.queryForLong("select SUM(point) from point_history where  member_id = ?  and  type=0  and point_type=?", new Object[] { member.getMember_id(), Integer.valueOf(pointType) }));

    return result;
  }

  public Long getGainedPoint(int pointType)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    Long result = Long.valueOf(this.baseDaoSupport.queryForLong("select SUM(point) from point_history where    member_id = ? and  type=1  and point_type=?", new Object[] { member.getMember_id(), Integer.valueOf(pointType) }));

    return result;
  }

  public void addPointHistory(PointHistory pointHistory)
  {
    this.baseDaoSupport.insert("point_history", pointHistory);
  }

  public List<PointHistory> listPointHistory(int member_id, int pointtype)
  {
    String sql = "select * from point_history where member_id = ? and point_type = ? order by time desc";
    List list = this.baseDaoSupport.queryForList(sql, PointHistory.class, new Object[] { Integer.valueOf(member_id), Integer.valueOf(pointtype) });
    return list;
  }

  public Page pagePointFreeze(int pageNo, int pageSize)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    String sql = "select * from freeze_point where memberid = ? order by id desc";
    Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });

    return webpage;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.PointHistoryManager
 * JD-Core Version:    0.6.1
 */