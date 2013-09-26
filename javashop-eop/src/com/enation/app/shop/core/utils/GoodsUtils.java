package com.enation.app.shop.core.utils;

import com.enation.app.shop.core.model.support.Adjunct;
import com.enation.app.shop.core.model.support.AdjunctGroup;
import com.enation.app.shop.core.model.support.SpecJson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public abstract class GoodsUtils
{
  public static List getSpecList(String specString)
  {
    if ((specString == null) || (specString.equals("[]")) || (specString.equals(""))) {
      return new ArrayList();
    }
    JSONArray j1 = JSONArray.fromObject(specString);
    List list = (List)JSONArray.toCollection(j1, SpecJson.class);
    return list;
  }

  public static AdjunctGroup converAdjFormString(String adjString)
  {
    if (adjString == null) {
      return null;
    }
    Map classMap = new HashMap();

    classMap.put("adjList", Adjunct.class);
    JSONObject j1 = JSONObject.fromObject(adjString);
    AdjunctGroup adjunct = (AdjunctGroup)JSONObject.toBean(j1, AdjunctGroup.class, classMap);

    return adjunct;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.utils.GoodsUtils
 * JD-Core Version:    0.6.1
 */