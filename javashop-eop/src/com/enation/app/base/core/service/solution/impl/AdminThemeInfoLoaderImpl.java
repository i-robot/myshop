package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.IAdminThemeInfoFileLoader;
import com.enation.eop.sdk.context.EopSetting;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class AdminThemeInfoLoaderImpl
  implements IAdminThemeInfoFileLoader
{
  public Document load(String productId, String path, Boolean isCommonTheme)
  {
    String xmlFile = null;
    if (isCommonTheme.booleanValue())
      xmlFile = EopSetting.EOP_PATH + "/adminthemes/" + path + "/themeini.xml";
    else {
      xmlFile = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/adminthemes/" + path + "/themeini.xml";
    }
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(xmlFile);
    } catch (Exception e) {
    }
    throw new RuntimeException("load [" + productId + " , " + path + "] themeini error!FileName:" + xmlFile);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.AdminThemeInfoLoaderImpl
 * JD-Core Version:    0.6.1
 */