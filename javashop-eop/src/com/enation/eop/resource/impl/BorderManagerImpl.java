package com.enation.eop.resource.impl;

import com.enation.eop.resource.IBorderManager;
import com.enation.eop.resource.model.Border;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import java.util.List;

public class BorderManagerImpl extends BaseSupport<Border>
  implements IBorderManager
{
  public void clean()
  {
    this.baseDaoSupport.execute("truncate table border", new Object[0]);
  }

  public void add(Border border)
  {
    this.baseDaoSupport.insert("border", border);
  }

  public void delete(Integer id)
  {
    this.baseDaoSupport.execute("delete from border where id=?", new Object[] { id });
  }

  public List<Border> list()
  {
    String sql = "select * from  border";
    List list = this.baseDaoSupport.queryForList(sql, Border.class, new Object[0]);
    return list;
  }

  public void update(Border border)
  {
    this.baseDaoSupport.update("border", border, "id=" + border.getId());
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.BorderManagerImpl
 * JD-Core Version:    0.6.1
 */