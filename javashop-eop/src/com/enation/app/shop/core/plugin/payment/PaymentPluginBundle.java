package com.enation.app.shop.core.plugin.payment;

import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import java.util.List;

public class PaymentPluginBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "支付插件桩";
  }

  public List getPluginList() {
    return getPlugins();
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.payment.PaymentPluginBundle
 * JD-Core Version:    0.6.1
 */