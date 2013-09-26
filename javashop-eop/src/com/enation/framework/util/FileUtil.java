package com.enation.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;

public class FileUtil
{
  public static void createFile(InputStream in, String filePath)
  {
    if (in == null) throw new RuntimeException("create file error: inputstream is null");
    int potPos = filePath.lastIndexOf('/') + 1;
    String folderPath = filePath.substring(0, potPos);
    createFolder(folderPath);
    FileOutputStream outputStream = null;
    try {
      outputStream = new FileOutputStream(filePath);
      byte[] by = new byte[1024];
      int c;
      while ((c = in.read(by)) != -1)
        outputStream.write(by, 0, c);
    }
    catch (IOException e) {
      e.getStackTrace().toString();
    }
    try {
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isAllowUp(String logoFileName)
  {
    logoFileName = logoFileName.toLowerCase();
    String allowTYpe = "gif,jpg,bmp,swf,png,rar,doc,docx,xls,xlsx,pdf,zip,ico";
    if ((!logoFileName.trim().equals("")) && (logoFileName.length() > 0)) {
      String ex = logoFileName.substring(logoFileName.lastIndexOf(".") + 1, logoFileName.length());

      return allowTYpe.toUpperCase().indexOf(ex.toUpperCase()) >= 0;
    }
    return false;
  }

  public static void write(String filePath, String fileContent)
  {
    try
    {
      FileOutputStream fo = new FileOutputStream(filePath);
      OutputStreamWriter out = new OutputStreamWriter(fo, "UTF-8");

      out.write(fileContent);

      out.close();
    }
    catch (IOException ex) {
      System.err.println("Create File Error!");
      ex.printStackTrace();
    }
  }

  public static String read(String filePath, String code)
  {
    if ((code == null) || (code.equals(""))) {
      code = "UTF-8";
    }
    String fileContent = "";
    File file = new File(filePath);
    try {
      InputStreamReader read = new InputStreamReader(new FileInputStream(file), code);

      BufferedReader reader = new BufferedReader(read);
      String line;
      while ((line = reader.readLine()) != null) {
        fileContent = fileContent + line + "\n";
      }
      read.close();
      read = null;
      reader.close();
      read = null;
    } catch (Exception ex) {
      ex.printStackTrace();
      fileContent = "";
    }
    return fileContent;
  }

  public static void delete(String filePath)
  {
    try
    {
      File file = new File(filePath);
      if (file.exists())
        if (file.isDirectory())
          FileUtils.deleteDirectory(file);
        else
          file.delete();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public static boolean exist(String filepath) {
    File file = new File(filepath);

    return file.exists();
  }

  public static void createFolder(String filePath)
  {
    try
    {
      File file = new File(filePath);
      if (!file.exists())
        file.mkdirs();
    }
    catch (Exception ex) {
      System.err.println("Make Folder Error:" + ex.getMessage());
    }
  }

  public static void renameFile(String from, String to)
  {
    try
    {
      File file = new File(from);
      if (file.exists())
        file.renameTo(new File(to));
    }
    catch (Exception ex) {
      System.err.println("Rename File/Folder Error:" + ex.getMessage());
    }
  }

  public static String getFileExt(String fileName)
  {
    int potPos = fileName.lastIndexOf('.') + 1;
    String type = fileName.substring(potPos, fileName.length());
    return type;
  }

  public static void createFile(File file, String filePath)
  {
    int potPos = filePath.lastIndexOf('/') + 1;
    String folderPath = filePath.substring(0, potPos);
    createFolder(folderPath);
    FileOutputStream outputStream = null;
    FileInputStream fileInputStream = null;
    try {
      outputStream = new FileOutputStream(filePath);
      fileInputStream = new FileInputStream(file);
      byte[] by = new byte[1024];
      int c;
      while ((c = fileInputStream.read(by)) != -1)
        outputStream.write(by, 0, c);
    }
    catch (IOException e) {
      e.getStackTrace().toString();
    }
    try {
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      fileInputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String readFile(String resource)
  {
    InputStream stream = getResourceAsStream(resource);
    String content = readStreamToString(stream);

    return content;
  }

  public static InputStream getResourceAsStream(String resource)
  {
    String stripped = resource.startsWith("/") ? resource.substring(1) : resource;

    InputStream stream = null;
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    if (classLoader != null) {
      stream = classLoader.getResourceAsStream(stripped);
    }

    return stream;
  }

  public static String readStreamToString(InputStream stream) {
    String fileContent = "";
    try
    {
      InputStreamReader read = new InputStreamReader(stream, "utf-8");
      BufferedReader reader = new BufferedReader(read);
      String line;
      while ((line = reader.readLine()) != null) {
        fileContent = fileContent + line + "\n";
      }
      read.close();
      read = null;
      reader.close();
      read = null;
    } catch (Exception ex) {
      fileContent = "";
    }
    return fileContent;
  }

  public static void removeFile(File path)
  {
    if (path.isDirectory())
      try {
        FileUtils.deleteDirectory(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  public static void copyFile(String srcFile, String destFile)
  {
    try
    {
      if (exist(srcFile))
        FileUtils.copyFile(new File(srcFile), new File(destFile));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static void copyFolder(String sourceFolder, String destinationFolder)
  {
    try
    {
      File sourceF = new File(sourceFolder);
      if (sourceF.exists())
        FileUtils.copyDirectory(new File(sourceFolder), new File(destinationFolder));
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("copy file error");
    }
  }

  public static void copyNewFile(String sourceFolder, String targetFolder)
  {
    try
    {
      File sourceF = new File(sourceFolder);

      if (!targetFolder.endsWith("/")) targetFolder = targetFolder + "/";

      if (sourceF.exists()) {
        File[] filelist = sourceF.listFiles();
        for (File f : filelist) {
          File targetFile = new File(targetFolder + f.getName());

          if (f.isFile())
          {
            if ((!targetFile.exists()) || (FileUtils.isFileNewer(f, targetFile))) {
              FileUtils.copyFileToDirectory(f, new File(targetFolder));
            }
          }

        }

      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("copy file error");
    }
  }

  public static void unZip(String zipPath, String targetFolder, boolean cleanZip)
  {
    File folderFile = new File(targetFolder);
    File zipFile = new File(zipPath);
    Project prj = new Project();
    Expand expand = new Expand();
    expand.setEncoding("UTF-8");
    expand.setProject(prj);
    expand.setSrc(zipFile);
    expand.setOverwrite(true);
    expand.setDest(folderFile);
    expand.execute();

    if (cleanZip)
    {
      Delete delete = new Delete();
      delete.setProject(prj);
      delete.setDir(zipFile);
      delete.execute();
    }
  }

  public static void main(String[] arg)
  {
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.FileUtil
 * JD-Core Version:    0.6.1
 */