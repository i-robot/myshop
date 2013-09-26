package com.enation.eop.resource;

import com.enation.eop.resource.model.IndexItem;
import java.util.List;

public abstract interface IIndexItemManager
{
  public abstract void add(IndexItem paramIndexItem);

  public abstract List<IndexItem> list();

  public abstract void clean();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IIndexItemManager
 * JD-Core Version:    0.6.1
 */