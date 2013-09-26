package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.GoodsType;
import com.enation.app.shop.core.model.support.GoodsTypeDTO;
import com.enation.app.shop.core.model.support.ParamGroup;
import com.enation.app.shop.core.model.support.TypeSaveState;
import com.enation.app.shop.core.service.IBrandManager;
import com.enation.app.shop.core.service.IGoodsTypeManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class TypeAction extends WWAction
{
  private IGoodsTypeManager goodsTypeManager;
  private IBrandManager brandManager;
  private List brandlist;
  private GoodsTypeDTO goodsType;
  private String[] propnames;
  private int[] proptypes;
  private String[] options;
  private String[] datatype;
  private int[] required;
  private String[] unit;
  private String paramnum;
  private String[] groupnames;
  private String[] paramnames;
  private Integer type_id;
  private int is_edit;
  private Integer[] id;
  private Integer[] chhoose_brands;
  private static String GOODSTYPE_SESSION_KEY = "goods_type_in_session";

  private static String GOODSTYPE_STATE_SESSION_KEY = "goods_type_state_in_session";
  private String order;
  private List attrList;
  private ParamGroup[] paramAr;

  public String getOrder()
  {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public String checkname()
  {
    if (this.goodsTypeManager.checkname(this.goodsType.getName(), this.goodsType.getType_id()))
      this.json = "{result:1}";
    else {
      this.json = "{result:0}";
    }
    return "json_message";
  }

  public String list()
  {
    this.webpage = this.goodsTypeManager.pageType(this.order, getPage(), getPageSize());

    return "list";
  }

  public String trash_list()
  {
    this.webpage = this.goodsTypeManager.pageTrashType(this.order, getPage(), getPageSize());

    return "trash_list";
  }

  public String step1()
  {
    return "step1";
  }

  public String step2()
  {
    TypeSaveState saveState = new TypeSaveState();
    this.session.put(GOODSTYPE_STATE_SESSION_KEY, saveState);

    GoodsType tempType = getTypeFromSession();
    if (tempType == null)
    {
      this.session.put(GOODSTYPE_SESSION_KEY, this.goodsType);
    }
    else if (this.is_edit == 1) {
      tempType.setHave_parm(this.goodsType.getHave_parm());
      tempType.setHave_prop(this.goodsType.getHave_prop());
      tempType.setJoin_brand(this.goodsType.getJoin_brand());
      tempType.setIs_physical(this.goodsType.getIs_physical());
      tempType.setHave_field(this.goodsType.getHave_field());
      tempType.setName(this.goodsType.getName());
    } else {
      this.session.put(GOODSTYPE_SESSION_KEY, this.goodsType);
    }

    String result = getResult();
    if (result == null) {
      renderText("参数不正确！");
    }
    return result;
  }

  public String edit()
  {
    this.goodsType = this.goodsTypeManager.get(this.type_id);
    this.session.put(GOODSTYPE_SESSION_KEY, this.goodsType);
    this.is_edit = 1;
    return "edit";
  }

  public String saveParams()
  {
    String[] paramnums = new String[0];
    if (this.paramnum != null) {
      if (this.paramnum.indexOf(",-1") >= 0) {
        this.paramnum = this.paramnum.replaceAll(",-1", "");
      }
      paramnums = this.paramnum.split(",");
    }

    String params = this.goodsTypeManager.getParamString(paramnums, this.groupnames, this.paramnames, null);

    GoodsType tempType = getTypeFromSession();
    TypeSaveState saveState = getSaveStateFromSession();
    tempType.setParams(params);
    saveState.setDo_save_params(1);
    return getResult();
  }

  public String saveProps()
  {
    String props = this.goodsTypeManager.getPropsString(this.propnames, this.proptypes, this.options, this.unit, this.required, this.datatype);

    GoodsType tempType = getTypeFromSession();
    tempType.setProps(props);

    TypeSaveState saveState = getSaveStateFromSession();
    saveState.setDo_save_props(1);
    return getResult();
  }

  public String saveBrand()
  {
    GoodsType tempType = getTypeFromSession();
    tempType.setBrand_ids(this.chhoose_brands);

    TypeSaveState saveState = getSaveStateFromSession();
    saveState.setDo_save_brand(1);
    return getResult();
  }

  public String save()
  {
    GoodsTypeDTO tempType = getTypeFromSession();
    tempType.setDisabled(0);
    tempType.setBrandList(null);
    tempType.setPropList(null);
    tempType.setParamGroups(null);

    this.type_id = this.goodsTypeManager.save(tempType);
    this.session.remove(GOODSTYPE_SESSION_KEY);

    if (tempType.getHave_field() == 0) {
      this.msgs.add("商品类型保存成功");
      this.urls.put("商品类型列表", "type!list.do");
      return "message";
    }
    return "field";
  }

  private GoodsTypeDTO getTypeFromSession()
  {
    Object obj = this.session.get(GOODSTYPE_SESSION_KEY);

    if (obj == null)
    {
      return null;
    }

    GoodsTypeDTO tempType = (GoodsTypeDTO)obj;

    return tempType;
  }

  private TypeSaveState getSaveStateFromSession()
  {
    Object obj = this.session.get(GOODSTYPE_STATE_SESSION_KEY);
    if (obj == null) {
      renderText("参数不正确");
      return null;
    }
    TypeSaveState tempType = (TypeSaveState)obj;
    return tempType;
  }

  private String getResult()
  {
    GoodsType tempType = getTypeFromSession();
    this.goodsType = getTypeFromSession();
    TypeSaveState saveState = getSaveStateFromSession();
    String result = null;

    if ((tempType.getHave_prop() != 0) && (saveState.getDo_save_props() == 0)) {
      result = "add_props";
    } else if ((tempType.getHave_parm() != 0) && (saveState.getDo_save_params() == 0))
    {
      result = "add_parms";
    } else if ((tempType.getJoin_brand() != 0) && (saveState.getDo_save_brand() == 0))
    {
      this.brandlist = this.brandManager.list();
      result = "join_brand";
    }
    else {
      result = save();
    }

    return result;
  }

  public String delete()
  {
    try
    {
      int result = this.goodsTypeManager.delete(this.id);
      if (result == 1)
        this.json = "{'result':0,'message':'删除成功'}";
      else
        this.json = "{'result':1,'message':'此类型存在与之关联的商品或类别，不能删除。'}";
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String clean()
  {
    try
    {
      this.goodsTypeManager.clean(this.id);
      this.json = "{'result':0,'message':'清除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'清除失败'}";
    }
    return "json_message";
  }

  public String revert()
  {
    try
    {
      this.goodsTypeManager.revert(this.id);
      this.json = "{'result':0,'message':'还原成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'还原失败'}";
    }
    return "json_message";
  }

  public String disPropsInput()
  {
    this.attrList = this.goodsTypeManager.getAttrListByTypeId(this.type_id.intValue());
    this.attrList = ((this.attrList == null) || (this.attrList.isEmpty()) ? null : this.attrList);
    return "props_input";
  }

  public String disParamsInput()
  {
    this.paramAr = this.goodsTypeManager.getParamArByTypeId(this.type_id.intValue());
    return "params_input";
  }

  public String listBrand()
  {
    this.brandlist = this.goodsTypeManager.listByTypeId(this.type_id);
    return "brand_list";
  }

  public List getAttrList() {
    return this.attrList;
  }

  public void setAttrList(List attrList) {
    this.attrList = attrList;
  }

  public ParamGroup[] getParamAr() {
    return this.paramAr;
  }

  public void setParamAr(ParamGroup[] paramAr) {
    this.paramAr = paramAr;
  }

  public GoodsTypeDTO getGoodsType() {
    return this.goodsType;
  }

  public void setGoodsType(GoodsTypeDTO goodsType) {
    this.goodsType = goodsType;
  }

  public String[] getPropnames() {
    return this.propnames;
  }

  public void setPropnames(String[] propnames) {
    this.propnames = propnames;
  }

  public int[] getProptypes() {
    return this.proptypes;
  }

  public void setProptypes(int[] proptypes) {
    this.proptypes = proptypes;
  }

  public String[] getOptions() {
    return this.options;
  }

  public void setOptions(String[] options) {
    this.options = options;
  }

  public IGoodsTypeManager getGoodsTypeManager() {
    return this.goodsTypeManager;
  }

  public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
    this.goodsTypeManager = goodsTypeManager;
  }

  public String[] getGroupnames() {
    return this.groupnames;
  }

  public void setGroupnames(String[] groupnames) {
    this.groupnames = groupnames;
  }

  public String[] getParamnames() {
    return this.paramnames;
  }

  public void setParamnames(String[] paramnames) {
    this.paramnames = paramnames;
  }

  public String getParamnum() {
    return this.paramnum;
  }

  public void setParamnum(String paramnum) {
    this.paramnum = paramnum;
  }

  public Integer[] getChhoose_brands() {
    return this.chhoose_brands;
  }

  public void setChhoose_brands(Integer[] chhoose_brands) {
    this.chhoose_brands = chhoose_brands;
  }

  public Integer getType_id() {
    return this.type_id;
  }

  public void setType_id(Integer type_id) {
    this.type_id = type_id;
  }

  public int getIs_edit() {
    return this.is_edit;
  }

  public void setIs_edit(int is_edit) {
    this.is_edit = is_edit;
  }

  public Integer[] getId() {
    return this.id;
  }

  public void setId(Integer[] id) {
    this.id = id;
  }

  public void setBrandManager(IBrandManager brandManager) {
    this.brandManager = brandManager;
  }

  public List getBrandlist() {
    return this.brandlist;
  }

  public void setBrandlist(List brandlist) {
    this.brandlist = brandlist;
  }

  public String[] getDatatype() {
    return this.datatype;
  }

  public void setDatatype(String[] datatype) {
    this.datatype = datatype;
  }

  public int[] getRequired() {
    return this.required;
  }

  public void setRequired(int[] required) {
    this.required = required;
  }

  public String[] getUnit() {
    return this.unit;
  }

  public void setUnit(String[] unit) {
    this.unit = unit;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.TypeAction
 * JD-Core Version:    0.6.1
 */