package com.enation.eop.resource;

import com.enation.eop.resource.model.Theme;
import java.util.List;

public abstract interface IThemeManager
{
  public abstract void addBlank(Theme paramTheme);

  public abstract Integer add(Theme paramTheme, boolean paramBoolean);

  public abstract List<Theme> list();

  public abstract List<Theme> list(int paramInt);

  public abstract Theme getTheme(Integer paramInteger);

  public abstract void clean();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IThemeManager
 * JD-Core Version:    0.6.1
 */