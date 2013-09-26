package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.service.IRegionsManager;
import com.enation.app.shop.core.model.DlyCenter;
import com.enation.app.shop.core.service.IDlyCenterManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class DlyCenterAction extends WWAction
{
  private IDlyCenterManager dlyCenterManager;
  private IRegionsManager regionsManager;
  private DlyCenter dlyCenter;
  private Integer dlyCenterId;
  private Integer[] id;
  private List<DlyCenter> list;
  private List provinceList;
  private List cityList;
  private List regionList;

  public String add()
  {
    this.provinceList = this.regionsManager.listProvince();
    return "add";
  }

  public String edit() {
    this.dlyCenter = this.dlyCenterManager.get(this.dlyCenterId);
    this.provinceList = this.regionsManager.listProvince();
    if (this.dlyCenter.getProvince_id() != null) {
      this.cityList = this.regionsManager.listCity(this.dlyCenter.getProvince_id().intValue());
    }
    if (this.dlyCenter.getCity_id() != null) {
      this.regionList = this.regionsManager.listRegion(this.dlyCenter.getCity_id().intValue());
    }
    return "edit";
  }

  public String list() {
    this.list = this.dlyCenterManager.list();
    return "list";
  }

  public String delete() {
    try {
      this.dlyCenterManager.delete(this.id);
      this.json = "{result:0,message:'发货信息删除成功'}";
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:1,message:\"发货信息删除失败：" + e.getMessage() + "\"}");
    }

    return "json_message";
  }

  public String saveAdd() {
    try {
      this.dlyCenterManager.add(this.dlyCenter);
      this.msgs.add("发货信息添加成功");
    }
    catch (Exception e) {
      e.printStackTrace();
      this.msgs.add("发货信息添加失败");
    }
    this.urls.put("发货信息管理", "dlyCenter!list.do");
    return "message";
  }

  public String saveEdit() {
    try {
      this.dlyCenterManager.edit(this.dlyCenter);
      this.msgs.add("发货信息修改成功");
    }
    catch (Exception e) {
      e.printStackTrace();
      this.msgs.add("发货信息修改失败");
    }
    this.urls.put("发货信息管理", "dlyCenter!list.do");
    return "message";
  }

  public IDlyCenterManager getDlyCenterManager() {
    return this.dlyCenterManager;
  }

  public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) {
    this.dlyCenterManager = dlyCenterManager;
  }

  public DlyCenter getDlyCenter() {
    return this.dlyCenter;
  }

  public void setDlyCenter(DlyCenter dlyCenter) {
    this.dlyCenter = dlyCenter;
  }

  public Integer getDlyCenterId() {
    return this.dlyCenterId;
  }

  public void setDlyCenterId(Integer dlyCenterId) {
    this.dlyCenterId = dlyCenterId;
  }

  public Integer[] getId() {
    return this.id;
  }

  public void setId(Integer[] id) {
    this.id = id;
  }

  public List<DlyCenter> getList() {
    return this.list;
  }

  public void setList(List<DlyCenter> list) {
    this.list = list;
  }

  public IRegionsManager getRegionsManager() {
    return this.regionsManager;
  }

  public void setRegionsManager(IRegionsManager regionsManager) {
    this.regionsManager = regionsManager;
  }

  public List getProvinceList() {
    return this.provinceList;
  }

  public void setProvinceList(List provinceList) {
    this.provinceList = provinceList;
  }

  public List getCityList() {
    return this.cityList;
  }

  public void setCityList(List cityList) {
    this.cityList = cityList;
  }

  public List getRegionList() {
    return this.regionList;
  }

  public void setRegionList(List regionList) {
    this.regionList = regionList;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.DlyCenterAction
 * JD-Core Version:    0.6.1
 */