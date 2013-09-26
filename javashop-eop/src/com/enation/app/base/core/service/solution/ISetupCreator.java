package com.enation.app.base.core.service.solution;

import org.dom4j.Document;

public abstract interface ISetupCreator
{
  public abstract void addTable(Document paramDocument, String paramString1, String paramString2);

  public abstract Document createSetup(String paramString);

  public abstract void save(Document paramDocument, String paramString);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.ISetupCreator
 * JD-Core Version:    0.6.1
 */