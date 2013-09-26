package com.enation.app.base.core.model;

import com.enation.eop.resource.model.EopProduct;
import com.enation.eop.sdk.utils.UploadUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProductMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    EopProduct product = new EopProduct();
    product.setId(Integer.valueOf(rs.getInt("id")));
    product.setProductid(rs.getString("productid"));
    product.setProduct_name(rs.getString("product_name"));
    product.setAuthor(rs.getString("author"));
    product.setCatid(Integer.valueOf(rs.getInt("catid")));
    product.setColorid(Integer.valueOf(rs.getInt("colorid")));
    product.setCreatetime(Long.valueOf(rs.getLong("createtime")));
    product.setDescript(rs.getString("descript"));
    product.setTypeid(Integer.valueOf(rs.getInt("typeid")));
    product.setPstate(Integer.valueOf(rs.getInt("pstate")));
    String preview = rs.getString("preview");

    preview = UploadUtil.replacePath(preview);
    product.setPreview(preview);
    product.setVersion(rs.getString("version"));
    product.setSort(Integer.valueOf(rs.getInt("sort")));

    return product;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.ProductMapper
 * JD-Core Version:    0.6.1
 */