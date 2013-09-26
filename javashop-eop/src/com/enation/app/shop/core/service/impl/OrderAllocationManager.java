package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
import com.enation.app.shop.core.service.IOrderAllocationManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.impl.IRowMapperColumnFilter;
import com.enation.framework.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class OrderAllocationManager extends BaseSupport
  implements IOrderAllocationManager
{
  private OrderPluginBundle orderPluginBundle;

  public OrderPluginBundle getOrderPluginBundle()
  {
    return this.orderPluginBundle;
  }

  public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
    this.orderPluginBundle = orderPluginBundle;
  }

  public List listAllocation(int orderid)
  {
    String sql = "select oi.sn sn ,oi.name  gname,oi.price price,a.iscmpl iscmpl,a.num num ,d.name a_name ,oi.addon addon,oi.cat_id cat_id,oi.image image";
    sql = sql + " from    " + getTableName("allocation_item") + " a ";
    sql = sql + " LEFT JOIN " + getTableName("order_items") + " oi on a.itemid= oi.item_id";
    sql = sql + " LEFT JOIN " + getTableName("depot") + " d on a.depotid= d.id ";
    sql = sql + " where a.orderid=?";

    IRowMapperColumnFilter columnFilter = new IRowMapperColumnFilter()
    {
      public void filter(Map colValues, ResultSet rs) throws SQLException {
        int catid = rs.getInt("cat_id");
        OrderAllocationManager.this.orderPluginBundle.filterAlloItem(catid, colValues, rs);
      }
    };
    return this.daoSupport.queryForList(sql, columnFilter, new Object[] { Integer.valueOf(orderid) });
  }
  @Transactional(propagation=Propagation.REQUIRED)
  public void clean(Integer[] ids) {
    if (ids == null)
      return;
    String id_str = StringUtil.arrayToString(ids, ",");
    String sql = "delete from " + getTableName("allocation_item") + " where orderid in (" + id_str + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.OrderAllocationManager
 * JD-Core Version:    0.6.1
 */