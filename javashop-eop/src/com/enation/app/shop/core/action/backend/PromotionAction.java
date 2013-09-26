package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.model.MemberLv;
import com.enation.app.shop.component.coupon.service.ICouponManager;
import com.enation.app.shop.core.model.Promotion;
import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IPromotionManager;
import com.enation.app.shop.core.service.promotion.IPromotionMethod;
import com.enation.app.shop.core.service.promotion.PromotionConditions;
import com.enation.framework.action.WWAction;
import com.enation.framework.context.spring.SpringContextHolder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class PromotionAction extends WWAction
{
  private Integer activityid;
  private Integer pmtid;
  private IPromotionManager promotionManager;
  private IMemberLvManager memberLvManager;
  private ICouponManager couponManager;
  private List pmtList;
  private List pluginList;
  private List lvList;
  private List goodsList;
  private String pluginid;
  private PromotionConditions conditions;
  private String solutionHtml;
  private Double money_from;
  private Double money_to;
  private Date time_begin;
  private Date time_end;
  private Integer[] lvidArray;
  private Integer[] goodsidArray;
  private Integer[] pmtidArray;
  private int ifcoupon;
  private String describe;
  private Promotion promotion;

  public String list()
  {
    this.pmtList = this.promotionManager.listByActivityId(this.activityid);
    return "list";
  }

  public String select_plugin()
  {
    if (this.pmtid != null) {
      this.promotion = this.promotionManager.get(this.pmtid);
      this.pluginid = this.promotion.getPmts_id();
    }
    this.pluginList = this.promotionManager.listPmtPlugins();
    return "select_plugin";
  }

  public String select_condition()
  {
    try
    {
      this.lvList = this.memberLvManager.list();

      String solution = null;
      if (this.pmtid != null) {
        this.promotion = this.promotionManager.get(this.pmtid);
        this.time_begin = new Date(this.promotion.getPmt_time_begin().longValue());
        this.time_end = new Date(this.promotion.getPmt_time_end().longValue());
        solution = this.promotion.getPmt_solution();
        this.describe = this.promotion.getPmt_describe();
        this.ifcoupon = this.promotion.getPmt_ifcoupon();
        this.money_from = this.promotion.getOrder_money_from();
        this.money_to = this.promotion.getOrder_money_to();

        List<Integer> dbLvList = this.promotionManager.listMemberLvId(this.pmtid);
        int i = 0;
        MemberLv lv;
        for (int len = this.lvList.size(); i < len; i++) {
          lv = (MemberLv)this.lvList.get(i);
          for (Integer dbLvid : dbLvList) {
            if (lv.getLv_id().intValue() == dbLvid.intValue()) {
              lv.setSelected(true);
            }
          }
        }

        this.goodsList = this.promotionManager.listGoodsId(this.pmtid);
      }
      else
      {
        this.time_begin = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(5, 10);
        this.time_end = cal.getTime();
      }
      IPromotionPlugin plugin = this.promotionManager.getPlugin(this.pluginid);
      this.conditions = new PromotionConditions(plugin.getConditions());
      String methodBeanName = plugin.getMethods();
      IPromotionMethod pmtMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
      this.solutionHtml = pmtMethod.getInputHtml(this.pmtid, solution);

      return "select_condition";
    } catch (RuntimeException e) {
      this.logger.error(e);
      this.msgs.add(e.getMessage());
    }return "message";
  }

  public String save()
  {
    try
    {
      IPromotionPlugin plugin = this.promotionManager.getPlugin(this.pluginid);
      this.conditions = new PromotionConditions(plugin.getConditions());

      if (this.pmtid != null)
        this.promotion = this.promotionManager.get(this.pmtid);
      else {
        this.promotion = new Promotion();
      }

      this.promotion.setOrder_money_from(this.money_from);
      this.promotion.setOrder_money_to(this.money_to);
      this.promotion.setPmt_time_begin(Long.valueOf(this.time_begin.getTime()));
      this.promotion.setPmt_time_end(Long.valueOf(this.time_end.getTime()));
      this.promotion.setPmt_ifcoupon(this.ifcoupon);
      this.promotion.setPmt_describe(this.describe);

      if (this.activityid != null) {
        this.promotion.setPmta_id(this.activityid.intValue());
        this.promotion.setPmt_type(0);
      } else {
        this.promotion.setPmt_type(1);
      }

      this.promotion.setPmts_id(this.pluginid);

      if (this.pmtid != null) {
        this.promotionManager.edit(this.promotion, this.lvidArray, this.goodsidArray);

        if (this.activityid != null)
        {
          this.msgs.add("促销规则修改成功");
        }

      }
      else
      {
        this.pmtid = this.promotionManager.add(this.promotion, this.lvidArray, this.goodsidArray);
        this.msgs.add("促销规则添加成功");
      }

      if (this.activityid == null)
        this.urls.put("优惠券列表", "coupon!list.do");
      else {
        this.urls.put("返回此活动促销规则列表", "promotion!list.do?activityid=" + this.promotion.getPmta_id());
      }

      return "message";
    }
    catch (RuntimeException e)
    {
      e.printStackTrace();
      this.logger.error(e.getStackTrace());
      this.msgs.add(e.getMessage());
    }return "message";
  }

  public String delete()
  {
    try
    {
      this.promotionManager.delete(this.pmtidArray);
      this.json = "{result:0,message:'删除成功'}";
    } catch (RuntimeException e) {
      this.json = ("{result:1,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public Integer getActivityid() {
    return this.activityid;
  }

  public void setActivityid(Integer activityid) {
    this.activityid = activityid;
  }

  public IPromotionManager getPromotionManager()
  {
    return this.promotionManager;
  }

  public void setPromotionManager(IPromotionManager promotionManager)
  {
    this.promotionManager = promotionManager;
  }

  public List getPmtList()
  {
    return this.pmtList;
  }

  public void setPmtList(List pmtList)
  {
    this.pmtList = pmtList;
  }

  public List getPluginList()
  {
    return this.pluginList;
  }

  public void setPluginList(List pluginList)
  {
    this.pluginList = pluginList;
  }

  public String getPluginid()
  {
    return this.pluginid;
  }

  public void setPluginid(String pluginid)
  {
    this.pluginid = pluginid;
  }

  public PromotionConditions getConditions()
  {
    return this.conditions;
  }

  public void setConditions(PromotionConditions conditions)
  {
    this.conditions = conditions;
  }

  public String getSolutionHtml()
  {
    return this.solutionHtml;
  }

  public void setSolutionHtml(String solutionHtml)
  {
    this.solutionHtml = solutionHtml;
  }

  public Double getMoney_from()
  {
    return this.money_from;
  }

  public void setMoney_from(Double moneyFrom)
  {
    this.money_from = moneyFrom;
  }

  public Double getMoney_to()
  {
    return this.money_to;
  }

  public void setMoney_to(Double moneyTo)
  {
    this.money_to = moneyTo;
  }

  public Date getTime_begin()
  {
    return this.time_begin;
  }

  public void setTime_begin(Date timeBegin)
  {
    this.time_begin = timeBegin;
  }

  public Date getTime_end()
  {
    return this.time_end;
  }

  public void setTime_end(Date timeEnd)
  {
    this.time_end = timeEnd;
  }

  public Integer[] getLvidArray()
  {
    return this.lvidArray;
  }

  public void setLvidArray(Integer[] lvidArray)
  {
    this.lvidArray = lvidArray;
  }

  public Integer[] getGoodsidArray()
  {
    return this.goodsidArray;
  }

  public void setGoodsidArray(Integer[] goodsidArray)
  {
    this.goodsidArray = goodsidArray;
  }

  public Promotion getPromotion()
  {
    return this.promotion;
  }

  public void setPromotion(Promotion promotion)
  {
    this.promotion = promotion;
  }

  public int getIfcoupon()
  {
    return this.ifcoupon;
  }

  public void setIfcoupon(int ifcoupon)
  {
    this.ifcoupon = ifcoupon;
  }

  public String getDescribe()
  {
    return this.describe;
  }

  public void setDescribe(String describe)
  {
    this.describe = describe;
  }

  public IMemberLvManager getMemberLvManager()
  {
    return this.memberLvManager;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager)
  {
    this.memberLvManager = memberLvManager;
  }

  public List getLvList()
  {
    return this.lvList;
  }

  public void setLvList(List lvList)
  {
    this.lvList = lvList;
  }

  public Integer[] getPmtidArray()
  {
    return this.pmtidArray;
  }

  public void setPmtidArray(Integer[] pmtidArray)
  {
    this.pmtidArray = pmtidArray;
  }

  public Integer getPmtid()
  {
    return this.pmtid;
  }

  public void setPmtid(Integer pmtid)
  {
    this.pmtid = pmtid;
  }

  public List getGoodsList()
  {
    return this.goodsList;
  }

  public void setGoodsList(List goodsList)
  {
    this.goodsList = goodsList;
  }

  public ICouponManager getCouponManager()
  {
    return this.couponManager;
  }

  public void setCouponManager(ICouponManager couponManager)
  {
    this.couponManager = couponManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.PromotionAction
 * JD-Core Version:    0.6.1
 */