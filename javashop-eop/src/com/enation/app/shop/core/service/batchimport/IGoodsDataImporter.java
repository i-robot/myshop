package com.enation.app.shop.core.service.batchimport;

import com.enation.app.shop.core.model.ImportDataSource;
import java.util.Map;
import org.w3c.dom.Element;

public abstract interface IGoodsDataImporter
{
  public abstract void imported(Object paramObject, Element paramElement, ImportDataSource paramImportDataSource, Map paramMap);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.batchimport.IGoodsDataImporter
 * JD-Core Version:    0.6.1
 */