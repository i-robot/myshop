package com.enation.app.shop.core.plugin.member;

import com.enation.app.base.core.model.Member;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import com.enation.framework.plugin.IPlugin;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.logging.Log;

public class MemberPluginBundle extends AutoRegisterPluginsBundle
{
  public void onLogout(Member member)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IMemberLogoutEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onLogout 开始...");
            }
            IMemberLogoutEvent event = (IMemberLogoutEvent)plugin;
            event.onLogout(member);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onLogout 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用会员插件注销事件错误", e);
      throw e;
    }
  }

  public void onLogin(Member member, Long upLogintime)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IMemberLoginEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onLogin 开始...");
            }
            IMemberLoginEvent event = (IMemberLoginEvent)plugin;
            event.onLogin(member, upLogintime);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onLogin 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用会员插件登录事件错误", e);
      throw e;
    }
  }

  public void onRegister(Member member)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IMemberRegisterEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onRegister 开始...");
            }
            IMemberRegisterEvent event = (IMemberRegisterEvent)plugin;
            event.onRegister(member);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onRegister 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用会员插件注册事件错误", e);
      throw e;
    }
  }

  public void onEmailCheck(Member member)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IMemberEmailCheckEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onRegister 开始...");
            }
            IMemberEmailCheckEvent event = (IMemberEmailCheckEvent)plugin;
            event.onEmailCheck(member);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onRegister 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用会员插件邮件验证事件错误", e);
      throw e;
    }
  }

  public void onUpdatePassword(String password, int memberid)
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IMemberUpdatePasswordEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onUpdatePassword 开始...");
            }
            IMemberUpdatePasswordEvent event = (IMemberUpdatePasswordEvent)plugin;
            event.updatePassword(password, memberid);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onUpdatePassword 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用会员更新密码事件错误", e);
      throw e;
    }
  }

  public Map<Integer, String> getTabList(Member member)
  {
    List<IPlugin> plugins = getPlugins();

    Map tabMap = new TreeMap();
    if (plugins != null)
    {
      for (IPlugin plugin : plugins) {
        if ((plugin instanceof IMemberTabShowEvent))
        {
          IMemberTabShowEvent event = (IMemberTabShowEvent)plugin;

          if (event.canBeExecute(member))
          {
            String name = event.getTabName(member);
            tabMap.put(Integer.valueOf(event.getOrder()), name);
          }
        }

      }

    }

    return tabMap;
  }

  public Map<Integer, String> getDetailHtml(Member member)
  {
    Map htmlMap = new TreeMap();
    FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
    freeMarkerPaser.putData("member", member);
    List<IPlugin> plugins = getPlugins();

    if (plugins != null) {
      for (IPlugin plugin : plugins)
      {
        if ((plugin instanceof IMemberTabShowEvent)) {
          IMemberTabShowEvent event = (IMemberTabShowEvent)plugin;
          freeMarkerPaser.setClz(event.getClass());

          if (event.canBeExecute(member))
          {
            String html = event.onShowMemberDetailHtml(member);
            htmlMap.put(Integer.valueOf(event.getOrder()), html);
          }
        }
      }

    }

    return htmlMap;
  }

  public String getName()
  {
    return "会员插件桩";
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.member.MemberPluginBundle
 * JD-Core Version:    0.6.1
 */