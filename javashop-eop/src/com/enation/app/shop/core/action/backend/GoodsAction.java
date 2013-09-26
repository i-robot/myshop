package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.service.auth.IAdminUserManager;
import com.enation.app.base.core.service.auth.IPermissionManager;
import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.model.Tag;
import com.enation.app.shop.core.model.support.GoodsEditDTO;
import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
import com.enation.app.shop.core.service.IBrandManager;
import com.enation.app.shop.core.service.ICartManager;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.app.shop.core.service.IProductManager;
import com.enation.app.shop.core.service.ITagManager;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.StringUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class GoodsAction extends WWAction
{
  protected String name;
  protected String sn;
  protected String order;
  private Integer catid;
  protected Integer[] id;
  protected List brandList;
  protected Integer brand_id;
  protected Integer is_market;
  protected Goods goods;
  protected Map goodsView;
  protected Integer goods_id;
  protected List catList;
  protected IGoodsCatManager goodsCatManager;
  protected IBrandManager brandManager;
  protected IGoodsManager goodsManager;
  private IProductManager productManager;
  private ICartManager cartManager;
  private IOrderManager orderManager;
  protected Boolean is_edit;
  protected String actionName;
  protected Integer market_enable;
  private Integer[] tagids;
  private GoodsPluginBundle goodsPluginBundle;
  private IPermissionManager permissionManager;
  private IAdminUserManager adminUserManager;
  private ITagManager tagManager;
  protected Map<Integer, String> pluginTabs;
  protected Map<Integer, String> pluginHtmls;
  private List<Tag> tagList;
  private String optype;
  private int depotid;
  private String is_other;
  private List<String> tagHtmlList;

  public ICartManager getCartManager()
  {
    return this.cartManager;
  }

  public void setCartManager(ICartManager cartManager)
  {
    this.cartManager = cartManager;
  }

  public IOrderManager getOrderManager()
  {
    return this.orderManager;
  }

  public void setOrderManager(IOrderManager orderManager)
  {
    this.orderManager = orderManager;
  }

  public String selectCat()
  {
    return "select_cat";
  }

  public String list()
  {
    if (this.name != null) {
      String encoding = EopSetting.ENCODING;
      if (!StringUtil.isEmpty(encoding)) {
        this.name = StringUtil.to(this.name, encoding);
      }
    }

    this.brandList = this.brandManager.list();
    this.tagList = this.tagManager.list();
    this.webpage = this.goodsManager.searchGoods(this.brand_id, this.is_market, this.catid, this.name, this.sn, this.market_enable, this.tagids, this.order, getPage(), getPageSize(), this.is_other);

    this.is_edit = (this.is_edit == null ? Boolean.FALSE : Boolean.TRUE);
    if (!this.is_edit.booleanValue()) {
      return "list";
    }
    return "edit_list";
  }

  public String batchEdit()
  {
    try {
      this.goodsManager.batchEdit();
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.json = "{result:0}";
    }

    return "json_message";
  }

  public String getCatTree() {
    this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
    return "cat_tree";
  }

  public String trash_list()
  {
    this.webpage = this.goodsManager.pageTrash(this.name, this.sn, this.order, getPage(), getPageSize());
    return "trash_list";
  }

  public String delete()
  {
    try {
      if (this.id != null)
        for (Integer goodsid : this.id) {
          if (this.cartManager.checkGoodsInCart(goodsid)) {
            this.json = "{'result':1,'message':'删除失败，此商品已加入购物车'}";
            return "json_message";
          }
          if (this.orderManager.checkGoodsInOrder(goodsid.intValue())) {
            this.json = "{'result':1,'message':'删除失败，此商品已下过单'}";
            return "json_message";
          }
        }
      this.goodsManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'删除失败'}";
    }
    return "json_message";
  }

  public String revert()
  {
    try {
      this.goodsManager.revert(this.id);
      this.json = "{'result':0,'message':'清除成功'}";
    } catch (RuntimeException e) {
      this.json = "{'result':1,'message':'清除失败'}";
    }
    return "json_message";
  }

  public String clean()
  {
    try {
      this.goodsManager.clean(this.id);
      this.json = "{'result':0,'message':'清除成功'}";
    } catch (RuntimeException e) {
      e.printStackTrace();
      this.json = "{'result':1,'message':'清除失败'}";
    }
    return "json_message";
  }

  public String selector_list_ajax()
  {
    return "selector";
  }

  public String add()
  {
    this.actionName = "goods!saveAdd.do";
    this.is_edit = Boolean.valueOf(false);

    this.pluginTabs = this.goodsPluginBundle.getTabList();
    this.pluginHtmls = this.goodsPluginBundle.onFillAddInputData();

    return "input";
  }

  public String addBind()
  {
    this.actionName = "goods!saveBindAdd.do";
    return "bind_goods_input";
  }

  public String edit()
  {
    this.actionName = "goods!saveEdit.do";
    this.is_edit = Boolean.valueOf(true);

    this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
    GoodsEditDTO editDTO = this.goodsManager.getGoodsEditData(this.goods_id);
    this.goodsView = editDTO.getGoods();

    this.pluginTabs = this.goodsPluginBundle.getTabList();
    this.pluginHtmls = editDTO.getHtmlMap();

    return "input";
  }

  public String saveAdd()
  {
    try
    {
      this.goodsManager.add(this.goods);
      this.catid = this.goods.getCat_id();
      this.msgs.add("商品添加成功");

      this.blankUrls.put("查看此商品", "../../goods-" + this.goods.getGoods_id() + ".html");
      this.urls.put("修改此商品", "goods!edit.do?goods_id=" + this.goods.getGoods_id());
      this.urls.put("继续添加此分类商品", "goods!add.do?catid=" + this.catid);
      this.urls.put("此分类商品列表", "goods!list.do?catid=" + this.catid);
    }
    catch (RuntimeException e)
    {
      this.logger.error("添加商品出错", e);
      this.msgs.add(e.getMessage());
    }

    return "message";
  }

  public String saveEdit()
  {
    try {
      this.goodsManager.edit(this.goods);
      this.msgs.add("商品修改成功");

      this.blankUrls.put("查看此商品", "../../goods-" + this.goods.getGoods_id() + ".html");
      this.urls.put("修改此商品", "goods!edit.do?goods_id=" + this.goods.getGoods_id());
      this.urls.put("继续添加此分类商品", "goods!add.do?catid=" + this.goods.getCat_id());
      this.urls.put("此分类商品列表", "goods!list.do?catid=" + this.goods.getCat_id());
    }
    catch (RuntimeException e)
    {
      this.logger.error("修改商品出错", e);
      this.msgs.add(e.getMessage());
    }
    return "message";
  }

  public String updateMarketEnable()
  {
    try {
      this.goodsManager.updateField("market_enable", Integer.valueOf(1), this.goods_id);
      showSuccessJson("更新上架状态成功");
    } catch (RuntimeException e) {
      showErrorJson("更新上架状态失败");
    }
    return "json_message";
  }

  public String selector()
  {
    return "selector";
  }

  public List getCatList() {
    return this.catList;
  }

  public void setCatList(List catList) {
    this.catList = catList;
  }

  public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
  {
    this.goodsCatManager = goodsCatManager;
  }

  public void setGoods(Goods goods) {
    this.goods = goods;
  }

  public IGoodsManager getGoodsManager()
  {
    return this.goodsManager;
  }

  public void setGoodsManager(IGoodsManager goodsManager)
  {
    this.goodsManager = goodsManager;
  }

  public Goods getGoods()
  {
    return this.goods;
  }

  public Integer getGoods_id()
  {
    return this.goods_id;
  }

  public void setGoods_id(Integer goods_id)
  {
    this.goods_id = goods_id;
  }

  public Map getGoodsView()
  {
    return this.goodsView;
  }

  public void setGoodsView(Map goodsView)
  {
    this.goodsView = goodsView;
  }

  public Boolean getIs_edit()
  {
    return this.is_edit;
  }

  public void setIs_edit(Boolean is_edit)
  {
    this.is_edit = is_edit;
  }

  public String getActionName()
  {
    return this.actionName;
  }

  public void setActionName(String actionName)
  {
    this.actionName = actionName;
  }

  public String getName()
  {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getOrder() {
    return this.order;
  }
  public void setOrder(String order) {
    this.order = order;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }

  public Integer[] getId()
  {
    return this.id;
  }

  public void setId(Integer[] id)
  {
    this.id = id;
  }

  public GoodsPluginBundle getGoodsPluginBundle() {
    return this.goodsPluginBundle;
  }

  public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
    this.goodsPluginBundle = goodsPluginBundle;
  }

  public List<String> getTagHtmlList() {
    return this.tagHtmlList;
  }

  public void setTagHtmlList(List<String> tagHtmlList) {
    this.tagHtmlList = tagHtmlList;
  }

  public Integer getCatid() {
    return this.catid;
  }

  public void setCatid(Integer catid) {
    this.catid = catid;
  }

  public Integer getMarket_enable() {
    return this.market_enable;
  }

  public void setMarket_enable(Integer marketEnable) {
    this.market_enable = marketEnable;
  }

  public Integer[] getTagids() {
    return this.tagids;
  }

  public void setTagids(Integer[] tagids) {
    this.tagids = tagids;
  }

  public ITagManager getTagManager() {
    return this.tagManager;
  }

  public void setTagManager(ITagManager tagManager) {
    this.tagManager = tagManager;
  }

  public IProductManager getProductManager()
  {
    return this.productManager;
  }

  public void setProductManager(IProductManager productManager) {
    this.productManager = productManager;
  }

  public IPermissionManager getPermissionManager() {
    return this.permissionManager;
  }

  public void setPermissionManager(IPermissionManager permissionManager) {
    this.permissionManager = permissionManager;
  }

  public IAdminUserManager getAdminUserManager() {
    return this.adminUserManager;
  }

  public void setAdminUserManager(IAdminUserManager adminUserManager) {
    this.adminUserManager = adminUserManager;
  }

  public String getOptype()
  {
    return this.optype;
  }

  public void setOptype(String optype) {
    this.optype = optype;
  }

  public int getDepotid() {
    return this.depotid;
  }

  public void setDepotid(int depotid) {
    this.depotid = depotid;
  }

  public List getBrandList() {
    return this.brandList;
  }

  public void setBrandList(List brandList) {
    this.brandList = brandList;
  }

  public IBrandManager getBrandManager() {
    return this.brandManager;
  }

  public void setBrandManager(IBrandManager brandManager) {
    this.brandManager = brandManager;
  }

  public IGoodsCatManager getGoodsCatManager() {
    return this.goodsCatManager;
  }

  public Integer getBrand_id()
  {
    return this.brand_id;
  }

  public void setBrand_id(Integer brand_id) {
    this.brand_id = brand_id;
  }

  public Integer getIs_market() {
    return this.is_market;
  }

  public void setIs_market(Integer is_market) {
    this.is_market = is_market;
  }

  public String getIs_other() {
    return this.is_other;
  }

  public void setIs_other(String is_other) {
    this.is_other = is_other;
  }

  public List<Tag> getTagList()
  {
    return this.tagList;
  }

  public void setTagList(List<Tag> tagList) {
    this.tagList = tagList;
  }

  public Map<Integer, String> getPluginTabs() {
    return this.pluginTabs;
  }

  public void setPluginTabs(Map<Integer, String> pluginTabs) {
    this.pluginTabs = pluginTabs;
  }

  public Map<Integer, String> getPluginHtmls() {
    return this.pluginHtmls;
  }

  public void setPluginHtmls(Map<Integer, String> pluginHtmls) {
    this.pluginHtmls = pluginHtmls;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsAction
 * JD-Core Version:    0.6.1
 */