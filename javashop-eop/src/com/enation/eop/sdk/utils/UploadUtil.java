package com.enation.eop.sdk.utils;

import com.enation.eop.processor.MultipartRequestWrapper;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.image.IThumbnailCreator;
import com.enation.framework.image.ThumbnailCreatorFactory;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;

public class UploadUtil
{
  public static String uploadUseRequest(String name, String subFolder, String allowext)
  {
    if (name == null) throw new IllegalArgumentException("file or filename object is null");
    if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    if (!MultipartFormDataRequest.isMultipartFormData(request)) throw new RuntimeException("request data is not MultipartFormData");
    try
    {
      String encoding = EopSetting.ENCODING;
      if (StringUtil.isEmpty(encoding)) {
        encoding = "UTF-8";
      }

      MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request, null, 1048576000, MultipartFormDataRequest.COSPARSER, encoding);
      request = new MultipartRequestWrapper(request, mrequest);
      ThreadContextHolder.setHttpRequest(request);

      Hashtable files = mrequest.getFiles();
      UploadFile file = (UploadFile)files.get(name);
      if (file.getInpuStream() == null) return null;

      String fileFileName = file.getFileName();

      String fileName = null;
      String filePath = "";

      String ext = FileUtil.getFileExt(fileFileName);

      if (!allowext.equals(ext)) {
        throw new IllegalArgumentException("不被允许的上传文件类型");
      }

      fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();

      filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(EopContext.getContext().getContextPath()).append("/attachment/").toString();
      if (subFolder != null) {
        filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
      }

      String path = new StringBuilder().append(EopSetting.FILE_STORE_PREFIX).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();

      filePath = new StringBuilder().append(filePath).append(fileName).toString();
      FileUtil.createFile(file.getInpuStream(), filePath);

      return path;
    }
    catch (UploadException e)
    {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static String uploadUseRequest(String name, String subFolder)
  {
    if (name == null) throw new IllegalArgumentException("file or filename object is null");
    if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    if (!MultipartFormDataRequest.isMultipartFormData(request)) throw new RuntimeException("request data is not MultipartFormData");
    try
    {
      String encoding = EopSetting.ENCODING;
      if (StringUtil.isEmpty(encoding)) {
        encoding = "UTF-8";
      }

      MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request, null, 1048576000, MultipartFormDataRequest.COSPARSER, encoding);
      request = new MultipartRequestWrapper(request, mrequest);
      ThreadContextHolder.setHttpRequest(request);

      Hashtable files = mrequest.getFiles();
      UploadFile file = (UploadFile)files.get(name);
      if (file.getInpuStream() == null) return null;

      String fileFileName = file.getFileName();

      String fileName = null;
      String filePath = "";

      String ext = FileUtil.getFileExt(fileFileName);
      fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();

      filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(EopContext.getContext().getContextPath()).append("/attachment/").toString();
      if (subFolder != null) {
        filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
      }

      String path = new StringBuilder().append(EopSetting.FILE_STORE_PREFIX).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();

      filePath = new StringBuilder().append(filePath).append(fileName).toString();
      FileUtil.createFile(file.getInpuStream(), filePath);

      return path;
    }
    catch (UploadException e)
    {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static String upload(File file, String fileFileName, String subFolder)
  {
    if ((file == null) || (fileFileName == null)) throw new IllegalArgumentException("file or filename object is null");
    if (subFolder == null) throw new IllegalArgumentException("subFolder is null");

    if (!FileUtil.isAllowUp(fileFileName)) {
      throw new IllegalArgumentException("不被允许的上传文件类型");
    }

    String fileName = null;
    String filePath = "";

    String ext = FileUtil.getFileExt(fileFileName);
    fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();

    filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(EopContext.getContext().getContextPath()).append("/attachment/").toString();
    if (subFolder != null) {
      filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
    }

    String path = new StringBuilder().append(EopSetting.FILE_STORE_PREFIX).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();

    filePath = new StringBuilder().append(filePath).append(fileName).toString();
    FileUtil.createFile(file, filePath);

    return path;
  }

  public static String replacePath(String path)
  {
    if (StringUtil.isEmpty(path)) return path;

    return path.replaceAll(EopSetting.FILE_STORE_PREFIX, new StringBuilder().append(EopSetting.IMG_SERVER_DOMAIN).append(EopContext.getContext().getContextPath()).toString());
  }

  public static String[] upload(File file, String fileFileName, String subFolder, int width, int height)
  {
    if ((file == null) || (fileFileName == null)) throw new IllegalArgumentException("file or filename object is null");
    if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
    String fileName = null;
    String filePath = "";
    String[] path = new String[2];
    if (!FileUtil.isAllowUp(fileFileName)) {
      throw new IllegalArgumentException("不被允许的上传文件类型");
    }
    String ext = FileUtil.getFileExt(fileFileName);
    fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();

    filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(getContextFolder()).append("/attachment/").toString();
    if (subFolder != null) {
      filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
    }

    path[0] = new StringBuilder().append(EopSetting.IMG_SERVER_DOMAIN).append(getContextFolder()).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();
    filePath = new StringBuilder().append(filePath).append(fileName).toString();
    FileUtil.createFile(file, filePath);
    String thumbName = getThumbPath(filePath, "_thumbnail");

    IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filePath, thumbName);
    thumbnailCreator.resize(width, height);
    path[1] = getThumbPath(path[0], "_thumbnail");
    return path;
  }

  public static void deleteFile(String filePath)
  {
    filePath = filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
    FileUtil.delete(filePath);
  }

  public static void deleteFileAndThumb(String filePath)
  {
    filePath = filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
    FileUtil.delete(filePath);
    FileUtil.delete(getThumbPath(filePath, "_thumbnail"));
  }

  private static String getContextFolder()
  {
    return EopContext.getContext().getContextPath();
  }

  public static String getThumbPath(String filePath, String shortName)
  {
    String pattern = "(.*)([\\.])(.*)";
    String thumbPath = new StringBuilder().append("$1").append(shortName).append("$2$3").toString();

    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(filePath);
    if (m.find()) {
      String s = m.replaceAll(thumbPath);

      return s;
    }
    return null;
  }

  public static void main(String[] args)
  {
    System.out.println(getThumbPath("http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg", "_thumbnail"));
    System.out.println(getThumbPath("/user/1/1/attachment/goods/2001010101030.jpg", "_thumbnail"));
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.utils.UploadUtil
 * JD-Core Version:    0.6.1
 */