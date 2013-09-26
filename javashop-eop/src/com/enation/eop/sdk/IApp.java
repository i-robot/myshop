package com.enation.eop.sdk;

import com.enation.eop.resource.model.EopSite;
import org.dom4j.Document;

public abstract interface IApp
{
  public abstract void install();

  public abstract void saasInstall();

  public abstract String dumpSql();

  public abstract String dumpSql(Document paramDocument);

  public abstract String dumpXml(Document paramDocument);

  public abstract void sessionDestroyed(String paramString, EopSite paramEopSite);

  public abstract String getName();

  public abstract String getId();

  public abstract String getNameSpace();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.IApp
 * JD-Core Version:    0.6.1
 */