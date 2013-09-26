package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import com.enation.app.shop.core.model.PaymentLog;
import com.enation.app.shop.core.model.RefundLog;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IOrderReportManager
{
  public abstract Page listPayment(int paramInt1, int paramInt2, String paramString);

  public abstract Page listRefund(int paramInt1, int paramInt2, String paramString);

  public abstract Page listShipping(int paramInt1, int paramInt2, String paramString);

  public abstract Page listReturned(int paramInt1, int paramInt2, String paramString);

  public abstract PaymentLog getPayment(Integer paramInteger);

  public abstract Delivery getDelivery(Integer paramInteger);

  public abstract List<Delivery> getDeliveryList(int paramInt);

  public abstract List<DeliveryItem> listDeliveryItem(Integer paramInteger);

  public abstract List<PaymentLog> listPayLogs(Integer paramInteger);

  public abstract List<RefundLog> listRefundLogs(Integer paramInteger);

  public abstract List listDelivery(Integer paramInteger1, Integer paramInteger2);

  public abstract Page listAllocation(int paramInt1, int paramInt2);

  public abstract Page listAllocation(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IOrderReportManager
 * JD-Core Version:    0.6.1
 */