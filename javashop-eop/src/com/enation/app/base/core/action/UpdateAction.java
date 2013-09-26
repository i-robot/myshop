package com.enation.app.base.core.action;

import com.enation.app.base.core.model.VersionState;
import com.enation.app.base.core.service.IUpdateManager;
import com.enation.framework.action.WWAction;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

public class UpdateAction extends WWAction
{
  private IUpdateManager updateManager;

  public String checkNewVersion()
  {
    VersionState versionState = null;
    try {
      versionState = this.updateManager.checkNewVersion();
    } catch (Exception e) {
      versionState = new VersionState();
    }
    this.json = JSONObject.fromObject(versionState).toString();
    return "json_message";
  }

  public String update()
  {
    try {
      this.updateManager.update();
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.logger.error(e);
      e.printStackTrace();
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public IUpdateManager getUpdateManager()
  {
    return this.updateManager;
  }
  public void setUpdateManager(IUpdateManager updateManager) {
    this.updateManager = updateManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.UpdateAction
 * JD-Core Version:    0.6.1
 */