package com.enation.app.base.core.action;

import com.enation.app.base.core.service.solution.ISolutionExporter;
import com.enation.app.base.core.service.solution.ISolutionImporter;
import com.enation.eop.resource.IDomainManager;
import com.enation.eop.resource.ISiteManager;
import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopSiteDomain;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.action.WWAction;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class UserSiteAction extends WWAction
{
  private ISiteManager siteManager;
  private IDomainManager domainManager;
  private EopSite eopSite;
  private Integer id;
  private Integer domainid;
  private Integer statusid;
  private File cologo;
  private String cologoFileName;
  private File ico;
  private String icoFileName;
  private File bklogo;
  private String bklogoFileName;
  private File bkloginpic;
  private String bkloginpicFileName;
  private List<EopSiteDomain> siteDomainList;
  private EopSiteDomain eopSiteDomain;
  private String sitedomain;
  private IUserManager userManager;
  private ISolutionImporter solutionImporter;
  private ISolutionExporter solutionExporter;
  private int exportData;
  private int exportTheme;
  private int exportProfile;
  private int exportAttr;
  private String name;
  private File zip;
  private String zipFileName;
  private Integer siteid;
  private String vcode;
  private String order;
  private String search;
  private Integer managerid;

  public String toExport()
  {
    return "export";
  }

  public String toImport() {
    return "import";
  }

  public String backup()
  {
    try
    {
      this.name = ("backup_" + System.currentTimeMillis());
      this.solutionExporter.export(this.name, true, true, true, true);
      this.json = ("{result:1,name:'" + this.name + "'}");
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String restore()
  {
    try
    {
      String productid = "temp_" + System.currentTimeMillis();

      String zipPath = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/backup/" + this.name + ".zip";

      this.solutionImporter.imported(productid, zipPath, true);
      this.json = "{result:1}";
    } catch (Exception e) {
      e.printStackTrace();
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String export()
  {
    try {
      this.solutionExporter.export(this.name, this.exportData == 1, this.exportTheme == 1, this.exportProfile == 1, this.exportAttr == 1);

      this.json = ("{result:1,path:'" + EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath() + "/backup/" + this.name + ".zip" + "'}");
    }
    catch (RuntimeException e)
    {
      e.printStackTrace();
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String imported() {
    try {
      String productid = "temp_" + System.currentTimeMillis();

      String zipPath = UploadUtil.upload(this.zip, this.zipFileName, "solution");
      String header = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath();
      header = header.replaceAll("\\\\", "/");
      zipPath = zipPath.replaceAll(EopSetting.FILE_STORE_PREFIX, header);
      this.solutionImporter.imported(productid, zipPath, true);
      this.json = "{result:1}";
    }
    catch (Exception e) {
      e.printStackTrace();
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String toInitData()
  {
    return "init_data";
  }

  public String initData()
  {
    try
    {
      WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();

      Object realCode = sessonContext.getAttribute("valid_codeinitdata");

      if (!this.vcode.equals(realCode)) {
        this.json = "{result:0,message:'验证码输入错误'}";
        return "json_message";
      }
      this.siteManager.initData();
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public IUserManager getUserManager() {
    return this.userManager;
  }

  public void setUserManager(IUserManager userManager) {
    this.userManager = userManager;
  }

  public EopSiteDomain getEopSiteDomain() {
    return this.eopSiteDomain;
  }

  public void setEopSiteDomain(EopSiteDomain eopSiteDomain) {
    this.eopSiteDomain = eopSiteDomain;
  }

  public List<EopSiteDomain> getSiteDomainList() {
    return this.siteDomainList;
  }

  public void setSiteDomainList(List<EopSiteDomain> siteDomainList) {
    this.siteDomainList = siteDomainList;
  }

  public File getIco() {
    return this.ico;
  }

  public void setIco(File ico) {
    this.ico = ico;
  }

  public String getIcoFileName() {
    return this.icoFileName;
  }

  public void setIcoFileName(String icoFileName) {
    this.icoFileName = icoFileName;
  }

  public String getCologoFileName() {
    return this.cologoFileName;
  }

  public void setCologoFileName(String cologoFileName) {
    this.cologoFileName = cologoFileName;
  }

  public File getCologo() {
    return this.cologo;
  }

  public void setCologo(File cologo) {
    this.cologo = cologo;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public EopSite getEopSite() {
    return this.eopSite;
  }

  public void setEopSite(EopSite eopSite) {
    this.eopSite = eopSite;
  }

  public String execute()
  {
    this.webpage = this.siteManager.list(getPage(), getPageSize(), this.order, this.search);

    return "success";
  }

  public String add() {
    return "add";
  }

  public String edit() {
    IUserService userService = UserServiceFactory.getUserService();
    this.eopSite = this.siteManager.get(userService.getCurrentSiteId());

    return "edit";
  }

  public String edit_multi() {
    IUserService userService = UserServiceFactory.getUserService();
    this.eopSite = this.siteManager.get(userService.getCurrentSiteId());
    return "edit_multi";
  }

  public String domainlist() {
    this.siteDomainList = this.domainManager.listSiteDomain();
    return "domainlist";
  }

  public String domain() {
    IUserService userService = UserServiceFactory.getUserService();
    this.eopSite = this.siteManager.get(userService.getCurrentSiteId());
    this.siteDomainList = this.domainManager.listSiteDomain();
    return "domain";
  }

  public String deleteDomain() {
    try {
      this.siteManager.deleteDomain(this.domainid);
      this.json = "{result:1,message:'删除成功'}";
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e);
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String editdomain() throws Exception {
    if (this.statusid.intValue() == 0)
      this.statusid = Integer.valueOf(1);
    else {
      this.statusid = Integer.valueOf(0);
    }

    this.eopSiteDomain = new EopSiteDomain();
    this.eopSiteDomain.setStatus(this.statusid);
    this.eopSiteDomain.setId(this.domainid);
    try
    {
      this.domainManager.edit(this.eopSiteDomain);
      this.json = "{result:1,message:'修改成功'}";
    } catch (RuntimeException e) {
      this.logger.error(e.getStackTrace());
      this.json = "{result:0,message:'修改失败'}";
    }

    return "json_message";
  }

  public String editSave()
    throws Exception
  {
    try
    {
      if (this.cologo != null) {
        String logo = UploadUtil.upload(this.cologo, this.cologoFileName, "user");
        this.eopSite.setLogofile(logo);
      }

      if (this.ico != null) {
        String icoPath = UploadUtil.upload(this.ico, this.icoFileName, "user");
        this.eopSite.setIcofile(icoPath);
      }

      if (this.bklogo != null) {
        String logo = UploadUtil.upload(this.bklogo, this.bklogoFileName, "user");
        this.eopSite.setBklogofile(logo);
      }

      if (this.bkloginpic != null) {
        String loginpic = UploadUtil.upload(this.bkloginpic, this.bkloginpicFileName, "user");

        this.eopSite.setBkloginpicfile(loginpic);
      }

      this.eopSite.setQq(Integer.valueOf(this.eopSite.getQq() == null ? 0 : this.eopSite.getQq().intValue()));
      this.eopSite.setMsn(Integer.valueOf(this.eopSite.getMsn() == null ? 0 : this.eopSite.getMsn().intValue()));
      this.eopSite.setWw(Integer.valueOf(this.eopSite.getWw() == null ? 0 : this.eopSite.getWw().intValue()));
      this.eopSite.setTel(Integer.valueOf(this.eopSite.getTel() == null ? 0 : this.eopSite.getTel().intValue()));
      this.eopSite.setWt(Integer.valueOf(this.eopSite.getWt() == null ? 0 : this.eopSite.getWt().intValue()));

      this.siteManager.edit(this.eopSite);

      this.msgs.add("修改成功");
      this.urls.put("我的站点", "userSite!edit.do");
    } catch (RuntimeException e) {
      this.logger.error("修改站点", e);
      this.msgs.add(e.getMessage());
      this.urls.put("我的站点", "userSite!edit.do");
    }
    return "message";
  }

  public String delete() throws Exception {
    try {
      this.siteManager.delete(this.id);
      this.msgs.add("删除成功");
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.msgs.add(e.getMessage());
    }
    this.urls.put("站点列表", "userSite.do");
    return "message";
  }

  public String adddomain() throws Exception {
    return "adddomain";
  }

  public String addDomainSave() throws Exception {
    Integer userid = EopContext.getContext().getCurrentSite().getUserid();
    this.eopSiteDomain = new EopSiteDomain();
    this.eopSiteDomain.setUserid(userid);
    this.eopSiteDomain.setDomain(this.sitedomain);
    this.eopSiteDomain.setSiteid(this.siteid);
    int result = -1;
    try {
      result = this.siteManager.addDomain(this.eopSiteDomain);
    } catch (RuntimeException e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
      return "json_message";
    }

    if (result > 0)
      this.json = "{result:1,message:'增加成功'}";
    else {
      this.json = "{result:0,message:'新增域名失败'}";
    }

    return "json_message";
  }

  public String changeDefaultSite()
    throws Exception
  {
    Integer userid = EopContext.getContext().getCurrentSite().getUserid();
    this.userManager.changeDefaultSite(userid, this.managerid, this.id);

    return "message";
  }

  public Integer getDomainid() {
    return this.domainid;
  }

  public void setDomainid(Integer domainid) {
    this.domainid = domainid;
  }

  public Integer getStatusid() {
    return this.statusid;
  }

  public void setStatusid(Integer statusid) {
    this.statusid = statusid;
  }

  public String getSitedomain() {
    return this.sitedomain;
  }

  public void setSitedomain(String sitedomain) {
    this.sitedomain = sitedomain;
  }

  public ISiteManager getSiteManager() {
    return this.siteManager;
  }

  public void setSiteManager(ISiteManager siteManager) {
    this.siteManager = siteManager;
  }

  public Integer getSiteid() {
    return this.siteid;
  }

  public void setSiteid(Integer siteid) {
    this.siteid = siteid;
  }

  public IDomainManager getDomainManager() {
    return this.domainManager;
  }

  public void setDomainManager(IDomainManager domainManager) {
    this.domainManager = domainManager;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public String getSearch() {
    return this.search;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  public Integer getManagerid() {
    return this.managerid;
  }

  public void setManagerid(Integer managerid) {
    this.managerid = managerid;
  }

  public File getBklogo() {
    return this.bklogo;
  }

  public void setBklogo(File bklogo) {
    this.bklogo = bklogo;
  }

  public String getBklogoFileName() {
    return this.bklogoFileName;
  }

  public void setBklogoFileName(String bklogoFileName) {
    this.bklogoFileName = bklogoFileName;
  }

  public File getBkloginpic() {
    return this.bkloginpic;
  }

  public void setBkloginpic(File bkloginpic) {
    this.bkloginpic = bkloginpic;
  }

  public String getBkloginpicFileName() {
    return this.bkloginpicFileName;
  }

  public void setBkloginpicFileName(String bkloginpicFileName) {
    this.bkloginpicFileName = bkloginpicFileName;
  }

  public String getVcode() {
    return this.vcode;
  }

  public void setVcode(String vcode) {
    this.vcode = vcode;
  }

  public int getExportData() {
    return this.exportData;
  }

  public void setExportData(int exportData) {
    this.exportData = exportData;
  }

  public int getExportTheme() {
    return this.exportTheme;
  }

  public void setExportTheme(int exportTheme) {
    this.exportTheme = exportTheme;
  }

  public int getExportProfile() {
    return this.exportProfile;
  }

  public void setExportProfile(int exportProfile) {
    this.exportProfile = exportProfile;
  }

  public int getExportAttr() {
    return this.exportAttr;
  }

  public void setExportAttr(int exportAttr) {
    this.exportAttr = exportAttr;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public File getZip() {
    return this.zip;
  }

  public void setZip(File zip) {
    this.zip = zip;
  }

  public String getZipFileName() {
    return this.zipFileName;
  }

  public void setZipFileName(String zipFileName) {
    this.zipFileName = zipFileName;
  }

  public ISolutionImporter getSolutionImporter() {
    return this.solutionImporter;
  }

  public void setSolutionImporter(ISolutionImporter solutionImporter) {
    this.solutionImporter = solutionImporter;
  }

  public ISolutionExporter getSolutionExporter() {
    return this.solutionExporter;
  }

  public void setSolutionExporter(ISolutionExporter solutionExporter) {
    this.solutionExporter = solutionExporter;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.UserSiteAction
 * JD-Core Version:    0.6.1
 */