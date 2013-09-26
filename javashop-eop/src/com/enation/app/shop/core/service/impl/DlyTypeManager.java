package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.service.IRegionsManager;
import com.enation.app.shop.core.model.DlyType;
import com.enation.app.shop.core.model.mapper.TypeAreaMapper;
import com.enation.app.shop.core.model.support.DlyTypeConfig;
import com.enation.app.shop.core.model.support.TypeArea;
import com.enation.app.shop.core.model.support.TypeAreaConfig;
import com.enation.app.shop.core.service.IDlyTypeManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DlyTypeManager extends BaseSupport<DlyType>
  implements IDlyTypeManager
{
  private IRegionsManager regionsManager;

  public void delete(String id)
  {
    if ((id == null) || (id.equals("")))
      return;
    String sql = "delete from dly_type_area where type_id in (" + id + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
    sql = "delete from dly_type where type_id in (" + id + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public DlyType getDlyTypeById(Integer typeId)
  {
    String sql = "select * from dly_type where type_id=?";
    DlyType dlyType = (DlyType)this.baseDaoSupport.queryForObject(sql, DlyType.class, new Object[] { typeId });

    if (dlyType.getIs_same().intValue() == 0) {
      dlyType.setTypeAreaList(listAreabyTypeId(dlyType.getType_id()));
    }
    convertTypeJson(dlyType);
    return dlyType;
  }

  private List listAreabyTypeId(Integer typeid) {
    String sql = "select * from dly_type_area where type_id=?";
    List typeAreaList = this.baseDaoSupport.queryForList(sql, new TypeAreaMapper(), new Object[] { typeid });
    return typeAreaList;
  }

  public List listByAreaId(Integer areaId)
  {
    String sql = "select a.* ,ta.price price from   " + getTableName("dly_area") + " a left join (select  * from    " + getTableName("dly_type_area") + " where type_id=?)  ta     on ( a.area_id  = ta.area_id  )";
    List l = this.daoSupport.queryForList(sql, new Object[] { areaId });
    return l;
  }

  public List<DlyType> list()
  {
    String sql = "select * from dly_type order by ordernum";
    return this.baseDaoSupport.queryForList(sql, DlyType.class, new Object[0]);
  }

  public List<DlyType> list(Double weight, Double orderPrice, String regoinId)
  {
    String sql = "select * from dly_type order by ordernum";
    List<DlyType> typeList = this.baseDaoSupport.queryForList(sql, DlyType.class, new Object[0]);

    sql = "select * from dly_type_area ";
    List typeAreaList = this.baseDaoSupport.queryForList(sql, new TypeAreaMapper(), new Object[0]);

    List resultList = new ArrayList();

    for (DlyType dlyType : typeList)
    {
      convertTypeJson(dlyType);

      if (dlyType.getIs_same().intValue() == 0) {
        List areaList = filterTypeArea(dlyType.getType_id(), typeAreaList);
        Double price = countPrice(areaList, weight, orderPrice, regoinId);

        if ((price == null) && (dlyType.getTypeConfig().getDefAreaFee() != null) && (dlyType.getTypeConfig().getDefAreaFee().compareTo(Integer.valueOf(1)) == 0)) {
          price = countExp(dlyType.getExpressions(), weight, orderPrice);
          if (price.compareTo(Double.valueOf(-1.0D)) != 0) {
            price = null;
          }
        }

        if (price != null) {
          dlyType.setPrice(price);
          resultList.add(dlyType);
        }
      }
      else {
        Double price = countExp(dlyType.getExpressions(), weight, orderPrice);
        if (price.compareTo(Double.valueOf(-1.0D)) != 0) {
          dlyType.setPrice(price);
          resultList.add(dlyType);
        }
      }
    }

    return resultList;
  }

  private Double countPrice(List<TypeArea> areaList, Double weight, Double orderPrice, String regoinId)
  {
    Double price = null;
    for (TypeArea typeArea : areaList) {
      String idGroup = typeArea.getArea_id_group();

      if ((idGroup != null) && (!idGroup.equals("")))
      {
        idGroup = idGroup == null ? "" : idGroup;
        String[] idArray = idGroup.split(",");

        if (StringUtil.isInArray(regoinId, idArray)) {
          Double thePrice = countExp(typeArea.getExpressions(), weight, orderPrice);

          if (price != null)
            price = thePrice.compareTo(price) > 0 ? thePrice : price;
          else {
            price = thePrice;
          }
        }
      }
    }
    return price;
  }

  private List<TypeArea> filterTypeArea(Integer type_id, List typeAreaList)
  {
    List areaList = new ArrayList();
    int i = 0; for (int len = typeAreaList.size(); i < len; i++) {
      TypeArea typeArea = (TypeArea)typeAreaList.get(i);
      if (typeArea.getType_id().compareTo(type_id) == 0) {
        areaList.add(typeArea);
      }
    }

    return areaList;
  }

  private void convertTypeJson(DlyType dlyType)
  {
    String config = dlyType.getConfig();
    JSONObject typeJsonObject = JSONObject.fromObject(config);
    DlyTypeConfig typeConfig = (DlyTypeConfig)JSONObject.toBean(typeJsonObject, DlyTypeConfig.class);
    dlyType.setTypeConfig(typeConfig);
    dlyType.setJson(JSONObject.fromObject(dlyType).toString());
  }

  public Page pageDlyType(int page, int pageSize)
  {
    String sql = "select  * from  dly_type order by ordernum";

    Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public void add(DlyType type, DlyTypeConfig config)
  {
    if (type.getIs_same().intValue() == 1) {
      type = fillType(type, config);
      this.baseDaoSupport.insert("dly_type", type);
    } else {
      throw new RuntimeException("type not is same config,cant'add");
    }
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void add(DlyType type, DlyTypeConfig config, TypeAreaConfig[] configArray)
  {
    type = fillType(type, config);

    this.baseDaoSupport.insert("dly_type", type);
    Integer typeId = Integer.valueOf(this.baseDaoSupport.getLastId("dly_type"));
    addTypeArea(typeId, configArray);
  }

  public void edit(DlyType type, DlyTypeConfig config)
  {
    if (type.getType_id() == null) {
      throw new RuntimeException("type id is null ,can't edit");
    }

    if (type.getIs_same().intValue() == 1) {
      Integer typeId = type.getType_id();
      this.baseDaoSupport.execute("delete from dly_type_area where type_id=?", new Object[] { typeId });
      type = fillType(type, config);
      this.baseDaoSupport.update("dly_type", type, "type_id=" + type.getType_id());
    } else {
      throw new RuntimeException("type  is not same config,cant'edit");
    }
  }

  private DlyType fillType(DlyType type, DlyTypeConfig config) {
    Double firstprice = config.getFirstprice();
    Double continueprice = config.getContinueprice();
    Integer firstunit = config.getFirstunit();
    Integer continueunit = config.getContinueunit();

    String expressions = null;

    if (config.getUseexp().intValue() == 0)
      expressions = createExpression(firstprice, continueprice, firstunit, continueunit);
    else {
      expressions = config.getExpression();
    }

    type.setExpressions(expressions);
    type.setConfig(JSONObject.fromObject(config).toString());
    return type;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void edit(DlyType type, DlyTypeConfig config, TypeAreaConfig[] configArray)
  {
    if (type.getType_id() == null) {
      throw new RuntimeException("type id is null ,can't edit");
    }

    type = fillType(type, config);

    Integer typeId = type.getType_id();
    this.baseDaoSupport.execute("delete from dly_type_area where type_id=?", new Object[] { typeId });

    addTypeArea(typeId, configArray);

    this.baseDaoSupport.update("dly_type", type, "type_id=" + type.getType_id());
  }

  private void addTypeArea(Integer typeId, TypeAreaConfig[] configArray)
  {
    for (TypeAreaConfig areaConfig : configArray) {
      String[] idArray = areaConfig.getAreaId().split(",");
      String closeAreaId = "";
      String checkedAreaId = "";

      for (String id : idArray) {
        if (!checkedAreaId.equals("")) checkedAreaId = checkedAreaId + ",";
        String[] idarray = id.split("\\|");
        if (idarray.length > 1) {
          if (!closeAreaId.equals("")) closeAreaId = closeAreaId + ",";
          closeAreaId = closeAreaId + idarray[0];
          checkedAreaId = checkedAreaId + idarray[0];
        } else {
          checkedAreaId = checkedAreaId + id;
        }

      }

      List<Integer> areaIdList = this.regionsManager.listChildren(closeAreaId);

      for (Integer childId : areaIdList) {
        checkedAreaId = checkedAreaId + "," + childId;
      }
      TypeArea typeArea = new TypeArea();
      typeArea.setArea_id_group(checkedAreaId);
      typeArea.setArea_name_group(areaConfig.getAreaName());
      typeArea.setType_id(typeId);
      typeArea.setHas_cod(areaConfig.getHave_cod());

      typeArea.setConfig(JSONObject.fromObject(areaConfig).toString());
      String expressions = "";
      if (areaConfig.getUseexp().intValue() == 1) {
        expressions = areaConfig.getExpression();
      }
      else {
        expressions = createExpression(areaConfig);
      }
      typeArea.setExpressions(expressions);
      this.baseDaoSupport.insert("dly_type_area", typeArea);
    }
  }

  private String createExpression(TypeAreaConfig areaConfig)
  {
    return createExpression(areaConfig.getFirstprice(), areaConfig.getContinueprice(), areaConfig.getFirstunit(), areaConfig.getContinueunit());
  }

  private String createExpression(Double firstprice, Double continueprice, Integer firstunit, Integer continueunit)
  {
    return firstprice + "+tint(w-" + firstunit + ")/" + continueunit + "*" + continueprice;
  }

  public IRegionsManager getRegionsManager()
  {
    return this.regionsManager;
  }

  public void setRegionsManager(IRegionsManager regionsManager) {
    this.regionsManager = regionsManager;
  }

  public Double countExp(String exp, Double weight, Double orderprice)
  {
    Context cx = Context.enter();
    try
    {
      Scriptable scope = cx.initStandardObjects();
      String str = "var w=" + weight + ";";
      str = str + "p=" + orderprice + ";";
      str = str + "function tint(value){return value<0?0:value;}";
      str = str + exp;
      Object result = cx.evaluateString(scope, str, null, 1, null);
      Double res = Double.valueOf(Context.toNumber(result));

      return res;
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      Context.exit();
    }
    return Double.valueOf(-1.0D);
  }

  public Double[] countPrice(Integer typeId, Double weight, Double orderPrice, String regionId, boolean isProtected)
  {
    DlyType dlyType = getDlyTypeById(typeId);
    Double dlyPrice = null;
    if (dlyType.getIs_same().compareTo(Integer.valueOf(1)) == 0)
      dlyPrice = countExp(dlyType.getExpressions(), weight, orderPrice);
    else {
      dlyPrice = countPrice(dlyType.getTypeAreaList(), weight, orderPrice, regionId);
    }
    Double protectPrice = null;
    if (isProtected) {
      Float protectRate = dlyType.getProtect_rate();
      protectPrice = Double.valueOf(orderPrice.doubleValue() * protectRate.floatValue() / 100.0D);
      protectPrice = Double.valueOf(dlyType.getMin_price().floatValue() > protectPrice.doubleValue() ? dlyType.getMin_price().floatValue() : protectPrice.doubleValue());
    }
    Double[] priceArray = { dlyPrice, protectPrice };
    return priceArray;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.DlyTypeManager
 * JD-Core Version:    0.6.1
 */