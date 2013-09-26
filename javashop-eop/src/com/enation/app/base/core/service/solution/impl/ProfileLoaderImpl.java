package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.IProfileLoader;
import com.enation.eop.sdk.context.EopSetting;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class ProfileLoaderImpl
  implements IProfileLoader
{
  protected final Logger logger = Logger.getLogger(getClass());

  public Document load(String productId) { String xmlFile = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/profile.xml";
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(xmlFile);
    }
    catch (Exception e)
    {
      this.logger.error(e);
      e.printStackTrace();
    }throw new RuntimeException("load [" + productId + "] profile error");
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.ProfileLoaderImpl
 * JD-Core Version:    0.6.1
 */