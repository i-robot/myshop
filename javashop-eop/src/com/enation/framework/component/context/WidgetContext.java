package com.enation.framework.component.context;

import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import java.util.HashMap;
import java.util.Map;

public class WidgetContext
{
  private static Map<String, Boolean> widgetState;
  private static Map<String, Map<String, Boolean>> saasWidgetState;

  public static void putWidgetState(String widgetId, Boolean state)
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      String key = getKey();
      Map stateMap = (Map)saasWidgetState.get(key);
      if (stateMap == null) {
        stateMap = new HashMap();
      }

      stateMap.put(widgetId, state);
      saasWidgetState.put(key, stateMap);
    }
    else {
      widgetState.put(widgetId, state);
    }
  }

  public static Boolean getWidgetState(String widgetId)
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      String key = getKey();
      Map stateMap = (Map)saasWidgetState.get(key);
      if (stateMap == null) {
        stateMap = new HashMap();
        saasWidgetState.put(key, stateMap);
      }

      Boolean state = (Boolean)stateMap.get(widgetId);
      if (state == null) return Boolean.valueOf(false);
      return state;
    }

    Boolean state = (Boolean)widgetState.get(widgetId);
    if (state == null) return Boolean.valueOf(true);
    return state;
  }

  private static String getKey()
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    int userid = site.getUserid().intValue();
    int siteid = site.getId().intValue();
    String key = userid + "_" + siteid;

    return key;
  }

  static
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      saasWidgetState = new HashMap();
    }
    else
      widgetState = new HashMap();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.component.context.WidgetContext
 * JD-Core Version:    0.6.1
 */