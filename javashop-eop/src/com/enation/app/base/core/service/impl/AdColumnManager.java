package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.AdColumn;
import com.enation.app.base.core.service.IAdColumnManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import java.util.List;

public class AdColumnManager extends BaseSupport<AdColumn>
  implements IAdColumnManager
{
  public void addAdvc(AdColumn adColumn)
  {
    this.baseDaoSupport.insert("adcolumn", adColumn);
  }

  public void delAdcs(String ids)
  {
    if ((ids == null) || (ids.equals("")))
      return;
    String sql = "delete from adcolumn where acid in (" + ids + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public AdColumn getADcolumnDetail(Long acid)
  {
    AdColumn adColumn = (AdColumn)this.baseDaoSupport.queryForObject("select * from adcolumn where acid = ?", AdColumn.class, new Object[] { acid });
    return adColumn;
  }

  public List listAllAdvPos()
  {
    List list = this.baseDaoSupport.queryForList("select * from adcolumn", AdColumn.class, new Object[0]);
    return list;
  }

  public Page pageAdvPos(int page, int pageSize)
  {
    String sql = "select * from adcolumn order by acid";
    Page rpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return rpage;
  }

  public void updateAdvc(AdColumn adColumn)
  {
    this.baseDaoSupport.update("adcolumn", adColumn, "acid = " + adColumn.getAcid());
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.AdColumnManager
 * JD-Core Version:    0.6.1
 */