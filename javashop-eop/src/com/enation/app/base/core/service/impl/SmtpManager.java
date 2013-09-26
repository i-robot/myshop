package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.Smtp;
import com.enation.app.base.core.service.ISmtpManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.StringUtil;
import java.util.List;

public class SmtpManager extends BaseSupport<Smtp>
  implements ISmtpManager
{
  public void add(Smtp smtp)
  {
    this.baseDaoSupport.insert("smtp", smtp);
  }

  public void edit(Smtp smtp)
  {
    this.baseDaoSupport.update("smtp", smtp, "id=" + smtp.getId());
  }

  public void delete(Integer[] idAr)
  {
    if ((idAr == null) || (idAr.length == 0)) return;
    String idstr = StringUtil.arrayToString(idAr, ",");

    this.baseDaoSupport.execute("delete from smtp where id in(" + idstr + ")", new Object[0]);
  }

  public List<Smtp> list()
  {
    return this.baseDaoSupport.queryForList("select * from smtp", Smtp.class, new Object[0]);
  }

  public void sendOneMail(Smtp currSmtp)
  {
    this.baseDaoSupport.update("smtp", currSmtp, "id=" + currSmtp.getId());
  }

  public Smtp get(int id)
  {
    return (Smtp)this.baseDaoSupport.queryForObject("select * from smtp where id=?", Smtp.class, new Object[] { Integer.valueOf(id) });
  }

  public Smtp getCurrentSmtp()
  {
    return null;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.SmtpManager
 * JD-Core Version:    0.6.1
 */