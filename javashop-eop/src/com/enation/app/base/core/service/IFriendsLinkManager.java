package com.enation.app.base.core.service;

import com.enation.app.base.core.model.FriendsLink;
import java.util.List;

public abstract interface IFriendsLinkManager
{
  public abstract FriendsLink get(int paramInt);

  public abstract List listLink();

  public abstract void add(FriendsLink paramFriendsLink);

  public abstract void delete(String paramString);

  public abstract void update(FriendsLink paramFriendsLink);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.IFriendsLinkManager
 * JD-Core Version:    0.6.1
 */