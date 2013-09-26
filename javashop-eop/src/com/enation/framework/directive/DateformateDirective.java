package com.enation.framework.directive;

import com.enation.framework.util.DateUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

public class DateformateDirective
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
    throws TemplateException, IOException
  {
    String timeStr = params.get("time").toString();
    String pattern = params.get("pattern").toString();
    long time = Long.valueOf(timeStr).longValue();
    if (time > 0L)
    {
      Date date = new Date(time);
      String str = DateUtil.toString(date, pattern);
      env.getOut().write(str);
    } else {
      env.getOut().write("");
    }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.directive.DateformateDirective
 * JD-Core Version:    0.6.1
 */