package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.model.MemberLv;
import com.enation.app.base.core.service.IRegionsManager;
import com.enation.app.shop.core.model.AdvanceLogs;
import com.enation.app.shop.core.model.PointHistory;
import com.enation.app.shop.core.plugin.member.MemberPluginBundle;
import com.enation.app.shop.core.service.IAdvanceLogsManager;
import com.enation.app.shop.core.service.IMemberCommentManager;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IMemberManager;
import com.enation.app.shop.core.service.IPointHistoryManager;
import com.enation.eop.resource.IUserManager;
import com.enation.eop.resource.model.EopUser;
import com.enation.framework.action.WWAction;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class MemberAction extends WWAction
{
  private IMemberManager memberManager;
  private IMemberLvManager memberLvManager;
  private IRegionsManager regionsManager;
  private IPointHistoryManager pointHistoryManager;
  private IAdvanceLogsManager advanceLogsManager;
  private IMemberCommentManager memberCommentManager;
  private MemberPluginBundle memberPluginBundle;
  private IUserManager userManager;
  private Member member;
  private MemberLv lv;
  private String birthday;
  private String order;
  private Integer lv_id;
  private Integer member_id;
  private String id;
  private List lvlist;
  private List provinceList;
  private List cityList;
  private List regionList;
  private List listPointHistory;
  private List listAdvanceLogs;
  private List listComments;
  private int point;
  private int pointtype;
  private Double modify_advance;
  private String modify_memo;
  private String object_type;
  private String name;
  private String uname;
  private List catDiscountList;
  private int[] cat_ids;
  private int[] cat_discounts;
  protected Map<Integer, String> pluginTabs;
  protected Map<Integer, String> pluginHtmls;
  private String message;

  public String add_lv()
  {
    return "add_lv";
  }

  public String edit_lv() {
    this.lv = this.memberLvManager.get(this.lv_id);

    return "edit_lv";
  }

  public String list_lv() {
    this.webpage = this.memberLvManager.list(this.order, getPage(), getPageSize());

    return "list_lv";
  }

  public String saveAddLv() {
    this.memberLvManager.add(this.lv);
    this.msgs.add("会员等级添加成功");
    this.urls.put("会员等级列表", "member!list_lv.do");
    return "message";
  }

  public String saveEditLv()
  {
    try {
      this.memberLvManager.edit(this.lv);

      this.msgs.add("会员等级修改成功");
      this.urls.put("会员等级列表", "member!list_lv.do");
    } catch (Exception e) {
      this.msgs.add("非法参数");
    }
    return "message";
  }

  public String deletelv() {
    try {
      this.memberLvManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String add_member() {
    this.lvlist = this.memberLvManager.list();
    this.provinceList = this.regionsManager.listProvince();
    return "add_member";
  }

  public String edit_member() {
    this.member = this.memberManager.get(this.member_id);
    this.lvlist = this.memberLvManager.list();
    return "edit_member";
  }

  public String memberlist() {
    this.webpage = this.memberManager.list(this.order, this.name, this.uname, getPage(), getPageSize());

    return "list_member";
  }

  public String saveEditMember() {
    if (!StringUtil.isEmpty(this.birthday)) {
      Date br = DateUtil.toDate(this.birthday, "yyyy-MM-dd");
      this.member.setBirthday(Long.valueOf(br.getTime()));
    }
    try {
      Member oldMember = this.memberManager.get(this.member.getMember_id());

      HttpServletRequest request = ThreadContextHolder.getHttpRequest();
      String province = request.getParameter("province");
      String city = request.getParameter("city");
      String region = request.getParameter("region");

      String province_id = request.getParameter("province_id");
      String city_id = request.getParameter("city_id");
      String region_id = request.getParameter("region_id");

      oldMember.setProvince(province);
      oldMember.setCity(city);
      oldMember.setRegion(region);

      if (!StringUtil.isEmpty(province_id)) {
        oldMember.setProvince_id(Integer.valueOf(StringUtil.toInt(province_id, true)));
      }

      if (!StringUtil.isEmpty(city_id)) {
        oldMember.setCity_id(Integer.valueOf(StringUtil.toInt(city_id, true)));
      }

      if (!StringUtil.isEmpty(province_id)) {
        oldMember.setRegion_id(Integer.valueOf(StringUtil.toInt(region_id, true)));
      }

      oldMember.setName(this.member.getName());
      oldMember.setSex(this.member.getSex());
      oldMember.setBirthday(this.member.getBirthday());
      oldMember.setEmail(this.member.getEmail());
      oldMember.setTel(this.member.getTel());
      oldMember.setMobile(this.member.getMobile());
      oldMember.setLv_id(this.member.getLv_id());
      oldMember.setZip(this.member.getZip());
      oldMember.setAddress(this.member.getAddress());
      oldMember.setQq(this.member.getQq());
      oldMember.setMsn(this.member.getMsn());
      oldMember.setPw_answer(this.member.getPw_answer());
      oldMember.setPw_question(this.member.getPw_question());
      this.memberManager.edit(oldMember);
      this.json = "{'result':0,'message':'修改成功'}";
    } catch (Exception e) {
      this.json = "{'result':1,'message':'修改失败'}";
      e.printStackTrace();
    }
    return "json_message";
  }

  public String delete()
  {
    try {
      this.memberManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String detail()
  {
    this.member = this.memberManager.get(this.member_id);

    this.pluginTabs = this.memberPluginBundle.getTabList(this.member);
    this.pluginHtmls = this.memberPluginBundle.getDetailHtml(this.member);

    return "detail";
  }

  public String editPoint()
  {
    this.member = this.memberManager.get(this.member_id);
    return "editPoint";
  }

  public String editSavePoint() {
    this.member = this.memberManager.get(this.member_id);
    this.member.setPoint(Integer.valueOf(this.member.getPoint().intValue() + this.point));
    PointHistory pointHistory = new PointHistory();
    pointHistory.setMember_id(this.member_id.intValue());
    pointHistory.setOperator("管理员");
    pointHistory.setPoint(this.point);
    pointHistory.setReason("operator_adjust");
    pointHistory.setTime(Long.valueOf(System.currentTimeMillis()));
    try {
      this.memberManager.edit(this.member);
      this.pointHistoryManager.addPointHistory(pointHistory);
      this.json = "{'result':0,'message':'会员积分修改成功'}";
    } catch (Exception e) {
      this.json = "{'result':1,'message':'修改失败'}";
      e.printStackTrace();
    }
    return "json_message";
  }

  public String pointLog() {
    this.listPointHistory = this.pointHistoryManager.listPointHistory(this.member_id.intValue(), this.pointtype);
    this.member = this.memberManager.get(this.member_id);
    return "pointLog";
  }

  public String advance() {
    this.member = this.memberManager.get(this.member_id);
    this.listAdvanceLogs = this.advanceLogsManager.listAdvanceLogsByMemberId(this.member_id.intValue());

    return "advance";
  }

  public String comments() {
    Page page = this.memberCommentManager.getMemberComments(1, 100, StringUtil.toInt(this.object_type), this.member_id.intValue());
    if (page != null) {
      this.listComments = ((List)page.getResult());
    }
    return "comments";
  }

  public String editSaveAdvance()
  {
    this.member = this.memberManager.get(this.member_id);
    this.member.setAdvance(Double.valueOf(this.member.getAdvance().doubleValue() + this.modify_advance.doubleValue()));
    try {
      this.memberManager.edit(this.member);
    } catch (Exception e) {
      this.json = "{'result':1, 'message':'操作失败'}";
      e.printStackTrace();
    }

    AdvanceLogs advanceLogs = new AdvanceLogs();
    advanceLogs.setMember_id(this.member_id.intValue());
    advanceLogs.setDisabled("false");
    advanceLogs.setMtime(Long.valueOf(System.currentTimeMillis()));
    advanceLogs.setImport_money(this.modify_advance);
    advanceLogs.setMember_advance(this.member.getAdvance());
    advanceLogs.setShop_advance(this.member.getAdvance());
    advanceLogs.setMoney(this.modify_advance);
    advanceLogs.setMessage(this.modify_memo);
    advanceLogs.setMemo("admin代充值");
    try {
      this.advanceLogsManager.add(advanceLogs);
    } catch (Exception e) {
      this.json = "{'result':1, 'message':'操作失败'}";
      e.printStackTrace();
    }
    this.json = "{'result':0,'message':'操作成功'}";

    return "json_message";
  }

  public String saveMember()
  {
    int result = this.memberManager.checkname(this.member.getUname());
    if (result == 1) {
      this.msgs.add("用户名已经存在");
      this.urls.put("查看会员列表", "member!memberlist.do");
      return "message";
    }
    if (this.member != null) {
      Date br = DateUtil.toDate(this.birthday, "yyyy-MM-dd");
      this.member.setBirthday(Long.valueOf(br.getTime()));
      this.member.setPassword(this.member.getPassword());
      this.member.setRegtime(Long.valueOf(System.currentTimeMillis()));
      this.memberManager.add(this.member);
      this.msgs.add("保存添加会员成功");
      this.urls.put("查看会员列表", "member!memberlist.do");
    }

    return "message";
  }

  public String sysLogin() {
    try {
      this.name = StringUtil.toUTF8(this.name);
      int r = this.memberManager.loginbysys(this.name);
      if (r == 1) {
        EopUser user = this.userManager.get(this.name);
        if (user != null) {
          WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();

          sessonContext.setAttribute("eop_user_key", user);
        }
        return "syslogin";
      }
      this.msgs.add("登录失败");
      return "message";
    } catch (RuntimeException e) {
      this.msgs.add(e.getMessage());
    }return "message";
  }

  public MemberLv getLv()
  {
    return this.lv;
  }

  public void setLv(MemberLv lv) {
    this.lv = lv;
  }

  public Member getMember() {
    return this.member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public IMemberManager getMemberManager() {
    return this.memberManager;
  }

  public void setMemberManager(IMemberManager memberManager) {
    this.memberManager = memberManager;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getBirthday() {
    return this.birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public IMemberLvManager getMemberLvManager() {
    return this.memberLvManager;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager) {
    this.memberLvManager = memberLvManager;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public Integer getLv_id() {
    return this.lv_id;
  }

  public void setLv_id(Integer lvId) {
    this.lv_id = lvId;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getMember_id() {
    return this.member_id;
  }

  public void setMember_id(Integer memberId) {
    this.member_id = memberId;
  }

  public List getLvlist() {
    return this.lvlist;
  }

  public void setLvlist(List lvlist) {
    this.lvlist = lvlist;
  }

  public IRegionsManager getRegionsManager() {
    return this.regionsManager;
  }

  public void setRegionsManager(IRegionsManager regionsManager) {
    this.regionsManager = regionsManager;
  }

  public List getProvinceList() {
    return this.provinceList;
  }

  public void setProvinceList(List provinceList) {
    this.provinceList = provinceList;
  }

  public List getCityList() {
    return this.cityList;
  }

  public void setCityList(List cityList) {
    this.cityList = cityList;
  }

  public List getRegionList() {
    return this.regionList;
  }

  public void setRegionList(List regionList) {
    this.regionList = regionList;
  }

  public int getPoint()
  {
    return this.point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public IPointHistoryManager getPointHistoryManager() {
    return this.pointHistoryManager;
  }

  public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
    this.pointHistoryManager = pointHistoryManager;
  }

  public List getListPointHistory() {
    return this.listPointHistory;
  }

  public void setListPointHistory(List listPointHistory) {
    this.listPointHistory = listPointHistory;
  }

  public IAdvanceLogsManager getAdvanceLogsManager() {
    return this.advanceLogsManager;
  }

  public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
    this.advanceLogsManager = advanceLogsManager;
  }

  public List getListAdvanceLogs() {
    return this.listAdvanceLogs;
  }

  public void setListAdvanceLogs(List listAdvanceLogs) {
    this.listAdvanceLogs = listAdvanceLogs;
  }

  public Double getModify_advance() {
    return this.modify_advance;
  }

  public void setModify_advance(Double modifyAdvance) {
    this.modify_advance = modifyAdvance;
  }

  public String getModify_memo() {
    return this.modify_memo;
  }

  public void setModify_memo(String modifyMemo) {
    this.modify_memo = modifyMemo;
  }

  public List getListComments() {
    return this.listComments;
  }

  public void setListComments(List listComments) {
    this.listComments = listComments;
  }

  public String getObject_type() {
    return this.object_type;
  }

  public void setObject_type(String objectType) {
    this.object_type = objectType;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUname() {
    return this.uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public IUserManager getUserManager() {
    return this.userManager;
  }

  public void setUserManager(IUserManager userManager) {
    this.userManager = userManager;
  }

  public List getCatDiscountList() {
    return this.catDiscountList;
  }

  public void setCatDiscountList(List catDiscountList) {
    this.catDiscountList = catDiscountList;
  }

  public int[] getCat_ids() {
    return this.cat_ids;
  }

  public void setCat_ids(int[] cat_ids) {
    this.cat_ids = cat_ids;
  }

  public int[] getCat_discounts() {
    return this.cat_discounts;
  }

  public void setCat_discounts(int[] cat_discounts) {
    this.cat_discounts = cat_discounts;
  }

  public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
    this.memberCommentManager = memberCommentManager;
  }

  public int getPointtype() {
    return this.pointtype;
  }

  public void setPointtype(int pointtype) {
    this.pointtype = pointtype;
  }

  public Map<Integer, String> getPluginTabs() {
    return this.pluginTabs;
  }

  public void setPluginTabs(Map<Integer, String> pluginTabs) {
    this.pluginTabs = pluginTabs;
  }

  public Map<Integer, String> getPluginHtmls() {
    return this.pluginHtmls;
  }

  public void setPluginHtmls(Map<Integer, String> pluginHtmls) {
    this.pluginHtmls = pluginHtmls;
  }

  public MemberPluginBundle getMemberPluginBundle() {
    return this.memberPluginBundle;
  }

  public void setMemberPluginBundle(MemberPluginBundle memberPluginBundle) {
    this.memberPluginBundle = memberPluginBundle;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.MemberAction
 * JD-Core Version:    0.6.1
 */