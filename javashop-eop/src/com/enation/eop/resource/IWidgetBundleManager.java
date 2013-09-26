package com.enation.eop.resource;

import com.enation.eop.resource.model.WidgetBundle;
import java.util.List;

public abstract interface IWidgetBundleManager
{
  public abstract void add(WidgetBundle paramWidgetBundle);

  public abstract List<WidgetBundle> getWidgetbundleList();

  public abstract WidgetBundle getWidgetBundle(String paramString);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IWidgetBundleManager
 * JD-Core Version:    0.6.1
 */