package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.eop.resource.IMenuManager;
import com.enation.eop.resource.model.Menu;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MenuInstaller
  implements IInstaller
{
  private IMenuManager menuManager;

  public void install(String productId, Node fragment)
  {
    NodeList menuList = fragment.getChildNodes();
    addMenu(menuList, Integer.valueOf(0));
  }

  private void addMenu(NodeList nodeList, Integer parentId)
  {
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == 1)
        addMenu((Element)node, parentId);
    }
  }

  private void addMenu(Element ele, Integer parentId)
  {
    try
    {
      Menu menu = new Menu();
      menu.setPid(parentId);

      String text = ele.getAttribute("text");
      String url = ele.getAttribute("url");
      String type = ele.getAttribute("type");
      String selected = ele.getAttribute("selected");
      String target = ele.getAttribute("target");
      String mode = ele.getAttribute("mode");
      if (type == null) {
        type = ((Element)ele.getParentNode()).getAttribute("type");
      }

      if (target != null) {
        menu.setTarget(target);
      }

      int menuType = 2;
      if ("sys".equals(type))
        menuType = 1;
      if ("app".equals(type))
        menuType = 2;
      if ("ext".equals(type)) {
        menuType = 3;
      }
      menu.setMenutype(Integer.valueOf(menuType));
      menu.setTitle(text);
      if ((selected != null) && (!selected.equals(""))) {
        menu.setSelected(Integer.valueOf(selected));
      }
      if (url != null)
        menu.setUrl(url);
      menu.setSorder(Integer.valueOf(50));
      if (mode != null) {
        menu.setAppid(mode);
      }
      Integer menuid = this.menuManager.add(menu);
      NodeList children = ele.getChildNodes();

      if (children != null)
        addMenu(children, menuid);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("install menu error");
    }
  }

  public IMenuManager getMenuManager() {
    return this.menuManager;
  }

  public void setMenuManager(IMenuManager menuManager) {
    this.menuManager = menuManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.MenuInstaller
 * JD-Core Version:    0.6.1
 */