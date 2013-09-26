package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.ISetupLoader;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.util.FileUtil;
import java.io.File;
import java.io.PrintStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class SetupLoaderImpl
  implements ISetupLoader
{
  public Document load(String productId)
  {
    String xmlFile = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/setup.xml";
    Document document = null;
    SAXReader saxReader = new SAXReader();
    try {
      if (FileUtil.exist(xmlFile))
        document = saxReader.read(new File(xmlFile));
    }
    catch (DocumentException e)
    {
      System.out.println(e.getMessage());
    }
    return document;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SetupLoaderImpl
 * JD-Core Version:    0.6.1
 */