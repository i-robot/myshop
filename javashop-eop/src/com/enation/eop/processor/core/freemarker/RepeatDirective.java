package com.enation.eop.processor.core.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Map;

public class RepeatDirective
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
    throws TemplateException, IOException
  {
    Integer count = Integer.valueOf(params.get("count").toString());
    int i = 0; for (int len = count.intValue(); i < len; i++)
      body.render(env.getOut());
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.core.freemarker.RepeatDirective
 * JD-Core Version:    0.6.1
 */