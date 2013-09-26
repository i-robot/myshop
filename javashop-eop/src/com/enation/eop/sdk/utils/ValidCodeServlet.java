package com.enation.eop.sdk.utils;

import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidCodeServlet extends HttpServlet
{
  public static final String SESSION_VALID_CODE = "valid_code";
  private Random generator = new Random();

  private static char[] captchars = { 'a', 'b', 'c', 'd', 'e', '2', '3', '4', '5', '6', '7', '8', 'g', 'f', 'y', 'n', 'm', 'n', 'p', 'w', 'x' };

  private Font getFont()
  {
    Random random = new Random();
    Font[] font = new Font[5];
    font[0] = new Font("Ravie", 0, 45);
    font[1] = new Font("Antique Olive Compact", 0, 45);
    font[2] = new Font("Forte", 0, 45);
    font[3] = new Font("Wide Latin", 0, 40);
    font[4] = new Font("Gill Sans Ultra Bold", 0, 45);
    return font[random.nextInt(5)];
  }

  private Color getRandColor()
  {
    Random random = new Random();
    Color[] color = new Color[10];
    color[0] = new Color(32, 158, 25);
    color[1] = new Color(218, 42, 19);
    color[2] = new Color(31, 75, 208);
    return color[random.nextInt(3)];
  }

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    resp.addHeader("Cache-Control", "no-cache");
    resp.addHeader("Cache-Control", "no-store");
    resp.addHeader("Cache-Control", "must-revalidate");
    resp.setHeader("Pragma", "no-cache");
    resp.setDateHeader("Expires", -1L);

    String vtype = "";

    if (req.getParameter("vtype") != null) {
      vtype = req.getParameter("vtype");
    }

    int ImageWidth = 200;
    int ImageHeight = 100;

    int car = captchars.length - 1;

    String test = "";
    for (int i = 0; i < 4; i++) {
      test = test + captchars[(this.generator.nextInt(car) + 1)];
    }

    ThreadContextHolder.getSessionContext().setAttribute("valid_code" + vtype, test);

    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(resp.getOutputStream());

    BufferedImage bi = new BufferedImage(ImageWidth + 10, ImageHeight, 13);

    Graphics2D graphics = bi.createGraphics();

    graphics.setColor(Color.white);

    graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());

    graphics.setColor(Color.black);
    AttributedString attstr = new AttributedString(test);

    TextLayout textTl = new TextLayout(test, new Font("Courier", 1, 70), new FontRenderContext(null, true, false));

    AffineTransform textAt = graphics.getTransform();
    graphics.setFont(new Font("Courier", 1, 70));
    graphics.setColor(getRandColor());
    graphics.drawString(test, 10, 70);

    int w = bi.getWidth();
    int h = bi.getHeight();
    shear(graphics, w, h, Color.white);

    resp.setContentType("image/jpg");

    encoder.encode(bi);
  }

  private void shear(Graphics g, int w1, int h1, Color color)
  {
    shearX(g, w1, h1, color);
    shearY(g, w1, h1, color);
  }

  public void shearX(Graphics g, int w1, int h1, Color color)
  {
    int period = this.generator.nextInt(2);

    boolean borderGap = true;
    int frames = 1;
    int phase = this.generator.nextInt(2);

    for (int i = 0; i < h1; i++) {
      double d = (double) (period >> 1) * Math.sin(i / period + 6.283185307179586D * phase / frames);

      g.copyArea(0, i, w1, 1, (int)d, 0);
      if (borderGap) {
        g.setColor(color);
        g.drawLine((int)d, i, 0, i);
        g.drawLine((int)d + w1, i, w1, i);
      }
    }
  }

  public void shearY(Graphics g, int w1, int h1, Color color)
  {
    int period = this.generator.nextInt(40) + 10;

    boolean borderGap = true;
    int frames = 20;
    int phase = 7;
    for (int i = 0; i < w1; i++) {
      double d = (double) (period >> 1) * Math.sin(i / period + 6.283185307179586D * phase / frames);

      g.copyArea(i, 0, 1, h1, 0, (int)d);
      if (borderGap) {
        g.setColor(color);
        g.drawLine(i, (int)d, i, 0);
        g.drawLine(i, (int)d + h1, i, h1);
      }
    }
  }

  private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c)
  {
    g.setColor(c);
    int dX = x2 - x1;
    int dY = y2 - y1;

    double lineLength = Math.sqrt(dX * dX + dY * dY);

    double scale = thickness / (2.0D * lineLength);

    double ddx = -scale * dY;
    double ddy = scale * dX;
    ddx += (ddx > 0.0D ? 0.5D : -0.5D);
    ddy += (ddy > 0.0D ? 0.5D : -0.5D);
    int dx = (int)ddx;
    int dy = (int)ddy;

    int[] xPoints = new int[4];
    int[] yPoints = new int[4];

    xPoints[0] = (x1 + dx);
    yPoints[0] = (y1 + dy);
    xPoints[1] = (x1 - dx);
    yPoints[1] = (y1 - dy);
    xPoints[2] = (x2 - dx);
    yPoints[2] = (y2 - dy);
    xPoints[3] = (x2 + dx);
    yPoints[3] = (y2 + dy);

    g.fillPolygon(xPoints, yPoints, 4);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.utils.ValidCodeServlet
 * JD-Core Version:    0.6.1
 */