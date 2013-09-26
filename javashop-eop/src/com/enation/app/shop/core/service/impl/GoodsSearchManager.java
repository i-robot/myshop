package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.model.MemberLv;
import com.enation.app.shop.core.model.Attribute;
import com.enation.app.shop.core.model.GoodsLvPrice;
import com.enation.app.shop.core.model.support.GoodsView;
import com.enation.app.shop.core.service.IGoodsSearchManager;
import com.enation.app.shop.core.service.IGoodsTypeManager;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IMemberPriceManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.database.StringMapper;
import com.enation.framework.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

public class GoodsSearchManager extends BaseSupport
  implements IGoodsSearchManager
{
  private IMemberPriceManager memberPriceManager;
  private IMemberLvManager memberLvManager;
  private IGoodsTypeManager goodsTypeManager;

  public Page search(int page, int pageSize, Map<String, String> params)
  {
    String cat_path = (String)params.get("cat_path");
    String order = (String)params.get("order");
    String brandStr = (String)params.get("brandStr");
    String propStr = (String)params.get("propStr");
    String keyword = (String)params.get("keyword");
    String minPrice = (String)params.get("minPrice");
    String maxPrice = (String)params.get("maxPrice");
    String tagids = (String)params.get("tagids");
    String attrStr = (String)params.get("attrStr");

    int typeid = Integer.valueOf((String)params.get("typeid")).intValue();

    List list = listByCatId(typeid, cat_path, page, pageSize, order, brandStr, propStr, keyword, minPrice, maxPrice, tagids, attrStr);

    long count = countByCatId(typeid, cat_path, brandStr, propStr, keyword, minPrice, maxPrice, tagids, attrStr);

    Page webPage = new Page(0L, count, pageSize, list);
    return webPage;
  }

  public List[] getPropListByCat(final int type_id, String cat_path, String brand_str, String propStr, String attrStr)
  {
    List temp_prop_list = this.goodsTypeManager.getAttrListByTypeId(type_id);

    temp_prop_list = temp_prop_list == null ? new ArrayList() : temp_prop_list;

    if ((propStr != null) && (!propStr.equals(""))) {
      String[] s_ar = propStr.split(",");

      for (int i = 0; i < s_ar.length; i++) {
        String[] value = s_ar[i].split("\\_");
        int index = Integer.valueOf(value[0]).intValue();
        Attribute attr = (Attribute)temp_prop_list.get(index);
        attr.getOptionMap()[Integer.valueOf(value[1]).intValue()].put("selected", Integer.valueOf(1));
        if (attr.getType() == 3)
        {
          attr.setHidden(1);
        }
        else attr.setHidden(0);

      }

    }

    List temp_brand_list = null;

    temp_brand_list = this.goodsTypeManager.getBrandListByTypeId(type_id);

    final List propList = temp_prop_list;
    final List brandList = temp_brand_list;

    String sql = "select g.* from " + getTableName("goods") + " g where g.disabled=0 and g.market_enable=1 and g.cat_id in(";
    sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat_path + "%')";

    sql = sql + buildTermForByCat(type_id, brand_str, propStr, attrStr);

    RowMapper mapper = new RowMapper()
    {
      public Object mapRow(ResultSet rs, int arg1) throws SQLException
      {
        GoodsView goods = new GoodsView();

        if (rs.getInt("type_id") == type_id) {
          for (int i = 0; i < 20; i++)
          {
            if (i >= propList.size()) {
              break;
            }
            String value = rs.getString("p" + (i + 1));

            Attribute prop = (Attribute)propList.get(i);

            if ((prop.getType() == 3) && (value != null) && (!value.toString().equals("")))
            {
              int[] nums = prop.getNums();
              int pos = Integer.valueOf(value).intValue();
              nums[pos] += 1;
            }
          }

        }

        for (int i = 0; i < brandList.size(); i++) {
          Map brand = (Map)brandList.get(i);
          if (rs.getInt("brand_id") == ((Integer)brand.get("brand_id")).intValue())
          {
            Object obj_num = brand.get("num");
            if (obj_num == null) {
              obj_num = Integer.valueOf(0);
            }

            int num = Integer.valueOf(obj_num.toString()).intValue();
            num++;
            brand.put("num", Integer.valueOf(num));
          }
        }

        return goods;
      }
    };
    this.daoSupport.queryForList(sql, mapper, new Object[0]);

    List[] props = new List[2];
    props[0] = propList;
    props[1] = brandList;

    return props;
  }

  private List getSpecListByCatId(String cat_path)
  {
    String sql = "select s.* from " + getTableName("product") + " s," + getTableName("goods") + " g  where s.goods_id=g.goods_id  ";

    if (!StringUtil.isEmpty(cat_path)) {
      sql = sql + " and g.cat_id in(";
      sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat_path + "%')";
    }

    List specList = this.daoSupport.queryForList(sql, new Object[0]);

    return specList;
  }

  private List listByCatId(int typeid, String cat_path, int page, int pageSize, String order, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String tagids, String attrStr)
  {
    final List goods_spec_list = getSpecListByCatId(cat_path);

    if ("1".equals(order))
      order = "last_modify desc";
    else if ("1".equals(order))
      order = "last_modify asc";
    else if ("2".equals(order))
      order = "last_modify asc";
    else if ("3".equals(order))
      order = "price desc";
    else if ("4".equals(order))
      order = "price asc";
    else if ("5".equals(order))
      order = "view_count desc";
    else if ("6".equals(order))
      order = "buy_count asc";
    else if ((order == null) || (order.equals("")) || (order.equals("0"))) {
      order = "sord desc";
    }

    String sql = "select g.* from " + getTableName("goods") + " g where g.goods_type = 'normal' and g.disabled=0 and market_enable=1 ";

    if (cat_path != null) {
      sql = sql + " and  g.cat_id in(";
      sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat_path + "%')  ";
    }

    sql = sql + buildTermForByCat(typeid, brand_str, propStr, keyword, minPrice, maxPrice, tagids, attrStr);

    sql = sql + " order by " + order;

    IUserService userService = UserServiceFactory.getUserService();
    final Member member = userService.getCurrentMember();
    List memPriceList = new ArrayList();
    double discount = 1.0D;
    if ((member != null) && (member.getLv_id() != null)) {
      memPriceList = this.memberPriceManager.listPriceByLvid(member.getLv_id().intValue());
      MemberLv lv = this.memberLvManager.get(member.getLv_id());
      discount = lv.getDiscount().intValue() / 100.0D;
      applyMemPrice(goods_spec_list, memPriceList, discount);
    }
    final List<GoodsLvPrice> priceList = memPriceList;
    final double final_discount = discount;

    RowMapper mapper = new RowMapper()
    {
      public Object mapRow(ResultSet rs, int arg1) throws SQLException
      {
        GoodsView goods = new GoodsView();
        goods.setName(rs.getString("name"));
        goods.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
        goods.setImage_default(rs.getString("image_default"));
        goods.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
        goods.setPrice(Double.valueOf(rs.getDouble("price")));

        goods.setCreate_time(Long.valueOf(rs.getLong("create_time")));
        goods.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
        goods.setType_id(Integer.valueOf(rs.getInt("type_id")));
        goods.setStore(Integer.valueOf(rs.getInt("store")));
        List specList = GoodsSearchManager.this.getSpecList(goods.getGoods_id().intValue(), goods_spec_list);

        goods.setSpecList(specList);
        goods.setHasSpec(rs.getInt("have_spec"));

        goods.setSn(rs.getString("sn"));
        goods.setIntro(rs.getString("intro"));
        String image_file = rs.getString("image_file");
        if (image_file != null) {
          image_file = UploadUtil.replacePath(image_file);
          goods.setImage_file(image_file);
        }
        goods.setCat_id(Integer.valueOf(rs.getInt("cat_id")));

        if ((goods.getHasSpec() == 0) && (specList != null) && (!specList.isEmpty())) {
          goods.setProductid((Integer)((Map)specList.get(0)).get("product_id"));
        }
        if ((member != null) && (goods.getProductid() != null)) {
          goods.setPrice(Double.valueOf(goods.getPrice().doubleValue() * final_discount));
          for (GoodsLvPrice lvPrice : priceList) {
            if (goods.getProductid().intValue() == lvPrice.getProductid()) {
              goods.setPrice(lvPrice.getPrice());
            }

          }

        }

        Map propMap = new HashMap();

        for (int i = 0; i < 20; i++) {
          String value = rs.getString("p" + (i + 1));
          propMap.put("p" + (i + 1), value);
        }
        goods.setPropMap(propMap);

        return goods;
      }
    };
    List goodslist = this.daoSupport.queryForList(sql, page, pageSize, mapper);

    return goodslist;
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

  private long countByCatId(int typeid, String cat_path, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String tagids, String attrStr)
  {
    String sql = "select count(0) from " + getTableName("goods") + " g where g.disabled=0 and market_enable=1 ";

    if (cat_path != null) {
      sql = sql + " and g.cat_id in(";
      sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat_path + "%')";
    }

    sql = sql + buildTermForByCat(typeid, brand_str, propStr, keyword, minPrice, maxPrice, tagids, attrStr);

    long count = this.daoSupport.queryForLong(sql, new Object[0]);
    return count;
  }

  private List getSpecList(int goods_id, List specList)
  {
    List list = new ArrayList();

    for (int i = 0; i < specList.size(); i++) {
      Map spec = (Map)specList.get(i);
      Integer temp_id = (Integer)spec.get("goods_id");
      if (temp_id.intValue() == goods_id) {
        list.add(spec);
      }
    }

    return list;
  }

  private String buildTerm(int typeid, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String attrStr)
  {
    StringBuffer sql = new StringBuffer();

    sql.append(buildTermForByCat(typeid, brand_str, propStr, attrStr));

    if (keyword != null) {
      sql.append(" and g.name like '%");
      sql.append(keyword);
      sql.append("%'");
    }

    if (minPrice != null) {
      sql.append(" and  g.price>=");
      sql.append(minPrice);
    }

    if (maxPrice != null) {
      sql.append(" and g.price<=");
      sql.append(maxPrice);
    }

    return sql.toString();
  }

  private String buildTermForByCat(int typeid, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String tagids, String attrStr)
  {
    StringBuffer sql = new StringBuffer(buildTerm(typeid, brand_str, propStr, keyword, minPrice, maxPrice, attrStr));

    if (tagids != null) {
      String filter = goodsIdInTags(tagids);
      filter = filter.equals("") ? "-1" : filter;
      sql.append(" and goods_id in(" + filter + ")");
    }
    return sql.toString();
  }

  private String goodsIdInTags(String tags) {
    String sql = "select rel_id from tag_rel where tag_id in (" + tags + ")";
    List goodsIdList = this.baseDaoSupport.queryForList(sql, new StringMapper(), new Object[0]);
    return StringUtil.listToString(goodsIdList, ",");
  }

  private String buildTermForByCat(int typeid, String brand_str, String propStr, String attrStr)
  {
    StringBuffer sql = new StringBuffer();

    if ((brand_str != null) && (!brand_str.equals(""))) {
      brand_str = "-1," + brand_str.replaceAll("\\_", ",");
      sql.append(" and g.brand_id in(");
      sql.append(brand_str);
      sql.append(")");
    }

    if (!StringUtil.isEmpty(attrStr))
    {
      String[] attrAr = attrStr.split(",");
      for (String attrTerm : attrAr) {
        String[] term = attrTerm.split("\\_");
        if (term.length == 2) {
          sql.append(" and " + term[0] + "=" + term[1]);
        }

      }

    }

    if ((propStr != null) && (!propStr.equals(""))) {
      List prop_list = this.goodsTypeManager.getAttrListByTypeId(typeid);
      prop_list = prop_list == null ? new ArrayList() : prop_list;
      String[] s_ar = propStr.split(",");

      for (int i = 0; i < s_ar.length; i++) {
        String[] value = s_ar[i].split("\\_");
        int index = Integer.valueOf(value[0]).intValue();
        Attribute attr = (Attribute)prop_list.get(index);
        int type = attr.getType();
        if ((type != 2) && (type != 5))
        {
          sql.append(" and g.p" + (index + 1));

          if (type == 1) {
            sql.append(" like'%");
            sql.append(value[1]);
            sql.append("%'");
          }
          if ((type == 3) || (type == 4)) {
            sql.append("='");
            sql.append(value[1]);
            sql.append("'");
          }
        }
      }
    }

    return sql.toString();
  }

  public IGoodsTypeManager getGoodsTypeManager()
  {
    return this.goodsTypeManager;
  }

  public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
    this.goodsTypeManager = goodsTypeManager;
  }

  public IMemberPriceManager getMemberPriceManager()
  {
    return this.memberPriceManager;
  }

  public void setMemberPriceManager(IMemberPriceManager memberPriceManager)
  {
    this.memberPriceManager = memberPriceManager;
  }

  public IMemberLvManager getMemberLvManager()
  {
    return this.memberLvManager;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager)
  {
    this.memberLvManager = memberLvManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsSearchManager
 * JD-Core Version:    0.6.1
 */