package com.enation.eop.sdk.webapp.taglib.html;

import com.enation.eop.sdk.webapp.taglib.HtmlTaglib;

public class GridHeaderTaglib extends HtmlTaglib
{
  protected String postStart()
  {
    return "<thead><tr>";
  }

  protected String postEnd()
  {
    return "</tr></thead>";
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.GridHeaderTaglib
 * JD-Core Version:    0.6.1
 */