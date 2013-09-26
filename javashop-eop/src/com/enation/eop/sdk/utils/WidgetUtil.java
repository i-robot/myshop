package com.enation.eop.sdk.utils;

import com.enation.eop.processor.Widget;
import com.enation.eop.resource.model.EopApp;

public class WidgetUtil
{
  public static String toHtml(Widget widget, String content)
  {
    StringBuffer htmlBuffer = new StringBuffer();

    htmlBuffer.append("<div ");

    htmlBuffer.append("class=\"widget\"");
    htmlBuffer.append(" ");

    htmlBuffer.append("eop_type=\"widget\"");
    htmlBuffer.append(" ");

    htmlBuffer.append("appId=\"");
    htmlBuffer.append(widget.getApp().getId());
    htmlBuffer.append("\"");
    htmlBuffer.append(" ");

    htmlBuffer.append("widgetType=\"");
    htmlBuffer.append(widget.getWidgetType());
    htmlBuffer.append("\"");
    htmlBuffer.append(" ");

    htmlBuffer.append(">");

    htmlBuffer.append(content);

    htmlBuffer.append("</div>");

    return htmlBuffer.toString();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.utils.WidgetUtil
 * JD-Core Version:    0.6.1
 */