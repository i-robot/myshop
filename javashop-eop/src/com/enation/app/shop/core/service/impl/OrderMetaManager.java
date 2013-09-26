package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.OrderMeta;
import com.enation.app.shop.core.service.IOrderMetaManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import java.util.List;

public class OrderMetaManager extends BaseSupport<OrderMeta>
  implements IOrderMetaManager
{
  public void add(OrderMeta orderMeta)
  {
    this.baseDaoSupport.insert("order_meta", orderMeta);
  }

  public List<OrderMeta> list(int orderid)
  {
    return this.baseDaoSupport.queryForList("select * from order_meta where orderid=?", OrderMeta.class, new Object[] { Integer.valueOf(orderid) });
  }

  public OrderMeta get(int orderid, String meta_key)
  {
    return (OrderMeta)this.baseDaoSupport.queryForObject("select * from order_meta where orderid=? and meta_key=?", OrderMeta.class, new Object[] { Integer.valueOf(orderid), meta_key });
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.OrderMetaManager
 * JD-Core Version:    0.6.1
 */