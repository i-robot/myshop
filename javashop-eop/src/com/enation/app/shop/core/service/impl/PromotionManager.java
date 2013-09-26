package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.component.coupon.Coupons;
import com.enation.app.shop.core.model.Promotion;
import com.enation.app.shop.core.model.support.CartItem;
import com.enation.app.shop.core.model.support.DiscountPrice;
import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
import com.enation.app.shop.core.plugin.promotion.PromotionPluginBundle;
import com.enation.app.shop.core.service.IPromotionManager;
import com.enation.app.shop.core.service.promotion.IDiscountBehavior;
import com.enation.app.shop.core.service.promotion.IGiveGiftBehavior;
import com.enation.app.shop.core.service.promotion.IPromotionMethod;
import com.enation.app.shop.core.service.promotion.IReduceFreightBehavior;
import com.enation.app.shop.core.service.promotion.IReducePriceBehavior;
import com.enation.app.shop.core.service.promotion.ITimesPointBehavior;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.IntegerMapper;
import com.enation.framework.database.ObjectNotFoundException;
import com.enation.framework.plugin.IPlugin;
import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PromotionManager extends BaseSupport
  implements IPromotionManager
{
  private PromotionPluginBundle promotionPluginBundle;

  @Transactional(propagation=Propagation.REQUIRED)
  public Integer add(Promotion promotion, Integer[] memberLvIdArray, Integer[] goodsIdArray)
  {
    if (promotion == null) throw new IllegalArgumentException("param promotion is NULL");
    if (promotion.getPmt_describe() == null) throw new IllegalArgumentException("param promotion 's Pmt_describe is NULL");
    if (promotion.getPmts_id() == null) throw new IllegalArgumentException("param promotion 's Pmts_id is NULL");
    if (promotion.getPmt_time_begin() == null) throw new IllegalArgumentException("param promotion 's pmt_time_begin is NULL");
    if (promotion.getPmt_time_end() == null) throw new IllegalArgumentException("param promotion 's Pmt_time_end is NULL");

    promotion.setPmt_update_time(Long.valueOf(new Date().getTime()));
    promotion.setDisabled("false");
    IPromotionPlugin plugin = getPlugin(promotion.getPmts_id());
    promotion.setPmt_basic_type(plugin.getType());

    this.baseDaoSupport.insert("promotion", promotion);
    Integer pmtid = Integer.valueOf(this.baseDaoSupport.getLastId("promotion"));
    promotion.setPmt_id(pmtid.intValue());

    if (memberLvIdArray != null) {
      saveMemberLv(pmtid, memberLvIdArray);
    }

    if (goodsIdArray != null) {
      saveGoods(pmtid, goodsIdArray);
    }

    pluginSave(promotion);
    return pmtid;
  }

  public Integer add(Promotion promotion, Integer[] memberLvIdArray, int type, Integer[] goodsIdArray, Integer[] goodsCatIdArray, Integer[] tagIdArray) {
    Integer result = null;
    if (type == 1) {
      result = add(promotion, memberLvIdArray, goodsIdArray);
    }
    if (type == 2);
    return result;
  }

  public void edit(Promotion promotion, Integer[] memberLvIdArray, Integer[] goodsIdArray)
  {
    if (promotion == null) throw new IllegalArgumentException("param promotion is NULL");
    if (promotion.getPmt_describe() == null) throw new IllegalArgumentException("param promotion 's Pmt_describe is NULL");
    if (promotion.getPmts_id() == null) throw new IllegalArgumentException("param promotion 's Pmts_id is NULL");
    if (promotion.getPmt_time_begin() == null) throw new IllegalArgumentException("param promotion 's pmt_time_begin is NULL");
    if (promotion.getPmt_time_end() == null) throw new IllegalArgumentException("param promotion 's Pmt_time_end is NULL");

    promotion.setPmt_update_time(Long.valueOf(new Date().getTime()));
    promotion.setDisabled("false");
    IPromotionPlugin plugin = getPlugin(promotion.getPmts_id());
    promotion.setPmt_basic_type(plugin.getType());
    Integer pmtid = Integer.valueOf(promotion.getPmt_id());
    this.baseDaoSupport.update("promotion", promotion, "pmt_id=" + pmtid);
    promotion.setPmt_id(pmtid.intValue());

    if (memberLvIdArray != null) {
      this.baseDaoSupport.execute("delete from pmt_member_lv where pmt_id=?", new Object[] { pmtid });
      saveMemberLv(pmtid, memberLvIdArray);
    }

    if (goodsIdArray != null) {
      this.baseDaoSupport.execute("delete from pmt_goods where pmt_id=?", new Object[] { pmtid });
      saveGoods(pmtid, goodsIdArray);
    }

    pluginSave(promotion);
  }

  public List<Promotion> list(Integer goodsid, Integer memberLvId)
  {
    if (goodsid == null) throw new IllegalArgumentException("goodsid is NULL");
    if (memberLvId == null) throw new IllegalArgumentException("memberLvId is NULL");
    long now = System.currentTimeMillis();
    String sql = "select * from  " + getTableName("promotion") + " where pmt_basic_type='goods'" + " and  pmt_time_begin<" + now + " and  pmt_time_end>" + now + " and pmt_id in  (select pmt_id from " + getTableName("pmt_goods") + " where goods_id=?)" + " and pmt_id in (select pmt_id from " + getTableName("pmt_member_lv") + " where lv_id =? )" + " and pmt_type='0' ";

    Coupons coupon = (Coupons)ThreadContextHolder.getSessionContext().getAttribute("coupon");

    return this.daoSupport.queryForList(sql, Promotion.class, new Object[] { goodsid, memberLvId });
  }

  public List<Promotion> list(Double orderPrice, Integer memberLvId)
  {
    long now = System.currentTimeMillis();

    String sql = "select * from  " + getTableName("promotion") + " where pmt_basic_type='order' " + " and pmt_time_begin<" + now + " and  pmt_time_end>" + now + " and order_money_from<=? and order_money_to>=?" + " and pmt_id in (select pmt_id from " + getTableName("pmt_member_lv") + " where lv_id =? ) " + " and pmt_type='0' ";

    Coupons coupon = (Coupons)ThreadContextHolder.getSessionContext().getAttribute("coupon");

    return this.daoSupport.queryForList(sql, Promotion.class, new Object[] { orderPrice, orderPrice, memberLvId });
  }

  private Integer getCoupPmtId(String coupcode) {
    String sql = "select  c.pmt_id from  " + getTableName("member_coupon") + " mc ," + getTableName("coupons") + " c where mc.cpns_id = c.cpns_id and mc.memc_code=? ";
    return Integer.valueOf(this.daoSupport.queryForInt(sql, new Object[] { coupcode }));
  }

  public void applyGoodsPmt(List<CartItem> list, Integer memberLvId)
  {
    if ((list == null) || (memberLvId == null)) return;

    for (Iterator i$ = list.iterator(); i$.hasNext(); ) { CartItem item = (CartItem)i$.next();

      Integer goodsid = item.getGoods_id();

      List<Promotion> pmtList = list(goodsid, memberLvId);
      item.setPmtList(pmtList);

      for (Promotion pmt : pmtList)
      {
        String pluginBeanId = pmt.getPmts_id();
        IPromotionPlugin plugin = getPlugin(pluginBeanId);

        if (plugin == null) {
          this.logger.error("plugin[" + pluginBeanId + "] not found ");
          throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
        }

        String methodBeanName = plugin.getMethods();
        if (this.logger.isDebugEnabled()) {
          this.logger.debug("find promotion method[" + methodBeanName + "]");
        }
        IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
        if (promotionMethod == null) {
          this.logger.error("plugin[" + methodBeanName + "] not found ");
          throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
        }

        if ((promotionMethod instanceof IDiscountBehavior)) {
          IDiscountBehavior discountBehavior = (IDiscountBehavior)promotionMethod;
          Double newPrice = discountBehavior.discount(pmt, item.getCoupPrice());
          item.setCoupPrice(newPrice);
        }

        if ((promotionMethod instanceof ITimesPointBehavior)) {
          Integer point = item.getPoint();
          ITimesPointBehavior timesPointBehavior = (ITimesPointBehavior)promotionMethod;
          point = timesPointBehavior.countPoint(pmt, point);
          item.setPoint(point);
        }
      }
    }
    CartItem item;
  }

  public DiscountPrice applyOrderPmt(Double orderPrice, Double shipFee, Integer point, Integer memberLvId)
  {
    List<Promotion> pmtList = list(orderPrice, memberLvId);
    for (Promotion pmt : pmtList)
    {
      String pluginBeanId = pmt.getPmts_id();
      IPromotionPlugin plugin = getPlugin(pluginBeanId);
      if (plugin == null) {
        this.logger.error("plugin[" + pluginBeanId + "] not found ");
        throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
      }

      String methodBeanName = plugin.getMethods();
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("find promotion method[" + methodBeanName + "]");
      }
      IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
      if (promotionMethod == null) {
        this.logger.error("plugin[" + methodBeanName + "] not found ");
        throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
      }

      if ((promotionMethod instanceof IReducePriceBehavior)) {
        IReducePriceBehavior reducePriceBehavior = (IReducePriceBehavior)promotionMethod;
        orderPrice = reducePriceBehavior.reducedPrice(pmt, orderPrice);
      }

      if ((promotionMethod instanceof IDiscountBehavior)) {
        IDiscountBehavior discountBehavior = (IDiscountBehavior)promotionMethod;
        orderPrice = discountBehavior.discount(pmt, orderPrice);
      }

      if ((promotionMethod instanceof IReduceFreightBehavior)) {
        IReduceFreightBehavior reduceFreightBehavior = (IReduceFreightBehavior)promotionMethod;
        shipFee = reduceFreightBehavior.reducedPrice(shipFee);
      }

      if ((promotionMethod instanceof ITimesPointBehavior)) {
        ITimesPointBehavior timesPointBehavior = (ITimesPointBehavior)promotionMethod;
        point = timesPointBehavior.countPoint(pmt, point);
      }

    }

    DiscountPrice discountPrice = new DiscountPrice();
    discountPrice.setOrderPrice(orderPrice);
    discountPrice.setShipFee(shipFee);
    discountPrice.setPoint(point);
    return discountPrice;
  }

  public void applyOrderPmt(Integer orderId, Double orderPrice, Integer memberLvId)
  {
    List<Promotion> pmtList = list(orderPrice, memberLvId);
    for (Promotion pmt : pmtList)
    {
      String pluginBeanId = pmt.getPmts_id();
      IPromotionPlugin plugin = getPlugin(pluginBeanId);
      if (plugin == null) {
        this.logger.error("plugin[" + pluginBeanId + "] not found ");
        throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
      }

      String methodBeanName = plugin.getMethods();
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("find promotion method[" + methodBeanName + "]");
      }
      IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
      if (promotionMethod == null) {
        this.logger.error("plugin[" + methodBeanName + "] not found ");
        throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
      }

      if ((promotionMethod instanceof IGiveGiftBehavior)) {
        IGiveGiftBehavior giveGiftBehavior = (IGiveGiftBehavior)promotionMethod;
        giveGiftBehavior.giveGift(pmt, orderId);
      }
    }
  }

  public List listGift(List<Promotion> pmtList)
  {
    List giftList = new ArrayList();
    for (Promotion pmt : pmtList)
    {
      String pluginBeanId = pmt.getPmts_id();
      IPromotionPlugin plugin = getPlugin(pluginBeanId);
      if (plugin == null) {
        this.logger.error("plugin[" + pluginBeanId + "] not found ");
        throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
      }

      String methodBeanName = plugin.getMethods();
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("find promotion method[" + methodBeanName + "]");
      }
      IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
      if (promotionMethod == null) {
        this.logger.error("plugin[" + methodBeanName + "] not found ");
        throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
      }

      if ((promotionMethod instanceof IGiveGiftBehavior)) {
        IGiveGiftBehavior giveGiftBehavior = (IGiveGiftBehavior)promotionMethod;
        List list = giveGiftBehavior.getGiftList(pmt);
        giftList.addAll(list);
      }

    }

    return giftList;
  }

  public List listByActivityId(Integer activityid)
  {
    String sql = "select * from promotion where disabled='false' and pmta_id=?";
    return this.baseDaoSupport.queryForList(sql, Promotion.class, new Object[] { activityid });
  }

  private void pluginSave(Promotion promotion)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("find promotion plugin[" + promotion.getPmts_id() + "]");
    }
    IPromotionPlugin plugin = getPlugin(promotion.getPmts_id());

    if (plugin == null) {
      this.logger.error("plugin[" + promotion.getPmts_id() + "] not found ");
      throw new ObjectNotFoundException("plugin[" + promotion.getPmts_id() + "] not found ");
    }

    String methodBeanName = plugin.getMethods();
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("find promotion method[" + methodBeanName + "]");
    }
    IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
    if (promotionMethod == null) {
      this.logger.error("plugin[" + methodBeanName + "] not found ");
      throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
    }

    String solution = promotionMethod.onPromotionSave(Integer.valueOf(promotion.getPmt_id()));
    this.baseDaoSupport.execute("update promotion set pmt_solution =? where pmt_id=?", new Object[] { solution, Integer.valueOf(promotion.getPmt_id()) });
  }

  private void saveMemberLv(Integer pmtid, Integer[] memberLvIdArray)
  {
    for (Integer memberLvId : memberLvIdArray)
    {
      this.baseDaoSupport.execute("insert into pmt_member_lv(pmt_id,lv_id)values(?,?)", new Object[] { pmtid, memberLvId });
    }
  }

  public void saveGoods(Integer pmtid, Integer[] goodsIdArray)
  {
    for (Integer goodsid : goodsIdArray)
      this.baseDaoSupport.execute("insert into pmt_goods(pmt_id,goods_id)values(?,?)", new Object[] { pmtid, goodsid });
  }

  public List listPmtPlugins()
  {
    return this.promotionPluginBundle.getPluginList();
  }

  public IPromotionPlugin getPlugin(String pluginid)
  {
    List<IPlugin> pluginList = this.promotionPluginBundle.getPluginList();

    for (IPlugin plugin : pluginList)
    {
      if (((plugin instanceof IPromotionPlugin)) && 
        (((IPromotionPlugin)plugin).getId().equals(pluginid))) {
        return (IPromotionPlugin)plugin;
      }
    }

    return null;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer[] pmtidArray)
  {
    if ((pmtidArray == null) || (pmtidArray.length == 0)) return;
    String idStr = StringUtil.arrayToString(pmtidArray, ",");
    String sql = "delete from pmt_goods where pmt_id in(" + idStr + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);

    sql = "delete from pmt_member_lv where pmt_id in(" + idStr + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);

    sql = "delete from promotion where pmt_id in(" + idStr + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public Promotion get(Integer pmtid)
  {
    return (Promotion)this.baseDaoSupport.queryForObject("select * from promotion where pmt_id =?", Promotion.class, new Object[] { pmtid });
  }

  public List listGoodsId(Integer pmtid)
  {
    String sql = "select * from  " + getTableName("goods") + " where goods_id in(select goods_id from " + getTableName("pmt_goods") + " where pmt_id =? )";
    return this.daoSupport.queryForList(sql, new Object[] { pmtid });
  }

  public List listMemberLvId(Integer pmtid)
  {
    String sql = "select lv_id from pmt_member_lv where pmt_id =? ";
    return this.baseDaoSupport.queryForList(sql, new IntegerMapper(), new Object[] { pmtid });
  }

  public PromotionPluginBundle getPromotionPluginBundle()
  {
    return this.promotionPluginBundle;
  }
  public void setPromotionPluginBundle(PromotionPluginBundle promotionPluginBundle) {
    this.promotionPluginBundle = promotionPluginBundle;
  }

  public List<Map> listOrderPmt(Integer orderid)
  {
    String sql = "select * from order_pmt where order_id = ?";
    return this.baseDaoSupport.queryForList(sql, new Object[] { orderid });
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.PromotionManager
 * JD-Core Version:    0.6.1
 */