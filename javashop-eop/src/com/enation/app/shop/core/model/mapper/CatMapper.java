package com.enation.app.shop.core.model.mapper;

import com.enation.app.shop.core.model.Cat;
import com.enation.eop.sdk.utils.UploadUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CatMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    Cat cat = new Cat();
    cat.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
    cat.setCat_order(rs.getInt("cat_order"));
    cat.setCat_path(rs.getString("cat_path"));
    String image = rs.getString("image");
    if (image != null) {
      image = UploadUtil.replacePath(image);
    }
    cat.setImage(image);
    cat.setList_show(rs.getString("list_show"));
    cat.setName(rs.getString("name"));
    cat.setParent_id(Integer.valueOf(rs.getInt("parent_id")));
    cat.setType_id(Integer.valueOf(rs.getInt("type_id")));
    cat.setType_name(rs.getString("type_name"));
    return cat;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.mapper.CatMapper
 * JD-Core Version:    0.6.1
 */