package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Attribute;
import com.enation.app.shop.core.model.GoodsParam;
import com.enation.app.shop.core.model.support.ParamGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;

public class GoodsTypeUtil
{
  public static ParamGroup[] converFormString(String params)
  {
    if ((params == null) || (params.equals("")) || (params.equals("[]")))
      return null;
    Map classMap = new HashMap();

    classMap.put("paramList", GoodsParam.class);
    JSONArray jsonArray = JSONArray.fromObject(params);

    Object obj = JSONArray.toArray(jsonArray, ParamGroup.class, classMap);

    if (obj == null) {
      return null;
    }
    return (ParamGroup[])obj;
  }

  public static List<Attribute> converAttrFormString(String props)
  {
    if ((props == null) || (props.equals(""))) {
      return new ArrayList();
    }
    JSONArray jsonArray = JSONArray.fromObject(props);
    List list = (List)JSONArray.toCollection(jsonArray, Attribute.class);

    return list;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.GoodsTypeUtil
 * JD-Core Version:    0.6.1
 */