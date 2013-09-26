package com.enation.app.base.core.action;

import com.enation.app.base.core.model.AdColumn;
import com.enation.app.base.core.model.Adv;
import com.enation.app.base.core.service.IAdColumnManager;
import com.enation.app.base.core.service.IAdvManager;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.FileUtil;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AdvAction extends WWAction
{
  private IAdColumnManager adColumnManager;
  private IAdvManager advManager;
  private List<AdColumn> adColumnList;
  private Adv adv;
  private Long acid;
  private String advname;
  private Long advid;
  private String id;
  private File pic;
  private String picFileName;
  private Date mstartdate;
  private Date menddate;
  private String order;

  public String list()
  {
    this.adColumnList = this.adColumnManager.listAllAdvPos();
    this.webpage = this.advManager.search(this.acid, this.advname, getPage(), getPageSize(), this.order);
    return "list";
  }

  public String detail() {
    this.adv = this.advManager.getAdvDetail(this.advid);
    return "detail";
  }

  public String click() {
    if (this.advid.longValue() == 0L) {
      getRequest().setAttribute("gourl", "/eop/shop/adv/zhaozu.jsp");
    } else {
      this.adv = this.advManager.getAdvDetail(this.advid);

      if (getRequest().getSession().getAttribute("AD" + this.advid.toString()) == null)
      {
        getRequest().getSession().setAttribute("AD" + this.advid.toString(), "clicked");

        this.adv.setClickcount(Integer.valueOf(this.adv.getClickcount().intValue() + 1));
        this.advManager.updateAdv(this.adv);
      }

      getRequest().setAttribute("gourl", this.adv.getUrl());
    }
    return "go";
  }

  public String delete() {
    try {
      this.advManager.delAdvs(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String add() {
    this.adColumnList = this.adColumnManager.listAllAdvPos();
    return "add";
  }

  public String addSave() {
    if (this.pic != null)
    {
      if (FileUtil.isAllowUp(this.picFileName)) {
        String path = UploadUtil.upload(this.pic, this.picFileName, "adv");
        this.adv.setAtturl(path);
      } else {
        this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
        return "message";
      }
    }
    this.adv.setBegintime(Long.valueOf(this.mstartdate.getTime()));
    this.adv.setEndtime(Long.valueOf(this.menddate.getTime()));
    this.adv.setDisabled("false");
    try {
      this.advManager.addAdv(this.adv);
      this.msgs.add("新增广告成功");
      this.urls.put("广告列表", "adv!list.do");
    } catch (RuntimeException e) {
      this.msgs.add("新增广告失败");
      this.urls.put("广告列表", "adv!list.do");
    }
    return "message";
  }

  public String edit() {
    this.adColumnList = this.adColumnManager.listAllAdvPos();
    this.adv = this.advManager.getAdvDetail(this.advid);
    return "edit";
  }

  public String editSave() {
    if (this.pic != null) {
      if (FileUtil.isAllowUp(this.picFileName)) {
        String path = UploadUtil.upload(this.pic, this.picFileName, "adv");
        this.adv.setAtturl(path);
      } else {
        this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
        return "message";
      }
    }
    this.adv.setBegintime(Long.valueOf(this.mstartdate.getTime()));
    this.adv.setEndtime(Long.valueOf(this.menddate.getTime()));
    this.advManager.updateAdv(this.adv);
    this.msgs.add("修改广告成功");
    this.urls.put("广告列表", "adv!list.do");
    return "message";
  }

  public String stop() {
    this.adv = this.advManager.getAdvDetail(this.advid);
    this.adv.setIsclose(Integer.valueOf(1));
    try {
      this.advManager.updateAdv(this.adv);
      this.json = "{'result':0,'message':'操作成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'操作失败'}";
    }
    return "json_message";
  }

  public String start() {
    this.adv = this.advManager.getAdvDetail(this.advid);
    this.adv.setIsclose(Integer.valueOf(0));
    try {
      this.advManager.updateAdv(this.adv);
      this.json = "{'result':0,'message':'操作成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'操作失败'}";
    }
    return "json_message";
  }

  public IAdColumnManager getAdColumnManager() {
    return this.adColumnManager;
  }

  public void setAdColumnManager(IAdColumnManager adColumnManager) {
    this.adColumnManager = adColumnManager;
  }

  public IAdvManager getAdvManager() {
    return this.advManager;
  }

  public void setAdvManager(IAdvManager advManager) {
    this.advManager = advManager;
  }

  public Adv getAdv() {
    return this.adv;
  }

  public void setAdv(Adv adv) {
    this.adv = adv;
  }

  public Long getAcid() {
    return this.acid;
  }

  public void setAcid(Long acid) {
    this.acid = acid;
  }

  public Long getAdvid() {
    return this.advid;
  }

  public void setAdvid(Long advid) {
    this.advid = advid;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<AdColumn> getAdColumnList() {
    return this.adColumnList;
  }

  public void setAdColumnList(List<AdColumn> adColumnList) {
    this.adColumnList = adColumnList;
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

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public String getAdvname() {
    return this.advname;
  }

  public void setAdvname(String advname) {
    this.advname = advname;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.AdvAction
 * JD-Core Version:    0.6.1
 */