package com.enation.eop.resource.impl;

import com.enation.app.base.core.model.MultiSite;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.Theme;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.FileUtil;
import java.util.List;

public class ThemeManagerImpl extends BaseSupport<Theme>
  implements IThemeManager
{
  public void clean()
  {
    this.baseDaoSupport.execute("truncate table theme", new Object[0]);
  }

  public Theme getTheme(Integer themeid) {
    return (Theme)this.baseDaoSupport.queryForObject("select * from theme where id=?", Theme.class, new Object[] { themeid });
  }

  public List<Theme> list()
  {
    if (EopContext.getContext().getCurrentSite().getMulti_site().intValue() == 0) {
      return this.baseDaoSupport.queryForList("select * from theme where siteid = 0", Theme.class, new Object[0]);
    }
    return this.baseDaoSupport.queryForList("select * from theme where siteid = ?", Theme.class, new Object[] { EopContext.getContext().getCurrentChildSite().getSiteid() });
  }

  public List<Theme> list(int siteid)
  {
    return this.baseDaoSupport.queryForList("select * from theme where siteid = 0", Theme.class, new Object[0]);
  }

  public void addBlank(Theme theme)
  {
    try
    {
      String basePath = EopSetting.APP_DATA_STORAGE_PATH;
      basePath = basePath + "/themes";

      theme.setSiteid(Integer.valueOf(0));
      String contextPath = EopContext.getContext().getContextPath();
      String targetPath = EopSetting.IMG_SERVER_PATH + contextPath + "/themes/" + theme.getPath();
      FileUtil.copyFolder(basePath + "/blank/images", targetPath + "/images");
      FileUtil.copyFile(basePath + "/blank/preview.png", targetPath + "/preview.png");
      FileUtil.copyFolder(basePath + "/blank/css", targetPath + "/css");
      FileUtil.copyFolder(basePath + "/blank/js", targetPath + "/js");
      FileUtil.copyFolder(basePath + "/blank", EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath());

      this.baseDaoSupport.insert("theme", theme);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("创建主题出错");
    }
  }

  public Integer add(Theme theme, boolean isCommon) {
    try {
      copy(theme, isCommon);

      this.baseDaoSupport.insert("theme", theme);
      return Integer.valueOf(this.baseDaoSupport.getLastId("theme"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }throw new RuntimeException("安装主题出错");
  }

  private void copy(Theme theme, boolean isCommon)
    throws Exception
  {
    String basePath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + theme.getProductId();
    basePath = basePath + "/themes";

    String contextPath = EopContext.getContext().getContextPath();

    FileUtil.copyFolder(basePath + "/" + theme.getPath(), EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath());
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.ThemeManagerImpl
 * JD-Core Version:    0.6.1
 */