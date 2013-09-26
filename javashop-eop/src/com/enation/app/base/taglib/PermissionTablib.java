package com.enation.app.base.taglib;

import com.enation.app.base.core.service.auth.IPermissionManager;
import com.enation.app.base.core.service.auth.impl.PermissionConfig;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.taglib.EnationTagSupport;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang.StringUtils;

public class PermissionTablib extends EnationTagSupport
{
  private String actid;

  public int doStartTag()
    throws JspException
  {
    IPermissionManager permissionManager = (IPermissionManager)SpringContextHolder.getBean("permissionManager");
    String[] arr = StringUtils.split(this.actid, ",");
    boolean result = false;
    for (String item : arr) {
      result = permissionManager.checkHaveAuth(PermissionConfig.getAuthId(item));
      if (result)
      {
        break;
      }
    }
    if (result) {
      return 1;
    }
    return 0;
  }

  public String getActid() {
    return this.actid;
  }

  public void setActid(String actid) {
    this.actid = actid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.taglib.PermissionTablib
 * JD-Core Version:    0.6.1
 */