package com.enation.app.base.core.plugin.user;

import com.enation.eop.resource.model.AdminUser;

public abstract interface IAdminUserInputDisplayEvent
{
  public abstract String getInputHtml(AdminUser paramAdminUser);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.plugin.user.IAdminUserInputDisplayEvent
 * JD-Core Version:    0.6.1
 */