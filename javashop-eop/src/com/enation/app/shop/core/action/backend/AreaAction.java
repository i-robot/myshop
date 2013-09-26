package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.model.Regions;
import com.enation.app.base.core.service.IRegionsManager;
import com.enation.app.shop.core.model.DlyArea;
import com.enation.app.shop.core.service.IAreaManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class AreaAction extends WWAction
{
  private IRegionsManager regionsManager;
  private Regions regions;
  private List provinceList;
  private List cityList;
  private List regionList;
  private int province_id;
  private int city_id;
  private Integer regionid;
  private Integer area_id;
  private String name;
  private IAreaManager areaManager;
  private String order;
  private String id;
  private DlyArea area;

  public String add_region()
  {
    return "add_region";
  }

  public String edit_region() {
    return "eidt_region";
  }

  public String list_province() {
    this.provinceList = this.regionsManager.listProvince();
    return "list_province";
  }

  public String list_city() {
    this.cityList = this.regionsManager.listCity(this.province_id);
    return "list_city";
  }

  public String list_region() {
    this.regionList = this.regionsManager.listRegion(this.city_id);
    return "list_region";
  }

  public String listChildren() {
    this.json = this.regionsManager.getChildrenJson(this.regionid);
    return "json_message";
  }

  public IRegionsManager getRegionsManager()
  {
    return this.regionsManager;
  }

  public void setRegionsManager(IRegionsManager regionsManager) {
    this.regionsManager = regionsManager;
  }

  public Regions getRegions() {
    return this.regions;
  }

  public void setRegions(Regions regions) {
    this.regions = regions;
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

  public int getProvince_id() {
    return this.province_id;
  }

  public void setProvince_id(int provinceId) {
    this.province_id = provinceId;
  }

  public int getCity_id() {
    return this.city_id;
  }

  public void setCity_id(int cityId) {
    this.city_id = cityId;
  }

  public String add_area()
  {
    return "add_area";
  }

  public String edit_area() {
    this.area = this.areaManager.getDlyAreaById(this.area_id);
    return "edit_area";
  }

  public String list() {
    this.webpage = this.areaManager.pageArea(this.order, getPage(), getPageSize());
    return "list_area";
  }

  public String delete() {
    try {
      this.areaManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String saveAdd() {
    this.areaManager.saveAdd(this.name);
    this.msgs.add("地区保存成功");
    this.urls.put("地区列表", "area!list.do");
    return "message";
  }

  public String saveEdit()
  {
    this.areaManager.saveEdit(this.area_id, this.name);
    this.msgs.add("地区保存成功");
    this.urls.put("地区列表", "area!list.do");
    return "message";
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public IAreaManager getAreaManager()
  {
    return this.areaManager;
  }

  public void setAreaManager(IAreaManager areaManager)
  {
    this.areaManager = areaManager;
  }

  public Integer getArea_id()
  {
    return this.area_id;
  }

  public void setArea_id(Integer area_id) {
    this.area_id = area_id;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DlyArea getArea() {
    return this.area;
  }

  public void setArea(DlyArea area) {
    this.area = area;
  }

  public Integer getRegionid() {
    return this.regionid;
  }

  public void setRegionid(Integer regionid) {
    this.regionid = regionid;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.AreaAction
 * JD-Core Version:    0.6.1
 */