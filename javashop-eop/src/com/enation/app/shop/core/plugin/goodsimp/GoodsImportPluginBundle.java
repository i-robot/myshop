package com.enation.app.shop.core.plugin.goodsimp;

import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import com.enation.framework.plugin.IPlugin;
import java.util.List;
import org.apache.commons.logging.Log;
import org.w3c.dom.Document;

public class GoodsImportPluginBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "商品批量导入插件桩";
  }

  public void onBeforeImport(Document configDoc)
  {
    try {
      List<IPlugin> plugins = getPlugins();

      if (plugins != null) {
        for (IPlugin plugin : plugins)
          if ((plugin instanceof IBeforeGoodsImportEvent)) {
            if (loger.isDebugEnabled()) {
              loger.debug("调用插件 : " + plugin.getClass() + " onBeforeImport 开始...");
            }
            IBeforeGoodsImportEvent event = (IBeforeGoodsImportEvent)plugin;
            event.onBeforeImport(configDoc);
            if (loger.isDebugEnabled())
              loger.debug("调用插件 : " + plugin.getClass() + " onBeforeImport 结束.");
          }
      }
    }
    catch (RuntimeException e)
    {
      if (loger.isErrorEnabled())
        loger.error("调用商品导入插件[导入前]事件错误", e);
      throw e;
    }
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.goodsimp.GoodsImportPluginBundle
 * JD-Core Version:    0.6.1
 */