// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 2013/9/26 10:57:43
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Lanczos.java

package com.enation.framework.image.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Lanczos
{
    private static class ContributionInfo
    {

        private double weight;
        private int pixel;





        private ContributionInfo()
        {
        }

        ContributionInfo(ContributionInfo contributioninfo)
        {
            this();
        }
    }


    public Lanczos()
    {
    }

    public static BufferedImage resizeImage(BufferedImage image, double ratio)
    {
        return resizeImage(image, (int)((double)image.getWidth() * ratio + 0.5D), (int)((double)image.getHeight() * ratio + 0.5D));
    }

    public static BufferedImage resizeImage(BufferedImage image, double xRatio, double yRatio)
    {
        return resizeImage(image, (int)((double)image.getWidth() * xRatio + 0.5D), (int)((double)image.getHeight() * yRatio + 0.5D));
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height)
    {
        double xFactor = ((double)width * 1.0D) / (double)image.getWidth();
        double yFactor = ((double)height * 1.0D) / (double)image.getHeight();
        BufferedImage resizeImage = new BufferedImage(width, height, image.getType());
        BufferedImage filterImage = null;
        if(xFactor * yFactor > 0.26500000000000001D)
        {
            filterImage = new BufferedImage(width, image.getHeight(), image.getType());
            horizontalFilter(image, filterImage, xFactor);
            verticalFilter(filterImage, resizeImage, yFactor);
        } else
        {
            filterImage = new BufferedImage(image.getWidth(), height, image.getType());
            verticalFilter(image, filterImage, yFactor);
            horizontalFilter(filterImage, resizeImage, xFactor);
        }
        return resizeImage;
    }

    private static void verticalFilter(BufferedImage image, BufferedImage resizeImage, double yFactor)
    {
        double scale = Math.max(1.0D / yFactor, 1.0D);
        double support = scale * 3D;
        if(support < 0.5D)
        {
            support = 0.5D;
            scale = 1.0D;
        }
        scale = 1.0D / scale;
        for(int y = 0; y < resizeImage.getHeight(); y++)
        {
            double center = ((double)y + 0.5D) / yFactor;
            int start = (int)(Math.max(center - support - 9.9999999999999995E-007D, 0.0D) + 0.5D);
            int stop = (int)(Math.min(center + support, image.getHeight()) + 0.5D);
            double density = 0.0D;
            ContributionInfo contribution[] = new ContributionInfo[stop - start];
            int n;
            for(n = 0; n < stop - start; n++)
            {
                contribution[n] = new ContributionInfo(null);
                contribution[n].pixel = start + n;
                contribution[n].weight = getResizeFilterWeight(scale * (((double)(start + n) - center) + 0.5D));
                density += contribution[n].weight;
            }

            if(density != 0.0D && density != 1.0D)
            {
                density = 1.0D / density;
                for(int i = 0; i < n; i++)
                    contribution[i].weight *= density;

            }
            for(int x = 0; x < resizeImage.getWidth(); x++)
            {
                double red = 0.0D;
                double green = 0.0D;
                double blue = 0.0D;
                for(int i = 0; i < n; i++)
                {
                    double alpha = contribution[i].weight;
                    int rgb = image.getRGB(x, contribution[i].pixel);
                    red += alpha * (double)(rgb >> 16 & 0xff);
                    green += alpha * (double)(rgb >> 8 & 0xff);
                    blue += alpha * (double)(rgb & 0xff);
                }

                int rgb = roundToQuantum(red) << 16 | roundToQuantum(green) << 8 | roundToQuantum(blue);
                resizeImage.setRGB(x, y, rgb);
            }

        }

    }

    private static void horizontalFilter(BufferedImage image, BufferedImage resizeImage, double xFactor)
    {
        double scale = Math.max(1.0D / xFactor, 1.0D);
        double support = scale * 3D;
        if(support < 0.5D)
        {
            support = 0.5D;
            scale = 1.0D;
        }
        scale = 1.0D / scale;
        for(int x = 0; x < resizeImage.getWidth(); x++)
        {
            double center = ((double)x + 0.5D) / xFactor;
            int start = (int)(Math.max(center - support - 9.9999999999999995E-007D, 0.0D) + 0.5D);
            int stop = (int)(Math.min(center + support, image.getWidth()) + 0.5D);
            double density = 0.0D;
            ContributionInfo contribution[] = new ContributionInfo[stop - start];
            int n;
            for(n = 0; n < stop - start; n++)
            {
                contribution[n] = new ContributionInfo(null);
                contribution[n].pixel = start + n;
                contribution[n].weight = getResizeFilterWeight(scale * (((double)(start + n) - center) + 0.5D));
                density += contribution[n].weight;
            }

            if(density != 0.0D && density != 1.0D)
            {
                density = 1.0D / density;
                for(int i = 0; i < n; i++)
                    contribution[i].weight *= density;

            }
            for(int y = 0; y < resizeImage.getHeight(); y++)
            {
                double red = 0.0D;
                double green = 0.0D;
                double blue = 0.0D;
                for(int i = 0; i < n; i++)
                {
                    double alpha = contribution[i].weight;
                    int rgb = image.getRGB(contribution[i].pixel, y);
                    red += alpha * (double)(rgb >> 16 & 0xff);
                    green += alpha * (double)(rgb >> 8 & 0xff);
                    blue += alpha * (double)(rgb & 0xff);
                }

                int rgb = roundToQuantum(red) << 16 | roundToQuantum(green) << 8 | roundToQuantum(blue);
                resizeImage.setRGB(x, y, rgb);
            }

        }

    }

    private static double getResizeFilterWeight(double x)
    {
        double blur = Math.abs(x) / 1.0D;
        double scale = 0.33333333333333331D;
        scale = sinc(blur * scale);
        return scale * sinc(blur);
    }

    private static double sinc(double x)
    {
        if(x == 0.0D)
            return 1.0D;
        else
            return Math.sin(3.1415926535897931D * x) / (3.1415926535897931D * x);
    }

    private static int roundToQuantum(double value)
    {
        if(value <= 0.0D)
            return 0;
        if(value >= 255D)
            return 255;
        else
            return (int)(value + 0.5D);
    }

    public static void main(String args[])
        throws Exception
    {
        BufferedImage image = ImageIO.read(new File("d:/1.jpg"));
        ImageIO.write(resizeImage(image, 800, 800), "JPEG", new File("d:/1d800.jpg"));
    }

    private static final double WORK_LOAD_FACTOR = 0.26500000000000001D;
    private static final double LANCZOS_SUPPORT = 3D;
    private static final double LANCZOS_WINDOW = 3D;
    private static final double LANCZOS_SCALE = 1D;
    private static final double LANCZOS_BLUR = 1D;
    private static final double EPSILON = 9.9999999999999995E-007D;
}