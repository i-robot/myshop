package com.enation.app.shop.core.model.mapper;

import com.enation.app.shop.core.model.SpecValue;
import com.enation.eop.sdk.utils.UploadUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SpecValueMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    SpecValue specValue = new SpecValue();
    specValue.setSpec_id(Integer.valueOf(rs.getInt("spec_id")));
    String spec_img = rs.getString("spec_image");
    if (spec_img != null) {
      spec_img = UploadUtil.replacePath(spec_img);
    }
    specValue.setSpec_image(spec_img);
    specValue.setSpec_order(Integer.valueOf(rs.getInt("spec_order")));
    specValue.setSpec_type(Integer.valueOf(rs.getInt("spec_type")));
    specValue.setSpec_value(rs.getString("spec_value"));
    specValue.setSpec_value_id(Integer.valueOf(rs.getInt("spec_value_id")));
    return specValue;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.mapper.SpecValueMapper
 * JD-Core Version:    0.6.1
 */