package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.OrderItem;
import com.enation.framework.database.Page;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface IOrderManager
{
  public abstract void savePrice(double paramDouble, int paramInt);

  public abstract double saveShipmoney(double paramDouble, int paramInt);

  public abstract boolean saveAddrDetail(String paramString, int paramInt);

  public abstract boolean saveShipInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt);

  public abstract void refuseReturn(String paramString);

  public abstract Order add(Order paramOrder, String paramString);

  public abstract void edit(Order paramOrder);

  public abstract Page list(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3);

  public abstract Page list(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString);

  public abstract Page listConfirmPay(int paramInt1, int paramInt2, String paramString);

  public abstract Order get(Integer paramInteger);

  public abstract Order getNext(String paramString1, Integer paramInteger1, Integer paramInteger2, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract Order get(String paramString);

  public abstract List<OrderItem> listGoodsItems(Integer paramInteger);

  public abstract List listLogs(Integer paramInteger);

  public abstract void delete(Integer[] paramArrayOfInteger);

  public abstract void clean(Integer[] paramArrayOfInteger);

  public abstract void revert(Integer[] paramArrayOfInteger);

  public abstract List listOrderByMemberId(int paramInt);

  public abstract Map mapOrderByMemberId(int paramInt);

  public abstract List<Map> listAdjItem(Integer paramInteger);

  public abstract Map censusState();

  public abstract String export(Date paramDate1, Date paramDate2);

  public abstract OrderItem getItem(int paramInt);

  public abstract int getMemberOrderNum(int paramInt1, int paramInt2);

  public abstract List<Map> getItemsByOrderid(Integer paramInteger);

  public abstract void updateOrderPrice(double paramDouble, int paramInt);

  public abstract String queryLogiNameById(Integer paramInteger);

  public abstract Page searchForGuest(int paramInt1, int paramInt2, String paramString1, String paramString2);

  public abstract Page listByStatus(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract List<Order> listByStatus(int paramInt1, int paramInt2);

  public abstract int getMemberOrderNum(int paramInt);

  public abstract Page search(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt4);

  public abstract Page search(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt4, Integer paramInteger);

  public abstract Page listbyshipid(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString);

  public abstract boolean delItem(Integer paramInteger1, Integer paramInteger2);

  public abstract void updatePayMethod(int paramInt1, int paramInt2, String paramString1, String paramString2);

  public abstract boolean checkProInOrder(int paramInt);

  public abstract boolean checkGoodsInOrder(int paramInt);

  public abstract String createSn();
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IOrderManager
 * JD-Core Version:    0.6.1
 */