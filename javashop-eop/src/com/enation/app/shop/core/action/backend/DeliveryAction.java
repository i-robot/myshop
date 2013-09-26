package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.service.IRegionsManager;
import com.enation.app.shop.core.model.Allocation;
import com.enation.app.shop.core.model.AllocationItem;
import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.service.IDepotManager;
import com.enation.app.shop.core.service.IDlyTypeManager;
import com.enation.app.shop.core.service.IGoodsStoreManager;
import com.enation.app.shop.core.service.ILogiManager;
import com.enation.app.shop.core.service.IOrderFlowManager;
import com.enation.app.shop.core.service.IOrderManager;
import com.enation.framework.action.WWAction;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class DeliveryAction extends WWAction
{
  private IDepotManager depotManager;
  private ILogiManager logiManager;
  private IDlyTypeManager dlyTypeManager;
  private IOrderManager orderManager;
  private IOrderFlowManager orderFlowManager;
  private IRegionsManager regionsManager;
  private IGoodsStoreManager goodsStoreManager;
  private Integer orderId;
  private Order ord;
  private List logiList;
  private List dlyTypeList;
  private List itemList;
  private List giftList;
  private List depotList;
  private String allocationHtml;
  private Integer productid;
  private Integer depotid;
  private Delivery delivery;
  private String[] goods_nameArray;
  private String[] goods_snArray;
  private Integer[] goods_idArray;
  private Integer[] product_idArray;
  private Integer[] numArray;
  private Integer[] depot_idArray;
  private Integer[] cat_idArray;
  private Integer[] item_idArray;
  private int province_id;
  private int city_id;
  private int region_id;
  private String province;
  private String city;
  private String region;
  private int itemid;
  private int id;

  public String showAllocationDialog()
  {
    this.ord = this.orderManager.get(this.orderId);
    this.itemList = this.orderManager.listGoodsItems(this.orderId);
    this.depotList = this.depotManager.list();
    return "allocation_dialog";
  }

  public String getProductStore()
  {
    this.allocationHtml = this.orderFlowManager.getAllocationHtml(this.itemid);
    return "product_store";
  }

  public String viewProductStore()
  {
    this.allocationHtml = this.orderFlowManager.getAllocationViewHtml(this.itemid);
    return "view_product_store";
  }

  public String allocation()
  {
    try
    {
      Allocation allocation = new Allocation();
      allocation.setOrderid(this.orderId.intValue());
      allocation.setShipDepotId(this.depotid.intValue());

      List aitemList = new ArrayList();
      int i = 0;
      for (Integer goods_id : this.goods_idArray)
      {
        AllocationItem item = new AllocationItem();
        item.setDepotid(this.depot_idArray[i].intValue());
        item.setGoodsid(goods_id.intValue());
        item.setNum(this.numArray[i].intValue());
        item.setProductid(this.product_idArray[i].intValue());
        item.setCat_id(this.cat_idArray[i].intValue());
        item.setItemid(this.item_idArray[i].intValue());
        aitemList.add(item);
        i++;
      }

      allocation.setItemList(aitemList);
      this.orderFlowManager.allocation(allocation);
      Order order = this.orderManager.get(this.orderId);
      this.json = ("{result:1,message:'订单[" + order.getSn() + "]配货成功',orderStatus:" + order.getStatus() + ",payStatus:" + order.getPay_status() + ",shipStatus:" + order.getShip_status() + "}");
    } catch (RuntimeException e) {
      this.logger.error("配货失败", e);
      showErrorJson("配货失败，错误信息：" + e.getMessage());
    }

    return "json_message";
  }

  public String allocationFinish()
  {
    try
    {
      this.orderFlowManager.updateAllocation(this.id, this.orderId.intValue());
      this.json = "{result:1,message:'确认配货成功'}";
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e);
      }
      this.json = ("{result:0,message:\"确认配货失败：" + e.getMessage() + "\"}");
    }

    return "json_message";
  }

  public String showShipDialog()
  {
    fillShipData();

    this.itemList = this.orderFlowManager.listNotShipGoodsItem(this.orderId);

    return "ship_dialog";
  }

  public String showReturnDialog()
  {
    fillShipData();

    this.itemList = this.orderFlowManager.listShipGoodsItem(this.orderId);

    return "return_dialog";
  }

  public String showChangeDialog()
  {
    fillShipData();

    this.itemList = this.orderFlowManager.listShipGoodsItem(this.orderId);

    return "change_dialog";
  }

  private void fillShipData()
  {
    this.logiList = this.logiManager.list();
    this.dlyTypeList = this.dlyTypeManager.list();
    this.ord = this.orderManager.get(this.orderId);
  }

  public String ship()
  {
    try
    {
      this.delivery.setProvince(this.province);
      this.delivery.setCity(this.city);
      this.delivery.setRegion(this.region);

      this.delivery.setProvince_id(this.province_id);
      this.delivery.setCity_id(this.city_id);
      this.delivery.setRegion_id(this.region_id);

      List itemList = new ArrayList();
      int i = 0;
      for (Integer goods_id : this.goods_idArray)
      {
        DeliveryItem item = new DeliveryItem();
        item.setGoods_id(goods_id);
        item.setName(this.goods_nameArray[i]);
        item.setNum(this.numArray[i]);
        item.setProduct_id(this.product_idArray[i]);
        item.setSn(this.goods_snArray[i]);
        item.setOrder_itemid(this.item_idArray[i].intValue());
        item.setItemtype(Integer.valueOf(0));
        itemList.add(item);
        i++;
      }

      this.delivery.setOrder_id(this.orderId);
      this.orderFlowManager.shipping(this.delivery, itemList);
      Order order = this.orderManager.get(this.orderId);
      this.json = ("{result:1,message:'订单[" + order.getSn() + "]发货成功',orderStatus:" + order.getStatus() + ",payStatus:" + order.getPay_status() + ",shipStatus:" + order.getShip_status() + "}");
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e.getStackTrace());
        this.json = ("{result:0,message:\"发货失败：" + e.getLocalizedMessage() + "\"}");
      }
    }

    return "json_message";
  }

  public String returned()
  {
    try
    {
      List itemList = new ArrayList();
      int i = 0;
      for (Integer goods_id : this.goods_idArray)
      {
        DeliveryItem item = new DeliveryItem();
        item.setGoods_id(goods_id);
        item.setName(this.goods_nameArray[i]);
        item.setNum(this.numArray[i]);
        item.setProduct_id(this.product_idArray[i]);
        item.setSn(this.goods_snArray[i]);
        itemList.add(item);
        i++;
      }

      i = 0;
      List giftitemList = new ArrayList();

      this.delivery.setProvince(this.province);
      this.delivery.setCity(this.city);
      this.delivery.setRegion(this.region);

      this.delivery.setProvince_id(this.province_id);
      this.delivery.setCity_id(this.city_id);
      this.delivery.setRegion_id(this.region_id);

      this.delivery.setOrder_id(this.orderId);
      this.orderFlowManager.returned(this.delivery, itemList, giftitemList);
      Order order = this.orderManager.get(this.orderId);
      this.json = ("{result:1,message:'订单[" + order.getSn() + "]退货成功',shipStatus:" + order.getShip_status() + "}");
    } catch (RuntimeException e) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug(e.getStackTrace());
        this.json = ("{result:0,message:\"退货失败：" + e.getLocalizedMessage() + "\"}");
      }
    }

    return "json_message";
  }

  public String change()
  {
    try
    {
      List itemList = new ArrayList();
      int i = 0;
      for (Integer goods_id : this.goods_idArray)
      {
        DeliveryItem item = new DeliveryItem();
        item.setGoods_id(goods_id);
        item.setName(this.goods_nameArray[i]);
        item.setNum(this.numArray[i]);
        item.setProduct_id(this.product_idArray[i]);
        item.setSn(this.goods_snArray[i]);
        itemList.add(item);
        i++;
      }

      this.delivery.setOrder_id(this.orderId);
      this.orderFlowManager.change(this.delivery, itemList, null);
      Order order = this.orderManager.get(this.orderId);
      this.json = ("{result:1,message:'订单[" + order.getSn() + "]换货成功',shipStatus:" + order.getShip_status() + "}");
    }
    catch (RuntimeException e)
    {
      this.logger.error(e.getMessage(), e);

      this.json = ("{result:0,message:\"换货失败：" + e.getLocalizedMessage() + "\"}");
    }

    return "json_message";
  }

  public ILogiManager getLogiManager() {
    return this.logiManager;
  }

  public void setLogiManager(ILogiManager logiManager)
  {
    this.logiManager = logiManager;
  }

  public IDlyTypeManager getDlyTypeManager()
  {
    return this.dlyTypeManager;
  }

  public void setDlyTypeManager(IDlyTypeManager dlyTypeManager)
  {
    this.dlyTypeManager = dlyTypeManager;
  }

  public List getLogiList()
  {
    return this.logiList;
  }

  public void setLogiList(List logiList)
  {
    this.logiList = logiList;
  }

  public List getDlyTypeList()
  {
    return this.dlyTypeList;
  }

  public void setDlyTypeList(List dlyTypeList)
  {
    this.dlyTypeList = dlyTypeList;
  }

  public IOrderManager getOrderManager()
  {
    return this.orderManager;
  }

  public void setOrderManager(IOrderManager orderManager)
  {
    this.orderManager = orderManager;
  }

  public Integer getOrderId()
  {
    return this.orderId;
  }

  public void setOrderId(Integer orderId)
  {
    this.orderId = orderId;
  }

  public Order getOrd()
  {
    return this.ord;
  }

  public void setOrd(Order ord)
  {
    this.ord = ord;
  }

  public IOrderFlowManager getOrderFlowManager()
  {
    return this.orderFlowManager;
  }

  public void setOrderFlowManager(IOrderFlowManager orderFlowManager)
  {
    this.orderFlowManager = orderFlowManager;
  }

  public List getItemList()
  {
    return this.itemList;
  }

  public void setItemList(List itemList)
  {
    this.itemList = itemList;
  }

  public Delivery getDelivery()
  {
    return this.delivery;
  }

  public void setDelivery(Delivery delivery)
  {
    this.delivery = delivery;
  }

  public String[] getGoods_nameArray()
  {
    return this.goods_nameArray;
  }

  public void setGoods_nameArray(String[] goodsNameArray)
  {
    this.goods_nameArray = goodsNameArray;
  }

  public Integer[] getGoods_idArray()
  {
    return this.goods_idArray;
  }

  public void setGoods_idArray(Integer[] goodsIdArray)
  {
    this.goods_idArray = goodsIdArray;
  }

  public Integer[] getProduct_idArray()
  {
    return this.product_idArray;
  }

  public void setProduct_idArray(Integer[] productIdArray)
  {
    this.product_idArray = productIdArray;
  }

  public Integer[] getNumArray()
  {
    return this.numArray;
  }

  public void setNumArray(Integer[] numArray)
  {
    this.numArray = numArray;
  }

  public List getGiftList()
  {
    return this.giftList;
  }

  public void setGiftList(List giftList)
  {
    this.giftList = giftList;
  }

  public String[] getGoods_snArray()
  {
    return this.goods_snArray;
  }

  public void setGoods_snArray(String[] goodsSnArray)
  {
    this.goods_snArray = goodsSnArray;
  }
  public IDepotManager getDepotManager() {
    return this.depotManager;
  }
  public void setDepotManager(IDepotManager depotManager) {
    this.depotManager = depotManager;
  }
  public List getDepotList() {
    return this.depotList;
  }
  public void setDepotList(List depotList) {
    this.depotList = depotList;
  }

  public IRegionsManager getRegionsManager() {
    return this.regionsManager;
  }
  public void setRegionsManager(IRegionsManager regionsManager) {
    this.regionsManager = regionsManager;
  }

  public Integer getProductid() {
    return this.productid;
  }

  public void setProductid(Integer productid) {
    this.productid = productid;
  }

  public IGoodsStoreManager getGoodsStoreManager() {
    return this.goodsStoreManager;
  }

  public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
    this.goodsStoreManager = goodsStoreManager;
  }

  public Integer getDepotid() {
    return this.depotid;
  }

  public void setDepotid(Integer depotid)
  {
    this.depotid = depotid;
  }

  public Integer[] getDepot_idArray() {
    return this.depot_idArray;
  }

  public void setDepot_idArray(Integer[] depot_idArray) {
    this.depot_idArray = depot_idArray;
  }

  public int getItemid() {
    return this.itemid;
  }

  public void setItemid(int itemid) {
    this.itemid = itemid;
  }

  public Integer[] getCat_idArray() {
    return this.cat_idArray;
  }

  public void setCat_idArray(Integer[] cat_idArray) {
    this.cat_idArray = cat_idArray;
  }

  public String getAllocationHtml()
  {
    return this.allocationHtml;
  }

  public void setAllocationHtml(String allocationHtml) {
    this.allocationHtml = allocationHtml;
  }

  public Integer[] getItem_idArray() {
    return this.item_idArray;
  }

  public void setItem_idArray(Integer[] item_idArray) {
    this.item_idArray = item_idArray;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getProvince_id() {
    return this.province_id;
  }

  public void setProvince_id(int province_id) {
    this.province_id = province_id;
  }

  public int getCity_id() {
    return this.city_id;
  }

  public void setCity_id(int city_id) {
    this.city_id = city_id;
  }

  public int getRegion_id() {
    return this.region_id;
  }

  public void setRegion_id(int region_id) {
    this.region_id = region_id;
  }

  public String getProvince() {
    return this.province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getRegion() {
    return this.region;
  }

  public void setRegion(String region) {
    this.region = region;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.DeliveryAction
 * JD-Core Version:    0.6.1
 */