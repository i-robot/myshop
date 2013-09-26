package com.enation.app.base.core.service;

import com.enation.app.base.core.model.VersionState;

public abstract interface IUpdateManager
{
  public abstract VersionState checkNewVersion();

  public abstract void update();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.IUpdateManager
 * JD-Core Version:    0.6.1
 */