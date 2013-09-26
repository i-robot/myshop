package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.service.IAccessRecorder;
import com.enation.eop.resource.model.Access;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.ThemeUri;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.RequestUtil;
import com.enation.framework.util.ip.IPSeeker;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class AccessRecorder extends BaseSupport
  implements IAccessRecorder
{
  public int record(ThemeUri themeUri)
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();

    Access access = new Access();
    access.setAccess_time((int)(System.currentTimeMillis() / 1000L));
    access.setIp(request.getRemoteAddr());

    access.setPage(themeUri.getPagename());
    access.setUrl(RequestUtil.getRequestUrl(request));
    access.setPoint(themeUri.getPoint());
    access.setArea(new IPSeeker().getCountry(access.getIp()));

    Member member = UserServiceFactory.getUserService().getCurrentMember();
    if (member != null) {
      access.setMembername(member.getUname());
    }
    Access last_access = (Access)ThreadContextHolder.getSessionContext().getAttribute("user_access");

    if (last_access != null) {
      int stay_time = access.getAccess_time() - last_access.getAccess_time();

      last_access.setStay_time(stay_time);
      int last = (int)(System.currentTimeMillis() / 1000L - 3600L);
      String sql = "select count(0)  from access where ip=? and url=? and access_time>=?";

      int count = this.baseDaoSupport.queryForInt(sql, new Object[] { last_access.getIp(), last_access.getUrl(), Integer.valueOf(last) });

      if (count == 0) {
        EopSite site = EopContext.getContext().getCurrentSite();
        int point = site.getPoint();
        if ((point == -1) || (site.getIsimported() == 1)) {
          this.baseDaoSupport.insert("access", last_access);
          return 1;
        }
        if (point > access.getPoint())
        {
          this.daoSupport.execute("update eop_site set point=point-? where id=?", new Object[] { Integer.valueOf(last_access.getPoint()), site.getId() });

          this.baseDaoSupport.insert("access", last_access);
          site.setPoint(site.getPoint() - last_access.getPoint());
        } else {
          return 0;
        }
      }

    }

    ThreadContextHolder.getSessionContext().setAttribute("user_access", access);

    return 1;
  }

  public static void main(String[] args)
  {
    int todaystart = (int)(DateUtil.toDate(DateUtil.toString(new Date(), "yyyy-MM-dd 00:00"), "yyyy-MM-dd mm:ss").getTime() / 1000L);

    System.out.println((int)(DateUtil.toDate("2010-11-01 00:00", "yyyy-MM-dd mm:ss").getTime() / 1000L));

    System.out.println(todaystart);
    System.out.println(System.currentTimeMillis() / 1000L);
  }

  public Page list(String starttime, String endtime, int pageNo, int pageSize)
  {
    int now = (int)(System.currentTimeMillis() / 1000L);

    int stime = now - 2592000;

    if (starttime != null) {
      stime = (int)(DateUtil.toDate(starttime, "yyyy-MM-dd").getTime() / 1000L);
    }

    if (endtime != null) {
      now = (int)(DateUtil.toDate(endtime, "yyyy-MM-dd").getTime() / 1000L);
    }

    String sql = "select ip,max(access_time) access_time,max(membername) mname,floor(access_time/86400) daytime,count(0) count,sum(stay_time) sum_stay_time,max(access_time) maxtime,min(access_time) mintime,sum(point) point from access where access_time>=? and access_time<=? group by ip,floor(access_time/86400) order by access_time desc";
    sql = this.baseDaoSupport.buildPageSql(sql, pageNo, pageSize);
    List list = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(stime), Integer.valueOf(now) });
    sql = "select count(0) from (select access_time from access where access_time>=? and access_time<=? group by ip, floor(access_time/86400)) tb";

    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(stime), Integer.valueOf(now) });
    Page page = new Page(0L, count, pageSize, list);

    return page;
  }

  public List detaillist(String ip, String daytime)
  {
    String sql = "select * from access where ip=? and floor(access_time/86400)=? order by access_time asc ";
    return this.baseDaoSupport.queryForList(sql, new Object[] { ip, daytime });
  }

  public void export()
  {
    String sql = "select * from eop_site ";
    List<Map> list = this.daoSupport.queryForList(sql, new Object[0]);

    for (Map map : list) {
      AccessExporter accessExporter = (AccessExporter)SpringContextHolder.getBean("accessExporter");

      accessExporter.setContext(Integer.valueOf(map.get("userid").toString()), Integer.valueOf(map.get("id").toString()));

      Thread thread = new Thread(accessExporter);
      thread.start();
    }
  }

  public Map<String, Long> census()
  {
    int todaystart = (int)(DateUtil.toDate(DateUtil.toString(new Date(), "yyyy-MM-dd 00:00"), "yyyy-MM-dd mm:ss").getTime() / 1000L);

    int todayend = (int)(System.currentTimeMillis() / 1000L);

    String sql = "select count(0) from access where access_time>=?  and access_time<=?";
    long todayaccess = this.baseDaoSupport.queryForLong(sql, new Object[] { Integer.valueOf(todaystart), Integer.valueOf(todayend) });

    sql = "select sum(point) from access where access_time>=?  and access_time<=?";
    long todaypoint = this.baseDaoSupport.queryForLong(sql, new Object[] { Integer.valueOf(todaystart), Integer.valueOf(todayend) });

    String[] currentMonth = DateUtil.getCurrentMonth();
    int monthstart = (int)(DateUtil.toDate(currentMonth[0], "yyyy-MM-dd").getTime() / 1000L);

    int monthend = (int)(DateUtil.toDate(currentMonth[1], "yyyy-MM-dd").getTime() / 1000L);

    sql = "select count(0) from access where access_time>=? and access_time<=?";
    long monthaccess = this.baseDaoSupport.queryForLong(sql, new Object[] { Integer.valueOf(monthstart), Integer.valueOf(monthend) });

    sql = "select sum(point) from access where access_time>=? and access_time<=?";
    long monthpoint = this.baseDaoSupport.queryForLong(sql, new Object[] { Integer.valueOf(monthstart), Integer.valueOf(monthend) });

    sql = "select sumpoint,sumaccess from eop_site where id=?";
    List list = this.daoSupport.queryForList(sql, new Object[] { EopContext.getContext().getCurrentSite().getId() });

    if ((list.isEmpty()) || (list == null) || (list.size() == 0)) {
      throw new RuntimeException("站点[" + EopContext.getContext().getCurrentSite().getId() + "]不存在");
    }

    Map siteData = (Map)list.get(0);
    long sumaccess = Long.valueOf("" + siteData.get("sumaccess")).longValue();
    long sumpoint = Long.valueOf("" + siteData.get("sumpoint")).longValue();

    sumaccess += monthaccess;
    sumpoint += monthpoint;

    Map sData = new HashMap();
    sData.put("todayaccess", Long.valueOf(todayaccess));
    sData.put("todaypoint", Long.valueOf(todaypoint));
    sData.put("monthaccess", Long.valueOf(monthaccess));
    sData.put("monthpoint", Long.valueOf(monthpoint));
    sData.put("sumaccess", Long.valueOf(sumaccess));
    sData.put("sumpoint", Long.valueOf(sumpoint));
    return sData;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.AccessRecorder
 * JD-Core Version:    0.6.1
 */