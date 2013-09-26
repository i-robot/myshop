package com.enation.app.base.core.service.impl.cache;

import com.enation.app.base.core.model.Regions;
import com.enation.app.base.core.service.IRegionsManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.cache.CacheFactory;
import com.enation.framework.cache.ICache;
import com.enation.framework.database.IDaoSupport;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class RegionsCacheProxy extends BaseSupport<Regions>
  implements IRegionsManager
{
  protected final Logger logger = Logger.getLogger(getClass());
  protected ICache<List<Regions>> cache;
  private IRegionsManager regionsManager;
  private static final String REGION_LIST_CACHE_KEY = "regionCache";

  public RegionsCacheProxy(IRegionsManager regionsManager)
  {
    this.cache = CacheFactory.getCache("regionCache");
    this.regionsManager = regionsManager;
  }

  public List listCity(int province_id)
  {
    return this.regionsManager.listCity(province_id);
  }

  public List listProvince()
  {
    return this.regionsManager.listProvince();
  }

  public List listRegion(int city_id)
  {
    return this.regionsManager.listRegion(city_id);
  }

  public List listChildren(Integer regionid)
  {
    return this.regionsManager.listChildren(regionid);
  }

  private List<Regions> listAll() {
    String sql = "select * from regions order by region_id";
    return this.baseDaoSupport.queryForList(sql, Regions.class, new Object[0]);
  }

  public List listChildren(String regionid)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.info("find parents is " + regionid);
    }
    if ((regionid == null) || ("".equals(regionid))) return new ArrayList();

    List<Regions> regionsList = (List)this.cache.get("regionCache");
    if (regionsList == null) {
      if (this.logger.isDebugEnabled()) {
        this.logger.info("load regions list from db");
      }
      regionsList = listAll();
      this.cache.put("regionCache", regionsList);
    }
    else if (this.logger.isDebugEnabled()) {
      this.logger.info("load regions list from cache");
    }

    List list = new ArrayList();
    String[] regionsArray = StringUtils.split(regionid, ",");
    for (String id : regionsArray)
    {
      for (Regions region : regionsList) {
        if (region.getRegion_path().indexOf("," + id + ",") >= 0) {
          list.add(region.getRegion_id());
        }
      }
    }

    return list;
  }

  public String getChildrenJson(Integer regionid)
  {
    return this.regionsManager.getChildrenJson(regionid);
  }

  public void add(Regions regions)
  {
    this.regionsManager.add(regions);
  }

  public void delete(int regionId)
  {
    this.regionsManager.delete(regionId);
  }

  public Regions get(int regionId)
  {
    return this.regionsManager.get(regionId);
  }

  public void update(Regions regions)
  {
    this.regionsManager.update(regions);
  }

  public void reset()
  {
    this.regionsManager.reset();
  }

  public Regions[] get(String area)
  {
    String[] areas = StringUtils.split(area, "-");
    List<Regions> list = listAll();
    Regions[] result = new Regions[3];
    for (Regions regions : list) {
      if (regions.getLocal_name().equals(areas[0])) result[0] = regions;
      if (regions.getLocal_name().equals(areas[1])) result[1] = regions;
      if (regions.getLocal_name().equals(areas[2])) result[2] = regions;
    }
    return result;
  }

  public Regions getByName(String name)
  {
    return this.regionsManager.getByName(name);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.cache.RegionsCacheProxy
 * JD-Core Version:    0.6.1
 */