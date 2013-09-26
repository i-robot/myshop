package com.enation.eop.processor;

public class PageWrapper
  implements IPagePaser
{
  protected IPagePaser pagePaser;

  public PageWrapper(IPagePaser paser)
  {
    this.pagePaser = paser;
  }

  public String pase(String url)
  {
    return this.pagePaser.pase(url);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.PageWrapper
 * JD-Core Version:    0.6.1
 */