package com.enation.framework.database;

import java.io.Serializable;
import java.util.ArrayList;

public class Page
  implements Serializable
{
  private static int DEFAULT_PAGE_SIZE = 20;

  private int pageSize = DEFAULT_PAGE_SIZE;
  private long start;
  private Object data;
  private long totalCount;

  public Page()
  {
    this(0L, 0L, DEFAULT_PAGE_SIZE, new ArrayList());
  }

  public Page(long start, long totalSize, int pageSize, Object data)
  {
    setParam(start, totalSize, pageSize, data);
  }

  public void setParam(long start, long totalSize, int pageSize, Object data) {
    this.pageSize = pageSize;
    this.start = start;
    this.totalCount = totalSize;
    this.data = data;
  }

  public long getTotalCount()
  {
    return this.totalCount;
  }

  public long getTotalPageCount()
  {
    if (this.totalCount % this.pageSize == 0L) {
      return this.totalCount / this.pageSize;
    }
    return this.totalCount / this.pageSize + 1L;
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public Object getResult()
  {
    return this.data;
  }

  public long getCurrentPageNo()
  {
    return this.start / this.pageSize + 1L;
  }

  public boolean hasNextPage()
  {
    return getCurrentPageNo() < getTotalPageCount() - 1L;
  }

  public boolean hasPreviousPage()
  {
    return getCurrentPageNo() > 1L;
  }

  protected static int getStartOfPage(int pageNo)
  {
    return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
  }

  public static int getStartOfPage(int pageNo, int pageSize)
  {
    return (pageNo - 1) * pageSize;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.Page
 * JD-Core Version:    0.6.1
 */