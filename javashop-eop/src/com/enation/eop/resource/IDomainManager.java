package com.enation.eop.resource;

import com.enation.eop.resource.model.EopSiteDomain;
import java.util.List;

public abstract interface IDomainManager
{
  public abstract EopSiteDomain get(Integer paramInteger);

  public abstract void edit(EopSiteDomain paramEopSiteDomain);

  public abstract List<EopSiteDomain> listUserDomain();

  public abstract List<EopSiteDomain> listSiteDomain();

  public abstract List<EopSiteDomain> listSiteDomain(Integer paramInteger);

  public abstract int getDomainCount(Integer paramInteger);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IDomainManager
 * JD-Core Version:    0.6.1
 */