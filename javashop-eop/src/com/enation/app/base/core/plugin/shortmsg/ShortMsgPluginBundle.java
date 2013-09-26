package com.enation.app.base.core.plugin.shortmsg;

import com.enation.app.base.core.model.ShortMsg;
import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import com.enation.framework.plugin.IPlugin;
import java.util.ArrayList;
import java.util.List;

public class ShortMsgPluginBundle extends AutoRegisterPluginsBundle
{
  public String getName()
  {
    return "短消息插件桩";
  }

  public List<ShortMsg> getMessageList() {
    List msgList = new ArrayList();
    List<IPlugin> plugins = getPlugins();

    if (plugins != null) {
      for (IPlugin plugin : plugins) {
        if ((plugin instanceof IShortMessageEvent)) {
          IShortMessageEvent event = (IShortMessageEvent)plugin;
          List somemsgList = event.getMessage();
          msgList.addAll(somemsgList);
        }
      }
    }
    return msgList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.plugin.shortmsg.ShortMsgPluginBundle
 * JD-Core Version:    0.6.1
 */