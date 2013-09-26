package com.enation.app.shop.core.service;

import com.enation.app.base.core.model.Member;
import com.enation.framework.database.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IMemberManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int add(Member paramMember);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int register(Member paramMember);

  public abstract void checkEmailSuccess(Member paramMember);

  public abstract int checkname(String paramString);

  public abstract int checkemail(String paramString);

  public abstract Member edit(Member paramMember);

  public abstract Member get(Integer paramInteger);

  public abstract Page list(String paramString, int paramInt1, int paramInt2);

  public abstract Page list(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract void delete(String paramString);

  public abstract Member getMemberByUname(String paramString);

  public abstract Member getMemberByEmail(String paramString);

  public abstract void updatePassword(String paramString);

  public abstract void updatePassword(Integer paramInteger, String paramString);

  public abstract void updateFindCode(Integer paramInteger, String paramString);

  public abstract void addMoney(Integer paramInteger, Double paramDouble);

  public abstract void cutMoney(Integer paramInteger, Double paramDouble);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int login(String paramString1, String paramString2);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int loginWithCookie(String paramString1, String paramString2);

  public abstract void logout();

  public abstract int loginbysys(String paramString);

  public abstract void updateLv(int paramInt1, int paramInt2);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IMemberManager
 * JD-Core Version:    0.6.1
 */