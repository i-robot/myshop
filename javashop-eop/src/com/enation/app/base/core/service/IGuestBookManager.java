package com.enation.app.base.core.service;

import com.enation.app.base.core.model.GuestBook;
import com.enation.framework.database.Page;

public abstract interface IGuestBookManager
{
  public abstract void add(GuestBook paramGuestBook);

  public abstract void reply(GuestBook paramGuestBook);

  public abstract Page list(String paramString, int paramInt1, int paramInt2);

  public abstract GuestBook get(int paramInt);

  public abstract void edit(int paramInt, String paramString);

  public abstract void delete(Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.IGuestBookManager
 * JD-Core Version:    0.6.1
 */