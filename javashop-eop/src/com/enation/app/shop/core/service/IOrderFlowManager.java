package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Allocation;
import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.OrderItem;
import com.enation.app.shop.core.model.PaymentLog;
import com.enation.app.shop.core.model.RefundLog;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IOrderFlowManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void pay(PaymentLog paramPaymentLog, boolean paramBoolean);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void refund(RefundLog paramRefundLog);

  public abstract String getAllocationHtml(int paramInt);

  public abstract String getAllocationViewHtml(int paramInt);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void allocation(Allocation paramAllocation);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void shipping(Delivery paramDelivery, List<DeliveryItem> paramList);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void returned(Delivery paramDelivery, List<DeliveryItem> paramList1, List<DeliveryItem> paramList2);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void change(Delivery paramDelivery, List<DeliveryItem> paramList1, List<DeliveryItem> paramList2);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void complete(Integer paramInteger);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void cancel(Integer paramInteger);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void confirmOrder(Integer paramInteger);

  public abstract List<OrderItem> listNotShipGoodsItem(Integer paramInteger);

  public abstract List<OrderItem> listShipGoodsItem(Integer paramInteger);

  public abstract void updateAllocation(int paramInt1, int paramInt2);

  public abstract Order payConfirm(int paramInt);

  public abstract void rogConfirm(int paramInt1, Integer paramInteger, String paramString1, String paramString2, int paramInt2);

  public abstract void cancel(String paramString1, String paramString2);

  public abstract void restore(String paramString);

  public abstract void addCodPaymentLog(Order paramOrder);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IOrderFlowManager
 * JD-Core Version:    0.6.1
 */