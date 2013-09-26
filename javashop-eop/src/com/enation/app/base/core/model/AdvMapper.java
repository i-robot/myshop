package com.enation.app.base.core.model;

import com.enation.eop.sdk.utils.UploadUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AdvMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    Adv adv = new Adv();
    adv.setAcid(Integer.valueOf(rs.getInt("acid")));
    adv.setAid(Integer.valueOf(rs.getInt("aid")));
    adv.setAname(rs.getString("aname"));
    String atturl = rs.getString("atturl");
    if (atturl != null) atturl = UploadUtil.replacePath(atturl);
    adv.setAtturl(atturl);
    adv.setAtype(Integer.valueOf(rs.getInt("atype")));
    adv.setBegintime(Long.valueOf(rs.getLong("begintime")));
    adv.setClickcount(Integer.valueOf(rs.getInt("clickcount")));
    adv.setCompany(rs.getString("company"));
    adv.setContact(rs.getString("contact"));
    adv.setDisabled(rs.getString("disabled"));
    adv.setEndtime(Long.valueOf(rs.getLong("endtime")));
    adv.setIsclose(Integer.valueOf(rs.getInt("isclose")));
    adv.setLinkman(rs.getString("linkman"));
    adv.setUrl(rs.getString("url"));

    adv.setCname(rs.getString("cname"));

    return adv;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.AdvMapper
 * JD-Core Version:    0.6.1
 */