package com.enation.eop.sdk.user;

import com.enation.eop.sdk.user.impl.UserServiceImpl;

public final class UserServiceFactory
{
  public static int isTest = 0;
  private static IUserService userService;

  public static void set(IUserService _userService)
  {
    userService = _userService;
  }

  public static IUserService getUserService() {
    if (userService != null) {
      return userService;
    }
    return new UserServiceImpl();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.user.UserServiceFactory
 * JD-Core Version:    0.6.1
 */