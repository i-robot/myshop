package com.enation.eop.sdk.user;

import com.enation.app.base.core.model.Member;

public abstract interface IUserService
{
  public static final String CURRENT_MEMBER_KEY = "curr_member";

  public abstract boolean isUserLoggedIn();

  public abstract Integer getCurrentUserId();

  public abstract Integer getCurrentSiteId();

  public abstract Integer getCurrentManagerId();

  public abstract Member getCurrentMember();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.user.IUserService
 * JD-Core Version:    0.6.1
 */