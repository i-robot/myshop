package com.enation.app.base.core.service;

import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface ISettingService
{
  public abstract void add(String paramString1, String paramString2, String paramString3);

  public abstract void save(String paramString1, String paramString2, String paramString3);

  public abstract void delete(String paramString);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void save(Map<String, Map<String, String>> paramMap)
    throws SettingRuntimeException;

  public abstract Map<String, Map<String, String>> getSetting();

  public abstract String getSetting(String paramString1, String paramString2);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.ISettingService
 * JD-Core Version:    0.6.1
 */