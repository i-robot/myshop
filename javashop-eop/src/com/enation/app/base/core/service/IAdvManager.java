package com.enation.app.base.core.service;

import com.enation.app.base.core.model.Adv;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IAdvManager
{
  public abstract void updateAdv(Adv paramAdv);

  public abstract Adv getAdvDetail(Long paramLong);

  public abstract void addAdv(Adv paramAdv);

  public abstract void delAdvs(String paramString);

  public abstract Page pageAdv(String paramString, int paramInt1, int paramInt2);

  public abstract List listAdv(Long paramLong);

  public abstract Page search(Long paramLong, String paramString1, int paramInt1, int paramInt2, String paramString2);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.IAdvManager
 * JD-Core Version:    0.6.1
 */