package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.FreeOffer;
import com.enation.app.shop.core.service.IFreeOfferCategoryManager;
import com.enation.app.shop.core.service.IFreeOfferManager;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FreeOfferAction extends WWAction
{
  private String name;
  private String order;
  private FreeOffer freeOffer;
  private Integer fo_id;
  private String id;
  private Date mstartdate;
  private Date menddate;
  private File thumb;
  private String thumbFileName;
  private File pic;
  private String picFileName;
  private String oldthumb;
  private String oldpic;
  private Integer[] lv_ids;
  private List categoryList;
  private List member_lvList;
  private IFreeOfferManager freeOfferManager;
  private IFreeOfferCategoryManager freeOfferCategoryManager;
  private IMemberLvManager memberLvManager;

  public String list()
    throws Exception
  {
    this.webpage = this.freeOfferManager.list(this.name, this.order, getPage(), getPageSize());

    return "list";
  }

  public String trash_list()
  {
    this.webpage = this.freeOfferManager.pageTrash(this.name, this.order, getPage(), getPageSize());

    return "trash_list";
  }

  public String add() {
    this.categoryList = this.freeOfferCategoryManager.getFreeOfferCategoryList();
    this.member_lvList = this.memberLvManager.list();

    return "add";
  }

  public String edit() {
    this.categoryList = this.freeOfferCategoryManager.getFreeOfferCategoryList();
    this.member_lvList = this.memberLvManager.list();
    this.freeOffer = this.freeOfferManager.get(this.fo_id.intValue());
    return "edit";
  }

  public String save()
  {
    if (this.pic != null) {
      if (FileUtil.isAllowUp(this.picFileName)) {
        String[] path = UploadUtil.upload(this.pic, this.picFileName, "gift", 120, 120);
        this.freeOffer.setPic(path[0]);
        this.freeOffer.setList_thumb(path[1]);
      } else {
        this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
        return "message";
      }
    }

    this.freeOffer.setLv_ids(StringUtil.arrayToString(this.lv_ids, ","));
    this.freeOffer.setStartdate(Long.valueOf(this.mstartdate.getTime()));
    this.freeOffer.setEnddate(Long.valueOf(this.menddate.getTime()));
    this.freeOffer.setDisabled(Integer.valueOf(0));
    this.freeOfferManager.saveAdd(this.freeOffer);
    this.msgs.add("赠品添加成功");
    this.urls.put("赠品列表", "freeOffer!list.do");
    return "message";
  }

  public String saveEdit()
  {
    if (this.pic != null) {
      if (FileUtil.isAllowUp(this.picFileName))
      {
        String[] path = UploadUtil.upload(this.pic, this.picFileName, "gift", 120, 120);
        this.freeOffer.setPic(path[0]);
        this.freeOffer.setList_thumb(path[1]);
        UploadUtil.deleteFileAndThumb(this.oldpic);
      }
      else {
        this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
        return "message";
      }
    }
    this.freeOffer.setLv_ids(StringUtil.arrayToString(this.lv_ids, ","));
    this.freeOffer.setStartdate(Long.valueOf(this.mstartdate.getTime()));
    this.freeOffer.setEnddate(Long.valueOf(this.menddate.getTime()));
    this.freeOfferManager.update(this.freeOffer);
    this.msgs.add("赠品修改成功");
    this.urls.put("赠品列表", "freeOffer!list.do");
    return "message";
  }

  public String delete()
  {
    try
    {
      this.freeOfferManager.delete(this.id);
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
      this.freeOfferManager.revert(this.id);
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
      this.freeOfferManager.clean(this.id);
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

  public IFreeOfferManager getFreeOfferManager() {
    return this.freeOfferManager;
  }

  public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
    this.freeOfferManager = freeOfferManager;
  }

  public FreeOffer getFreeOffer() {
    return this.freeOffer;
  }

  public void setFreeOffer(FreeOffer freeOffer) {
    this.freeOffer = freeOffer;
  }

  public Integer getFo_id() {
    return this.fo_id;
  }

  public void setFo_id(Integer foId) {
    this.fo_id = foId;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getMstartdate() {
    return this.mstartdate;
  }

  public void setMstartdate(Date mstartdate) {
    this.mstartdate = mstartdate;
  }

  public Date getMenddate() {
    return this.menddate;
  }

  public void setMenddate(Date menddate) {
    this.menddate = menddate;
  }

  public File getThumb() {
    return this.thumb;
  }

  public void setThumb(File thumb) {
    this.thumb = thumb;
  }

  public String getThumbFileName() {
    return this.thumbFileName;
  }

  public void setThumbFileName(String thumbFileName) {
    this.thumbFileName = thumbFileName;
  }

  public File getPic() {
    return this.pic;
  }

  public void setPic(File pic) {
    this.pic = pic;
  }

  public String getPicFileName() {
    return this.picFileName;
  }

  public void setPicFileName(String picFileName) {
    this.picFileName = picFileName;
  }

  public String getOldthumb() {
    return this.oldthumb;
  }

  public void setOldthumb(String oldthumb) {
    this.oldthumb = oldthumb;
  }

  public String getOldpic() {
    return this.oldpic;
  }

  public void setOldpic(String oldpic) {
    this.oldpic = oldpic;
  }

  public IFreeOfferCategoryManager getFreeOfferCategoryManager() {
    return this.freeOfferCategoryManager;
  }

  public void setFreeOfferCategoryManager(IFreeOfferCategoryManager freeOfferCategoryManager)
  {
    this.freeOfferCategoryManager = freeOfferCategoryManager;
  }

  public List getCategoryList() {
    return this.categoryList;
  }

  public void setCategoryList(List categoryList) {
    this.categoryList = categoryList;
  }

  public List getMember_lvList() {
    return this.member_lvList;
  }

  public void setMember_lvList(List memberLvList) {
    this.member_lvList = memberLvList;
  }

  public Integer[] getLv_ids() {
    return this.lv_ids;
  }

  public void setLv_ids(Integer[] lvIds) {
    this.lv_ids = lvIds;
  }

  public IMemberLvManager getMemberLvManager() {
    return this.memberLvManager;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager) {
    this.memberLvManager = memberLvManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.FreeOfferAction
 * JD-Core Version:    0.6.1
 */