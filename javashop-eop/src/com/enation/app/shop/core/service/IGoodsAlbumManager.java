package com.enation.app.shop.core.service;

import java.io.File;

public abstract interface IGoodsAlbumManager
{
  public abstract String[] upload(File paramFile, String paramString);

  public abstract void createThumb(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract void delete(String paramString);

  public abstract void delete(Integer[] paramArrayOfInteger);

  public abstract void recreate(int paramInt1, int paramInt2);

  public abstract int getTotal();
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IGoodsAlbumManager
 * JD-Core Version:    0.6.1
 */