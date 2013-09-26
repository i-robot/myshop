package com.enation.app.base.core.action;

import com.enation.app.base.core.model.Smtp;
import com.enation.app.base.core.service.ISmtpManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class SmtpAction extends WWAction
{
  private ISmtpManager smtpManager;
  private Smtp smtp;
  private Integer[] idAr;
  private int id;
  private int isedit;
  private List<Smtp> smtpList;

  public String add()
  {
    this.isedit = 0;
    return "input";
  }

  public String edit() {
    this.isedit = 1;
    this.smtp = this.smtpManager.get(this.id);
    return "input";
  }

  public String saveAdd()
  {
    try
    {
      this.smtpManager.add(this.smtp);
      this.msgs.add("smtp添加成功");
      this.urls.put("smtp列表", "smtp!list.do");
    } catch (RuntimeException e) {
      this.logger.error("smtp修改失败", e);
      this.msgs.add("smtp添加失败" + e.getMessage());
    }

    return "message";
  }

  public String saveEdit()
  {
    try {
      this.smtpManager.edit(this.smtp);
      this.msgs.add("smtp修改成功");
      this.urls.put("smtp列表", "smtp!list.do");
    } catch (RuntimeException e) {
      this.logger.error("smtp修改失败", e);
      this.msgs.add("smtp修改失败" + e.getMessage());
    }

    return "message";
  }

  public String list()
  {
    this.smtpList = this.smtpManager.list();
    return "list";
  }

  public String delete() {
    try {
      this.smtpManager.delete(this.idAr);
      this.json = "{\"result\":0,\"message\":\"smtp删除成功\"}";
    } catch (RuntimeException e) {
      this.logger.error("smtp删除失败", e);
      showSuccessJson("smtp删除失败[" + e.getMessage() + "]");
    }
    return "json_message";
  }

  public ISmtpManager getSmtpManager() {
    return this.smtpManager;
  }

  public void setSmtpManager(ISmtpManager smtpManager) {
    this.smtpManager = smtpManager;
  }

  public Smtp getSmtp() {
    return this.smtp;
  }

  public void setSmtp(Smtp smtp) {
    this.smtp = smtp;
  }

  public Integer[] getIdAr() {
    return this.idAr;
  }

  public void setIdAr(Integer[] idAr) {
    this.idAr = idAr;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIsedit() {
    return this.isedit;
  }

  public void setIsedit(int isedit) {
    this.isedit = isedit;
  }

  public List<Smtp> getSmtpList() {
    return this.smtpList;
  }

  public void setSmtpList(List<Smtp> smtpList) {
    this.smtpList = smtpList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.SmtpAction
 * JD-Core Version:    0.6.1
 */