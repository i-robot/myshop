package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.DlyType;
import com.enation.app.shop.core.model.support.DlyTypeConfig;
import com.enation.app.shop.core.model.support.TypeAreaConfig;
import com.enation.app.shop.core.service.IDlyTypeManager;
import com.enation.app.shop.core.service.ILogiManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class DlyTypeAction extends WWAction
{
  private Integer type_id;
  private String id;
  private List logiList;
  private DlyType type;
  private IDlyTypeManager dlyTypeManager;
  private ILogiManager logiManager;
  private Integer firstunit;
  private Integer continueunit;
  private Double[] firstprice;
  private Double[] continueprice;
  private String[] areaGroupName;
  private String[] areaGroupId;
  private Integer[] has_cod;
  private Integer[] useexp;
  private String[] expressions;
  private String exp;
  private Integer defAreaFee;
  private Boolean isEdit;

  public String add_type()
  {
    this.isEdit = Boolean.valueOf(false);
    this.logiList = this.logiManager.list();
    return "add";
  }

  public String edit() {
    this.isEdit = Boolean.valueOf(true);
    this.type = this.dlyTypeManager.getDlyTypeById(this.type_id);
    this.logiList = this.logiManager.list();
    return "edit";
  }

  public String list() {
    this.webpage = this.dlyTypeManager.pageDlyType(getPage(), getPageSize());
    return "list";
  }

  public String vaildateExp()
  {
    return null;
  }

  public String saveAdd()
  {
    if (this.type.getIs_same().intValue() == 1) {
      saveSame(false);
    }

    if (this.type.getIs_same().intValue() == 0) {
      saveDiff(false);
    }

    this.msgs.add("配送方式添加成功");
    this.urls.put("配送方式列表", "dlyType!list.do");
    return "message";
  }

  private void saveSame(boolean isUpdate)
  {
    DlyTypeConfig config = new DlyTypeConfig();
    config.setFirstunit(this.firstunit);
    config.setContinueunit(this.continueunit);

    config.setFirstprice(this.firstprice[0]);
    config.setContinueprice(this.continueprice[0]);

    if (this.useexp[0].intValue() == 1) {
      config.setExpression(this.expressions[0]);
      config.setUseexp(Integer.valueOf(1));
    } else {
      config.setUseexp(Integer.valueOf(0));
    }

    this.type.setHas_cod(this.has_cod[0]);
    config.setHave_cod(this.type.getHas_cod());

    if (isUpdate)
      this.dlyTypeManager.edit(this.type, config);
    else
      this.dlyTypeManager.add(this.type, config);
  }

  private void saveDiff(boolean isUpdate)
  {
    DlyTypeConfig config = new DlyTypeConfig();

    config.setFirstunit(this.firstunit);
    config.setContinueunit(this.continueunit);
    config.setDefAreaFee(this.defAreaFee);

    if ((this.defAreaFee != null) && (this.defAreaFee.intValue() == 1)) {
      config.setFirstprice(this.firstprice[0]);
      config.setContinueprice(this.continueprice[0]);
      if (this.useexp[0].intValue() == 1) {
        config.setExpression(this.expressions[0]);
        config.setUseexp(Integer.valueOf(1));
      } else {
        config.setUseexp(Integer.valueOf(0));
      }
    }

    TypeAreaConfig[] configArray = new TypeAreaConfig[this.areaGroupId.length];
    int price_index = 0;

    for (int i = 0; i < this.areaGroupId.length; i++)
    {
      if ((this.defAreaFee != null) && (this.defAreaFee.intValue() == 1))
        price_index = i + 1;
      else {
        price_index = i;
      }

      TypeAreaConfig areaConfig = new TypeAreaConfig();

      areaConfig.setContinueunit(config.getContinueunit());
      areaConfig.setFirstunit(config.getFirstunit());
      areaConfig.setUseexp(this.useexp[price_index]);

      areaConfig.setAreaId(this.areaGroupId[i]);
      areaConfig.setAreaName(this.areaGroupName[i]);

      if (this.useexp[price_index].intValue() == 1) {
        areaConfig.setExpression(this.expressions[price_index]);
      } else {
        areaConfig.setFirstprice(this.firstprice[price_index]);
        areaConfig.setContinueprice(this.continueprice[price_index]);
      }
      areaConfig.setHave_cod(this.has_cod[i]);

      configArray[i] = areaConfig;
    }
    if (isUpdate)
      this.dlyTypeManager.edit(this.type, config, configArray);
    else
      this.dlyTypeManager.add(this.type, config, configArray);
  }

  public String saveEdit()
  {
    if (this.type.getIs_same().intValue() == 1) {
      saveSame(true);
    }

    if (this.type.getIs_same().intValue() == 0) {
      saveDiff(true);
    }

    this.msgs.add("配送方式修改成功");
    this.urls.put("配送方式列表", "dlyType!list.do");
    return "message";
  }

  public String delete() {
    this.dlyTypeManager.delete(this.id);
    this.json = "{'result':0,'message':'删除成功'}";
    return "json_message";
  }

  public IDlyTypeManager getDlyTypeManager()
  {
    return this.dlyTypeManager;
  }

  public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
    this.dlyTypeManager = dlyTypeManager;
  }

  public DlyType getType() {
    return this.type;
  }

  public void setType(DlyType type) {
    this.type = type;
  }

  public Integer getType_id() {
    return this.type_id;
  }

  public void setType_id(Integer type_id) {
    this.type_id = type_id;
  }

  public List getLogiList()
  {
    return this.logiList;
  }

  public void setLogiList(List logiList) {
    this.logiList = logiList;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ILogiManager getLogiManager()
  {
    return this.logiManager;
  }

  public void setLogiManager(ILogiManager logiManager) {
    this.logiManager = logiManager;
  }

  public Integer getFirstunit() {
    return this.firstunit;
  }

  public void setFirstunit(Integer firstunit) {
    this.firstunit = firstunit;
  }

  public Integer getContinueunit() {
    return this.continueunit;
  }

  public void setContinueunit(Integer continueunit) {
    this.continueunit = continueunit;
  }

  public Double[] getFirstprice() {
    return this.firstprice;
  }

  public void setFirstprice(Double[] firstprice) {
    this.firstprice = firstprice;
  }

  public Double[] getContinueprice() {
    return this.continueprice;
  }

  public void setContinueprice(Double[] continueprice) {
    this.continueprice = continueprice;
  }

  public Integer getDefAreaFee() {
    return this.defAreaFee;
  }

  public void setDefAreaFee(Integer defAreaFee) {
    this.defAreaFee = defAreaFee;
  }

  public String[] getAreaGroupName() {
    return this.areaGroupName;
  }

  public void setAreaGroupName(String[] areaGroupName) {
    this.areaGroupName = areaGroupName;
  }

  public String[] getAreaGroupId() {
    return this.areaGroupId;
  }

  public void setAreaGroupId(String[] areaGroupId) {
    this.areaGroupId = areaGroupId;
  }

  public Integer[] getUseexp() {
    return this.useexp;
  }

  public void setUseexp(Integer[] useexp) {
    this.useexp = useexp;
  }

  public String[] getExpressions() {
    return this.expressions;
  }

  public void setExpressions(String[] expressions) {
    this.expressions = expressions;
  }

  public Integer[] getHas_cod() {
    return this.has_cod;
  }

  public void setHas_cod(Integer[] hasCod) {
    this.has_cod = hasCod;
  }

  public Boolean getIsEdit() {
    return this.isEdit;
  }

  public void setIsEdit(Boolean isEdit) {
    this.isEdit = isEdit;
  }

  public String getExp() {
    return this.exp;
  }

  public void setExp(String exp) {
    this.exp = exp;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.DlyTypeAction
 * JD-Core Version:    0.6.1
 */