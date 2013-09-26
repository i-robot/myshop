package com.enation.app.shop.core.model.mapper;

import com.enation.app.shop.core.model.support.GoodsView;
import com.enation.eop.sdk.utils.UploadUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

public class GoodsListMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
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
    goods.setPoint(Integer.valueOf(rs.getInt("point")));
    goods.setStore(Integer.valueOf(rs.getInt("store")));
    goods.setCat_id(Integer.valueOf(rs.getInt("cat_id")));

    goods.setSn(rs.getString("sn"));
    goods.setIntro(rs.getString("intro"));
    goods.setStore(Integer.valueOf(rs.getInt("store")));
    goods.setImage_file(UploadUtil.replacePath(rs.getString("image_file")));

    Map propMap = new HashMap();

    for (int i = 0; i < 20; i++) {
      String value = rs.getString("p" + (i + 1));
      propMap.put("p" + (i + 1), value);
    }
    goods.setPropMap(propMap);

    return goods;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.mapper.GoodsListMapper
 * JD-Core Version:    0.6.1
 */