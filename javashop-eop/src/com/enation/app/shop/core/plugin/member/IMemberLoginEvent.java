package com.enation.app.shop.core.plugin.member;

import com.enation.app.base.core.model.Member;

public abstract interface IMemberLoginEvent
{
  public abstract void onLogin(Member paramMember, Long paramLong);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.member.IMemberLoginEvent
 * JD-Core Version:    0.6.1
 */