package com.enation.framework.image;

public class ImageRuntimeException extends RuntimeException
{
  public ImageRuntimeException(String path, String proesstype)
  {
    super("对图片" + path + "进行" + proesstype + "出错");
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.image.ImageRuntimeException
 * JD-Core Version:    0.6.1
 */