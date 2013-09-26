package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.model.Regions;
import com.enation.app.base.core.service.IRegionsManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class RegionAction extends WWAction
{
  private IRegionsManager regionsManager;
  private List listRegion;
  private Integer parentid;
  private Regions regions;
  private Integer region_id;
  private Integer regiongrade;

  public String list()
  {
    return "list";
  }

  public String listChildren() {
    this.listRegion = this.regionsManager.listChildren(this.parentid);
    return "listChildren";
  }

  public String add() {
    return "add";
  }

  public String saveAdd() {
    try {
      this.regionsManager.add(this.regions);
      this.msgs.add("地区添加成功");
    }
    catch (Exception e) {
      e.printStackTrace();
      this.msgs.add("地区添加失败");
    }
    this.urls.put("地区管理", "region!list.do");
    return "message";
  }

  public String edit() {
    this.regions = this.regionsManager.get(this.region_id.intValue());
    return "edit";
  }

  public String saveEdit() {
    try {
      this.regionsManager.update(this.regions);
      this.msgs.add("地区修改成功");
    }
    catch (Exception e) {
      e.printStackTrace();
      this.msgs.add("地区修改失败");
    }
    this.urls.put("地区管理", "region!list.do");
    return "message";
  }

  public String delete() {
    try {
      this.regionsManager.delete(this.region_id.intValue());
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
      e.printStackTrace();
    }
    return "json_message";
  }

  public String reset() {
    try {
      this.regionsManager.reset();
      this.json = "{'result':0,'message':'成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'失败'}";
      e.printStackTrace();
    }
    return "json_message";
  }

  public IRegionsManager getRegionsManager() {
    return this.regionsManager;
  }

  public void setRegionsManager(IRegionsManager regionsManager) {
    this.regionsManager = regionsManager;
  }

  public List getListRegion() {
    return this.listRegion;
  }

  public void setListRegion(List listRegion) {
    this.listRegion = listRegion;
  }

  public Integer getParentid() {
    return this.parentid;
  }

  public void setParentid(Integer parentid) {
    this.parentid = parentid;
  }

  public Regions getRegions() {
    return this.regions;
  }

  public void setRegions(Regions regions) {
    this.regions = regions;
  }

  public Integer getRegion_id() {
    return this.region_id;
  }

  public void setRegion_id(Integer regionId) {
    this.region_id = regionId;
  }

  public Integer getRegiongrade() {
    return this.regiongrade;
  }

  public void setRegiongrade(Integer regiongrade) {
    this.regiongrade = regiongrade;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.RegionAction
 * JD-Core Version:    0.6.1
 */