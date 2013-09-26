package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.Cart;
import com.enation.app.shop.core.model.GoodsLvPrice;
import com.enation.app.shop.core.model.Promotion;
import com.enation.app.shop.core.model.mapper.CartItemMapper;
import com.enation.app.shop.core.model.support.CartItem;
import com.enation.app.shop.core.model.support.DiscountPrice;
import com.enation.app.shop.core.model.support.OrderPrice;
import com.enation.app.shop.core.plugin.cart.CartPluginBundle;
import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
import com.enation.app.shop.core.service.ICartManager;
import com.enation.app.shop.core.service.IDlyTypeManager;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IPromotionManager;
import com.enation.app.shop.core.service.promotion.IPromotionMethod;
import com.enation.app.shop.core.service.promotion.ITimesPointBehavior;
import com.enation.eop.processor.httpcache.HttpCacheManager;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.database.DoubleMapper;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.ObjectNotFoundException;
import com.enation.framework.util.CurrencyUtil;
import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CartManager extends BaseSupport
  implements ICartManager
{
  private IDlyTypeManager dlyTypeManager;
  private CartPluginBundle cartPluginBundle;
  private IMemberLvManager memberLvManager;
  private IPromotionManager promotionManager;

  @Transactional(propagation=Propagation.REQUIRED)
  public int add(Cart cart)
  {
    HttpCacheManager.sessionChange();

    this.cartPluginBundle.onAdd(cart);

    String sql = "select count(0) from cart where  product_id=? and session_id=? and itemtype=? ";

    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { cart.getProduct_id(), cart.getSession_id(), cart.getItemtype() });
    if (count > 0) {
      this.baseDaoSupport.execute("update cart set num=num+? where  product_id=? and session_id=? and itemtype=? ", new Object[] { cart.getNum(), cart.getProduct_id(), cart.getSession_id(), cart.getItemtype() });

      return 0;
    }

    this.baseDaoSupport.insert("cart", cart);

    Integer cartid = Integer.valueOf(this.baseDaoSupport.getLastId("cart"));
    cart.setCart_id(cartid);

    return cartid.intValue();
  }

  public Cart get(int cart_id)
  {
    return (Cart)this.baseDaoSupport.queryForObject("SELECT * FROM cart WHERE cart_id=?", Cart.class, new Object[] { Integer.valueOf(cart_id) });
  }

  public Cart getCartByProductId(int productId, String sessionid) {
    return (Cart)this.baseDaoSupport.queryForObject("SELECT * FROM cart WHERE product_id=? AND session_id=?", Cart.class, new Object[] { Integer.valueOf(productId), sessionid });
  }

  public Cart getCartByProductId(int productId, String sessionid, String addon) {
    return (Cart)this.baseDaoSupport.queryForObject("SELECT * FROM cart WHERE product_id=? AND session_id=? AND addon=?", Cart.class, new Object[] { Integer.valueOf(productId), sessionid, addon });
  }

  public Integer countItemNum(String sessionid) {
    String sql = "select count(0) from cart where session_id =?";
    return Integer.valueOf(this.baseDaoSupport.queryForInt(sql, new Object[] { sessionid }));
  }

  public List<CartItem> listGoods(String sessionid)
  {
    StringBuffer sql = new StringBuffer();

    sql.append("select g.cat_id as catid,g.goods_id,g.image_default,g.istejia,g.no_discount, c.name ,  p.sn, p.specs  ,g.mktprice,g.unit,g.point,p.product_id,c.price,c.cart_id as cart_id,c.num as num,c.itemtype,c.addon  from " + getTableName("cart") + " c," + getTableName("product") + " p," + getTableName("goods") + " g ");
    sql.append("where c.itemtype=0 and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=?");
    List list = this.daoSupport.queryForList(sql.toString(), new CartItemMapper(), new Object[] { sessionid });

    this.cartPluginBundle.filterList(list, sessionid);

    return list;
  }

  private void applyMemPrice(List<CartItem> itemList, List<GoodsLvPrice> memPriceList, double discount)
  {
    for (CartItem item : itemList) {
      double price = item.getCoupPrice().doubleValue() * discount;
      for (GoodsLvPrice lvPrice : memPriceList) {
        if (item.getProduct_id().intValue() == lvPrice.getProductid()) {
          price = lvPrice.getPrice().doubleValue();
        }

      }

      item.setCoupPrice(Double.valueOf(price));
    }
  }

  public void clean(String sessionid)
  {
    String sql = "delete from cart where session_id=?";

    this.baseDaoSupport.execute(sql, new Object[] { sessionid });
    HttpCacheManager.sessionChange();
  }

  public void clean(String sessionid, Integer userid, Integer siteid)
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      String sql = "delete from es_cart_" + userid + "_" + siteid + " where session_id=?";

      this.daoSupport.execute(sql, new Object[] { sessionid });
    }
    else {
      String sql = "delete from cart where session_id=?";
      this.baseDaoSupport.execute(sql, new Object[] { sessionid });
    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("clean cart sessionid[" + sessionid + "]");
    }
    HttpCacheManager.sessionChange();
  }

  public void delete(String sessionid, Integer cartid) {
    String sql = "delete from cart where session_id=? and cart_id=?";
    this.baseDaoSupport.execute(sql, new Object[] { sessionid, cartid });
    this.cartPluginBundle.onDelete(sessionid, cartid);
    this.cartPluginBundle.onDelete(sessionid, cartid);
    HttpCacheManager.sessionChange();
  }

  public void updateNum(String sessionid, Integer cartid, Integer num) {
    String sql = "update cart set num=? where session_id =? and cart_id=?";
    this.baseDaoSupport.execute(sql, new Object[] { num, sessionid, cartid });
  }

  public Double countGoodsTotal(String sessionid) {
    StringBuffer sql = new StringBuffer();
    sql.append("select sum( c.price * c.num ) as num from cart c ");
    sql.append("where  c.session_id=? and c.itemtype=0 ");
    Double price = (Double)this.baseDaoSupport.queryForObject(sql.toString(), new DoubleMapper(), new Object[] { sessionid });

    return price;
  }

  public Double countGoodsDiscountTotal(String sessionid)
  {
    List<CartItem> itemList = listGoods(sessionid);

    double price = 0.0D;
    for (CartItem item : itemList)
    {
      price = CurrencyUtil.add(price, item.getSubtotal().doubleValue());
    }

    return Double.valueOf(price);
  }

  public Integer countPoint(String sessionid)
  {
    Member member = UserServiceFactory.getUserService().getCurrentMember();
    if (member != null) {
      Integer memberLvId = member.getLv_id();
      StringBuffer sql = new StringBuffer();
      sql.append("select c.*, g.goods_id, g.point from " + getTableName("cart") + " c," + getTableName("goods") + " g, " + getTableName("product") + " p where p.product_id = c.product_id and g.goods_id = p.goods_id and c.session_id = ?");

      List list = this.daoSupport.queryForList(sql.toString(), new Object[] { sessionid });

      Integer result = Integer.valueOf(0);
      for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Map map = (Map)i$.next();
        Integer goodsid = Integer.valueOf(StringUtil.toInt(map.get("goods_id").toString()));

        List<Promotion> pmtList = new ArrayList();

        if (memberLvId != null) {
          pmtList = this.promotionManager.list(goodsid, memberLvId);
        }

        for (Promotion pmt : pmtList)
        {
          String pluginBeanId = pmt.getPmts_id();
          IPromotionPlugin plugin = this.promotionManager.getPlugin(pluginBeanId);

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

          if ((promotionMethod instanceof ITimesPointBehavior)) {
            Integer point = Integer.valueOf(StringUtil.toInt(map.get("point").toString()));

            ITimesPointBehavior timesPointBehavior = (ITimesPointBehavior)promotionMethod;
            point = timesPointBehavior.countPoint(pmt, point);
            result = Integer.valueOf(result.intValue() + point.intValue());
          }
        }
      }
      Map map;
      return result;
    }
    StringBuffer sql = new StringBuffer();
    sql.append("select  sum(g.point * c.num) from " + getTableName("cart") + " c," + getTableName("product") + " p," + getTableName("goods") + " g ");

    sql.append("where (c.itemtype=0  or c.itemtype=1)  and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=?");

    return Integer.valueOf(this.daoSupport.queryForInt(sql.toString(), new Object[] { sessionid }));
  }

  public Double countGoodsWeight(String sessionid)
  {
    StringBuffer sql = new StringBuffer("select sum( c.weight * c.num )  from cart c where c.session_id=?");

    Double weight = (Double)this.baseDaoSupport.queryForObject(sql.toString(), new DoubleMapper(), new Object[] { sessionid });

    return weight;
  }

  public OrderPrice countPrice(String sessionid, Integer shippingid, String regionid, Boolean isProtected)
  {
    OrderPrice orderPrice = new OrderPrice();

    Double weight = countGoodsWeight(sessionid);

    Integer point = countPoint(sessionid);

    Double originalPrice = countGoodsTotal(sessionid);

    Double coupPrice = originalPrice;

    Double orderTotal = Double.valueOf(0.0D);

    Double dlyPrice = Double.valueOf(0.0D);

    Double protectPrice = Double.valueOf(0.0D);

    Member member = UserServiceFactory.getUserService().getCurrentMember();

    if (member != null) {
      coupPrice = countGoodsDiscountTotal(sessionid);
    }

    if ((regionid != null) && (shippingid != null) && (isProtected != null))
    {
      Double[] priceArray = this.dlyTypeManager.countPrice(shippingid, weight, originalPrice, regionid, isProtected.booleanValue());
      dlyPrice = priceArray[0];

      if (member != null)
      {
        DiscountPrice discountPrice = this.promotionManager.applyOrderPmt(coupPrice, dlyPrice, point, member.getLv_id());
        coupPrice = discountPrice.getOrderPrice();
        dlyPrice = discountPrice.getShipFee();
        point = discountPrice.getPoint();
      }

      if (isProtected.booleanValue()) {
        protectPrice = priceArray[1];

        orderPrice.setProtectPrice(protectPrice);
      }

    }

    Double reducePrice = Double.valueOf(CurrencyUtil.sub(originalPrice.doubleValue(), coupPrice.doubleValue()));

    orderTotal = Double.valueOf(CurrencyUtil.add(coupPrice.doubleValue(), dlyPrice.doubleValue()));
    orderTotal = Double.valueOf(CurrencyUtil.add(orderTotal.doubleValue(), protectPrice.doubleValue()));

    orderPrice.setDiscountPrice(reducePrice);
    orderPrice.setGoodsPrice(coupPrice);
    orderPrice.setShippingPrice(dlyPrice);
    orderPrice.setPoint(point);
    orderPrice.setOriginalPrice(originalPrice);
    orderPrice.setOrderPrice(orderTotal);
    orderPrice.setWeight(weight);
    orderPrice.setNeedPayMoney(orderTotal);
    orderPrice = this.cartPluginBundle.coutPrice(orderPrice);
    return orderPrice;
  }

  public IPromotionManager getPromotionManager()
  {
    return this.promotionManager;
  }

  public CartPluginBundle getCartPluginBundle()
  {
    return this.cartPluginBundle;
  }

  public void setCartPluginBundle(CartPluginBundle cartPluginBundle) {
    this.cartPluginBundle = cartPluginBundle;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager)
  {
    this.memberLvManager = memberLvManager;
  }

  public IDlyTypeManager getDlyTypeManager() {
    return this.dlyTypeManager;
  }

  public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
    this.dlyTypeManager = dlyTypeManager;
  }

  public IMemberLvManager getMemberLvManager() {
    return this.memberLvManager;
  }

  public void setPromotionManager(IPromotionManager promotionManager) {
    this.promotionManager = promotionManager;
  }

  public boolean checkGoodsInCart(Integer goodsid)
  {
    String sql = "select count(0) from cart where goods_id=?";
    return this.baseDaoSupport.queryForInt(sql, new Object[] { goodsid }) > 0;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.CartManager
 * JD-Core Version:    0.6.1
 */