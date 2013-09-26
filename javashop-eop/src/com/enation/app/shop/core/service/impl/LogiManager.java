package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Logi;
import com.enation.app.shop.core.service.ILogiManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import java.util.List;

public class LogiManager extends BaseSupport<Logi>
  implements ILogiManager
{
  public void delete(String id)
  {
    if ((id == null) || (id.equals("")))
      return;
    String sql = "delete from logi_company where id in (" + id + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public Logi getLogiById(Integer id)
  {
    String sql = "select * from logi_company where id=?";
    Logi a = (Logi)this.baseDaoSupport.queryForObject(sql, Logi.class, new Object[] { id });
    return a;
  }

  public Page pageLogi(String order, Integer page, Integer pageSize)
  {
    order = order == null ? " id desc" : order;
    String sql = "select * from logi_company";
    sql = sql + " order by  " + order;
    Page webpage = this.baseDaoSupport.queryForPage(sql, page.intValue(), pageSize.intValue(), new Object[0]);
    return webpage;
  }

  public void saveAdd(Logi logi)
  {
    this.baseDaoSupport.insert("logi_company", logi);
  }

  public void saveEdit(Logi logi)
  {
    this.baseDaoSupport.update("logi_company", logi, "id=" + logi.getId());
  }

  public List list() {
    String sql = "select * from logi_company";
    return this.baseDaoSupport.queryForList(sql, new Object[0]);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.LogiManager
 * JD-Core Version:    0.6.1
 */