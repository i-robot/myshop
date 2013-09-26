package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.model.MemberLv;
import com.enation.app.shop.core.model.Cat;
import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.model.GoodsLvPrice;
import com.enation.app.shop.core.model.GoodsStores;
import com.enation.app.shop.core.model.support.GoodsEditDTO;
import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
import com.enation.app.shop.core.service.IDepotMonitorManager;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IMemberPriceManager;
import com.enation.app.shop.core.service.IPackageProductManager;
import com.enation.app.shop.core.service.ITagManager;
import com.enation.app.shop.core.service.SnDuplicateException;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class GoodsManager extends BaseSupport
  implements IGoodsManager
{
  private ITagManager tagManager;
  private GoodsPluginBundle goodsPluginBundle;
  private IPackageProductManager packageProductManager;
  private IGoodsCatManager goodsCatManager;
  private IMemberPriceManager memberPriceManager;
  private IMemberLvManager memberLvManager;
  private IDepotMonitorManager depotMonitorManager;

  @Transactional(propagation=Propagation.REQUIRED)
  public void add(Goods goods)
  {
    try
    {
      Map goodsMap = po2Map(goods);

      this.goodsPluginBundle.onBeforeAdd(goodsMap);

      goodsMap.put("disabled", Integer.valueOf(0));
      goodsMap.put("create_time", Long.valueOf(System.currentTimeMillis()));
      goodsMap.put("view_count", Integer.valueOf(0));
      goodsMap.put("buy_count", Integer.valueOf(0));
      goodsMap.put("last_modify", Long.valueOf(System.currentTimeMillis()));

      this.baseDaoSupport.insert("goods", goodsMap);
      Integer goods_id = Integer.valueOf(this.baseDaoSupport.getLastId("goods"));

      goods.setGoods_id(goods_id);
      goodsMap.put("goods_id", goods_id);

      this.goodsPluginBundle.onAfterAdd(goodsMap);
    } catch (RuntimeException e) {
      if ((e instanceof SnDuplicateException)) {
        throw e;
      }

      e.printStackTrace();
    }
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void edit(Goods goods)
  {
    try
    {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("开始保存商品数据...");
      }
      Map goodsMap = po2Map(goods);
      this.goodsPluginBundle.onBeforeEdit(goodsMap);
      this.baseDaoSupport.update("goods", goodsMap, "goods_id=" + goods.getGoods_id());

      this.goodsPluginBundle.onAfterEdit(goodsMap);
      if (this.logger.isDebugEnabled())
        this.logger.debug("保存商品数据完成.");
    }
    catch (RuntimeException e) {
      if ((e instanceof SnDuplicateException)) {
        throw e;
      }
      e.printStackTrace();
    }
  }

  public GoodsEditDTO getGoodsEditData(Integer goods_id)
  {
    GoodsEditDTO editDTO = new GoodsEditDTO();
    String sql = "select * from goods  where goods_id=?";
    Map goods = this.baseDaoSupport.queryForMap(sql, new Object[] { goods_id });

    String image_file = (String)goods.get("image_file");
    if (!StringUtil.isEmpty(image_file)) {
      image_file = UploadUtil.replacePath(image_file);
      goods.put("image_file", image_file);
    }

    String intro = (String)goods.get("intro");
    if (intro != null) {
      intro = UploadUtil.replacePath(intro);
      goods.put("intro", intro);
    }

    Map htmlMap = this.goodsPluginBundle.onFillEditInputData(goods);

    editDTO.setGoods(goods);
    editDTO.setHtmlMap(htmlMap);

    return editDTO;
  }

  public Map get(Integer goods_id)
  {
    String sql = "select g.*,b.name as brand_name from " + getTableName("goods") + " g left join " + getTableName("brand") + " b on g.brand_id=b.brand_id ";

    sql = sql + "  where goods_id=?";

    Map goods = this.daoSupport.queryForMap(sql, new Object[] { goods_id });

    String image_file = (String)goods.get("image_file");
    if (image_file != null) {
      image_file = UploadUtil.replacePath(image_file);

      goods.put("image_file", image_file);
    }

    String image_default = (String)goods.get("image_default");
    if (image_default != null) {
      image_default = UploadUtil.replacePath(image_default);
      goods.put("image_default", image_default);
    }

    if ((goods.get("have_spec") == null) || (((Integer)goods.get("have_spec")).intValue() == 0))
    {
      IUserService userService = UserServiceFactory.getUserService();
      Member member = userService.getCurrentMember();
      List<GoodsLvPrice> memPriceList = new ArrayList();

      double discount = 1.0D;
      if (member != null) {
        memPriceList = this.memberPriceManager.listPriceByGid(((Integer)goods.get("goods_id")).intValue());

        MemberLv lv = this.memberLvManager.get(member.getLv_id());

        if ((lv != null) && (lv.getDiscount() != null)) {
          discount = lv.getDiscount().intValue() / 100.0D;
        }

        Double price = Double.valueOf(Double.valueOf(goods.get("price").toString()).doubleValue() * discount);

        for (GoodsLvPrice lvPrice : memPriceList) {
          if ((lvPrice.getGoodsid() == ((Integer)goods.get("goods_id")).intValue()) && (lvPrice.getLvid() == member.getLv_id().intValue()))
          {
            price = lvPrice.getPrice();
          }
        }

        goods.put("member_price", price);
      }

    }

    return goods;
  }

  public void getNavdata(Map goods)
  {
    int catid = ((Integer)goods.get("cat_id")).intValue();
    List list = this.goodsCatManager.getNavpath(catid);
    goods.put("navdata", list);
  }

  private String getListSql(int disabled)
  {
    String selectSql = this.goodsPluginBundle.onGetSelector();
    String fromSql = this.goodsPluginBundle.onGetFrom();

    String sql = "select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name " + selectSql + " from " + getTableName("goods") + " g left join " + getTableName("goods_cat") + " c on g.cat_id=c.cat_id left join " + getTableName("brand") + " b on g.brand_id = b.brand_id and b.disabled=0 left join " + getTableName("goods_type") + " t on g.type_id =t.type_id " + fromSql + " where g.goods_type = 'normal' and g.disabled=" + disabled;

    return sql;
  }

  private String getBindListSql(int disabled)
  {
    String sql = "select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name from " + getTableName("goods") + " g left join " + getTableName("goods_cat") + " c on g.cat_id=c.cat_id left join " + getTableName("brand") + " b on g.brand_id = b.brand_id left join " + getTableName("goods_type") + " t on g.type_id =t.type_id" + " where g.goods_type = 'bind' and g.disabled=" + disabled;

    return sql;
  }

  public Page searchGoods(Integer brand_id, Integer is_market, Integer catid, String name, String sn, Integer market_enable, Integer[] tagid, String order, int page, int pageSize)
  {
    return searchGoods(brand_id, is_market, catid, name, sn, market_enable, tagid, order, page, pageSize, null);
  }

  public Page searchGoods(Integer brand_id, Integer is_market, Integer catid, String name, String sn, Integer market_enable, Integer[] tagid, String order, int page, int pageSize, String other)
  {
    other = other == null ? "" : other;

    String sql = getListSql(0);
    if ((brand_id != null) && (brand_id.intValue() != 0)) {
      sql = sql + " and g.brand_id = " + brand_id + " ";
    }
    if ((is_market == null) || (is_market.intValue() == -1))
      sql = sql + "  ";
    else if (is_market.intValue() == 1)
      sql = sql + " and g.market_enable = 1 ";
    else if (is_market.intValue() == 0) {
      sql = sql + " and g.market_enable = 0 ";
    }

    if ("1".equals(other))
    {
      sql = sql + " and g.no_discount=1";
    }
    if ("2".equals(other))
    {
      sql = sql + " and (select count(0) from " + getTableName("goods_lv_price") + " glp where glp.goodsid=g.goods_id) >0";
    }

    if (order == null) {
      order = "goods_id desc";
    }

    if ((name != null) && (!name.equals(""))) {
      name = name.trim();
      String[] keys = name.split("\\s");

      for (String key : keys) {
        sql = sql + " and g.name like '%";
        sql = sql + key;
        sql = sql + "%'";
      }

    }

    if ((sn != null) && (!sn.equals(""))) {
      sql = sql + "   and g.sn = '" + sn + "'";
    }

    if (market_enable != null) {
      sql = sql + " and g.market_enable=" + market_enable;
    }

    if (catid != null) {
      Cat cat = this.goodsCatManager.getById(catid.intValue());
      sql = sql + " and  g.cat_id in(";
      sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
    }

    if ((tagid != null) && (tagid.length > 0)) {
      String tagidstr = StringUtil.arrayToString(tagid, ",");
      sql = sql + " and g.goods_id in(select rel_id from " + getTableName("tag_rel") + " where tag_id in(" + tagidstr + "))";
    }

    StringBuffer _sql = new StringBuffer(sql);
    this.goodsPluginBundle.onSearchFilter(_sql);
    _sql.append(" order by g." + order);

    Page webpage = this.daoSupport.queryForPage(_sql.toString(), page, pageSize, new Object[0]);

    return webpage;
  }

  public Page searchBindGoods(String name, String sn, String order, int page, int pageSize)
  {
    String sql = getBindListSql(0);

    if (order == null) {
      order = "goods_id desc";
    }

    if ((name != null) && (!name.equals(""))) {
      sql = sql + "  and g.name like '%" + name + "%'";
    }

    if ((sn != null) && (!sn.equals(""))) {
      sql = sql + "   and g.sn = '" + sn + "'";
    }

    sql = sql + " order by g." + order;
    Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);

    List<Map> list = (List)webpage.getResult();

    for (Map map : list) {
      List productList = this.packageProductManager.list(Integer.valueOf(map.get("goods_id").toString()).intValue());

      productList = productList == null ? new ArrayList() : productList;
      map.put("productList", productList);
    }

    return webpage;
  }

  public Page pageTrash(String name, String sn, String order, int page, int pageSize)
  {
    String sql = getListSql(1);
    if (order == null) {
      order = "goods_id desc";
    }

    if ((name != null) && (!name.equals(""))) {
      sql = sql + "  and g.name like '%" + name + "%'";
    }

    if ((sn != null) && (!sn.equals(""))) {
      sql = sql + "   and g.sn = '" + sn + "'";
    }

    sql = sql + " order by g." + order;

    Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);

    return webpage;
  }

  public List<GoodsStores> storeWarnGoods(int warnTotal, int page, int pageSize)
  {
    String select_sql = "select gc.name as gc_name,b.name as b_name,g.cat_id,g.goods_id,g.name,g.sn,g.price,g.last_modify,g.market_enable,s.sumstore ";
    String left_sql = " left join " + getTableName("goods") + " g  on s.goodsid = g.goods_id  left join " + getTableName("goods_cat") + " gc on gc.cat_id = g.cat_id left join " + getTableName("brand") + " b on b.brand_id = g.brand_id ";
    List list = new ArrayList();

    String sql_2 = select_sql + " from  (select ss.* from (select goodsid,productid,sum(store) sumstore from " + getTableName("product_store") + "  group by goodsid,productid   ) ss " + "  left join " + getTableName("warn_num") + " wn on wn.goods_id = ss.goodsid  where ss.sumstore <=  (case when (wn.warn_num is not null or wn.warn_num <> 0) then wn.warn_num else ?  end )  ) s  " + left_sql;

    List list_2 = this.daoSupport.queryForList(sql_2, new RowMapper()
    {
      public Object mapRow(ResultSet rs, int arg1)
        throws SQLException
      {
        GoodsStores gs = new GoodsStores();
        gs.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
        gs.setName(rs.getString("name"));
        gs.setSn(rs.getString("sn"));
        gs.setRealstore(Integer.valueOf(rs.getInt("sumstore")));
        gs.setPrice(Double.valueOf(rs.getDouble("price")));
        gs.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
        gs.setBrandname(rs.getString("b_name"));
        gs.setCatname(rs.getString("gc_name"));
        gs.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
        gs.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
        return gs;
      }
    }
    , new Object[] { Integer.valueOf(warnTotal) });

    list.addAll(list_2);

    return list;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer[] ids)
  {
    if (ids == null) {
      return;
    }
    for (Integer id : ids) {
      this.tagManager.saveRels(id, null);
    }
    String id_str = StringUtil.arrayToString(ids, ",");
    String sql = "update  goods set disabled=1  where goods_id in (" + id_str + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public void revert(Integer[] ids)
  {
    if (ids == null)
      return;
    String id_str = StringUtil.arrayToString(ids, ",");
    String sql = "update  goods set disabled=0  where goods_id in (" + id_str + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void clean(Integer[] ids)
  {
    if (ids == null)
      return;
    for (Integer id : ids) {
      this.tagManager.saveRels(id, null);
    }
    this.goodsPluginBundle.onGoodsDelete(ids);
    String id_str = StringUtil.arrayToString(ids, ",");
    String sql = "delete  from goods  where goods_id in (" + id_str + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public List list(Integer[] ids)
  {
    if ((ids == null) || (ids.length == 0))
      return new ArrayList();
    String idstr = StringUtil.arrayToString(ids, ",");
    String sql = "select * from goods where goods_id in(" + idstr + ")";
    return this.baseDaoSupport.queryForList(sql, new Object[0]);
  }

  public GoodsPluginBundle getGoodsPluginBundle()
  {
    return this.goodsPluginBundle;
  }

  public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
    this.goodsPluginBundle = goodsPluginBundle;
  }

  protected Map po2Map(Object po)
  {
    Map poMap = new HashMap();
    Map map = new HashMap();
    try {
      map = BeanUtils.describe(po);
    } catch (Exception ex) {
    }
    Object[] keyArray = map.keySet().toArray();
    for (int i = 0; i < keyArray.length; i++) {
      String str = keyArray[i].toString();
      if ((str != null) && (!str.equals("class")) && 
        (map.get(str) != null)) {
        poMap.put(str, map.get(str));
      }
    }

    return poMap;
  }

  public IPackageProductManager getPackageProductManager() {
    return this.packageProductManager;
  }

  public void setPackageProductManager(IPackageProductManager packageProductManager)
  {
    this.packageProductManager = packageProductManager;
  }

  public Goods getGoods(Integer goods_id) {
    Goods goods = (Goods)this.baseDaoSupport.queryForObject("select * from goods where goods_id=?", Goods.class, new Object[] { goods_id });

    return goods;
  }

  public IGoodsCatManager getGoodsCatManager() {
    return this.goodsCatManager;
  }

  public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
    this.goodsCatManager = goodsCatManager;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void batchEdit() {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String[] ids = request.getParameterValues("goodsidArray");
    String[] names = request.getParameterValues("name");
    String[] prices = request.getParameterValues("price");
    String[] cats = request.getParameterValues("catidArray");
    String[] market_enable = request.getParameterValues("market_enables");
    String[] store = request.getParameterValues("store");
    String[] sord = request.getParameterValues("sord");

    String sql = "";

    for (int i = 0; i < ids.length; i++) {
      sql = "";
      if ((names != null) && (names.length > 0) && 
        (!StringUtil.isEmpty(names[i]))) {
        if (!sql.equals(""))
          sql = sql + ",";
        sql = sql + " name='" + names[i] + "'";
      }

      if ((prices != null) && (prices.length > 0) && 
        (!StringUtil.isEmpty(prices[i]))) {
        if (!sql.equals(""))
          sql = sql + ",";
        sql = sql + " price=" + prices[i];
      }

      if ((cats != null) && (cats.length > 0) && 
        (!StringUtil.isEmpty(cats[i]))) {
        if (!sql.equals(""))
          sql = sql + ",";
        sql = sql + " cat_id=" + cats[i];
      }

      if ((store != null) && (store.length > 0) && 
        (!StringUtil.isEmpty(store[i]))) {
        if (!sql.equals(""))
          sql = sql + ",";
        sql = sql + " store=" + store[i];
      }

      if ((market_enable != null) && (market_enable.length > 0) && 
        (!StringUtil.isEmpty(market_enable[i]))) {
        if (!sql.equals(""))
          sql = sql + ",";
        sql = sql + " market_enable=" + market_enable[i];
      }

      if ((sord != null) && (sord.length > 0) && 
        (!StringUtil.isEmpty(sord[i]))) {
        if (!sql.equals(""))
          sql = sql + ",";
        sql = sql + " sord=" + sord[i];
      }

      sql = "update  goods set " + sql + " where goods_id=?";
      this.baseDaoSupport.execute(sql, new Object[] { ids[i] });
    }
  }

  public Map census()
  {
    String sql = "select count(0) from goods ";
    int allcount = this.baseDaoSupport.queryForInt(sql, new Object[0]);

    sql = "select count(0) from goods where market_enable=1 and  disabled = 0";
    int salecount = this.baseDaoSupport.queryForInt(sql, new Object[0]);

    sql = "select count(0) from goods where market_enable=0 and  disabled = 0";
    int unsalecount = this.baseDaoSupport.queryForInt(sql, new Object[0]);

    sql = "select count(0) from goods where   disabled = 1";
    int disabledcount = this.baseDaoSupport.queryForInt(sql, new Object[0]);

    sql = "select count(0) from comments where   for_comment_id is null  and commenttype='goods' and object_type='discuss'";
    int discusscount = this.baseDaoSupport.queryForInt(sql, new Object[0]);

    sql = "select count(0) from comments where for_comment_id is null  and  commenttype='goods' and object_type='ask'";
    int askcount = this.baseDaoSupport.queryForInt(sql, new Object[0]);

    Map map = new HashMap(2);
    map.put("salecount", Integer.valueOf(salecount));
    map.put("unsalecount", Integer.valueOf(unsalecount));
    map.put("disabledcount", Integer.valueOf(disabledcount));
    map.put("allcount", Integer.valueOf(allcount));
    map.put("discuss", Integer.valueOf(discusscount));
    map.put("ask", Integer.valueOf(askcount));
    return map;
  }

  public List getRecommentList(int goods_id, int cat_id, int brand_id, int num)
  {
    int newNum = num;
    if (newNum % 2 != 0)
      newNum++;
    if (newNum == 0) {
      return null;
    }
    List recommentList = new ArrayList();
    String sql = " SELECT g.* FROM goods g WHERE g.disabled=0 AND g.market_enable=1 ";

    if ((cat_id == 1) || (cat_id == 2))
    {
      List list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (7,8) AND g.brand_id=? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(brand_id), Integer.valueOf(newNum / 2) });

      if ((list1 != null) && (list1.size() >= 0)) {
        int max = list1.size() >= newNum / 2 ? newNum / 2 : list1.size();

        for (int i = 0; i < max; i++) {
          recommentList.add(list1.get(i));
        }
      }

      if (recommentList.size() < newNum / 2) {
        list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (7,8) ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(newNum / 2) });

        if ((list1 != null) && (list1.size() > 0)) {
          int max = list1.size() >= newNum / 2 - recommentList.size() ? newNum / 2 - recommentList.size() : list1.size();

          for (int i = 0; i < max; i++) {
            recommentList.add(list1.get(i));
          }
        }
      }

      int tempCatId = cat_id == 1 ? 2 : 1;
      int max = num - recommentList.size();
      list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id =? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(tempCatId), Integer.valueOf(max) });

      if ((list1 != null) && (list1.size() > 0)) {
        for (int i = 0; i < max; i++) {
          recommentList.add(list1.get(i));
        }
      }

    }
    else if ((cat_id == 7) || (cat_id == 8))
    {
      List list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 1 AND g.brand_id=? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(brand_id), Integer.valueOf(newNum / 2) });

      if ((list1 != null) && (list1.size() >= 0)) {
        int max = list1.size() >= newNum / 2 ? newNum / 2 : list1.size();

        for (int i = 0; i < max; i++) {
          recommentList.add(list1.get(i));
        }
      }

      if (recommentList.size() < newNum / 2) {
        list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 1 ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(newNum / 2) });

        if ((list1 != null) && (list1.size() > 0)) {
          int max = list1.size() >= newNum / 2 - recommentList.size() ? newNum / 2 - recommentList.size() : list1.size();

          for (int i = 0; i < max; i++) {
            recommentList.add(list1.get(i));
          }
        }
      }

      int max = num - recommentList.size();
      list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id =? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(1), Integer.valueOf(max) });

      if ((list1 != null) && (list1.size() > 0)) {
        for (int i = 0; i < max; i++) {
          recommentList.add(list1.get(i));
        }
      }

    }
    else if ((cat_id == 18) || (cat_id == 3) || (cat_id == 4))
    {
      List list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 1 ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(newNum / 2) });

      if ((list1 != null) && (list1.size() > 0)) {
        for (int i = 0; i < list1.size(); i++) {
          recommentList.add(list1.get(i));
        }
      }

      int max = num - recommentList.size();
      list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id =? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(12), Integer.valueOf(max) });

      if ((list1 != null) && (list1.size() > 0)) {
        for (int i = 0; i < max; i++) {
          recommentList.add(list1.get(i));
        }
      }

    }
    else if (cat_id == 6) {
      recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (18,3,4) ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(num) });
    }
    else if (cat_id == 12) {
      recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 12 AND g.goods_id != ? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(goods_id), Integer.valueOf(num) });
    }
    else if ((cat_id == 14) || (cat_id == 15) || (cat_id == 16) || (cat_id == 17)) {
      recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (14,15,16,17) AND g.goods_id != ? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(goods_id), Integer.valueOf(num) });
    }
    else if (cat_id == 19) {
      recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 19 AND g.goods_id != ? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(goods_id), Integer.valueOf(num) });
    }
    else if (cat_id == 9)
    {
      List list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 1 ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(newNum / 2) });

      if ((list1 != null) && (list1.size() > 0)) {
        for (int i = 0; i < list1.size(); i++) {
          recommentList.add(list1.get(i));
        }
      }

      int max = num - recommentList.size();
      list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id =? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(12), Integer.valueOf(max) });

      if ((list1 != null) && (list1.size() > 0)) {
        for (int i = 0; i < max; i++) {
          recommentList.add(list1.get(i));
        }
      }

    }
    else if (cat_id == 10) {
      recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (3,4,18) ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(num) });
    }

    return recommentList;
  }

  public List list()
  {
    String sql = "select * from goods where disabled = 0";
    return this.baseDaoSupport.queryForList(sql, new Object[0]);
  }

  public IMemberPriceManager getMemberPriceManager() {
    return this.memberPriceManager;
  }

  public ITagManager getTagManager() {
    return this.tagManager;
  }

  public void setTagManager(ITagManager tagManager) {
    this.tagManager = tagManager;
  }

  public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
    this.memberPriceManager = memberPriceManager;
  }

  public IMemberLvManager getMemberLvManager() {
    return this.memberLvManager;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager) {
    this.memberLvManager = memberLvManager;
  }

  public void updateField(String filedname, Object value, Integer goodsid)
  {
    this.baseDaoSupport.execute("update goods set " + filedname + "=? where goods_id=?", new Object[] { value, goodsid });
  }

  public Goods getGoodBySn(String goodSn)
  {
    Goods goods = (Goods)this.baseDaoSupport.queryForObject("select * from goods where sn=?", Goods.class, new Object[] { goodSn });

    return goods;
  }

  public IDepotMonitorManager getDepotMonitorManager() {
    return this.depotMonitorManager;
  }

  public void setDepotMonitorManager(IDepotMonitorManager depotMonitorManager) {
    this.depotMonitorManager = depotMonitorManager;
  }

  public Page searchGoods(Integer catid, String name, String sn, Integer market_enable, Integer[] tagid, String order, int page, int pageSize)
  {
    String sql = getListSql(0);

    if (order == null) {
      order = "goods_id desc";
    }

    if ((name != null) && (!name.equals(""))) {
      sql = sql + "  and g.name like '%" + name + "%'";
    }

    if ((sn != null) && (!sn.equals(""))) {
      sql = sql + "   and g.sn = '" + sn + "'";
    }

    if (market_enable != null) {
      sql = sql + " and g.market_enable=" + market_enable;
    }

    if (catid != null) {
      Cat cat = this.goodsCatManager.getById(catid.intValue());
      sql = sql + " and  g.cat_id in(";
      sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
    }

    if ((tagid != null) && (tagid.length > 0)) {
      String tagidstr = StringUtil.arrayToString(tagid, ",");
      sql = sql + " and g.goods_id in(select rel_id from " + getTableName("tag_rel") + " where tag_id in(" + tagidstr + "))";
    }

    sql = sql + " order by g." + order;

    Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);

    return webpage;
  }

  public List listByCat(Integer catid)
  {
    String sql = getListSql(0);
    if (catid.intValue() != 0) {
      Cat cat = this.goodsCatManager.getById(catid.intValue());
      sql = sql + " and  g.cat_id in(";
      sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
    }

    return this.daoSupport.queryForList(sql, new Object[0]);
  }

  public List listByTag(Integer[] tagid)
  {
    String sql = getListSql(0);
    if ((tagid != null) && (tagid.length > 0)) {
      String tagidstr = StringUtil.arrayToString(tagid, ",");
      sql = sql + " and g.goods_id in(select rel_id from " + getTableName("tag_rel") + " where tag_id in(" + tagidstr + "))";
    }
    return this.daoSupport.queryForList(sql, new Object[0]);
  }

  public void incViewCount(Integer goods_id)
  {
    this.baseDaoSupport.execute("update goods set view_count = view_count + 1 where goods_id = ?", new Object[] { goods_id });
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsManager
 * JD-Core Version:    0.6.1
 */