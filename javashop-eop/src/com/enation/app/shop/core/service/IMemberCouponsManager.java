package com.enation.app.shop.core.service;

import com.enation.app.shop.component.coupon.MemberCoupon;
import com.enation.framework.database.Page;

public abstract interface IMemberCouponsManager
{
  public abstract Page pageMemberCoupons(int paramInt1, int paramInt2);

  public abstract Page pageExchangeCoupons(int paramInt1, int paramInt2);

  public abstract void exchange(int paramInt);

  public abstract Page queryAllCoupons(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public abstract MemberCoupon get(Integer paramInteger);

  public abstract void updateMemberid(int paramInt1, int paramInt2);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IMemberCouponsManager
 * JD-Core Version:    0.6.1
 */