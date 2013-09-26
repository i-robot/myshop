package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.Logi;
import com.enation.app.shop.core.service.ILogiManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class LogiAction extends WWAction
{
  private ILogiManager logiManager;
  private String name;
  private Integer cid;
  private String id;
  private String order;
  private Logi logi;
  private String code;

  public String add_logi()
  {
    return "add_logi";
  }

  public String edit_logi() {
    this.logi = this.logiManager.getLogiById(this.cid);
    return "edit_logi";
  }

  public String list_logi() {
    this.webpage = this.logiManager.pageLogi(this.order, Integer.valueOf(getPage()), Integer.valueOf(getPageSize()));
    return "list_logi";
  }

  public String delete() {
    try {
      this.logiManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String saveAdd() {
    Logi logi = new Logi();
    logi.setCode(this.code);
    logi.setName(this.name);
    this.logiManager.saveAdd(logi);
    this.msgs.add("添加成功");
    this.urls.put("物流公司列表", "logi!list_logi.do");
    return "message";
  }

  public String saveEdit() {
    Logi logi = new Logi();
    logi.setId(this.cid);
    logi.setCode(this.code);
    logi.setName(this.name);
    this.logiManager.saveEdit(logi);

    this.msgs.add("修改成功");
    this.urls.put("物流公司列表", "logi!list_logi.do");
    return "message";
  }
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ILogiManager getLogiManager() {
    return this.logiManager;
  }

  public void setLogiManager(ILogiManager logiManager) {
    this.logiManager = logiManager;
  }

  public Integer getCid() {
    return this.cid;
  }
  public void setCid(Integer cid) {
    this.cid = cid;
  }
  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public Logi getLogi() {
    return this.logi;
  }

  public void setLogi(Logi logi) {
    this.logi = logi;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.LogiAction
 * JD-Core Version:    0.6.1
 */