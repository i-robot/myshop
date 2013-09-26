package com.enation.app.shop.core.taglib;

import com.enation.app.shop.core.service.OrderStatus;
import com.enation.framework.taglib.EnationTagSupport;
import javax.servlet.jsp.JspException;

public class OrderStatusTablib extends EnationTagSupport
{
  private int status;
  private String type;

  public int doEndTag()
    throws JspException
  {
    if ("order".equals(this.type)) {
      String text = OrderStatus.getOrderStatusText(this.status);
      print(text);
    }
    if ("pay".equals(this.type)) {
      String text = OrderStatus.getPayStatusText(this.status);
      print(text);
    }
    if ("ship".equals(this.type)) {
      String text = OrderStatus.getShipStatusText(this.status);
      print(text);
    }
    return 1;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.taglib.OrderStatusTablib
 * JD-Core Version:    0.6.1
 */