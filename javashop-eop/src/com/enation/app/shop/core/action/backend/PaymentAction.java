package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.PaymentLog;
import com.enation.app.shop.core.model.RefundLog;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IOrderFlowManager;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.app.shop.core.service.IOrderReportManager;
import com.enation.app.shop.core.service.IPaymentManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class PaymentAction extends WWAction
{
  private PaymentLog payment;
  private RefundLog refund;
  private Integer orderId;
  private IOrderManager orderManager;
  private IOrderFlowManager orderFlowManager;
  private IPaymentManager paymentManager;
  private IMemberManager memberManager;
  private Order ord;
  private List paymentList;
  private List payLogList;
  private IOrderReportManager orderReportManager;

  public IMemberManager getMemberManager()
  {
    return this.memberManager;
  }

  public void setMemberManager(IMemberManager memberManager) {
    this.memberManager = memberManager;
  }

  public String showPayDialog()
  {
    this.ord = this.orderManager.get(this.orderId);
    this.payLogList = this.orderReportManager.listPayLogs(this.orderId);
    return "pay_dialog";
  }

  public String showRefundDialog()
  {
    this.ord = this.orderManager.get(this.orderId);

    return "refund_dialog";
  }

  public String showPaylogDialog()
  {
    this.ord = this.orderManager.get(this.orderId);
    return "pay_log_dialog";
  }

  public String pay_log()
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String orderId = request.getParameter("orderId");
    String pay_method = request.getParameter("pay_method");
    String paydate = request.getParameter("paydate");
    String sn = request.getParameter("sn");
    String paymoney = request.getParameter("paymoney");
    String remark = request.getParameter("remark");

    if (StringUtil.isEmpty(paymoney)) {
      this.json = "{result:0,message:\"付款金额不能为空，请确认后再提交付款信息！\"}";
      return "json_message";
    }
    if (!StringUtil.checkFloat(paymoney, "0+")) {
      this.json = "{result:0,message:\"付款金额格式不正确，请确认后再提交付款信息！\"}";
      return "json_message";
    }
    if ((orderId == null) || (orderId.equals(""))) {
      this.json = "{result:0,message:\"订单号错误，请确认后再提交付款信息！\"}";
      return "json_message";
    }
    Order order = this.orderManager.get(Integer.valueOf(Integer.parseInt(orderId)));
    if (order == null) {
      this.json = "{result:0,message:\"订单号错误，请确认后再提交付款信息！\"}";
      return "json_message";
    }
    if ((!order.getIsCod()) && (
      (order.getStatus() == null) || (order.getStatus().intValue() != 0))) {
      this.json = "{result:0,message:\"订单状态错误，请确认后再提交付款信息！\"}";
      return "json_message";
    }

    PaymentLog paymentLog = new PaymentLog();

    Member member = null;
    if (order.getMember_id() != null) {
      member = this.memberManager.get(order.getMember_id());
    }
    if (member != null) {
      paymentLog.setMember_id(member.getMember_id());
      paymentLog.setPay_user(member.getUname());
    } else {
      paymentLog.setPay_user("匿名购买者");
    }
    paymentLog.setPay_date(DateUtil.getDatelineLong(paydate));
    paymentLog.setRemark(remark);
    paymentLog.setMoney(Double.valueOf(paymoney));
    paymentLog.setOrder_sn(order.getSn());
    paymentLog.setPay_method(pay_method);
    paymentLog.setSn(sn);
    paymentLog.setOrder_id(order.getOrder_id().intValue());
    this.orderFlowManager.pay(paymentLog, false);
    this.json = "{result:1,message:\"添加收款成功！\"}";
    return "json_message";
  }

  public String pay()
  {
    try
    {
      Order order = this.orderFlowManager.payConfirm(this.orderId.intValue());
      this.json = ("{result:1,message:'订单[" + order.getSn() + "]已确认收款成功',orderStatus:" + order.getStatus() + ",payStatus:" + order.getPay_status() + ",shipStatus:" + order.getShip_status() + "}");
    }
    catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:0,message:\"确认收款失败：" + e.getMessage() + "\"}");
    }
    return "json_message";
  }

  public String cancel_pay()
  {
    try
    {
      this.refund.setOrder_id(this.orderId.intValue());
      this.orderFlowManager.refund(this.refund);
      Order order = this.orderManager.get(this.orderId);
      this.json = ("{result:1,message:'订单[" + order.getSn() + "]退款成功',payStatus:" + order.getPay_status() + "}");
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:0,message:\"退款失败：" + e.getMessage() + "\"}");
    }
    return "json_message";
  }

  public PaymentLog getPayment() {
    return this.payment;
  }

  public void setPayment(PaymentLog payment) {
    this.payment = payment;
  }

  public Integer getOrderId() {
    return this.orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public IOrderManager getOrderManager() {
    return this.orderManager;
  }

  public void setOrderManager(IOrderManager orderManager) {
    this.orderManager = orderManager;
  }

  public IOrderFlowManager getOrderFlowManager() {
    return this.orderFlowManager;
  }

  public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
    this.orderFlowManager = orderFlowManager;
  }

  public IPaymentManager getPaymentManager() {
    return this.paymentManager;
  }

  public void setPaymentManager(IPaymentManager paymentManager) {
    this.paymentManager = paymentManager;
  }

  public Order getOrd() {
    return this.ord;
  }

  public void setOrd(Order ord) {
    this.ord = ord;
  }

  public List getPaymentList() {
    return this.paymentList;
  }

  public void setPaymentList(List paymentList) {
    this.paymentList = paymentList;
  }

  public IOrderReportManager getOrderReportManager()
  {
    return this.orderReportManager;
  }

  public void setOrderReportManager(IOrderReportManager orderReportManager) {
    this.orderReportManager = orderReportManager;
  }

  public List getPayLogList() {
    return this.payLogList;
  }

  public void setPayLogList(List payLogList) {
    this.payLogList = payLogList;
  }

  public RefundLog getRefund() {
    return this.refund;
  }

  public void setRefund(RefundLog refund) {
    this.refund = refund;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.PaymentAction
 * JD-Core Version:    0.6.1
 */