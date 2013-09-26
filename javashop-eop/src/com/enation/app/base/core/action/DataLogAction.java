package com.enation.app.base.core.action;

import com.enation.eop.resource.IDataLogManager;
import com.enation.framework.action.WWAction;

public class DataLogAction extends WWAction
{
  private IDataLogManager dataLogManager;
  private String start;
  private String end;
  private Integer[] ids;

  public String list()
  {
    this.webpage = this.dataLogManager.list(this.start, this.end, getPage(), getPageSize());
    return "list";
  }

  public String delete() {
    try {
      this.dataLogManager.delete(this.ids);
      this.json = "{result:0,message:'删除成功'}";
    } catch (RuntimeException e) {
      this.json = ("{result:1,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public IDataLogManager getDataLogManager() {
    return this.dataLogManager;
  }

  public void setDataLogManager(IDataLogManager dataLogManager) {
    this.dataLogManager = dataLogManager;
  }

  public String getStart() {
    return this.start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return this.end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public Integer[] getIds() {
    return this.ids;
  }

  public void setIds(Integer[] ids) {
    this.ids = ids;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.DataLogAction
 * JD-Core Version:    0.6.1
 */