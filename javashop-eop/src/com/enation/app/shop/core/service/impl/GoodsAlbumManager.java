package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.service.ISettingService;
import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.service.IGoodsAlbumManager;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.image.IThumbnailCreator;
import com.enation.framework.image.ThumbnailCreatorFactory;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.ImageMagickMaskUtil;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public final class GoodsAlbumManager extends BaseSupport<Goods>
  implements IGoodsAlbumManager
{
  private IGoodsManager goodsManager;
  private ISettingService settingService;

  public void delete(String photoName)
  {
    if ((photoName != null) && (!photoName.startsWith("http://static.enationsoft.com/attachment/"))) {
      photoName = UploadUtil.replacePath(photoName);
      photoName = photoName.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
      FileUtil.delete(photoName);
      FileUtil.delete(UploadUtil.getThumbPath(photoName, "_thumbnail"));
      FileUtil.delete(UploadUtil.getThumbPath(photoName, "_small"));
      FileUtil.delete(UploadUtil.getThumbPath(photoName, "_big"));
    }
  }

  public void delete(Integer[] goodsid) {
    String id_str = StringUtil.arrayToString(goodsid, ",");
    String sql = "select image_file from goods where goods_id in (" + id_str + ")";
    List<Map> goodsList = this.baseDaoSupport.queryForList(sql, new Object[0]);
    for (Map goods : goodsList) {
      String name = (String)goods.get("image_file");
      if ((name != null) && (!name.equals(""))) {
        String[] pname = name.split(",");
        for (String n : pname)
          delete(n);
      }
    }
  }

  public String[] upload(File file, String fileFileName)
  {
    String fileName = null;
    String filePath = "";

    String[] path = new String[2];

    if ((file != null) && (fileFileName != null)) {
      String ext = FileUtil.getFileExt(fileFileName);
      fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;
      filePath = EopSetting.IMG_SERVER_PATH + getContextFolder() + "/attachment/goods/";
      path[0] = (EopSetting.IMG_SERVER_DOMAIN + getContextFolder() + "/attachment/goods/" + fileName);
      filePath = filePath + fileName;
      FileUtil.createFile(file, filePath);

      String watermark = this.settingService.getSetting("photo", "watermark");
      String marktext = this.settingService.getSetting("photo", "marktext");
      String markpos = this.settingService.getSetting("photo", "markpos");
      String markcolor = this.settingService.getSetting("photo", "markcolor");
      String marksize = this.settingService.getSetting("photo", "marksize");

      marktext = StringUtil.isEmpty(marktext) ? "水印文字" : marktext;
      marksize = StringUtil.isEmpty(marksize) ? "12" : marksize;

      if ((watermark != null) && (watermark.equals("on"))) {
        ImageMagickMaskUtil magickMask = new ImageMagickMaskUtil();
        magickMask.mask(filePath, marktext, markcolor, Integer.valueOf(marksize).intValue(), Integer.valueOf(markpos).intValue(), EopSetting.EOP_PATH + "/font/st.TTF");
      }

    }

    return path;
  }

  public static String getContextFolder() {
    return EopContext.getContext().getContextPath();
  }

  public void createThumb(String filepath, String thumbName, int thumbnail_pic_width, int thumbnail_pic_height)
  {
    String imgDomain = "http://static.enationsoft.com/attachment/";
    if ((filepath != null) && (!filepath.startsWith(imgDomain))) {
      filepath = filepath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
      thumbName = thumbName.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
      File tempFile = new File(thumbName);
      if (!tempFile.exists())
      {
        IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filepath, thumbName);
        thumbnailCreator.resize(thumbnail_pic_width, thumbnail_pic_height);
      }
    }
  }

  public int getTotal()
  {
    return this.goodsManager.list().size();
  }

  public void recreate(int start, int end)
  {
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

    List goodsList = this.goodsManager.list();
    for (int i = start - 1; i < end; i++) {
      Map goods = (Map)goodsList.get(i);
      String imageFile = (String)goods.get("image_file");
      if (imageFile != null) {
        String[] imgFileAr = imageFile.split(",");
        System.out.println("Create thumbnail image, the index:" + (i + 1));
        for (String imgFile : imgFileAr) {
          String realPath = UploadUtil.replacePath(imgFile);
          realPath = realPath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
          System.out.println("Image file:" + realPath);

          String thumbName = UploadUtil.getThumbPath(realPath, "_thumbnail");
          createThumb1(realPath, thumbName, thumbnail_pic_width, thumbnail_pic_height);

          thumbName = UploadUtil.getThumbPath(realPath, "_small");
          createThumb1(realPath, thumbName, detail_pic_width, detail_pic_height);

          thumbName = UploadUtil.getThumbPath(realPath, "_big");
          createThumb1(realPath, thumbName, album_pic_width, album_pic_height);
        }
      }
    }
  }

  private String getSettingValue(String code) {
    return this.settingService.getSetting("photo", code);
  }

  private void createThumb1(String filepath, String thumbName, int thumbnail_pic_width, int thumbnail_pic_height)
  {
    if (!StringUtil.isEmpty(filepath)) {
      String ctx = EopContext.getContext().getContextPath();
      filepath = filepath.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_PATH + ctx);
      thumbName = thumbName.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_PATH + ctx);
      IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filepath, thumbName);
      thumbnailCreator.resize(thumbnail_pic_width, thumbnail_pic_height);
    }
  }

  public ISettingService getSettingService() {
    return this.settingService;
  }

  public void setSettingService(ISettingService settingService) {
    this.settingService = settingService;
  }

  public IGoodsManager getGoodsManager() {
    return this.goodsManager;
  }

  public void setGoodsManager(IGoodsManager goodsManager) {
    this.goodsManager = goodsManager;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsAlbumManager
 * JD-Core Version:    0.6.1
 */