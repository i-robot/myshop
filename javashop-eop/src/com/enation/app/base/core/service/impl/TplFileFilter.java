package com.enation.app.base.core.service.impl;

import com.enation.framework.util.FileUtil;
import java.io.File;
import java.io.FileFilter;

public class TplFileFilter
  implements FileFilter
{
  private static final String[] EXTS = { "html", "xml" };

  public boolean accept(File pathname) { String fileName = pathname.getName();

    if (pathname.isDirectory()) {
      if (fileName.equals("images")) return false;
      if (fileName.equals("css")) return false;
      if (fileName.equals("js")) return false;
      if (fileName.equals(".svn")) return false;
      return true;
    }
    String ext = FileUtil.getFileExt(fileName).toLowerCase();
    for (String e : EXTS) {
      if (ext.equals(e)) {
        return true;
      }
    }
    return false;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.TplFileFilter
 * JD-Core Version:    0.6.1
 */