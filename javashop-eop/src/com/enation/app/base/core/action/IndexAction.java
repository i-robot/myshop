package com.enation.app.base.core.action;

import com.enation.eop.resource.IIndexItemManager;
import com.enation.eop.resource.model.IndexItem;
import com.enation.framework.action.WWAction;
import java.util.List;

public class IndexAction extends WWAction
{
  private IIndexItemManager indexItemManager;
  private List<IndexItem> itemList;

  public String execute()
  {
    this.itemList = this.indexItemManager.list();
    return "index";
  }

  public IIndexItemManager getIndexItemManager() {
    return this.indexItemManager;
  }

  public void setIndexItemManager(IIndexItemManager indexItemManager) {
    this.indexItemManager = indexItemManager;
  }

  public List<IndexItem> getItemList() {
    return this.itemList;
  }

  public void setItemList(List<IndexItem> itemList) {
    this.itemList = itemList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.IndexAction
 * JD-Core Version:    0.6.1
 */