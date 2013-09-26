package com.enation.framework.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesUtil
{
  private HashMap propertiesMap;

  private PropertiesUtil()
  {
  }

  public PropertiesUtil(String filePath)
  {
    InputStream in = FileUtil.getResourceAsStream(filePath);
    load(in);
  }

  public void load(InputStream in) {
    try {
      Properties props = new Properties();
      this.propertiesMap = new HashMap();
      props.load(in);
      Enumeration en = props.propertyNames();
      while (en.hasMoreElements()) {
        String key = (String)en.nextElement();
        String Property = props.getProperty(key);
        this.propertiesMap.put(key, Property);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getProperties(String key)
  {
    Object value = this.propertiesMap.get(key);
    if (value != null) {
      return value.toString();
    }
    return null;
  }

  public HashMap getPropertiesMap()
  {
    return this.propertiesMap;
  }

  public static void main(String[] args)
  {
    PropertiesUtil pu = new PropertiesUtil("E:\\ProductSpace\\EOA\\resources\\config\\info.properties");
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.PropertiesUtil
 * JD-Core Version:    0.6.1
 */