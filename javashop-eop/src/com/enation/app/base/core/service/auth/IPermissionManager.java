package com.enation.app.base.core.service.auth;

import com.enation.app.base.core.model.AuthAction;
import com.enation.app.base.core.model.Role;
import java.util.List;

public abstract interface IPermissionManager
{
  public abstract void giveRolesToUser(int paramInt, int[] paramArrayOfInt);

  public abstract List<Role> getUserRoles(int paramInt);

  public abstract List<AuthAction> getUesrAct(int paramInt, String paramString);

  public abstract List<AuthAction> getUesrAct(int paramInt);

  public abstract boolean checkHaveAuth(int paramInt);

  public abstract void cleanUserRoles(int paramInt);

  public abstract boolean checkHaveRole(int paramInt1, int paramInt2);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.auth.IPermissionManager
 * JD-Core Version:    0.6.1
 */