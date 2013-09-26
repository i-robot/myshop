package com.enation.app.shop.core.plugin.goods;

import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import com.enation.framework.plugin.IPlugin;
import com.enation.framework.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;

public class GoodsPluginBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "商品插件桩";
  }

  public Map<Integer, String> getTabList()
  {
    Map tabMap = new TreeMap();
    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
    {
      List tabList = new ArrayList();
      for (IPlugin plugin : plugins) {
        if ((plugin instanceof IGoodsTabShowEvent)) {
          IGoodsTabShowEvent event = (IGoodsTabShowEvent)plugin;
          String name = event.getTabName();
          if ((!StringUtil.isEmpty(name)) && 
            ((plugin instanceof IGoodsTabShowEvent))) {
            IGoodsTabShowEvent tabEvent = (IGoodsTabShowEvent)plugin;
            tabMap.put(Integer.valueOf(tabEvent.getOrder()), name);
          }

        }

      }

    }

    return tabMap;
  }

  public void onBeforeAdd(Map goods)
  {
    ActionContext ctx = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGoodsBeforeAddEvent)) {
          if (loger.isDebugEnabled()) {
            loger.debug("调用插件 : " + plugin.getClass() + " onBeforeGoodsAdd 开始...");
          }
          IGoodsBeforeAddEvent event = (IGoodsBeforeAddEvent)plugin;
          event.onBeforeGoodsAdd(goods, request);
          if (loger.isDebugEnabled()) {
            loger.debug("调用插件 : " + plugin.getClass() + " onBeforeGoodsAdd 结束.");
          }
        }
        else if (loger.isDebugEnabled()) {
          loger.debug(" no,skip...");
        }
      }
  }

  public void onAfterAdd(Map goods)
  {
    ActionContext ctx = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
      {
        if (loger.isDebugEnabled()) {
          loger.debug("check plugin : " + plugin.getClass() + " is IGoodsAfterAddEvent ?");
        }

        if ((plugin instanceof IGoodsAfterAddEvent)) {
          if (loger.isDebugEnabled()) {
            loger.debug(" yes ,do event...");
          }
          IGoodsAfterAddEvent event = (IGoodsAfterAddEvent)plugin;
          event.onAfterGoodsAdd(goods, request);
        }
        else if (loger.isDebugEnabled()) {
          loger.debug(" no,skip...");
        }
      }
  }

  public Map<Integer, String> onFillAddInputData()
  {
    Map htmlMap = new TreeMap();

    HttpServletRequest request = ThreadContextHolder.getHttpRequest();

    FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
    freeMarkerPaser.putData("goods", new HashMap(0));
    List<IPlugin> plugins = getPlugins();

    if (plugins != null) {
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGetGoodsAddHtmlEvent)) {
          IGetGoodsAddHtmlEvent event = (IGetGoodsAddHtmlEvent)plugin;
          freeMarkerPaser.setClz(event.getClass());
          String html = event.getAddHtml(request);
          if ((!StringUtil.isEmpty(html)) && 
            ((plugin instanceof IGoodsTabShowEvent))) {
            IGoodsTabShowEvent tabEvent = (IGoodsTabShowEvent)plugin;
            htmlMap.put(Integer.valueOf(tabEvent.getOrder()), html);
          }
        }

      }

    }

    return htmlMap;
  }

  public Map<Integer, String> onFillEditInputData(Map goods)
  {
    Map htmlMap = new TreeMap();
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
    freeMarkerPaser.putData("goods", goods);
    List<IPlugin> plugins = getPlugins();

    if (plugins != null) {
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGetGoodsEditHtmlEvent)) {
          IGetGoodsEditHtmlEvent event = (IGetGoodsEditHtmlEvent)plugin;
          freeMarkerPaser.setClz(event.getClass());
          freeMarkerPaser.setPageName(null);
          String html = event.getEditHtml(goods, request);
          if ((!StringUtil.isEmpty(html)) && 
            ((plugin instanceof IGoodsTabShowEvent))) {
            IGoodsTabShowEvent tabEvent = (IGoodsTabShowEvent)plugin;
            htmlMap.put(Integer.valueOf(tabEvent.getOrder()), html);
          }
        }
      }
    }

    return htmlMap;
  }

  public void onBeforeEdit(Map goods)
  {
    ActionContext ctx = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGoodsBeforeEditEvent))
        {
          if (loger.isDebugEnabled()) {
            loger.debug("调用插件[" + plugin.getClass() + "] onBeforeGoodsEdit 开始...");
          }
          IGoodsBeforeEditEvent event = (IGoodsBeforeEditEvent)plugin;
          event.onBeforeGoodsEdit(goods, request);
          if (loger.isDebugEnabled())
            loger.debug("调用插件[" + plugin.getClass() + "] onBeforeGoodsEdit 结束.");
        }
      }
  }

  public void onAfterEdit(Map goods)
  {
    ActionContext ctx = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGoodsAfterEditEvent)) {
          IGoodsAfterEditEvent event = (IGoodsAfterEditEvent)plugin;
          event.onAfterGoodsEdit(goods, request);
        }
      }
  }

  public void onVisit(Map goods)
  {
    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGoodsVisitEvent)) {
          IGoodsVisitEvent event = (IGoodsVisitEvent)plugin;
          event.onVisit(goods);
        }
      }
  }

  public void onGoodsDelete(Integer[] goodsid)
  {
    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGoodsDeleteEvent)) {
          IGoodsDeleteEvent event = (IGoodsDeleteEvent)plugin;
          event.onGoodsDelete(goodsid);
        }
      }
  }

  public String onGetSelector()
  {
    StringBuffer sql = new StringBuffer();
    List<IPlugin> plugins = getPlugins();

    if (plugins != null) {
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGoodsSearchFilter)) {
          IGoodsSearchFilter event = (IGoodsSearchFilter)plugin;
          sql.append(event.getSelector());
        }
      }
    }
    return sql.toString();
  }

  public String onGetFrom()
  {
    StringBuffer sql = new StringBuffer();
    List<IPlugin> plugins = getPlugins();

    if (plugins != null) {
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGoodsSearchFilter)) {
          IGoodsSearchFilter event = (IGoodsSearchFilter)plugin;
          sql.append(event.getFrom());
        }
      }
    }
    return sql.toString();
  }

  public void onSearchFilter(StringBuffer sql)
  {
    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IGoodsSearchFilter)) {
          IGoodsSearchFilter event = (IGoodsSearchFilter)plugin;
          event.filter(sql);
        }
      }
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.GoodsPluginBundle
 * JD-Core Version:    0.6.1
 */