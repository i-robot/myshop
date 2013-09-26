package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.Depot;
import com.enation.app.shop.core.service.IDepotManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class DepotAction extends WWAction
{
  private IDepotManager depotManager;
  private Depot room;
  private int id;
  private List depotList;

  public String list()
  {
    this.depotList = this.depotManager.list();
    return "list";
  }

  public String add() {
    return "add";
  }

  public String edit() {
    this.room = this.depotManager.get(this.id);
    return "edit";
  }

  public String saveAdd() {
    this.depotManager.add(this.room);
    this.msgs.add("仓库新增成功");
    this.urls.put("仓库列表", "depot!list.do");
    return "message";
  }

  public String saveEdit() {
    this.depotManager.update(this.room);
    this.msgs.add("仓库修改成功");
    this.urls.put("仓库列表", "depot!list.do");
    return "message";
  }

  public String delete() {
    this.depotManager.delete(this.id);
    this.json = "{'result':0,'message':'操作成功'}";
    return "json_message";
  }

  public IDepotManager getDepotManager() {
    return this.depotManager;
  }

  public void setDepotManager(IDepotManager depotManager) {
    this.depotManager = depotManager;
  }

  public Depot getRoom() {
    return this.room;
  }

  public void setRoom(Depot room) {
    this.room = room;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List getDepotList() {
    return this.depotList;
  }

  public void setDepotList(List depotList) {
    this.depotList = depotList;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.DepotAction
 * JD-Core Version:    0.6.1
 */