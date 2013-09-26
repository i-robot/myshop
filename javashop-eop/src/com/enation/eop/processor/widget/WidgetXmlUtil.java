package com.enation.eop.processor.widget;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WidgetXmlUtil
{
  public static Map<String, Map<String, Map<String, String>>> parse(String path)
  {
    try
    {
      Document document = paseParamDoc(path);
      return parsedoc(document);
    }
    catch (Exception e) {
      e.printStackTrace();
    }throw new RuntimeException("load [" + path + "] widget file error");
  }

  public static List<Map<String, String>> jsonToMapList(String paramJson)
  {
    JSONArray tempArray = JSONArray.fromObject(paramJson);
    List paramList = new ArrayList();
    Object[] paramArray = tempArray.toArray();
    for (Object param : paramArray) {
      JSONObject paramObj = JSONObject.fromObject(param);
      paramList.add((Map)JSONObject.toBean(paramObj, Map.class));
    }

    return paramList;
  }

  public static String mapToJson(Map<String, Map<String, String>> params)
  {
    if (params == null) {
      return "[]";
    }
    Set<String> widgetIdSet = params.keySet();
    List mapList = new ArrayList();
    for (String widgetId : widgetIdSet) {
      Map widgetParams = (Map)params.get(widgetId);
      widgetParams.put("id", widgetId);
      mapList.add(widgetParams);
    }
    JSONArray array = JSONArray.fromObject(mapList);

    return array.toString();
  }

  public static void save(String path, String pageId, List<Map<String, String>> params)
  {
    try
    {
      Document document = paseParamDoc(path);

      Node newPageNode = createPageNode(document, pageId, params);

      Node widgets = document.getFirstChild();
      NodeList nodeList = widgets.getChildNodes();
      int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
        Node page = nodeList.item(i);
        if (page.getNodeType() == 1) {
          Element pageEl = (Element)page;
          String id = pageEl.getAttribute("id");
          if (id.equals(pageId)) {
            widgets.replaceChild(newPageNode, page);
          }
        }
      }

      TransformerFactory tfactory = TransformerFactory.newInstance();
      Transformer t = tfactory.newTransformer();
      t.setOutputProperty("encoding", "UTF-8");
      t.setOutputProperty("indent", "yes");
      t.setOutputProperty("method", "xml");

      FileOutputStream stream = new FileOutputStream(new File(path));
      DOMSource source = new DOMSource(document);
      t.transform(source, new StreamResult(stream));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("save [" + path + "] widget file error");
    }
  }

  private static Node createPageNode(Document doc, String pageId, List<Map<String, String>> params)
  {
    Element pageEl = doc.createElement("page");
    pageEl.setAttribute("id", pageId);

    for (Map paramMap : params) {
      Element widgetEl = doc.createElement("widget");
      widgetEl.setAttribute("id", (String)paramMap.get("id"));
      Set<String> paramSet = paramMap.keySet();
      for (String name : paramSet)
      {
        if (!"id".equals(name)) {
          Element paramEl = doc.createElement(name);
          paramEl.setTextContent((String)paramMap.get(name));
          widgetEl.appendChild(paramEl);
        }
      }

      pageEl.appendChild(widgetEl);
    }

    return pageEl;
  }

  private static Map<String, Map<String, Map<String, String>>> parsedoc(Document doc)
  {
    Map params = new LinkedHashMap();
    Node widgets = doc.getFirstChild();
    if (widgets == null) throw new RuntimeException("widget xml error[page node is null]");
    NodeList nodeList = widgets.getChildNodes();
    int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
      Node page = nodeList.item(i);
      if (page.getNodeType() == 1) {
        Map widgetParams = parse(page);
        params.put(((Element)page).getAttribute("id"), widgetParams);
      }
    }

    return params;
  }

  private static Map<String, Map<String, String>> parse(Node page)
  {
    Map params = new LinkedHashMap();

    if (page == null) throw new RuntimeException("widget xml error[page node is null]");

    NodeList nodeList = page.getChildNodes();
    int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == 1) {
        Element widgetEl = (Element)node;
        String main = widgetEl.getAttribute("main");

        Map param = parae(widgetEl);
        if ("yes".equals(main))
          params.put("main", param);
        else {
          params.put(widgetEl.getAttribute("id"), param);
        }
      }

    }

    return params;
  }

  private static Map<String, String> parae(Element element)
  {
    NodeList nodeList = element.getChildNodes();
    Map param = new LinkedHashMap();
    param.put("widgetid", element.getAttribute("id"));

    int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
      Node node = nodeList.item(i);

      if (node.getNodeType() == 1) {
        Element attr = (Element)node;
        String name = attr.getNodeName();
        String value = attr.getTextContent();
        if ("action".equals(name)) {
          String actionname = attr.getAttribute("name");
          param.put("action_" + actionname, value);
        } else {
          param.put(name, value);
        }
      }

    }

    return param;
  }

  private static Document paseParamDoc(String path)
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(path);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }throw new RuntimeException("load [" + path + "] widget file error");
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.widget.WidgetXmlUtil
 * JD-Core Version:    0.6.1
 */