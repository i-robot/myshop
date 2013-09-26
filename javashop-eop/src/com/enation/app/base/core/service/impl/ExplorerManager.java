package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.FileNode;
import com.enation.app.base.core.service.IExplorerManager;
import com.enation.framework.util.FileUtil;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExplorerManager
  implements IExplorerManager
{
  private String rootPath;

  public ExplorerManager(String rootPath)
  {
    if (rootPath == null) throw new IllegalArgumentException("param rootPath is NULL");
    this.rootPath = rootPath;
  }

  public void add(FileNode node) throws IOException
  {
    if (node == null) throw new IllegalArgumentException("param FileNode is NULL");

    String filePath = this.rootPath + "/" + node.getName();
    File file = new File(filePath);

    if (file.exists()) {
      throw new RuntimeException("文件" + node.getName() + "已存在");
    }

    if (node.getIsfolder().intValue() == 1) {
      file.mkdirs();
    } else {
      file.createNewFile();
      FileUtil.write(filePath, node.getContent());
    }
  }

  public void delete(String name)
  {
    FileUtil.delete(this.rootPath + name);
  }

  public void edit(FileNode node) {
    FileUtil.write(this.rootPath + node.getName(), node.getContent());
  }

  public List<FileNode> list(FileFilter filter) {
    List list = new ArrayList();
    File file = new File(this.rootPath);

    File[] files = null;
    if (filter != null)
      files = file.listFiles(filter);
    else {
      files = file.listFiles();
    }
    if (files == null) return list;
    for (File f : files)
    {
      FileNode node = fileToNode(f);
      list.add(node);
    }
    return list;
  }

  public List<FileNode> listex(FileFilter filter)
  {
    List listD = new ArrayList();
    List listF = new ArrayList();
    File file = new File(this.rootPath);

    File[] files = null;
    if (filter != null)
      files = file.listFiles(filter);
    else {
      files = file.listFiles();
    }
    if (files == null) return listD;
    for (File f : files) {
      FileNode node = fileToNode(f);
      if (f.isDirectory())
        listD.add(node);
      else
        listF.add(node);
    }
    FileNode node;
    for (Iterator i$ = listF.iterator(); i$.hasNext(); listD.add(node)) node = (FileNode)i$.next();
    return listD;
  }

  public void move(String name, String oldFolder, String newFolder)
  {
    String oldRoot = this.rootPath;
    if ((newFolder.startsWith("/")) && (!oldFolder.equals("/"))) {
      this.rootPath = this.rootPath.replaceAll(oldFolder, "");
    }

    if ((newFolder.startsWith("/")) && (oldFolder.equals("/"))) {
      newFolder = newFolder.substring(1, newFolder.length());
    }

    if (!newFolder.endsWith("/")) {
      newFolder = newFolder + "/";
    }

    String target = this.rootPath + newFolder + name;
    if (!new File(this.rootPath + newFolder).exists()) {
      throw new RuntimeException(newFolder + " 不存在");
    }

    FileUtil.copyFile(oldRoot + name, target);
    FileUtil.delete(oldRoot + name);
  }

  public void rename(String oldname, String newname)
  {
    File file = new File(this.rootPath + oldname);
    File newFile = new File(this.rootPath + newname);
    boolean result = file.renameTo(newFile);
  }

  public FileNode get(String name)
  {
    File file = new File(this.rootPath + name);
    FileNode node = fileToNode(file);
    node.setContent(FileUtil.read(this.rootPath + name, "UTF-8"));
    return node;
  }

  private FileNode fileToNode(File f) {
    FileNode node = new FileNode();
    node.setName(f.getName());
    node.setIsfolder(Integer.valueOf(f.isDirectory() ? 1 : 0));
    node.setSize(Long.valueOf(f.length()));
    node.setLastmodify(Long.valueOf(f.lastModified()));
    if (node.getIsfolder().intValue() == 0) {
      node.setExt(FileUtil.getFileExt(node.getName()).toLowerCase());
    }

    return node;
  }

  public void upload(File file, String fileFileName)
  {
    String filePath = this.rootPath + fileFileName;
    File temp = new File(filePath);
    if (temp.exists()) throw new RuntimeException("文件" + fileFileName + "已经存在");
    FileUtil.createFile(file, filePath);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.ExplorerManager
 * JD-Core Version:    0.6.1
 */