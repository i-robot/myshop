package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.Tag;
import com.enation.app.shop.core.service.ITagManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class TagAction extends WWAction
{
  private ITagManager tagManager;
  private Tag tag;
  private Integer[] tag_ids;
  private Integer tag_id;

  public String checkJoinGoods()
  {
    if (this.tagManager.checkJoinGoods(this.tag_ids))
      this.json = "{result:1}";
    else {
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String checkname() {
    if (this.tagManager.checkname(this.tag.getTag_name(), this.tag.getTag_id()))
      this.json = "{result:1}";
    else {
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String add()
  {
    return "add";
  }

  public String edit() {
    this.tag = this.tagManager.getById(this.tag_id);
    return "edit";
  }

  public String saveAdd()
  {
    this.tagManager.add(this.tag);
    this.msgs.add("标签添加成功");
    this.urls.put("标签列表", "tag!list.do");
    return "message";
  }

  public String saveEdit()
  {
    this.tagManager.update(this.tag);

    this.msgs.add("标签修改成功");
    this.urls.put("标签列表", "tag!list.do");

    return "message";
  }

  public String delete()
  {
    this.tagManager.delete(this.tag_ids);
    this.json = "{'result':0,'message':'删除成功'}";

    return "json_message";
  }

  public String list() {
    this.webpage = this.tagManager.list(getPage(), getPageSize());
    return "list";
  }

  public ITagManager getTagManager() {
    return this.tagManager;
  }

  public void setTagManager(ITagManager tagManager) {
    this.tagManager = tagManager;
  }

  public Tag getTag() {
    return this.tag;
  }

  public void setTag(Tag tag) {
    this.tag = tag;
  }

  public Integer[] getTag_ids() {
    return this.tag_ids;
  }

  public void setTag_ids(Integer[] tagIds) {
    this.tag_ids = tagIds;
  }

  public Integer getTag_id() {
    return this.tag_id;
  }

  public void setTag_id(Integer tagId) {
    this.tag_id = tagId;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.TagAction
 * JD-Core Version:    0.6.1
 */