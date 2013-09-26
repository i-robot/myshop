package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Cat;
import com.enation.app.shop.core.model.GoodsLvPrice;
import com.enation.app.shop.core.plugin.search.GoodsDataFilterBundle;
import com.enation.app.shop.core.plugin.search.GoodsSearchPluginBundle;
import com.enation.app.shop.core.plugin.search.IGoodsDataFilter;
import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
import com.enation.app.shop.core.plugin.search.IGoodsSortFilter;
import com.enation.app.shop.core.plugin.search.IMultiSelector;
import com.enation.app.shop.core.plugin.search.IPutWidgetParamsEvent;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.app.shop.core.service.IGoodsSearchManager2;
import com.enation.app.shop.core.utils.UrlUtils;
import com.enation.eop.processor.core.UrlNotFoundException;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

public class GoodsSearchManager2 extends BaseSupport
  implements IGoodsSearchManager2
{
  private GoodsSearchPluginBundle goodsSearchPluginBundle;
  private GoodsDataFilterBundle goodsDataFilterBundle;
  private IGoodsCatManager goodsCatManager;

  public Map<String, Object> getSelector(String url)
  {
    Cat cat = getCat(url);

    Map selectorMap = new HashMap();

    List<IGoodsSearchFilter> filterList = this.goodsSearchPluginBundle.getPluginList();
    for (IGoodsSearchFilter filter : filterList)
    {
      String urlFragment = UrlUtils.getParamStringValue(url, filter.getFilterId());
      String exSelfurl = UrlUtils.getExParamUrl(url, filter.getFilterId()).replaceAll(".html", "");

      List selectorList = filter.createSelectorList(cat, exSelfurl, urlFragment);

      if (selectorList != null) {
        selectorMap.put(filter.getFilterId(), selectorList);
      }

      if ((filter instanceof IMultiSelector)) {
        Map multiSelector = ((IMultiSelector)filter).createMultiSelector(cat, exSelfurl, urlFragment);
        if (multiSelector != null)
          selectorMap.put(filter.getFilterId(), multiSelector);
      }
    }
    return selectorMap;
  }

  public void putParams(Map<String, Object> params, String url)
  {
    List<IGoodsSearchFilter> filterList = this.goodsSearchPluginBundle.getPluginList();
    for (IGoodsSearchFilter filter : filterList)
      if ((filter instanceof IPutWidgetParamsEvent)) {
        String urlFragment = UrlUtils.getParamStringValue(url, filter.getFilterId());
        String exSelfurl = UrlUtils.getExParamUrl(url, filter.getFilterId()).replaceAll(".html", "");
        IPutWidgetParamsEvent event = (IPutWidgetParamsEvent)filter;
        event.putParams(params, urlFragment, exSelfurl);
      }
  }

  public Cat getCat(String url)
  {
    Cat cat = null;
    String catidstr = UrlUtils.getParamStringValue(url, "cat");
    if ((!StringUtil.isEmpty(catidstr)) && (!"0".equals(catidstr))) {
      Integer catid = Integer.valueOf(catidstr);
      cat = this.goodsCatManager.getById(catid.intValue());
      if (cat == null) {
        throw new UrlNotFoundException();
      }
    }

    return cat;
  }

  public Page search(int pageNo, int pageSize, String url) {
    List list = list(pageNo, pageSize, url);
    int count = count(url);
    Page webPage = new Page(0L, count, pageSize, list);
    return webPage;
  }

  public List list(int pageNo, int pageSize, String url)
  {
    StringBuffer sql = new StringBuffer();
    sql.append("select g.* from ");
    sql.append(getTableName("goods"));
    sql.append(" g where g.goods_type = 'normal' and g.disabled=0 and market_enable=1 ");

    filterTerm(sql, url);

    RowMapper mapper = new RowMapper()
    {
      public Object mapRow(ResultSet rs, int arg1) throws SQLException
      {
        Map goodsMap = new HashMap();

        goodsMap.put("name", rs.getString("name"));
        goodsMap.put("goods_id", Integer.valueOf(rs.getInt("goods_id")));
        goodsMap.put("image_default", rs.getString("image_default"));
        goodsMap.put("mktprice", Double.valueOf(rs.getDouble("mktprice")));
        goodsMap.put("price", Double.valueOf(rs.getDouble("price")));
        goodsMap.put("create_time", Long.valueOf(rs.getLong("create_time")));
        goodsMap.put("last_modify", Long.valueOf(rs.getLong("last_modify")));
        goodsMap.put("type_id", Integer.valueOf(rs.getInt("type_id")));
        goodsMap.put("store", Integer.valueOf(rs.getInt("store")));
        goodsMap.put("have_spec", Integer.valueOf(rs.getInt("have_spec")));
        goodsMap.put("sn", rs.getString("sn"));
        goodsMap.put("intro", rs.getString("intro"));
        goodsMap.put("cat_id", Integer.valueOf(rs.getInt("cat_id")));
        goodsMap.put("istejia", Integer.valueOf(rs.getInt("istejia")));

        GoodsSearchManager2.this.filterGoods(goodsMap, rs);

        return goodsMap;
      }
    };
    List goodslist = this.daoSupport.queryForList(sql.toString(), pageNo, pageSize, mapper);

    return goodslist;
  }

  private final void filterGoods(Map<String, Object> goods, ResultSet rs)
  {
    List<IGoodsDataFilter> filterList = this.goodsDataFilterBundle.getPluginList();
    if (filterList == null) return;
    for (IGoodsDataFilter filter : filterList)
      filter.filter(goods, rs);
  }

  private void applyMemPrice(List<Map> proList, List<GoodsLvPrice> memPriceList, double discount)
  {
    for (Map pro : proList) {
      double price = Double.valueOf(pro.get("price").toString()).doubleValue() * discount;
      for (GoodsLvPrice lvPrice : memPriceList) {
        if (((Integer)pro.get("product_id")).intValue() == lvPrice.getProductid()) {
          price = lvPrice.getPrice().doubleValue();
        }
      }

      pro.put("price", Double.valueOf(price));
    }
  }

  private int count(String url)
  {
    StringBuffer sql = new StringBuffer("select count(0) from " + getTableName("goods") + " g where g.disabled=0 and market_enable=1 ");

    filterTerm(sql, url);
    return this.daoSupport.queryForInt(sql.toString(), new Object[0]);
  }

  private String noSpace(String text) {
    String[] s = text.split(" ");
    String r = "";
    for (int i = 0; i < s.length; i++) {
      if (!"".equals(s[i]))
        r = r + s[i];
    }
    return r;
  }

  private void filterTerm(StringBuffer sql, String url)
  {
    Cat cat = getCat(url);

    StringBuffer sortStr = new StringBuffer();

    List<IGoodsSearchFilter> filterList = this.goodsSearchPluginBundle.getPluginList();
    for (IGoodsSearchFilter filter : filterList) {
      String urlFragment = UrlUtils.getParamStringValue(url, filter.getFilterId());
      if ((filter instanceof IGoodsSortFilter))
        filter.filter(sortStr, cat, urlFragment);
      else {
        filter.filter(sql, cat, urlFragment);
      }
    }
    if (!noSpace(sql.toString()).startsWith("selectcount"))
      sql.append(sortStr);
  }

  public GoodsSearchPluginBundle getGoodsSearchPluginBundle()
  {
    return this.goodsSearchPluginBundle;
  }

  public void setGoodsSearchPluginBundle(GoodsSearchPluginBundle goodsSearchPluginBundle)
  {
    this.goodsSearchPluginBundle = goodsSearchPluginBundle;
  }

  public IGoodsCatManager getGoodsCatManager()
  {
    return this.goodsCatManager;
  }

  public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
  {
    this.goodsCatManager = goodsCatManager;
  }

  public GoodsDataFilterBundle getGoodsDataFilterBundle()
  {
    return this.goodsDataFilterBundle;
  }

  public void setGoodsDataFilterBundle(GoodsDataFilterBundle goodsDataFilterBundle)
  {
    this.goodsDataFilterBundle = goodsDataFilterBundle;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsSearchManager2
 * JD-Core Version:    0.6.1
 */