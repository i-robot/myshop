package com.enation.framework.database;

import java.util.HashMap;
import java.util.Map;

public class DynamicField
{
  private Map<String, Object> fields;

  public DynamicField()
  {
    this.fields = new HashMap();
  }

  public void addField(String name, Object value) {
    this.fields.put(name, value);
  }

  @NotDbField
  public Map<String, Object> getFields() {
    return this.fields;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.DynamicField
 * JD-Core Version:    0.6.1
 */