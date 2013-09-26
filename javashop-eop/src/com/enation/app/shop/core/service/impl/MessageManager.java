package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.model.Message;
import com.enation.app.shop.core.service.IMessageManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import java.util.List;

public class MessageManager extends BaseSupport<Message>
  implements IMessageManager
{
  public Page pageMessage(int pageNo, int pageSize, String folder)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    String sql = "select * from message where folder = ? ";
    if (folder.equals("inbox"))
      sql = sql + " and to_id = ? and del_status != '1'";
    else {
      sql = sql + " and from_id = ? and del_status != '2'";
    }
    sql = sql + " order by date_line desc";
    Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { folder, member.getMember_id() });
    List list = (List)webpage.getResult();

    return webpage;
  }

  public void addMessage(Message message)
  {
    this.baseDaoSupport.insert("message", message);
  }

  public void delinbox(String ids)
  {
    this.baseDaoSupport.execute("delete from message where msg_id in (" + ids + ") and del_status = '2'", new Object[0]);
    this.baseDaoSupport.execute("update message set del_status = '1' where msg_id in (" + ids + ")", new Object[0]);
  }

  public void deloutbox(String ids)
  {
    this.baseDaoSupport.execute("delete from message where msg_id in (" + ids + ") and del_status = '1'", new Object[0]);
    this.baseDaoSupport.execute("update message set del_status = '2' where msg_id in (" + ids + ")", new Object[0]);
  }

  public void setMessage_read(int msg_id)
  {
    this.daoSupport.execute("update " + getTableName("message") + " set unread = '1' where msg_id = ?", new Object[] { Integer.valueOf(msg_id) });
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.MessageManager
 * JD-Core Version:    0.6.1
 */