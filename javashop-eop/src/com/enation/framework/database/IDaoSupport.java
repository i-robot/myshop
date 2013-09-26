package com.enation.framework.database;

import com.enation.framework.database.impl.IRowMapperColumnFilter;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public abstract interface IDaoSupport<T>
{
  public abstract void execute(String paramString, Object[] paramArrayOfObject);

  public abstract int queryForInt(String paramString, Object[] paramArrayOfObject);

  public abstract long queryForLong(String paramString, Object[] paramArrayOfObject);

  public abstract String queryForString(String paramString);

  public abstract T queryForObject(String paramString, Class paramClass, Object[] paramArrayOfObject);

  public abstract T queryForObject(String paramString, ParameterizedRowMapper paramParameterizedRowMapper, Object[] paramArrayOfObject);

  public abstract Map queryForMap(String paramString, Object[] paramArrayOfObject);

  public abstract List<Map> queryForList(String paramString, Object[] paramArrayOfObject);

  public abstract List<T> queryForList(String paramString, RowMapper paramRowMapper, Object[] paramArrayOfObject);

  public abstract List<T> queryForList(String paramString, IRowMapperColumnFilter paramIRowMapperColumnFilter, Object[] paramArrayOfObject);

  public abstract List<T> queryForList(String paramString, Class paramClass, Object[] paramArrayOfObject);

  public abstract List<Map> queryForListPage(String paramString, int paramInt1, int paramInt2, Object[] paramArrayOfObject);

  public abstract List<T> queryForList(String paramString, int paramInt1, int paramInt2, RowMapper paramRowMapper);

  public abstract List<T> queryForList(String paramString, int paramInt1, int paramInt2, IRowMapperColumnFilter paramIRowMapperColumnFilter);

  public abstract Page queryForPage(String paramString, int paramInt1, int paramInt2, Object[] paramArrayOfObject);

  public abstract Page queryForPage(String paramString, int paramInt1, int paramInt2, RowMapper paramRowMapper, Object[] paramArrayOfObject);

  public abstract Page queryForPage(String paramString, int paramInt1, int paramInt2, IRowMapperColumnFilter paramIRowMapperColumnFilter, Object[] paramArrayOfObject);

  public abstract Page queryForPage(String paramString, int paramInt1, int paramInt2, Class<T> paramClass, Object[] paramArrayOfObject);

  public abstract void update(String paramString, Map paramMap1, Map paramMap2);

  public abstract void update(String paramString1, Map paramMap, String paramString2);

  public abstract void update(String paramString, T paramT, Map paramMap);

  public abstract void update(String paramString1, T paramT, String paramString2);

  public abstract void insert(String paramString, Map paramMap);

  public abstract void insert(String paramString, Object paramObject);

  public abstract int getLastId(String paramString);

  public abstract String buildPageSql(String paramString, int paramInt1, int paramInt2);
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.IDaoSupport
 * JD-Core Version:    0.6.1
 */