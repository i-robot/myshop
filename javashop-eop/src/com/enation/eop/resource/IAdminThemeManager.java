package com.enation.eop.resource;

import com.enation.eop.resource.model.AdminTheme;
import java.util.List;

public abstract interface IAdminThemeManager
{
  public abstract Integer add(AdminTheme paramAdminTheme, boolean paramBoolean);

  public abstract List<AdminTheme> list();

  public abstract AdminTheme get(Integer paramInteger);

  public abstract void clean();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IAdminThemeManager
 * JD-Core Version:    0.6.1
 */