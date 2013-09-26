package com.enation.framework.util;

import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonMessageUtil
{
  public static String getObjectJson(Object object)
  {
    if (object == null) {
      return getErrorJson("object is null");
    }
    String objStr = JSONObject.fromObject(object).toString();

    return "{\"result\":1,\"data\":" + objStr + "}";
  }

  public static String getNumberJson(String name, Object value)
  {
    return "{\"result\":1,\"" + name + "\":" + value + "}";
  }

  public static String getListJson(List list) {
    if (list == null) {
      return getErrorJson("list is null");
    }
    String listStr = JSONArray.fromObject(list).toString();
    return "{\"result\":1,\"data\":" + listStr + "}";
  }

  public static String getErrorJson(String message)
  {
    return "{\"result\":0,\"message\":\"" + message + "\"}";
  }

  public static String getSuccessJson(String message)
  {
    return "{\"result\":1,\"message\":\"" + message + "\"}";
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.JsonMessageUtil
 * JD-Core Version:    0.6.1
 */