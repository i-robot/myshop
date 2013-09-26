package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import com.enation.app.shop.core.model.PaymentLog;
import com.enation.app.shop.core.service.IOrderFlowManager;
import com.enation.app.shop.core.service.IOrderReportManager;
import com.enation.app.shop.core.service.IPaymentManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class OrderReportAction extends WWAction
{
  private IOrderReportManager orderReportManager;
  private String order;
  private int id;
  private PaymentLog payment;
  private Delivery delivery;
  private List<DeliveryItem> listDeliveryItem;
  private IPaymentManager paymentManager;
  private List paymentList;
  private IOrderFlowManager orderFlowManager;
  private int orderId;
  private List paymentLogsList;
  private List refundLogsList;

  public String paymentList()
  {
    this.webpage = this.orderReportManager.listPayment(getPage(), getPageSize(), this.order);
    return "paymentList";
  }

  public String paymentLogs() {
    this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
    this.paymentLogsList = this.orderReportManager.listPayLogs(Integer.valueOf(this.payment.getOrder_id()));
    return "paymentLogs";
  }

  public String paymentDetail() {
    this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
    this.paymentList = this.paymentManager.list();
    return "paymentDetail";
  }

  public String refundList() {
    this.webpage = this.orderReportManager.listRefund(getPage(), getPageSize(), this.order);
    return "refundList";
  }

  public String refundLogs() {
    this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
    this.refundLogsList = this.orderReportManager.listRefundLogs(Integer.valueOf(this.payment.getOrder_id()));
    return "refundLogs";
  }

  public String refundDetail() {
    this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
    this.paymentList = this.paymentManager.list();
    return "refundDetail";
  }

  public String allocationList()
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String depotid = request.getParameter("depotid");
    if (depotid == null) {
      depotid = "0";
    }
    this.webpage = this.orderReportManager.listAllocation(Integer.parseInt(depotid), 0, getPage(), getPageSize());
    return "allocationList";
  }

  public String allocationedList()
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String depotid = request.getParameter("depotid");
    if (depotid == null) {
      depotid = "0";
    }
    this.webpage = this.orderReportManager.listAllocation(Integer.parseInt(depotid), 1, getPage(), getPageSize());
    return "allocationList";
  }

  public String shippingList()
  {
    this.webpage = this.orderReportManager.listShipping(getPage(), getPageSize(), this.order);
    return "shippingList";
  }

  public String shippingDetail() {
    this.delivery = this.orderReportManager.getDelivery(Integer.valueOf(this.id));
    this.listDeliveryItem = this.orderReportManager.listDeliveryItem(Integer.valueOf(this.id));
    return "shippingDetail";
  }

  public String returnedList() {
    this.webpage = this.orderReportManager.listReturned(getPage(), getPageSize(), this.order);
    return "returnedList";
  }

  public String returnedDetail() {
    this.delivery = this.orderReportManager.getDelivery(Integer.valueOf(this.id));
    this.listDeliveryItem = this.orderReportManager.listDeliveryItem(Integer.valueOf(this.id));
    return "returnedDetail";
  }

  public IOrderReportManager getOrderReportManager()
  {
    return this.orderReportManager;
  }

  public void setOrderReportManager(IOrderReportManager orderReportManager) {
    this.orderReportManager = orderReportManager;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public PaymentLog getPayment() {
    return this.payment;
  }

  public void setPayment(PaymentLog payment) {
    this.payment = payment;
  }

  public Delivery getDelivery() {
    return this.delivery;
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
  }

  public List<DeliveryItem> getListDeliveryItem() {
    return this.listDeliveryItem;
  }

  public void setListDeliveryItem(List<DeliveryItem> listDeliveryItem) {
    this.listDeliveryItem = listDeliveryItem;
  }

  public IPaymentManager getPaymentManager() {
    return this.paymentManager;
  }

  public void setPaymentManager(IPaymentManager paymentManager) {
    this.paymentManager = paymentManager;
  }

  public List getPaymentList() {
    return this.paymentList;
  }

  public void setPaymentList(List paymentList) {
    this.paymentList = paymentList;
  }

  public IOrderFlowManager getOrderFlowManager() {
    return this.orderFlowManager;
  }

  public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
    this.orderFlowManager = orderFlowManager;
  }

  public int getOrderId() {
    return this.orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public List getPaymentLogsList() {
    return this.paymentLogsList;
  }

  public void setPaymentLogsList(List paymentLogsList) {
    this.paymentLogsList = paymentLogsList;
  }

  public List getRefundLogsList() {
    return this.refundLogsList;
  }

  public void setRefundLogsList(List refundLogsList) {
    this.refundLogsList = refundLogsList;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.OrderReportAction
 * JD-Core Version:    0.6.1
 */