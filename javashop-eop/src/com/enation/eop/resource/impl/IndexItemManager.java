package com.enation.eop.resource.impl;

import com.enation.eop.resource.IIndexItemManager;
import com.enation.eop.resource.model.IndexItem;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import java.util.List;

public class IndexItemManager extends BaseSupport<IndexItem>
  implements IIndexItemManager
{
  public void add(IndexItem item)
  {
    this.baseDaoSupport.insert("index_item", item);
  }

  public List<IndexItem> list()
  {
    String sql = "select * from index_item order by sort";
    return this.baseDaoSupport.queryForList(sql, IndexItem.class, new Object[0]);
  }

  public void clean() {
    this.baseDaoSupport.execute("truncate table index_item", new Object[0]);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.IndexItemManager
 * JD-Core Version:    0.6.1
 */