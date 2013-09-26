package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.FreeOfferCategory;
import com.enation.app.shop.core.service.IFreeOfferCategoryManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class FreeOfferCategoryAction extends WWAction
{
  private String name;
  private String order;
  private FreeOfferCategory freeOfferCategory;
  private Integer cat_id;
  private String id;
  private IFreeOfferCategoryManager freeOfferCategoryManager;

  public String list()
    throws Exception
  {
    this.webpage = this.freeOfferCategoryManager.searchFreeOfferCategory(this.name, this.order, getPage(), getPageSize());

    return "list";
  }

  public String trash_list()
  {
    this.webpage = this.freeOfferCategoryManager.pageTrash(this.name, this.order, getPage(), getPageSize());

    return "trash_list";
  }

  public String add()
  {
    return "add";
  }

  public String edit() {
    this.freeOfferCategory = this.freeOfferCategoryManager.get(this.cat_id.intValue());
    return "edit";
  }

  public String save()
  {
    this.freeOfferCategory.setDisabled(Integer.valueOf(0));
    this.freeOfferCategoryManager.saveAdd(this.freeOfferCategory);
    this.msgs.add("赠品分类添加成功");
    this.urls.put("赠品分类列表", "freeOfferCategory!list.do");
    return "message";
  }

  public String saveEdit()
  {
    this.freeOfferCategoryManager.update(this.freeOfferCategory);
    this.msgs.add("赠品分类修改成功");
    this.urls.put("赠品分类列表", "freeOfferCategory!list.do");
    return "message";
  }

  public String delete()
  {
    try
    {
      this.freeOfferCategoryManager.delete(this.id);
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
      this.freeOfferCategoryManager.revert(this.id);
      this.json = "{'result':0,'message':'还原成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'还原失败'}";
    }
    return "json_message";
  }

  public String clean()
  {
    try
    {
      this.freeOfferCategoryManager.clean(this.id);
      this.json = "{'result':0,'message':'清除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'清除失败'}";
    }
    return "json_message";
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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

  public FreeOfferCategory getFreeOfferCategory() {
    return this.freeOfferCategory;
  }

  public void setFreeOfferCategory(FreeOfferCategory freeOfferCategory) {
    this.freeOfferCategory = freeOfferCategory;
  }

  public Integer getCat_id() {
    return this.cat_id;
  }

  public void setCat_id(Integer catId) {
    this.cat_id = catId;
  }

  public IFreeOfferCategoryManager getFreeOfferCategoryManager() {
    return this.freeOfferCategoryManager;
  }

  public void setFreeOfferCategoryManager(IFreeOfferCategoryManager freeOfferCategoryManager)
  {
    this.freeOfferCategoryManager = freeOfferCategoryManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.FreeOfferCategoryAction
 * JD-Core Version:    0.6.1
 */