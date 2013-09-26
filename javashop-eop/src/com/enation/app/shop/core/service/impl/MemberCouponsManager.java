package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.service.ISettingService;
import com.enation.app.shop.component.coupon.MemberCoupon;
import com.enation.app.shop.core.model.PointHistory;
import com.enation.app.shop.core.service.IMemberCouponsManager;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IPointHistoryManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MemberCouponsManager extends BaseSupport
  implements IMemberCouponsManager
{
  private ISettingService settingService;
  private IMemberManager memberManager;
  private IPointHistoryManager pointHistoryManager;

  public void updateMemberid(int mc_id, int memberid)
  {
    this.baseDaoSupport.execute("update member_coupon  set memberid = ? where id = ? ", new Object[] { Integer.valueOf(memberid), Integer.valueOf(mc_id) });
  }

  public Page queryAllCoupons(int memberid, int pageNo, int pageSize, int status, int outdate)
  {
    String sql = "SELECT * FROM " + getTableName("member_coupon") + " where memberid = ?";
    if (status == 0) {
      sql = sql + " and used = 0 ";
    }
    if (status == 1) {
      sql = sql + " and used = 1 ";
    }
    if (outdate == 0) {
      sql = sql + " and " + DateUtil.getDateline() + " <= end_time  ";
    }
    if (outdate == 1) {
      sql = sql + " and " + DateUtil.getDateline() + " > end_time  ";
    }

    Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { Integer.valueOf(memberid) });
    return page;
  }

  public MemberCoupon get(Integer id) {
    MemberCoupon memberCoupon = (MemberCoupon)this.baseDaoSupport.queryForObject("select * from  member_coupon  where id= ? ", MemberCoupon.class, new Object[] { id });
    return memberCoupon;
  }
  public Page pageMemberCoupons(int pageNo, int pageSize) {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    String str_mc_use_times = this.settingService.getSetting("coupons", "coupon.mc.use_times");

    int mc_use_times = str_mc_use_times == null ? 1 : Integer.valueOf(str_mc_use_times).intValue();

    String sql = "select * from " + getTableName("member_coupon") + " as mc left join " + getTableName("coupons") + " as c on c.cpns_id=mc.cpns_id  left join " + getTableName("promotion") + " as p on c.pmt_id=p.pmt_id";

    sql = sql + " where member_id=?";
    sql = sql + " order by mc.memc_gen_time desc";
    Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });

    List<Map> list = (List)page.getResult();
    for (Map map : list) {
      if (((String)map.get("cpns_status")).equals("1")) {
        if (isLevelAllowuse(((Integer)map.get("pmt_id")).intValue(), member.getLv_id().intValue()))
        {
          Long curTime = Long.valueOf(new Date().getTime());
          if ((((Long)map.get("pmt_time_begin")).longValue() <= curTime.longValue()) && (curTime.longValue() < ((Long)map.get("pmt_time_end")).longValue()))
          {
            if (((Integer)map.get("memc_used_times")).intValue() < mc_use_times) {
              if (((String)map.get("memc_enabled")).equals("true"))
              {
                map.put("status", "可使用");
              }
              else map.put("status", "本优惠券已作废");
            }
            else
              map.put("status", "本优惠券次数已用完");
          }
          else
            map.put("status", "还未开始或已过期");
        }
        else {
          map.put("status", "本级别不准使用");
        }
      }
      else map.put("status", "此种优惠券已取消");
    }

    return page;
  }

  public Page pageExchangeCoupons(int pageNo, int pageSize)
  {
    Long curTime = Long.valueOf(new Date().getTime());
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    String sql = "select *,c.pmt_id as pmt_id from " + getTableName("coupons") + " as c left join " + getTableName("promotion") + " as p on c.pmt_id=p.pmt_id";

    sql = sql + " where cpns_type='1' and cpns_point is not null";
    sql = sql + " and cpns_status='1' AND pmt_time_begin <= " + curTime + " and pmt_time_end >" + curTime;

    Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
    List<Map> list = (List)page.getResult();
    for (Map map : list) {
      if (isLevelAllowuse(((Integer)map.get("pmt_id")).intValue(), member.getLv_id().intValue()))
      {
        map.put("use_status", Integer.valueOf(1));
      }
      else map.put("use_status", Integer.valueOf(0));
    }

    return page;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void exchange(int cpns_id)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    String sql = "select cpns_point from coupons where cpns_status='1' and cpns_type='1' and cpns_point is not null and cpns_id=?";
    int point = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(cpns_id) });
    if (member.getPoint().intValue() >= point) {
      member.setPoint(Integer.valueOf(member.getPoint().intValue() - point));
      this.memberManager.edit(member);
      generateCoupon(cpns_id, member.getMember_id().intValue());

      PointHistory pointHistory = new PointHistory();
      pointHistory.setMember_id(member.getMember_id().intValue());
      pointHistory.setPoint(0 - point);
      pointHistory.setReason("exchange_coupon");
      pointHistory.setTime(Long.valueOf(new Date().getTime()));
      pointHistory.setType(3);
      this.pointHistoryManager.addPointHistory(pointHistory);
    } else {
      throw new RuntimeException("积分扣除超过会员已有积分");
    }
  }

  private boolean isLevelAllowuse(int pmt_id, int lv_id)
  {
    int count = this.baseDaoSupport.queryForInt("select count(lv_id) from pmt_member_lv where pmt_id = ? and lv_id = ?", new Object[] { Integer.valueOf(pmt_id), Integer.valueOf(lv_id) });

    return count > 0;
  }

  private String makeCouponCode(int num, String prefix, int member_id)
  {
    return prefix + DateUtil.toString(new Date(), "yyyyMMddHHmmss") + member_id + num;
  }

  private void generateCoupon(int cpns_id, int member_id)
  {
    Long curTime = Long.valueOf(new Date().getTime());
    String sql = "select * from " + getTableName("coupons") + " as c left join " + getTableName("promotion") + " as p on c.pmt_id=p.pmt_id";

    sql = sql + " where cpns_status='1' and cpns_type='1' and c.cpns_id=" + cpns_id + " and pmt_time_begin <= " + curTime + " and pmt_time_end >" + curTime;

    List list = this.daoSupport.queryForList(sql, new Object[0]);
    MemberCoupon memberCoupon;
    if ((list != null) && (list.size() > 0)) {
      Map map = (Map)list.get(0);
      String couponCode = makeCouponCode(((Long)map.get("cpns_gen_quantity")).intValue() + 1, (String)map.get("cpns_prefix"), member_id);

      memberCoupon = new MemberCoupon();
    }
  }

  public ISettingService getSettingService()
  {
    return this.settingService;
  }

  public void setSettingService(ISettingService settingService) {
    this.settingService = settingService;
  }

  public IMemberManager getMemberManager() {
    return this.memberManager;
  }

  public void setMemberManager(IMemberManager memberManager) {
    this.memberManager = memberManager;
  }

  public IPointHistoryManager getPointHistoryManager() {
    return this.pointHistoryManager;
  }

  public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
    this.pointHistoryManager = pointHistoryManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberCouponsManager
 * JD-Core Version:    0.6.1
 */