package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Brand;
import com.enation.app.shop.core.model.mapper.BrandMapper;
import com.enation.app.shop.core.service.IBrandManager;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.database.StringMapper;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class BrandManager extends BaseSupport<Brand>
  implements IBrandManager
{
  private IGoodsCatManager goodsCatManager;

  public Page list(String order, int page, int pageSize)
  {
    order = order == null ? " brand_id desc" : order;
    String sql = "select * from brand where disabled=0";
    sql = sql + " order by  " + order;
    Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public List<Map> queryAllTypeNameAndId()
  {
    String sql = "SELECT type_id,name FROM goods_type";
    return this.baseDaoSupport.queryForList(sql, new Object[0]);
  }

  public Page search(int page, int pageSize, String brandname, Integer type_id)
  {
    Page webpage = null;
    String sql = "SELECT b.* FROM " + getTableName("brand") + " b left  join " + getTableName("type_brand") + " tb on b.brand_id=tb.brand_id left  join " + getTableName("goods_type") + " gt on tb.type_id = gt.type_id where b.name  like '%" + brandname + "%' ";
    if (type_id.intValue() != -100) {
      sql = sql + "  and gt.type_id = ? ";
      webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[] { type_id });
    } else {
      webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    }
    System.out.println(sql);
    return webpage;
  }

  public Page listTrash(String order, int page, int pageSize)
  {
    order = order == null ? " brand_id desc" : order;
    String sql = "select * from brand where disabled=1";
    sql = sql + " order by  " + order;
    Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public void revert(String bid)
  {
    if ((bid == null) || (bid.equals(""))) {
      return;
    }
    String sql = "update brand set disabled=0  where brand_id in (" + bid + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public boolean checkUsed(String ids) {
    if ((ids == null) || (ids.equals(""))) return false;
    String sql = "select count(0) from goods where brand_id in (" + ids + ")";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
    if (count > 0) {
      return true;
    }
    return false;
  }

  public void delete(String bid)
  {
    if ((bid == null) || (bid.equals("")))
      return;
    String sql = "update brand set disabled=1  where brand_id in (" + bid + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public void clean(String bid)
  {
    if ((bid == null) || (bid.equals("")))
      return;
    String[] bids = bid.split(",");

    for (int i = 0; i < bids.length; i++) {
      int brand_id = Integer.parseInt(bids[i].trim());
      Brand brand = get(Integer.valueOf(brand_id));
      if (brand != null) {
        String f = brand.getLogo();
        if ((f != null) && (!f.trim().equals(""))) {
          File file = new File(StringUtil.getRootPath() + "/" + f);
          file.delete();
        }
      }
    }

    String sql = "delete  from  brand   where brand_id in (" + bid + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  private String getThumbpath(String file) {
    String fStr = "";
    if (!file.trim().equals("")) {
      String[] arr = file.split("/");
      fStr = "/" + arr[0] + "/" + arr[1] + "/thumb/" + arr[2];
    }
    return fStr;
  }

  public List<Brand> list()
  {
    String sql = "select * from brand where disabled=0";
    List list = this.baseDaoSupport.queryForList(sql, new BrandMapper(), new Object[0]);
    return list;
  }

  public List<Brand> listByTypeId(Integer typeid)
  {
    String sql = "select b.* from " + getTableName("type_brand") + " tb inner join " + getTableName("brand") + " b  on    b.brand_id = tb.brand_id where tb.type_id=? and b.disabled=0";
    List list = this.daoSupport.queryForList(sql, new BrandMapper(), new Object[] { typeid });
    return list;
  }

  public Brand get(Integer brand_id)
  {
    String sql = "select * from brand where brand_id=?";
    Brand brand = (Brand)this.baseDaoSupport.queryForObject(sql, Brand.class, new Object[] { brand_id });

    String logo = brand.getLogo();
    if (logo != null) {
      logo = UploadUtil.replacePath(logo);
    }
    brand.setLogo(logo);
    return brand;
  }

  public Page getGoods(Integer brand_id, int pageNo, int pageSize)
  {
    String sql = "select * from goods where brand_id=? and disabled=0";
    Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { brand_id });

    return page;
  }

  public void add(Brand brand)
  {
    if ((brand.getFile() != null) && (brand.getFileFileName() != null)) {
      brand.setLogo(UploadUtil.upload(brand.getFile(), brand.getFileFileName(), "brand"));
    }
    this.baseDaoSupport.insert("brand", brand);
  }

  private void deleteOldLogo(String logo)
  {
    if (!logo.equals("http://static.enationsfot.com")) {
      logo = UploadUtil.replacePath(logo);
      FileUtil.delete(logo);
    }
  }

  public void update(Brand brand)
  {
    if ((brand.getLogo() != null) && ("".equals(brand.getLogo()))) {
      deleteOldLogo(brand.getLogo());
    }
    if ((brand.getFile() != null) && (brand.getFileFileName() != null)) {
      brand.setLogo(UploadUtil.upload(brand.getFile(), brand.getFileFileName(), "brand"));
    }
    this.baseDaoSupport.update("brand", brand, "brand_id=" + brand.getBrand_id());
  }

  public List<Brand> listByCatId(Integer catid)
  {
    String sql = "select b.* from " + getTableName("brand") + " b ," + getTableName("type_brand") + " tb," + getTableName("goods_cat") + " c where tb.brand_id=b.brand_id and c.type_id=tb.type_id and c.cat_id=?";
    return this.daoSupport.queryForList(sql, Brand.class, new Object[] { catid });
  }

  public List groupByCat()
  {
    List<Map> listCat = this.baseDaoSupport.queryForList("select * from goods_cat where parent_id = 0 order by cat_order", new Object[0]);
    for (Map map : listCat) {
      List list = this.baseDaoSupport.queryForList("select type_id from goods_cat where cat_path like '" + map.get("cat_path").toString() + "%'", new StringMapper(), new Object[0]);
      String types = StringUtil.listToString(list, ",");
      List listid = this.baseDaoSupport.queryForList("select brand_id from type_brand where type_id in (" + types + ")", new StringMapper(), new Object[0]);
      String ids = StringUtil.listToString(listid, ",");
      List listBrand = this.baseDaoSupport.queryForList("select * from brand where brand_id in (" + ids + ")", Brand.class, new Object[0]);
      map.put("listBrand", listBrand);
    }
    return listCat;
  }

  public boolean checkname(String name, Integer brandid) {
    if (name != null) name = name.trim();
    String sql = "select count(0) from brand where name=? and brand_id!=?";
    if (brandid == null) brandid = Integer.valueOf(0);

    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name, brandid });
    if (count > 0) {
      return true;
    }
    return false;
  }

  public IGoodsCatManager getGoodsCatManager() {
    return this.goodsCatManager;
  }

  public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
    this.goodsCatManager = goodsCatManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.BrandManager
 * JD-Core Version:    0.6.1
 */