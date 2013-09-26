package com.enation.app.base.core.action;

import com.enation.app.base.core.model.FileNode;
import com.enation.app.base.core.service.IExplorerManager;
import com.enation.app.base.core.service.impl.ExplorerManager;
import com.enation.eop.resource.IThemeManager;
import com.enation.eop.resource.model.Theme;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.util.List;
import java.util.Map;

public class ThemeFileAction extends WWAction
{
  private IExplorerManager explorerManager;
  private IThemeManager themeManager;
  private int themeid;
  private String name;
  private String folderName;
  private String content;
  private String newName;
  private String parent;
  private File file;
  private String fileFileName;
  private String newPath;
  private String type;
  private List<FileNode> fileList;

  private void initExplorer()
  {
    this.folderName = (StringUtil.isEmpty(this.folderName) ? "" : this.folderName);
    String themePath = this.themeManager.getTheme(Integer.valueOf(this.themeid)).getPath();
    String respath = "";
    if (EopSetting.RESOURCEMODE.equals("1"))
      respath = EopSetting.IMG_SERVER_PATH;
    else {
      respath = EopSetting.EOP_PATH;
    }
    String folderPath = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes/" + themePath + this.folderName;

    String styleFldPath = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/themes/" + themePath + this.folderName;

    if ("style".equals(this.type))
      this.explorerManager = new ExplorerManager(styleFldPath);
    else {
      this.explorerManager = new ExplorerManager(folderPath);
    }
    if ((this.folderName.equals("")) || (this.folderName.equals("/"))) {
      this.parent = "/";
    } else {
      this.parent = this.folderName.substring(0, this.folderName.lastIndexOf('/') - 1);
      this.parent = (this.parent.substring(0, this.parent.lastIndexOf('/')) + "/");
    }
  }

  public String list() {
    initExplorer();

    this.fileList = this.explorerManager.list(null);
    return "list";
  }

  public String delete() {
    try {
      initExplorer();
      this.explorerManager.delete(this.name);
      this.msgs.add("文件删除成功");
      this.urls.put("返回目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
    } catch (Exception e) {
      this.urls.put("返回目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
      this.msgs.add(e.getMessage());
    }

    return "message";
  }

  public String toNewFile()
  {
    return "new_file";
  }

  public String addFile() {
    if (StringUtil.isEmpty(this.folderName)) this.folderName = "/"; try
    {
      initExplorer();
      FileNode node = new FileNode();
      node.setContent(this.content);
      node.setName(this.name);
      node.setIsfolder(Integer.valueOf(0));
      this.explorerManager.add(node);
      this.msgs.add("文件创建成功");
      this.urls.put("返回目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
    } catch (Exception e) {
      this.urls.put("返回目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
      this.msgs.add(e.getMessage());
    }

    return "message";
  }

  public String addFolder() {
    try {
      initExplorer();
      FileNode node = new FileNode();
      node.setName(this.name);
      node.setIsfolder(Integer.valueOf(1));
      this.explorerManager.add(node);
      this.json = "{result:1}";
    } catch (Exception e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String toEdit()
  {
    initExplorer();
    FileNode node = this.explorerManager.get(this.name);
    this.name = node.getName();
    this.content = node.getContent();
    return "edit_file";
  }

  public String edit()
  {
    if (StringUtil.isEmpty(this.folderName)) this.folderName = "/";
    try
    {
      initExplorer();
      FileNode node = new FileNode();
      node.setContent(this.content);
      node.setName(this.name);
      this.explorerManager.edit(node);
      this.json = "{result:1}";
      this.msgs.add("文件修改成功!");
      this.urls.put("目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
    } catch (Exception e) {
      this.urls.put("目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
      this.msgs.add(e.getMessage());
    }

    return "message";
  }

  public String rename() {
    try {
      initExplorer();
      this.explorerManager.rename(this.name, this.newName);
      this.json = "{result:1}";
    } catch (Exception e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String upload()
  {
    try {
      initExplorer();
      this.explorerManager.upload(this.file, this.fileFileName);
      this.json = "{result:1}";
    } catch (Exception e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String move() {
    try {
      initExplorer();
      this.explorerManager.move(this.name, this.folderName, this.newPath);
      this.json = "{result:1}";
    } catch (Exception e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFolderName() {
    if (StringUtil.isEmpty(this.folderName)) this.folderName = "/";
    return this.folderName;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getNewName() {
    return this.newName;
  }

  public void setNewName(String newName) {
    this.newName = newName;
  }

  public List<FileNode> getFileList() {
    return this.fileList;
  }

  public void setFileList(List<FileNode> fileList) {
    this.fileList = fileList;
  }

  public IThemeManager getThemeManager() {
    return this.themeManager;
  }

  public void setThemeManager(IThemeManager themeManager) {
    this.themeManager = themeManager;
  }

  public int getThemeid() {
    return this.themeid;
  }

  public void setThemeid(int themeid) {
    this.themeid = themeid;
  }

  public String getParent() {
    return this.parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public File getFile() {
    return this.file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getFileFileName() {
    return this.fileFileName;
  }

  public void setFileFileName(String fileFileName) {
    this.fileFileName = fileFileName;
  }

  public String getNewPath() {
    return this.newPath;
  }

  public void setNewPath(String newPath) {
    this.newPath = newPath;
  }

  public IExplorerManager getExplorerManager() {
    return this.explorerManager;
  }

  public void setExplorerManager(IExplorerManager explorerManager) {
    this.explorerManager = explorerManager;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.ThemeFileAction
 * JD-Core Version:    0.6.1
 */