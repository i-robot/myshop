package com.enation.app.shop.core.plugin.payment;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.PayCfg;
import com.enation.app.shop.core.model.PaymentLog;
import com.enation.app.shop.core.service.IAdvanceLogsManager;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IOrderFlowManager;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.app.shop.core.service.IPaymentManager;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.plugin.AutoRegisterPlugin;
import com.enation.framework.util.DateUtil;
import java.text.NumberFormat;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public abstract class AbstractPaymentPlugin extends AutoRegisterPlugin
{
  protected IPaymentManager paymentManager;
  private IOrderFlowManager orderFlowManager;
  private IOrderManager orderManager;
  private IMemberManager memberManager;
  private IAdvanceLogsManager advanceLogsManager;
  protected final Logger logger = Logger.getLogger(getClass());
  private String callbackUrl;
  private String showUrl;

  protected String getCallBackUrl(PayCfg payCfg)
  {
    if (this.callbackUrl != null)
      return this.callbackUrl;
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String serverName = request.getServerName();
    int port = request.getLocalPort();
    String contextPath = request.getContextPath();
    return "http://" + serverName + ":" + port + contextPath + "/payok_callback_" + payCfg.getType() + ".html";
  }

  protected String getReturnUrl(PayCfg payCfg) {
    if (this.callbackUrl != null) return this.callbackUrl;
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String serverName = request.getServerName();
    int port = request.getLocalPort();
    String contextPath = request.getContextPath();
    return "http://" + serverName + ":" + port + contextPath + "/pay_return_" + payCfg.getType() + ".html";
  }

  protected String formatPrice(Double price)
  {
    NumberFormat nFormat = NumberFormat.getNumberInstance();
    nFormat.setMaximumFractionDigits(0);
    nFormat.setGroupingUsed(false);
    return nFormat.format(price);
  }

  protected String getShowUrl(Order order)
  {
    if (this.showUrl != null) return this.showUrl;

    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String serverName = request.getServerName();
    int port = request.getLocalPort();
    String contextPath = request.getContextPath();
    return "http://" + serverName + ":" + port + contextPath + "/orderdetail_" + order.getSn() + ".html";
  }

  public void setCallBackUrl(String url)
  {
    this.callbackUrl = url;
  }

  public void setShowUrl(String url)
  {
    this.showUrl = url;
  }

  protected Map<String, String> getConfigParams()
  {
    return this.paymentManager.getConfigParams(getId());
  }

  protected void paySuccess(String ordersn, String tradeno)
  {
    Order order = this.orderManager.get(ordersn);
    if (order.getPay_status().intValue() != 0) {
      return;
    }
    PaymentLog paymentLog = new PaymentLog();
    Member member = UserServiceFactory.getUserService().getCurrentMember();
    if (member != null) {
      paymentLog.setMember_id(member.getMember_id());
      paymentLog.setPay_user(member.getUname());
    } else {
      paymentLog.setPay_user("匿名购买者");
    }

    paymentLog.setRemark("在线支付");
    paymentLog.setMoney(order.getNeedPayMoney());
    paymentLog.setOrder_sn(order.getSn());
    paymentLog.setPay_method(order.getPayment_name());
    paymentLog.setSn(tradeno);
    paymentLog.setOrder_id(order.getOrder_id().intValue());
    paymentLog.setPay_date(DateUtil.getDatelineLong());
    this.orderFlowManager.pay(paymentLog, true);
  }

  public abstract String getId();

  public abstract String getName();

  public IPaymentManager getPaymentManager()
  {
    return this.paymentManager;
  }

  public void setPaymentManager(IPaymentManager paymentManager) {
    this.paymentManager = paymentManager;
  }

  public IOrderFlowManager getOrderFlowManager() {
    return this.orderFlowManager;
  }

  public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
    this.orderFlowManager = orderFlowManager;
  }

  public IOrderManager getOrderManager() {
    return this.orderManager;
  }

  public void setOrderManager(IOrderManager orderManager) {
    this.orderManager = orderManager;
  }

  public IMemberManager getMemberManager()
  {
    return this.memberManager;
  }

  public void setMemberManager(IMemberManager memberManager) {
    this.memberManager = memberManager;
  }

  public IAdvanceLogsManager getAdvanceLogsManager() {
    return this.advanceLogsManager;
  }

  public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
    this.advanceLogsManager = advanceLogsManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin
 * JD-Core Version:    0.6.1
 */