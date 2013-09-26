package com.enation.app.shop.core.plugin.member;

import com.enation.app.base.core.model.Member;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IMemberRegisterEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void onRegister(Member paramMember);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.member.IMemberRegisterEvent
 * JD-Core Version:    0.6.1
 */