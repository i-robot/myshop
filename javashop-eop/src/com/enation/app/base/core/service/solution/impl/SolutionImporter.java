package com.enation.app.base.core.service.solution.impl;

import com.enation.app.base.core.service.solution.ISolutionImporter;
import com.enation.app.base.core.service.solution.ISolutionInstaller;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.util.FileUtil;
import java.io.File;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class SolutionImporter
  implements ISolutionImporter
{
  protected final Logger logger = Logger.getLogger(getClass());
  private ISolutionInstaller solutionInstaller;

  @Transactional(propagation=Propagation.REQUIRED)
  public void imported(String productid, String zipPath, boolean cleanZip)
  {
    try
    {
      if (EopSetting.RUNMODE.equals("1")) {
        EopSetting.INSTALL_LOCK = "NO";
      }
      if ("1".equals(EopSetting.RESOURCEMODE)) {
        FileUtil.delete(EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes");
        FileUtil.delete(EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/themes");
      }

      if ("2".equals(EopSetting.RESOURCEMODE)) {
        FileUtil.delete(EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes");
      }

      String temppath = expand(productid, zipPath, cleanZip);
      File tempdir = new File(temppath);

      EopSite site = EopContext.getContext().getCurrentSite();

      this.solutionInstaller.install(site.getUserid(), site.getId(), productid);
      this.solutionInstaller.install(site.getUserid(), site.getId(), "base");

      Project prj = new Project();

      Delete delete = new Delete();
      delete.setProject(prj);
      delete.setDir(tempdir);
      delete.execute();

      if (EopSetting.RUNMODE.equals("1"))
        EopSetting.INSTALL_LOCK = "YES";
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error("导入解决方案", e.fillInStackTrace());
      if (EopSetting.RUNMODE.equals("1")) {
        EopSetting.INSTALL_LOCK = "YES";
      }
      throw new RuntimeException(e);
    }
  }

  private String expand(String productid, String zipPath, boolean cleanZip)
  {
    String temppath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productid;
    File tempdir = new File(temppath);
    tempdir.mkdirs();

    File zipFile = new File(zipPath);
    Project prj = new Project();
    Expand expand = new Expand();
    expand.setEncoding("UTF-8");
    expand.setProject(prj);
    expand.setSrc(zipFile);
    expand.setOverwrite(true);
    expand.setDest(tempdir);
    expand.execute();

    if (cleanZip)
    {
      Delete delete = new Delete();
      delete.setDir(zipFile);
      delete.execute();
    }

    return temppath;
  }

  public ISolutionInstaller getSolutionInstaller() {
    return this.solutionInstaller;
  }

  public void setSolutionInstaller(ISolutionInstaller solutionInstaller) {
    this.solutionInstaller = solutionInstaller;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SolutionImporter
 * JD-Core Version:    0.6.1
 */