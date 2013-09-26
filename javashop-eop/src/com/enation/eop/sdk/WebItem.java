package com.enation.eop.sdk;

import net.sf.json.JSONObject;

public class WebItem
{
  private String style;
  private String target;
  private String text;
  private String url;
  private String className;

  public WebItem()
  {
  }

  public WebItem(String text)
  {
    this.text = text;
  }

  public String getStyle()
  {
    return this.style;
  }
  public void setStyle(String style) {
    this.style = style;
  }
  public String getTarget() {
    return this.target;
  }
  public void setTarget(String target) {
    this.target = target;
  }
  public String getText() {
    return this.text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }

  public String getClassName() {
    return this.className;
  }
  public void setClassName(String className) {
    this.className = className;
  }

  public String toJson() {
    JSONObject jsonObject = JSONObject.fromObject(this);
    return jsonObject.toString();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.WebItem
 * JD-Core Version:    0.6.1
 */