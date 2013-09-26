package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.service.IWelcomeInfoManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.DoubleMapper;
import com.enation.framework.database.IDaoSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WelcomeInfoManager extends BaseSupport
  implements IWelcomeInfoManager
{
  public Map mapWelcomInfo()
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    Map map = new HashMap();
    int oNum = this.baseDaoSupport.queryForInt("SELECT count(*) as oNum  FROM order where pay_status=0 and member_id=? and status != 4", new Object[] { member.getMember_id() });
    map.put("oNum", Integer.valueOf(oNum));
    int totalOrder = this.baseDaoSupport.queryForInt("SELECT count(*) as totalOrder  FROM order where member_id=? and disabled=0 ", new Object[] { member.getMember_id() });
    map.put("totalOrder", Integer.valueOf(totalOrder));
    int mNum = this.baseDaoSupport.queryForInt("SELECT count(*) as mNum FROM message where to_id=? and unread='0' and to_type=0 and disabled='false'", new Object[] { member.getMember_id() });
    map.put("mNum", Integer.valueOf(mNum));
    int pNum = this.baseDaoSupport.queryForInt("SELECT sum(point) as pNum FROM member where member_id=?", new Object[] { member.getMember_id() });
    map.put("pNum", Integer.valueOf(pNum));
    Object oaNum = this.baseDaoSupport.queryForObject("SELECT advance FROM member where member_id=?", new DoubleMapper(), new Object[] { member.getMember_id() });
    Double aNum = Double.valueOf(oaNum == null ? 0.0D : ((Double)oaNum).doubleValue());
    map.put("aNum", aNum);

    int couponNum = this.baseDaoSupport.queryForInt("SELECT count(*) as couponNum FROM member_coupon where memberid=?", new Object[] { member.getMember_id() });
    map.put("couponNum", Integer.valueOf(couponNum));

    Long curTime = Long.valueOf(new Date().getTime());

    String sql = "SELECT sum(discount_price) FROM  member_coupon WHERE used=1 and memberid = ?";
    Double couponprice = (Double)this.baseDaoSupport.queryForObject(sql, new DoubleMapper(), new Object[] { member.getMember_id() });
    map.put("couponprice", couponprice);

    int commentRNum = this.baseDaoSupport.queryForInt("SELECT count(*) as commentRNum FROM comments where author_id=? and display='true'", new Object[] { member.getMember_id() });

    map.put("commentRNum", Integer.valueOf(commentRNum));
    List pa = this.baseDaoSupport.queryForList("SELECT name FROM promotion_activity where enable='1' and end_time>=? and begin_time<=?", Object.class, new Object[] { curTime, curTime });
    pa = pa == null ? new ArrayList() : pa;
    map.put("pa", pa);
    return map;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.WelcomeInfoManager
 * JD-Core Version:    0.6.1
 */