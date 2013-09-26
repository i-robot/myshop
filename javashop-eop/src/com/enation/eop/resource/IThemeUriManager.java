package com.enation.eop.resource;

import com.enation.eop.resource.model.ThemeUri;
import java.util.List;

public abstract interface IThemeUriManager
{
  public abstract ThemeUri get(Integer paramInteger);

  public abstract void add(ThemeUri paramThemeUri);

  public abstract List<ThemeUri> list();

  public abstract ThemeUri getPath(String paramString);

  public abstract void edit(List<ThemeUri> paramList);

  public abstract void edit(ThemeUri paramThemeUri);

  public abstract void delete(int paramInt);

  public abstract void clean();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IThemeUriManager
 * JD-Core Version:    0.6.1
 */