package com.enation.app.base.core.plugin.job;

import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import com.enation.framework.plugin.IPlugin;
import java.util.List;
import org.apache.commons.logging.Log;

public class JobExecutePluginsBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "任务执行器插件桩";
  }

  public void everyHourExcecute()
  {
    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
        if ((plugin instanceof IEveryHourExecuteEvent))
        {
          if (loger.isDebugEnabled()) {
            loger.debug("调度任务插件[" + plugin.getClass() + "]everyHour开始执行...");
          }

          IEveryHourExecuteEvent event = (IEveryHourExecuteEvent)plugin;
          event.everyHour();

          if (loger.isDebugEnabled())
            loger.debug("调度任务插件[" + plugin.getClass() + "]everyHour执行完成...");
        }
  }

  public void everyDayExcecute()
  {
    try
    {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IEveryDayExecuteEvent))
          {
            if (loger.isDebugEnabled()) {
              loger.debug("调度任务插件[" + plugin.getClass() + "]everyDay开始执行...");
            }

            IEveryDayExecuteEvent event = (IEveryDayExecuteEvent)plugin;
            event.everyDay();

            if (loger.isDebugEnabled())
              loger.debug("调度任务插件[" + plugin.getClass() + "]everyDay执行完成...");
          }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void everyMonthExcecute()
  {
    List<IPlugin> plugins = getPlugins();

    if (plugins != null)
      for (IPlugin plugin : plugins)
        if ((plugin instanceof IEveryMonthExecuteEvent))
        {
          if (loger.isDebugEnabled()) {
            loger.debug("调度任务插件[" + plugin.getClass() + "]everyMonth开始执行...");
          }

          IEveryMonthExecuteEvent event = (IEveryMonthExecuteEvent)plugin;
          event.everyMonth();

          if (loger.isDebugEnabled())
            loger.debug("调度任务插件[" + plugin.getClass() + "]everyMonth执行完成...");
        }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.plugin.job.JobExecutePluginsBundle
 * JD-Core Version:    0.6.1
 */