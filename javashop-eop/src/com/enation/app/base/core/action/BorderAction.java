package com.enation.app.base.core.action;

import com.enation.eop.resource.IBorderManager;
import com.enation.eop.resource.model.Border;
import com.enation.framework.action.WWAction;
import java.util.List;

public class BorderAction extends WWAction
{
  private IBorderManager borderManager;
  private List<Border> borderList;

  public String execute()
  {
    this.borderList = this.borderManager.list();
    return "list";
  }
  public void setBorderManager(IBorderManager borderManager) {
    this.borderManager = borderManager;
  }
  public void setBorderList(List<Border> borderList) {
    this.borderList = borderList;
  }
  public IBorderManager getBorderManager() {
    return this.borderManager;
  }
  public List<Border> getBorderList() {
    return this.borderList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.BorderAction
 * JD-Core Version:    0.6.1
 */