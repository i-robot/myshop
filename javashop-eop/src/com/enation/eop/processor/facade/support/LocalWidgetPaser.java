package com.enation.eop.processor.facade.support;

import com.enation.eop.processor.core.EopException;
import com.enation.eop.processor.core.UrlNotFoundException;
import com.enation.eop.processor.widget.IWidgetPaser;
import com.enation.eop.sdk.widget.IWidget;
import com.enation.framework.component.context.WidgetContext;
import com.enation.framework.context.spring.SpringContextHolder;
import java.util.Map;

public class LocalWidgetPaser
  implements IWidgetPaser
{
  public String pase(Map<String, String> params)
  {
    if (params == null) throw new EopException("挂件参数不能为空");

    String widgetType = (String)params.get("type");
    if (widgetType == null) throw new EopException("挂件类型不能为空");

    if (!WidgetContext.getWidgetState(widgetType).booleanValue()) return "此挂件已停用";

    try
    {
      IWidget widget = (IWidget)SpringContextHolder.getBean(widgetType);
      String content;
      if (widget == null) { content = "widget[" + widgetType + "]not found";
      } else {
        content = widget.process(params);
        widget.update(params);
      }

      return content;
    }
    catch (UrlNotFoundException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
    }return "widget[" + widgetType + "]pase error ";
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.LocalWidgetPaser
 * JD-Core Version:    0.6.1
 */