package com.enation.eop.resource;

import com.enation.eop.resource.model.Dns;
import com.enation.eop.resource.model.EopApp;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.EopSiteDomain;
import com.enation.eop.resource.model.EopUser;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface ISiteManager
{
  public abstract Integer add(EopSite paramEopSite, String paramString);

  public abstract Integer add(EopUser paramEopUser, EopSite paramEopSite, String paramString);

  public abstract Page list(int paramInt1, int paramInt2, String paramString1, String paramString2);

  public abstract List list(Integer paramInteger);

  public abstract Page list(String paramString, int paramInt1, int paramInt2);

  public abstract List<Map> list();

  public abstract List<Dns> getDnsList();

  public abstract EopSite get(Integer paramInteger);

  public abstract EopSite get(String paramString);

  public abstract Boolean checkInDomain(String paramString);

  public abstract void edit(EopSite paramEopSite);

  public abstract void delete(Integer paramInteger);

  public abstract int addDomain(EopSiteDomain paramEopSiteDomain);

  public abstract void deleteDomain(Integer paramInteger);

  public abstract void deleteDomain(String paramString);

  public abstract List listDomain(Integer paramInteger1, Integer paramInteger2);

  public abstract void editDomain(String paramString1, String paramString2);

  public abstract void setSiteProduct(Integer paramInteger1, Integer paramInteger2, String paramString);

  public abstract void changeAdminTheme(Integer paramInteger);

  public abstract void changeTheme(Integer paramInteger);

  public abstract void updatePoint(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract int getPoint(Integer paramInteger, int paramInt);

  public abstract void initData();

  public abstract List<EopApp> getSiteApps();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.ISiteManager
 * JD-Core Version:    0.6.1
 */