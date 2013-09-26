package com.enation.framework.jms;

import com.enation.eop.sdk.context.EopContext;
import java.util.HashMap;
import java.util.Map;

public class EmailModel
{
  private Map data = new HashMap();

  private String title = "";

  private String to = "";

  private String template = "";
  private EopContext eopContext;

  public EmailModel()
  {
    this.eopContext = EopContext.getContext();
  }

  public EmailModel(Map data, String title, String to, String template)
  {
    this.data = data;
    this.title = title;
    this.to = to;
    this.template = template;
  }

  public Map getData() {
    return this.data;
  }

  public void setData(Map data) {
    this.data = data;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTo() {
    return this.to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getTemplate() {
    return this.template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public EopContext getEopContext()
  {
    return this.eopContext;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.jms.EmailModel
 * JD-Core Version:    0.6.1
 */