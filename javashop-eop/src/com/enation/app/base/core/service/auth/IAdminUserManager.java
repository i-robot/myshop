package com.enation.app.base.core.service.auth;

import com.enation.eop.resource.model.AdminUser;
import java.util.List;
import java.util.Map;

public abstract interface IAdminUserManager
{
  public abstract Integer add(AdminUser paramAdminUser);

  public abstract Integer add(int paramInt1, int paramInt2, AdminUser paramAdminUser);

  public abstract int login(String paramString1, String paramString2);

  public abstract AdminUser getCurrentUser();

  public abstract int loginBySys(String paramString1, String paramString2);

  public abstract AdminUser get(Integer paramInteger);

  public abstract void edit(AdminUser paramAdminUser);

  public abstract void delete(Integer paramInteger);

  public abstract int checkLast();

  public abstract List list();

  public abstract List<Map> list(Integer paramInteger1, Integer paramInteger2);

  public abstract List<AdminUser> listByRoleId(int paramInt);

  public abstract void clean();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.auth.IAdminUserManager
 * JD-Core Version:    0.6.1
 */