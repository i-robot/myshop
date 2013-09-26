package com.enation.framework.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class XMLUtil
{
  public static Element getChildByTagName(Node node, String tagName)
  {
    if (node.getNodeType() != 1) {
      throw new RuntimeException(tagName + "节点格式不正确,非Element类型。");
    }
    Element el = (Element)node;

    NodeList nodeList = el.getElementsByTagName(tagName);
    int length = nodeList.getLength();

    if ((nodeList == null) || (length == 0)) {
      return null;
    }
    return (Element)nodeList.item(0);
  }

  public static Element getChildByTagName(Document doc, String tagName)
  {
    NodeList nodeList = doc.getElementsByTagName(tagName);
    int length = nodeList.getLength();

    if ((nodeList == null) || (length == 0)) {
      return null;
    }
    return (Element)nodeList.item(0);
  }

  public static Element getChildByAttrName(Node node, String attrName, String attrValue)
  {
    NodeList nodeList = node.getChildNodes();
    int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
      Node n = nodeList.item(i);
      if (n.getNodeType() == 1) {
        Element el = (Element)n;
        if (attrValue.equals(el.getAttribute(attrName))) {
          return el;
        }
      }
    }

    return null;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.XMLUtil
 * JD-Core Version:    0.6.1
 */