package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.Brand;
import com.enation.app.shop.core.service.IBrandManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.FileUtil;
import java.io.File;
import java.util.List;
import java.util.Map;

public class BrandAction extends WWAction
{
  private IBrandManager brandManager;
  private Brand brand;
  private File logo;
  private String logoFileName;
  private String oldlogo;
  private String filePath;
  private String order;
  private Integer brand_id;
  private String id;
  private List<Map> brand_types;
  private int type_id;
  private String brandname;

  public String getBrandname()
  {
    return this.brandname;
  }

  public void setBrandname(String brandname) {
    this.brandname = brandname;
  }

  public int getType_id() {
    return this.type_id;
  }

  public void setType_id(int type_id) {
    this.type_id = type_id;
  }

  public List<Map> getBrand_types() {
    return this.brand_types;
  }

  public void setBrand_types(List<Map> brand_types) {
    this.brand_types = brand_types;
  }

  public IBrandManager getBrandManager() {
    return this.brandManager;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String checkUsed() {
    if (this.brandManager.checkUsed(this.id))
      this.json = "{result:1}";
    else {
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String checkname() {
    if (this.brandManager.checkname(this.brand.getName(), this.brand.getBrand_id()))
      this.json = "{result:1}";
    else {
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String add() {
    return "add";
  }

  public String edit() {
    this.brand = this.brandManager.get(this.brand_id);
    return "edit";
  }

  public String list()
  {
    this.brand_types = this.brandManager.queryAllTypeNameAndId();
    this.webpage = this.brandManager.list(this.order, getPage(), getPageSize());
    return "list";
  }

  public String search()
  {
    this.brand_types = this.brandManager.queryAllTypeNameAndId();
    this.webpage = this.brandManager.search(getPage(), getPageSize(), this.brandname, Integer.valueOf(this.type_id));
    return "list";
  }

  public String trash_list()
  {
    this.webpage = this.brandManager.listTrash(this.order, getPage(), getPageSize());
    return "trash_list";
  }

  public String save()
  {
    if ((this.logo != null) && 
      (!FileUtil.isAllowUp(this.logoFileName)))
    {
      this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
      return "message";
    }

    this.brand.setDisabled(Integer.valueOf(0));
    this.brand.setFile(this.logo);
    this.brand.setFileFileName(this.logoFileName);
    this.brandManager.add(this.brand);
    this.msgs.add("品牌添加成功");
    this.urls.put("品牌列表", "brand!list.do");
    return "message";
  }

  public String saveEdit()
  {
    if ((this.logo != null) && 
      (!FileUtil.isAllowUp(this.logoFileName)))
    {
      this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
      return "message";
    }

    this.brand.setFile(this.logo);
    this.brand.setFileFileName(this.logoFileName);
    this.brandManager.update(this.brand);
    this.msgs.add("品牌修改成功");
    this.urls.put("品牌列表", "brand!list.do");
    return "message";
  }

  public String delete()
  {
    try
    {
      this.brandManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String revert()
  {
    try
    {
      this.brandManager.revert(this.id);
      this.json = "{'result':0,'message':'还原成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String clean()
  {
    try
    {
      this.brandManager.clean(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public Brand getBrand() {
    return this.brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public File getLogo() {
    return this.logo;
  }

  public void setLogo(File logo) {
    this.logo = logo;
  }

  public String getLogoFileName() {
    return this.logoFileName;
  }

  public void setLogoFileName(String logoFileName) {
    this.logoFileName = logoFileName;
  }

  public void setBrandManager(IBrandManager brandManager)
  {
    this.brandManager = brandManager;
  }

  public String getOldlogo() {
    return this.oldlogo;
  }

  public void setOldlogo(String oldlogo) {
    this.oldlogo = oldlogo;
  }

  public String getFilePath() {
    return this.filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public Integer getBrand_id() {
    return this.brand_id;
  }

  public void setBrand_id(Integer brand_id) {
    this.brand_id = brand_id;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.BrandAction
 * JD-Core Version:    0.6.1
 */