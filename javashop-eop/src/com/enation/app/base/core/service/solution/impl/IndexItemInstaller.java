package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.eop.resource.IIndexItemManager;
import com.enation.eop.resource.model.IndexItem;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IndexItemInstaller
  implements IInstaller
{
  private IIndexItemManager indexItemManager;

  @Transactional(propagation=Propagation.REQUIRED)
  public void install(String productId, Node fragment)
  {
    NodeList nodeList = fragment.getChildNodes();
    int sort = 1;
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);

      if (node.getNodeType() == 1) {
        install((Element)node, sort);
        sort++;
      }
    }
  }

  public void install(Element ele, int sort)
  {
    Element titleEl = getChild(ele, "title");
    Element urlEl = getChild(ele, "url");

    IndexItem item = new IndexItem();
    item.setTitle(titleEl.getTextContent());
    item.setUrl(urlEl.getTextContent());
    item.setSort(sort);

    this.indexItemManager.add(item);
  }

  private Element getChild(Element ele, String name)
  {
    NodeList childList = ele.getElementsByTagName(name);
    Element child = (Element)childList.item(0);
    return child;
  }

  public IIndexItemManager getIndexItemManager() {
    return this.indexItemManager;
  }

  public void setIndexItemManager(IIndexItemManager indexItemManager) {
    this.indexItemManager = indexItemManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.IndexItemInstaller
 * JD-Core Version:    0.6.1
 */