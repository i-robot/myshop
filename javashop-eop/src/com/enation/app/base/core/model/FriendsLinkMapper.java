package com.enation.app.base.core.model;

import com.enation.eop.sdk.utils.UploadUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FriendsLinkMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    FriendsLink friendsLink = new FriendsLink();
    friendsLink.setLink_id(Integer.valueOf(rs.getInt("link_id")));
    String logo = rs.getString("logo");
    if (logo != null) logo = UploadUtil.replacePath(logo);
    friendsLink.setLogo(logo);
    friendsLink.setName(rs.getString("name"));
    friendsLink.setSort(Integer.valueOf(rs.getInt("sort")));
    friendsLink.setUrl(rs.getString("url"));
    return friendsLink;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.FriendsLinkMapper
 * JD-Core Version:    0.6.1
 */