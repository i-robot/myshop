package com.enation.eop.sdk.webapp.taglib;

import javax.servlet.jsp.JspException;

public abstract class HtmlTaglib extends BaseTaglibSupport
{
  protected String startHtml = "";
  protected String endHtml = "";

  protected void startAppend(String html) {
    this.startHtml += html;
  }
  protected void endAppend(String html) {
    this.endHtml += html;
  }

  public int doStartTag() throws JspException {
    this.startHtml = "";
    print(postStart());
    return 1;
  }

  public int doAfterBody() {
    String content = postEnd();
    print(content);
    return 0;
  }

  protected abstract String postStart();

  protected abstract String postEnd();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.HtmlTaglib
 * JD-Core Version:    0.6.1
 */