package com.enation.app.shop.core.service.impl.batchimport.util;

import com.enation.app.base.core.service.ISettingService;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.image.IThumbnailCreator;
import com.enation.framework.image.ThumbnailCreatorFactory;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.PrintStream;
import org.apache.log4j.Logger;

public class GoodsImageReader
{
  protected final Logger logger = Logger.getLogger(getClass());
  private ISettingService settingService;

  private String getSettingValue(String code)
  {
    return this.settingService.getSetting("photo", code);
  }
  public String[] read(String folder, String goodsid, String excludeStr) {
    String[] exclude = null;

    if (!StringUtil.isEmpty(excludeStr)) {
      exclude = excludeStr.split(",");
    }
    int thumbnail_pic_width = 107;
    int thumbnail_pic_height = 107;
    int detail_pic_width = 320;
    int detail_pic_height = 240;
    int album_pic_width = 550;
    int album_pic_height = 412;
    try
    {
      thumbnail_pic_width = Integer.valueOf(getSettingValue("thumbnail_pic_width").toString()).intValue();
      thumbnail_pic_height = Integer.valueOf(getSettingValue("thumbnail_pic_height").toString()).intValue();

      detail_pic_width = Integer.valueOf(getSettingValue("detail_pic_width").toString()).intValue();
      detail_pic_height = Integer.valueOf(getSettingValue("detail_pic_height").toString()).intValue();

      album_pic_width = Integer.valueOf(getSettingValue("album_pic_width").toString()).intValue();
      album_pic_height = Integer.valueOf(getSettingValue("album_pic_height").toString()).intValue();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("开始导入商品[" + goodsid + "]图片...");
    }

    String imgsString = "";
    String defaultImg = "";
    String datafolder = folder + "/images";
    File file = new File(datafolder);

    if (!file.exists()) {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("商品[" + goodsid + "]图片目录不存在跳过");
      }
      return null;
    }

    String[] imgNames = file.list();
    for (String imgname : imgNames) {
      String lowname = imgname.toLowerCase();
      if ((lowname.endsWith(".jpg")) || (lowname.endsWith(".gif")) || (lowname.endsWith(".jpeg")) || (lowname.endsWith(".bmp")) || (lowname.endsWith(".png")))
      {
        if (this.logger.isDebugEnabled()) {
          this.logger.debug("处理图片[" + imgname + "]...");
        }

        if (isExclude(exclude, imgname))
        {
          if (this.logger.isDebugEnabled()) {
            this.logger.debug("图片[" + imgname + "]在排除范围，跳过.");
          }

        }
        else
        {
          String sourcePath = datafolder + "/" + imgname;

          if (!new File(sourcePath).isDirectory())
          {
            imgname = imgname.toLowerCase();
            FileUtil.copyFile(sourcePath, EopSetting.IMG_SERVER_PATH + "/attachment/goods/" + goodsid + "/" + imgname);
            String goodsImg = "fs:/attachment/goods/" + goodsid + "/" + imgname;

            if (this.logger.isDebugEnabled()) {
              this.logger.debug("为图片[" + imgname + "]生成各种缩略图...");
            }
            String targetPath = getThumbPath(imgname, "_thumbnail", goodsid);
            createThumb(sourcePath, targetPath, thumbnail_pic_width, thumbnail_pic_height);

            targetPath = getThumbPath(imgname, "_small", goodsid);
            createThumb(sourcePath, targetPath, detail_pic_width, detail_pic_height);

            targetPath = getThumbPath(imgname, "_big", goodsid);
            createThumb(sourcePath, targetPath, album_pic_width, album_pic_height);

            if (!imgsString.equals("")) imgsString = imgsString + ",";
            imgsString = imgsString + goodsImg;

            if (imgname.startsWith("default")) {
              defaultImg = goodsImg;
            }
          }
        }
      }

    }

    if ((defaultImg.equals("")) && (imgsString != null)) defaultImg = imgsString.split(",")[0];

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("缩略图生成完毕，图片字串信息为image_file[" + imgsString + "],defaultImg[" + defaultImg + "]");
    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug(" 商品[" + goodsid + "]图片导入完成");
    }

    return new String[] { imgsString, defaultImg };
  }

  private boolean isExclude(String[] exclude, String imageName)
  {
    if (exclude == null) return false;
    for (String ex : exclude) {
      if (imageName.startsWith(ex)) return true;
    }
    return false;
  }

  private void createThumb(String sourcePath, String targetPath, int width, int height)
  {
    IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(sourcePath, targetPath);
    thumbnailCreator.resize(width, height);
  }
  private String getThumbPath(String filename, String shortName, String goodsid) {
    String name = UploadUtil.getThumbPath(filename, shortName);
    String path = EopSetting.IMG_SERVER_PATH + "/attachment/goods/" + goodsid;
    File file = new File(path);

    if (!file.exists()) file.mkdir();

    path = path + "/" + name;
    return path;
  }
  public ISettingService getSettingService() {
    return this.settingService;
  }
  public void setSettingService(ISettingService settingService) {
    this.settingService = settingService;
  }

  public static void main(String[] args) {
    System.out.println("aaa.jpg".endsWith(".jpg"));
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.util.GoodsImageReader
 * JD-Core Version:    0.6.1
 */