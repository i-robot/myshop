package com.enation.app.shop.core.plugin.search;

import com.enation.app.shop.core.model.Cat;
import java.util.List;

public abstract interface IGoodsSearchFilter
{
  public abstract List<SearchSelector> createSelectorList(Cat paramCat, String paramString1, String paramString2);

  public abstract void filter(StringBuffer paramStringBuffer, Cat paramCat, String paramString);

  public abstract String getFilterId();
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.search.IGoodsSearchFilter
 * JD-Core Version:    0.6.1
 */