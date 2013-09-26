package com.enation.app.shop.core.plugin.member;

import com.enation.app.base.core.model.Member;

public abstract interface IMemberTabShowEvent
{
  public abstract String getTabName(Member paramMember);

  public abstract int getOrder();

  public abstract boolean canBeExecute(Member paramMember);

  public abstract String onShowMemberDetailHtml(Member paramMember);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.member.IMemberTabShowEvent
 * JD-Core Version:    0.6.1
 */