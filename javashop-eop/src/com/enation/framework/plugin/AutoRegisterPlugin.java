package com.enation.framework.plugin;

import java.util.List;
import org.apache.log4j.Logger;

public abstract class AutoRegisterPlugin
  implements IPlugin
{
  protected final Logger logger = Logger.getLogger(getClass());
  protected List<IPluginBundle> bundleList;
  private boolean isEnable = false;

  public List<IPluginBundle> getBundleList() {
    return this.bundleList;
  }

  public void setBundleList(List<IPluginBundle> bundleList) {
    this.bundleList = bundleList;
  }

  public void disable() {
    this.isEnable = false;
  }

  public void enable() {
    this.isEnable = true;
  }

  public boolean getIsEnable() {
    return this.isEnable;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.plugin.AutoRegisterPlugin
 * JD-Core Version:    0.6.1
 */