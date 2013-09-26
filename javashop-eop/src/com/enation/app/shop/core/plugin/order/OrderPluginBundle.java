package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Allocation;
import com.enation.app.shop.core.model.AllocationItem;
import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.OrderItem;
import com.enation.app.shop.core.model.support.CartItem;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import com.enation.framework.plugin.IPlugin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.logging.Log;

public class OrderPluginBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "订单插件桩";
  }

  public Map<Integer, String> getTabList(Order order)
  {
    List<IPlugin> plugins = getPlugins();

    Map tabMap = new TreeMap();
    if (plugins != null)
    {
      for (IPlugin plugin : plugins) {
        if ((plugin instanceof IOrderTabShowEvent))
        {
          IOrderTabShowEvent event = (IOrderTabShowEvent)plugin;

          if (event.canBeExecute(order))
          {
            String name = event.getTabName(order);
            tabMap.put(Integer.valueOf(event.getOrder()), name);
          }
        }

      }

    }

    return tabMap;
  }

  public Map<Integer, String> getDetailHtml(Order order)
  {
    Map htmlMap = new TreeMap();
    FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
    freeMarkerPaser.putData("order", order);
    List<IPlugin> plugins = getPlugins();

    if (plugins != null) {
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IShowOrderDetailHtmlEvent)) {
          IShowOrderDetailHtmlEvent event = (IShowOrderDetailHtmlEvent)plugin;
          freeMarkerPaser.setClz(event.getClass());
          if ((plugin instanceof IOrderTabShowEvent))
          {
            IOrderTabShowEvent tabEvent = (IOrderTabShowEvent)plugin;

            if (tabEvent.canBeExecute(order))
            {
              String html = event.onShowOrderDetailHtml(order);
              htmlMap.put(Integer.valueOf(tabEvent.getOrder()), html);
            }
          }
        }
      }
    }

    return htmlMap;
  }

  public void onBeforeCreate(Order order, List<CartItem> itemList, String sessionid)
  {
    try {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IBeforeOrderCreateEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onBeforeCreate 开始...");
            }
            IBeforeOrderCreateEvent event = (IBeforeOrderCreateEvent)plugin;
            event.onBeforeOrderCreate(order, itemList, sessionid);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onBeforeCreate 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[Before创建]事件错误", e);
      throw e;
    }
  }

  public void onAfterCreate(Order order, List<CartItem> itemList, String sessionid) {
    try { List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IAfterOrderCreateEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onAfterCreate 开始...");
            }
            IAfterOrderCreateEvent event = (IAfterOrderCreateEvent)plugin;
            event.onAfterOrderCreate(order, itemList, sessionid);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onAfterCreate 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[After创建]事件错误", e);
      throw e;
    }
  }

  public void onFilter(Integer orderid, List<OrderItem> itemList) {
    try {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IOrderItemFilter)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onFilter 开始...");
            }
            IOrderItemFilter event = (IOrderItemFilter)plugin;
            event.filter(orderid, itemList);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onFilter 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[filter]事件错误", e);
      throw e;
    }
  }

  public void onShip(Delivery delivery, List<DeliveryItem> itemList)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IOrderShipEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onShip 开始...");
            }
            IOrderShipEvent event = (IOrderShipEvent)plugin;
            event.ship(delivery, itemList);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onShip 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[ship]事件错误", e);
      throw e;
    }
  }

  public void onItemShip(Order order, DeliveryItem deliveryItem, AllocationItem allocationItem)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IOrderShipEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onItemShip 开始...");
            }
            IOrderShipEvent event = (IOrderShipEvent)plugin;
            if (event.canBeExecute(allocationItem.getCat_id())) {
              event.itemShip(order, deliveryItem, allocationItem);
            }

            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onItemShip 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[onItemShip]事件错误", e);
      throw e;
    }
  }

  public void onReturned(Delivery delivery, List<DeliveryItem> itemList)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IOrderReturnsEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onReturned 开始...");
            }
            IOrderReturnsEvent event = (IOrderReturnsEvent)plugin;
            event.returned(delivery, itemList);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onReturned 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled()) {
        loger.error("调用订单插件[returned]事件错误", e);
      }
      throw e;
    }
  }

  public void onDelete(Integer[] orderId)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IOrderDeleteEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onDelete 开始...");
            }
            IOrderDeleteEvent event = (IOrderDeleteEvent)plugin;
            event.delete(orderId);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onDelete 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[delete]事件错误", e);
      throw e;
    }
  }

  public void onAllocationItem(AllocationItem allocationItem)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IOrderAllocationItemEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onAllocationItem 开始...");
            }
            IOrderAllocationItemEvent event = (IOrderAllocationItemEvent)plugin;
            if (event.canBeExecute(allocationItem.getCat_id())) {
              event.onAllocation(allocationItem);
            }
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onAllocationItem 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      loger.error("调用订单插件[onAllocationItem]事件错误", e);

      throw e;
    }
  }

  public void onAllocation(Allocation allocation)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IOrderAllocationEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onAllocation 开始...");
            }
            IOrderAllocationEvent event = (IOrderAllocationEvent)plugin;
            event.onAllocation(allocation);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onAllocation 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      loger.error("调用订单插件[onAllocation]事件错误", e);

      throw e;
    }
  }

  public void filterAlloItem(int catid, Map values, ResultSet rs)
    throws SQLException
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof IOrderAllocationItemEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " filterAlloViewItem 开始...");
            }
            IOrderAllocationItemEvent event = (IOrderAllocationItemEvent)plugin;
            if (event.canBeExecute(catid)) {
              ((IOrderAllocationItemEvent)plugin).filterAlloViewItem(values, rs);
            }
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " filterAlloViewItem 结束.");
          }
        }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[filterAlloViewItem]事件错误", e);
      throw e;
    }
  }

  public String getAllocationHtml(OrderItem item)
  {
    String html = null;
    try {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof IOrderAllocationItemEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " getAllocationHtml 开始...");
            }
            IOrderAllocationItemEvent event = (IOrderAllocationItemEvent)plugin;
            if (event.canBeExecute(item.getCat_id())) {
              html = ((IOrderAllocationItemEvent)plugin).getAllocationStoreHtml(item);
            }
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " getAllocationHtml 结束.");
            }
          }
        }
      }

      return html;
    } catch (RuntimeException e) {
      if (loger.isErrorEnabled()) {
        loger.error("调用订单插件[getAllocationHtml]事件错误", e);
      }
      throw e;
    }
  }

  public String getAllocationViewHtml(OrderItem item)
  {
    String html = null;
    try {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof IOrderAllocationItemEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " getAllocationViewHtml 开始...");
            }
            IOrderAllocationItemEvent event = (IOrderAllocationItemEvent)plugin;
            if (event.canBeExecute(item.getCat_id())) {
              html = ((IOrderAllocationItemEvent)plugin).getAllocationViewHtml(item);
            }
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " getAllocationViewHtml 结束.");
            }
          }
        }
      }

      return html;
    } catch (RuntimeException e) {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[getAllocationViewHtml]事件错误", e);
      throw e;
    }
  }

  public void onPay(Order order, boolean isOnline)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof IOrderPayEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " pay 开始...");
            }
            IOrderPayEvent event = (IOrderPayEvent)plugin;
            event.pay(order, isOnline);

            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " pay 结束.");
          }
        }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[pay]事件错误", e);
      throw e;
    }
  }

  public void onCanel(Order order) {
    try {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof IOrderCanelEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " canel 开始...");
            }
            IOrderCanelEvent event = (IOrderCanelEvent)plugin;
            event.canel(order);

            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " canel 结束.");
          }
        }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[canel]事件错误", e);
      throw e;
    }
  }

  public void onRestore(Order order) {
    try {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof IOrderRestoreEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " canel 开始...");
            }
            IOrderRestoreEvent event = (IOrderRestoreEvent)plugin;
            event.restore(order);

            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " canel 结束.");
          }
        }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[canel]事件错误", e);
      throw e;
    }
  }

  public void onConfirmPay(Order order)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof IOrderConfirmPayEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " confirmPay 开始...");
            }
            IOrderConfirmPayEvent event = (IOrderConfirmPayEvent)plugin;
            event.confirmPay(order);

            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " confirmPay 结束.");
          }
        }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用订单插件[confirmPay]事件错误", e);
      throw e;
    }
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.order.OrderPluginBundle
 * JD-Core Version:    0.6.1
 */