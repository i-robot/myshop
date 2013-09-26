package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.ArticleCat;
import com.enation.app.shop.core.service.IArticleCatManager;
import com.enation.app.shop.core.service.impl.ArticleCatRuntimeException;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class ArticleCatAction extends WWAction
{
  private IArticleCatManager articleCatManager;
  private List catList;
  private ArticleCat cat;
  private int cat_id;
  private int[] cat_ids;
  private int[] cat_sorts;

  public String cat_list()
  {
    this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));
    return "cat_list";
  }

  public String add()
  {
    this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));
    return "cat_add";
  }

  public String edit()
  {
    this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));
    this.cat = this.articleCatManager.getById(this.cat_id);
    return "cat_edit";
  }

  public String saveAdd()
  {
    try
    {
      this.articleCatManager.saveAdd(this.cat);
    } catch (ArticleCatRuntimeException ex) {
      this.msgs.add("同级文章栏目不能同名");
      this.urls.put("分类列表", "articleCat!cat_list.do");
      return "message";
    }
    this.msgs.add("文章栏目添加成功");
    this.urls.put("分类列表", "articleCat!cat_list.do");
    return "message";
  }

  public String saveEdit()
  {
    try
    {
      this.articleCatManager.update(this.cat);
    } catch (ArticleCatRuntimeException ex) {
      this.msgs.add("同级文章栏目不能同名");
      this.urls.put("分类列表", "articleCat!cat_list.do");
      return "message";
    }
    this.msgs.add("文章栏目修改成功");
    this.urls.put("分类列表", "articleCat!cat_list.do");
    return "message";
  }

  public String delete()
  {
    int r = this.articleCatManager.delete(this.cat_id);

    if (r == 0)
      this.json = "{'result':0,'message':'删除成功'}";
    else if (r == 1) {
      this.json = "{'result':1,'message':'此类别下存在子类别或者文章不能删除!'}";
    }

    return "json_message";
  }

  public String saveSort()
  {
    this.articleCatManager.saveSort(this.cat_ids, this.cat_sorts);
    this.json = "{'result':0,'message':'保存成功'}";
    return "json_message";
  }

  public List getCatList()
  {
    return this.catList;
  }

  public void setCatList(List catList)
  {
    this.catList = catList;
  }

  public ArticleCat getCat()
  {
    return this.cat;
  }

  public void setCat(ArticleCat cat)
  {
    this.cat = cat;
  }

  public IArticleCatManager getArticleCatManager()
  {
    return this.articleCatManager;
  }

  public void setArticleCatManager(IArticleCatManager articleCatManager)
  {
    this.articleCatManager = articleCatManager;
  }

  public int getCat_id()
  {
    return this.cat_id;
  }

  public void setCat_id(int cat_id)
  {
    this.cat_id = cat_id;
  }

  public int[] getCat_ids()
  {
    return this.cat_ids;
  }

  public void setCat_ids(int[] cat_ids)
  {
    this.cat_ids = cat_ids;
  }

  public int[] getCat_sorts()
  {
    return this.cat_sorts;
  }

  public void setCat_sorts(int[] cat_sorts)
  {
    this.cat_sorts = cat_sorts;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.ArticleCatAction
 * JD-Core Version:    0.6.1
 */