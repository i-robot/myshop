package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.ArticleCat;
import com.enation.app.shop.core.service.IArticleCatManager;
import com.enation.app.shop.core.service.IArticleManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ArticleCatManager extends BaseSupport
  implements IArticleCatManager
{
  private IArticleManager articleManager;

  public ArticleCat getById(int cat_id)
  {
    return (ArticleCat)this.baseDaoSupport.queryForObject("select * from article_cat where cat_id=?", ArticleCat.class, new Object[] { Integer.valueOf(cat_id) });
  }

  public void saveAdd(ArticleCat cat)
  {
    if (cat.getParent_id() == null) {
      cat.setParent_id(Integer.valueOf(0));
    }
    else if ((cat.getCat_id() != null) && (cat.getParent_id() == cat.getCat_id())) {
      throw new ArticleCatRuntimeException(2);
    }

    if (cat.getName() != null) {
      String sql = "select count(0) from article_cat where name = '" + cat.getName() + "' and parent_id=" + cat.getParent_id();
      int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
      if (count > 0)
        throw new ArticleCatRuntimeException(1);
    }
    this.baseDaoSupport.insert("article_cat", cat);
    int cat_id = this.baseDaoSupport.getLastId("article_cat");

    String sql = "";

    if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0)) {
      sql = "select * from article_cat where cat_id=?";
      ArticleCat parent = (ArticleCat)this.baseDaoSupport.queryForObject(sql, ArticleCat.class, new Object[] { cat.getParent_id() });

      if (parent != null)
        cat.setCat_path(parent.getCat_path() + cat_id + "|");
    }
    else {
      cat.setCat_path(cat.getParent_id() + "|" + cat_id + "|");
    }

    sql = "update article_cat set cat_path='" + cat.getCat_path() + "' where cat_id=" + cat_id;

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public void update(ArticleCat cat)
  {
    if (cat.getParent_id() == null) {
      cat.setParent_id(Integer.valueOf(0));
    }
    else if ((cat.getCat_id() != null) && (cat.getParent_id() == cat.getCat_id())) {
      throw new ArticleCatRuntimeException(2);
    }

    if (cat.getName() != null) {
      String sql = "select count(0) from article_cat where cat_id != " + cat.getCat_id() + " and name = '" + cat.getName() + "' and parent_id=" + cat.getParent_id();
      int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
      if (count > 0) {
        throw new ArticleCatRuntimeException(1);
      }
    }
    if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0)) {
      String sql = "select * from article_cat where cat_id=?";
      ArticleCat parent = (ArticleCat)this.baseDaoSupport.queryForObject(sql, ArticleCat.class, new Object[] { cat.getParent_id() });

      if (parent != null)
        cat.setCat_path(parent.getCat_path() + cat.getCat_id() + "|");
    }
    else {
      cat.setCat_path(cat.getParent_id() + "|" + cat.getCat_id() + "|");
    }

    HashMap map = new HashMap();
    map.put("name", cat.getName());
    map.put("parent_id", cat.getParent_id());
    map.put("cat_order", Integer.valueOf(cat.getCat_order()));
    map.put("cat_path", cat.getCat_path());

    this.baseDaoSupport.update("article_cat", map, "cat_id=" + cat.getCat_id());
  }

  public int delete(int cat_id)
  {
    String sql = "select count(0) from article_cat where parent_id = ?";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(cat_id) });

    String sqla = "select count(0) from article where cat_id = ?";
    int counta = this.baseDaoSupport.queryForInt(sqla, new Object[] { Integer.valueOf(cat_id) });

    if ((count > 0) || (counta > 0)) {
      return 1;
    }

    sql = "delete from article_cat where cat_id=" + cat_id;

    this.baseDaoSupport.execute(sql, new Object[0]);

    return 0;
  }

  public void saveSort(int[] cat_ids, int[] cat_sorts)
  {
    String sql = "";
    if ((cat_ids != null) && (cat_sorts != null) && (cat_ids.length == cat_sorts.length))
      for (int i = 0; i < cat_ids.length; i++) {
        sql = "update article_cat set cat_order=" + cat_sorts[i] + " where cat_id=" + cat_ids[i];

        this.baseDaoSupport.execute(sql, new Object[0]);
      }
  }

  public List listChildById(Integer cat_id)
  {
    String sql = "select * from  article_cat  order by parent_id,cat_order";
    List<ArticleCat> allCatList = this.baseDaoSupport.queryForList(sql, ArticleCat.class, new Object[0]);
    List topCatList = new ArrayList();
    for (ArticleCat cat : allCatList) {
      if (cat.getParent_id().compareTo(cat_id) == 0) {
        if (this.logger.isDebugEnabled()) {
          this.logger.debug("发现子[" + cat.getName() + "-" + cat.getCat_id() + "]");
        }
        List children = getChildren(allCatList, cat.getCat_id());
        cat.setChildren(children);
        topCatList.add(cat);
      }
    }
    return topCatList;
  }

  private List<ArticleCat> getChildren(List<ArticleCat> catList, Integer parentid) {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("查找[" + parentid + "]的子");
    }
    List children = new ArrayList();
    for (ArticleCat cat : catList) {
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

  public List listHelp(int cat_id)
  {
    int userid = EopContext.getContext().getCurrentSite().getUserid().intValue();
    int siteid = EopContext.getContext().getCurrentSite().getId().intValue();
    String sql = "select cat_id, name  from  article_cat c  where c.parent_id = " + cat_id;

    List<Map> list = this.baseDaoSupport.queryForList(sql, new Object[0]);
    for (Map map : list) {
      List articleList = this.articleManager.listByCatId(Integer.valueOf(map.get("cat_id").toString()));
      map.put("articleList", articleList);
    }
    return list;
  }

  public IArticleManager getArticleManager()
  {
    return this.articleManager;
  }

  public void setArticleManager(IArticleManager articleManager) {
    this.articleManager = articleManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.ArticleCatManager
 * JD-Core Version:    0.6.1
 */