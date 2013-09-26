package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.service.IDlyTypeAreaManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

public class DlyTypeAreaManager extends BaseSupport
  implements IDlyTypeAreaManager
{
  public Map listAllByRegion(String regionid)
  {
    String sql = "select dta.type_id,dta.area_id_group,dta.area_name_group,dta.has_cod,dta.config,dt.name,dt.is_same,dt.config as dt_config  from " + getTableName("dly_type_area") + " dta left join " + getTableName("dly_type") + " dt on dta.type_id = dt.type_id";
    List<Map> typeAreaList = null;
    if (regionid != null) {
      typeAreaList = this.daoSupport.queryForList(sql, new RowMapper()
      {
        public Object mapRow(ResultSet rs, int arg1) throws SQLException
        {
          Map map = new HashMap();
          map.put("type_id", Integer.valueOf(rs.getInt("type_id")));
          map.put("area_name_group", rs.getString("area_name_group"));
          map.put("area_id_group", rs.getString("area_id_group"));
          map.put("has_cod", Integer.valueOf(rs.getInt("has_cod")));
          map.put("dta_config", JSONObject.fromObject(rs.getString("config")));

          map.put("name", rs.getString("name"));
          map.put("is_same", Integer.valueOf(rs.getInt("is_same")));
          map.put("config", JSONObject.fromObject(rs.getString("dt_config")));

          return map;
        }
      }
      , new Object[0]);

      if ((typeAreaList != null) && (typeAreaList.size() != 0)) {
        Map area = null;
        for (Map map : typeAreaList) {
          if (map.get("area_id_group").toString().indexOf("," + regionid.trim() + ",") != -1) {
            int cod = queryByrdgion(regionid);
            map.put("has_cod", Integer.valueOf(cod));
            area = map;
          }

        }

        if (area == null) {
          return queryOtherRegions(Integer.valueOf(1), regionid);
        }
        return area;
      }

      return null;
    }

    return null;
  }

  public int queryByrdgion(String regionid)
  {
    int cod = 0;
    if (!StringUtil.isEmpty(regionid)) {
      cod = this.baseDaoSupport.queryForInt("select cod from regions where region_id = " + regionid, new Object[0]);
    }
    return cod;
  }

  public Map queryOtherRegions(Integer type_id, String regionid)
  {
    String sql = "select * from dly_type where type_id = " + type_id;
    List typeAreaList = this.baseDaoSupport.queryForList(sql, new RowMapper()
    {
      public Object mapRow(ResultSet rs, int arg1) throws SQLException
      {
        Map map = new HashMap();
        map.put("type_id", Integer.valueOf(rs.getInt("type_id")));
        map.put("area_name_group", null);
        map.put("area_id_group", null);
        map.put("has_cod", Integer.valueOf(rs.getInt("has_cod")));
        map.put("dta_config", JSONObject.fromObject(rs.getString("config")));
        map.put("name", rs.getString("name"));
        map.put("is_same", Integer.valueOf(rs.getInt("is_same")));
        return map;
      }
    }
    , new Object[0]);

    Map area = null;
    if ((typeAreaList != null) && (!typeAreaList.isEmpty())) {
      area = (Map)typeAreaList.get(0);
      int cod = queryByrdgion(regionid);
      area.put("has_cod", Integer.valueOf(cod));
    }
    return area;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.DlyTypeAreaManager
 * JD-Core Version:    0.6.1
 */