package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.model.MemberLv;
import com.enation.app.shop.core.model.GoodsLvPrice;
import com.enation.app.shop.core.model.Product;
import com.enation.app.shop.core.model.SpecValue;
import com.enation.app.shop.core.model.Specification;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IMemberPriceManager;
import com.enation.app.shop.core.service.IProductManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.database.StringMapper;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ProductManager extends BaseSupport<Product>
  implements IProductManager
{
  private IMemberPriceManager memberPriceManager;
  private IMemberLvManager memberLvManager;

  private String getProductidStr(List<Product> productList)
  {
    StringBuffer str = new StringBuffer();
    for (Product pro : productList)
    {
      if (str.length() != 0) {
        str.append(",");
      }
      Integer productid = pro.getProduct_id();
      if (productid != null) {
        str.append(pro.getProduct_id());
      }
    }
    return str.toString();
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void add(List<Product> productList) {
    if (productList.size() > 0) {
      Integer goodsid = ((Product)productList.get(0)).getGoods_id();

      this.baseDaoSupport.execute("delete from  goods_spec  where goods_id=?", new Object[] { goodsid });
      this.baseDaoSupport.execute("delete from  goods_lv_price  where goodsid=?", new Object[] { goodsid });

      String proidstr = getProductidStr(productList);

      String sql = "delete from product where goods_id=? ";
      if (!StringUtil.isEmpty(proidstr)) {
        sql = sql + " and  product_id  not in(" + proidstr + ")";
      }
      this.baseDaoSupport.execute(sql, new Object[] { goodsid });

      sql = "delete from product_store where goodsid=? ";
      if (!StringUtil.isEmpty(proidstr)) {
        sql = sql + " and  productid  not in(" + proidstr + ")";
      }
      this.baseDaoSupport.execute(sql, new Object[] { goodsid });
    }

    for (Product product : productList)
    {
    	 Integer product_id = product.getProduct_id();
      if (product_id == null) {
        this.baseDaoSupport.insert("product", product);
        product_id = Integer.valueOf(this.baseDaoSupport.getLastId("product"));
        product.setProduct_id(product_id);
      } else {
        this.baseDaoSupport.update("product", product, "product_id=" + product_id);
      }

      List<SpecValue> specList = product.getSpecList();

      for (SpecValue specvalue : specList) {
        this.daoSupport.execute("insert into " + getTableName("goods_spec") + "(spec_id,spec_value_id,goods_id,product_id)values(?,?,?,?)", new Object[] { specvalue.getSpec_id(), specvalue.getSpec_value_id(), product.getGoods_id(), product_id });
      }

      List<GoodsLvPrice> lvPriceList = product.getGoodsLvPrices();
      if (lvPriceList != null)
        for (GoodsLvPrice lvPrice : lvPriceList) {
          lvPrice.setProductid(product_id.intValue());
          this.baseDaoSupport.insert("goods_lv_price", lvPrice);
        }
    }
    Integer product_id;
    if (productList.size() > 0) {
      Integer goodsid = ((Product)productList.get(0)).getGoods_id();

      this.baseDaoSupport.execute("update goods set specs=? where goods_id=?", new Object[] { JSONArray.fromObject(productList).toString(), goodsid });
    }
  }

  public Product get(Integer productid)
  {
    String sql = "select * from product where product_id=?";
    return (Product)this.baseDaoSupport.queryForObject(sql, Product.class, new Object[] { productid });
  }

  public List<String> listSpecName(int goodsid)
  {
    StringBuffer sql = new StringBuffer();
    sql.append("select distinct s.spec_name ");
    sql.append(" from ");

    sql.append(getTableName("specification"));
    sql.append(" s,");

    sql.append(getTableName("goods_spec"));
    sql.append(" gs ");

    sql.append("where s.spec_id = gs.spec_id and gs.goods_id=?");
    List list = this.daoSupport.queryForList(sql.toString(), new StringMapper(), new Object[] { Integer.valueOf(goodsid) });
    return list;
  }

  public List<Specification> listSpecs(Integer goodsId)
  {
    StringBuffer sql = new StringBuffer();
    sql.append("select distinct s.spec_id,s.spec_type,s.spec_name,sv.spec_value_id,sv.spec_value,sv.spec_image ,gs.goods_id as goods_id ");
    sql.append(" from ");

    sql.append(getTableName("specification"));
    sql.append(" s,");

    sql.append(getTableName("spec_values"));
    sql.append(" sv,");

    sql.append(getTableName("goods_spec"));
    sql.append(" gs ");

    sql.append("where s.spec_id = sv.spec_id  and gs.spec_value_id = sv.spec_value_id and gs.goods_id=?  ORDER BY s.spec_id");
    List<Map> list = this.daoSupport.queryForList(sql.toString(), new Object[] { goodsId });

    List specList = new ArrayList();
    Integer spec_id = Integer.valueOf(0);
    Specification spec = null;
    for (Map map : list) {
      Integer dbspecid = Integer.valueOf(map.get("spec_id").toString());
      List valueList;
      if (spec_id.intValue() != dbspecid.intValue()) {
        spec_id = dbspecid;
        valueList = new ArrayList();

        spec = new Specification();
        spec.setSpec_id(dbspecid);
        spec.setSpec_name(map.get("spec_name").toString());
        spec.setSpec_type((Integer)map.get("spec_type"));

        specList.add(spec);

        spec.setValueList(valueList);
      } else {
        valueList = spec.getValueList();
      }

      SpecValue value = new SpecValue();
      value.setSpec_value(map.get("spec_value").toString());
      value.setSpec_value_id(Integer.valueOf(map.get("spec_value_id").toString()));
      String spec_img = (String)map.get("spec_image");

      if (spec_img != null) {
        spec_img = UploadUtil.replacePath(spec_img);
      }
      value.setSpec_image(spec_img);

      valueList.add(value);
    }

    return specList;
  }

  public List<Product> list(Integer goodsId)
  {
    String sql = "select * from product where goods_id=?";
    List prolist = this.baseDaoSupport.queryForList(sql, Product.class, new Object[] { goodsId });

    sql = "select sv.*,gs.product_id product_id from  " + getTableName("goods_spec") + "  gs inner join " + getTableName("spec_values") + "  sv on gs.spec_value_id = sv.spec_value_id where  gs.goods_id=?";

    List<Map> gsList = this.daoSupport.queryForList(sql, new Object[] { goodsId });

    List memPriceList = new ArrayList();

    Member member = UserServiceFactory.getUserService().getCurrentMember();
    double discount = 1.0D;
    if (member != null) {
      memPriceList = this.memberPriceManager.listPriceByGid(goodsId.intValue());
      MemberLv lv = this.memberLvManager.get(member.getLv_id());
      if (lv != null) {
        discount = lv.getDiscount().intValue() / 100.0D;
      }
    }

    for (Iterator i$ = prolist.iterator(); i$.hasNext(); ) { Product pro = (Product)i$.next();

      if (member != null) {
        Double price = pro.getPrice();
        if ((memPriceList != null) && (memPriceList.size() > 0))
          price = getMemberPrice(pro.getProduct_id().intValue(), member.getLv_id().intValue(), price, memPriceList, discount);
        pro.setPrice(price);
      }

      for (Map gs : gsList)
      {
        Integer productid = Integer.valueOf(((Integer)gs.get("product_id")).intValue());

        if (pro.getProduct_id().intValue() == productid.intValue()) {
          SpecValue spec = new SpecValue();
          spec.setSpec_value_id((Integer)gs.get("spec_value_id"));
          spec.setSpec_id((Integer)gs.get("spec_id"));
          String spec_img = (String)gs.get("spec_image");

          if (spec_img != null) {
            spec_img = UploadUtil.replacePath(spec_img);
          }
          spec.setSpec_image(spec_img);
          spec.setSpec_value((String)gs.get("spec_value"));
          spec.setSpec_type((Integer)gs.get("spec_type"));
          pro.addSpec(spec);
        }
      }
    }
    Product pro;
    return prolist;
  }

  private Double getMemberPrice(int productid, int lvid, Double price, List<GoodsLvPrice> memPriceList, double discount)
  {
    Double memPrice = Double.valueOf(price.doubleValue() * discount);

    for (GoodsLvPrice lvPrice : memPriceList) {
      if ((lvPrice.getProductid() == productid) && (lvPrice.getLvid() == lvid)) {
        memPrice = lvPrice.getPrice();
      }
    }
    return memPrice;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer[] goodsid)
  {
    String id_str = StringUtil.arrayToString(goodsid, ",");
    String sql = "delete from goods_spec where goods_id in (" + id_str + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);

    sql = "delete from goods_lv_price where goodsid in (" + id_str + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);

    sql = "delete from product where goods_id in (" + id_str + ")";
    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public Page list(String name, String sn, int pageNo, int pageSize, String order)
  {
    order = order == null ? "product_id asc" : order;
    StringBuffer sql = new StringBuffer();
    sql.append("select p.* from " + getTableName("product") + " p left join " + getTableName("goods") + " g on g.goods_id = p.goods_id ");
    sql.append(" where g.disabled=0");
    if (!StringUtil.isEmpty(name)) {
      sql.append(" and g.name like '%");
      sql.append(name);
      sql.append("%'");
    }
    if (!StringUtil.isEmpty(sn)) {
      sql.append(" and g.sn = '");
      sql.append(sn);
      sql.append("'");
    }

    sql.append(" order by " + order);
    return this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
  }

  public List list(Integer[] productids)
  {
    if ((productids == null) || (productids.length == 0)) return new ArrayList();
    StringBuffer sql = new StringBuffer();
    sql.append("select p.* from " + getTableName("product") + " p left join " + getTableName("goods") + " g on g.goods_id = p.goods_id ");
    sql.append(" where g.disabled=0");
    sql.append(" and p.product_id in(");
    sql.append(StringUtil.arrayToString(productids, ","));
    sql.append(")");

    return this.daoSupport.queryForList(sql.toString(), new Object[0]);
  }

  public Product getByGoodsId(Integer goodsid)
  {
    String sql = "select * from product where goods_id=?";
    List proList = this.baseDaoSupport.queryForList(sql, Product.class, new Object[] { goodsid });
    if ((proList == null) || (proList.isEmpty())) {
      return null;
    }
    return (Product)proList.get(0);
  }

  public IMemberPriceManager getMemberPriceManager()
  {
    return this.memberPriceManager;
  }

  public void setMemberPriceManager(IMemberPriceManager memberPriceManager)
  {
    this.memberPriceManager = memberPriceManager;
  }

  public IMemberLvManager getMemberLvManager()
  {
    return this.memberLvManager;
  }

  public void setMemberLvManager(IMemberLvManager memberLvManager)
  {
    this.memberLvManager = memberLvManager;
  }

  public static void main(String[] args) {
    double discount = 0.9D;
    System.out.println(discount);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.ProductManager
 * JD-Core Version:    0.6.1
 */