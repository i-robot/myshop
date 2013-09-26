package com.enation.app.base.core.action;

import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopUser;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.action.WWAction;
import java.io.File;
import java.util.List;
import java.util.Map;

public class BaseInfoAction extends WWAction
{
  private EopUser eopUser;
  private Integer userid;
  private IUserManager userManager;
  private File cologo;
  private String cologoFileName;
  private File license;
  private String licenseFileName;

  public File getCologo()
  {
    return this.cologo;
  }

  public void setCologo(File cologo) {
    this.cologo = cologo;
  }

  public String getCologoFileName() {
    return this.cologoFileName;
  }

  public void setCologoFileName(String cologoFileName) {
    this.cologoFileName = cologoFileName;
  }

  public File getLicense()
  {
    return this.license;
  }

  public void setLicense(File license) {
    this.license = license;
  }

  public String getLicenseFileName() {
    return this.licenseFileName;
  }

  public void setLicenseFileName(String licenseFileName) {
    this.licenseFileName = licenseFileName;
  }

  public EopUser getEopUser()
  {
    return this.eopUser;
  }

  public void setEopUser(EopUser eopUser) {
    this.eopUser = eopUser;
  }

  public String execute()
    throws Exception
  {
    this.userid = EopContext.getContext().getCurrentSite().getUserid();
    this.eopUser = this.userManager.get(this.userid);
    return "input";
  }

  public String save() throws Exception
  {
    try
    {
      if (this.cologo != null) {
        String logoPath = UploadUtil.upload(this.cologo, this.cologoFileName, "user");

        this.eopUser.setLogofile(logoPath);
      }

      if (this.license != null)
      {
        String licensePath = UploadUtil.upload(this.license, this.licenseFileName, "user");

        this.eopUser.setLicensefile(licensePath);
      }

      this.userManager.edit(this.eopUser);
      this.msgs.add("修改成功");
    }
    catch (RuntimeException e) {
      this.msgs.add(e.getMessage());
    }
    this.urls.put("用户信息页面", "baseInfo.do");
    return "message";
  }

  public Integer getUserid() {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public IUserManager getUserManager() {
    return this.userManager;
  }

  public void setUserManager(IUserManager userManager) {
    this.userManager = userManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.BaseInfoAction
 * JD-Core Version:    0.6.1
 */