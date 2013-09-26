package com.enation.app.base.core.service.auth;

import com.enation.app.base.core.model.Role;
import java.util.List;

public abstract interface IRoleManager
{
  public abstract List<Role> list();

  public abstract void add(Role paramRole, int[] paramArrayOfInt);

  public abstract void edit(Role paramRole, int[] paramArrayOfInt);

  public abstract void delete(int paramInt);

  public abstract Role get(int paramInt);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.auth.IRoleManager
 * JD-Core Version:    0.6.1
 */