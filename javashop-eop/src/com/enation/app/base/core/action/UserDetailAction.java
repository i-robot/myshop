package com.enation.app.base.core.action;

import com.enation.eop.resource.IUserDetailManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopUserDetail;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class UserDetailAction extends WWAction
{
  private EopUserDetail eopUserDetail;
  private IUserDetailManager userDetailManager;
  private Integer userid;

  public String execute()
    throws Exception
  {
    this.userid = EopContext.getContext().getCurrentSite().getUserid();
    this.eopUserDetail = this.userDetailManager.get(this.userid);
    return "input";
  }

  public String save() throws Exception {
    try {
      this.userDetailManager.edit(this.eopUserDetail);
      this.msgs.add("修改成功");
    } catch (RuntimeException e) {
      this.msgs.add(e.getMessage());
    }

    this.urls.put("用户信息页面", "userDetail.do");

    return "message";
  }

  public EopUserDetail getEopUserDetail() {
    return this.eopUserDetail;
  }

  public void setEopUserDetail(EopUserDetail eopUserDetail) {
    this.eopUserDetail = eopUserDetail;
  }

  public IUserDetailManager getUserDetailManager() {
    return this.userDetailManager;
  }

  public void setUserDetailManager(IUserDetailManager userDetailManager) {
    this.userDetailManager = userDetailManager;
  }

  public Integer getUserid() {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.UserDetailAction
 * JD-Core Version:    0.6.1
 */