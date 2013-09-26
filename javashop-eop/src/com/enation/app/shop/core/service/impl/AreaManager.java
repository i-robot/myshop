package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.DlyArea;
import com.enation.app.shop.core.service.IAreaManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import java.util.List;

public class AreaManager extends BaseSupport<DlyArea>
  implements IAreaManager
{
  public void delete(String id)
  {
    if ((id == null) || (id.equals("")))
      return;
    String sql = "delete from dly_area where area_id in (" + id + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public List getAll()
  {
    String sql = "select * from dly_area order by area_id desc";
    List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
    return list;
  }

  public Page pageArea(String order, int page, int pageSize)
  {
    order = order == null ? " area_id desc" : order;
    String sql = "select  * from  dly_area";
    sql = sql + " order by  " + order;
    Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public void saveAdd(String name)
  {
    DlyArea dlyArea = new DlyArea();
    dlyArea.setName(name);
    this.baseDaoSupport.insert("dly_area", dlyArea);
  }

  public void saveEdit(Integer areaId, String name)
  {
    String sql = "update dly_area set name = ? where area_id=?";
    this.baseDaoSupport.execute(sql, new Object[] { name, areaId });
  }

  public DlyArea getDlyAreaById(Integer areaId)
  {
    String sql = "select * from dly_area where area_id=?";
    DlyArea a = (DlyArea)this.baseDaoSupport.queryForObject(sql, DlyArea.class, new Object[] { areaId });
    return a;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.AreaManager
 * JD-Core Version:    0.6.1
 */