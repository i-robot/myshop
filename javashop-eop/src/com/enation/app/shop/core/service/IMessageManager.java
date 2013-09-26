package com.enation.app.shop.core.service;

import com.enation.app.base.core.model.Message;
import com.enation.framework.database.Page;

public abstract interface IMessageManager
{
  public abstract Page pageMessage(int paramInt1, int paramInt2, String paramString);

  public abstract void addMessage(Message paramMessage);

  public abstract void delinbox(String paramString);

  public abstract void deloutbox(String paramString);

  public abstract void setMessage_read(int paramInt);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IMessageManager
 * JD-Core Version:    0.6.1
 */