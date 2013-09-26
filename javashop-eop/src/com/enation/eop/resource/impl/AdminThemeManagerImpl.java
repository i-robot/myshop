package com.enation.eop.resource.impl;

import com.enation.eop.resource.IAdminThemeManager;
import com.enation.eop.resource.model.AdminTheme;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.FileUtil;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AdminThemeManagerImpl extends BaseSupport<AdminTheme>
  implements IAdminThemeManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public Integer add(AdminTheme theme, boolean isCommon)
  {
    try
    {
      this.baseDaoSupport.insert("admintheme", theme);
      return Integer.valueOf(this.baseDaoSupport.getLastId("admintheme"));
    } catch (Exception e) {
      e.printStackTrace();
    }throw new RuntimeException("安装后台主题出错");
  }

  public void clean()
  {
    this.baseDaoSupport.execute("truncate table admintheme", new Object[0]);
  }

  private void copy(AdminTheme theme, boolean isCommon)
    throws Exception
  {
    EopSite site = EopContext.getContext().getCurrentSite();

    String basePath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + theme.getProductId();
    basePath = basePath + "/adminthemes";

    String contextPath = EopContext.getContext().getContextPath();

    String targetPath = EopSetting.IMG_SERVER_PATH + contextPath + "/adminthemes/" + theme.getPath();
    FileUtil.copyFolder(basePath + "/" + theme.getPath() + "/images", targetPath + "/images");
    FileUtil.copyFile(basePath + "/" + theme.getPath() + "/preview.png", targetPath + "/preview.png");
    FileUtil.copyFolder(basePath + "/" + theme.getPath() + "/css", targetPath + "/css");
    FileUtil.copyFolder(basePath + "/" + theme.getPath() + "/js", targetPath + "/js");

    FileUtil.copyFolder(basePath + "/" + theme.getPath(), EopSetting.EOP_PATH + contextPath + "/adminthemes/" + theme.getPath());
  }

  public AdminTheme get(Integer themeid)
  {
    List list = this.baseDaoSupport.queryForList("select * from admintheme where id=?", AdminTheme.class, new Object[] { themeid });
    if ((list == null) || (list.isEmpty())) return null;

    return (AdminTheme)list.get(0);
  }

  public List<AdminTheme> list()
  {
    return this.baseDaoSupport.queryForList("select * from admintheme ", AdminTheme.class, new Object[0]);
  }

  public IDaoSupport<AdminTheme> getDaoSupport() {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport<AdminTheme> daoSupport) {
    this.daoSupport = daoSupport;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.AdminThemeManagerImpl
 * JD-Core Version:    0.6.1
 */