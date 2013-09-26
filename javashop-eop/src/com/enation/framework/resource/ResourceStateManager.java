package com.enation.framework.resource;

import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import java.util.HashMap;
import java.util.Map;

public class ResourceStateManager
{
  private static int DISPLOY_STATE;
  private static Map<String, String> disployStateMap = new HashMap();

  public static boolean getHaveNewDisploy()
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      EopSite site = EopContext.getContext().getCurrentSite();
      return "1".equals(disployStateMap.get(site.getUserid() + "_" + site.getId()));
    }
    return DISPLOY_STATE == 1;
  }

  public static void setDisplayState(int state)
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      EopSite site = EopContext.getContext().getCurrentSite();
      disployStateMap.put(site.getUserid() + "_" + site.getId(), "" + state);
    } else {
      DISPLOY_STATE = state;
    }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.resource.ResourceStateManager
 * JD-Core Version:    0.6.1
 */