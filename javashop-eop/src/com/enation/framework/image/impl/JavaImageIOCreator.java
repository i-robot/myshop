package com.enation.framework.image.impl;

import com.enation.framework.image.IThumbnailCreator;
import com.enation.framework.util.FileUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class JavaImageIOCreator
  implements IThumbnailCreator
{
  private String srcFile;
  private String destFile;
  private static Map<String, String> extMap = new HashMap(5);

  static { extMap.put("jpg", "JPEG");
    extMap.put("jpeg", "JPEG");
    extMap.put("gif", "GIF");
    extMap.put("png", "PNG");
    extMap.put("bmp", "BMP");
  }

  public JavaImageIOCreator(String sourcefile, String targetFile)
  {
    this.srcFile = sourcefile;
    this.destFile = targetFile;
  }

  public void resize(int w, int h)
  {
    String ext = FileUtil.getFileExt(this.srcFile).toLowerCase();
    try
    {
      BufferedImage image = ImageIO.read(new File(this.srcFile));
      ImageIO.write(Lanczos.resizeImage(image, w, h), ext, new File(this.destFile));
    } catch (IOException e) {
      throw new RuntimeException("生成缩略图错误", e);
    }
    BufferedImage image;
  }

  public static void main(String[] args)
  {
    JavaImageIOCreator creator = new JavaImageIOCreator("d:/1.jpg", "d:/1_j_180.jpg");
    creator.resize(180, 180);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.image.impl.JavaImageIOCreator
 * JD-Core Version:    0.6.1
 */