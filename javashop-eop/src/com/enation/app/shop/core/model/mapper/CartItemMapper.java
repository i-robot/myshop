package com.enation.app.shop.core.model.mapper;

import com.enation.app.shop.core.model.support.CartItem;
import com.enation.eop.sdk.utils.UploadUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CartItemMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int rowNum)
    throws SQLException
  {
    CartItem cartItem = new CartItem();
    cartItem.setId(Integer.valueOf(rs.getInt("cart_id")));
    cartItem.setProduct_id(Integer.valueOf(rs.getInt("product_id")));
    cartItem.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
    cartItem.setName(rs.getString("name"));
    cartItem.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
    cartItem.setPrice(Double.valueOf(rs.getDouble("price")));
    cartItem.setCoupPrice(Double.valueOf(rs.getDouble("price")));
    cartItem.setCatid(rs.getInt("catid"));

    cartItem.setIstejia(Integer.valueOf(rs.getInt("istejia")));
    cartItem.setNo_discount(Integer.valueOf(rs.getInt("no_discount")));

    String image_default = rs.getString("image_default");

    if (image_default != null) {
      image_default = UploadUtil.replacePath(image_default);
    }
    cartItem.setImage_default(image_default);
    cartItem.setNum(rs.getInt("num"));
    cartItem.setPoint(Integer.valueOf(rs.getInt("point")));
    cartItem.setItemtype(Integer.valueOf(rs.getInt("itemtype")));

    cartItem.setAddon(rs.getString("addon"));

    if (cartItem.getItemtype().intValue() == 2) {
      cartItem.setLimitnum(Integer.valueOf(rs.getInt("limitnum")));
    }

    cartItem.setSn(rs.getString("sn"));

    if (cartItem.getItemtype().intValue() == 0) {
      String specs = rs.getString("specs");
      cartItem.setSpecs(specs);
    }

    cartItem.setUnit(rs.getString("unit"));
    return cartItem;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.mapper.CartItemMapper
 * JD-Core Version:    0.6.1
 */