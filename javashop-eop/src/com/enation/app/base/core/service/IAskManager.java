package com.enation.app.base.core.service;

import com.enation.app.base.core.model.Ask;
import com.enation.app.base.core.model.Reply;
import com.enation.framework.database.Page;
import java.util.Date;

public abstract interface IAskManager
{
  public abstract void add(Ask paramAsk);

  public abstract void reply(Reply paramReply);

  public abstract Ask get(Integer paramInteger);

  public abstract Page listMyAsk(String paramString, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2);

  public abstract Page listAllAsk(String paramString, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2);

  public abstract void delete(Integer[] paramArrayOfInteger);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.IAskManager
 * JD-Core Version:    0.6.1
 */