package com.enation.app.shop.core.model.mapper;

import com.enation.app.shop.core.model.support.GoodsView;
import com.enation.app.shop.core.utils.GoodsUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

public class GoodsDetailMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    GoodsView goods = new GoodsView();
    goods.setName(rs.getString("name"));
    goods.setSn(rs.getString("sn"));
    goods.setUnit(rs.getString("unit"));
    goods.setWeight(Double.valueOf(rs.getDouble("weight")));
    goods.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
    goods.setImage_default(rs.getString("image_default"));
    goods.setImage_file(rs.getString("image_file"));
    goods.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
    goods.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
    goods.setPrice(Double.valueOf(rs.getDouble("price")));
    goods.setCreate_time(Long.valueOf(rs.getLong("create_time")));
    goods.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
    goods.setBrand_name(rs.getString("brand_name"));
    goods.setParams(rs.getString("params"));
    goods.setIntro(rs.getString("intro"));
    goods.setBrief(rs.getString("brief"));
    goods.setPage_title(rs.getString("page_title"));
    goods.setMeta_keywords(rs.getString("meta_keywords"));
    goods.setMeta_description(rs.getString("meta_description"));
    goods.setSpecs(rs.getString("specs"));
    List specList = GoodsUtils.getSpecList(goods.getSpecs());
    goods.setSpecList(specList);
    goods.setType_id(Integer.valueOf(rs.getInt("type_id")));
    goods.setBrand_id(Integer.valueOf(rs.getInt("brand_id")));
    goods.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
    goods.setAdjuncts(rs.getString("adjuncts"));
    if ((goods.getAdjuncts() != null) && (goods.getAdjuncts().equals(""))) {
      goods.setAdjuncts(null);
    }
    goods.setStore(Integer.valueOf(rs.getInt("store")));
    goods.setDisabled(Integer.valueOf(rs.getInt("disabled")));
    goods.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));

    Map propMap = new HashMap();

    for (int i = 0; i < 20; i++) {
      String value = rs.getString("p" + (i + 1));
      propMap.put("p" + i, value);
    }
    goods.setPropMap(propMap);
    return goods;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.mapper.GoodsDetailMapper
 * JD-Core Version:    0.6.1
 */