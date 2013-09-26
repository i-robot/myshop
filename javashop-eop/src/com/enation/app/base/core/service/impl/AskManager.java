package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.Ask;
import com.enation.app.base.core.model.Reply;
import com.enation.app.base.core.service.IAskManager;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopUser;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AskManager extends BaseSupport<Ask>
  implements IAskManager
{
  private ISiteManager siteManager;
  private IUserManager userManager;

  public void add(Ask ask)
  {
    HttpServletRequest request = EopContext.getHttpRequest();
    EopSite site = EopContext.getContext().getCurrentSite();
    EopUser user = this.userManager.get(site.getUserid());
    ask.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
    ask.setUserid(site.getUserid());
    ask.setSiteid(site.getId());
    ask.setIsreply(Integer.valueOf(0));
    ask.setSitename(site.getSitename());
    ask.setDomain(request.getServerName());
    ask.setUsername(user.getUsername());

    this.daoSupport.insert("eop_ask", ask);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer[] id)
  {
    if ((id == null) || (id.length == 0)) return;

    String idstr = StringUtil.arrayToString(id, ",");
    String sql = "delete from eop_reply where askid in(" + idstr + ")";
    this.daoSupport.execute(sql, new Object[0]);

    sql = "delete from eop_ask where askid in(" + idstr + ")";
    this.daoSupport.execute(sql, new Object[0]);
  }

  public void edit(Ask ask)
  {
  }

  public Page list()
  {
    return null;
  }

  public ISiteManager getSiteManager() {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }

  public void reply(Reply reply)
  {
    if (reply.getUsername().equals("客服"))
      this.daoSupport.execute("update eop_ask set isreply=1 where askid=?", new Object[] { reply.getAskid() });
    else {
      this.daoSupport.execute("update eop_ask set isreply=0 where askid=?", new Object[] { reply.getAskid() });
    }
    reply.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
    this.daoSupport.insert("eop_reply", reply);
  }

  public Page listAllAsk(String keyword, Date startTime, Date endTime, int pageNo, int pageSize)
  {
    endTime = endTime == null ? new Date() : endTime;

    StringBuffer sql = new StringBuffer();
    sql.append("select * from eop_ask where ");

    sql.append("  dateline<=");
    sql.append(endTime.getTime() / 1000L);

    if (startTime != null) {
      sql.append(" and dateline>=");
      sql.append(startTime.getTime() / 1000L);
    }

    if (!StringUtil.isEmpty(keyword)) {
      sql.append(" and  ");
      sql.append("(");

      sql.append("title like '%");
      sql.append(keyword);
      sql.append("%'");

      sql.append(" or content like '%");
      sql.append(keyword);
      sql.append("%'");

      sql.append(" or username like '%");
      sql.append(keyword);
      sql.append("%'");

      sql.append(" or sitename like '%");
      sql.append(keyword);
      sql.append("%'");

      sql.append(" or domain  like '%");
      sql.append(keyword);
      sql.append("%'");

      sql.append(")");
    }

    sql.append(" order by isreply asc,dateline desc");

    return this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
  }

  public Page listMyAsk(String keyword, Date startTime, Date endTime, int pageNo, int pageSize)
  {
    StringBuffer sql = new StringBuffer();
    EopSite site = EopContext.getContext().getCurrentSite();
    sql.append("select * from eop_ask where userid =");
    sql.append(site.getUserid());
    sql.append(" and siteid=");
    sql.append(site.getId());

    if (!StringUtil.isEmpty(keyword)) {
      sql.append(" and  ");
      sql.append("(");

      sql.append("title like '%");
      sql.append(keyword);
      sql.append("%'");

      sql.append(" or content like '%");
      sql.append(keyword);
      sql.append("%'");

      sql.append(")");
    }

    if (startTime != null) {
      sql.append(" and dateline>=");
      sql.append(startTime.getTime() / 1000L);
    }

    if (endTime != null) {
      sql.append(" and dateline<=");
      sql.append(endTime.getTime() / 1000L);
    }

    sql.append(" order by isreply asc,dateline desc");

    return this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
  }

  public Ask get(Integer askid)
  {
    String sql = "select * from eop_ask where askid=?";
    Ask ask = (Ask)this.daoSupport.queryForObject(sql, Ask.class, new Object[] { askid });

    sql = "select * from eop_reply where askid=? order by dateline asc";
    List replylist = this.daoSupport.queryForList(sql, Reply.class, new Object[] { askid });
    ask.setReplyList(replylist);

    return ask;
  }

  public IUserManager getUserManager()
  {
    return this.userManager;
  }

  public void setUserManager(IUserManager userManager)
  {
    this.userManager = userManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.AskManager
 * JD-Core Version:    0.6.1
 */