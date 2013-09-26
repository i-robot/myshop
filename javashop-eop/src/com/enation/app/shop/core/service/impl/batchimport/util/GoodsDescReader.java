package com.enation.app.shop.core.service.impl.batchimport.util;

import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.util.FileUtil;
import java.io.File;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoodsDescReader
{
  protected final Logger logger = Logger.getLogger(getClass());

  public String read(String folder, String goodsid)
  {
    String descFilePath = folder + "/desc.htm";

    if (!new File(descFilePath).exists()) {
      descFilePath = folder + "/desc.html";
      if ((!new File(descFilePath).exists()) && 
        (this.logger.isDebugEnabled())) {
        this.logger.debug("描述[" + descFilePath + "]文件不存,跳过");
      }

      return null;
    }

    String bodyHtml = null;

    Document doc = Jsoup.parse(FileUtil.read(descFilePath, "GBK"));
    Elements bodys = doc.select("body");
    if ((bodys != null) && (!bodys.isEmpty())) {
      Element bodyEl = bodys.get(0);
      bodyHtml = bodyEl.html();
      bodyHtml = bodyHtml.replaceAll("src=\"desc.files/", "src=\"fs:/attachment/ckeditor/" + goodsid + "/");
      bodyHtml = bodyHtml.replaceAll("src=\"desc_files/", "src=\"fs:/attachment/ckeditor/" + goodsid + "/");

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("read商品[" + goodsid + "]描述信息完成");
      }

    }

    String folderPath = folder + "/desc.files";
    if (new File(folderPath).exists()) {
      FileUtil.copyFolder(folderPath, EopSetting.IMG_SERVER_PATH + "/attachment/ckeditor/" + goodsid);
      if (this.logger.isDebugEnabled())
        this.logger.debug("copy商品[" + goodsid + "]描述图片完成");
    }
    else {
      folderPath = folder + "/desc_files";

      if (new File(folderPath).exists()) {
        FileUtil.copyFolder(folderPath, EopSetting.IMG_SERVER_PATH + "/attachment/ckeditor/" + goodsid);
        if (this.logger.isDebugEnabled()) {
          this.logger.debug("copy商品[" + goodsid + "]描述图片完成");
        }
      }
      else if (this.logger.isDebugEnabled()) {
        this.logger.debug("商品[" + goodsid + "]描述图片不存在，跳过导入描述图片");
      }

    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("导入商品[" + goodsid + "]描述 完成");
    }

    return bodyHtml;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.util.GoodsDescReader
 * JD-Core Version:    0.6.1
 */