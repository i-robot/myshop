package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Depot;
import com.enation.app.shop.core.service.IDepotManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DepotManager extends BaseSupport<Depot>
  implements IDepotManager
{
  public void add(Depot room)
  {
    this.baseDaoSupport.insert("depot", room);
  }

  public void update(Depot room)
  {
    this.baseDaoSupport.update("depot", room, "id=" + room.getId());
  }

  public Depot get(int roomid)
  {
    return (Depot)this.baseDaoSupport.queryForObject("select * from depot where id=?", Depot.class, new Object[] { Integer.valueOf(roomid) });
  }

  public List<Depot> list()
  {
    return this.baseDaoSupport.queryForList("select * from depot", Depot.class, new Object[0]);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(int roomid)
  {
    this.baseDaoSupport.execute("delete from goods_depot where depotid = ?", new Object[] { Integer.valueOf(roomid) });
    this.baseDaoSupport.execute("delete from product_store where depotid = ?", new Object[] { Integer.valueOf(roomid) });
    this.baseDaoSupport.execute("delete from depot_user where depotid = ?", new Object[] { Integer.valueOf(roomid) });
    this.baseDaoSupport.execute("delete from depot where id = ?", new Object[] { Integer.valueOf(roomid) });
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.DepotManager
 * JD-Core Version:    0.6.1
 */