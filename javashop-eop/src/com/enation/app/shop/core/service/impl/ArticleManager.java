package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Article;
import com.enation.app.shop.core.service.IArticleManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.database.StringMapper;
import java.util.List;

public class ArticleManager extends BaseSupport
  implements IArticleManager
{
  public void saveAdd(Article article)
  {
    if (article.getCat_id() != null) {
      String sql = "select count(*) from article_cat where cat_id=?";
      int count = this.baseDaoSupport.queryForInt(sql, new Object[] { article.getCat_id() });
      if (count <= 0)
        throw new ArticleRuntimeException(2);
    }
    article.setCreate_time(Long.valueOf(System.currentTimeMillis()));
    this.baseDaoSupport.insert("article", article);
  }

  public void saveEdit(Article article) {
    if (article.getCat_id() != null) {
      String sql = "select count(*) from article_cat where cat_id=?";
      int count = this.baseDaoSupport.queryForInt(sql, new Object[] { article.getCat_id() });
      if (count <= 0)
        throw new ArticleRuntimeException(2);
    }
    this.baseDaoSupport.update("article", article, "id=" + article.getId());
  }

  public Article get(Integer id)
  {
    String sql = "select * from article where id=?";
    Article article = null;
    try {
      article = (Article)this.baseDaoSupport.queryForObject(sql, Article.class, new Object[] { id });
    }
    catch (Exception ex) {
      article = null;
    }
    if (article == null)
      throw new ArticleRuntimeException(1);
    return article;
  }

  public List listByCatId(Integer cat_id)
  {
    String cat_path = (String)this.baseDaoSupport.queryForObject("select cat_path from article_cat where cat_id = ?", new StringMapper(), new Object[] { cat_id });
    String sql = "select a.*,b.cat_path from " + getTableName("article") + " a left join " + getTableName("article_cat") + " b on b.cat_id = a.cat_id where cat_path like '" + cat_path + "%' order by create_time desc";
    List list = this.daoSupport.queryForList(sql, new Object[0]);
    return list;
  }

  public String getCatName(Integer cat_id) {
    String sql = "select name from article_cat where cat_id=?";
    String cat_name = null;
    try {
      cat_name = (String)this.baseDaoSupport.queryForObject(sql, String.class, new Object[] { cat_id });
    } catch (Exception ex) {
      cat_name = null;
    }
    if (cat_name == null)
      throw new ArticleRuntimeException(1);
    return cat_name;
  }

  public void delete(String id)
  {
    if ((id == null) || (id.equals("")))
      return;
    String sql = "delete from article  where id in (" + id + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public Page pageByCatId(Integer pageNo, Integer pageSize, Integer catId)
  {
    String cat_path = (String)this.baseDaoSupport.queryForObject("select cat_path from article_cat where cat_id = ?", new StringMapper(), new Object[] { catId });
    String sql = "select a.*,b.cat_path from " + getTableName("article") + " a left join " + getTableName("article_cat") + " b on b.cat_id = a.cat_id where cat_path like '" + cat_path + "%' order by create_time desc";
    Page page = this.daoSupport.queryForPage(sql, pageNo.intValue(), pageSize.intValue(), new Object[0]);
    return page;
  }

  public List topListByCatId(Integer catId, Integer topNum)
  {
    String sql = "select * from article where cat_id=? order by create_time limit 0," + topNum;
    List list = this.baseDaoSupport.queryForList(sql, new Object[] { catId });
    return list;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.ArticleManager
 * JD-Core Version:    0.6.1
 */