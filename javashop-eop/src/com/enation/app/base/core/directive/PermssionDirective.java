package com.enation.app.base.core.directive;

import com.enation.app.base.core.service.auth.IPermissionManager;
import com.enation.app.base.core.service.auth.impl.PermissionConfig;
import com.enation.framework.context.spring.SpringContextHolder;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class PermssionDirective
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
    throws TemplateException, IOException
  {
    IPermissionManager permissionManager = (IPermissionManager)SpringContextHolder.getBean("permissionManager");
    String actid = params.get("actid").toString();
    String[] arr = StringUtils.split(actid, ",");
    boolean result = false;
    for (String item : arr) {
      result = permissionManager.checkHaveAuth(PermissionConfig.getAuthId(item));
      if (result)
      {
        break;
      }
    }
    if (result)
      body.render(env.getOut());
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.directive.PermssionDirective
 * JD-Core Version:    0.6.1
 */