package com.enation.app.base.core.action;

import com.enation.app.base.core.model.Help;
import com.enation.app.base.core.service.IHelpManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.database.ObjectNotFoundException;

public class HelpAction extends WWAction
{
  private IHelpManager helpManager;
  private String helpid;
  private Help help;

  public String execute()
  {
    try
    {
      this.help = this.helpManager.get(this.helpid);
      if (this.help == null) {
        this.help = new Help();
        this.help.setContent("此帮助未定义");
      }
    } catch (ObjectNotFoundException e) {
      this.help = new Help();
      this.help.setContent("此帮助未定义");
    }
    return "content";
  }

  public String getHelpid()
  {
    return this.helpid;
  }
  public void setHelpid(String helpid) {
    this.helpid = helpid;
  }
  public IHelpManager getHelpManager() {
    return this.helpManager;
  }
  public void setHelpManager(IHelpManager helpManager) {
    this.helpManager = helpManager;
  }
  public Help getHelp() {
    return this.help;
  }
  public void setHelp(Help help) {
    this.help = help;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.HelpAction
 * JD-Core Version:    0.6.1
 */