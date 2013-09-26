package com.enation.framework.directive;

import com.enation.framework.resource.Resource;
import com.enation.framework.resource.impl.ResourceBuilder;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Map;

public class CssDirectiveModel extends AbstractResourceDirectiveModel
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
    throws TemplateException, IOException
  {
    Resource resource = createResource(params);
    resource.setType("css");
    ResourceBuilder.putCss(resource);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.directive.CssDirectiveModel
 * JD-Core Version:    0.6.1
 */