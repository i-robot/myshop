package com.enation.app.shop.core.plugin.cart;

import com.enation.app.shop.core.model.Cart;
import com.enation.app.shop.core.model.support.CartItem;
import com.enation.app.shop.core.model.support.OrderPrice;
import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import com.enation.framework.plugin.IPlugin;
import java.util.List;
import org.apache.commons.logging.Log;

public class CartPluginBundle extends AutoRegisterPluginsBundle
{
  public OrderPrice coutPrice(OrderPrice orderpice)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof ICountPriceEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + "cart.countPrice开始...");
            }
            ICountPriceEvent event = (ICountPriceEvent)plugin;
            orderpice = event.countPrice(orderpice);
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " cart.countPrice结束.");
            }
          }
          else if (loger.isDebugEnabled()) {
            loger.debug(" no,skip...");
          }
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return orderpice;
  }

  public void filterList(List<CartItem> itemList, String sessionid) {
    try {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof ICartItemFilter)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + "cart.add开始...");
            }
            ICartItemFilter event = (ICartItemFilter)plugin;
            event.filter(itemList, sessionid);
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " cart.add结束.");
            }
          }
          else if (loger.isDebugEnabled()) {
            loger.debug(" no,skip...");
          }
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void onAdd(Cart cart)
  {
    try {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof ICartAddEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + "cart.add开始...");
            }
            ICartAddEvent event = (ICartAddEvent)plugin;
            event.add(cart);
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " cart.add结束.");
            }
          }
          else if (loger.isDebugEnabled()) {
            loger.debug(" no,skip...");
          }
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void onDelete(String sessionid, Integer cartid)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins) {
          if ((plugin instanceof ICartDeleteEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + "cart.delete开始...");
            }
            ICartDeleteEvent event = (ICartDeleteEvent)plugin;
            event.delete(sessionid, cartid);
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " cart.delete结束.");
            }
          }
          else if (loger.isDebugEnabled()) {
            loger.debug(" no,skip...");
          }
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getName()
  {
    return "购物车插件桩";
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.cart.CartPluginBundle
 * JD-Core Version:    0.6.1
 */