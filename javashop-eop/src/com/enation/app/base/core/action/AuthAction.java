package com.enation.app.base.core.action;

import com.enation.app.base.core.service.auth.IAuthActionManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.StringUtil;
import org.apache.log4j.Logger;

public class AuthAction extends WWAction
{
  private String name;
  private int authid;
  private int isEdit;
  private IAuthActionManager authActionManager;
  private Integer[] menuids;
  private com.enation.app.base.core.model.AuthAction auth;

  public String add()
  {
    this.isEdit = 0;
    return "input";
  }

  public String edit() {
    this.isEdit = 1;
    this.auth = this.authActionManager.get(this.authid);
    return "input";
  }

  public String save() {
    if (this.isEdit == 0) {
      return saveAdd();
    }
    return saveEdit();
  }

  public String saveEdit()
  {
    try
    {
      com.enation.app.base.core.model.AuthAction act = new com.enation.app.base.core.model.AuthAction();
      act.setName(this.name);
      act.setType("menu");
      act.setActid(Integer.valueOf(this.authid));
      act.setObjvalue(StringUtil.arrayToString(this.menuids, ","));
      this.authActionManager.edit(act);
      this.json = ("{result:1,authid:'" + this.authid + "'}");
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e.fillInStackTrace());
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String saveAdd() {
    try {
      com.enation.app.base.core.model.AuthAction act = new com.enation.app.base.core.model.AuthAction();
      act.setName(this.name);
      act.setType("menu");
      act.setObjvalue(StringUtil.arrayToString(this.menuids, ","));
      this.authid = this.authActionManager.add(act);
      this.json = ("{result:1,authid:'" + this.authid + "'}");
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e.fillInStackTrace());
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String delete() {
    try {
      this.authActionManager.delete(this.authid);
      this.json = ("{result:1,authid:'" + this.authid + "'}");
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e.fillInStackTrace());
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAuthid() {
    return this.authid;
  }

  public void setAuthid(int authid) {
    this.authid = authid;
  }

  public int getIsEdit() {
    return this.isEdit;
  }

  public void setIsEdit(int isEdit) {
    this.isEdit = isEdit;
  }

  public IAuthActionManager getAuthActionManager() {
    return this.authActionManager;
  }

  public void setAuthActionManager(IAuthActionManager authActionManager) {
    this.authActionManager = authActionManager;
  }

  public Integer[] getMenuids() {
    return this.menuids;
  }

  public void setMenuids(Integer[] menuids) {
    this.menuids = menuids;
  }

  public com.enation.app.base.core.model.AuthAction getAuth() {
    return this.auth;
  }

  public void setAuth(com.enation.app.base.core.model.AuthAction auth) {
    this.auth = auth;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.AuthAction
 * JD-Core Version:    0.6.1
 */