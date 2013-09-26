package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.Article;
import com.enation.app.shop.core.service.IArticleCatManager;
import com.enation.app.shop.core.service.IArticleManager;
import com.enation.app.shop.core.service.impl.ArticleRuntimeException;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class ArticleAction extends WWAction
{
  private Article article;
  private IArticleCatManager articleCatManager;
  private IArticleManager articleManager;
  private Integer cat_id;
  private List catList;
  private List articleList;
  private int article_id;
  private String id;

  public String list()
  {
    this.articleList = this.articleManager.listByCatId(this.cat_id);
    return "list";
  }

  public String delete() {
    try {
      this.articleManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String add() {
    this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));
    return "add_input";
  }

  public String saveAdd() {
    if (this.article.getCat_id() == null) {
      this.msgs.add("文章分类不能为空");
      this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
      return "message";
    }
    try {
      this.articleManager.saveAdd(this.article);
    } catch (ArticleRuntimeException ex) {
      this.msgs.add("文章分类不存在");
      this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
      return "message";
    }
    this.msgs.add("文章添加成功");
    this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
    return "message";
  }

  public String edit()
  {
    this.article = this.articleManager.get(Integer.valueOf(this.article_id));
    this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));

    return "edit_input";
  }

  public String saveEdit() {
    if (this.article.getCat_id() == null) {
      this.msgs.add("文章分类不能为空");
      this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
      return "message";
    }
    try
    {
      this.articleManager.saveEdit(this.article);
    } catch (ArticleRuntimeException ex) {
      this.msgs.add("文章分类不存在");
      this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
      return "message";
    }
    this.msgs.add("文章修改成功");
    this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
    return "message";
  }

  public Article getArticle() {
    return this.article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public IArticleCatManager getArticleCatManager()
  {
    return this.articleCatManager;
  }

  public void setArticleCatManager(IArticleCatManager articleCatManager) {
    this.articleCatManager = articleCatManager;
  }

  public List getCatList() {
    return this.catList;
  }

  public void setCatList(List catList) {
    this.catList = catList;
  }

  public List getArticleList() {
    return this.articleList;
  }

  public void setArticleList(List articleList) {
    this.articleList = articleList;
  }

  public IArticleManager getArticleManager() {
    return this.articleManager;
  }

  public void setArticleManager(IArticleManager articleManager) {
    this.articleManager = articleManager;
  }

  public Integer getCat_id() {
    return this.cat_id;
  }

  public void setCat_id(Integer cat_id) {
    this.cat_id = cat_id;
  }

  public int getArticle_id() {
    return this.article_id;
  }

  public void setArticle_id(int articleId) {
    this.article_id = articleId;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.ArticleAction
 * JD-Core Version:    0.6.1
 */