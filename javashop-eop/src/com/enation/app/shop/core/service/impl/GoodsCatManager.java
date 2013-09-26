package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Cat;
import com.enation.app.shop.core.model.mapper.CatMapper;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class GoodsCatManager extends BaseSupport<Cat>
  implements IGoodsCatManager
{
  public boolean checkname(String name, Integer catid)
  {
    if (name != null) name = name.trim();
    String sql = "select count(0) from goods_cat where name=? and cat_id!=?";
    if (catid == null) {
      catid = Integer.valueOf(0);
    }

    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name, catid });
    if (count > 0) return true;
    return false;
  }

  public int delete(int catId) {
    String sql = "select count(0) from goods_cat where parent_id = ?";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(catId) });
    if (count > 0) {
      return 1;
    }

    sql = "select count(0) from goods where cat_id = ?";
    count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(catId) });
    if (count > 0) {
      return 2;
    }
    sql = "delete from  goods_cat   where cat_id=?";
    this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(catId) });

    return 0;
  }

  public Cat getById(int catId)
  {
    String sql = "select * from goods_cat  where cat_id=?";
    Cat cat = (Cat)this.baseDaoSupport.queryForObject(sql, Cat.class, new Object[] { Integer.valueOf(catId) });
    if (cat != null) {
      String image = cat.getImage();
      if (image != null) {
        image = UploadUtil.replacePath(image);
        cat.setImage(image);
      }
    }
    return cat;
  }

  public List<Cat> listChildren(Integer catId)
  {
    String sql = "select c.*,'' type_name from goods_cat c where parent_id=?";
    return this.baseDaoSupport.queryForList(sql, new CatMapper(), new Object[] { catId });
  }

  public List<Cat> listAllChildren(Integer catId)
  {
    String tableName = getTableName("goods_cat");
    String sql = "select c.*,t.name as type_name  from  " + tableName + " c  left join " + getTableName("goods_type") + " t on c.type_id = t.type_id  " + " order by parent_id,cat_order";

    List<Cat> allCatList = this.daoSupport.queryForList(sql, new CatMapper(), new Object[0]);
    List topCatList = new ArrayList();
    if (catId.intValue() != 0) {
      Cat cat = getById(catId.intValue());
      topCatList.add(cat);
    }
    for (Cat cat : allCatList) {
      if (cat.getParent_id().compareTo(catId) == 0) {
        if (this.logger.isDebugEnabled()) {
          this.logger.debug("发现子[" + cat.getName() + "-" + cat.getCat_id() + "]" + cat.getImage());
        }
        List children = getChildren(allCatList, cat.getCat_id());
        cat.setChildren(children);
        topCatList.add(cat);
      }
    }
    return topCatList;
  }

  private List<Cat> getChildren(List<Cat> catList, Integer parentid)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("查找[" + parentid + "]的子");
    }
    List children = new ArrayList();
    for (Cat cat : catList) {
      if (cat.getParent_id().compareTo(parentid) == 0) {
        if (this.logger.isDebugEnabled()) {
          this.logger.debug(cat.getName() + "-" + cat.getCat_id() + "是子");
        }
        cat.setChildren(getChildren(catList, cat.getCat_id()));
        children.add(cat);
      }
    }
    return children;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void saveAdd(Cat cat)
  {
    this.baseDaoSupport.insert("goods_cat", cat);
    int cat_id = this.baseDaoSupport.getLastId("goods_cat");
    String sql = "";

    if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0)) {
      sql = "select * from goods_cat  where cat_id=?";
      Cat parent = (Cat)this.baseDaoSupport.queryForObject(sql, Cat.class, new Object[] { cat.getParent_id() });

      cat.setCat_path(parent.getCat_path() + cat_id + "|");
    } else {
      cat.setCat_path(cat.getParent_id() + "|" + cat_id + "|");
    }

    sql = "update goods_cat set  cat_path=? where  cat_id=?";
    this.baseDaoSupport.execute(sql, new Object[] { cat.getCat_path(), Integer.valueOf(cat_id) });
  }

  public void update(Cat cat)
  {
    checkIsOwner(cat.getCat_id());

    if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0))
    {
      String sql = "select * from goods_cat where cat_id=?";
      Cat parent = (Cat)this.baseDaoSupport.queryForObject(sql, Cat.class, new Object[] { cat.getParent_id() });

      cat.setCat_path(parent.getCat_path() + cat.getCat_id() + "|");
    }
    else {
      cat.setCat_path(cat.getParent_id() + "|" + cat.getCat_id() + "|");
    }

    HashMap map = new HashMap();
    map.put("name", cat.getName());
    map.put("parent_id", cat.getParent_id());
    map.put("cat_order", Integer.valueOf(cat.getCat_order()));
    map.put("type_id", cat.getType_id());
    map.put("cat_path", cat.getCat_path());
    map.put("list_show", cat.getList_show());
    map.put("image", StringUtil.isEmpty(cat.getImage().trim()) ? null : cat.getImage());
    this.baseDaoSupport.update("goods_cat", map, "cat_id=" + cat.getCat_id());
  }

  protected void checkIsOwner(Integer catId)
  {
  }

  public void saveSort(int[] cat_ids, int[] cat_sorts)
  {
    String sql = "";
    if (cat_ids != null)
      for (int i = 0; i < cat_ids.length; i++) {
        sql = "update  goods_cat  set cat_order=? where cat_id=?";
        this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(cat_sorts[i]), Integer.valueOf(cat_ids[i]) });
      }
  }

  public List getNavpath(int catId)
  {
    return null;
  }

  public List<Cat> getParents(int catid)
  {
    Cat cat = getById(catid);
    String path = cat.getCat_path();
    path = path.substring(0, path.length() - 1);
    path = path.replace("|", ",");

    String sql = "select * from goods_cat where cat_id in(" + path + ") order by cat_id asc";

    return this.baseDaoSupport.queryForList(sql, Cat.class, new Object[0]);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsCatManager
 * JD-Core Version:    0.6.1
 */