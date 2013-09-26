package com.enation.app.shop.core.taglib;

import com.enation.app.shop.core.service.OrderStatus;
import com.enation.framework.util.StringUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Map;

public class OrderStatusDirectiveModel
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
    throws TemplateException, IOException
  {
    int status = StringUtil.toInt(params.get("status").toString(), true);
    String type = params.get("type").toString();
    if ("order".equals(type)) {
      String text = OrderStatus.getOrderStatusText(status);
      env.getOut().write(text);
    }
    if ("pay".equals(type)) {
      String text = OrderStatus.getPayStatusText(status);
      env.getOut().write(text);
    }
    if ("ship".equals(type)) {
      String text = OrderStatus.getShipStatusText(status);
      env.getOut().write(text);
    }
  }

  public static void main(String[] args)
  {
    System.out.println(0.1000000000000001D);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.taglib.OrderStatusDirectiveModel
 * JD-Core Version:    0.6.1
 */