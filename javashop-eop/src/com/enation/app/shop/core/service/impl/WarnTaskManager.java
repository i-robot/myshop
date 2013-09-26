package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.service.IWarnTaskManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarnTaskManager extends BaseSupport
  implements IWarnTaskManager
{
  public List<Map> listColor(Integer goodsId)
  {
    String sql = new StringBuilder().append("select  pc.productid,pc.color  from ").append(getTableName("product")).append(" p left join ").append(getTableName("product_color")).append(" pc  on p.product_id=pc.productid where p.goods_id=").append(goodsId).toString();
    return this.daoSupport.queryForList(sql, new Object[0]);
  }

  public void saveTask(Map map)
  {
    Goods goods = (Goods)map.get("goods");
    String depotval = map.get("depotval").toString();
    String sphereval = map.get("sphereval").toString();
    String cylinderval = map.get("cylinderval").toString();
    String productval = map.get("productval").toString();
    String[] deptArr = depotval.split(",");
    for (int i = 0; i < deptArr.length; i++) {
      Map tempMap = new HashMap();
      tempMap.put("goodsid", goods.getGoods_id());
      tempMap.put("catid", goods.getCat_id());
      tempMap.put("depotid", deptArr[i]);
      tempMap.put("sphere", sphereval);
      tempMap.put("cylinder", cylinderval);
      tempMap.put("flag", Integer.valueOf(1));
      tempMap.put("productids", productval);
      this.baseDaoSupport.insert("warn_task", tempMap);
    }
  }

  public Page listAll(Integer page, Integer pageSize) {
    String sql = new StringBuilder().append("SELECT d.name as depotname,g.sn,g.name,gc.name as catname,w.* FROM ").append(getTableName("warn_task")).append(" w  ").append(" left join ").append(getTableName("goods")).append(" g on w.goodsid = g.goods_id ").append(" left join ").append(getTableName("goods_cat")).append(" gc on w.catid = gc.cat_id ").append(" left join ").append(getTableName("depot")).append(" d on d.id = w.depotid ").toString();

    Page webpage = this.daoSupport.queryForPage(sql, page.intValue(), pageSize.intValue(), new Object[0]);

    List<Map> list = (List)webpage.getResult();
    for (Map map : list) {
      StringBuilder product_color = new StringBuilder("");
      if ((map.get("catid").toString().equals("3")) || (map.get("catid").toString().equals("4")) || (map.get("catid").toString().equals("12")) || (map.get("catid").toString().equals("18"))) {
        String[] productids = map.get("productids").toString().split(",");
        int flag = 0;
        for (String productid : productids) {
          if (flag != 0) {
            product_color.append(",");
          }
          product_color.append(this.baseDaoSupport.queryForString(new StringBuilder().append("select color from product_color where productid  =").append(productid).toString()));
          flag++;
        }
        map.put("color", product_color.toString());
      }

      StringBuilder glasphere = new StringBuilder("");
      if ((map.get("catid").toString().equals("6")) && (!map.get("sphere").equals(""))) {
        String[] spheres = map.get("sphere").toString().split(",");
        String[] sylinderes = map.get("cylinder").toString().split("\\|");
        if ((sylinderes != null) && (sylinderes.length > 0)) {
          for (int i = 0; i < spheres.length; i++) {
            sylinderes[i] = sylinderes[i].substring(0, sylinderes[i].lastIndexOf(","));
            glasphere.append(new StringBuilder().append("[度数：").append(spheres[i]).append(",散光：").append(sylinderes[i]).append("]").toString());
          }
        }
        map.put("glasses_sphere", glasphere.toString());
      }
    }
    return webpage;
  }

  public Page listdepot(Integer depotId, String name, String sn, int page, int pageSize)
  {
    String sql = new StringBuilder().append("select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name,wt.id as task_id,wt.productids,wt.sphere,wt.cylinder from ").append(getTableName("goods")).append(" g left join ").append(getTableName("goods_cat")).append(" c on g.cat_id=c.cat_id left join ").append(getTableName("brand")).append(" b on g.brand_id = b.brand_id and b.disabled=0 left join ").append(getTableName("goods_type")).append(" t on g.type_id =t.type_id  left join ").append(getTableName("warn_task")).append(" wt  on g.goods_id=wt.goodsid ").append(" where wt.flag=1 and g.goods_type = 'normal' and g.disabled=0 and wt.depotid=").append(depotId).toString();

    if ((name != null) && (!name.equals(""))) {
      name = name.trim();
      String[] keys = name.split("\\s");

      for (String key : keys) {
        sql = new StringBuilder().append(sql).append(" and g.name like '%").toString();
        sql = new StringBuilder().append(sql).append(key).toString();
        sql = new StringBuilder().append(sql).append("%'").toString();
      }
    }

    if ((sn != null) && (!sn.equals(""))) {
      sql = new StringBuilder().append(sql).append("   and g.sn = '").append(sn).append("'").toString();
    }

    Page webpage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[0]);
    List<Map> list = (List)webpage.getResult();
    for (Map map : list) {
      StringBuilder product_color = new StringBuilder("");
      if ((map.get("cat_id").toString().equals("3")) || (map.get("cat_id").toString().equals("4")) || (map.get("cat_id").toString().equals("12")) || (map.get("cat_id").toString().equals("18"))) {
        String[] productids = map.get("productids").toString().split(",");
        int flag = 0;
        for (String productid : productids) {
          if (flag != 0) {
            product_color.append(",");
          }
          product_color.append(this.baseDaoSupport.queryForString(new StringBuilder().append("select color from product_color where productid  =").append(productid).toString()));
          flag++;
        }
        map.put("color", product_color.toString());
      }

      StringBuilder glasphere = new StringBuilder("");
      if ((map.get("cat_id").toString().equals("6")) && (!map.get("sphere").equals(""))) {
        String[] spheres = map.get("sphere").toString().split(",");
        String[] sylinderes = map.get("cylinder").toString().split("\\|");
        for (int i = 0; i < spheres.length; i++) {
          sylinderes[i] = sylinderes[i].substring(0, sylinderes[i].lastIndexOf(","));
          glasphere.append(new StringBuilder().append("[度数：").append(spheres[i]).append(",散光：").append(sylinderes[i]).append("]").toString());
        }
        map.put("glasses_sphere", glasphere.toString());
      }
    }
    return webpage;
  }

  public Map listTask(Integer taskId)
  {
    String sql = "select wt.* from warn_task wt where wt.id=?";
    return this.baseDaoSupport.queryForMap(sql, new Object[] { taskId });
  }

  public Integer getProductId(Integer goodsid) {
    String sql = "select product_id from product where goods_id = ?";
    Integer productid = Integer.valueOf(this.baseDaoSupport.queryForInt(sql, new Object[] { goodsid }));
    return productid;
  }

  public void updateTask(Integer taskId) {
    this.baseDaoSupport.execute("update warn_task set flag=2 where id=? ", new Object[] { taskId });
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.WarnTaskManager
 * JD-Core Version:    0.6.1
 */