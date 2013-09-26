package com.enation.app.shop.core.model.support;

public class ParamDTO
{
  private String[] paramnums;
  private String[] groupnames;
  private String[] paramnames;
  private String[] paramvalues;

  public String[] getGroupnames()
  {
    return this.groupnames;
  }
  public void setGroupnames(String[] groupnames) {
    this.groupnames = groupnames;
  }
  public String[] getParamnames() {
    return this.paramnames;
  }
  public void setParamnames(String[] paramnames) {
    this.paramnames = paramnames;
  }
  public String[] getParamnums() {
    return this.paramnums;
  }
  public void setParamnums(String[] paramnums) {
    this.paramnums = paramnums;
  }
  public String[] getParamvalues() {
    return this.paramvalues;
  }
  public void setParamvalues(String[] paramvalues) {
    this.paramvalues = paramvalues;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.ParamDTO
 * JD-Core Version:    0.6.1
 */