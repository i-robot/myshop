package com.enation.app.shop.core.model.mapper;

import com.enation.app.shop.core.model.Brand;
import com.enation.eop.sdk.utils.UploadUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BrandMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    Brand brand = new Brand();
    brand.setBrand_id(Integer.valueOf(rs.getInt("brand_id")));
    brand.setBrief(rs.getString("brief"));
    String logo = rs.getString("logo");
    if (logo != null) {
      logo = UploadUtil.replacePath(logo);
    }
    brand.setLogo(logo);
    brand.setName(rs.getString("name"));
    brand.setUrl(rs.getString("url"));
    return brand;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.mapper.BrandMapper
 * JD-Core Version:    0.6.1
 */