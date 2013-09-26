package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.IInstaller;
import com.enation.eop.resource.IBorderManager;
import com.enation.eop.resource.model.Border;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.util.FileUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BorderInstaller
  implements IInstaller
{
  private IBorderManager borderManager;

  public void install(String productId, Node fragment)
  {
    try
    {
      FileUtil.copyFolder(EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/borders/", EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/borders/");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("安装borders出错...");
    }

    if (fragment.getNodeType() == 1) {
      Element themeNode = (Element)fragment;
      NodeList nodeList = themeNode.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == 1) {
          Element el = (Element)node;
          String id = el.getAttribute("id");
          String name = el.getAttribute("name");
          Border border = new Border();
          border.setBorderid(id);
          border.setBordername(name);
          border.setThemepath(themeNode.getAttribute("id"));
          this.borderManager.add(border);
        }
      }
    }
  }

  public void setBorderManager(IBorderManager borderManager) {
    this.borderManager = borderManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.BorderInstaller
 * JD-Core Version:    0.6.1
 */