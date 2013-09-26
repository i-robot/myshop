package com.enation.eop.resource.impl;

import com.enation.eop.processor.core.EopException;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;

public final class UserUtil
{
  private static IUserService userService;

  public static void validUser(Integer userid)
  {
    userService = UserServiceFactory.getUserService();

    if (!userid.equals(userService.getCurrentUserId()))
      throw new EopException("非法操作");
  }

  public IUserService getUserService()
  {
    return userService;
  }

  public void setUserService(IUserService userService) {
    userService = userService;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.UserUtil
 * JD-Core Version:    0.6.1
 */