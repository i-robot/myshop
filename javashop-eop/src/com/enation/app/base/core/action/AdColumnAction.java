package com.enation.app.base.core.action;

import com.enation.app.base.core.model.AdColumn;
import com.enation.app.base.core.service.IAdColumnManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class AdColumnAction extends WWAction
{
  private IAdColumnManager adColumnManager;
  private AdColumn adColumn;
  private Long acid;
  private String id;

  public String list()
  {
    this.webpage = this.adColumnManager.pageAdvPos(getPage(), getPageSize());
    return "list";
  }

  public String detail() {
    this.adColumn = this.adColumnManager.getADcolumnDetail(this.acid);
    return "detail";
  }

  public String delete() {
    try {
      this.adColumnManager.delAdcs(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String add() {
    return "add";
  }

  public String addSave() {
    this.adColumnManager.addAdvc(this.adColumn);
    this.msgs.add("新增广告位成功");
    this.urls.put("广告位列表", "adColumn!list.do");
    return "message";
  }

  public String edit() {
    this.adColumn = this.adColumnManager.getADcolumnDetail(this.acid);
    return "edit";
  }

  public String editSave() {
    this.adColumnManager.updateAdvc(this.adColumn);
    this.msgs.add("修改广告位成功");
    this.urls.put("广告位列表", "adColumn!list.do");
    return "message";
  }

  public IAdColumnManager getAdColumnManager() {
    return this.adColumnManager;
  }

  public void setAdColumnManager(IAdColumnManager adColumnManager) {
    this.adColumnManager = adColumnManager;
  }

  public AdColumn getAdColumn() {
    return this.adColumn;
  }

  public void setAdColumn(AdColumn adColumn) {
    this.adColumn = adColumn;
  }

  public Long getAcid() {
    return this.acid;
  }

  public void setAcid(Long acid) {
    this.acid = acid;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.AdColumnAction
 * JD-Core Version:    0.6.1
 */