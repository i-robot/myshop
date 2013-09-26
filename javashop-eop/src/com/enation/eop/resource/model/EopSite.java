package com.enation.eop.resource.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.PrintStream;
import java.io.Serializable;

public class EopSite
  implements Serializable
{
  private static final long serialVersionUID = 7525130003L;
  private Integer id;
  private Integer userid;
  private String sitename;
  private String title;
  private String keywords;
  private String descript;
  private String username;
  private String usertel;
  private String usermobile;
  private String usertel1;
  private Integer usersex;
  private String useremail;
  private Integer state;
  private String qqlist;
  private String msnlist;
  private String wwlist;
  private String tellist;
  private String worktime;
  private Integer qq;
  private Integer msn;
  private Integer ww;
  private Integer tel;
  private Integer wt;
  private Integer siteon;
  private String closereson;
  private String copyright;
  private String icp;
  private String address;
  private String zipcode;
  private String linkman;
  private String linktel;
  private String email;
  private String productid;
  private Integer themeid;
  private String themepath;
  private Integer adminthemeid;
  private String icofile;
  private String logofile;
  private Long createtime;
  private Long lastlogin;
  private long lastgetpoint;
  private String bklogofile;
  private String bkloginpicfile;
  private int logincount;
  private int point;
  private String relid;
  private int sitestate;
  private int isimported;
  private int imptype;
  private Integer multi_site;

  public String getKeywords()
  {
    return this.keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  @PrimaryKeyField
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserid() {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public String getSitename() {
    return this.sitename;
  }

  public void setSitename(String sitename) {
    this.sitename = sitename;
  }

  public String getDescript() {
    return this.descript;
  }

  public void setDescript(String descript) {
    this.descript = descript;
  }

  public Integer getThemeid() {
    return this.themeid;
  }

  public void setThemeid(Integer themeid) {
    this.themeid = themeid;
  }

  public Integer getAdminthemeid() {
    return this.adminthemeid;
  }

  public void setAdminthemeid(Integer adminthemeid) {
    this.adminthemeid = adminthemeid;
  }

  public String getIcofile() {
    return this.icofile;
  }

  public void setIcofile(String icofile) {
    this.icofile = icofile;
  }

  public String getLogofile() {
    return this.logofile;
  }

  public void setLogofile(String logofile) {
    this.logofile = logofile;
  }

  public String getProductid() {
    return this.productid;
  }

  public void setProductid(String productid) {
    this.productid = productid;
  }

  public String getThemepath()
  {
    return this.themepath;
  }

  public void setThemepath(String themepath) {
    this.themepath = themepath;
  }

  public int getPoint() {
    return this.point;
  }

  public void setPoint(int point) {
    this.point = point;
  }
  public String getBklogofile() {
    return this.bklogofile;
  }

  public void setBklogofile(String bklogofile) {
    this.bklogofile = bklogofile;
  }

  public String getBkloginpicfile() {
    return this.bkloginpicfile;
  }

  public void setBkloginpicfile(String bkloginpicfile) {
    this.bkloginpicfile = bkloginpicfile;
  }

  public EopSite clone() {
    EopSite site = new EopSite();
    site.setAdminthemeid(this.adminthemeid);
    site.setDescript(this.descript);
    site.setIcofile(this.icofile);
    site.setLogofile(this.logofile);
    site.setId(this.id);
    site.setKeywords(this.keywords);
    site.setPoint(this.point);
    site.setProductid(this.productid);
    site.setThemeid(this.themeid);
    site.setThemepath(this.themepath);
    site.setSitename(this.sitename);
    site.setUserid(this.userid);

    return site;
  }

  public Long getCreatetime()
  {
    return this.createtime;
  }

  public void setCreatetime(Long createtime) {
    this.createtime = createtime;
  }

  public Long getLastlogin() {
    return this.lastlogin;
  }

  public void setLastlogin(Long lastlogin) {
    this.lastlogin = lastlogin;
  }

  public long getLastgetpoint() {
    return this.lastgetpoint;
  }

  public void setLastgetpoint(long lastgetpoint) {
    this.lastgetpoint = lastgetpoint;
  }

  public static void main(String[] args) {
    System.out.println(System.currentTimeMillis() / 1000L);
  }

  public int getLogincount() {
    return this.logincount;
  }

  public void setLogincount(int logincount) {
    this.logincount = logincount;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsertel() {
    return this.usertel;
  }

  public void setUsertel(String usertel) {
    this.usertel = usertel;
  }

  public String getUsermobile() {
    return this.usermobile;
  }

  public void setUsermobile(String usermobile) {
    this.usermobile = usermobile;
  }

  public String getUsertel1() {
    return this.usertel1;
  }

  public void setUsertel1(String usertel1) {
    this.usertel1 = usertel1;
  }

  public Integer getUsersex() {
    return this.usersex;
  }

  public void setUsersex(Integer usersex) {
    this.usersex = usersex;
  }

  public String getUseremail() {
    return this.useremail;
  }

  public void setUseremail(String useremail) {
    this.useremail = useremail;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getSiteon() {
    return this.siteon;
  }

  public void setSiteon(Integer siteon) {
    this.siteon = siteon;
  }

  public String getCopyright() {
    return this.copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public String getIcp() {
    return this.icp;
  }

  public void setIcp(String icp) {
    this.icp = icp;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getZipcode() {
    return this.zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public Integer getState() {
    return this.state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getQqlist() {
    return this.qqlist;
  }

  public void setQqlist(String qqlist) {
    this.qqlist = qqlist;
  }

  public String getMsnlist() {
    return this.msnlist;
  }

  public void setMsnlist(String msnlist) {
    this.msnlist = msnlist;
  }

  public String getWwlist() {
    return this.wwlist;
  }

  public void setWwlist(String wwlist) {
    this.wwlist = wwlist;
  }

  public String getTellist() {
    return this.tellist;
  }

  public void setTellist(String tellist) {
    this.tellist = tellist;
  }

  public String getWorktime() {
    return this.worktime;
  }

  public void setWorktime(String worktime) {
    this.worktime = worktime;
  }

  public Integer getQq() {
    return this.qq;
  }

  public void setQq(Integer qq) {
    this.qq = qq;
  }

  public Integer getMsn() {
    return this.msn;
  }

  public void setMsn(Integer msn) {
    this.msn = msn;
  }

  public Integer getWw() {
    return this.ww;
  }

  public void setWw(Integer ww) {
    this.ww = ww;
  }

  public Integer getTel() {
    return this.tel;
  }

  public void setTel(Integer tel) {
    this.tel = tel;
  }

  public Integer getWt() {
    return this.wt;
  }

  public void setWt(Integer wt) {
    this.wt = wt;
  }

  public String getLinkman() {
    return this.linkman;
  }

  public void setLinkman(String linkman) {
    this.linkman = linkman;
  }

  public String getLinktel() {
    return this.linktel;
  }

  public void setLinktel(String linktel) {
    this.linktel = linktel;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getClosereson() {
    return this.closereson;
  }

  public void setClosereson(String closereson) {
    this.closereson = closereson;
  }

  public Integer getMulti_site() {
    this.multi_site = (this.multi_site == null ? (this.multi_site = Integer.valueOf(0)) : this.multi_site);
    return this.multi_site;
  }

  public void setMulti_site(Integer multiSite) {
    this.multi_site = multiSite;
  }

  public String getRelid() {
    return this.relid;
  }

  public void setRelid(String relid) {
    this.relid = relid;
  }

  public int getSitestate() {
    return this.sitestate;
  }

  public void setSitestate(int sitestate) {
    this.sitestate = sitestate;
  }

  public int getIsimported() {
    return this.isimported;
  }

  public void setIsimported(int isimported) {
    this.isimported = isimported;
  }

  public int getImptype() {
    return this.imptype;
  }

  public void setImptype(int imptype) {
    this.imptype = imptype;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.EopSite
 * JD-Core Version:    0.6.1
 */