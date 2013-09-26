package com.enation.app.base.core.action;

import com.enation.app.base.core.model.ShortMsg;
import com.enation.app.base.core.service.IShortMsgManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import net.sf.json.JSONArray;

public class ShortMsgAction extends WWAction
{
  private IShortMsgManager shortMsgManager;
  private List<ShortMsg> msgList;

  public String listNew()
  {
    this.msgList = this.shortMsgManager.listNotReadMessage();
    this.json = JSONArray.fromObject(JSONArray.fromObject(this.msgList)).toString();
    return "json_message";
  }

  public IShortMsgManager getShortMsgManager()
  {
    return this.shortMsgManager;
  }

  public void setShortMsgManager(IShortMsgManager shortMsgManager)
  {
    this.shortMsgManager = shortMsgManager;
  }

  public List<ShortMsg> getMsgList()
  {
    return this.msgList;
  }

  public void setMsgList(List<ShortMsg> msgList)
  {
    this.msgList = msgList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.ShortMsgAction
 * JD-Core Version:    0.6.1
 */