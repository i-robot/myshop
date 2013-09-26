package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.Tag;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.app.shop.core.service.IGoodsTagManager;
import com.enation.app.shop.core.service.ITagManager;
import com.enation.eop.processor.httpcache.HttpCacheManager;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.StringUtil;
import java.util.List;
import java.util.Map;

public class GoodsShowAction extends WWAction
{
  protected String name;
  protected String sn;
  protected String order;
  private Integer catid;
  protected Integer[] id;
  private Integer[] tagids;
  private Integer[] ordernum;
  protected Integer market_enable = new Integer(1);
  protected IGoodsManager goodsManager;
  protected ITagManager tagManager;
  private Tag tag;
  private int tagid;
  private int goodsid;
  private List<Tag> taglist;
  private IGoodsTagManager goodsTagManager;

  public String taglist()
  {
    this.taglist = this.tagManager.list();
    return "taglist";
  }

  public String execute()
  {
    this.tag = this.tagManager.getById(Integer.valueOf(this.tagid));
    if ((this.catid == null) || (this.catid.intValue() == 0)) {
      this.webpage = this.goodsTagManager.getGoodsList(this.tagid, getPage(), getPageSize());
    }
    else {
      this.webpage = this.goodsTagManager.getGoodsList(this.tagid, this.catid.intValue(), getPage(), getPageSize());
    }

    return "list";
  }

  public String add()
  {
    this.tag = this.tagManager.getById(Integer.valueOf(this.tagid));
    return "add";
  }

  public String search()
  {
    this.tag = this.tagManager.getById(Integer.valueOf(this.tagid));
    if (this.name != null) {
      String encoding = EopSetting.ENCODING;
      if (!StringUtil.isEmpty(encoding)) {
        this.name = StringUtil.to(this.name, encoding);
      }
    }
    this.webpage = this.goodsManager.searchGoods(null, null, this.catid, this.name, this.sn, this.market_enable, null, this.order, getPage(), getPageSize());

    return "search_list";
  }

  public String batchAdd()
  {
    try
    {
      if ((this.id != null) && (this.id.length > 0)) {
        for (Integer goodsId : this.id) {
          this.goodsTagManager.addTag(this.tagid, goodsId.intValue());
        }
      }
      updateHttpCache();

      this.json = "{'result':0,'message':'添加成功！'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'添加失败！'}";
    }
    return "json_message";
  }

  public String delete()
  {
    try
    {
      this.goodsTagManager.removeTag(this.tagid, this.goodsid);
      int tempCatId = this.catid == null ? 0 : this.catid.intValue();
      updateHttpCache();
      this.msgs.add("删除成功");
      this.urls.put("列表页面", "goodsShow.do?tagid=" + this.tagid + "&catid=" + tempCatId);
    }
    catch (RuntimeException e) {
      e.printStackTrace();
      this.msgs.add(e.getMessage());
    }
    return "message";
  }

  public String saveOrdernum()
  {
    try
    {
      this.goodsTagManager.updateOrderNum(this.id, this.tagids, this.ordernum);
      int tempCatId = this.catid == null ? 0 : this.catid.intValue();
      updateHttpCache();
      this.msgs.add("保存排序成功");
      this.urls.put("列表页面", "goodsShow.do?tagid=" + this.tagid + "&catid=" + tempCatId);
    }
    catch (RuntimeException e) {
      e.printStackTrace();
      this.msgs.add(e.getMessage());
    }
    return "message";
  }

  private void updateHttpCache()
  {
    HttpCacheManager.updateUriModified("/");
    HttpCacheManager.updateUriModified("/index.html");
    HttpCacheManager.updateUriModified("/search-(.*).html");
  }

  public void setGoodsTagManager(IGoodsTagManager goodsTagManager) {
    this.goodsTagManager = goodsTagManager;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSn() {
    return this.sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public Integer getCatid() {
    return this.catid;
  }

  public void setCatid(Integer catid) {
    this.catid = catid;
  }

  public Integer[] getId() {
    return this.id;
  }

  public void setId(Integer[] id) {
    this.id = id;
  }

  public Integer[] getTagids() {
    return this.tagids;
  }

  public void setTagids(Integer[] tagids) {
    this.tagids = tagids;
  }

  public int getTagid() {
    return this.tagid;
  }

  public void setTagid(int tagid) {
    this.tagid = tagid;
  }

  public void setGoodsManager(IGoodsManager goodsManager) {
    this.goodsManager = goodsManager;
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

  public Integer[] getOrdernum() {
    return this.ordernum;
  }

  public void setOrdernum(Integer[] ordernum) {
    this.ordernum = ordernum;
  }

  public int getGoodsid() {
    return this.goodsid;
  }

  public void setGoodsid(int goodsid) {
    this.goodsid = goodsid;
  }
  public List<Tag> getTaglist() {
    return this.taglist;
  }
  public void setTaglist(List<Tag> taglist) {
    this.taglist = taglist;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsShowAction
 * JD-Core Version:    0.6.1
 */