package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.plugin.member.MemberPluginBundle;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IMemberPointManger;
import com.enation.eop.processor.httpcache.HttpCacheManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MemberManager extends BaseSupport<Member>
  implements IMemberManager
{
  protected IMemberLvManager memberLvManager;
  private IMemberPointManger memberPointManger;
  private MemberPluginBundle memberPluginBundle;

  @Transactional(propagation=Propagation.REQUIRED)
  public void logout()
  {
    Member member = UserServiceFactory.getUserService().getCurrentMember();
    member = get(member.getMember_id());
    ThreadContextHolder.getSessionContext().removeAttribute("curr_member");
    this.memberPluginBundle.onLogout(member);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public int register(Member member)
  {
    int result = add(member);
    this.memberPluginBundle.onRegister(member);

    return result;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public int add(Member member) {
    if (member == null) throw new IllegalArgumentException("member is null");
    if (member.getUname() == null) throw new IllegalArgumentException("member' uname is null");
    if (member.getPassword() == null) throw new IllegalArgumentException("member' password is null");
    if (member.getEmail() == null) throw new IllegalArgumentException("member'email is null");

    if (checkname(member.getUname()) == 1) {
      return 0;
    }

    Integer lvid = this.memberLvManager.getDefaultLv();
    member.setLv_id(lvid);

    member.setName(member.getUname());

    member.setPoint(Integer.valueOf(0));
    member.setAdvance(Double.valueOf(0.0D));
    member.setRegtime(Long.valueOf(System.currentTimeMillis()));
    member.setLastlogin(Long.valueOf(DateUtil.getDatelineLong()));
    member.setLogincount(Integer.valueOf(1));
    member.setPassword(StringUtil.md5(member.getPassword()));

    member.setMp(Integer.valueOf(0));
    member.setFace("");
    member.setMidentity(Integer.valueOf(0));
    member.setNickname(member.getEmail().split("@")[0]);

    this.baseDaoSupport.insert("member", member);
    int memberid = this.baseDaoSupport.getLastId("member");
    member.setMember_id(Integer.valueOf(memberid));

    return 1;
  }

  public void checkEmailSuccess(Member member)
  {
    int memberid = member.getMember_id().intValue();

    String sql = "update member set is_cheked = 1 where member_id =  " + memberid;
    this.baseDaoSupport.execute(sql, new Object[0]);
    this.memberPluginBundle.onEmailCheck(member);
  }

  public Member get(Integer memberId)
  {
    String sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where member_id=?";
    Member m = (Member)this.daoSupport.queryForObject(sql, Member.class, new Object[] { memberId });
    return m;
  }

  public Member getMemberByUname(String uname)
  {
    String sql = "select * from member where uname=?";
    List list = this.baseDaoSupport.queryForList(sql, Member.class, new Object[] { uname });
    Member m = null;
    if ((list != null) && (list.size() > 0)) {
      m = (Member)list.get(0);
    }
    return m;
  }

  public Member getMemberByEmail(String email) {
    String sql = "select * from member where email=?";
    List list = this.baseDaoSupport.queryForList(sql, Member.class, new Object[] { email });
    Member m = null;
    if ((list != null) && (list.size() > 0)) {
      m = (Member)list.get(0);
    }
    return m;
  }

  public Member edit(Member member)
  {
    this.baseDaoSupport.update("member", member, "member_id=" + member.getMember_id());

    ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
    return null;
  }

  public int checkname(String name)
  {
    String sql = "select count(0) from member where uname=?";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name });
    count = count > 0 ? 1 : 0;
    return count;
  }

  public int checkemail(String email) {
    String sql = "select count(0) from member where email=?";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { email });
    count = count > 0 ? 1 : 0;
    return count;
  }

  public Page list(String order, int page, int pageSize)
  {
    order = order == null ? " m.member_id desc" : order;
    String sql = "select m.*,lv.name as lv_name from " + getTableName("member") + " m left join " + getTableName("member_lv") + " lv on m.lv_id = lv.lv_id ";

    sql = sql + " order by  " + order;
    Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public void delete(String id)
  {
    if ((id == null) || (id.equals("")))
      return;
    String sql = "delete from member where member_id in (" + id + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public void updatePassword(String password)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    updatePassword(member.getMember_id(), password);
    member.setPassword(StringUtil.md5(password));
    ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
  }

  public void updatePassword(Integer memberid, String password)
  {
    String md5password = password == null ? StringUtil.md5("") : StringUtil.md5(password);

    String sql = "update member set password = ? where member_id =? ";
    this.baseDaoSupport.execute(sql, new Object[] { md5password, memberid });

    this.memberPluginBundle.onUpdatePassword(password, memberid.intValue());
  }

  public void updateFindCode(Integer memberid, String code)
  {
    String sql = "update member set find_code = ? where member_id =? ";
    this.baseDaoSupport.execute(sql, new Object[] { code, memberid });
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public int login(String username, String password) {
    String sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.uname=? and password=?";

    if (username.contains("@")) {
      sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.email=? and password=?";
    }

    List list = this.daoSupport.queryForList(sql, Member.class, new Object[] { username, StringUtil.md5(password) });
    if ((list == null) || (list.isEmpty())) {
      return 0;
    }

    Member member = (Member)list.get(0);
    long ldate = member.getLastlogin().longValue() * 1000L;
    Date date = new Date(ldate);
    Date today = new Date();
    int logincount = member.getLogincount().intValue();
    if (DateUtil.toString(date, "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM")))
      logincount++;
    else {
      logincount = 1;
    }
    Long upLogintime = member.getLastlogin();
    member.setLastlogin(Long.valueOf(DateUtil.getDatelineLong()));
    member.setLogincount(Integer.valueOf(logincount));
    edit(member);
    ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);

    HttpCacheManager.sessionChange();

    this.memberPluginBundle.onLogin(member, upLogintime);

    return 1;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public int loginWithCookie(String username, String password) {
    String sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.uname=? and password=?";

    if (username.contains("@")) {
      sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.email=? and password=?";
    }
    List list = this.daoSupport.queryForList(sql, Member.class, new Object[] { username, password });
    if ((list == null) || (list.isEmpty())) {
      return 0;
    }

    Member member = (Member)list.get(0);
    long ldate = member.getLastlogin().longValue() * 1000L;
    Date date = new Date(ldate);
    Date today = new Date();
    int logincount = member.getLogincount().intValue();
    if (DateUtil.toString(date, "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM")))
      logincount++;
    else {
      logincount = 1;
    }
    Long upLogintime = member.getLastlogin();
    member.setLastlogin(Long.valueOf(DateUtil.getDatelineLong()));
    member.setLogincount(Integer.valueOf(logincount));
    edit(member);
    ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);

    this.memberPluginBundle.onLogin(member, upLogintime);

    return 1;
  }

  public int loginbysys(String username)
  {
    IUserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      throw new RuntimeException("您无权进行此操作，或者您的登录已经超时");
    }

    String sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.uname=?";
    List list = this.daoSupport.queryForList(sql, Member.class, new Object[] { username });
    if ((list == null) || (list.isEmpty())) {
      return 0;
    }

    Member member = (Member)list.get(0);
    ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
    HttpCacheManager.sessionChange();
    return 1;
  }

  public IMemberLvManager getMemberLvManager()
  {
    return this.memberLvManager;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager) {
    this.memberLvManager = memberLvManager;
  }

  public void addMoney(Integer memberid, Double num)
  {
    String sql = "update member set advance=advance+? where member_id=?";
    this.baseDaoSupport.execute(sql, new Object[] { num, memberid });
  }

  public void cutMoney(Integer memberid, Double num)
  {
    Member member = get(memberid);
    if (member.getAdvance().doubleValue() < num.doubleValue()) {
      throw new RuntimeException("预存款不足:需要[" + num + "],剩余[" + member.getAdvance() + "]");
    }
    String sql = "update member set advance=advance-? where member_id=?";
    this.baseDaoSupport.execute(sql, new Object[] { num, memberid });
  }

  public Page list(String order, String name, String uname, int page, int pageSize)
  {
    order = order == null ? " m.member_id desc" : order;
    String sql = "select m.*,lv.name as lv_name from " + getTableName("member") + " m left join " + getTableName("member_lv") + " lv on m.lv_id = lv.lv_id where 1=1";
    if ((name != null) && (!name.equals(""))) {
      sql = sql + " and m.name like '%" + name + "%'";
    }
    if ((uname != null) && (!uname.equals(""))) {
      sql = sql + " and m.uname like '%" + uname + "%'";
    }
    sql = sql + " order by  " + order;
    Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public IMemberPointManger getMemberPointManger()
  {
    return this.memberPointManger;
  }

  public void setMemberPointManger(IMemberPointManger memberPointManger)
  {
    this.memberPointManger = memberPointManger;
  }

  public static void main(String[] args)
  {
    System.out.println(DateUtil.toDate("2011-05-27", "yyyy-MM-dd").getTime());
  }

  public void updateLv(int memberid, int lvid)
  {
    String sql = "update member set lv_id=? where member_id=?";
    this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(lvid), Integer.valueOf(memberid) });
  }
  public MemberPluginBundle getMemberPluginBundle() {
    return this.memberPluginBundle;
  }
  public void setMemberPluginBundle(MemberPluginBundle memberPluginBundle) {
    this.memberPluginBundle = memberPluginBundle;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberManager
 * JD-Core Version:    0.6.1
 */