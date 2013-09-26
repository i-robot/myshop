package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.component.spec.service.ISpecManager;
import com.enation.app.shop.component.spec.service.ISpecValueManager;
import com.enation.app.shop.core.model.SpecValue;
import com.enation.app.shop.core.model.Specification;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.action.WWAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecAction extends WWAction
{
  private ISpecManager specManager;
  private ISpecValueManager specValueManager;
  private Integer spec_id;
  private Map specView;
  private List specList;
  private List valueList;
  private Specification spec;
  private String[] valueArray;
  private String[] imageArray;
  private Integer[] valueIdArray;
  private Integer[] id;
  private int valueid;

  public String checkUsed()
  {
    if (this.specManager.checkUsed(this.id))
      this.json = "{result:1}";
    else {
      this.json = "{result:0}";
    }

    return "json_message";
  }

  public String checkValueUsed()
  {
    boolean isused = this.specManager.checkUsed(Integer.valueOf(this.valueid));

    if (isused)
      this.json = "{result:1}";
    else {
      this.json = "{result:0}";
    }

    return "json_message";
  }

  public String list()
  {
    this.specList = this.specManager.list();
    return "list";
  }

  public String add() {
    return "input";
  }

  public String saveAdd() {
    fillSpecValueList();
    this.specManager.add(this.spec, this.valueList);
    this.msgs.add("规格添加成功");
    this.urls.put("规格列表", "spec!list.do");
    return "message";
  }

  public String edit() {
    this.specView = this.specManager.get(this.spec_id);
    this.valueList = this.specValueManager.list(this.spec_id);
    return "input";
  }

  public String saveEdit() {
    fillSpecValueList();
    this.specManager.edit(this.spec, this.valueList);
    this.msgs.add("规格修改成功");
    this.urls.put("规格列表", "spec!list.do");
    return "message";
  }

  private List<SpecValue> fillSpecValueList() {
    this.valueList = new ArrayList();

    if (this.valueArray != null) {
      for (int i = 0; i < this.valueArray.length; i++) {
        String value = this.valueArray[i];

        SpecValue specValue = new SpecValue();
        specValue.setSpec_value_id(this.valueIdArray[i]);
        specValue.setSpec_value(value);
        if (this.imageArray != null) {
          String image = this.imageArray[i];
          if ((image == null) || (image.equals(""))) image = "../shop/admin/spec/image/spec_def.gif";
          else
            image = UploadUtil.replacePath(image);
          specValue.setSpec_image(image);
        } else {
          specValue.setSpec_image("../shop/admin/spec/image/spec_def.gif");
        }
        this.valueList.add(specValue);
      }
    }
    return this.valueList;
  }

  public String delete()
  {
    this.specManager.delete(this.id);
    this.json = "{'result':0,'message':'规格删除成功'}";
    return "json_message";
  }

  public ISpecManager getSpecManager()
  {
    return this.specManager;
  }

  public void setSpecManager(ISpecManager specManager) {
    this.specManager = specManager;
  }

  public ISpecValueManager getSpecValueManager() {
    return this.specValueManager;
  }

  public void setSpecValueManager(ISpecValueManager specValueManager) {
    this.specValueManager = specValueManager;
  }

  public List getSpecList() {
    return this.specList;
  }

  public void setSpecList(List specList) {
    this.specList = specList;
  }

  public Specification getSpec() {
    return this.spec;
  }

  public void setSpec(Specification spec) {
    this.spec = spec;
  }

  public String[] getValueArray() {
    return this.valueArray;
  }

  public void setValueArray(String[] valueArray) {
    this.valueArray = valueArray;
  }

  public String[] getImageArray() {
    return this.imageArray;
  }

  public void setImageArray(String[] imageArray) {
    this.imageArray = imageArray;
  }

  public Integer getSpec_id() {
    return this.spec_id;
  }

  public void setSpec_id(Integer specId) {
    this.spec_id = specId;
  }

  public Map getSpecView() {
    return this.specView;
  }

  public void setSpecView(Map specView) {
    this.specView = specView;
  }

  public List getValueList() {
    return this.valueList;
  }

  public void setValueList(List valueList) {
    this.valueList = valueList;
  }

  public Integer[] getId() {
    return this.id;
  }

  public void setId(Integer[] id) {
    this.id = id;
  }

  public Integer[] getValueIdArray() {
    return this.valueIdArray;
  }

  public void setValueIdArray(Integer[] valueIdArray) {
    this.valueIdArray = valueIdArray;
  }

  public int getValueid()
  {
    return this.valueid;
  }

  public void setValueid(int valueid)
  {
    this.valueid = valueid;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.SpecAction
 * JD-Core Version:    0.6.1
 */