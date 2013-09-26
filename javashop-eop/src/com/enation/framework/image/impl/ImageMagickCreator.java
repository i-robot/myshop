package com.enation.framework.image.impl;

import com.enation.framework.image.IThumbnailCreator;
import com.enation.framework.image.ImageRuntimeException;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import magick.ImageInfo;
import magick.MagickApiException;
import magick.MagickException;
import magick.MagickImage;

public class ImageMagickCreator
  implements IThumbnailCreator
{
  private String source;
  private String target;
  private ImageInfo info;
  private MagickImage image;
  private double width;
  private double height;

  static
  {
    System.setProperty("jmagick.systemclassloader", "no");
  }

  public ImageMagickCreator(String _source, String _target)
    throws IOException
  {
    this.source = _source;
    this.target = _target;
    File f = new File(_source);
    FileInputStream fis = new FileInputStream(f);
    try
    {
      byte[] b = new byte[(int)f.length()];
      fis.read(b);

      this.info = new ImageInfo();
      this.image = new MagickImage(this.info, b);

      this.width = this.image.getDimension().getWidth();
      this.height = this.image.getDimension().getHeight();
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new ImageRuntimeException(this.source, "构造jmagickutils");
    } finally {
      if (fis != null)
        fis.close();
      fis = null;
    }
  }

  public void resize(int w, int h)
  {
    int x = 0; int y = 0;
    x = y = 0;
    int target_w = w;
    int target_h = h;

    MagickImage scaled = null;
    try
    {
      if (this.width / this.height > w / h) {
        target_w = w;
        target_h = (int)(target_w * this.height / this.width);
        x = 0;
        y = (h - target_h) / 2;
      }

      if (this.width / this.height < w / h) {
        target_h = h;
        target_w = (int)(target_h * this.width / this.height);
        y = 0;
        x = (w - target_w) / 2;
      }
      MagickImage thumb_img = this.image.scaleImage(target_w, target_h);
      MagickImage blankImage = new MagickImage();

      byte[] pixels = new byte[w * h * 4];
      for (int i = 0; i < w * h; i++) {
        pixels[(4 * i)] = -1;
        pixels[(4 * i + 1)] = -1;
        pixels[(4 * i + 2)] = -1;
        pixels[(4 * i + 3)] = -1;
      }

      blankImage.constituteImage(w, h, "RGBA", pixels);
      blankImage.compositeImage(3, 
        thumb_img, x, y);
      blankImage.setFileName(this.target);
      blankImage.writeImage(this.info);
    }
    catch (MagickApiException ex) {
      ex.printStackTrace();
      throw new ImageRuntimeException(this.source, "生成缩略图");
    } catch (MagickException ex) {
      ex.printStackTrace();
      throw new ImageRuntimeException(this.source, "生成缩略图");
    } finally {
      if (scaled != null) {
        scaled.destroyImages();
      }
      if (this.image != null)
        this.image.destroyImages();
    }
  }

  public static void main(String[] args)
    throws IOException
  {
    ImageMagickCreator creator = new ImageMagickCreator("d:/1.jpg", "d:/2.jpg");
    creator.resize(200, 200);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.image.impl.ImageMagickCreator
 * JD-Core Version:    0.6.1
 */