package com.enation.app.base.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class Member
  implements Serializable
{
  private Integer member_id;
  private Integer lv_id;
  private String uname;
  private String email;
  private String password;
  private Long regtime;
  private String pw_answer;
  private String pw_question;
  private String name;
  private Integer sex;
  private Long birthday;
  private Double advance;
  private Integer province_id;
  private Integer city_id;
  private Integer region_id;
  private String province;
  private String city;
  private String region;
  private String address;
  private String zip;
  private String mobile;
  private String tel;
  private int info_full;
  private int recommend_point_state;
  private Integer point;
  private String qq;
  private String msn;
  private String remark;
  private Long lastlogin;
  private Integer logincount;
  private Integer mp;
  private String lvname;
  private Integer parentid;
  private Integer agentid;
  private Integer is_cheked;
  private String registerip;
  private String nickname;
  private String face;
  private Integer midentity;
  private Integer last_send_email;
  private String find_code;

  @PrimaryKeyField
  public Integer getMember_id()
  {
    return this.member_id;
  }

  public void setMember_id(Integer memberId) {
    this.member_id = memberId;
  }

  public Integer getLv_id()
  {
    return this.lv_id;
  }

  public void setLv_id(Integer lvId) {
    this.lv_id = lvId;
  }

  public String getUname() {
    return this.uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getRegtime() {
    return this.regtime;
  }

  public void setRegtime(Long regtime) {
    this.regtime = regtime;
  }

  public String getPw_answer() {
    return this.pw_answer;
  }

  public void setPw_answer(String pwAnswer) {
    this.pw_answer = pwAnswer;
  }

  public String getPw_question() {
    return this.pw_question;
  }

  public void setPw_question(String pwQuestion) {
    this.pw_question = pwQuestion;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getSex() {
    this.sex = Integer.valueOf(this.sex == null ? 0 : this.sex.intValue());
    return this.sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public Long getBirthday() {
    return this.birthday;
  }

  public void setBirthday(Long birthday) {
    this.birthday = birthday;
  }

  public Double getAdvance() {
    return this.advance;
  }

  public void setAdvance(Double advance) {
    this.advance = advance;
  }

  public Integer getProvince_id()
  {
    return this.province_id;
  }

  public void setProvince_id(Integer provinceId) {
    this.province_id = provinceId;
  }

  public Integer getCity_id() {
    return this.city_id;
  }

  public void setCity_id(Integer cityId) {
    this.city_id = cityId;
  }

  public Integer getRegion_id() {
    return this.region_id;
  }

  public void setRegion_id(Integer regionId) {
    this.region_id = regionId;
  }

  public String getProvince() {
    return this.province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getRegion() {
    return this.region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getZip() {
    return this.zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getMobile() {
    return this.mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public Integer getPoint() {
    if (this.point == null)
      this.point = Integer.valueOf(0);
    return this.point;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }

  public String getQq() {
    return this.qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getMsn() {
    return this.msn;
  }

  public void setMsn(String msn) {
    this.msn = msn;
  }

  public Integer getMidentity() {
    return this.midentity;
  }

  public void setMidentity(Integer midentity) {
    this.midentity = midentity;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @NotDbField
  public String getLvname() {
    return this.lvname;
  }

  public void setLvname(String lvname) {
    this.lvname = lvname;
  }

  public Long getLastlogin() {
    return this.lastlogin;
  }

  public void setLastlogin(Long lastlogin) {
    this.lastlogin = lastlogin;
  }

  public Integer getMp() {
    return this.mp;
  }

  public void setMp(Integer mp) {
    this.mp = mp;
  }

  public Integer getParentid() {
    return this.parentid;
  }

  public void setParentid(Integer parentid) {
    this.parentid = parentid;
  }

  public Integer getIs_cheked() {
    return this.is_cheked;
  }

  public void setIs_cheked(Integer is_cheked) {
    this.is_cheked = is_cheked;
  }

  public Integer getLogincount() {
    return this.logincount;
  }

  public void setLogincount(Integer logincount) {
    this.logincount = logincount;
  }

  public Integer getAgentid() {
    return this.agentid;
  }

  public void setAgentid(Integer agentid) {
    this.agentid = agentid;
  }

  public String getRegisterip() {
    return this.registerip;
  }

  public void setRegisterip(String registerip) {
    this.registerip = registerip;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getFace() {
    return this.face;
  }

  public void setFace(String face) {
    this.face = face;
  }

  public String getFind_code()
  {
    return this.find_code;
  }

  public void setFind_code(String find_code) {
    this.find_code = find_code;
  }

  public Integer getLast_send_email() {
    return this.last_send_email;
  }

  public void setLast_send_email(Integer last_send_email) {
    this.last_send_email = last_send_email;
  }

  public int getInfo_full() {
    return this.info_full;
  }

  public void setInfo_full(int info_full) {
    this.info_full = info_full;
  }

  public int getRecommend_point_state() {
    return this.recommend_point_state;
  }

  public void setRecommend_point_state(int recommend_point_state) {
    this.recommend_point_state = recommend_point_state;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.Member
 * JD-Core Version:    0.6.1
 */