package com.enation.app.shop.core.action.backend;

import com.enation.app.base.core.service.ISettingService;
import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.model.GoodsStores;
import com.enation.app.shop.core.service.IDepotManager;
import com.enation.app.shop.core.service.IDepotMonitorManager;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

public class DepotMonitorAction extends WWAction
{
  private IDepotMonitorManager depotMonitorManager;
  private List listTask;
  private int payTotal;
  private int pildTotal;
  private List<GoodsStores> storeList;
  private String name;
  private String sn;
  private Integer catid;
  private IGoodsManager goodsManager;
  private Goods goods;
  private int goodsid;
  private int totalCount;
  private String startDate;
  private String endDate;
  private int depotid;
  private int depotType = -1;
  private int opType = -1;
  private IDepotManager depotManager;
  private List list;
  private ISettingService settingService;

  public String listTask()
  {
    this.listTask = this.depotMonitorManager.getTaskList();
    return "listtask";
  }

  public String listAllocation()
  {
    this.listTask = this.depotMonitorManager.getAllocationList();
    return "listallocation";
  }

  public String listSend()
  {
    this.listTask = this.depotMonitorManager.getSendList();
    return "listsend";
  }

  public String listOrder()
  {
    this.payTotal = this.depotMonitorManager.getTotalByStatus(1);
    this.pildTotal = this.depotMonitorManager.getTotalByStatus(2);
    return "listorder";
  }

  public String listStock()
  {
    if (this.name != null) this.name = StringUtil.toUTF8(this.name);
    this.webpage = this.goodsManager.searchGoods(null, null, this.catid, this.name, this.sn, null, null, null, getPage(), getPageSize());
    return "liststock";
  }

  public String depotStock()
  {
    this.goods = this.goodsManager.getGoods(Integer.valueOf(this.goodsid));
    this.listTask = this.depotMonitorManager.depotidDepotByGoodsid(this.goodsid, this.goods.getCat_id().intValue());
    return "depotstock";
  }

  public String storeWarn()
  {
    String value = this.settingService.getSetting("shop", "goods_alerm_num");
    int num = 10;
    if (!StringUtil.isEmpty(value)) {
      num = StringUtil.toInt(value, true);
    }
    this.storeList = this.goodsManager.storeWarnGoods(num, getPage(), getPageSize());
    this.totalCount = this.storeList.size();
    return "storewarn";
  }

  public String listSell()
  {
    int start = 0; int end = 0;
    if ((this.startDate != null) && (!"".equals(this.startDate)) && (this.endDate != null) && (!"".equals(this.endDate))) {
      start = DateUtil.getDateline(this.startDate);
      end = DateUtil.getDateline(this.endDate) + 86400;
    }

    this.listTask = this.depotMonitorManager.searchOrderSalesAmout(start, end);
    return "listsell";
  }

  public String listSellNum()
  {
    if (this.catid == null)
      this.catid = Integer.valueOf(0);
    int start = 0; int end = 0;
    if ((this.startDate != null) && (!"".equals(this.startDate)) && (this.endDate != null) && (!"".equals(this.endDate))) {
      start = DateUtil.getDateline(this.startDate);
      end = DateUtil.getDateline(this.endDate) + 86400;
    }

    this.listTask = this.depotMonitorManager.searchOrderSaleNumber(start, end, this.catid.intValue());
    return "listsellnum";
  }

  public String listStockLog()
  {
    this.list = this.depotManager.list();
    int start = 0; int end = 0;
    if ((this.startDate != null) && (!"".equals(this.startDate)) && (this.endDate != null) && (!"".equals(this.endDate))) {
      start = DateUtil.getDateline(this.startDate);
      end = DateUtil.getDateline(this.endDate) + 86400;
    }
    this.webpage = this.depotMonitorManager.searchStoreLog(start, end, this.depotid, this.depotType, this.opType, getPage(), getPageSize());
    return "liststocklog";
  }

  public IDepotMonitorManager getDepotMonitorManager() {
    return this.depotMonitorManager;
  }
  public void setDepotMonitorManager(IDepotMonitorManager depotMonitorManager) {
    this.depotMonitorManager = depotMonitorManager;
  }

  public List getListTask() {
    return this.listTask;
  }

  public void setListTask(List listTask) {
    this.listTask = listTask;
  }
  public int getPayTotal() {
    return this.payTotal;
  }
  public void setPayTotal(int payTotal) {
    this.payTotal = payTotal;
  }
  public int getPildTotal() {
    return this.pildTotal;
  }
  public void setPildTotal(int pildTotal) {
    this.pildTotal = pildTotal;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }
  public Integer getCatid() {
    return this.catid;
  }
  public void setCatid(Integer catid) {
    this.catid = catid;
  }
  public IGoodsManager getGoodsManager() {
    return this.goodsManager;
  }
  public void setGoodsManager(IGoodsManager goodsManager) {
    this.goodsManager = goodsManager;
  }
  public Goods getGoods() {
    return this.goods;
  }
  public void setGoods(Goods goods) {
    this.goods = goods;
  }
  public int getGoodsid() {
    return this.goodsid;
  }
  public void setGoodsid(int goodsid) {
    this.goodsid = goodsid;
  }

  public static void main(String[] args) {
    Date d1 = DateUtil.toDate("2011-10-01 00:10", "yyyy-MM-dd HH:mm");
    Date d1_1 = DateUtil.toDate("2011-10-01 23:01", "yyyy-MM-dd HH:mm");
    Date d2 = DateUtil.toDate("2011-10-02 00:01", "yyyy-MM-dd HH:mm");
    Date d2_1 = DateUtil.toDate("2011-10-02 19:01", "yyyy-MM-dd HH:mm");
    int t = 86400;
    System.out.println(d1.getTime() / 1000L / t + "---" + d1_1.getTime() / 1000L / t);
    System.out.println(d2.getTime() / 1000L / t + "---" + d2_1.getTime() / 1000L / t);

    t = 86400000;
    System.out.println(d1.getTime() / t + "---" + d1_1.getTime() / t);
    System.out.println(d2.getTime() / t + "---" + d2_1.getTime() / t);
  }
  public String getStartDate() {
    return this.startDate;
  }
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
  public String getEndDate() {
    return this.endDate;
  }
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  public int getDepotid() {
    return this.depotid;
  }
  public void setDepotid(int depotid) {
    this.depotid = depotid;
  }
  public int getDepotType() {
    return this.depotType;
  }
  public void setDepotType(int depotType) {
    this.depotType = depotType;
  }
  public int getOpType() {
    return this.opType;
  }
  public void setOpType(int opType) {
    this.opType = opType;
  }
  public IDepotManager getDepotManager() {
    return this.depotManager;
  }
  public void setDepotManager(IDepotManager depotManager) {
    this.depotManager = depotManager;
  }
  public List getList() {
    return this.list;
  }
  public void setList(List list) {
    this.list = list;
  }
  public ISettingService getSettingService() {
    return this.settingService;
  }
  public void setSettingService(ISettingService settingService) {
    this.settingService = settingService;
  }
  public List<GoodsStores> getStoreList() {
    return this.storeList;
  }
  public void setStoreList(List<GoodsStores> storeList) {
    this.storeList = storeList;
  }
  public int getTotalCount() {
    return this.totalCount;
  }
  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.DepotMonitorAction
 * JD-Core Version:    0.6.1
 */