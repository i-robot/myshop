package com.enation.app.shop.core.model.mapper;

import com.enation.app.shop.core.model.FreeOffer;
import com.enation.eop.sdk.utils.UploadUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class GiftMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    FreeOffer freeOffer = new FreeOffer();

    freeOffer.setDescript(rs.getString("descript"));
    freeOffer.setDisabled(Integer.valueOf(rs.getInt("disabled")));
    freeOffer.setEnddate(Long.valueOf(rs.getLong("enddate")));
    freeOffer.setFo_category_id(Integer.valueOf(rs.getInt("fo_category_id")));
    freeOffer.setFo_id(Integer.valueOf(rs.getInt("fo_id")));
    freeOffer.setFo_name(rs.getString("fo_name"));
    freeOffer.setLimit_purchases(Integer.valueOf(rs.getInt("limit_purchases")));
    freeOffer.setList_thumb(rs.getString("list_thumb"));
    freeOffer.setLv_ids(rs.getString("lv_ids"));
    String pic = rs.getString("pic");
    if (pic != null) {
      pic = UploadUtil.replacePath(pic);
    }
    freeOffer.setPic(pic);
    freeOffer.setPrice(Double.valueOf(rs.getDouble("price")));
    freeOffer.setPublishable(Integer.valueOf(rs.getInt("publishable")));
    freeOffer.setRecommend(Integer.valueOf(rs.getInt("recommend")));
    freeOffer.setRepertory(Integer.valueOf(rs.getInt("repertory")));
    freeOffer.setScore(Integer.valueOf(rs.getInt("score")));
    freeOffer.setSorder(Integer.valueOf(rs.getInt("sorder")));
    freeOffer.setStartdate(Long.valueOf(rs.getLong("Startdate")));
    freeOffer.setSynopsis(rs.getString("synopsis"));
    freeOffer.setWeight(Double.valueOf(rs.getDouble("weight")));
    return freeOffer;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.mapper.GiftMapper
 * JD-Core Version:    0.6.1
 */