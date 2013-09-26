package com.enation.eop.resource.dto;

import com.enation.eop.processor.core.EopException;
import com.enation.eop.resource.model.AdminUser;
import com.enation.eop.resource.model.EopUser;
import com.enation.eop.resource.model.EopUserDetail;

public class UserDTO
{
  private EopUser user;
  private EopUserDetail userDetail;
  private AdminUser userAdmin;
  private SiteDTO siteDTO;
  private Integer siteid;

  public void vaild()
  {
    if (this.userAdmin == null) {
      throw new EopException("用户管理员不能为空！");
    }
    if (this.userDetail == null) {
      throw new EopException("用户详细信息不能为空！");
    }
    if (this.siteDTO == null) {
      throw new EopException("用户站点不能为空！");
    }
    this.siteDTO.vaild();
  }

  public void setUserId(Integer userid) {
    this.userDetail.setUserid(userid);
    this.userAdmin.setUserid(userid);
    this.siteDTO.setUserId(userid);
  }

  public EopUser getUser() {
    return this.user;
  }

  public void setUser(EopUser user) {
    this.user = user;
  }

  public EopUserDetail getUserDetail() {
    return this.userDetail;
  }

  public void setUserDetail(EopUserDetail userDetail) {
    this.userDetail = userDetail;
  }

  public AdminUser getUserAdmin() {
    return this.userAdmin;
  }

  public void setUserAdmin(AdminUser userAdmin) {
    this.userAdmin = userAdmin;
  }

  public SiteDTO getSiteDTO() {
    return this.siteDTO;
  }

  public void setSiteDTO(SiteDTO siteDTO) {
    this.siteDTO = siteDTO;
  }

  public Integer getSiteid() {
    return this.siteid;
  }

  public void setSiteid(Integer siteid) {
    this.siteDTO.setSiteId(siteid);
    this.siteid = siteid;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.dto.UserDTO
 * JD-Core Version:    0.6.1
 */