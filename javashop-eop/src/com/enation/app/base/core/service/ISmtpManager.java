package com.enation.app.base.core.service;

import com.enation.app.base.core.model.Smtp;
import java.util.List;

public abstract interface ISmtpManager
{
  public abstract void add(Smtp paramSmtp);

  public abstract void edit(Smtp paramSmtp);

  public abstract void delete(Integer[] paramArrayOfInteger);

  public abstract List<Smtp> list();

  public abstract void sendOneMail(Smtp paramSmtp);

  public abstract Smtp get(int paramInt);

  public abstract Smtp getCurrentSmtp();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.ISmtpManager
 * JD-Core Version:    0.6.1
 */