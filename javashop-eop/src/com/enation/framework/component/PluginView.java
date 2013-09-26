package com.enation.framework.component;

import java.util.ArrayList;
import java.util.List;

public class PluginView
{
  private String id;
  private String name;
  private List<String> bundleList;

  public PluginView()
  {
    this.bundleList = new ArrayList();
  }

  public void addBundle(String beanid) {
    this.bundleList.add(beanid);
  }

  public String getId()
  {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public List<String> getBundleList() {
    return this.bundleList;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.component.PluginView
 * JD-Core Version:    0.6.1
 */