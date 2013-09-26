package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Attribute;
import com.enation.app.shop.core.model.GoodsParam;
import com.enation.app.shop.core.model.GoodsType;
import com.enation.app.shop.core.model.support.GoodsTypeDTO;
import com.enation.app.shop.core.model.support.ParamGroup;
import com.enation.app.shop.core.service.GoodsTypeUtil;
import com.enation.app.shop.core.service.IGoodsTypeManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class GoodsTypeManager extends BaseSupport<GoodsType>
  implements IGoodsTypeManager
{
  private static final Log loger = LogFactory.getLog(GoodsTypeManager.class);
  private SimpleJdbcTemplate simpleJdbcTemplate;

  public List listAll()
  {
    String sql = "select * from goods_type where disabled=0";
    List typeList = this.baseDaoSupport.queryForList(sql, GoodsType.class, new Object[0]);

    return typeList;
  }

  public Page pageType(String order, int page, int pageSize)
  {
    order = order == null ? " type_id desc" : order;

    String sql = "select * from goods_type where disabled=0";
    sql = sql + "  order by ";
    sql = sql + order;

    Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public Page pageTrashType(String order, int page, int pageSize)
  {
    order = order == null ? " type_id desc" : order;

    String sql = "select * from goods_type where disabled=1";
    sql = sql + "  order by ";
    sql = sql + order;

    Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
    return webpage;
  }

  public GoodsTypeDTO get(Integer type_id)
  {
    String sql = "select * from goods_type where type_id=?";
    GoodsTypeDTO type = (GoodsTypeDTO)this.baseDaoSupport.queryForObject(sql, GoodsTypeDTO.class, new Object[] { type_id });
    if (type == null) {
      return null;
    }
    List propList = GoodsTypeUtil.converAttrFormString(type.getProps());
    ParamGroup[] paramGroups = GoodsTypeUtil.converFormString(type.getParams());
    List brandList = getBrandListByTypeId(type_id.intValue());
    type.setPropList(propList);
    type.setParamGroups(paramGroups);
    type.setBrandList(brandList);
    return type;
  }

  public GoodsType getById(int typeid)
  {
    String sql = "select * from goods_type where type_id=?";
    return (GoodsType)this.baseDaoSupport.queryForObject(sql, GoodsType.class, new Object[] { Integer.valueOf(typeid) });
  }

  public List getBrandListByTypeId(int type_id)
  {
    String sql = "select b.name name ,b.brand_id brand_id,0 as num from " + getTableName("type_brand") + " tb inner join " + getTableName("brand") + " b  on    b.brand_id = tb.brand_id where tb.type_id=? and b.disabled=0";

    List list = this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(type_id) });
    return list;
  }

  public List listByTypeId(Integer typeid)
  {
    String sql = "select b.* from " + getTableName("type_brand") + " tb inner join " + getTableName("brand") + " b  on    b.brand_id = tb.brand_id where tb.type_id=? and b.disabled=0";

    List list = this.daoSupport.queryForList(sql, new Object[] { typeid });

    return list;
  }

  public static List<Attribute> converAttrFormString(String props)
  {
    if ((props == null) || (props.equals(""))) {
      return new ArrayList();
    }
    JSONArray jsonArray = JSONArray.fromObject(props);
    List list = (List)JSONArray.toCollection(jsonArray, Attribute.class);

    return list;
  }

  public List<Attribute> getAttrListByTypeId(int type_id)
  {
    GoodsTypeDTO type = get(Integer.valueOf(type_id));
    if (type.getHave_prop() == 0) return new ArrayList();
    return type.getPropList();
  }

  public ParamGroup[] getParamArByTypeId(int type_id)
  {
    String params = getParamsByTypeId(type_id);
    return GoodsTypeUtil.converFormString(params);
  }

  private String getParamsByTypeId(int type_id)
  {
    String sql = "select params from goods_type where disabled=0 and type_id=" + type_id;
    IDaoSupport strDaoSuport = this.baseDaoSupport;

    String props = strDaoSuport.queryForString(sql);

    return props;
  }

  public String getPropsString(String[] propnames, int[] proptypes, String[] options, String[] unit, int[] required, String[] datatype)
  {
    List list = toAttrList(propnames, proptypes, options, unit, required, datatype);
    JSONArray jsonarray = JSONArray.fromObject(list);

    return jsonarray.toString();
  }

  public String getParamString(String[] paramnums, String[] groupnames, String[] paramnames, String[] paramvalues)
  {
    List list = toParamList(paramnums, groupnames, paramnames, paramvalues);
    JSONArray jsonarray = JSONArray.fromObject(list);
    return jsonarray.toString();
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public Integer save(GoodsType type)
  {
    String typeTableName = "goods_type";
    String tbTableName = "type_brand";
    Integer[] brand_id = type.getBrand_ids();

    type.setBrand_ids(null);
    if ((type.getParams() != null) && (type.getParams().equals("[]"))) {
      type.setParams(null);
    }
    Integer type_id = null;
    if (type.getType_id() != null) {
      type_id = type.getType_id();
      if (type.getHave_prop() == 0) {
        type.setProps(null);
      }
      if (type.getHave_parm() == 0) {
        type.setParams(null);
      }
      this.baseDaoSupport.update(typeTableName, type, "type_id=" + type_id);
      String sql = "delete from " + getTableName("type_brand") + " where type_id = ?";
      this.simpleJdbcTemplate.update(sql, new Object[] { type_id });
    }
    else {
      this.baseDaoSupport.insert(typeTableName, type);
      type_id = Integer.valueOf(this.baseDaoSupport.getLastId(typeTableName));
      if (loger.isDebugEnabled()) {
        loger.debug("增加商品类型成功 , id is " + type_id);
      }

    }

    if (brand_id != null) {
      for (int i = 0; i < brand_id.length; i++)
      {
        String sql = "insert into  " + getTableName("type_brand") + "(type_id,brand_id) values(?,?)";
        this.simpleJdbcTemplate.update(sql, new Object[] { type_id, brand_id[i] });
      }
    }

    return type_id;
  }

  private List<Attribute> toAttrList(String[] propnames, int[] proptypes, String[] options, String[] unit, int[] required, String[] datatype)
  {
    List attrList = new ArrayList();

    if ((propnames != null) && (proptypes != null) && (options != null)) {
      for (int i = 0; i < propnames.length; i++)
      {
        Attribute attribute = new Attribute();
        String name = propnames[i];
        String option = options[i];
        int type = proptypes[i];

        attribute.setName(name);
        attribute.setOptions(option);
        attribute.setType(type);
        attribute.setDatatype(datatype[i]);
        attribute.setRequired(required[i]);
        attribute.setUnit(unit[i]);
        attrList.add(attribute);
      }
    }

    return attrList;
  }

  private List<ParamGroup> toParamList(String[] ar_paramnum, String[] groupnames, String[] paramnames, String[] paramvalues)
  {
    List list = new ArrayList();
    if (groupnames != null) {
      for (int i = 0; i < groupnames.length; i++) {
        ParamGroup paramGroup = new ParamGroup();
        paramGroup.setName(groupnames[i]);
        List paramList = getParamList(ar_paramnum, paramnames, paramvalues, i);

        paramGroup.setParamList(paramList);
        list.add(paramGroup);
      }
    }
    return list;
  }

  private List<GoodsParam> getParamList(String[] ar_paramnum, String[] paramnames, String[] paramvalues, int groupindex)
  {
    int[] pos = countPos(groupindex, ar_paramnum);
    List list = new ArrayList();
    for (int i = pos[0]; i < pos[1]; i++) {
      GoodsParam goodsParam = new GoodsParam();

      goodsParam.setName(paramnames[i]);

      if (paramvalues != null) {
        String value = paramvalues[i];
        goodsParam.setValue(value);
      }

      list.add(goodsParam);
    }
    return list;
  }

  private int[] countPos(int groupindex, String[] ar_paramnum)
  {
    int index = 0;
    int start = 0;
    int end = 0;
    int[] r = new int[2];
    for (int i = 0; i <= groupindex; i++) {
      int p_num = Integer.valueOf(ar_paramnum[i]).intValue();

      index += p_num;
      if (i == groupindex - 1) {
        start = index;
      }

      if (i == groupindex) {
        end = index;
      }

    }

    r[0] = start;
    r[1] = end;

    return r;
  }

  private boolean checkUsed(Integer[] type_ids)
  {
    String sql = "select count(0) from goods_cat where type_id in";
    return false;
  }

  public int delete(Integer[] type_ids)
  {
    if (type_ids == null) return 1;

    String ids = "";
    for (int i = 0; i < type_ids.length; i++) {
      if (i != 0)
        ids = ids + ",";
      ids = ids + type_ids[i];
    }
    String sql = "select count(0) from " + getTableName("goods") + " where type_id in (" + ids + ")";
    int count = this.daoSupport.queryForInt(sql, new Object[0]);

    sql = "select count(0) from goods_cat where type_id in (" + ids + ")";
    int catcout = this.baseDaoSupport.queryForInt(sql, new Object[0]);
    if (catcout > 0) {
      return 0;
    }

    if (count == 0) {
      sql = "update  goods_type set disabled=1  where type_id in (" + ids + ")";
      this.baseDaoSupport.execute(sql, new Object[0]);
      return 1;
    }
    return 0;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void clean(Integer[] type_ids)
  {
    if (type_ids == null) return;
    String ids = "";
    for (int i = 0; i < type_ids.length; i++) {
      if (i != 0)
        ids = ids + ",";
      ids = ids + type_ids[i];
    }
    String sql = "delete from goods_type where type_id in(" + ids + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);

    sql = "delete from " + getTableName("type_brand") + " where type_id in(" + ids + ")";
    this.simpleJdbcTemplate.update(sql, new Object[0]);
  }

  public void revert(Integer[] type_ids)
  {
    if (type_ids == null) return;
    String ids = "";
    for (int i = 0; i < type_ids.length; i++) {
      if (i != 0)
        ids = ids + ",";
      ids = ids + type_ids[i];
    }
    String sql = "update  goods_type set disabled=0  where type_id in (" + ids + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate)
  {
    this.simpleJdbcTemplate = simpleJdbcTemplate;
  }
  public boolean checkname(String name, Integer typeid) {
    if (name != null) name = name.trim();
    String sql = "select count(0) from goods_type where name=? and type_id!=? and disabled=0";
    if (typeid == null) typeid = Integer.valueOf(0);
    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name, typeid });
    if (count > 0) {
      return true;
    }
    return false;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsTypeManager
 * JD-Core Version:    0.6.1
 */