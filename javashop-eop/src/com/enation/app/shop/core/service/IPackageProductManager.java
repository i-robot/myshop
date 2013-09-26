package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.model.PackageProduct;
import java.util.List;

public abstract interface IPackageProductManager
{
  public abstract void add(PackageProduct paramPackageProduct);

  public abstract List list(int paramInt);

  public abstract void add(Goods paramGoods, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public abstract void edit(Goods paramGoods, int[] paramArrayOfInt1, int[] paramArrayOfInt2);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IPackageProductManager
 * JD-Core Version:    0.6.1
 */