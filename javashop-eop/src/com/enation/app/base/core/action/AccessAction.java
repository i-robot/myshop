package com.enation.app.base.core.action;

import com.enation.app.base.core.service.IAccessRecorder;
import com.enation.eop.resource.model.Link;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.action.WWAction;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AccessAction extends WWAction
{
  private IAccessRecorder accessRecorder;
  private int startday;
  private int endday;
  private String runmodel;
  private Map sData;
  private String daytime;
  private String ip;
  private List accessList;
  private List<Link> linkList;

  public String list()
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    int year = cal.get(1);
    int month = cal.get(2) + 1;

    String starttime = null;
    String endtime = null;

    if (this.startday != 0) {
      starttime = year + "-" + month + "-" + this.startday;
    }
    if (this.endday != 0) {
      endtime = year + "-" + month + "-" + this.endday;
    }

    this.webpage = this.accessRecorder.list(starttime, endtime, getPage(), 50);

    this.sData = this.accessRecorder.census();
    this.runmodel = EopSetting.RUNMODE;
    return "list";
  }

  public String detaillist()
  {
    this.accessList = this.accessRecorder.detaillist(this.ip, this.daytime);
    this.runmodel = EopSetting.RUNMODE;
    return "detaillist";
  }

  public String history()
  {
    this.linkList = new ArrayList();
    String target = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/access";

    File file = new File(target);
    if (file.exists()) {
      String[] reportList = file.list();
      for (String name : reportList) {
        Link link = new Link();
        link.setLink(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath() + "/access/" + name);

        link.setText(name);
        this.linkList.add(link);
      }
    }
    return "history";
  }

  public IAccessRecorder getAccessRecorder() {
    return this.accessRecorder;
  }

  public void setAccessRecorder(IAccessRecorder accessRecorder) {
    this.accessRecorder = accessRecorder;
  }

  public int getStartday() {
    return this.startday;
  }

  public void setStartday(int startday) {
    this.startday = startday;
  }

  public int getEndday() {
    return this.endday;
  }

  public void setEndday(int endday) {
    this.endday = endday;
  }

  public List<Link> getLinkList() {
    return this.linkList;
  }

  public void setLinkList(List<Link> linkList) {
    this.linkList = linkList;
  }

  public String getDaytime() {
    return this.daytime;
  }

  public void setDaytime(String daytime) {
    this.daytime = daytime;
  }

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public List getAccessList() {
    return this.accessList;
  }

  public void setAccessList(List accessList) {
    this.accessList = accessList;
  }

  public String getRunmodel() {
    return this.runmodel;
  }

  public void setRunmodel(String runmodel) {
    this.runmodel = runmodel;
  }

  public Map getsData() {
    return this.sData;
  }

  public void setsData(Map sData) {
    this.sData = sData;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.AccessAction
 * JD-Core Version:    0.6.1
 */