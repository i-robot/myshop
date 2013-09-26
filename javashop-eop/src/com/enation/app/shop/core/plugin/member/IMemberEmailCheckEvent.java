package com.enation.app.shop.core.plugin.member;

import com.enation.app.base.core.model.Member;

public abstract interface IMemberEmailCheckEvent
{
  public abstract void onEmailCheck(Member paramMember);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.member.IMemberEmailCheckEvent
 * JD-Core Version:    0.6.1
 */