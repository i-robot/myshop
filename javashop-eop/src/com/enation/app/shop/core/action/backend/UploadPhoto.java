package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.service.IGoodsAlbumManager;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UploadPhoto extends WWAction
{
  private IGoodsAlbumManager goodsAlbumManager;
  private File filedata;
  private String filedataFileName;
  private String photoName;

  public void delFile(String fileName)
  {
    if ((fileName != null) && (!fileName.trim().equals(""))) {
      File file = new File(StringUtil.getRootPath() + "/" + fileName);
      file.delete();
      File fileThumb = new File(StringUtil.getRootPath() + getThumbpath(fileName));

      if (fileThumb.exists())
        fileThumb.delete();
    }
  }

  private String getThumbpath(String file) {
    String fStr = "";
    if (!file.trim().equals("")) {
      String[] arr = file.split("/");
      fStr = "/" + arr[0] + "/" + arr[1] + "/thumb/" + arr[2];
    }
    return fStr;
  }

  public String execute()
  {
    if (this.filedata != null)
    {
      String[] names = this.goodsAlbumManager.upload(this.filedata, this.filedataFileName);

      this.json = (names[0] + "," + names[0]);
    }

    return "json_message";
  }

  public String delPhoto()
  {
    this.goodsAlbumManager.delete(this.photoName);
    this.json = "{'result':0,'message:':'图片删除成功'}";
    return "json_message";
  }

  private static String getName(String path) {
    String regEx = "(/goods/)(.*)";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(path);
    String name = "";

    while (m.find()) {
      name = m.group();
    }
    return name;
  }

  public static void main(String[] args)
  {
    String path = "http://www.javashop.com/attachment/goods/200901020201052381.jpg";

    String name = getName(path);
  }
  public File getFiledata() {
    return this.filedata;
  }

  public void setFiledata(File filedata) {
    this.filedata = filedata;
  }

  public String getFiledataFileName() {
    return this.filedataFileName;
  }

  public void setFiledataFileName(String filedataFileName) {
    this.filedataFileName = filedataFileName;
  }

  public String getPhotoName()
  {
    return this.photoName;
  }

  public void setPhotoName(String photoName)
  {
    this.photoName = photoName;
  }

  public void setGoodsAlbumManager(IGoodsAlbumManager goodsAlbumManager) {
    this.goodsAlbumManager = goodsAlbumManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.UploadPhoto
 * JD-Core Version:    0.6.1
 */