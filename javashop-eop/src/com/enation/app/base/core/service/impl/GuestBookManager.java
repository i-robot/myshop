package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.DataLog;
import com.enation.app.base.core.model.GuestBook;
import com.enation.app.base.core.service.IGuestBookManager;
import com.enation.eop.resource.IDataLogManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class GuestBookManager extends BaseSupport<GuestBook>
  implements IGuestBookManager
{
  private IDataLogManager dataLogManager;

  @Transactional(propagation=Propagation.REQUIRED)
  public void add(GuestBook guestbook)
  {
    if (guestbook == null)
      throw new IllegalArgumentException("param guestbook is NULL");
    guestbook.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
    guestbook.setIssubject(Integer.valueOf(1));
    guestbook.setParentid(Integer.valueOf(0));
    this.baseDaoSupport.insert("guestbook", guestbook);
    DataLog datalog = new DataLog();
    datalog.setContent("标题:" + guestbook.getTitle() + "<br>内容：" + guestbook.getContent());

    datalog.setLogtype("留言");
    datalog.setOptype("添加");
    this.dataLogManager.add(datalog);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer[] idArray) {
    if ((idArray == null) || (idArray.length == 0))
      return;
    String ids = StringUtil.arrayToString(idArray, ",");
    this.baseDaoSupport.execute("delete from guestbook where id in(" + ids + ")", new Object[0]);

    this.baseDaoSupport.execute("delete from guestbook where parentid in(" + ids + ")", new Object[0]);
  }

  public Page list(String keyword, int pageNo, int pageSize)
  {
    String sql = "select * from guestbook where issubject=1";
    StringBuffer term = new StringBuffer();

    if (!StringUtil.isEmpty(keyword)) {
      term.append(" and  (");
      term.append(" title like'%" + keyword + "%'");
      term.append(" or content like'%" + keyword + "%'");
      term.append(" or username like'%" + keyword + "%'");
      term.append(")");
    }

    sql = sql + term + " order by dateline desc";
    Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, GuestBook.class, new Object[0]);

    List subjectList = (List)page.getResult();

    sql = "select * from guestbook where issubject=0 and parentid in (select id from " + getTableName("guestbook") + " where issubject =1 " + term + ") order by dateline asc ";

    List<GuestBook> replyList = this.baseDaoSupport.queryForList(sql, GuestBook.class, new Object[0]);

    for (GuestBook reply : replyList) {
      addtoSubject(subjectList, reply);
    }

    return page;
  }

  private void addtoSubject(List<GuestBook> subjectList, GuestBook reply)
  {
    for (GuestBook subject : subjectList) {
      int id = subject.getId().intValue();
      int pid = reply.getParentid().intValue();
      if (id == pid) {
        subject.addReply(reply);
        break;
      }
    }
  }

  public void reply(GuestBook guestbook) {
    if (guestbook == null)
      throw new IllegalArgumentException("param guestbook is NULL");
    guestbook.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
    guestbook.setIssubject(Integer.valueOf(0));
    this.baseDaoSupport.insert("guestbook", guestbook);
  }

  public void edit(int id, String content)
  {
    this.baseDaoSupport.execute("update guestbook set content=? where id=?", new Object[] { content, Integer.valueOf(id) });
  }

  public GuestBook get(int id)
  {
    GuestBook guestbook = (GuestBook)this.baseDaoSupport.queryForObject("select * from guestbook where id=?", GuestBook.class, new Object[] { Integer.valueOf(id) });

    List replyList = this.baseDaoSupport.queryForList("select * from guestbook where parentid=? order by dateline asc", GuestBook.class, new Object[] { Integer.valueOf(id) });

    guestbook.setReplyList(replyList);
    return guestbook;
  }

  public IDataLogManager getDataLogManager() {
    return this.dataLogManager;
  }

  public void setDataLogManager(IDataLogManager dataLogManager) {
    this.dataLogManager = dataLogManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.GuestBookManager
 * JD-Core Version:    0.6.1
 */