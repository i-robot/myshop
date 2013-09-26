package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.Cat;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.app.shop.core.service.IGoodsTypeManager;
import com.enation.eop.sdk.database.PermssionRuntimeException;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.action.WWAction;
import com.enation.framework.database.DBRuntimeException;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.JsonMessageUtil;
import java.io.File;
import java.util.List;
import java.util.Map;

public class CatAction extends WWAction
{
  private IGoodsCatManager goodsCatManager;
  private IGoodsTypeManager goodsTypeManager;
  protected List catList;
  private List typeList;
  private Cat cat;
  private File image;
  private String imageFileName;
  protected int cat_id;
  private int[] cat_ids;
  private int[] cat_sorts;

  public String checkname()
  {
    if (this.goodsCatManager.checkname(this.cat.getName(), this.cat.getCat_id()))
      this.json = "{result:1}";
    else {
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String list()
  {
    this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
    return "cat_list";
  }

  public String add()
  {
    this.typeList = this.goodsTypeManager.listAll();
    this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));

    return "cat_add";
  }

  public String edit()
  {
    try
    {
      this.typeList = this.goodsTypeManager.listAll();
      this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
      this.cat = this.goodsCatManager.getById(this.cat_id);
      return "cat_edit";
    } catch (DBRuntimeException ex) {
      this.msgs.add("您查询的商品不存在");
    }return "message";
  }

  public String saveAdd()
  {
    if (this.image != null) {
      if (FileUtil.isAllowUp(this.imageFileName)) {
        this.cat.setImage(UploadUtil.upload(this.image, this.imageFileName, "goodscat"));
      }
      else {
        this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
        return "message";
      }
    }
    this.cat.setGoods_count(Integer.valueOf(0));
    this.goodsCatManager.saveAdd(this.cat);

    this.msgs.add("商品分类添加成功");
    this.urls.put("分类列表", "cat!list.do");
    return "message";
  }

  public String saveEdit()
  {
    if (this.image != null)
      if (FileUtil.isAllowUp(this.imageFileName)) {
        this.cat.setImage(UploadUtil.upload(this.image, this.imageFileName, "goodscat"));
      }
      else {
        this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
        return "message";
      }
    try
    {
      if (this.cat.getParent_id().intValue() == 0) {
        this.goodsCatManager.update(this.cat);
        this.msgs.add("商品分类修改成功");
        this.urls.put("分类列表", "cat!list.do");
        return "message";
      }
      Cat targetCat = this.goodsCatManager.getById(this.cat.getParent_id().intValue());
      if ((this.cat.getParent_id().intValue() == this.cat.getCat_id().intValue()) || (targetCat.getParent_id().intValue() == this.cat.getCat_id().intValue())) {
        this.msgs.add("保存失败：上级分类不能选择当前分类或其子分类");
        this.urls.put("分类列表", "cat!list.do");
        return "message";
      }
      this.goodsCatManager.update(this.cat);
      this.msgs.add("商品分类修改成功");
      this.urls.put("分类列表", "cat!list.do");
      return "message";
    }
    catch (PermssionRuntimeException ex) {
      this.msgs.add("非法操作");
    }return "message";
  }

  public String delete()
  {
    try
    {
      int r = this.goodsCatManager.delete(this.cat_id);

      if (r == 0)
        this.json = "{'result':0,'message':'删除成功'}";
      else if (r == 1)
        this.json = "{'result':1,'message':'此类别下存在子类别不能删除!'}";
      else if (r == 2)
        this.json = "{'result':1,'message':'此类别下存在商品不能删除!'}";
    }
    catch (PermssionRuntimeException ex) {
      this.json = "{'result':1,'message':'非法操作!'}";
      return "json_message";
    }
    return "json_message";
  }

  public String getChildJson()
  {
    try
    {
      this.catList = this.goodsCatManager.listChildren(Integer.valueOf(this.cat_id));
      this.json = JsonMessageUtil.getListJson(this.catList);
    } catch (RuntimeException e) {
      showErrorJson(e.getMessage());
    }

    return "json_message";
  }

  public String saveSort() {
    this.goodsCatManager.saveSort(this.cat_ids, this.cat_sorts);
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

  public Cat getCat()
  {
    return this.cat;
  }

  public void setCat(Cat cat)
  {
    this.cat = cat;
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

  public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
    this.goodsCatManager = goodsCatManager;
  }

  public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager)
  {
    this.goodsTypeManager = goodsTypeManager;
  }

  public List getTypeList()
  {
    return this.typeList;
  }

  public void setTypeList(List typeList)
  {
    this.typeList = typeList;
  }

  public File getImage()
  {
    return this.image;
  }

  public void setImage(File image)
  {
    this.image = image;
  }

  public String getImageFileName() {
    return this.imageFileName;
  }
  public void setImageFileName(String imageFileName) {
    this.imageFileName = imageFileName;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.CatAction
 * JD-Core Version:    0.6.1
 */