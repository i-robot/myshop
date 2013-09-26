package com.enation.app.base.core.model;

import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.model.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

public class DataLogMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    DataLog datalog = new DataLog();
    datalog.setContent(rs.getString("content"));
    datalog.setDateline(Long.valueOf(rs.getLong("dateline")));
    datalog.setDomain(rs.getString("domain"));
    datalog.setId(Integer.valueOf(rs.getInt("id")));
    datalog.setLogtype(rs.getString("logtype"));
    datalog.setOptype(rs.getString("optype"));
    String pics = rs.getString("pics");
    datalog.setPics(pics);
    datalog.setSitename(rs.getString("sitename"));
    datalog.setSiteid(Integer.valueOf(rs.getInt("siteid")));
    datalog.setUserid(Integer.valueOf(rs.getInt("userid")));
    datalog.setUrl(rs.getString("url"));

    if ((pics != null) && (!"".equals(pics))) {
      List imgList = new ArrayList();
      String[] picar = StringUtils.split(pics, ",");

      for (String picstr : picar) {
        String[] pic = picstr.split("\\|");

        String thumbpic = pic[0];
        String originalpic = pic[1];

        if (thumbpic != null) {
          thumbpic = thumbpic.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN + "/user/" + datalog.getUserid() + "/" + datalog.getSiteid());
        }

        if (originalpic != null) {
          originalpic = originalpic.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN + "/user/" + datalog.getUserid() + "/" + datalog.getSiteid());
        }
        Image image = new Image();
        image.setOriginal(originalpic);
        image.setThumb(thumbpic);
        imgList.add(image);
      }
      datalog.setPicList(imgList);
    }

    return datalog;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.DataLogMapper
 * JD-Core Version:    0.6.1
 */