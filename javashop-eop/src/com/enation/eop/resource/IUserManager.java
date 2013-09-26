package com.enation.eop.resource;

import com.enation.eop.resource.model.EopUser;
import com.enation.framework.database.Page;

public abstract interface IUserManager
{
  public static final String USER_SESSION_KEY = "eop_user_key";

  public abstract EopUser get(Integer paramInteger);

  public abstract EopUser get(String paramString);

  public abstract Integer createUser(EopUser paramEopUser);

  public abstract void edit(EopUser paramEopUser);

  public abstract int login(String paramString1, String paramString2);

  public abstract void logout();

  public abstract void changeDefaultSite(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

  public abstract Page list(String paramString, int paramInt1, int paramInt2);

  public abstract void delete(Integer paramInteger);

  public abstract int checkIsLogin();

  public abstract EopUser getCurrentUser();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IUserManager
 * JD-Core Version:    0.6.1
 */