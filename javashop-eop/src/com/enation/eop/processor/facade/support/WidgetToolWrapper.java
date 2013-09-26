package com.enation.eop.processor.facade.support;

import com.enation.eop.processor.AbstractFacadePageWrapper;
import com.enation.eop.processor.FacadePage;
import com.enation.eop.processor.core.Request;
import com.enation.eop.processor.core.Response;

public class WidgetToolWrapper extends AbstractFacadePageWrapper
{
  private static final String toolsElement = "<div id=\"widget_setting\"></div><form id=\"pageForm\" method=\"POST\"><input type=\"hidden\" id=\"bodyHtml\" name=\"bodyHtml\"> </form></body>";

  public WidgetToolWrapper(FacadePage page, Request request)
  {
    super(page, request);
  }

  protected Response wrap(Response response) {
    String content = response.getContent();
    content = content.replaceAll("</body>", "<div id=\"widget_setting\"></div><form id=\"pageForm\" method=\"POST\"><input type=\"hidden\" id=\"bodyHtml\" name=\"bodyHtml\"> </form></body>");
    content = content.replaceAll("</BODY>", "<div id=\"widget_setting\"></div><form id=\"pageForm\" method=\"POST\"><input type=\"hidden\" id=\"bodyHtml\" name=\"bodyHtml\"> </form></body>");

    response.setContent(content);
    return response;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.facade.support.WidgetToolWrapper
 * JD-Core Version:    0.6.1
 */