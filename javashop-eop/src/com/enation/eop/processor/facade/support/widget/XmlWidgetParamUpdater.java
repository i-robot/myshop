package com.enation.eop.processor.facade.support.widget;

import com.enation.eop.processor.widget.IWidgetParamUpdater;
import com.enation.eop.processor.widget.WidgetXmlUtil;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.Theme;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import java.util.List;
import java.util.Map;

public class XmlWidgetParamUpdater
  implements IWidgetParamUpdater
{
  private IThemeManager themeManager;

  public void update(String pageId, List<Map<String, String>> params)
  {
    EopSite site = EopContext.getContext().getCurrentSite();
    Theme theme = this.themeManager.getTheme(site.getThemeid());
    String contextPath = EopContext.getContext().getContextPath();
    String path = EopSetting.EOP_PATH + contextPath + EopSetting.THEMES_STORAGE_PATH + "/" + theme.getPath() + "/widgets.xml";

    WidgetXmlUtil.save(path, pageId, params);
  }

  public void setThemeManager(IThemeManager themeManager) {
    this.themeManager = themeManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.XmlWidgetParamUpdater
 * JD-Core Version:    0.6.1
 */